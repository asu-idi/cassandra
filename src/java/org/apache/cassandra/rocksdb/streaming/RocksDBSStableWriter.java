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

package org.apache.cassandra.rocksdb.streaming;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.rows.Row;
import org.apache.cassandra.db.rows.Unfiltered;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.rocksdb.encoding.value.RowValueEncoder;
import org.apache.cassandra.utils.FBUtilities;
import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.BloomFilter;
import org.rocksdb.EnvOptions;
import org.rocksdb.Options;
import org.rocksdb.RocksDBException;
import org.rocksdb.SstFileWriter;

import static org.apache.cassandra.rocksdb.RocksDBUtils.getTokenLength;

public class RocksDBSStableWriter implements RocksDBDataWriter, AutoCloseable
{

    private static final Logger LOGGER = LoggerFactory.getLogger(RocksDBSStableWriter.class);
    private final UUID cfId;
    private final int shardId;
    private final EnvOptions envOptions;
    private final Options options;
    private File sstable = null;
    private SstFileWriter sstableWriter = null;
    private long currentSstableSize;
    private volatile long incomingBytes;
    private volatile int sstableIngested;
    private byte[] lastWrittenKeyAfterIngestion = null;

    public RocksDBSStableWriter(UUID cfId, int shardId)
    {
        this.cfId = cfId;
        this.currentSstableSize = 0;
        this.incomingBytes = 0;
        this.sstableIngested = 0;
        this.envOptions = new EnvOptions();
        this.options = new Options();
        this.options.setCompressionType(RocksDBConfigs.COMPRESSION_TYPE);
        this.options.setBottommostCompressionType(RocksDBConfigs.BOTTOMMOST_COMPRESSION);
        final BlockBasedTableConfig tableOptions = new BlockBasedTableConfig();
        tableOptions.setFilter(new BloomFilter(10, false));
        tableOptions.setWholeKeyFiltering(!RocksDBConfigs.DATA_DISABLE_WHOLE_KEY_FILTERING);
        this.options.setTableFormatConfig(tableOptions);
        if (RocksDBConfigs.DATA_ENABLE_PARTITION_TOKEN_KEY_FILTERING)
        {
            this.options.useFixedLengthPrefixExtractor(getTokenLength(cfId));
        }
        this.shardId = shardId;
        RocksDBThroughputManager.getInstance().registerIncomingStreamWriter(this);
    }

    private synchronized void createSstable() throws IOException, RocksDBException
    {
        sstable = File.createTempFile(cfId.toString() + "_" + shardId + "_", ".sst", RocksDBConfigs.STREAMING_TMPFILE_PATH);
        sstable.deleteOnExit();
        sstableWriter = new SstFileWriter(envOptions, options);
        sstableWriter.open(sstable.getAbsolutePath());
        LOGGER.info("Created " + sstable + " for receiving streamed data.");
    }

    private synchronized void IngestSstable() throws RocksDBException
    {

        LOGGER.info("Flushing " + sstable + ", estimated size: " + currentSstableSize + ", flushing threshold: " + RocksDBConfigs.SSTABLE_INGEST_THRESHOLD);
        try
        {
            if (sstableWriter != null)
                sstableWriter.finish();
            if (sstable != null && sstable.exists())
            {
                sstableIngested += 1;
                RocksDBStreamUtils.ingestRocksSstable(cfId, shardId, sstable.getAbsolutePath());
                lastWrittenKeyAfterIngestion = null;
            }
        }
        finally
        {
            if (sstableWriter != null)
                sstableWriter.close();
            sstableWriter = null;
            if (sstable != null)
            {
                sstable.delete();
                sstable = null;
            }
            currentSstableSize = 0;
        }
    }

    public synchronized void write(byte[] key, byte[] value) throws IOException, RocksDBException
    {
        // While streaming from Cassandra storage engine, the data might be out of order
        // whereas Rocksdb sstable writer can only support ordered pairs in one sstable.
        if (lastWrittenKeyAfterIngestion != null && FBUtilities.compareUnsigned(lastWrittenKeyAfterIngestion, key) >= 0)
            IngestSstable();

        if (currentSstableSize >= RocksDBConfigs.SSTABLE_INGEST_THRESHOLD)
            IngestSstable();

        if (sstableWriter == null)
            createSstable();
        sstableWriter.merge(key, value);

        lastWrittenKeyAfterIngestion = key;
        currentSstableSize += key.length + value.length;
        incomingBytes += key.length + value.length + RocksDBStreamUtils.MORE.length + Integer.BYTES * 2;
    }

    public synchronized void writePartition(UnfilteredRowIterator iterator) throws IOException, RocksDBException
    {
        DecoratedKey key = iterator.partitionKey();

        if (key.getKey().remaining() > FBUtilities.MAX_UNSIGNED_SHORT)
        {
            LOGGER.error("Key size {} exceeds maximum of {}, skipping row", key.getKey().remaining(), FBUtilities.MAX_UNSIGNED_SHORT);
            return;
        }

        if (iterator.isEmpty())
            return;

        DecoratedKey partitionKey = iterator.partitionKey();
        CFMetaData metaData = iterator.metadata();
        if(!iterator.partitionLevelDeletion().isLive())
        {
            applyPartitionDeleteDirectly(iterator);
        }
        while (iterator.hasNext())
        {
            Unfiltered unfiltered = iterator.next();
            if (unfiltered.kind() == Unfiltered.Kind.RANGE_TOMBSTONE_MARKER)
            {
                throw new UnsupportedOperationException("Range Tombstone is not supported during streaming, halt the streaming.");
            }
            Row row = (Row) unfiltered;
            write(RowKeyEncoder.encode(partitionKey, row.clustering(), metaData), RowValueEncoder.encode(metaData, row));
        }
    }

    private void applyPartitionDeleteDirectly(UnfilteredRowIterator iterator)
    {
        ColumnFamilyStore cfs = RocksDBStreamUtils.getColumnFamilyStore(cfId);
        RocksDBEngine engine = (RocksDBEngine) cfs.engine;
        engine.applyPartitionLevelDeletionToRocksdb(cfs, iterator.partitionKey(), iterator.partitionLevelDeletion());
    }

    public synchronized void close() throws IOException
    {
        try
        {
            IngestSstable();
        }
        catch (RocksDBException e)
        {
            throw new IOException("rocksdb failed", e);
        }
        finally
        {
            options.close();
            envOptions.close();
        }
    }

    public synchronized Throwable abort(Throwable e)
    {
        try
        {
            close();
        }
        catch (IOException re)
        {
        }
        return e;
    }

    public long getIncomingBytes()
    {
        return incomingBytes;
    }

    public int getSstableIngested()
    {
        return sstableIngested;
    }
}
