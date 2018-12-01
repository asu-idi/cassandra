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

package org.apache.cassandra.rocksdb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.concurrent.JMXEnabledThreadPoolExecutor;
import org.apache.cassandra.concurrent.NamedThreadFactory;
import org.apache.cassandra.concurrent.StageManager;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.DeletionTime;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.io.util.BufferedDataOutputStreamPlus;
import org.apache.cassandra.io.util.DataInputPlus;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.metrics.RocksDBTableMetrics;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;
import org.apache.cassandra.rocksdb.streaming.RocksDBMessageHeader;
import org.apache.cassandra.rocksdb.streaming.RocksDBStreamReader;
import org.apache.cassandra.rocksdb.streaming.RocksDBStreamWriter;
import org.apache.cassandra.rocksdb.tools.SanityCheckUtils;
import org.apache.cassandra.rocksdb.tools.StreamingConsistencyCheckUtils;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.Hex;
import org.apache.cassandra.utils.Pair;
import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.BloomFilter;
import org.rocksdb.CassandraCompactionFilter;
import org.rocksdb.CassandraValueMergeOperator;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.CompactionPriority;
import org.rocksdb.DBOptions;
import org.rocksdb.Env;
import org.rocksdb.FlushOptions;
import org.rocksdb.IndexType;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDBException;
import org.rocksdb.Statistics;
import org.rocksdb.StatsLevel;
import org.rocksdb.Transaction;
import org.rocksdb.WriteOptions;

import static org.apache.cassandra.rocksdb.RocksDBConfigs.NUM_SHARD;
import static org.apache.cassandra.rocksdb.RocksDBConfigs.ROCKSDB_DIR;

/**
 * A wrapper around RocksDB instance.
 */
public class RocksDBCF implements RocksDBCFMBean
{
    private static final Logger logger = LoggerFactory.getLogger(RocksDBCF.class);
    private static final int SCHEMA_VERSION = 0;
    private final String dataDir;
    private final UUID cfID;
    private final ColumnFamilyStore cfs;
    private final IPartitioner partitioner;
    private final RocksDBEngine engine;
    private final RocksDBTableMetrics rocksMetrics;
    private final String mbeanName;

    // Set `readOptions` to speed up read, with the cost of read the stale(range deleted) keys
    // until compaction happens. However in our case, range deletion is only used to remove ranges
    // no longer owned by this node. In such case, stale keys would never be quried.
    private final ReadOptions readOptions = new ReadOptions().setIgnoreRangeDeletions(true);
    private final WriteOptions writeOptions = new WriteOptions().setDisableWAL(RocksDBConfigs.DISABLE_WRITE_TO_COMMITLOG);

    private final List<RocksDBInstanceHandle> rocksDBHandles;
    private final Statistics stats;
    private boolean closed = false; // indicate whether close() function is called or not.
    private final ReentrantReadWriteLock lockForClosedFlag = new ReentrantReadWriteLock(true); // protect 'closed' field
    private static final ExecutorService DB_OPEN_EXECUTOR = new JMXEnabledThreadPoolExecutor(RocksDBConfigs.OPEN_CONCURRENCY,
                                                                                             StageManager.KEEPALIVE,
                                                                                             TimeUnit.SECONDS,
                                                                                             new LinkedBlockingQueue<Runnable>(),
                                                                                             new NamedThreadFactory("RocksDBOpen"),
                                                                                             "internal");

