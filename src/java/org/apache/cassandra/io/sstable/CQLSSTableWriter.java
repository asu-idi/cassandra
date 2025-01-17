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
package org.apache.cassandra.io.sstable;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import org.apache.cassandra.config.*;
import org.apache.cassandra.cql3.*;
import org.apache.cassandra.cql3.statements.CFStatement;
import org.apache.cassandra.cql3.statements.CreateTableStatement;
import org.apache.cassandra.cql3.statements.ParsedStatement;
import org.apache.cassandra.cql3.statements.UpdateStatement;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.SystemKeyspace;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.partitions.Partition;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.cassandra.exceptions.RequestValidationException;
import org.apache.cassandra.io.sstable.format.SSTableFormat;
import org.apache.cassandra.schema.KeyspaceMetadata;
import org.apache.cassandra.schema.KeyspaceParams;
import org.apache.cassandra.schema.SchemaKeyspace;
import org.apache.cassandra.schema.Tables;
import org.apache.cassandra.schema.Types;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.utils.Pair;

/**
 * Utility to write SSTables.
 * <p>
 * Typical usage looks like:
 * <pre>
 *   String schema = "CREATE TABLE myKs.myTable ("
 *                 + "  k int PRIMARY KEY,"
 *                 + "  v1 text,"
 *                 + "  v2 int"
 *                 + ")";
 *   String insert = "INSERT INTO myKs.myTable (k, v1, v2) VALUES (?, ?, ?)";
 *
 *   // Creates a new writer. You need to provide at least the directory where to write the created sstable,
 *   // the schema for the sstable to write and a (prepared) insert statement to use. If you do not use the
 *   // default partitioner (Murmur3Partitioner), you will also need to provide the partitioner in use, see
 *   // CQLSSTableWriter.Builder for more details on the available options.
 *   CQLSSTableWriter writer = CQLSSTableWriter.builder()
 *                                             .inDirectory("path/to/directory")
 *                                             .forTable(schema)
 *                                             .using(insert).build();
 *
 *   // Adds a nember of rows to the resulting sstable
 *   writer.addRow(0, "test1", 24);
 *   writer.addRow(1, "test2", null);
 *   writer.addRow(2, "test3", 42);
 *
 *   // Close the writer, finalizing the sstable
 *   writer.close();
 * </pre>
 *
 * Please note that {@code CQLSSTableWriter} is <b>not</b> thread-safe (multiple threads cannot access the
 * same instance). It is however safe to use multiple instances in parallel (even if those instance write
 * sstables for the same table).
 */
public class CQLSSTableWriter implements Closeable
{
    static
    {
        Config.setClientMode(true);
        // Partitioner is not set in client mode.
        if (DatabaseDescriptor.getPartitioner() == null)
            DatabaseDescriptor.setPartitionerUnsafe(Murmur3Partitioner.instance);
    }

    private final AbstractSSTableSimpleWriter writer;
    private final UpdateStatement insert;
    private final List<ColumnSpecification> boundNames;

    private CQLSSTableWriter(AbstractSSTableSimpleWriter writer, UpdateStatement insert, List<ColumnSpecification> boundNames)
    {
        this.writer = writer;
        this.insert = insert;
        this.boundNames = boundNames;
    }

    /**
     * Returns a new builder for a CQLSSTableWriter.
     *
     * @return the new builder.
     */
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Adds a new row to the writer.
     * <p>
     * This is a shortcut for {@code addRow(Arrays.asList(values))}.
     *
     * @param values the row values (corresponding to the bind variables of the
     * insertion statement used when creating by this writer).
     * @return this writer.
     */
    public CQLSSTableWriter addRow(Object... values)
    throws InvalidRequestException, IOException
    {
        return addRow(Arrays.asList(values));
    }

    /**
     * Adds a new row to the writer.
     * <p>
     * Each provided value type should correspond to the types of the CQL column
     * the value is for. The correspondance between java type and CQL type is the
     * same one than the one documented at
     * www.datastax.com/drivers/java/2.0/apidocs/com/datastax/driver/core/DataType.Name.html#asJavaClass().
     * <p>
     * If you prefer providing the values directly as binary, use
     * {@link #rawAddRow} instead.
     *
     * @param values the row values (corresponding to the bind variables of the
     * insertion statement used when creating by this writer).
     * @return this writer.
     */
    public CQLSSTableWriter addRow(List<Object> values)
    throws InvalidRequestException, IOException
    {
        int size = Math.min(values.size(), boundNames.size());
        List<ByteBuffer> rawValues = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            rawValues.add(values.get(i) == null ? null : ((AbstractType)boundNames.get(i).type).decompose(values.get(i)));
        return rawAddRow(rawValues);
    }

