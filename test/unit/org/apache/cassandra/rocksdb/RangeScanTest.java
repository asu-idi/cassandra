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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.cassandra.Util;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.cql3.CQLTester;
import org.apache.cassandra.cql3.Operator;
import org.apache.cassandra.cql3.UntypedResultSet;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.partitions.FilteredPartition;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.utils.ByteBufferUtil;

import static org.junit.Assert.assertEquals;

public class RangeScanTest extends RocksDBTestBase
{
    @BeforeClass
    public static void classSetUp()
    {
        System.setProperty("cassandra.rocksdb.dir", "/tmp/rocksdbtest/" + UUID.randomUUID());
        System.setProperty("cassandra.rocksdb.stream.dir", "/tmp/rocksdbteststream/" + UUID.randomUUID());

        RocksDBConfigs.ROCKSDB_KEYSPACE = CQLTester.KEYSPACE;
        RocksDBConfigs.NUM_SHARD = 10;
        File rocksdbdir = new File(RocksDBConfigs.ROCKSDB_DIR);
        if (rocksdbdir.exists())
        {
            FileUtils.deleteRecursive(rocksdbdir);
        }
    }

    @Test
    public void testSelectStar() throws Throwable
    {
        createTable("CREATE TABLE %s (userid uuid PRIMARY KEY, firstname text)");
        int num = 100;
        List<Object[]> expectedRows = new ArrayList<>(num);
        for (int i = 0; i < num; i++)
        {
            UUID randomUUID = UUID.randomUUID();
            String firstname = "foo_" + i;

            execute("INSERT INTO %s (userid, firstname) VALUES (?, ?)", randomUUID, firstname);
            expectedRows.add(row(randomUUID, firstname));
        }

        UntypedResultSet resultSet = execute("SELECT * FROM %s");

        assertRowCount(resultSet, num);
        assertRowsIgnoringOrder(resultSet, expectedRows.toArray(new Object[][] {}));
    }

    @Test
    public void testLimits() throws Throwable
    {
        createTable("CREATE TABLE %s (userid uuid PRIMARY KEY, firstname text)");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        int num = 100;
        for (int i = 0; i < num; i++)
        {
            UUID randomUUID = UUID.randomUUID();
            String firstname = "foo_" + i;

            execute("INSERT INTO %s (userid, firstname) VALUES (?, ?)", randomUUID, firstname);
        }

        assertEquals(num, Util.getAll(Util.cmd(cfs).build()).size());

        for (int i = 0; i < num; i++)
            assertEquals(i, Util.getAll(Util.cmd(cfs).withLimit(i).build()).size());
    }

    @Test
    public void testRangeSliceInclusionExclusion() throws Throwable
    {
        createTable("CREATE TABLE %s (userid int PRIMARY KEY, firstname text)");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        for (int i = 0; i < 10; i++)
        {
            String firstname = "foo_" + i;

            execute("INSERT INTO %s (userid, firstname) VALUES (?, ?)", i, firstname);
        }

        cfs.forceBlockingFlush();

        ColumnDefinition cDef = cfs.metadata.getColumnDefinition(ByteBufferUtil.bytes("firstname"));

        List<FilteredPartition> partitions;

        // Start and end inclusive
        partitions = Util.getAll(Util.cmd(cfs).fromKeyIncl(2).toKeyIncl(7).build());
        assertEquals(3, partitions.size());

        // Start and end excluded
        partitions = Util.getAll(Util.cmd(cfs).fromKeyExcl(2).toKeyExcl(7).build());
        assertEquals(1, partitions.size());

        // Start excluded, end included
        partitions = Util.getAll(Util.cmd(cfs).fromKeyExcl(2).toKeyIncl(7).build());
        assertEquals(2, partitions.size());

        // Start included, end excluded
        partitions = Util.getAll(Util.cmd(cfs).fromKeyIncl(2).toKeyExcl(7).build());
        assertEquals(2, partitions.size());
    }

    @Test
    public void testClusteringIndex() throws Throwable
    {
        createTable("CREATE TABLE %s (userid int, firstname text, lastname text, PRIMARY KEY (userid, firstname))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        int num_partitions = 10;
        int num_firstname_a = 3;
        for (int i = 0; i < num_partitions; i++)
        {
            if (i < num_firstname_a)
            {
                String firstname = "a";
                String lastname = "a";
                execute("INSERT INTO %s (userid, firstname, lastname) VALUES (?, ?, ?)", i, firstname, lastname);
            }
            else
            {
                String firstname = "z";
                String lastname = "z";
                execute("INSERT INTO %s (userid, firstname, lastname) VALUES (?, ?, ?)", i, firstname, lastname);
            }
        }

        cfs.forceBlockingFlush();
        List<FilteredPartition> partitions = Util.getAll(Util.cmd(cfs).filterOn("firstname", Operator.LT, "b").build());
        // only num_firstname_a of the partitions will have firstname < 'b'
        assertEquals(num_firstname_a, partitions.size());

        partitions = Util.getAll(Util.cmd(cfs).filterOn("firstname", Operator.EQ, "z").build());
        assertEquals(num_partitions - num_firstname_a, partitions.size());
    }
}
