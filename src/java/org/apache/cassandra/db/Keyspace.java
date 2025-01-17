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
package org.apache.cassandra.db;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.concurrent.Stage;
import org.apache.cassandra.concurrent.StageManager;
import org.apache.cassandra.config.*;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.db.commitlog.ReplayPosition;
import org.apache.cassandra.db.compaction.CompactionManager;
import org.apache.cassandra.db.lifecycle.SSTableSet;
import org.apache.cassandra.db.partitions.PartitionUpdate;
import org.apache.cassandra.db.view.ViewManager;
import org.apache.cassandra.exceptions.WriteTimeoutException;
import org.apache.cassandra.index.Index;
import org.apache.cassandra.index.SecondaryIndexManager;
import org.apache.cassandra.index.transactions.UpdateTransaction;
import org.apache.cassandra.io.sstable.format.SSTableReader;
import org.apache.cassandra.locator.AbstractReplicationStrategy;
import org.apache.cassandra.metrics.KeyspaceMetrics;
import org.apache.cassandra.metrics.TableMetrics;
import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.engine.StorageEngine;
import org.apache.cassandra.schema.KeyspaceMetadata;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.tracing.Tracing;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.JVMStabilityInspector;
import org.apache.cassandra.utils.concurrent.OpOrder;
import org.rocksdb.MemoryUsageType;
import org.rocksdb.MemoryUtil;

/**
 * It represents a Keyspace.
 */
public class Keyspace
{
    private static final Logger logger = LoggerFactory.getLogger(Keyspace.class);

    private static final String TEST_FAIL_WRITES_KS = System.getProperty("cassandra.test.fail_writes_ks", "");
    private static final boolean TEST_FAIL_WRITES = !TEST_FAIL_WRITES_KS.isEmpty();
    private static int TEST_FAIL_MV_LOCKS_COUNT = Integer.getInteger("cassandra.test.fail_mv_locks_count", 0);

    public final KeyspaceMetrics metric;

    public final StorageEngine engine;

    public boolean isRocksDBBacked()
    {
        return getName().equals(RocksDBConfigs.ROCKSDB_KEYSPACE);
    }

    public Map<String, Long> rocksDBMemUsage()
    {
        assert isRocksDBBacked();
        assert engine instanceof RocksDBEngine;
        return ((RocksDBEngine)engine).memoryUsage();
    }

    // It is possible to call Keyspace.open without a running daemon, so it makes sense to ensure
    // proper directories here as well as in CassandraDaemon.
    static
    {
        if (!Config.isClientMode())
            DatabaseDescriptor.createAllDirectories();
    }

    private volatile KeyspaceMetadata metadata;

    //OpOrder is defined globally since we need to order writes across
    //Keyspaces in the case of Views (batchlog of view mutations)
    public static final OpOrder writeOrder = new OpOrder();

    /* ColumnFamilyStore per column family */
    private final ConcurrentMap<UUID, ColumnFamilyStore> columnFamilyStores = new ConcurrentHashMap<>();
    private volatile AbstractReplicationStrategy replicationStrategy;
    public final ViewManager viewManager;

    public static final Function<String,Keyspace> keyspaceTransformer = new Function<String, Keyspace>()
    {
        public Keyspace apply(String keyspaceName)
        {
            return Keyspace.open(keyspaceName);
        }
    };

    private static volatile boolean initialized = false;

    public static void setInitialized()
    {
        initialized = true;
    }

    public static Keyspace open(String keyspaceName)
    {
        assert initialized || Schema.isSystemKeyspace(keyspaceName);
        return open(keyspaceName, Schema.instance, true);
    }

    // to only be used by org.apache.cassandra.tools.Standalone* classes
    public static Keyspace openWithoutSSTables(String keyspaceName)
    {
        return open(keyspaceName, Schema.instance, false);
    }

