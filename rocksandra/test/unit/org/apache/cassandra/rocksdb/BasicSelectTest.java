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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.junit.Test;

import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.cassandra.utils.UUIDGen;

import static org.apache.cassandra.utils.ByteBufferUtil.EMPTY_BYTE_BUFFER;
import static org.apache.cassandra.utils.ByteBufferUtil.bytes;

public class BasicSelectTest extends RocksDBTestBase
{

    @Test
    public void testSelectSingleColumn() throws Throwable
    {
        createTable("CREATE TABLE %s (userid uuid PRIMARY KEY, firstname text)");

        UUID id1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID id2 = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

        execute("INSERT INTO %s (userid, firstname) VALUES (?, 'Frodo')", id1);
        execute("INSERT INTO %s (userid, firstname) VALUES (?, 'Samwise')", id2);

        assertRowCount(execute("SELECT firstname FROM %s WHERE userid=?", id1), 1);
    }


    @Test
    public void testClusteringEndWithZero() throws Throwable
    {
        createTable("CREATE TABLE %s (p int, c varint, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", 1, new BigInteger("1500"), "foo");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=?" , 1),
                   row(1, new BigInteger("1500"), "foo"));
    }

    @Test
    public void testSelectWithOneClusteringKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=? AND c=?", "p1", "k1"),
                   row("p1", "k1", "v1"));
        assertRows(execute("SELECT p, c, v FROM %s WHERE p=? AND c=?", "p1", "k2"),
                   row("p1", "k2", "v2"));
    }


    @Test
    public void testSelectWithMultipleClusteringKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, u text, PRIMARY KEY (p, c, v))");

        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "k1", "v1", "u1");
        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "k2", "v2", "u2");
        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "k2", "v22", "u22");
        assertRows(execute("SELECT p, c, v, u FROM %s WHERE p=? AND c=? and v=?", "p1", "k1", "v1"),
                   row("p1", "k1", "v1", "u1"));
        assertRows(execute("SELECT p, c, v, u FROM %s WHERE p=? AND c=? and v=?", "p1", "k2", "v1"));
        assertRows(execute("SELECT p, c, v, u FROM %s WHERE p=? AND c=? and v=?", "p1", "k1", "v2"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c=? and v=?", "p1", "k2", "v2"),
                   row("p1", "k2", "v2", "u2"));
    }


    @Test
    public void testSelectWithPartialClusteringKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, u text, PRIMARY KEY (p, c, v))");

        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "c1", "v1", "u1");
        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "c1", "v11", "u11");
        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "c2", "v2", "u2");
        assertRows(execute("SELECT p, c, v, u FROM %s WHERE p=? AND c=?", "p1", "c1"),
                   row("p1", "c1", "v1", "u1"), row("p1", "c1", "v11", "u11"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c=?", "p1", "c2"),
                   row("p1", "c2", "v2", "u2"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<?", "p1", "c1"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<=?", "p1", "c1"),
                   row("p1", "c1", "v1", "u1"), row("p1", "c1", "v11", "u11"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<?", "p1", "c2"),
                   row("p1", "c1", "v1", "u1"), row("p1", "c1", "v11", "u11"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<=?", "p1", "c2"),
                   row("p1", "c1", "v1", "u1"),
                   row("p1", "c1", "v11", "u11"),
                   row("p1", "c2", "v2", "u2"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>?", "p1", "c1"),
                   row("p1", "c2", "v2", "u2"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>=?", "p1", "c1"),
                   row("p1", "c1", "v1", "u1"),
                   row("p1", "c1", "v11", "u11"),
                   row("p1", "c2", "v2", "u2"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>?", "p1", "c2"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>=?", "p1", "c2"),
                   row("p1", "c2", "v2", "u2"));
    }

    @Test
    public void testSelectWithPartialClusteringKeyWithReverseOrder() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, u text, PRIMARY KEY (p, c, v))");

        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "c1", "v1", "u1");
        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "c1", "v11", "u11");
        execute("INSERT INTO %s(p, c, v, u) values (?, ?, ?, ?)", "p1", "c2", "v2", "u2");
        assertRows(execute("SELECT p, c, v, u FROM %s WHERE p=? AND c=? ORDER BY c DESC, v DESC", "p1", "c1"),
                   row("p1", "c1", "v11", "u11"), row("p1", "c1", "v1", "u1"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c=? ORDER BY c DESC, v DESC", "p1", "c2"),
                   row("p1", "c2", "v2", "u2"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<? ORDER BY c DESC, v DESC", "p1", "c1"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<=? ORDER BY c DESC, v DESC", "p1", "c1"),
                   row("p1", "c1", "v11", "u11"), row("p1", "c1", "v1", "u1"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<? ORDER BY c DESC, v DESC", "p1", "c2"),
                   row("p1", "c1", "v11", "u11"), row("p1", "c1", "v1", "u1"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c<=? ORDER BY c DESC, v DESC", "p1", "c2"),
                   row("p1", "c2", "v2", "u2"),
                   row("p1", "c1", "v11", "u11"),
                   row("p1", "c1", "v1", "u1"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>? ORDER BY c DESC, v DESC", "p1", "c1"),
                   row("p1", "c2", "v2", "u2"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>=? ORDER BY c DESC, v DESC", "p1", "c1"),
                   row("p1", "c2", "v2", "u2"),
                   row("p1", "c1", "v11", "u11"),
                   row("p1", "c1", "v1", "u1"));

        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>? ORDER BY c DESC, v DESC", "p1", "c2"));
        assertRows(execute("SELECT p, c, v , u FROM %s WHERE p=? AND c>=?  ORDER BY c DESC, v DESC", "p1", "c2"),
                   row("p1", "c2", "v2", "u2"));
    }



    @Test
    public void testOrderIsPreservedForSortableClusteringKey() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c bigint, v text, PRIMARY KEY (p, c))");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", 12L, "v3");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", 9L, "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", 20L, "v2");

        assertRows(execute("SELECT v FROM %s WHERE p=?", "p1"),
                   row("v1"),
                   row("v3"),
                   row("v2"));
    }

    @Test
    public void testOrderIsPreservedForTimedUUIDKeys() throws Throwable
    {
        long now = System.currentTimeMillis();
        UUID uuid1 = UUIDGen.getTimeUUID(now, 1L);
        UUID uuid2 = UUIDGen.getTimeUUID(now, 2L);
        UUID uuid3 = UUIDGen.getTimeUUID(now, 3L);
        BigInteger p1 = new BigInteger("535959550");

        createTable("CREATE TABLE %s (p varint, c1 bigint, c2 timeuuid, v blob, PRIMARY KEY (p, c1, c2))");

        execute("INSERT INTO %s(p, c1, c2, v) values (?, ?, ?, ?)",
                p1, 9L, uuid2, ByteBuffer.wrap("v1".getBytes()));
        execute("INSERT INTO %s(p, c1, c2, v) values (?, ?, ?, ?)",
                p1, 9L, uuid1, ByteBuffer.wrap("v2".getBytes()));
        execute("INSERT INTO %s(p, c1, c2, v) values (?, ?, ?, ?)",
                p1, 9L, uuid3, ByteBuffer.wrap("v3".getBytes()));

        assertRows(execute("SELECT c1, c2, v FROM %s WHERE p=?", p1),
                   row(9L, uuid1, ByteBuffer.wrap("v2".getBytes())),
                   row(9L, uuid2, ByteBuffer.wrap("v1".getBytes())),
                   row(9L, uuid3, ByteBuffer.wrap("v3".getBytes())));
    }

    @Test
    public void testSelectWholePartitionRangeQuery() throws Throwable
    {
        createTable("CREATE TABLE %s (a int, b int, c int, d int, e int, f text, PRIMARY KEY (a, b, c, d, e) )");

        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 2, '2')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 1, '1')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 2, 1, '1')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 3, '3')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 5, '5')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (2, 1, 1, 1, 5, '5')");

        assertRows(execute("SELECT a, b, c, d, e, f FROM %s WHERE a = 1"),
                   row(1, 1, 1, 1, 1, "1"),
                   row(1, 1, 1, 1, 2, "2"),
                   row(1, 1, 1, 1, 3, "3"),
                   row(1, 1, 1, 1, 5, "5"),
                   row(1, 1, 1, 2, 1, "1"));
    }

    @Test
    public void testRangeQueryWithMerge() throws Throwable
    {
        createTable("CREATE TABLE %s (a int, b int, c int, d int, e int, f text, g text, PRIMARY KEY (a, b, c, d, e) )");

        execute("INSERT INTO %s (a, b, c, d, e, f, g) VALUES (1, 1, 1, 1, 2, '2', '0')");
        execute("INSERT INTO %s (a, b, c, d, e, f, g) VALUES (1, 1, 1, 1, 1, '1', '0')");
        execute("INSERT INTO %s (a, b, c, d, e, f, g) VALUES (1, 1, 1, 2, 1, '1', '0')");
        execute("INSERT INTO %s (a, b, c, d, e, f, g) VALUES (1, 1, 1, 1, 3, '3', '0')");
        execute("INSERT INTO %s (a, b, c, d, e, f, g) VALUES (1, 1, 1, 1, 5, '5', '0')");
        execute("INSERT INTO %s (a, b, c, d, e, f, g) VALUES (2, 1, 1, 1, 5, '5', '0')");

        execute("DELETE FROM %s WHERE a=1 AND b=1 AND c=1 AND d=1 AND e=1");
        execute("DELETE FROM %s WHERE a=1 AND b=1 AND c=1 AND d=1 AND e=3");
        execute("UPDATE %s SET f='6' WHERE a=1 AND b=1 AND c=1 AND d=1 AND e=5");
        execute("UPDATE %s SET g='1' WHERE a=1 AND b=1 AND c=1 AND d=1 AND e=5");

        assertRows(execute("SELECT a, b, c, d, e, f, g FROM %s WHERE a = 1"),
                   row(1, 1, 1, 1, 2, "2", "0"),
                   row(1, 1, 1, 1, 5, "6", "1"),
                   row(1, 1, 1, 2, 1, "1", "0"));
    }

    @Test
    public void testEmptyResult() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        assertRows(execute("SELECT * FROM %s WHERE p=? AND c=?", "p1", "k1"));
    }

    @Test
    public void testRangeQueryWithDescClusteringKey() throws Throwable
    {
        long now = System.currentTimeMillis();
        UUID uuid1 = UUIDGen.getTimeUUID(now, 1L);
        UUID uuid2 = UUIDGen.getTimeUUID(now, 2L);
        UUID uuid3 = UUIDGen.getTimeUUID(now, 3L);
        BigInteger p1 = new BigInteger("535959550");

        createTable("CREATE TABLE %s (" +
                    "key varint, " +
                    "column1 timeuuid, " +
                    "value text, " +
                    "PRIMARY KEY (key, column1))" +
                    "WITH CLUSTERING ORDER BY (column1 DESC)");

        execute("INSERT INTO %s(key, column1, value) values (?, ?, ?)",
                p1, uuid2, "v1");
        execute("INSERT INTO %s(key, column1, value) values (?, ?, ?)",
                p1, uuid1, "v2");
        execute("INSERT INTO %s(key, column1, value) values (?, ?, ?)",
                p1, uuid3, "v3");

        assertRows(execute("SELECT * FROM %s WHERE key=?", p1),
                   row(p1, uuid3, "v3"),
                   row(p1, uuid2, "v1"),
                   row(p1, uuid1, "v2"));
    }

    @Test
    public void testRangeQueryWithMixedOrderClusteringKey() throws Throwable
    {
        long now = System.currentTimeMillis();
        UUID uuid1 = UUIDGen.getTimeUUID(now, 1L);
        UUID uuid2 = UUIDGen.getTimeUUID(now, 2L);
        UUID uuid3 = UUIDGen.getTimeUUID(now, 3L);
        UUID uuid4 = UUIDGen.getTimeUUID(now, 4L);
        BigInteger p1 = new BigInteger("535959550");
        BigInteger big0 = BigInteger.valueOf(0);
        BigInteger big1 = BigInteger.valueOf(1);

        createTable("CREATE TABLE %s (" +
                    "key varint, " +
                    "column1 varint, " +
                    "column2 timeuuid, " +
                    "value text, " +
                    "PRIMARY KEY (key, column1, column2))" +
                    "WITH CLUSTERING ORDER BY (column1 ASC, column2 DESC)");


        execute("INSERT INTO %s(key, column1, column2, value) values (?, ?, ?, ?)",
                p1, big0, uuid2, "v1");
        execute("INSERT INTO %s(key, column1, column2, value) values (?, ?, ?, ?)",
                p1, big0, uuid1, "v2");
        execute("INSERT INTO %s(key, column1, column2, value) values (?, ?, ?, ?)",
                p1, big0, uuid3, "v3");
        execute("INSERT INTO %s(key, column1, column2, value) values (?, ?, ?, ?)",
                p1, big1, uuid4, "v4");


        assertRows(execute("SELECT * FROM %s WHERE key=?", p1),
                   row(p1, big0, uuid3, "v3"),
                   row(p1, big0, uuid2, "v1"),
                   row(p1, big0, uuid1, "v2"),
                   row(p1, big1, uuid4, "v4"));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testSingleClustering() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, s text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v, s) values (?, ?, ?, ?)", "p1", "k1", "v1", "sv1");
        execute("INSERT INTO %s(p, c, v, s) values (?, ?, ?, ?)", "p1", "k2", "v2", "sv1");
        execute("INSERT INTO %s(p, c, v, s) values (?, ?, ?, ?)", "p2", "k3", "v3", "sv2");

        assertRows(execute("SELECT * FROM %s WHERE p=?", "p1"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );


        // Ascending order

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c ASC", "p1"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c ASC", "p2"),
                   row("p2", "k3", "sv2", "v3")
        );

        // Descending order

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c DESC", "p1"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c DESC", "p2"),
                   row("p2", "k3", "sv2", "v3")
        );

        // No order with one relation

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=?", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=?", "p1", "k2"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c>=?", "p1", "k3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c =?", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c<=?", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c<=?", "p1", "k0"));

        // Ascending with one relation

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c ASC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c ASC", "p1", "k2"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c ASC", "p1", "k3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c =? ORDER BY c ASC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c ASC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c ASC", "p1", "k0"));

        // Descending with one relation

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c DESC", "p1", "k1"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c DESC", "p1", "k2"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c DESC", "p1", "k3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c =? ORDER BY c DESC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c DESC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c DESC", "p1", "k0"));

        // IN

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c IN (?, ?)", "p1", "k1", "k2"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c IN (?, ?) ORDER BY c ASC", "p1", "k1", "k2"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c IN (?, ?) ORDER BY c DESC", "p1", "k1", "k2"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testSingleClusteringReversed() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, s text, PRIMARY KEY (p, c)) WITH CLUSTERING ORDER BY (c DESC)");

        execute("INSERT INTO %s(p, c, v, s) values (?, ?, ?, ?)", "p1", "k1", "v1", "sv1");
        execute("INSERT INTO %s(p, c, v, s) values (?, ?, ?, ?)", "p1", "k2", "v2", "sv1");
        execute("INSERT INTO %s(p, c, v, s) values (?, ?, ?, ?)", "p2", "k3", "v3", "sv2");

        assertRows(execute("SELECT * FROM %s WHERE p=?", "p1"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=?", "p2"),
                   row("p2", "k3", "sv2", "v3")
        );

        // Ascending order

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c ASC", "p1"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c ASC", "p2"),
                   row("p2", "k3", "sv2", "v3")
        );

        // Descending order

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c DESC", "p1"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? ORDER BY c DESC", "p2"),
                   row("p2", "k3", "sv2", "v3")
        );

        // No order with one relation

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=?", "p1", "k1"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=?", "p1", "k2"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c>=?", "p1", "k3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c=?", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c<=?", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c<=?", "p1", "k0"));

        // Ascending with one relation

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c ASC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c ASC", "p1", "k2"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c ASC", "p1", "k3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c=? ORDER BY c ASC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c ASC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c ASC", "p1", "k0"));

        // Descending with one relation

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c DESC", "p1", "k1"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c DESC", "p1", "k2"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c>=? ORDER BY c DESC", "p1", "k3"));

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c=? ORDER BY c DESC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c DESC", "p1", "k1"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertEmpty(execute("SELECT * FROM %s WHERE p=? AND c<=? ORDER BY c DESC", "p1", "k0"));

        // IN

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c IN (?, ?)", "p1", "k1", "k2"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c IN (?, ?) ORDER BY c ASC", "p1", "k1", "k2"),
                   row("p1", "k1", "sv1", "v1"),
                   row("p1", "k2", "sv1", "v2")
        );

        assertRows(execute("SELECT * FROM %s WHERE p=? AND c IN (?, ?) ORDER BY c DESC", "p1", "k1", "k2"),
                   row("p1", "k2", "sv1", "v2"),
                   row("p1", "k1", "sv1", "v1")
        );
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testSelectBounds() throws Throwable
    {
        createTable("CREATE TABLE %s (k int, c int, v int, PRIMARY KEY (k, c))");

        for (int i = 0; i < 10; i++)
            execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", i, i);

        assertRowCount(execute("SELECT v FROM %s WHERE k = 0"), 10);

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 2 AND c <= 6"),
                   row(2), row(3), row(4), row(5), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > 2 AND c <= 6"),
                   row(3), row(4), row(5), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 2 AND c < 6"),
                   row(2), row(3), row(4), row(5));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > 2 AND c < 6"),
                   row(3), row(4), row(5));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > 2 AND c <= 6 LIMIT 2"),
                   row(3), row(4));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 2 AND c < 6 ORDER BY c DESC LIMIT 2"),
                   row(5), row(4));

        assertEmpty(execute("SELECT v FROM %s WHERE k = 0 AND c > 10"));
    }

    @Test
    public void testSelectBoundsWithVaryLengthKeyType() throws Throwable
    {
        createTable("CREATE TABLE %s (k int, c text, v int, PRIMARY KEY (k, c))");

        execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", "1", 1);
        execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", "2", 2);
        execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", "22", 3);
        execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", "3", 4);

        assertRowCount(execute("SELECT v FROM %s WHERE k = 0"), 4);

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= '2' AND c <= '3'"),
                   row(2), row(3), row(4));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > '2' AND c <= '3'"),
                   row(3), row(4));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > '1' AND c <= '2'"),
                   row(2));
    }


    @Test
    public void testSelectBoundsNotExistsInDB() throws Throwable
    {
        createTable("CREATE TABLE %s (k int, c int, v int, PRIMARY KEY (k, c))");

        for (int i = 0; i < 10; i += 3) // 0, 3, 6, 9
            execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", i, i);

        assertRowCount(execute("SELECT v FROM %s WHERE k = 0"), 4);

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 1 AND c <= 7"),
                   row(3), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > 1 AND c <= 7"),
                   row(3), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 1 AND c < 7"),
                   row(3), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > 1 AND c < 7"),
                   row(3), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > 1 AND c <= 7 LIMIT 2"),
                   row(3), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 1 AND c < 7 ORDER BY c DESC LIMIT 2"),
                   row(6), row(3));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c >= 1 AND c <= 7 ORDER BY c DESC LIMIT 2"),
                   row(6), row(3));


        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > -1 LIMIT 2"),
                   row(0), row(3));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c < 10 LIMIT 2"),
                   row(0), row(3));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c > -1 ORDER BY C DESC LIMIT 2"),
                   row(9), row(6));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c < 10 ORDER BY C DESC LIMIT 2"),
                   row(9), row(6));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testEmptyRestrictionValueWithMultipleClusteringColumns() throws Throwable
    {
        for (String options : new String[]{ "", " WITH COMPACT STORAGE" })
        {
            createTable("CREATE TABLE %s (pk blob, c1 blob, c2 blob, v blob, PRIMARY KEY (pk, c1, c2))" + options);
            execute("INSERT INTO %s (pk, c1, c2, v) VALUES (?, ?, ?, ?)", bytes("foo123"), bytes("1"), bytes("1"), bytes("1"));
            execute("INSERT INTO %s (pk, c1, c2, v) VALUES (?, ?, ?, ?)", bytes("foo123"), bytes("1"), bytes("2"), bytes("2"));

            beforeAndAfterFlush(() -> {

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('');"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 = textAsBlob('');"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) = (textAsBlob('1'), textAsBlob(''));"));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 IN (textAsBlob(''), textAsBlob('1')) AND c2 = textAsBlob('1');"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 IN (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) IN ((textAsBlob(''), textAsBlob('1')), (textAsBlob('1'), textAsBlob('1')));"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 > textAsBlob('');"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 > textAsBlob('');"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) > (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 >= textAsBlob('');"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 <= textAsBlob('');"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) <= (textAsBlob('1'), textAsBlob(''));"));
            });

            execute("INSERT INTO %s (pk, c1, c2, v) VALUES (?, ?, ?, ?)",
                    bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4"));

            beforeAndAfterFlush(() -> {
                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('');"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('') AND c2 = textAsBlob('1');"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) = (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 IN (textAsBlob(''), textAsBlob('1')) AND c2 = textAsBlob('1');"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) IN ((textAsBlob(''), textAsBlob('1')), (textAsBlob('1'), textAsBlob('1')));"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) > (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) >= (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) <= (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) < (textAsBlob(''), textAsBlob('1'));"));
            });
        }
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Check query with KEY IN clause
     * migrated from cql_tests.py:TestCQL.select_key_in_test()
     */
    @Test
    public void testSelectKeyIn() throws Throwable
    {
        createTable("CREATE TABLE %s (userid uuid PRIMARY KEY, firstname text, lastname text, age int)");

        UUID id1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID id2 = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

        execute("INSERT INTO %s (userid, firstname, lastname, age) VALUES (?, 'Frodo', 'Baggins', 32)", id1);
        execute("INSERT INTO %s (userid, firstname, lastname, age) VALUES (?, 'Samwise', 'Gamgee', 33)", id2);

        assertRowCount(execute("SELECT firstname, lastname FROM %s WHERE userid IN (?, ?)", id1, id2), 2);
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Check query with KEY IN clause for wide row tables
     * migrated from cql_tests.py:TestCQL.in_clause_wide_rows_test()
     */
    @Test
    public void testSelectKeyInForWideRows() throws Throwable
    {
        createTable("CREATE TABLE %s (k int, c int, v int, PRIMARY KEY (k, c)) WITH COMPACT STORAGE");

        for (int i = 0; i < 10; i++)
            execute("INSERT INTO %s (k, c, v) VALUES (0, ?, ?)", i, i);

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c IN (5, 2, 8)"),
                   row(2), row(5), row(8));

        createTable("CREATE TABLE %s (k int, c1 int, c2 int, v int, PRIMARY KEY (k, c1, c2)) WITH COMPACT STORAGE");

        for (int i = 0; i < 10; i++)
            execute("INSERT INTO %s (k, c1, c2, v) VALUES (0, 0, ?, ?)", i, i);

        assertEmpty(execute("SELECT v FROM %s WHERE k = 0 AND c1 IN (5, 2, 8) AND c2 = 3"));

        assertRows(execute("SELECT v FROM %s WHERE k = 0 AND c1 = 0 AND c2 IN (5, 2, 8)"),
                   row(2), row(5), row(8));
    }


    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Test select count
     * migrated from cql_tests.py:TestCQL.count_test()
     */
    @Test
    public void testSelectCount() throws Throwable
    {
        createTable(" CREATE TABLE %s (kind text, time int, value1 int, value2 int, PRIMARY KEY(kind, time))");

        execute("INSERT INTO %s (kind, time, value1, value2) VALUES ('ev1', ?, ?, ?)", 0, 0, 0);
        execute("INSERT INTO %s (kind, time, value1, value2) VALUES ('ev1', ?, ?, ?)", 1, 1, 1);
        execute("INSERT INTO %s (kind, time, value1) VALUES ('ev1', ?, ?)", 2, 2);
        execute("INSERT INTO %s (kind, time, value1, value2) VALUES ('ev1', ?, ?, ?)", 3, 3, 3);
        execute("INSERT INTO %s (kind, time, value1) VALUES ('ev1', ?, ?)", 4, 4);
        execute("INSERT INTO %s (kind, time, value1, value2) VALUES ('ev2', 0, 0, 0)");

        assertRows(execute("SELECT COUNT(*) FROM %s WHERE kind = 'ev1'"),
                   row(5L));

        assertRows(execute("SELECT COUNT(1) FROM %s WHERE kind IN ('ev1', 'ev2') AND time=0"),
                   row(2L));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Range test query from #4372
     * migrated from cql_tests.py:TestCQL.range_query_test()
     */
    @Test
    public void testRangeQuery() throws Throwable
    {
        createTable("CREATE TABLE %s (a int, b int, c int, d int, e int, f text, PRIMARY KEY (a, b, c, d, e) )");

        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 2, '2')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 1, '1')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 2, 1, '1')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 3, '3')");
        execute("INSERT INTO %s (a, b, c, d, e, f) VALUES (1, 1, 1, 1, 5, '5')");

        assertRows(execute("SELECT a, b, c, d, e, f FROM %s WHERE a = 1 AND b = 1 AND c = 1 AND d = 1 AND e >= 2"),
                   row(1, 1, 1, 1, 2, "2"),
                   row(1, 1, 1, 1, 3, "3"),
                   row(1, 1, 1, 1, 5, "5"));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Test for #4532, NPE when trying to select a slice from a composite table
     * migrated from cql_tests.py:TestCQL.bug_4532_test()
     */
    @Test
    public void testSelectSliceFromComposite() throws Throwable
    {
        createTable("CREATE TABLE %s (status ascii, ctime bigint, key ascii, nil ascii, PRIMARY KEY (status, ctime, key))");

        execute("INSERT INTO %s (status,ctime,key,nil) VALUES ('C',12345678,'key1','')");
        execute("INSERT INTO %s (status,ctime,key,nil) VALUES ('C',12345678,'key2','')");
        execute("INSERT INTO %s (status,ctime,key,nil) VALUES ('C',12345679,'key3','')");
        execute("INSERT INTO %s (status,ctime,key,nil) VALUES ('C',12345679,'key4','')");
        execute("INSERT INTO %s (status,ctime,key,nil) VALUES ('C',12345679,'key5','')");
        execute("INSERT INTO %s (status,ctime,key,nil) VALUES ('C',12345680,'key6','')");

        assertInvalid("SELECT * FROM %s WHERE ctime>=12345679 AND key='key3' AND ctime<=12345680 LIMIT 3;");
        assertInvalid("SELECT * FROM %s WHERE ctime=12345679  AND key='key3' AND ctime<=12345680 LIMIT 3");
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Test for #4716 bug and more generally for good behavior of ordering,
     * migrated from cql_tests.py:TestCQL.reversed_compact_test()
     */
    @Test
    public void testReverseCompact() throws Throwable
    {
        createTable("CREATE TABLE %s ( k text, c int, v int, PRIMARY KEY (k, c) ) WITH COMPACT STORAGE AND CLUSTERING ORDER BY (c DESC)");

        for (int i = 0; i < 10; i++)
            execute("INSERT INTO %s (k, c, v) VALUES ('foo', ?, ?)", i, i);

        assertRows(execute("SELECT c FROM %s WHERE c > 2 AND c < 6 AND k = 'foo'"),
                   row(5), row(4), row(3));

        assertRows(execute("SELECT c FROM %s WHERE c >= 2 AND c <= 6 AND k = 'foo'"),
                   row(6), row(5), row(4), row(3), row(2));

        assertRows(execute("SELECT c FROM %s WHERE c > 2 AND c < 6 AND k = 'foo' ORDER BY c ASC"),
                   row(3), row(4), row(5));

        assertRows(execute("SELECT c FROM %s WHERE c >= 2 AND c <= 6 AND k = 'foo' ORDER BY c ASC"),
                   row(2), row(3), row(4), row(5), row(6));

        assertRows(execute("SELECT c FROM %s WHERE c > 2 AND c < 6 AND k = 'foo' ORDER BY c DESC"),
                   row(5), row(4), row(3));

        assertRows(execute("SELECT c FROM %s WHERE c >= 2 AND c <= 6 AND k = 'foo' ORDER BY c DESC"),
                   row(6), row(5), row(4), row(3), row(2));

        createTable("CREATE TABLE %s ( k text, c int, v int, PRIMARY KEY (k, c) ) WITH COMPACT STORAGE");

        for (int i = 0; i < 10; i++)
            execute("INSERT INTO %s(k, c, v) VALUES ('foo', ?, ?)", i, i);

        assertRows(execute("SELECT c FROM %s WHERE c > 2 AND c < 6 AND k = 'foo'"),
                   row(3), row(4), row(5));

        assertRows(execute("SELECT c FROM %s WHERE c >= 2 AND c <= 6 AND k = 'foo'"),
                   row(2), row(3), row(4), row(5), row(6));

        assertRows(execute("SELECT c FROM %s WHERE c > 2 AND c < 6 AND k = 'foo' ORDER BY c ASC"),
                   row(3), row(4), row(5));

        assertRows(execute("SELECT c FROM %s WHERE c >= 2 AND c <= 6 AND k = 'foo' ORDER BY c ASC"),
                   row(2), row(3), row(4), row(5), row(6));

        assertRows(execute("SELECT c FROM %s WHERE c > 2 AND c < 6 AND k = 'foo' ORDER BY c DESC"),
                   row(5), row(4), row(3));

        assertRows(execute("SELECT c FROM %s WHERE c >= 2 AND c <= 6 AND k = 'foo' ORDER BY c DESC"),
                   row(6), row(5), row(4), row(3), row(2));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Migrated from cql_tests.py:TestCQL.bug_4882_test()
     */
    @Test
    public void testDifferentOrdering() throws Throwable
    {
        createTable(" CREATE TABLE %s ( k int, c1 int, c2 int, v int, PRIMARY KEY (k, c1, c2) ) WITH CLUSTERING ORDER BY (c1 ASC, c2 DESC)");

        execute("INSERT INTO %s (k, c1, c2, v) VALUES (0, 0, 0, 0)");
        execute("INSERT INTO %s (k, c1, c2, v) VALUES (0, 1, 1, 1)");
        execute("INSERT INTO %s (k, c1, c2, v) VALUES (0, 0, 2, 2)");
        execute("INSERT INTO %s (k, c1, c2, v) VALUES (0, 1, 3, 3)");

        assertRows(execute("select * from %s where k = 0 limit 1"),
                   row(0, 0, 2, 2));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Migrated from cql_tests.py:TestCQL.multi_in_compact_non_composite_test()
     */
    @Test
    public void testMultiSelectsNonCompositeCompactStorage() throws Throwable
    {
        createTable("CREATE TABLE %s (key int, c int, v int, PRIMARY KEY (key, c)) WITH COMPACT STORAGE");

        execute("INSERT INTO %s (key, c, v) VALUES (0, 0, 0)");
        execute("INSERT INTO %s (key, c, v) VALUES (0, 1, 1)");
        execute("INSERT INTO %s (key, c, v) VALUES (0, 2, 2)");

        assertRows(execute("SELECT * FROM %s WHERE key=0 AND c IN (0, 2)"),
                   row(0, 0, 0), row(0, 2, 2));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Migrated from cql_tests.py:TestCQL.ticket_5230_test()
     */
    @Test
    public void testMultipleClausesOnPrimaryKey() throws Throwable
    {
        createTable("CREATE TABLE %s (key text, c text, v text, PRIMARY KEY(key, c))");

        execute("INSERT INTO %s (key, c, v) VALUES ('foo', '1', '1')");
        execute("INSERT INTO %s(key, c, v) VALUES ('foo', '2', '2')");
        execute("INSERT INTO %s(key, c, v) VALUES ('foo', '3', '3')");

        assertRows(execute("SELECT c FROM %s WHERE key = 'foo' AND c IN ('1', '2')"),
                   row("1"), row("2"));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Migrated from cql_tests.py:TestCQL.bug_5404()
     */
    @Test
    public void testSelectWithToken() throws Throwable
    {
        createTable("CREATE TABLE %s (key text PRIMARY KEY)");

        // We just want to make sure this doesn 't NPE server side
        assertInvalid("select * from %s where token(key) > token(int(3030343330393233)) limit 1");
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Migrated from cql_tests.py:TestCQL.clustering_order_and_functions_test()
     */
    @Test
    public void testFunctionsWithClusteringDesc() throws Throwable
    {
        createTable("CREATE TABLE %s ( k int, t timeuuid, PRIMARY KEY (k, t) ) WITH CLUSTERING ORDER BY (t DESC)");

        for (int i = 0; i < 5; i++)
            execute("INSERT INTO %s (k, t) VALUES (?, now())", i);

        execute("SELECT dateOf(t) FROM %s");
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Test for #7105 bug,
     * migrated from cql_tests.py:TestCQL.clustering_order_in_test()
     */
    @Test
    public void testClusteringOrder() throws Throwable
    {
        createTable("CREATE TABLE %s (a int, b int, c int, PRIMARY KEY ((a, b), c) ) with clustering order by (c desc)");

        execute("INSERT INTO %s (a, b, c) VALUES (1, 2, 3)");
        execute("INSERT INTO %s (a, b, c) VALUES (4, 5, 6)");

        assertRows(execute("SELECT * FROM %s WHERE a=1 AND b=2 AND c IN (3)"),
                   row(1, 2, 3));
        assertRows(execute("SELECT * FROM %s WHERE a=1 AND b=2 AND c IN (3, 4)"),
                   row(1, 2, 3));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest

    /**
     * Test for #7105 bug,
     * SELECT with IN on final column of composite and compound primary key fails
     * migrated from cql_tests.py:TestCQL.bug7105_test()
     */
    @Test
    public void testSelectInFinalColumn() throws Throwable
    {
        createTable("CREATE TABLE %s (a int, b int, c int, d int, PRIMARY KEY (a, b))");

        execute("INSERT INTO %s (a, b, c, d) VALUES (1, 2, 3, 3)");
        execute("INSERT INTO %s (a, b, c, d) VALUES (1, 4, 6, 5)");

        assertRows(execute("SELECT * FROM %s WHERE a=1 AND b=2 ORDER BY b DESC"),
                   row(1, 2, 3, 3));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testAlias() throws Throwable
    {
        createTable("CREATE TABLE %s (id int PRIMARY KEY, name text)");

        for (int i = 0; i < 5; i++)
            execute("INSERT INTO %s (id, name) VALUES (?, ?) USING TTL 10 AND TIMESTAMP 0", i, Integer.toString(i));

        assertInvalidMessage("Aliases aren't allowed in the where clause",
                             "SELECT id AS user_id, name AS user_name FROM %s WHERE user_id = 0");

        // test that select throws a meaningful exception for aliases in order by clause
        assertInvalidMessage("Aliases are not allowed in order by clause",
                             "SELECT id AS user_id, name AS user_name FROM %s WHERE id IN (0) ORDER BY user_name");
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testPKQueryWithValueOver64K() throws Throwable
    {
        createTable("CREATE TABLE %s (a text, b text, PRIMARY KEY (a, b))");

        assertInvalidThrow(InvalidRequestException.class,
                           "SELECT * FROM %s WHERE a = ?", new String(TOO_BIG.array()));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testCKQueryWithValueOver64K() throws Throwable
    {
        createTable("CREATE TABLE %s (a text, b text, PRIMARY KEY (a, b))");

        execute("SELECT * FROM %s WHERE a = 'foo' AND b = ?", new String(TOO_BIG.array()));
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testClusteringOrderWithSlice() throws Throwable
    {
        for (String compactOption : new String[]{ "", " COMPACT STORAGE AND" })
        {
            // non-compound, ASC order
            createTable("CREATE TABLE %s (a text, b int, PRIMARY KEY (a, b)) WITH" +
                        compactOption +
                        " CLUSTERING ORDER BY (b ASC)");

            execute("INSERT INTO %s (a, b) VALUES ('a', 2)");
            execute("INSERT INTO %s (a, b) VALUES ('a', 3)");
            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0"),
                       row("a", 2),
                       row("a", 3));

            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0 ORDER BY b DESC"),
                       row("a", 3),
                       row("a", 2));

            // non-compound, DESC order
            createTable("CREATE TABLE %s (a text, b int, PRIMARY KEY (a, b)) WITH" +
                        compactOption +
                        " CLUSTERING ORDER BY (b DESC)");

            execute("INSERT INTO %s (a, b) VALUES ('a', 2)");
            execute("INSERT INTO %s (a, b) VALUES ('a', 3)");
            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0"),
                       row("a", 3),
                       row("a", 2));

            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0 ORDER BY b ASC"),
                       row("a", 2),
                       row("a", 3));

            // compound, first column DESC order
            createTable("CREATE TABLE %s (a text, b int, c int, PRIMARY KEY (a, b, c)) WITH" +
                        compactOption +
                        " CLUSTERING ORDER BY (b DESC)"
            );

            execute("INSERT INTO %s (a, b, c) VALUES ('a', 2, 4)");
            execute("INSERT INTO %s (a, b, c) VALUES ('a', 3, 5)");
            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0"),
                       row("a", 3, 5),
                       row("a", 2, 4));

            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0 ORDER BY b ASC"),
                       row("a", 2, 4),
                       row("a", 3, 5));

            // compound, mixed order
            createTable("CREATE TABLE %s (a text, b int, c int, PRIMARY KEY (a, b, c)) WITH" +
                        compactOption +
                        " CLUSTERING ORDER BY (b ASC, c DESC)"
            );

            execute("INSERT INTO %s (a, b, c) VALUES ('a', 2, 4)");
            execute("INSERT INTO %s (a, b, c) VALUES ('a', 3, 5)");
            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0"),
                       row("a", 2, 4),
                       row("a", 3, 5));

            assertRows(execute("SELECT * FROM %s WHERE a = 'a' AND b > 0 ORDER BY b ASC"),
                       row("a", 2, 4),
                       row("a", 3, 5));
        }
    }


    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testEmptyRestrictionValue() throws Throwable
    {
        for (String options : new String[]{ "", " WITH COMPACT STORAGE" })
        {
            createTable("CREATE TABLE %s (pk blob, c blob, v blob, PRIMARY KEY ((pk), c))" + options);
            execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                    bytes("foo123"), bytes("1"), bytes("1"));
            execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                    bytes("foo123"), bytes("2"), bytes("2"));

            beforeAndAfterFlush(() -> {

                assertInvalidMessage("Key may not be empty", "SELECT * FROM %s WHERE pk = textAsBlob('');");
                assertInvalidMessage("Key may not be empty", "SELECT * FROM %s WHERE pk IN (textAsBlob(''), textAsBlob('1'));");

                assertInvalidMessage("Key may not be empty",
                                     "INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                                     EMPTY_BYTE_BUFFER, bytes("2"), bytes("2"));

                // Test clustering columns restrictions
                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c = textAsBlob('');"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) = (textAsBlob(''));"));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c IN (textAsBlob(''), textAsBlob('1'));"),
                           row(bytes("foo123"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) IN ((textAsBlob('')), (textAsBlob('1')));"),
                           row(bytes("foo123"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c > textAsBlob('');"),
                           row(bytes("foo123"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) > (textAsBlob(''));"),
                           row(bytes("foo123"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c >= textAsBlob('');"),
                           row(bytes("foo123"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("2"), bytes("2")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) >= (textAsBlob(''));"),
                           row(bytes("foo123"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), bytes("2"), bytes("2")));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c <= textAsBlob('');"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) <= (textAsBlob(''));"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c < textAsBlob('');"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) < (textAsBlob(''));"));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c > textAsBlob('') AND c < textAsBlob('');"));
            });

            if (options.contains("COMPACT"))
            {
                assertInvalidMessage("Invalid empty or null value for column c",
                                     "INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                                     bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4"));
            }
            else
            {
                execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                        bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4"));

                beforeAndAfterFlush(() -> {
                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c = textAsBlob('');"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) = (textAsBlob(''));"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c IN (textAsBlob(''), textAsBlob('1'));"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")),
                               row(bytes("foo123"), bytes("1"), bytes("1")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) IN ((textAsBlob('')), (textAsBlob('1')));"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")),
                               row(bytes("foo123"), bytes("1"), bytes("1")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c > textAsBlob('');"),
                               row(bytes("foo123"), bytes("1"), bytes("1")),
                               row(bytes("foo123"), bytes("2"), bytes("2")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) > (textAsBlob(''));"),
                               row(bytes("foo123"), bytes("1"), bytes("1")),
                               row(bytes("foo123"), bytes("2"), bytes("2")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c >= textAsBlob('');"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")),
                               row(bytes("foo123"), bytes("1"), bytes("1")),
                               row(bytes("foo123"), bytes("2"), bytes("2")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) >= (textAsBlob(''));"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")),
                               row(bytes("foo123"), bytes("1"), bytes("1")),
                               row(bytes("foo123"), bytes("2"), bytes("2")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c <= textAsBlob('');"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) <= (textAsBlob(''));"),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));

                    assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c < textAsBlob('');"));

                    assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c) < (textAsBlob(''));"));

                    assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c >= textAsBlob('') AND c < textAsBlob('');"));
                });
            }

            // Test restrictions on non-primary key value
            assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND v = textAsBlob('') ALLOW FILTERING;"));

            execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                    bytes("foo123"), bytes("3"), EMPTY_BYTE_BUFFER);

            beforeAndAfterFlush(() -> {
                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND v = textAsBlob('') ALLOW FILTERING;"),
                           row(bytes("foo123"), bytes("3"), EMPTY_BYTE_BUFFER));
            });
        }
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testEmptyRestrictionValueWithOrderBy() throws Throwable
    {
        for (String options : new String[]{ "",
                                            " WITH COMPACT STORAGE",
                                            " WITH CLUSTERING ORDER BY (c DESC)",
                                            " WITH COMPACT STORAGE AND CLUSTERING ORDER BY (c DESC)" })
        {
            String orderingClause = options.contains("ORDER") ? "" : "ORDER BY c DESC";

            createTable("CREATE TABLE %s (pk blob, c blob, v blob, PRIMARY KEY ((pk), c))" + options);
            execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                    bytes("foo123"),
                    bytes("1"),
                    bytes("1"));
            execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                    bytes("foo123"),
                    bytes("2"),
                    bytes("2"));

            beforeAndAfterFlush(() -> {

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c > textAsBlob('')" + orderingClause),
                           row(bytes("foo123"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c >= textAsBlob('')" + orderingClause),
                           row(bytes("foo123"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1")));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c < textAsBlob('')" + orderingClause));

                assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c <= textAsBlob('')" + orderingClause));
            });

            if (options.contains("COMPACT"))
            {
                assertInvalidMessage("Invalid empty or null value for column c",
                                     "INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                                     bytes("foo123"),
                                     EMPTY_BYTE_BUFFER,
                                     bytes("4"));
            }
            else
            {
                execute("INSERT INTO %s (pk, c, v) VALUES (?, ?, ?)",
                        bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4"));

                beforeAndAfterFlush(() -> {

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c IN (textAsBlob(''), textAsBlob('1'))" + orderingClause),
                               row(bytes("foo123"), bytes("1"), bytes("1")),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c > textAsBlob('')" + orderingClause),
                               row(bytes("foo123"), bytes("2"), bytes("2")),
                               row(bytes("foo123"), bytes("1"), bytes("1")));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c >= textAsBlob('')" + orderingClause),
                               row(bytes("foo123"), bytes("2"), bytes("2")),
                               row(bytes("foo123"), bytes("1"), bytes("1")),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));

                    assertEmpty(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c < textAsBlob('')" + orderingClause));

                    assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c <= textAsBlob('')" + orderingClause),
                               row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("4")));
                });
            }
        }
    }

    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    /**
     * Test for the bug from #4760 and #4759,
     * migrated from cql_tests.py:TestCQL.reversed_compact_multikey_test()
     */
    @Test
    public void testReversedCompactMultikey() throws Throwable
    {
        createTable("CREATE TABLE %s (key text, c1 int, c2 int, value text, PRIMARY KEY(key, c1, c2) ) WITH COMPACT STORAGE AND CLUSTERING ORDER BY(c1 DESC, c2 DESC)");

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                execute("INSERT INTO %s (key, c1, c2, value) VALUES ('foo', ?, ?, 'bar')", i, j);

        // Equalities
        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 = 1"),
                   row(1, 2), row(1, 1), row(1, 0));

