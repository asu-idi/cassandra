/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.cql3.statements;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;

import org.apache.cassandra.auth.Permission;
import org.apache.cassandra.config.*;
import org.apache.cassandra.cql3.CFName;
import org.apache.cassandra.cql3.CQL3Type;
import org.apache.cassandra.cql3.ColumnIdentifier;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.CollectionType;
import org.apache.cassandra.db.marshal.CounterColumnType;
import org.apache.cassandra.db.marshal.ReversedType;
import org.apache.cassandra.db.view.View;
import org.apache.cassandra.exceptions.*;
import org.apache.cassandra.schema.IndexMetadata;
import org.apache.cassandra.schema.Indexes;
import org.apache.cassandra.schema.TableParams;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.service.MigrationManager;
import org.apache.cassandra.service.QueryState;
import org.apache.cassandra.transport.Event;

import static org.apache.cassandra.thrift.ThriftValidation.validateColumnFamily;

public class AlterTableStatement extends SchemaAlteringStatement
{
    public enum Type
    {
        ADD, ALTER, DROP, OPTS, RENAME
    }

    public final Type oType;
    public final CQL3Type.Raw validator;
    public final ColumnIdentifier.Raw rawColumnName;
    private final TableAttributes attrs;
    private final Map<ColumnIdentifier.Raw, ColumnIdentifier.Raw> renames;
    private final boolean isStatic; // Only for ALTER ADD
    private final Long deleteTimestamp;

    public AlterTableStatement(CFName name,
                               Type type,
                               ColumnIdentifier.Raw columnName,
                               CQL3Type.Raw validator,
                               TableAttributes attrs,
                               Map<ColumnIdentifier.Raw, ColumnIdentifier.Raw> renames,
                               boolean isStatic,
                               Long deleteTimestamp)
    {
        super(name);
        this.oType = type;
        this.rawColumnName = columnName;
        this.validator = validator; // used only for ADD/ALTER commands
        this.attrs = attrs;
        this.renames = renames;
        this.isStatic = isStatic;
        this.deleteTimestamp = deleteTimestamp;
    }

    public void checkAccess(ClientState state) throws UnauthorizedException, InvalidRequestException
    {
        state.hasColumnFamilyAccess(keyspace(), columnFamily(), Permission.ALTER);
    }

    public void validate(ClientState state)
    {
        // validated in announceMigration()
    }