    private static Keyspace open(String keyspaceName, Schema schema, boolean loadSSTables)
    {
        Keyspace keyspaceInstance = schema.getKeyspaceInstance(keyspaceName);

        if (keyspaceInstance == null)
        {
            // instantiate the Keyspace.  we could use putIfAbsent but it's important to making sure it is only done once
            // per keyspace, so we synchronize and re-check before doing it.
            synchronized (Keyspace.class)
            {
                keyspaceInstance = schema.getKeyspaceInstance(keyspaceName);
                if (keyspaceInstance == null)
                {
                    // open and store the keyspace
                    keyspaceInstance = new Keyspace(keyspaceName, loadSSTables);
                    schema.storeKeyspaceInstance(keyspaceInstance);
                }
            }
        }
        return keyspaceInstance;
    }

    public static Keyspace clear(String keyspaceName)
    {
        return clear(keyspaceName, Schema.instance);
    }

    public static Keyspace clear(String keyspaceName, Schema schema)
    {
        synchronized (Keyspace.class)
        {
            Keyspace t = schema.removeKeyspaceInstance(keyspaceName);
            if (t != null)
            {
                for (ColumnFamilyStore cfs : t.getColumnFamilyStores())
                    t.unloadCf(cfs);
                t.metric.release();
            }
            return t;
        }
    }

    public static ColumnFamilyStore openAndGetStore(CFMetaData cfm)
    {
        return open(cfm.ksName).getColumnFamilyStore(cfm.cfId);
    }

    /**
     * Removes every SSTable in the directory from the appropriate Tracker's view.
     * @param directory the unreadable directory, possibly with SSTables in it, but not necessarily.
     */
    public static void removeUnreadableSSTables(File directory)
    {
        for (Keyspace keyspace : Keyspace.all())
        {
            for (ColumnFamilyStore baseCfs : keyspace.getColumnFamilyStores())
            {
                for (ColumnFamilyStore cfs : baseCfs.concatWithIndexes())
                    cfs.maybeRemoveUnreadableSSTables(directory);
            }
        }
    }

    public void setMetadata(KeyspaceMetadata metadata)
    {
        this.metadata = metadata;
        createReplicationStrategy(metadata);
    }

    public KeyspaceMetadata getMetadata()
    {
        return metadata;
    }

    public Collection<ColumnFamilyStore> getColumnFamilyStores()
    {
        return Collections.unmodifiableCollection(columnFamilyStores.values());
    }

    public ColumnFamilyStore getColumnFamilyStore(String cfName)
    {
        UUID id = Schema.instance.getId(getName(), cfName);
        if (id == null)
            throw new IllegalArgumentException(String.format("Unknown keyspace/cf pair (%s.%s)", getName(), cfName));
        return getColumnFamilyStore(id);
    }

    public ColumnFamilyStore getColumnFamilyStore(UUID id)
    {
        ColumnFamilyStore cfs = columnFamilyStores.get(id);
        if (cfs == null)
            throw new IllegalArgumentException("Unknown CF " + id);
        return cfs;
    }

    public boolean hasColumnFamilyStore(UUID id)
    {
        return columnFamilyStores.containsKey(id);
    }

    /**
     * Take a snapshot of the specific column family, or the entire set of column families
     * if columnFamily is null with a given timestamp
     *
     * @param snapshotName     the tag associated with the name of the snapshot.  This value may not be null
     * @param columnFamilyName the column family to snapshot or all on null
     * @throws IOException if the column family doesn't exist
     */
    public void snapshot(String snapshotName, String columnFamilyName) throws IOException
    {
        assert snapshotName != null;
        boolean tookSnapShot = false;
        for (ColumnFamilyStore cfStore : columnFamilyStores.values())
        {
            if (columnFamilyName == null || cfStore.name.equals(columnFamilyName))
            {
                tookSnapShot = true;
                if (engine != null)
                    engine.snapshot(cfStore, snapshotName);
                else
                    cfStore.snapshot(snapshotName);
            }
        }

        if ((columnFamilyName != null) && !tookSnapShot)
            throw new IOException("Failed taking snapshot. Table " + columnFamilyName + " does not exist.");
    }

