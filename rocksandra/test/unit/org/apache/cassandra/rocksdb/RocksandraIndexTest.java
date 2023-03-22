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

import org.apache.cassandra.index.IndexNotAvailableException;
import org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex;

import static org.junit.Assert.assertTrue;

public class RocksandraIndexTest extends RocksDBTestBase
{
    @Test
    public void testSetTextIndex() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                    RocksandraClusteringColumnIndex.class.getName()));

        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k4", "v2", "j4");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k5", "v2", "j5");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k1", "v1", "j1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v2"),
                   row("p1", "k4", "v2", "j4"),
                   row("p1", "k5", "v2", "j5"));
    }

    @Test
    public void testSetDoubleIndex() throws Throwable
    {
        createTable("CREATE TABLE %s (p double, c double, v double, j double, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1.1, 2.1, 3.1, 4.1);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1.1, 2.2, 3.1, 4.2);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1.1, 2.3, 3.1, 4.3);

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1.1, 2.4, 3.2, 4.4);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1.1, 2.5, 3.2, 4.5);

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", 1.1, 3.1),
                   row( 1.1, 2.1, 3.1, 4.1),
                   row( 1.1, 2.2, 3.1, 4.2),
                   row( 1.1, 2.3, 3.1, 4.3));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", 1.1, 3.2),
                   row( 1.1, 2.4, 3.2, 4.4),
                   row( 1.1, 2.5, 3.2, 4.5));
    }

    @Test
    public void testSetInt32Index() throws Throwable
    {
        createTable("CREATE TABLE %s (p int, c double, v double, j double, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1, 2.1, 3.1, 4.1);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1, 2.2, 3.1, 4.2);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1, 2.3, 3.1, 4.3);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1, 2.4, 3.2, 4.4);
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", 1, 2.5, 3.2, 4.5);

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", 1, 3.1),
                   row( 1, 2.1, 3.1, 4.1),
                   row( 1, 2.2, 3.1, 4.2),
                   row( 1, 2.3, 3.1, 4.3));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", 1, 3.2),
                   row( 1, 2.4, 3.2, 4.4),
                   row( 1, 2.5, 3.2, 4.5));
    }

    @Test
    public void deleteRowsIndexTest() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k4", "v2", "j4");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k5", "v2", "j5");

        // deleting the first and last row
        execute("DELETE FROM %s WHERE p=? AND c=? AND v=?", "p1", "k1", "v1");
        execute("DELETE FROM %s WHERE p=? AND c=? AND v=?", "p1", "k5", "v2");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v2"),
                   row("p1", "k4", "v2", "j4"));
    }

    @Test
    public void updateRowsIndexTest() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k4", "v2", "j4");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k5", "v2", "j5");

        // updating the first and last row
        execute("UPDATE %s SET j='j6' WHERE p=? AND c=? AND v=?", "p1", "k1", "v1");
        execute("UPDATE %s SET j='j7' WHERE p=? AND c=? AND v=?", "p1", "k5", "v2");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k1", "v1", "j6"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v2"),
                   row("p1", "k4", "v2", "j4"),
                   row("p1", "k5", "v2", "j7"));
    }

    @Test
    public void testIndexTableCreatedAfterDataAlreadyInserted() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p2", "k4", "v2", "j4");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p2", "k5", "v2", "j5");

        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));
        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k1", "v1", "j1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p2", "v2"),
                   row("p2", "k4", "v2", "j4"),
                   row("p2", "k5", "v2", "j5"));
    }

    @Test
    public void testIndexTableCreatedAfterDataAlreadyInsertedWithDeletionsAndUpdates() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p2", "k4", "v2", "j4");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p2", "k5", "v2", "j5");

        execute("DELETE FROM %s WHERE p=? AND c=? AND v=?", "p1", "k1", "v1");
        execute("UPDATE %s SET j='j6' WHERE p=? AND c=? AND v=?", "p2", "k5", "v2");

        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));
        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p2", "v2"),
                   row("p2", "k4", "v2", "j4"),
                   row("p2", "k5", "v2", "j6"));
    }

    @Test
    public void testIndexTableValidityWhenDataIsUpdatedAfterCreation() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p2", "k4", "v2", "j4");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p2", "k5", "v2", "j5");

        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));
        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        execute("DELETE FROM %s WHERE p=? AND c=? AND v=?", "p1", "k1", "v1");
        execute("UPDATE %s SET j='j6' WHERE p=? AND c=? AND v=?", "p2", "k5", "v2");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p2", "v2"),
                   row("p2", "k4", "v2", "j4"),
                   row("p2", "k5", "v2", "j6"));
    }

    @Test
    public void testDropIndex() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));
        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k1", "v1", "j1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));

        dropIndex("DROP INDEX %s.test_index");

        execute("DELETE FROM %s WHERE p=? AND c=? AND v=?", "p1", "k1", "v1");

        createIndex(String.format("CREATE CUSTOM INDEX test_index ON %%s(v) USING '%s'",
                                  RocksandraClusteringColumnIndex.class.getName()));

        assertTrue(waitForIndex(KEYSPACE, currentTable(), "test_index"));
        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"),
                   row("p1", "k2", "v1", "j2"),
                   row("p1", "k3", "v1", "j3"));
    }
}
