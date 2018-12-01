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

import org.junit.Test;

import org.apache.cassandra.rocksdb.RocksDBTestBase;

public class PartitionDeletionTest extends RocksDBTestBase
{

    @Test
    public void partitionDeleteWithoutClusteringKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p))");
        execute("INSERT INTO %s(p, v) values (?, ?)", "p1", "v1");
        execute("INSERT INTO %s(p, v) values (?, ?)", "p2", "v2");

        execute("DELETE FROM %s WHERE p=?", "p1");
        triggerCompaction();
        assertRows(execute("SELECT p, v FROM %s WHERE p=?", "p1"));
        assertRows(execute("SELECT p, v FROM %s WHERE p=?", "p2"),
                   row("p2", "v2"));
    }

    @Test
    public void parititionDeleteWithClusteringKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"),
                   row("p1", "k1", "v1"), row("p1", "k2", "v2"));

        execute("DELETE FROM %s WHERE p=?", "p1");
        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"));
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k3", "v3");
        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"), row("p1", "k3", "v3"));
    }

    @Test
    public void parititionDeleteWithClusteringKeyAndFixLengthPartitionKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p bigint, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", 1L, "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", 1L, "k2", "v2");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", 1L),
                   row(1L, "k1", "v1"), row(1L, "k2", "v2"));

        execute("DELETE FROM %s WHERE p=?", 1L);
        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", 1L));
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", 1L, "k3", "v3");
        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", 1L), row(1L, "k3", "v3"));
    }

    @Test
    public void parititionDeleteWithUsingTimestamp() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?) USING TIMESTAMP 100", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?) USING TIMESTAMP 200", "p1", "k2", "v2");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"),
                   row("p1", "k1", "v1"), row("p1", "k2", "v2"));

        execute("DELETE FROM %s USING TIMESTAMP 150 WHERE p=?", "p1");
        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"),
                   row("p1", "k2", "v2"));

        execute("DELETE FROM %s WHERE p=?", "p1");

        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"));
    }

    @Test
    public void mergeMultiplePartitonDeletion() throws Throwable{
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?) USING TIMESTAMP 100", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?) USING TIMESTAMP 200", "p1", "k2", "v2");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?) USING TIMESTAMP 300", "p1", "k3", "v3");
        execute("DELETE FROM %s USING TIMESTAMP 250 WHERE p=?", "p1");
        execute("DELETE FROM %s USING TIMESTAMP 150 WHERE p=?", "p1");
        triggerCompaction();
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"),
                   row("p1", "k3", "v3"));
    }
}
