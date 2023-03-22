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

import java.util.Arrays;

import org.junit.Test;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.io.util.DataInputBuffer;
import org.apache.cassandra.io.util.DataOutputBuffer;
import org.apache.cassandra.rocksdb.RocksCFName;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.RocksDBUtils;
import org.apache.cassandra.utils.ByteBufferUtil;

import static org.apache.cassandra.Util.expectException;

public class RocksDBStreamWriterAndReaderTest extends RocksDBStreamTestBase
{
    private static final int BUFFER_SIZE = 1024 * 1024;

    @Test
    public void testStreamTableLocally() throws Throwable
    {
        int numberOfKeys = 1000;

        // Create table one and insert some data for streaming.
        createTable("CREATE TABLE %s (p TEXT, v TEXT, PRIMARY KEY (p))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        IPartitioner tokenPartioner = cfs.metadata.partitioner;
        for (int i = 0; i < numberOfKeys; i ++)
        {
            execute("INSERT INTO %s(p, v) values (?, ?)", "p" + i, "v" + i);
        }

        // Create an entry to verify the merge.
        execute("INSERT INTO %s(p, v) values (?, ?)", "merge", "old");
        assertRows(execute("SELECT v FROM %s WHERE p=?", "merge"), row("old"));

        // Write Rocksdb entries into stream.
        RocksDBStreamWriter writer = new RocksDBStreamWriter(RocksDBEngine.getRocksDBCF(cfs.metadata.cfId),
                                                                          new Range(RocksDBUtils.getMinToken(tokenPartioner),
                                                                                    RocksDBUtils.getMaxToken(tokenPartioner)),
                                                             createDummySession(), 0);
        DataOutputBuffer out = new DataOutputBuffer(BUFFER_SIZE);
        writer.write(out);

        // Create second table and verifies its emptiness.
        createTable("CREATE TABLE %s (p TEXT, v TEXT, PRIMARY KEY (p))");
        for (int i = 0; i < numberOfKeys; i ++)
        {
            assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i));
        }

        // Create an entry to verify the merge.
        execute("INSERT INTO %s(p, v) values (?, ?)", "merge", "new");
        assertRows(execute("SELECT v FROM %s WHERE p=?", "merge"), row("new"));

        // Read Rocksdb entries from stream and ingest into table 2.
        cfs = getCurrentColumnFamilyStore();
        RocksDBStreamReader reader = new RocksDBStreamReader(new RocksDBMessageHeader(cfs.metadata.cfId, 0), createDummySession());
        DataInputBuffer in = new DataInputBuffer(out.buffer(), false);
        reader.read(in);

        // Verifies all data are streamed.
        for (int i = 0; i < numberOfKeys; i ++)
        {
            assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i), row("v" + i));
        }

        // Verifies merge respect timestamp while streaming.
        assertRows(execute("SELECT v FROM %s WHERE p=?", "merge"), row("new"));
    }

    @Test(expected = StreamingShardDuplicateException.class)
    public void testStreamDuplicateShardId() throws Throwable
    {
        int numberOfKeys = 1000;

        // Create table one and insert some data for streaming.
        createTable("CREATE TABLE %s (p TEXT, v TEXT, PRIMARY KEY (p))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        IPartitioner tokenPartioner = cfs.metadata.partitioner;
        for (int i = 0; i < numberOfKeys; i ++)
        {
            execute("INSERT INTO %s(p, v) values (?, ?)", "p" + i, "v" + i);
        }

        // Create an entry to verify the merge.
        execute("INSERT INTO %s(p, v) values (?, ?)", "merge", "old");
        assertRows(execute("SELECT v FROM %s WHERE p=?", "merge"), row("old"));

        // Write Rocksdb entries into stream.
        RocksDBStreamWriter writer = new RocksDBStreamWriter(RocksDBEngine.getRocksDBCF(cfs.metadata.cfId),
                                                             new Range(RocksDBUtils.getMinToken(tokenPartioner),
                                                                       RocksDBUtils.getMaxToken(tokenPartioner)),
                                                             createDummySession(), 0);
        DataOutputBuffer out = new DataOutputBuffer(BUFFER_SIZE);
        out.write(ByteBufferUtil.bytes(0).array());
        for (RocksCFName rocksCFName : RocksCFName.NEED_STREAM)
        {
            out.write(ByteBufferUtil.bytes(rocksCFName.getId()).array());
            out.write(RocksDBStreamUtils.EOF);
        }

        writer.write(out);

        // Read Rocksdb entries from stream and ingest into table 2.
        cfs = getCurrentColumnFamilyStore();
        RocksDBStreamReader reader = new RocksDBStreamReader(new RocksDBMessageHeader(cfs.metadata.cfId, 0), createDummySession());
        DataInputBuffer in = new DataInputBuffer(out.buffer(), false);
        reader.read(in);
    }
}
