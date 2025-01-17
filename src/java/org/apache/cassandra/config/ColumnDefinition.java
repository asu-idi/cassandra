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
package org.apache.cassandra.config;

import java.nio.ByteBuffer;
import java.util.*;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Collections2;

import org.apache.cassandra.cql3.*;
import org.apache.cassandra.db.rows.*;
import org.apache.cassandra.db.marshal.*;
import org.apache.cassandra.serializers.MarshalException;
import org.github.jamm.Unmetered;

@Unmetered
public class ColumnDefinition extends ColumnSpecification implements Comparable<ColumnDefinition>
{
    public static final Comparator<Object> asymmetricColumnDataComparator =
        (a, b) -> ((ColumnData) a).column().compareTo((ColumnDefinition) b);

    public static final int NO_POSITION = -1;

    public enum ClusteringOrder
    {
        ASC, DESC, NONE
    }

    /*
     * The type of CQL3 column this definition represents.
     * There is 4 main type of CQL3 columns: those parts of the partition key,
     * those parts of the clustering columns and amongst the others, regular and
     * static ones.
     *
     * Note that thrift only knows about definitions of type REGULAR (and
     * the ones whose position == NO_POSITION (-1)).
     */
    public enum Kind
    {
        // NOTE: if adding a new type, must modify comparisonOrder
        PARTITION_KEY,
        CLUSTERING,
        REGULAR,
        STATIC;

        public boolean isPrimaryKeyKind()
        {
            return this == PARTITION_KEY || this == CLUSTERING;
        }

    }

    public final Kind kind;

    /*
     * If the column is a partition key or clustering column, its position relative to
     * other columns of the same kind. Otherwise,  NO_POSITION (-1).
     *
     * Note that partition key and clustering columns are numbered separately so
     * the first clustering column is 0.
     */
    private final int position;

    private final Comparator<CellPath> cellPathComparator;
    private final Comparator<Object> asymmetricCellPathComparator;
    private final Comparator<? super Cell> cellComparator;

    /**
     * These objects are compared frequently, so we encode several of their comparison components
     * into a single long value so that this can be done efficiently
     */
    private final long comparisonOrder;

    private static long comparisonOrder(Kind kind, boolean isComplex, long position, ColumnIdentifier name)
    {
        assert position >= 0 && position < 1 << 12;
        return   (((long) kind.ordinal()) << 61)
               | (isComplex ? 1L << 60 : 0)
               | (position << 48)
               | (name.prefixComparison >>> 16);
    }

    public static ColumnDefinition partitionKeyDef(CFMetaData cfm, ByteBuffer name, AbstractType<?> type, int position)
    {
        return new ColumnDefinition(cfm, name, type, position, Kind.PARTITION_KEY);
    }

    public static ColumnDefinition partitionKeyDef(String ksName, String cfName, String name, AbstractType<?> type, int position)
    {
        return new ColumnDefinition(ksName, cfName, ColumnIdentifier.getInterned(name, true), type, position, Kind.PARTITION_KEY);
    }

    public static ColumnDefinition clusteringDef(CFMetaData cfm, ByteBuffer name, AbstractType<?> type, int position)
    {
        return new ColumnDefinition(cfm, name, type, position, Kind.CLUSTERING);
    }

    public static ColumnDefinition clusteringDef(String ksName, String cfName, String name, AbstractType<?> type, int position)
    {
        return new ColumnDefinition(ksName, cfName, ColumnIdentifier.getInterned(name, true),  type, position, Kind.CLUSTERING);
    }

    public static ColumnDefinition regularDef(CFMetaData cfm, ByteBuffer name, AbstractType<?> type)
    {
        return new ColumnDefinition(cfm, name, type, NO_POSITION, Kind.REGULAR);
    }

    public static ColumnDefinition regularDef(String ksName, String cfName, String name, AbstractType<?> type)
    {
        return new ColumnDefinition(ksName, cfName, ColumnIdentifier.getInterned(name, true), type, NO_POSITION, Kind.REGULAR);
    }