    /**
     * @param clientSuppliedName may be null.
     * @return the name of the snapshot
     */
    public static String getTimestampedSnapshotName(String clientSuppliedName)
    {
        String snapshotName = Long.toString(System.currentTimeMillis());
        if (clientSuppliedName != null && !clientSuppliedName.equals(""))
        {
            snapshotName = snapshotName + "-" + clientSuppliedName;
        }
        return snapshotName;
    }

    /**
     * Check whether snapshots already exists for a given name.
     *
     * @param snapshotName the user supplied snapshot name
     * @return true if the snapshot exists
     */
    public boolean snapshotExists(String snapshotName)
    {
        assert snapshotName != null;
        for (ColumnFamilyStore cfStore : columnFamilyStores.values())
        {
            if (cfStore.snapshotExists(snapshotName))
                return true;
        }
        return false;
    }

    /**
     * Clear all the snapshots for a given keyspace.
     *
     * @param snapshotName the user supplied snapshot name. It empty or null,
     *                     all the snapshots will be cleaned
     */
    public static void clearSnapshot(String snapshotName, String keyspace)
    {
        List<File> snapshotDirs = Directories.getKSChildDirectories(keyspace, ColumnFamilyStore.getInitialDirectories());
        Directories.clearSnapshot(snapshotName, snapshotDirs);

        Keyspace ks = Keyspace.open(keyspace);
        if (ks.engine != null) {
            for (ColumnFamilyStore cfStore : ks.columnFamilyStores.values())
            {
                try
                {
                    ks.engine.clearSnapshot(cfStore, snapshotName);
                } catch (IOException e) {
                    logger.error("Failed to clear snapshot %s: %s", snapshotName, e);
                }
            }
        }

    }

    /**
     * @return A list of open SSTableReaders
     */
    public List<SSTableReader> getAllSSTables(SSTableSet sstableSet)
    {
        List<SSTableReader> list = new ArrayList<>(columnFamilyStores.size());
        for (ColumnFamilyStore cfStore : columnFamilyStores.values())
            Iterables.addAll(list, cfStore.getSSTables(sstableSet));
        return list;
    }

    private Keyspace(String keyspaceName, boolean loadSSTables)
    {
        metadata = Schema.instance.getKSMetaData(keyspaceName);
        assert metadata != null : "Unknown keyspace " + keyspaceName;
        createReplicationStrategy(metadata);

        if (keyspaceName.equals(RocksDBConfigs.ROCKSDB_KEYSPACE))
            engine = new RocksDBEngine(this);
        else
            engine = null;

        this.metric = new KeyspaceMetrics(this);
        this.viewManager = new ViewManager(this);
        for (CFMetaData cfm : metadata.tablesAndViews())
        {
            logger.trace("Initializing {}.{}", getName(), cfm.cfName);
            initCf(cfm, loadSSTables);
        }
        this.viewManager.reload();
    }

    private Keyspace(KeyspaceMetadata metadata)
    {
        this.metadata = metadata;
        createReplicationStrategy(metadata);
        this.metric = new KeyspaceMetrics(this);
        this.viewManager = new ViewManager(this);
        if (metadata.name.equals(RocksDBConfigs.ROCKSDB_KEYSPACE))
            engine = new RocksDBEngine(this);
        else
            engine = null;
    }

    public static Keyspace mockKS(KeyspaceMetadata metadata)
    {
        return new Keyspace(metadata);
    }

    private void createReplicationStrategy(KeyspaceMetadata ksm)
    {
        replicationStrategy = AbstractReplicationStrategy.createReplicationStrategy(ksm.name,
                                                                                    ksm.params.replication.klass,
                                                                                    StorageService.instance.getTokenMetadata(),
                                                                                    DatabaseDescriptor.getEndpointSnitch(),
                                                                                    ksm.params.replication.options);
    }

