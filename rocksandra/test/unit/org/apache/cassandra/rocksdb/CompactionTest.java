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

import org.junit.Test;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.SinglePartitionReadCommand;
import org.apache.cassandra.engine.StorageEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompactionTest extends RocksDBTestBase
{

    @Test
    public void testExpiredColumnsAreConvertedToTombstones() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p))");
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v0",
                System.currentTimeMillis() * 1000 - 140 * 1000000, 80);
        rocksDBFlush();

        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v1",
                System.currentTimeMillis() * 1000 - 120 * 1000000, 80);

        rocksDBFlush();

        // An expired column.
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v2",
                System.currentTimeMillis() * 1000 - 100 * 1000000, 80);

        rocksDBFlush();

        SinglePartitionReadCommand readCommand = readCommand("p1", "v");
        assertEquals(1, queryEngine(readCommand).size());
        assertTrue(queryEngine(readCommand).get(0).cells().iterator().next().isExpiring());

        triggerCompaction();

        assertEquals(1, queryEngine(readCommand).size());
        assertTrue(queryEngine(readCommand).get(0).cells().iterator().next().isTombstone());
        assertRows(execute("SELECT p, v FROM %s WHERE p=?", "p1"));
    }

    @Test
    public void testExpiredColumnsArePurgedWhenPurgeTtlOptionIsOn() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p)) WITH purge_ttl_on_expiration = True");
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v0",
                System.currentTimeMillis() * 1000 - 140 * 1000000, 80);
        rocksDBFlush();

        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v1",
                System.currentTimeMillis() * 1000 - 120 * 1000000, 80);

        rocksDBFlush();

        // An expired column.
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v2",
                System.currentTimeMillis() * 1000 - 100 * 1000000, 80);

        rocksDBFlush();

        SinglePartitionReadCommand readCommand = readCommand("p1", "v");
        assertEquals(1, queryEngine(readCommand).size());
        assertTrue(queryEngine(readCommand).get(0).cells().iterator().next().isExpiring());

        triggerCompaction();

        assertEquals(0, queryEngine(readCommand).size());
        assertRows(execute("SELECT p, v FROM %s WHERE p=?", "p1"));
    }

    @Test
    public void testTombstoneCellAreRemovedAfterGCGracePassed() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, t text, v text, PRIMARY KEY (p, t)) WITH gc_grace_seconds=0");
        SinglePartitionReadCommand readCommand = readCommand("p0", "v");
        execute("INSERT INTO %s (p, t, v) values (?, ?, ?)",
                "p0", "t0", "v0");
        execute("INSERT INTO %s (p, t, v) values (?, ?, ?)",
                "p0", "t1", "v1");
        execute("INSERT INTO %s (p, t, v) values (?, ?, ?)",
                "p1", "t0", "v0");
        rocksDBFlush();
        execute("DELETE v FROM %s WHERE p=? AND t=?",
                "p0", "t0");
        execute("DELETE v FROM %s WHERE p=? AND t=?",
                "p0", "t1");
        rocksDBFlush();

        assertEquals(0, queryEngine(readCommand).size());
        assertEquals(2, numOfRocksdbKeysInPartition("'p0'", getCurrentColumnFamilyStore()));

        triggerCompaction();
        triggerCompaction(); // trigger compaction twice since we gc and remove key in different cycle
        assertEquals(0, queryEngine(readCommand).size());
        assertTrue(numOfRocksdbKeysInPartition("'p0'", getCurrentColumnFamilyStore()) < 2);
    }
}
