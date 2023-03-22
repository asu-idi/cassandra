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
import java.nio.channels.ReadableByteChannel;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.compress.lzf.LZFOutputStream;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.io.util.DataOutputStreamPlus;
import org.apache.cassandra.rocksdb.RocksDBCF;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.streaming.messages.OutgoingMessage;

public class RocksDBOutgoingMessage extends OutgoingMessage
{
    private static final Logger logger = LoggerFactory.getLogger(RocksDBOutgoingMessage.class);

    public static Serializer<RocksDBOutgoingMessage> SERIALIZER = new Serializer<RocksDBOutgoingMessage>()
    {
        public RocksDBOutgoingMessage deserialize(ReadableByteChannel in, int version, StreamSession session) throws IOException
        {
            throw new UnsupportedOperationException("Not allowed to call deserialize on an outgoing file");
        }

        public void serialize(RocksDBOutgoingMessage message, DataOutputStreamPlus out, int version, StreamSession session) throws IOException
        {
            message.startTransfer();
            try 
            {
                message.serialize(out, session);
                session.fileSent(message.cfId, message.sequenceNumber,
                                 0 /* We've already updated outgoing bytes during streaming */);
            }
            finally
            {
                message.finishTransfer();
            }
        }
    };

    protected long serialize(DataOutputStreamPlus out,  StreamSession session) throws IOException
    {
        RocksDBMessageHeader.SERIALIZER.seriliaze(header, out);
        LZFOutputStream lzfOutputStream = new LZFOutputStream(out);
        RocksDBStreamWriter writer = new RocksDBStreamWriter(rocksDBCF, range, session, header.estimatedKeys);
        writer.write(lzfOutputStream);
        lzfOutputStream.flush();
        return writer.getOutgoingBytes();
    }

    public final RocksDBMessageHeader header;
    public final UUID cfId;
    public final int sequenceNumber;
    private final RocksDBCF rocksDBCF;
    public final Range<Token> range;
    private boolean completed = false;
    public boolean transferring = false;

    public RocksDBOutgoingMessage(UUID cfId, int sequenceNumber, RocksDBCF rocksDBCF, Range<Token> range)
    {
        super(Type.ROCKSFILE);
        this.cfId = cfId;
        this.sequenceNumber = sequenceNumber;
        this.rocksDBCF = rocksDBCF;
        this.range = range;
        this.header = new RocksDBMessageHeader(cfId, sequenceNumber, RocksDBStreamUtils.estimateDataSize(rocksDBCF, range),
                                               RocksDBStreamUtils.estimateNumKeys(rocksDBCF, range));
    }

    public synchronized void startTransfer()
    {
        if (completed)
            throw new RuntimeException("Transfer of rocksdb already completed or aborted (perhaps session failed?).");
        transferring = true;
    }

    public synchronized void finishTransfer()
    {
        transferring = false;
    }

    public synchronized void complete()
    {
        if (!completed)
        {
            completed = true;
        }
    }
}