    public RocksDBCF(ColumnFamilyStore cfs) throws RocksDBException
    {
        this.cfs = cfs;
        cfID = cfs.metadata.cfId;
        partitioner = cfs.getPartitioner();
        engine = (RocksDBEngine) cfs.engine;

        dataDir = Paths.get(ROCKSDB_DIR, "s" + SCHEMA_VERSION, cfs.keyspace.getName(), cfs.name).toString();
        FileUtils.createDirectory(ROCKSDB_DIR);
        FileUtils.createDirectory(dataDir);

        assert NUM_SHARD > 0;

        stats = new Statistics();
        stats.setStatsLevel(StatsLevel.EXCEPT_DETAILED_TIMERS);

        final BlockBasedTableConfig tableOptions = new BlockBasedTableConfig();
        tableOptions.setFilter(new BloomFilter(10, false));
        tableOptions.setBlockCache(engine.cache);
        tableOptions.setCacheIndexAndFilterBlocks(RocksDBConfigs.CACHE_INDEX_AND_FILTER_BLOCKS);
        tableOptions.setPinL0FilterAndIndexBlocksInCache(RocksDBConfigs.PIN_L0_FILTER_AND_INDEX_BLOCKS_IN_CACHE);
        tableOptions.setIndexType(getTableIndexType(RocksDBConfigs.TABLE_INDEX_TYPE));

        final BlockBasedTableConfig metaTableOption = new BlockBasedTableConfig();
        metaTableOption.setFilter(new BloomFilter(10, false));
        metaTableOption.setBlockCache(engine.metaCache);
        metaTableOption.setIndexType(getTableIndexType(RocksDBConfigs.TABLE_INDEX_TYPE));

        rocksDBHandles = new ArrayList<>(NUM_SHARD);

        openAllDBShards(dataDir, tableOptions, metaTableOption);

        rocksMetrics = new RocksDBTableMetrics(cfs, stats);

        // Register the mbean.
        mbeanName = getMbeanName(cfs.keyspace.getName(), cfs.getTableName());
        try
        {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.registerMBean(this, new ObjectName(mbeanName));
        }
        catch (Exception e)
        {
            throw Throwables.propagate(e);
        }
    }

