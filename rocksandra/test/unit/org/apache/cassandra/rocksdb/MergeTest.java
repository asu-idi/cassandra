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

public class MergeTest extends RocksDBTestBase
{

    @Test
    public void testSimpleColumnMerge() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v2");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=? AND c=?", "p1", "k1"),
                   row("p1", "k1", "v2"));
    }

    @Test
    public void testColumnTombstone() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("DELETE FROM %s where p = ? and c = ?", "p1", "k1");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=? AND c=?", "p1", "k1"));


        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v2");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=? AND c=?", "p1", "k1"),
                   row("p1", "k1", "v2"));
    }

    @Test
    public void testMultipeColumnMerge() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v0 text, v1 text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v0, v1) values (?, ?, ?, ?)", "p1", "k1", "v1", "v2");
        execute("UPDATE %s set v0 = ? where p = ? and c = ?", "v3", "p1", "k1");
        execute("UPDATE %s set v1 = ? where p = ? and c = ?", "v4", "p1", "k1");
        assertRows(execute("SELECT p, c, v0, v1 FROM %s WHERE p=? AND c=?", "p1", "k1"),
                   row("p1", "k1", "v3", "v4"));
    }

    @Test
    public void testTimestmap() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p))");
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ?", "p1", "v1", 100L);
        assertRows(execute("SELECT p, v, writetime(v) FROM %s WHERE p=?", "p1"),
                   row("p1", "v1", 100L));


        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ?", "p1", "v2", 101L);
        assertRows(execute("SELECT p, v, writetime(v) FROM %s WHERE p=?", "p1"),
                   row("p1", "v2", 101L));

        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ?", "p1", "v3", 99L);
        assertRows(execute("SELECT p, v, writetime(v) FROM %s WHERE p=?", "p1"),
                   row("p1", "v2", 101L));
    }

    @Test
    public void testTtl() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p))");

        // An expired column.
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v1",
                System.currentTimeMillis() * 1000 - 100 * 1000000,  80);
        assertRows(execute("SELECT p, v, writetime(v) FROM %s WHERE p=?", "p1"));

        // A not yet expired column.
        long timestamp = System.currentTimeMillis() * 1000 - 100 * 1000000;
        execute("INSERT INTO %s(p, v) values (?, ?) USING TIMESTAMP ? AND TTL ?", "p1", "v2",
                timestamp,  110);
        assertRows(execute("SELECT p, v, writetime(v) FROM %s WHERE p=?", "p1"),
                   row("p1", "v2", timestamp));
    }


}
