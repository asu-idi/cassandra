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

import org.apache.cassandra.io.util.DataInputPlus;
import org.apache.cassandra.io.util.DataOutputPlus;
import org.apache.cassandra.net.MessagingService;
import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.streaming.messages.FileMessageHeader;

public class RocksDBMessageHeader
{
    public static RocksDBMessageHeaderSerializer SERIALIZER = new RocksDBMessageHeaderSerializer();
    public final UUID cfId;
    public final int sequenceNumber;
    public final long estimatedBytes;
    public final long estimatedKeys;
    public final int numShard;

    public RocksDBMessageHeader(UUID cfId, int sequenceNumber)
    {
        this(cfId, sequenceNumber, 0, 0);
    }

    public RocksDBMessageHeader(UUID cfId, int sequenceNumber, long estimatedBytes, long estimatedKeys)
    {
        this.cfId = cfId;
        this.sequenceNumber = sequenceNumber;
        this.estimatedBytes = estimatedBytes;
        this.estimatedKeys = estimatedKeys;
        this.numShard = RocksDBConfigs.NUM_SHARD;
    }

    public RocksDBMessageHeader(FileMessageHeader header)
    {
        this(header.cfId, header.sequenceNumber, 0, header.estimatedKeys);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("RocksHeader (");
        sb.append("cfId: ").append(cfId);
        sb.append(", #").append(sequenceNumber);
        sb.append(", estimated number of bytes").append(sequenceNumber);
        sb.append(", estimated number of keys").append(sequenceNumber);
        sb.append(')');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RocksDBMessageHeader that = (RocksDBMessageHeader) o;
        return sequenceNumber == that.sequenceNumber && cfId.equals(that.cfId);
    }

    @Override
    public int hashCode()
    {
        int result = cfId.hashCode();
        result = 31 * result + sequenceNumber;
        return result;
    }

    static class RocksDBMessageHeaderSerializer
    {
        public static final int CURRENT_STREAMING_SCHEMA_VERSION = 1;

        public void seriliaze(RocksDBMessageHeader header, DataOutputPlus out) throws IOException
        {
            out.writeInt(CURRENT_STREAMING_SCHEMA_VERSION);
            org.apache.cassandra.utils.UUIDSerializer.serializer.serialize(header.cfId, out, MessagingService.current_version);
            out.writeInt(header.sequenceNumber);
            out.writeLong(header.estimatedBytes);
            out.writeLong(header.estimatedKeys);
            out.writeInt(header.numShard);
        }

        public RocksDBMessageHeader deserialize(DataInputPlus in) throws IOException
        {
            int streamingSchemaVersion = in.readInt();
            if (streamingSchemaVersion != CURRENT_STREAMING_SCHEMA_VERSION)
            {
                throw new RocksDBStreamingScheamVersionMismatchException(streamingSchemaVersion, CURRENT_STREAMING_SCHEMA_VERSION);
            }
            UUID cfId = org.apache.cassandra.utils.UUIDSerializer.serializer.deserialize(in, MessagingService.current_version);
            int sequenceNumber = in.readInt();
            long estimatedBytes = in.readLong();
            long estimatedKeys = in.readLong();
            int numShard = in.readInt();
            RocksDBMessageHeader header = new RocksDBMessageHeader(cfId, sequenceNumber, estimatedBytes, estimatedKeys);
            if (numShard != RocksDBConfigs.NUM_SHARD)
                throw new StreamingShardMismatchException(header, numShard, RocksDBConfigs.NUM_SHARD);
            return header;
        }
    }
}