    private void openAllDBShards(String rocksDBTableDir, BlockBasedTableConfig tableOptions, BlockBasedTableConfig metaTableOptions)
    {
        ArrayList<Future<RocksDBInstanceHandle>> tasks = new ArrayList<>(NUM_SHARD);

        for (int i = 0; i < NUM_SHARD; i++)
        {
            String shardedDir = NUM_SHARD == 1 ? rocksDBTableDir :
                                Paths.get(rocksDBTableDir, String.valueOf(i)).toString();
            tasks.add(DB_OPEN_EXECUTOR.submit(() -> new RocksDBInstanceHandle(cfs, shardedDir, tableOptions, metaTableOptions, stats)));
        }

        for (int i = 0; i < NUM_SHARD; i++)
        {
            try
            {
                rocksDBHandles.add(tasks.get(i).get());
            }
            catch (ExecutionException e)
            {
                throw new RuntimeException(e.getMessage() + ", Failed opening shard with path " +
                                           Paths.get(rocksDBTableDir, String.valueOf(i)).toString(),
                                           e);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e.getMessage() + ", Failed opening shard with path " +
                                           Paths.get(rocksDBTableDir, String.valueOf(i)).toString(),
                                           e);
            }
        }
    }

    private String getRocksDBDataDirFromShard(Integer shardId)
    {
        return NUM_SHARD == 1 ? this.dataDir : Paths.get(this.dataDir, String.valueOf(shardId)).toString();
    }

    private int shardIDForToken(Token token)
    {
        return Math.abs(token.hashCode() % RocksDBConfigs.NUM_SHARD);
    }

    public static String getMbeanName(String keyspace, String table)
    {
        return String.format("org.apache.cassandra.rocksdbcf:keyspace=%s,table=%s", keyspace, table);
    }


    public RocksDBTableMetrics getRocksMetrics()
    {
        return rocksMetrics;
    }

    public byte[] get(DecoratedKey partitionKey, byte[] key) throws RocksDBException
    {
        return get(RocksCFName.DEFAULT, partitionKey, key);
    }

    public byte[] get(RocksCFName rocksCFName, DecoratedKey partitionKey, byte[] key) throws RocksDBException
    {
        RocksDBInstanceHandle dbhandle = getDBHandleForPartitionKey(partitionKey);
        return dbhandle.get(rocksCFName, readOptions, key);
    }

    private RocksDBInstanceHandle getDBHandleForPartitionKey(DecoratedKey partitionKey)
    {
        return rocksDBHandles.get(getShardIdForKey(partitionKey));
    }

    public IndexType getTableIndexType(String indexType)
    {
        try
        {
            return IndexType.valueOf(indexType);
        }
        catch (Throwable e)
        {
            logger.warn("Failed to set table index type " + indexType, e);
            logger.warn("Setting table index type to default: " + IndexType.kBinarySearch.toString());
            return IndexType.kBinarySearch;
        }
    }

    public RocksDBIteratorAdapter newIterator(DecoratedKey partitionKey)
    {
        return newIterator(RocksCFName.DEFAULT, partitionKey, readOptions);
    }

    public RocksDBIteratorAdapter newIterator(RocksCFName rocksCFName, DecoratedKey partitionKey)
    {
        return newIterator(rocksCFName, partitionKey, readOptions);
    }

    public RocksDBIteratorAdapter newIterator(RocksCFName rocksCFName, DecoratedKey partitionKey, ReadOptions options)
    {
        RocksDBInstanceHandle dbhandle = getDBHandleForPartitionKey(partitionKey);
        return dbhandle.newIterator(rocksCFName, options, rocksMetrics);
    }

    public RocksDBIteratorAdapter newShardIterator(int shardId, ReadOptions options)
    {
        return newShardIterator(shardId, options, RocksCFName.DEFAULT);
    }

    public RocksDBIteratorAdapter newShardIterator(int shardId, ReadOptions options, RocksCFName rocksCFName)
    {
        RocksDBInstanceHandle dbhandle = rocksDBHandles.get(shardId);
        return dbhandle.newShardIterator(rocksCFName, options, rocksMetrics);
    }

    public void merge(DecoratedKey partitionKey, byte[] key, byte[] value) throws RocksDBException
    {
        merge(RocksCFName.DEFAULT, partitionKey, key, value);
    }

    public void merge(RocksCFName rocksCFName, DecoratedKey partitionKey, byte[] key, byte[] value) throws RocksDBException
    {
        RocksDBInstanceHandle dbhandle = getDBHandleForPartitionKey(partitionKey);
        dbhandle.merge(rocksCFName, writeOptions, key, value);
    }

    public void merge(RocksCFName rocksCFName, DecoratedKey partitionKey, byte[] key, byte[] value, Transaction transaction) throws RocksDBException
    {
        RocksDBInstanceHandle dbhandle = getDBHandleForPartitionKey(partitionKey);
        dbhandle.merge(rocksCFName, key, value, transaction);
    }

    public int getShardIdForKey(DecoratedKey partitionKey)
    {
        return shardIDForToken(partitionKey.getToken());
    }

    public void deleteRange(byte[] start, byte[] end) throws RocksDBException
    {
        for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
        {
            dbhandle.deleteRange(start, end);
        }
    }

    public void compactRange() throws RocksDBException
    {
        for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
        {
            dbhandle.compactRange();
        }
    }

    public void forceFlush() throws RocksDBException
    {
        logger.info("Flushing rocksdb table: " + cfs.name);
        for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
        {
            dbhandle.forceFlush();
        }
    }

    public List<String> getProperty(String property) throws RocksDBException
    {
        return this.getProperty(property, RocksCFName.DEFAULT);
    }

    public List<String> getProperty(String property, RocksCFName cf) throws RocksDBException
    {
        // synchronize with close() function which modifies 'closed' field
        try
        {
            lockForClosedFlag.readLock().lock();
            // if close() function is called already, calling rocksDB.getProperty() will crash the process.
            // So return empty ArrayList instead.
            if (closed)
                return new ArrayList<>();
            List<String> properties = new ArrayList<>(rocksDBHandles.size());
            for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
            {
                properties.add(dbhandle.getProperty(cf, property));
            }
            return properties;
        }
        finally
        {
            lockForClosedFlag.readLock().unlock();
        }
    }

    public void truncate() throws RocksDBException
    {
        logger.info("Truncating rocksdb table: " + cfs.name);
        byte[] startRange = RowKeyEncoder.encodeToken(RocksDBUtils.getMinToken(partitioner));
        byte[] endRange = RowKeyEncoder.encodeToken(RocksDBUtils.getMaxToken(partitioner));

        for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
        {
            dbhandle.truncate(startRange, endRange);
        }
    }

    protected void close()
    {
        logger.info("Closing rocksdb table: " + cfs.name);
        try
        {
            lockForClosedFlag.writeLock().lock();
            closed = true;
            for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
                dbhandle.close();
            // remove the rocksdb instance, since it's not usable
            engine.rocksDBFamily.remove(new Pair<>(cfID, cfs.name));
        }
        finally
        {
            lockForClosedFlag.writeLock().unlock();
        }
    }

    public String dumpPrefix(DecoratedKey partitionKey, byte[] rocksKeyPrefix, int limit)
    {
        StringBuilder sb = new StringBuilder();
        try (RocksDBIteratorAdapter rocksIterator = newIterator(partitionKey))
        {
            rocksIterator.seek(rocksKeyPrefix);
            while (limit > 0 && rocksIterator.isValid())
            {
                byte[] key = rocksIterator.key();
                if (!Bytes.startsWith(key, rocksKeyPrefix))
                {
                    break;
                }
                sb.append("0x")
                  .append(Hex.bytesToHex(key))
                  .append('\t');

                byte[] value = rocksIterator.value();
                if (value == null)
                {
                    sb.append("null\n");
                }
                else
                {
                    sb.append("0x")
                      .append(Hex.bytesToHex(value))
                      .append('\n');
                }
                limit--;
                rocksIterator.next();
            }
        }
        return sb.toString();
    }

    public UUID getCfID()
    {
        return cfID;
    }

    @Override
    public String rocksDBSanityCheck(boolean randomStartToken, long limit, boolean verbose)
    {
        return SanityCheckUtils.checkSanity(cfs, randomStartToken, limit, verbose).toString();
    }

    @Override
    public String exportRocksDBStream(String outputFile, int limit) throws IOException, RocksDBException
    {
        Collection<Range<Token>> ranges = Arrays.asList(new Range<Token>(RocksDBUtils.getMinToken(cfs.getPartitioner()),
                                                                         RocksDBUtils.getMaxToken(cfs.getPartitioner())));
        RocksDBStreamWriter writer = new RocksDBStreamWriter(RocksDBEngine.getRocksDBCF(cfs.metadata.cfId), ranges);
        BufferedDataOutputStreamPlus out = new BufferedDataOutputStreamPlus(new FileOutputStream(outputFile));
        long startTimeMs = System.currentTimeMillis();
        writer.write(out, limit);
        out.close();
        long timeElapsedMs = Math.max(1, System.currentTimeMillis() - startTimeMs); // Avoid divde by 0 Exception.
        double streamedMB = writer.getOutgoingBytes() / (1024.0 * 1024 /* MB in bytes */);
        double throughputMBps = streamedMB / (timeElapsedMs / 1000.0f /* Ms in seconds */);
        return "Data Streamed: " + streamedMB + "MB, time elapsed: " + timeElapsedMs + " MS, throughput: " + throughputMBps + " MB/S.";
    }

    @Override
    public String ingestRocksDBStream(String inputFile) throws IOException, RocksDBException
    {
        RocksDBStreamReader reader = new RocksDBStreamReader(new RocksDBMessageHeader(cfs.metadata.cfId, 0),
                                                             new StreamSession(FBUtilities.getBroadcastAddress(), FBUtilities.getBroadcastAddress(), null, 0, false, false));
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(inputFile));
        long startTimeMs = System.currentTimeMillis();
        reader.read(new DataInputPlus.DataInputStreamPlus(stream));
        long timeElapsedMs = Math.max(1, System.currentTimeMillis() - startTimeMs); // Avoid divde by 0 Exception.
        double streamedMB = reader.getTotalIncomingBytes() / (1024.0 * 1024 /* MB in bytes */);
        double throughputMBps = streamedMB / (timeElapsedMs / 1000.0f /* Ms in seconds */);
        return "Data Streamed: " + streamedMB + "MB, time elapsed: " + timeElapsedMs + " MS, throughput: " + throughputMBps + " MB/S.";
    }

    @Override
    public List<String> getRocksDBProperty(String property, RocksCFName cf)
    {
        try
        {
            return getProperty(property, cf);
        }
        catch (Throwable e)
        {
            logger.warn("Failed to get RocksDB property " + property, e);
            return Arrays.asList("Failed to get property:" + property + ", reason:" + e.toString());
        }
    }

    @Override
    public String dumpPartition(String partitionKey, int limit)
    {
        try
        {
            return engine.dumpPartition(cfs, partitionKey, limit);
        }
        catch (Throwable e)
        {
            logger.warn("Failed to dump partition " + partitionKey, e);
            return "Failed to dump:" + partitionKey + ", reason:" + e.toString();
        }
    }

    @Override
    public String dumpPartitionMetaData(String partitionKey)
    {
        try
        {
            return engine.dumpPartitionMetaData(cfs, partitionKey);
        }
        catch (Throwable e)
        {
            logger.warn("Failed to dump partition meta data " + partitionKey, e);
            return "Failed to dump meta data:" + partitionKey + ", reason:" + e.toString();
        }
    }

    @Override
    public String streamingConsistencyCheck(int expectedNumKeys)
    {
        return StreamingConsistencyCheckUtils.checkAndGenerateReport(cfs, expectedNumKeys);
    }

    public void ingestRocksSstable(int shardId, String sstFile, long ingestionWaitMs, RocksCFName rocksCFName) throws RocksDBException
    {
        RocksDBInstanceHandle dbhandle = rocksDBHandles.get(shardId);

        // There might be multiple streaming sessions (threads) for the same sstable/db at the same time.
        // Adding lock to the db to prevent multiple sstables are ingested at same time and trigger
        // write stalls.
        synchronized (dbhandle)
        {
            long startTime = System.currentTimeMillis();
            // Wait until compaction catch up by examing the number of l0 sstables.
            while (true)
            {
                int numOfLevel0Sstables = RocksDBProperty.getNumberOfSstablesByLevel(this, 0);
                if (numOfLevel0Sstables <= RocksDBConfigs.LEVEL0_STOP_WRITES_TRIGGER)
                    break;
                try
                {
                    logger.debug("Number of level0 sstables " + numOfLevel0Sstables + " exceeds the threshold " + RocksDBConfigs.LEVEL0_STOP_WRITES_TRIGGER
                                 + ", sleep for " + ingestionWaitMs + "ms.");
                    Thread.sleep(ingestionWaitMs);
                }
                catch (InterruptedException e)
                {
                    logger.warn("Ingestion wait interrupted, procceding.");
                }
            }
            rocksMetrics.rocksDBIngestWaitTimeHistogram.update(System.currentTimeMillis() - startTime);
            logger.info("Time spent waiting for compaction:" + (System.currentTimeMillis() - startTime));

            long ingestStartTime = System.currentTimeMillis();
            dbhandle.ingestRocksSstable(rocksCFName, sstFile);

            logger.info("Time spent on ingestion:" + (System.currentTimeMillis() - ingestStartTime));
            rocksMetrics.rocksDBIngestTimeHistogram.update(System.currentTimeMillis() - ingestStartTime);
        }

    }

    public Transaction beginTransaction(DecoratedKey partitionKey)
    {
        RocksDBInstanceHandle dbhandle = getDBHandleForPartitionKey(partitionKey);
        return dbhandle.beginTransaction(writeOptions);
    }

    @Override
    public void createSnapshot(String tag) throws IOException
    {
        FileUtils.createDirectory(ROCKSDB_DIR);
        for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
        {
            dbhandle.createSnapshot(tag);
        }
    }

    @Override
    public void clearSnapshot(String tag)
    {
        for (RocksDBInstanceHandle dbhandle : rocksDBHandles)
        {
            dbhandle.clearSnapshot(tag);
        }
    }

    public void deletePartition(DecoratedKey partitionKey, DeletionTime partitionLevelDeletion) throws RocksDBException
    {
        RocksDBInstanceHandle dbhandle = getDBHandleForPartitionKey(partitionKey);
        byte[] partitionKeyWithToken = RowKeyEncoder.encode(partitionKey, cfs.metadata);
        dbhandle.deleteParition(partitionKeyWithToken, partitionLevelDeletion.localDeletionTime(), partitionLevelDeletion.markedForDeleteAt());
    }
}