    // best invoked on the compaction mananger.
    public void dropCf(UUID cfId)
    {
        assert columnFamilyStores.containsKey(cfId);
        ColumnFamilyStore cfs = columnFamilyStores.remove(cfId);
        if (cfs == null)
            return;

        cfs.getCompactionStrategyManager().shutdown();
        CompactionManager.instance.interruptCompactionForCFs(cfs.concatWithIndexes(), true);
        // wait for any outstanding reads/writes that might affect the CFS
        cfs.keyspace.writeOrder.awaitNewBarrier();
        cfs.readOrdering.awaitNewBarrier();

        unloadCf(cfs);
    }

    // disassociate a cfs from this keyspace instance.
    private void unloadCf(ColumnFamilyStore cfs)
    {
        cfs.forceBlockingFlush();
        cfs.invalidate();
    }

    /**
     * adds a cf to internal structures, ends up creating disk files).
     */
    public void initCf(CFMetaData metadata, boolean loadSSTables)
    {
        ColumnFamilyStore cfs = columnFamilyStores.get(metadata.cfId);

        if (cfs == null)
        {
            // CFS being created for the first time, either on server startup or new CF being added.
            // We don't worry about races here; startup is safe, and adding multiple idential CFs
            // simultaneously is a "don't do that" scenario.
            ColumnFamilyStore oldCfs = columnFamilyStores.putIfAbsent(metadata.cfId, ColumnFamilyStore.createColumnFamilyStore(this, metadata, loadSSTables));
            // CFS mbean instantiation will error out before we hit this, but in case that changes...
            if (oldCfs != null)
                throw new IllegalStateException("added multiple mappings for cf id " + metadata.cfId);
        }
        else
        {
            // re-initializing an existing CF.  This will happen if you cleared the schema
            // on this node and it's getting repopulated from the rest of the cluster.
            assert cfs.name.equals(metadata.cfName);
            cfs.reload();
        }
    }

    public CompletableFuture<?> applyFuture(Mutation mutation, boolean writeCommitLog, boolean updateIndexes)
    {
        return applyInternal(mutation, writeCommitLog, updateIndexes, true, true, new CompletableFuture<>());
    }

    public CompletableFuture<?> applyFuture(Mutation mutation, boolean writeCommitLog, boolean updateIndexes, boolean isDroppable,
                                            boolean isDeferrable)
    {
        return applyInternal(mutation, writeCommitLog, updateIndexes, isDroppable, isDeferrable, new CompletableFuture<>());
    }

    public void apply(Mutation mutation, boolean writeCommitLog, boolean updateIndexes)
    {
        apply(mutation, writeCommitLog, updateIndexes, true);
    }

    public void apply(final Mutation mutation,
                      final boolean writeCommitLog)
    {
        apply(mutation, writeCommitLog, true, true);
    }

    /**
     * If apply is blocking, apply must not be deferred
     * Otherwise there is a race condition where ALL mutation workers are beeing blocked ending
     * in a complete deadlock of the mutation stage. See CASSANDRA-12689.
     *
     * @param mutation       the row to write.  Must not be modified after calling apply, since commitlog append
     *                       may happen concurrently, depending on the CL Executor type.
     * @param writeCommitLog false to disable commitlog append entirely
     * @param updateIndexes  false to disable index updates (used by CollationController "defragmenting")
     * @param isDroppable    true if this should throw WriteTimeoutException if it does not acquire lock within write_request_timeout_in_ms
     * @throws ExecutionException
     */
    public void apply(final Mutation mutation,
                      final boolean writeCommitLog,
                      boolean updateIndexes,
                      boolean isDroppable)
    {
        applyInternal(mutation, writeCommitLog, updateIndexes, isDroppable, false, null);
    }

