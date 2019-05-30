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

import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import org.apache.cassandra.Util;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.cql3.ColumnIdentifier;
import org.apache.cassandra.db.BufferDecoratedKey;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.rows.BTreeRow;
import org.apache.cassandra.db.rows.BufferCell;
import org.apache.cassandra.db.rows.Row;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.rocksdb.encoding.value.RowValueEncoder;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.openjdk.jmh.annotations.TearDown;
import org.rocksdb.IndexType;
import org.rocksdb.RocksDBException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class RocksDBCFTest extends RocksDBTestBase
{
    final DecoratedKey dk = Util.dk("test_key");

    @Before
    public void setUp()
    {
        RocksDBConfigs.NUM_SHARD = 10;
    }

    @TearDown
    public void tearDown()
    {
        RocksDBConfigs.NUM_SHARD = 1;
    }

    @Test
    public void testMerge() throws RocksDBException
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);
        byte[] key = "test_key".getBytes();
        byte[] value = encodeValue(cfs, "test_value");
        rocksDBCF.merge(dk, key, value);
        assertArrayEquals(value, rocksDBCF.get(dk, key));
    }

    private byte[] encodeValue(ColumnFamilyStore cfs, String value)
    {
        Row.Builder builder = BTreeRow.sortedBuilder();
        ColumnDefinition v = cfs.metadata.getColumnDefinition(ColumnIdentifier.getInterned("v", true));
        BufferCell cell = BufferCell.live(cfs.metadata, v, 0, ByteBuffer.wrap(value.getBytes()));
        builder.addCell(cell);
        return RowValueEncoder.encode(cfs.metadata, builder.build());
    }

    @Test
    public void testDeleteRange() throws RocksDBException
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);

        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();
        byte[] value = encodeValue(cfs, "test_value");

        rocksDBCF.merge(dk, a, value);
        rocksDBCF.merge(dk, b, value);
        rocksDBCF.merge(dk, c, value);
        rocksDBCF.merge(dk, d, value);

        rocksDBCF.deleteRange(b, d);
        rocksDBCF.compactRange();
        assertArrayEquals(value, rocksDBCF.get(dk, a));
        assertNull(rocksDBCF.get(dk, b));
        assertNull(rocksDBCF.get(dk, c));
        assertArrayEquals(value, rocksDBCF.get(dk, d));
    }

    @Test
    public void testTruncate() throws RocksDBException
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);

        byte[] key = "test_key".getBytes();
        byte[] value = encodeValue(cfs, "test_value");

        rocksDBCF.merge(dk, key, value);
        assertArrayEquals(value, rocksDBCF.get(dk, key));

        rocksDBCF.truncate();

        rocksDBCF.compactRange();
        assertNull(rocksDBCF.get(dk, key));
    }

    @Test
    public void testClose() throws RocksDBException
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);

        byte[] key = "test_key".getBytes();
        byte[] value = encodeValue(cfs, "test_value");

        rocksDBCF.merge(dk, key, value);

        assertArrayEquals(value, rocksDBCF.get(dk, key));

        cfs.engine.close(cfs);
    }

    @Test
    public void testGetPropertyAfterClose() throws RocksDBException
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);

        cfs.engine.close(cfs);

        // after close() is called, getProperty should return empty ArrayList
        assertTrue(rocksDBCF.getProperty("rocksdb.estimate-pending-compaction-bytes").isEmpty());
    }

    @Test
    public void testDumpPrefix() throws Exception
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        RocksDBCF rocksDBCF = getCurrentRocksDBCF();

        rocksDBCF.merge(dk, "test_key1".getBytes(), "test_value11".getBytes());
        rocksDBCF.merge(dk, "test_key1".getBytes(), "test_value12".getBytes());
        rocksDBCF.merge(dk, "test_key2".getBytes(), "test_value2".getBytes());

        String dump = rocksDBCF.dumpPrefix(dk, "test_key".getBytes(), Integer.MAX_VALUE);
        assertEquals(2, dump.split("\n").length);
        String dumpLimited = rocksDBCF.dumpPrefix(dk, "test_key".getBytes(), 1);
        assertEquals(1, dumpLimited.split("\n").length);
    }

    @Test
    public void testGetCorrectRocksdbInstanceWithBoundaryHashValue() throws Exception
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBCF rocksDBCF = getCurrentRocksDBCF();
        //token 2147483648L is hand picked to make Longs.hashCode(token) == Integer.MIN_VALUE
        BufferDecoratedKey decoratedKey = new BufferDecoratedKey(new Murmur3Partitioner.LongToken(2147483648L), ByteBufferUtil.bytes(0));
        assertEquals(Integer.MIN_VALUE, decoratedKey.getToken().hashCode());
        byte[] value = encodeValue(cfs, "test_value");
        rocksDBCF.merge(decoratedKey, "a".getBytes(), value);
        assertArrayEquals(value, rocksDBCF.get(decoratedKey, "a".getBytes()));
    }

    @Test
    public void testIndexReadWrite() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();

        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);
        byte[] key = "test_key".getBytes();
        byte[] value = encodeValue(cfs, "test_value");
        rocksDBCF.merge(RocksCFName.INDEX, dk, key, value);
        assertArrayEquals(value, rocksDBCF.get(RocksCFName.INDEX, dk, key));
    }
}