    public Event.SchemaChange announceMigration(QueryState queryState, boolean isLocalOnly) throws RequestValidationException
    {
        CFMetaData meta = validateColumnFamily(keyspace(), columnFamily());
        if (meta.isView())
            throw new InvalidRequestException("Cannot use ALTER TABLE on Materialized View");

        CFMetaData cfm = meta.copy();

        CQL3Type validator = this.validator == null ? null : this.validator.prepare(keyspace());
        ColumnIdentifier columnName = null;
        ColumnDefinition def = null;
        if (rawColumnName != null)
        {
            columnName = rawColumnName.prepare(cfm);
            def = cfm.getColumnDefinition(columnName);
        }

        List<ViewDefinition> viewUpdates = null;
        Iterable<ViewDefinition> views = View.findAll(keyspace(), columnFamily());

        switch (oType)
        {
            case ALTER:
                throw new InvalidRequestException("Altering of types is not allowed");
            case ADD:
                assert columnName != null;
                if (cfm.isDense())
                    throw new InvalidRequestException("Cannot add new column to a COMPACT STORAGE table");

                if (isStatic)
                {
                    if (!cfm.isCompound())
                        throw new InvalidRequestException("Static columns are not allowed in COMPACT STORAGE tables");
                    if (cfm.clusteringColumns().isEmpty())
                        throw new InvalidRequestException("Static columns are only useful (and thus allowed) if the table has at least one clustering column");
                }

                if (def != null)
                {
                    switch (def.kind)
                    {
                        case PARTITION_KEY:
                        case CLUSTERING:
                            throw new InvalidRequestException(String.format("Invalid column name %s because it conflicts with a PRIMARY KEY part", columnName));
                        default:
                            throw new InvalidRequestException(String.format("Invalid column name %s because it conflicts with an existing column", columnName));
                    }
                }

                // Cannot re-add a dropped counter column. See #7831.
                if (meta.isCounter() && meta.getDroppedColumns().containsKey(columnName.bytes))
                    throw new InvalidRequestException(String.format("Cannot re-add previously dropped counter column %s", columnName));

                AbstractType<?> type = validator.getType();
                if (type.isCollection() && type.isMultiCell())
                {
                    if (!cfm.isCompound())
                        throw new InvalidRequestException("Cannot use non-frozen collections in COMPACT STORAGE tables");
                    if (cfm.isSuper())
                        throw new InvalidRequestException("Cannot use non-frozen collections with super column families");

                    // If there used to be a non-frozen collection column with the same name (that has been dropped),
                    // we could still have some data using the old type, and so we can't allow adding a collection
                    // with the same name unless the types are compatible (see #6276).
                    CFMetaData.DroppedColumn dropped = cfm.getDroppedColumns().get(columnName.bytes);
                    if (dropped != null && dropped.type instanceof CollectionType
                        && dropped.type.isMultiCell() && !type.isCompatibleWith(dropped.type))
                    {
                        String message =
                            String.format("Cannot add a collection with the name %s because a collection with the same name"
                                          + " and a different type (%s) has already been used in the past",
                                          columnName,
                                          dropped.type.asCQL3Type());
                        throw new InvalidRequestException(message);
                    }
                }

                cfm.addColumnDefinition(isStatic
                                        ? ColumnDefinition.staticDef(cfm, columnName.bytes, type)
                                        : ColumnDefinition.regularDef(cfm, columnName.bytes, type));

                // Adding a column to a table which has an include all view requires the column to be added to the view
                // as well
                if (!isStatic)
                {
                    for (ViewDefinition view : views)
                    {
                        if (view.includeAllColumns)
                        {
                            ViewDefinition viewCopy = view.copy();
                            viewCopy.metadata.addColumnDefinition(ColumnDefinition.regularDef(viewCopy.metadata, columnName.bytes, type));
                            if (viewUpdates == null)
                                viewUpdates = new ArrayList<>();
                            viewUpdates.add(viewCopy);
                        }
                    }
                }
                break;

            case DROP:
                assert columnName != null;
                if (!cfm.isCQLTable())
                    throw new InvalidRequestException("Cannot drop columns from a non-CQL3 table");
                if (def == null)
                    throw new InvalidRequestException(String.format("Column %s was not found in table %s", columnName, columnFamily()));

                switch (def.kind)
                {
                    case PARTITION_KEY:
                    case CLUSTERING:
                        throw new InvalidRequestException(String.format("Cannot drop PRIMARY KEY part %s", columnName));
                    case REGULAR:
                    case STATIC:
                        ColumnDefinition toDelete = null;
                        for (ColumnDefinition columnDef : cfm.partitionColumns())
                        {
                            if (columnDef.name.equals(columnName))
                            {
                                toDelete = columnDef;
                                break;
                            }
                        }
                        assert toDelete != null;
                        cfm.removeColumnDefinition(toDelete);
                        cfm.recordColumnDrop(toDelete, deleteTimestamp == null ? queryState.getTimestamp() : deleteTimestamp);
                        break;
                }

                // If the dropped column is required by any secondary indexes
                // we reject the operation, as the indexes must be dropped first
                Indexes allIndexes = cfm.getIndexes();
                if (!allIndexes.isEmpty())
                {
                    ColumnFamilyStore store = Keyspace.openAndGetStore(cfm);
                    Set<IndexMetadata> dependentIndexes = store.indexManager.getDependentIndexes(def);
                    if (!dependentIndexes.isEmpty())
                        throw new InvalidRequestException(String.format("Cannot drop column %s because it has " +
                                                                        "dependent secondary indexes (%s)",
                                                                        def,
                                                                        dependentIndexes.stream()
                                                                                        .map(i -> i.name)
                                                                                        .collect(Collectors.joining(","))));
                }

                if (!Iterables.isEmpty(views))
                    throw new InvalidRequestException(String.format("Cannot drop column %s on base table with materialized views.",
                                                                    columnName.toString(),
                                                                    keyspace()));
                break;
            case OPTS:
                if (attrs == null)
                    throw new InvalidRequestException("ALTER TABLE WITH invoked, but no parameters found");
                attrs.validate();

                TableParams params = attrs.asAlteredTableParams(cfm.params);

                if (!Iterables.isEmpty(views) && params.gcGraceSeconds == 0)
                {
                    throw new InvalidRequestException("Cannot alter gc_grace_seconds of the base table of a " +
                                                      "materialized view to 0, since this value is used to TTL " +
                                                      "undelivered updates. Setting gc_grace_seconds too low might " +
                                                      "cause undelivered updates to expire " +
                                                      "before being replayed.");
                }

                if (meta.isCounter() && params.defaultTimeToLive > 0)
                    throw new InvalidRequestException("Cannot set default_time_to_live on a table with counters");

                cfm.params(params);

                break;
            case RENAME:
                for (Map.Entry<ColumnIdentifier.Raw, ColumnIdentifier.Raw> entry : renames.entrySet())
                {
                    ColumnIdentifier from = entry.getKey().prepare(cfm);
                    ColumnIdentifier to = entry.getValue().prepare(cfm);
                    cfm.renameColumn(from, to);

                    // If the view includes a renamed column, it must be renamed in the view table and the definition.
                    for (ViewDefinition view : views)
                    {
                        if (!view.includes(from)) continue;

                        ViewDefinition viewCopy = view.copy();
                        ColumnIdentifier viewFrom = entry.getKey().prepare(viewCopy.metadata);
                        ColumnIdentifier viewTo = entry.getValue().prepare(viewCopy.metadata);
                        viewCopy.renameColumn(viewFrom, viewTo);

                        if (viewUpdates == null)
                            viewUpdates = new ArrayList<>();
                        viewUpdates.add(viewCopy);
                    }
                }
                break;
        }

        MigrationManager.announceColumnFamilyUpdate(cfm, viewUpdates, isLocalOnly);
        return new Event.SchemaChange(Event.SchemaChange.Change.UPDATED, Event.SchemaChange.Target.TABLE, keyspace(), columnFamily());
    }

    public String toString()
    {
        return String.format("AlterTableStatement(name=%s, type=%s, column=%s, validator=%s)",
                             cfName,
                             oType,
                             rawColumnName,
                             validator);
    }
}
