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

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import org.apache.cassandra.io.util.DataInputBuffer;
import org.apache.cassandra.io.util.DataOutputBuffer;

import static org.junit.Assert.assertEquals;

public class RocksDBMessageHeaderTest
{
    @Test
    public void testSerializeAndDeserialize() throws IOException
    {
        RocksDBMessageHeader header = new RocksDBMessageHeader(UUID.randomUUID(), 1);
        DataOutputBuffer out = new DataOutputBuffer(1024);
        RocksDBMessageHeader.SERIALIZER.seriliaze(header, out);
        DataInputBuffer in = new DataInputBuffer(out.buffer(), false);
        RocksDBMessageHeader deserialized = RocksDBMessageHeader.SERIALIZER.deserialize(in);
        assertEquals(header, deserialized);
    }

    @Test(expected = RocksDBStreamingScheamVersionMismatchException.class)
    public void testShouldDetectFormatVersionMismatch() throws Exception{
        DataOutputBuffer out = new DataOutputBuffer(1024);
        out.writeInt(RocksDBMessageHeader.RocksDBMessageHeaderSerializer.CURRENT_STREAMING_SCHEMA_VERSION + 1);
        DataInputBuffer in = new DataInputBuffer(out.buffer(), false);
        RocksDBMessageHeader deserialized = RocksDBMessageHeader.SERIALIZER.deserialize(in);
    }
}