//        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 = 1 ORDER BY c1 ASC, c2 ASC"),
//                   row(1, 0), row(1, 1), row(1, 2));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 = 1 ORDER BY c1 DESC, c2 DESC"),
                   row(1, 2), row(1, 1), row(1, 0));

        // GT
        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 > 1"),
                   row(2, 2), row(2, 1), row(2, 0));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 > 1 ORDER BY c1 ASC, c2 ASC"),
                   row(2, 0), row(2, 1), row(2, 2));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 > 1 ORDER BY c1 DESC, c2 DESC"),
                   row(2, 2), row(2, 1), row(2, 0));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 >= 1"),
                   row(2, 2), row(2, 1), row(2, 0), row(1, 2), row(1, 1), row(1, 0));

//        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 >= 1 ORDER BY c1 ASC, c2 ASC"),
//                   row(1, 0), row(1, 1), row(1, 2), row(2, 0), row(2, 1), row(2, 2));

//        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 >= 1 ORDER BY c1 ASC"),
//                   row(1, 0), row(1, 1), row(1, 2), row(2, 0), row(2, 1), row(2, 2));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 >= 1 ORDER BY c1 DESC, c2 DESC"),
                   row(2, 2), row(2, 1), row(2, 0), row(1, 2), row(1, 1), row(1, 0));

        // LT
        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 < 1"),
                   row(0, 2), row(0, 1), row(0, 0));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 < 1 ORDER BY c1 ASC, c2 ASC"),
                   row(0, 0), row(0, 1), row(0, 2));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 < 1 ORDER BY c1 DESC, c2 DESC"),
                   row(0, 2), row(0, 1), row(0, 0));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 <= 1"),
                   row(1, 2), row(1, 1), row(1, 0), row(0, 2), row(0, 1), row(0, 0));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 <= 1 ORDER BY c1 ASC, c2 ASC"),
                   row(0, 0), row(0, 1), row(0, 2), row(1, 0), row(1, 1), row(1, 2));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 <= 1 ORDER BY c1 ASC"),
                   row(0, 0), row(0, 1), row(0, 2), row(1, 0), row(1, 1), row(1, 2));

        assertRows(execute("SELECT c1, c2 FROM %s WHERE key='foo' AND c1 <= 1 ORDER BY c1 DESC, c2 DESC"),
                   row(1, 2), row(1, 1), row(1, 0), row(0, 2), row(0, 1), row(0, 0));
    }


    // copied from org.apache.cassandra.cql3.validation.operations.SelectTest
    @Test
    public void testEmptyRestrictionValueWithMultipleClusteringColumnsAndOrderBy() throws Throwable
    {
        for (String options : new String[] { "",
                                             " WITH COMPACT STORAGE",
                                             " WITH CLUSTERING ORDER BY (c1 DESC, c2 DESC)",
                                             " WITH COMPACT STORAGE AND CLUSTERING ORDER BY (c1 DESC, c2 DESC)"})
        {
            String orderingClause = options.contains("ORDER") ? "" : "ORDER BY c1 DESC, c2 DESC" ;

            createTable("CREATE TABLE %s (pk blob, c1 blob, c2 blob, v blob, PRIMARY KEY (pk, c1, c2))" + options);
            execute("INSERT INTO %s (pk, c1, c2, v) VALUES (?, ?, ?, ?)", bytes("foo123"), bytes("1"), bytes("1"), bytes("1"));
            execute("INSERT INTO %s (pk, c1, c2, v) VALUES (?, ?, ?, ?)", bytes("foo123"), bytes("1"), bytes("2"), bytes("2"));

            beforeAndAfterFlush(() -> {

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 > textAsBlob('')" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 > textAsBlob('')" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) > (textAsBlob(''), textAsBlob('1'))" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 = textAsBlob('1') AND c2 >= textAsBlob('')" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));
            });

            execute("INSERT INTO %s (pk, c1, c2, v) VALUES (?, ?, ?, ?)",
                    bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4"));

            beforeAndAfterFlush(() -> {

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND c1 IN (textAsBlob(''), textAsBlob('1')) AND c2 = textAsBlob('1')" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) IN ((textAsBlob(''), textAsBlob('1')), (textAsBlob('1'), textAsBlob('1')))" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) > (textAsBlob(''), textAsBlob('1'))" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")));

                assertRows(execute("SELECT * FROM %s WHERE pk = textAsBlob('foo123') AND (c1, c2) >= (textAsBlob(''), textAsBlob('1'))" + orderingClause),
                           row(bytes("foo123"), bytes("1"), bytes("2"), bytes("2")),
                           row(bytes("foo123"), bytes("1"), bytes("1"), bytes("1")),
                           row(bytes("foo123"), EMPTY_BYTE_BUFFER, bytes("1"), bytes("4")));
            });
        }
    }


}
