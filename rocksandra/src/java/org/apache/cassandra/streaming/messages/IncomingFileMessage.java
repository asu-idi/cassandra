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
package org.apache.cassandra.streaming.messages;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;

import org.apache.cassandra.io.sstable.SSTableMultiWriter;
import org.apache.cassandra.io.util.DataInputPlus;
import org.apache.cassandra.io.util.DataInputPlus.DataInputStreamPlus;

import org.apache.cassandra.io.util.DataOutputStreamPlus;
import org.apache.cassandra.rocksdb.streaming.RocksDBCassandraStreamReader;
import org.apache.cassandra.rocksdb.streaming.RocksDBIncomingMessage;
import org.apache.cassandra.rocksdb.streaming.RocksDBMessageHeader;
import org.apache.cassandra.rocksdb.streaming.RocksDBStreamUtils;
import org.apache.cassandra.streaming.StreamReader;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.streaming.compress.CompressedStreamReader;
import org.apache.cassandra.utils.JVMStabilityInspector;

import static org.apache.cassandra.utils.Throwables.extractIOExceptionCause;

/**
 * IncomingFileMessage is used to receive the part(or whole) of a SSTable data file.
 */
public class IncomingFileMessage extends StreamMessage
{
    public static Serializer<StreamMessage> serializer = new Serializer<StreamMessage>()
    {
        @SuppressWarnings("resource")
        public StreamMessage deserialize(ReadableByteChannel in, int version, StreamSession session) throws IOException
        {
            DataInputPlus input = new DataInputStreamPlus(Channels.newInputStream(in));
            FileMessageHeader header = FileMessageHeader.serializer.deserialize(input, version);

            if (RocksDBStreamUtils.isRocksDBBacked(header.cfId))
            {
                return deserializeForRocksandra(in, session, header);
            }

            StreamReader reader = !header.isCompressed() ? new StreamReader(header, session)
                    : new CompressedStreamReader(header, session);

            try
            {
                return new IncomingFileMessage(reader.read(in), header);
            }
            catch (Throwable t)
            {
                JVMStabilityInspector.inspectThrowable(t);
                throw t;
            }
        }

        private StreamMessage deserializeForRocksandra(ReadableByteChannel in, StreamSession session, FileMessageHeader header) throws IOException
        {
            if (!header.isCompressed()) {
                throw new UnsupportedOperationException("Only support compressed streaming for Rocksandra");
            }
            try
            {
                RocksDBCassandraStreamReader reader = new RocksDBCassandraStreamReader(header, session);
                reader.read(in);
                return new RocksDBIncomingMessage(new RocksDBMessageHeader(header), reader.totalSize());
            }
            catch (Throwable t)
            {
                JVMStabilityInspector.inspectThrowable(t);
                throw t;
            }
        }

        public void serialize(StreamMessage message, DataOutputStreamPlus out, int version, StreamSession session) throws IOException
        {
            throw new UnsupportedOperationException("Not allowed to call serialize on an incoming file");
        }
    };

    public FileMessageHeader header;
    public SSTableMultiWriter sstable;

    public IncomingFileMessage(SSTableMultiWriter sstable, FileMessageHeader header)
    {
        super(Type.FILE);
        this.header = header;
        this.sstable = sstable;
    }

    @Override
    public String toString()
    {
        return "File (" + header + ", file: " + sstable.getFilename() + ")";
    }
}

