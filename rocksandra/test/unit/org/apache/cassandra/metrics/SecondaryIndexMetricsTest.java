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

package org.apache.cassandra.metrics;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.RocksDBTestBase;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(BMUnitRunner.class)
public class SecondaryIndexMetricsTest extends RocksDBTestBase
{
    @Test
    public void testSetIndexSuccess() throws Throwable
    {

        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex("CREATE CUSTOM INDEX test_index ON %s(v) USING 'org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex'");

        int ogFailures = (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount();
        int ogTotalInserts = (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount();

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        assertEquals(ogTotalInserts + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount());
        assertEquals(ogFailures, (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount());
    }

    @Test
    @BMRule(name = "Mark Index",
            targetClass = "RocksandraClusteringColumnIndex",
            targetMethod = "insert",
            targetLocation = "AT ENTRY",
            action = "throw new RuntimeException(\"test exception\");")
    public void testSetIndexFailure() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex("CREATE CUSTOM INDEX test_index ON %s(v) USING 'org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex'");

        int ogFailures = (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount();
        int ogTotalInserts = (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount();

        assertInvalidMessage("Index update failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        assertInvalidMessage("Index update failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        assertInvalidMessage("Index update failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        assertEquals(ogTotalInserts, (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount());
        assertEquals(ogFailures, (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount());
    }

    @Test
    @BMRule(name = "Trigger Index Transaction Rollback",
    targetClass = "RocksDBInstanceHandle",
    targetMethod = "merge(RocksCFName, byte[], byte[], Transaction)",
    targetLocation = "AT ENTRY",
    action = "throw new RocksDBException(\"test exception\");")
    public void testIndexTransactionRollbackSuccess() throws Throwable
    {
        RocksDBConfigs.ENABLE_INDEX_TRANSACTIONS = true;
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex("CREATE CUSTOM INDEX test_index ON %s(v) USING 'org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex'");

        int ogFailures = (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount();
        int ogTotalInserts = (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount();

        assertInvalidMessage("Index update failed: Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        assertInvalidMessage("Index update failed: Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        assertInvalidMessage("Index update failed: Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"));

        assertEquals(ogTotalInserts + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount());
        assertEquals(ogFailures + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount());
    }

    @Test
    @BMRules(rules = {
    @BMRule(name = "Trigger Index Transaction Rollback",
    targetClass = "RocksDBInstanceHandle",
    targetMethod = "merge(RocksCFName, byte[], byte[], Transaction)",
    targetLocation = "AT ENTRY",
    action = "throw new RocksDBException(\"test exception\");"),
    @BMRule(name = "Index Transaction Rollback Failure",
    targetClass = "Transaction",
    targetMethod = "rollback()",
    targetLocation = "AT ENTRY",
    action = "throw new RocksDBException(\"test exception\");")
    })
    public void testIndexTransactionRollbackFailure() throws Throwable
    {
        RocksDBConfigs.ENABLE_INDEX_TRANSACTIONS = true;
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex("CREATE CUSTOM INDEX test_index ON %s(v) USING 'org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex'");

        int ogFailures = (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount();
        int ogTotalInserts = (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount();

        assertInvalidMessage("Index update failed: Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        assertInvalidMessage("Index update failed: Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        assertInvalidMessage("Index update failed: Row merge failed: test exception",
                             "INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"));

        assertEquals(ogTotalInserts + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount());
        assertEquals(ogFailures + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount());
    }

    @Test
    @BMRule(name = "Index Transaction Commit Failure",
    targetClass = "Transaction",
    targetMethod = "commit()",
    targetLocation = "AT ENTRY",
    action = "throw new RocksDBException(\"test exception\");")
    public void testIndexTransactionCommitFailure() throws Throwable
    {
        RocksDBConfigs.ENABLE_INDEX_TRANSACTIONS = true;
        createTable("CREATE TABLE %s (p text, c text, v text, j text, PRIMARY KEY (p, c, v))");
        createIndex("CREATE CUSTOM INDEX test_index ON %s(v) USING 'org.apache.cassandra.rocksdb.index.RocksandraClusteringColumnIndex'");

        int ogFailures = (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount();
        int ogTotalInserts = (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount();

        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k1", "v1", "j1");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k2", "v1", "j2");
        execute("INSERT INTO %s(p, c, v, j) values (?, ?, ?, ?)", "p1", "k3", "v1", "j3");

        assertRows(execute("SELECT * FROM %s WHERE p=? AND v=?", "p1", "v1"));

        assertEquals(ogTotalInserts + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiTotalInsertions.getCount());
        assertEquals(ogFailures + 3, (int) RocksDBEngine.secondaryIndexMetrics.rsiInsertionFailures.getCount());
    }
}