    /**
     * Compatibility method that keeps <bold>isClReplay</bold> flag.
     * @deprecated Use {@link this#applyFuture(Mutation, boolean, boolean, boolean, boolean)} instead
     */
    @Deprecated
    public CompletableFuture<?> apply(final Mutation mutation,
                                       final boolean writeCommitLog,
                                       boolean updateIndexes,
                                       boolean isClReplay,
                                       boolean isDeferrable,
                                       CompletableFuture<?> future)
    {
        return applyInternal(mutation, writeCommitLog, updateIndexes, !isClReplay, isDeferrable, future != null? future : new CompletableFuture<>());
    }

    /**
     * This method appends a row to the global CommitLog, then updates memtables and indexes.
     *
     * @param mutation       the row to write.  Must not be modified after calling apply, since commitlog append
     *                       may happen concurrently, depending on the CL Executor type.
     * @param writeCommitLog false to disable commitlog append entirely
     * @param updateIndexes  false to disable index updates (used by CollationController "defragmenting")
     * @param isDroppable    true if this should throw WriteTimeoutException if it does not acquire lock within write_request_timeout_in_ms
     * @param isDeferrable   true if caller is not waiting for future to complete, so that future may be deferred
     */
    private CompletableFuture<?> applyInternal(final Mutation mutation,
                                               final boolean writeCommitLog,
                                               boolean updateIndexes,
                                               boolean isDroppable,
                                               boolean isDeferrable,
                                               CompletableFuture<?> future)
    {
        if (TEST_FAIL_WRITES && metadata.name.equals(TEST_FAIL_WRITES_KS))
            throw new RuntimeException("Testing write failures");

        boolean requiresViewUpdate = updateIndexes && viewManager.updatesAffectView(Collections.singleton(mutation), false);

        Lock lock = null;
        if (requiresViewUpdate)
        {
            mutation.viewLockAcquireStart.compareAndSet(0L, System.currentTimeMillis());
            while (true)
            {
                if (TEST_FAIL_MV_LOCKS_COUNT == 0)
                    lock = ViewManager.acquireLockFor(mutation.key().getKey());
                else
                    TEST_FAIL_MV_LOCKS_COUNT--;

                if (lock == null)
                {
                    //throw WTE only if request is droppable
                    if (isDroppable && (System.currentTimeMillis() - mutation.createdAt) > DatabaseDescriptor.getWriteRpcTimeout())
                    {
                        logger.trace("Could not acquire lock for {}", ByteBufferUtil.bytesToHex(mutation.key().getKey()));
                        Tracing.trace("Could not acquire MV lock");
                        if (future != null)
                        {
                            future.completeExceptionally(new WriteTimeoutException(WriteType.VIEW, ConsistencyLevel.LOCAL_ONE, 0, 1));
                            return future;
                        }
                        else
                        {
                            throw new WriteTimeoutException(WriteType.VIEW, ConsistencyLevel.LOCAL_ONE, 0, 1);
                        }
                    }
                    else if (isDeferrable)
                    {
                        //This view update can't happen right now. so rather than keep this thread busy
                        // we will re-apply ourself to the queue and try again later
                        final CompletableFuture<?> mark = future;
                        StageManager.getStage(Stage.MUTATION).execute(() ->
                                applyInternal(mutation, writeCommitLog, true, isDroppable, true, mark)
                        );

                        return future;
                    }
                    else
                    {
                        // Retry lock on same thread, if mutation is not deferrable.
                        // Mutation is not deferrable, if applied from MutationStage and caller is waiting for future to finish
                        // If blocking caller defers future, this may lead to deadlock situation with all MutationStage workers
                        // being blocked by waiting for futures which will never be processed as all workers are blocked
                        try
                        {
                            // Wait a little bit before retrying to lock
                            Thread.sleep(10);
                        }
                        catch (InterruptedException e)
                        {
                            // Just continue
                        }
                        // continue in while loop
                    }
                }
                else
                {
                    long acquireTime = System.currentTimeMillis() - mutation.viewLockAcquireStart.get();
                    // Metrics are only collected for droppable write operations
                    // Bulk non-droppable operations (e.g. commitlog replay, hint delivery) are not measured
                    if (isDroppable)
                    {
                        for (UUID cfid : mutation.getColumnFamilyIds())
                            columnFamilyStores.get(cfid).metric.viewLockAcquireTime.update(acquireTime, TimeUnit.MILLISECONDS);
                    }
                    break;
                }
            }
        }
        int nowInSec = FBUtilities.nowInSeconds();
        try (OpOrder.Group opGroup = writeOrder.start())
        {
            // write the mutation to the commitlog and memtables
            ReplayPosition replayPosition = null;
            if (writeCommitLog)
            {
                Tracing.trace("Appending to commitlog");
                replayPosition = CommitLog.instance.add(mutation);
            }

            for (PartitionUpdate upd : mutation.getPartitionUpdates())
            {
                ColumnFamilyStore cfs = columnFamilyStores.get(upd.metadata().cfId);
                if (cfs == null)
                {
                    logger.error("Attempting to mutate non-existant table {} ({}.{})", upd.metadata().cfId, upd.metadata().ksName, upd.metadata().cfName);
                    continue;
                }

                // Update the storage engine first in the double writing senario:
                // In case that Cassandra storage engine treats the bytebuffers in PartitionUpdate as mutable (such as read several bytes from the bytebuffer
                // without duplicating it) and corrupts the data accordingly.
                if (engine != null)
                {
                    long start = System.nanoTime();

                    UpdateTransaction indexTransaction = updateIndexes
                                                         ? cfs.indexManager.newUpdateTransaction(upd, opGroup, nowInSec)
                                                         : UpdateTransaction.NO_OP;

                    engineApply(cfs, upd, indexTransaction, opGroup, replayPosition, writeCommitLog);

                    cfs.metric.samplers.get(TableMetrics.Sampler.WRITES).addSample(upd.partitionKey().getKey(), upd.partitionKey().hashCode(), 1);
                    cfs.metric.writeLatency.addNano(System.nanoTime() - start);
                }

                if (engine == null || engine.doubleWrite())
                {

                    AtomicLong baseComplete = new AtomicLong(Long.MAX_VALUE);

                    if (requiresViewUpdate)
                    {
                        try
                        {
                            Tracing.trace("Creating materialized view mutations from base table replica");
                            viewManager.forTable(upd.metadata()).pushViewReplicaUpdates(upd, writeCommitLog, baseComplete);
                        }
                        catch (Throwable t)
                        {
                            JVMStabilityInspector.inspectThrowable(t);
                            logger.error(String.format("Unknown exception caught while attempting to update MaterializedView! %s.%s",
                                                       upd.metadata().ksName, upd.metadata().cfName), t);
                            throw t;
                        }
                    }

                    Tracing.trace("Adding to {} memtable", upd.metadata().cfName);
                    UpdateTransaction indexTransaction = updateIndexes
                                                         ? cfs.indexManager.newUpdateTransaction(upd, opGroup, nowInSec)
                                                         : UpdateTransaction.NO_OP;

                    cfs.apply(upd, indexTransaction, opGroup, replayPosition);

                    if (requiresViewUpdate)
                        baseComplete.set(System.currentTimeMillis());
                }

            }

            if (future != null) {
                future.complete(null);
            }
            return future;
        }
        finally
        {
            if (lock != null)
                lock.unlock();
        }
    }

