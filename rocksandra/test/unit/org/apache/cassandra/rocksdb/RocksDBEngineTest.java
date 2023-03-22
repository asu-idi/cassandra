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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;

import static org.junit.Assert.*;

@RunWith(BMUnitRunner.class)
public class RocksDBEngineTest extends RocksDBTestBase
{

    @Test
    public void testDumpPartition() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p2", "k", "v");


        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBEngine engine = (RocksDBEngine) cfs.engine;

        String dump = engine.dumpPartition(cfs, "'p1'", Integer.MAX_VALUE);
        assertEquals(2, dump.split("\n").length);
    }

    @Test
    public void testGetRocksMemoryUsage() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBEngine engine = (RocksDBEngine) cfs.engine;
        assertTrue(engine.memoryUsage().get("kMemTableTotal") > 0);
    }

    private Set<File> getSnapshotList(String tableName)
    {
        final Set<File> snapshots = new HashSet<>();
        Path snapshotPath = Paths.get(RocksDBConfigs.ROCKSDB_DIR, "s0", RocksDBConfigs.ROCKSDB_KEYSPACE, tableName, "snapshots");

        File snapshotDir = new File(snapshotPath.toString());
        if (snapshotDir.exists() && snapshotDir.isDirectory())
        {
            final File[] snapshotDirs = snapshotDir.listFiles();
            if (snapshotDirs != null)
            {
                for (final File snap : snapshotDirs)
                    snapshots.add(snap);
            }
        }

        return snapshots;
    }

    private void generateTestData() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p2", "k", "v");

        assertRows(execute("SELECT * from %s WHERE p = ?", "p1"),
                   row("p1", "k1", "v1"),
                   row("p1", "k2", "v2"));

        assertRows(execute("SELECT * from %s WHERE p = ?", "p2"),
                   row("p2", "k", "v"));
    }

    @Test
    public void testTruncate() throws Throwable
    {
        generateTestData();

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        cfs.truncateBlocking();
        assertEmpty(execute("SELECT * from %s WHERE p = ? AND c = ?", "p1", "k1"));
        assertEmpty(execute("SELECT * from %s WHERE p = ? AND c = ?", "p1", "k2"));
        assertEmpty(execute("SELECT * from %s WHERE p = ? AND c = ?", "p2", "k"));
    }

    @Test
    public void testTruncateWithAutoSnapshot() throws Throwable
    {
        boolean originalState = DatabaseDescriptor.isAutoSnapshot();

        try
        {
            DatabaseDescriptor.setAutoSnapshot(true);

            generateTestData();

            ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

            // Make sure there's no snapshot
            Set<File> snapshots = getSnapshotList(cfs.getTableName());
            assertFalse(snapshots.stream().anyMatch(x -> x.getName().endsWith("truncate")));
            cfs.truncateBlocking();

            // Make sure there's a snapshot
            snapshots = getSnapshotList(cfs.getTableName());
            assertTrue(snapshots.stream().anyMatch(x -> x.getName().endsWith("truncate")));
        }
        finally
        {
            DatabaseDescriptor.setAutoSnapshot(originalState);
        }
    }

    @Test
    public void testTruncateWithoutAutoSnapshot() throws Throwable
    {
        boolean originalState = DatabaseDescriptor.isAutoSnapshot();

        try
        {
            DatabaseDescriptor.setAutoSnapshot(false);

            generateTestData();

            ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

            // Make sure there's no snapshot
            Set<File> snapshots = getSnapshotList(cfs.getTableName());
            assertFalse(snapshots.stream().anyMatch(x -> x.getName().endsWith("truncate")));
            cfs.truncateBlocking();

            // Make sure there's a snapshot
            snapshots = getSnapshotList(cfs.getTableName());
            assertFalse(snapshots.stream().anyMatch(x -> x.getName().endsWith("truncate")));
        }
        finally
        {
            DatabaseDescriptor.setAutoSnapshot(originalState);
        }
    }

    @Test
    public void testSetCompactionThroughputNonZero() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBCF cf = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);
        cf.forceFlush();

        cfs.engine.setCompactionThroughputMbPerSec(1);
        assertEquals(1 * 1024L * 1024L, ((RocksDBEngine)cfs.engine).rateLimiter.getBytesPerSecond());
        cfs.engine.forceMajorCompaction(cfs);

        assertTrue(RocksDBProperty.getEstimatedLiveDataSize(cf) > 0);
    }

    @Test
    public void testSetCompactionThroughputZero() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBCF cf = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);
        cf.forceFlush();

        cfs.engine.setCompactionThroughputMbPerSec(0);
        assertEquals(Integer.MAX_VALUE * 1024L * 1024L, ((RocksDBEngine)cfs.engine).rateLimiter.getBytesPerSecond());
        cfs.engine.forceMajorCompaction(cfs);

        assertTrue(RocksDBProperty.getEstimatedLiveDataSize(cf) > 0);
    }

    @Test
    public void testNumberOfRocksDBCF() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        int originalNumRocksDBFamilySize = ((RocksDBEngine)cfs.engine).rocksDBFamily.size();

        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        assertEquals(originalNumRocksDBFamilySize, ((RocksDBEngine)cfs.engine).rocksDBFamily.size());
    }

    @Test
    @BMRule(name = "throw exception when merging rows",
    targetClass = "RocksDBCF",
    targetMethod = "merge",
    targetLocation = "AT ENTRY",
    action = "throw new RocksDBException(\"test exception\");")
    public void testShouldThrowStorageEngineExceptionWhenRowMergeFails() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        assertInvalidMessage("Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
    }

    @Test
    @BMRule(name = "throw exception when merging index rows",
    targetClass = "RocksandraClusteringColumnIndex",
    targetMethod = "insert",
    targetLocation = "AT ENTRY",
    action = "throw new RuntimeException(\"test exception\");")
    public void testShouldThrowStorageEngineExceptionWhenIndexInsertionFails() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));
        assertInvalidMessage("Index update failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
    }
}
