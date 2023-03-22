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
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.ning.compress.lzf.LZFInputStream;
import org.apache.cassandra.io.util.DataInputPlus;
import org.apache.cassandra.io.util.DataOutputStreamPlus;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.streaming.messages.StreamMessage;
import org.apache.cassandra.utils.JVMStabilityInspector;

/**
 * Receiving streamed Rocksdb key value pairs according to stream plan, which is multiple token
 * ranges of a column family.
 */
public class RocksDBIncomingMessage extends StreamMessage
{
    public static Serializer<RocksDBIncomingMessage> serializer = new Serializer<RocksDBIncomingMessage>()
    {
        @SuppressWarnings("resource")
        public RocksDBIncomingMessage deserialize(ReadableByteChannel in, int version, StreamSession session) throws IOException
        {
            DataInputPlus input = new DataInputPlus.DataInputStreamPlus(Channels.newInputStream(in));
            RocksDBMessageHeader header = RocksDBMessageHeader.SERIALIZER.deserialize(input);
            RocksDBStreamReader reader = new RocksDBStreamReader(header, session);

            try
            {
                reader.read(new DataInputPlus.DataInputStreamPlus(new LZFInputStream(Channels.newInputStream(in))));
                return new RocksDBIncomingMessage(header, reader.getTotalIncomingBytes());
            }
            catch (Throwable t)
            {
                JVMStabilityInspector.inspectThrowable(t);
                throw t;
            }
        }

        public void serialize(RocksDBIncomingMessage message, DataOutputStreamPlus out, int version, StreamSession session) throws IOException
        {
            throw new UnsupportedOperationException("Not allowed to call serialize on an incoming file");
        }
    };

    public final RocksDBMessageHeader header;
    public final long totalIncomingBytes;
    public RocksDBIncomingMessage(RocksDBMessageHeader header, long totalIncomingBytes)
    {
        super(Type.ROCKSFILE);
        this.header = header;
        this.totalIncomingBytes = totalIncomingBytes;
    }

    @Override
    public String toString()
    {
        return "Rocskdb File (" + header + ")";
    }
}