    // TODO: refactor this to clean the code up
    public void engineApply (final ColumnFamilyStore cfs,
                             final PartitionUpdate partitionUpdate,
                             UpdateTransaction indexTransaction,
                             OpOrder.Group opGroup,
                             ReplayPosition replayPosition,
                             final boolean writeCommitLog)
    {
        if (engine != null)
        {
            engine.apply(cfs, partitionUpdate, indexTransaction, writeCommitLog);
        }

        if (engine == null || engine.doubleWrite())
        {
            cfs.apply(partitionUpdate, indexTransaction, opGroup, replayPosition);
        }
    }

    public AbstractReplicationStrategy getReplicationStrategy()
    {
        return replicationStrategy;
    }

    public List<Future<?>> flush()
    {
        List<Future<?>> futures = new ArrayList<>(columnFamilyStores.size());
        for (ColumnFamilyStore cfs : columnFamilyStores.values())
            futures.add(cfs.forceFlush());
        return futures;
    }

    public Iterable<ColumnFamilyStore> getValidColumnFamilies(boolean allowIndexes,
                                                              boolean autoAddIndexes,
                                                              String... cfNames) throws IOException
    {
        Set<ColumnFamilyStore> valid = new HashSet<>();

        if (cfNames.length == 0)
        {
            // all stores are interesting
            for (ColumnFamilyStore cfStore : getColumnFamilyStores())
            {
                valid.add(cfStore);
                if (autoAddIndexes)
                    valid.addAll(getIndexColumnFamilyStores(cfStore));
            }
            return valid;
        }

        // include the specified stores and possibly the stores of any of their indexes
        for (String cfName : cfNames)
        {
            if (SecondaryIndexManager.isIndexColumnFamily(cfName))
            {
                if (!allowIndexes)
                {
                    logger.warn("Operation not allowed on secondary Index table ({})", cfName);
                    continue;
                }
                String baseName = SecondaryIndexManager.getParentCfsName(cfName);
                String indexName = SecondaryIndexManager.getIndexName(cfName);

                ColumnFamilyStore baseCfs = getColumnFamilyStore(baseName);
                Index index = baseCfs.indexManager.getIndexByName(indexName);
                if (index == null)
                    throw new IllegalArgumentException(String.format("Invalid index specified: %s/%s.",
                                                                     baseCfs.metadata.cfName,
                                                                     indexName));

                if (index.getBackingTable().isPresent())
                    valid.add(index.getBackingTable().get());
            }
            else
            {
                ColumnFamilyStore cfStore = getColumnFamilyStore(cfName);
                valid.add(cfStore);
                if (autoAddIndexes)
                    valid.addAll(getIndexColumnFamilyStores(cfStore));
            }
        }

        return valid;
    }