    /**
     * Adds a new row to the writer.
     * <p>
     * This is equivalent to the other addRow methods, but takes a map whose
     * keys are the names of the columns to add instead of taking a list of the
     * values in the order of the insert statement used during construction of
     * this write.
     * <p>
     * Please note that the column names in the map keys must be in lowercase unless
     * the declared column name is a
     * <a href="http://cassandra.apache.org/doc/cql3/CQL.html#identifiers">case-sensitive quoted identifier</a>
     * (in which case the map key must use the exact case of the column).
     *
     * @param values a map of colum name to column values representing the new
     * row to add. Note that if a column is not part of the map, it's value will
     * be {@code null}. If the map contains keys that does not correspond to one
     * of the column of the insert statement used when creating this writer, the
     * the corresponding value is ignored.
     * @return this writer.
     */
    public CQLSSTableWriter addRow(Map<String, Object> values)
    throws InvalidRequestException, IOException
    {
        int size = boundNames.size();
        List<ByteBuffer> rawValues = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ColumnSpecification spec = boundNames.get(i);
            Object value = values.get(spec.name.toString());
            rawValues.add(value == null ? null : ((AbstractType)spec.type).decompose(value));
        }
        return rawAddRow(rawValues);
    }

    /**
     * Adds a new row to the writer given already serialized values.
     *
     * @param values the row values (corresponding to the bind variables of the
     * insertion statement used when creating by this writer) as binary.
     * @return this writer.
     */
    public CQLSSTableWriter rawAddRow(ByteBuffer... values)
    throws InvalidRequestException, IOException
    {
        return rawAddRow(Arrays.asList(values));
    }

    /**
     * Adds a new row to the writer given already serialized values.
     * <p>
     * This is a shortcut for {@code rawAddRow(Arrays.asList(values))}.
     *
     * @param values the row values (corresponding to the bind variables of the
     * insertion statement used when creating by this writer) as binary.
     * @return this writer.
     */
    public CQLSSTableWriter rawAddRow(List<ByteBuffer> values)
    throws InvalidRequestException, IOException
    {
        if (values.size() != boundNames.size())
            throw new InvalidRequestException(String.format("Invalid number of arguments, expecting %d values but got %d", boundNames.size(), values.size()));

        QueryOptions options = QueryOptions.forInternalCalls(null, values);
        List<ByteBuffer> keys = insert.buildPartitionKeyNames(options);
        SortedSet<Clustering> clusterings = insert.createClustering(options);

        long now = System.currentTimeMillis() * 1000;
        // Note that we asks indexes to not validate values (the last 'false' arg below) because that triggers a 'Keyspace.open'
        // and that forces a lot of initialization that we don't want.
        UpdateParameters params = new UpdateParameters(insert.cfm,
                                                       insert.updatedColumns(),
                                                       options,
                                                       insert.getTimestamp(now, options),
                                                       insert.getTimeToLive(options),
                                                       Collections.<DecoratedKey, Partition>emptyMap());

        try
        {
            for (ByteBuffer key : keys)
            {
                for (Clustering clustering : clusterings)
                    insert.addUpdateForKey(writer.getUpdateFor(key), clustering, params);
            }
            return this;
        }
        catch (SSTableSimpleUnsortedWriter.SyncException e)
        {
            // If we use a BufferedWriter and had a problem writing to disk, the IOException has been
            // wrapped in a SyncException (see BufferedWriter below). We want to extract that IOE.
            throw (IOException)e.getCause();
        }
    }

    /**
     * Adds a new row to the writer given already serialized values.
     * <p>
     * This is equivalent to the other rawAddRow methods, but takes a map whose
     * keys are the names of the columns to add instead of taking a list of the
     * values in the order of the insert statement used during construction of
     * this write.
     *
     * @param values a map of colum name to column values representing the new
     * row to add. Note that if a column is not part of the map, it's value will
     * be {@code null}. If the map contains keys that does not correspond to one
     * of the column of the insert statement used when creating this writer, the
     * the corresponding value is ignored.
     * @return this writer.
     */
    public CQLSSTableWriter rawAddRow(Map<String, ByteBuffer> values)
    throws InvalidRequestException, IOException
    {
        int size = Math.min(values.size(), boundNames.size());
        List<ByteBuffer> rawValues = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ColumnSpecification spec = boundNames.get(i);
            rawValues.add(values.get(spec.name.toString()));
        }
        return rawAddRow(rawValues);
    }

    /**
     * Close this writer.
     * <p>
     * This method should be called, otherwise the produced sstables are not
     * guaranteed to be complete (and won't be in practice).
     */
    public void close() throws IOException
    {
        writer.close();
    }

    /**
     * A Builder for a CQLSSTableWriter object.
     */
    public static class Builder
    {
        private File directory;

        protected SSTableFormat.Type formatType = null;

        private CFMetaData schema;
        private UpdateStatement insert;
        private List<ColumnSpecification> boundNames;

        private boolean sorted = false;
        private long bufferSizeInMB = 128;

        protected Builder() {}

        /**
         * The directory where to write the sstables.
         * <p>
         * This is a mandatory option.
         *
         * @param directory the directory to use, which should exists and be writable.
         * @return this builder.
         *
         * @throws IllegalArgumentException if {@code directory} doesn't exist or is not writable.
         */
        public Builder inDirectory(String directory)
        {
            return inDirectory(new File(directory));
        }

        /**
         * The directory where to write the sstables (mandatory option).
         * <p>
         * This is a mandatory option.
         *
         * @param directory the directory to use, which should exists and be writable.
         * @return this builder.
         *
         * @throws IllegalArgumentException if {@code directory} doesn't exist or is not writable.
         */
        public Builder inDirectory(File directory)
        {
            if (!directory.exists())
                throw new IllegalArgumentException(directory + " doesn't exists");
            if (!directory.canWrite())
                throw new IllegalArgumentException(directory + " exists but is not writable");

            this.directory = directory;
            return this;
        }

        /**
         * The schema (CREATE TABLE statement) for the table for which sstable are to be created.
         * <p>
         * Please note that the provided CREATE TABLE statement <b>must</b> use a fully-qualified
         * table name, one that include the keyspace name.
         * <p>
         * This is a mandatory option.
         *
         * @param schema the schema of the table for which sstables are to be created.
         * @return this builder.
         *
         * @throws IllegalArgumentException if {@code schema} is not a valid CREATE TABLE statement
         * or does not have a fully-qualified table name.
         */
        public Builder forTable(String schema)
        {
            try
            {
                synchronized (CQLSSTableWriter.class)
                {
                    if (Schema.instance.getKSMetaData(SchemaKeyspace.NAME) == null)
                        Schema.instance.load(SchemaKeyspace.metadata());
                    if (Schema.instance.getKSMetaData(SystemKeyspace.NAME) == null)
                        Schema.instance.load(SystemKeyspace.metadata());

                    this.schema = getTableMetadata(schema);

                    // We need to register the keyspace/table metadata through Schema, otherwise we won't be able to properly
                    // build the insert statement in using().
                    KeyspaceMetadata ksm = Schema.instance.getKSMetaData(this.schema.ksName);
                    if (ksm == null)
                    {
                        createKeyspaceWithTable(this.schema);
                    }
                    else if (Schema.instance.getCFMetaData(this.schema.ksName, this.schema.cfName) == null)
                    {
                        addTableToKeyspace(ksm, this.schema);
                    }
                    return this;
                }
            }
            catch (RequestValidationException e)
            {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        /**
         * Creates the keyspace with the specified table.
         *
         * @param table the table that must be created.
         */
        private static void createKeyspaceWithTable(CFMetaData table)
        {
            Schema.instance.load(KeyspaceMetadata.create(table.ksName, KeyspaceParams.simple(1), Tables.of(table)));
        }

        /**
         * Adds the table to the to the specified keyspace.
         *
         * @param keyspace the keyspace to add to
         * @param table the table to add
         */
        private static void addTableToKeyspace(KeyspaceMetadata keyspace, CFMetaData table)
        {
            Schema.instance.load(table);
            Schema.instance.setKeyspaceMetadata(keyspace.withSwapped(keyspace.tables.with(table)));
        }

        /**
         * The partitioner to use.
         * <p>
         * By default, {@code Murmur3Partitioner} will be used. If this is not the partitioner used
         * by the cluster for which the SSTables are created, you need to use this method to
         * provide the correct partitioner.
         *
         * @param partitioner the partitioner to use.
         * @return this builder.
         */
        public Builder withPartitioner(IPartitioner partitioner)
        {
            this.schema = schema.copy(partitioner);
            return this;
        }

        /**
         * The INSERT statement defining the order of the values to add for a given CQL row.
         * <p>
         * Please note that the provided INSERT statement <b>must</b> use a fully-qualified
         * table name, one that include the keyspace name. Morewover, said statement must use
         * bind variables since it is those bind variables that will be bound to values by the
         * resulting writer.
         * <p>
         * This is a mandatory option, and this needs to be called after foTable().
         *
         * @param insertStatement an insertion statement that defines the order
         * of column values to use.
         * @return this builder.
         *
         * @throws IllegalArgumentException if {@code insertStatement} is not a valid insertion
         * statement, does not have a fully-qualified table name or have no bind variables.
         */
        public Builder using(String insertStatement)
        {
            if (schema == null)
                throw new IllegalStateException("You need to define the schema by calling forTable() prior to this call.");

            Pair<UpdateStatement, List<ColumnSpecification>> p = getStatement(insertStatement, UpdateStatement.class, "INSERT");
            this.insert = p.left;
            this.boundNames = p.right;
            if (this.insert.hasConditions())
                throw new IllegalArgumentException("Conditional statements are not supported");
            if (this.insert.isCounter())
                throw new IllegalArgumentException("Counter update statements are not supported");
            if (this.boundNames.isEmpty())
                throw new IllegalArgumentException("Provided insert statement has no bind variables");
            return this;
        }

        /**
         * The size of the buffer to use.
         * <p>
         * This defines how much data will be buffered before being written as
         * a new SSTable. This correspond roughly to the data size that will have the created
         * sstable.
         * <p>
         * The default is 128MB, which should be reasonable for a 1GB heap. If you experience
         * OOM while using the writer, you should lower this value.
         *
         * @param size the size to use in MB.
         * @return this builder.
         */
        public Builder withBufferSizeInMB(int size)
        {
            this.bufferSizeInMB = size;
            return this;
        }

        /**
         * Creates a CQLSSTableWriter that expects sorted inputs.
         * <p>
         * If this option is used, the resulting writer will expect rows to be
         * added in SSTable sorted order (and an exception will be thrown if that
         * is not the case during insertion). The SSTable sorted order means that
         * rows are added such that their partition key respect the partitioner
         * order.
         * <p>
         * You should thus only use this option is you know that you can provide
         * the rows in order, which is rarely the case. If you can provide the
         * rows in order however, using this sorted might be more efficient.
         * <p>
         * Note that if used, some option like withBufferSizeInMB will be ignored.
         *
         * @return this builder.
         */
        public Builder sorted()
        {
            this.sorted = true;
            return this;
        }

        private static CFMetaData getTableMetadata(String schema)
        {
            CFStatement parsed = (CFStatement)QueryProcessor.parseStatement(schema);
            // tables with UDTs are currently not supported by CQLSSTableWrite, so we just use Types.none(), for now
            // see CASSANDRA-10624 for more details
            CreateTableStatement statement = (CreateTableStatement) ((CreateTableStatement.RawStatement) parsed).prepare(Types.none()).statement;
            statement.validate(ClientState.forInternalCalls());
            return statement.getCFMetaData();
        }

        private static <T extends CQLStatement> Pair<T, List<ColumnSpecification>> getStatement(String query, Class<T> klass, String type)
        {
            try
            {
                ClientState state = ClientState.forInternalCalls();
                ParsedStatement.Prepared prepared = QueryProcessor.getStatement(query, state);
                CQLStatement stmt = prepared.statement;
                stmt.validate(state);

                if (!stmt.getClass().equals(klass))
                    throw new IllegalArgumentException("Invalid query, must be a " + type + " statement");

                return Pair.create(klass.cast(stmt), prepared.boundNames);
            }
            catch (RequestValidationException e)
            {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        @SuppressWarnings("resource")
        public CQLSSTableWriter build()
        {
            if (directory == null)
                throw new IllegalStateException("No ouptut directory specified, you should provide a directory with inDirectory()");
            if (schema == null)
                throw new IllegalStateException("Missing schema, you should provide the schema for the SSTable to create with forTable()");
            if (insert == null)
                throw new IllegalStateException("No insert statement specified, you should provide an insert statement through using()");

            AbstractSSTableSimpleWriter writer = sorted
                                               ? new SSTableSimpleWriter(directory, schema, insert.updatedColumns())
                                               : new SSTableSimpleUnsortedWriter(directory, schema, insert.updatedColumns(), bufferSizeInMB);

            if (formatType != null)
                writer.setSSTableFormatType(formatType);

            return new CQLSSTableWriter(writer, insert, boundNames);
        }
    }
}