    public static ColumnDefinition staticDef(CFMetaData cfm, ByteBuffer name, AbstractType<?> type)
    {
        return new ColumnDefinition(cfm, name, type, NO_POSITION, Kind.STATIC);
    }

    public ColumnDefinition(CFMetaData cfm, ByteBuffer name, AbstractType<?> type, int position, Kind kind)
    {
        this(cfm.ksName,
             cfm.cfName,
             ColumnIdentifier.getInterned(name, cfm.getColumnDefinitionNameComparator(kind)),
             type,
             position,
             kind);
    }

    @VisibleForTesting
    public ColumnDefinition(String ksName,
                            String cfName,
                            ColumnIdentifier name,
                            AbstractType<?> type,
                            int position,
                            Kind kind)
    {
        super(ksName, cfName, name, type);
        assert name != null && type != null && kind != null;
        assert name.isInterned();
        assert (position == NO_POSITION) == !kind.isPrimaryKeyKind(); // The position really only make sense for partition and clustering columns (and those must have one),
                                                                      // so make sure we don't sneak it for something else since it'd breaks equals()
        this.kind = kind;
        this.position = position;
        this.cellPathComparator = makeCellPathComparator(kind, type);
        this.cellComparator = cellPathComparator == null ? ColumnData.comparator : (a, b) -> cellPathComparator.compare(a.path(), b.path());
        this.asymmetricCellPathComparator = cellPathComparator == null ? null : (a, b) -> cellPathComparator.compare(((Cell)a).path(), (CellPath) b);
        this.comparisonOrder = comparisonOrder(kind, isComplex(), Math.max(0, position), name);
    }

    private static Comparator<CellPath> makeCellPathComparator(Kind kind, AbstractType<?> type)
    {
        if (kind.isPrimaryKeyKind() || !type.isCollection() || !type.isMultiCell())
            return null;

        CollectionType collection = (CollectionType) type;

        return new Comparator<CellPath>()
        {
            public int compare(CellPath path1, CellPath path2)
            {
                if (path1.size() == 0 || path2.size() == 0)
                {
                    if (path1 == CellPath.BOTTOM)
                        return path2 == CellPath.BOTTOM ? 0 : -1;
                    if (path1 == CellPath.TOP)
                        return path2 == CellPath.TOP ? 0 : 1;
                    return path2 == CellPath.BOTTOM ? 1 : -1;
                }

                // This will get more complicated once we have non-frozen UDT and nested collections
                assert path1.size() == 1 && path2.size() == 1;
                return collection.nameComparator().compare(path1.get(0), path2.get(0));
            }
        };
    }

    public ColumnDefinition copy()
    {
        return new ColumnDefinition(ksName, cfName, name, type, position, kind);
    }

    public ColumnDefinition withNewName(ColumnIdentifier newName)
    {
        return new ColumnDefinition(ksName, cfName, newName, type, position, kind);
    }

    public ColumnDefinition withNewType(AbstractType<?> newType)
    {
        return new ColumnDefinition(ksName, cfName, name, newType, position, kind);
    }

    public boolean isPartitionKey()
    {
        return kind == Kind.PARTITION_KEY;
    }

    public boolean isClusteringColumn()
    {
        return kind == Kind.CLUSTERING;
    }

    public boolean isStatic()
    {
        return kind == Kind.STATIC;
    }

    public boolean isRegular()
    {
        return kind == Kind.REGULAR;
    }

    public ClusteringOrder clusteringOrder()
    {
        if (!isClusteringColumn())
            return ClusteringOrder.NONE;

        return type.isReversed() ? ClusteringOrder.DESC : ClusteringOrder.ASC;
    }