    private Set<ColumnFamilyStore> getIndexColumnFamilyStores(ColumnFamilyStore baseCfs)
    {
        Set<ColumnFamilyStore> stores = new HashSet<>();
        for (ColumnFamilyStore indexCfs : baseCfs.indexManager.getAllIndexColumnFamilyStores())
        {
            logger.info("adding secondary index table {} to operation", indexCfs.metadata.cfName);
            stores.add(indexCfs);
        }
        return stores;
    }

    public static Iterable<Keyspace> all()
    {
        return Iterables.transform(Schema.instance.getKeyspaces(), keyspaceTransformer);
    }

    public static Iterable<Keyspace> nonSystem()
    {
        return Iterables.transform(Schema.instance.getNonSystemKeyspaces(), keyspaceTransformer);
    }

    public static Iterable<Keyspace> nonLocalStrategy()
    {
        return Iterables.transform(Schema.instance.getNonLocalStrategyKeyspaces(), keyspaceTransformer);
    }

    public static Iterable<Keyspace> system()
    {
        return Iterables.transform(Schema.SYSTEM_KEYSPACE_NAMES, keyspaceTransformer);
    }

    public void close()
    {
        if (engine == null)
            return;

        for (ColumnFamilyStore cfs : columnFamilyStores.values())
            engine.close(cfs);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(name='" + getName() + "')";
    }

    public String getName()
    {
        return metadata.name;
    }
}
