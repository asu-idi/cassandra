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

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Test;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.io.util.DataInputBuffer;
import org.apache.cassandra.io.util.DataOutputBuffer;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.RocksDBUtils;
import org.apache.cassandra.utils.ByteBufferUtil;

public class RocksDBStreamChecksumTest extends RocksDBStreamTestBase
{
    private static final int BUFFER_SIZE = 100;

    @Test(expected = StreamingDigestMismatchException.class)
    public void testExceptionThrownWhenDataCorrupted() throws Throwable
    {
        // Create table one and insert one pair for streaming.
        createTable("CREATE TABLE %s (p ASCII, v ASCII, PRIMARY KEY (p))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        IPartitioner tokenPartioner = cfs.metadata.partitioner;
        execute("INSERT INTO %s(p, v) values (?, ?)", "p", "v");

        // Write Rocksdb entries into stream.
        RocksDBStreamWriter writer = new RocksDBStreamWriter(RocksDBEngine.getRocksDBCF(cfs.metadata.cfId),
                                                                          new Range(RocksDBUtils.getMinToken(tokenPartioner),
                                                                                    RocksDBUtils.getMaxToken(tokenPartioner)),
                                                             createDummySession(), 0);
        DataOutputBuffer out = new DataOutputBuffer(BUFFER_SIZE);
        writer.write(out);
        byte[] array = ByteBufferUtil.getArray(out.buffer());

        // Flip one bit of the checksum, and checksum is the last 32 bytes of the stream.
        int length = out.getLength();
        array[length - 1] = (byte) (array[length - 1] ^ (byte)(1));

        // Create second table to read from stream.
        createTable("CREATE TABLE %s (p TEXT, v TEXT, PRIMARY KEY (p))");

        // Read Rocksdb entries from stream and ingest into table 2.
        cfs = getCurrentColumnFamilyStore();
        RocksDBStreamReader reader = new RocksDBStreamReader(new RocksDBMessageHeader(cfs.metadata.cfId, 0), createDummySession());
        DataInputBuffer in = new DataInputBuffer(ByteBuffer.wrap(array), false);

        // StreamingDigestMismatchException should be thrown upon this function call.
        reader.read(in);
    }
}