    public int position()
    {
        return position;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof ColumnDefinition))
            return false;

        ColumnDefinition cd = (ColumnDefinition) o;

        return Objects.equal(ksName, cd.ksName)
            && Objects.equal(cfName, cd.cfName)
            && Objects.equal(name, cd.name)
            && Objects.equal(type, cd.type)
            && Objects.equal(kind, cd.kind)
            && Objects.equal(position, cd.position);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(ksName, cfName, name, type, kind, position);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                      .add("name", name)
                      .add("type", type)
                      .add("kind", kind)
                      .add("position", position)
                      .toString();
    }

    public boolean isPrimaryKeyColumn()
    {
        return kind.isPrimaryKeyKind();
    }

    /**
     * Whether the name of this definition is serialized in the cell nane, i.e. whether
     * it's not just a non-stored CQL metadata.
     */
    public boolean isPartOfCellName(boolean isCQL3Table, boolean isSuper)
    {
        // When converting CQL3 tables to thrift, any regular or static column ends up in the cell name.
        // When it's a compact table however, the REGULAR definition is the name for the cell value of "dynamic"
        // column (so it's not part of the cell name) and it's static columns that ends up in the cell name.
        if (isCQL3Table)
            return kind == Kind.REGULAR || kind == Kind.STATIC;
        else if (isSuper)
            return kind == Kind.REGULAR;
        else
            return kind == Kind.STATIC;
    }

    /**
     * Converts the specified column definitions into column identifiers.
     *
     * @param definitions the column definitions to convert.
     * @return the column identifiers corresponding to the specified definitions
     */
    public static Collection<ColumnIdentifier> toIdentifiers(Collection<ColumnDefinition> definitions)
    {
        return Collections2.transform(definitions, new Function<ColumnDefinition, ColumnIdentifier>()
        {
            @Override
            public ColumnIdentifier apply(ColumnDefinition columnDef)
            {
                return columnDef.name;
            }
        });
    }

    public int compareTo(ColumnDefinition other)
    {
        if (this == other)
            return 0;

        if (comparisonOrder != other.comparisonOrder)
            return Long.compare(comparisonOrder, other.comparisonOrder);

        return this.name.compareTo(other.name);
    }

    public Comparator<CellPath> cellPathComparator()
    {
        return cellPathComparator;
    }

    public Comparator<Object> asymmetricCellPathComparator()
    {
        return asymmetricCellPathComparator;
    }

    public Comparator<? super Cell> cellComparator()
    {
        return cellComparator;
    }

    public boolean isComplex()
    {
        return cellPathComparator != null;
    }

    public boolean isSimple()
    {
        return !isComplex();
    }

    public CellPath.Serializer cellPathSerializer()
    {
        // Collections are our only complex so far, so keep it simple
        return CollectionType.cellPathSerializer;
    }

    public void validateCellValue(ByteBuffer value)
    {
        type.validateCellValue(value);
    }

    public void validateCellPath(CellPath path)
    {
        if (!isComplex())
            throw new MarshalException("Only complex cells should have a cell path");

        assert type instanceof CollectionType;
        ((CollectionType)type).nameComparator().validate(path.get(0));
    }

    public static String toCQLString(Iterable<ColumnDefinition> defs)
    {
        return toCQLString(defs.iterator());
    }

    public static String toCQLString(Iterator<ColumnDefinition> defs)
    {
        if (!defs.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(defs.next().name);
        while (defs.hasNext())
            sb.append(", ").append(defs.next().name);
        return sb.toString();
    }

    /**
     * The type of the cell values for cell belonging to this column.
     *
     * This is the same than the column type, except for non-frozen collections where it's the 'valueComparator'
     * of the collection.
     * 
     * This method should not be used to get value type of non-frozon UDT.
     */
    public AbstractType<?> cellValueType()
    {
        assert !(type instanceof UserType && type.isMultiCell());
        return type instanceof CollectionType && type.isMultiCell()
                ? ((CollectionType)type).valueComparator()
                : type;
    }


    public boolean isCounterColumn()
    {
        if (type instanceof CollectionType) // for thrift
            return ((CollectionType) type).valueComparator().isCounter();
        return type.isCounter();
    }
}
