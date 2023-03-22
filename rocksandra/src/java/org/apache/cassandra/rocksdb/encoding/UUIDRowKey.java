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

package org.apache.cassandra.rocksdb.encoding;

import java.io.IOException;
import java.util.UUID;

import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;
import org.apache.cassandra.rocksdb.encoding.orderly.ImmutableBytesWritable;
import org.apache.cassandra.rocksdb.encoding.orderly.RowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.RowKeyUtils;

public class UUIDRowKey extends RowKey
{

    static final int UUID_BYTE_SIZE = 16;

    @Override
    public Class<?> getSerializedClass()
    {
        return UUID.class;
    }

    @Override
    public int getSerializedLength(Object o) throws IOException
    {
        return UUID_BYTE_SIZE;
    }

    @Override
    public void serialize(Object o, ImmutableBytesWritable w) throws IOException
    {
        byte[] b = w.get();
        int offset = w.getOffset();
        UUID uuid = (UUID) o;
        int version = uuid.version();
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        lsb = flip(lsb);
        msb = moveVersionBitsLeft(msb);
        if (version == 1)
        {
            msb = reorderTimestampBits(msb);
        }
        Bytes.putLong(b, offset, msb ^ order.mask());
        Bytes.putLong(b, offset + 8, lsb ^ order.mask());
        RowKeyUtils.seek(w, UUID_BYTE_SIZE);
    }

    public void skip(ImmutableBytesWritable w) throws IOException
    {
        RowKeyUtils.seek(w, UUID_BYTE_SIZE);
    }

    @Override
    public Object deserialize(ImmutableBytesWritable w) throws IOException
    {
        int offset = w.getOffset();
        byte[] s = w.get();
        long msb = Bytes.toLong(s, offset) ^ order.mask();
        int version = (int) (msb >>> 60);
        if (version == 1)
        {
            msb = recoverTimestampBitOrder(msb);
        }
        msb = recoverVersionBits(msb);
        long lsb = Bytes.toLong(s, offset + 8) ^ order.mask();
        lsb = flip(lsb);
        RowKeyUtils.seek(w, UUID_BYTE_SIZE);
        return new UUID(msb, lsb);
    }

    // two's complement format <-> native format
    public long flip(long lsb)
    {
        return lsb ^ 0x8080808080808080L;
    }

    // moves 4 version bites (48~52 bit) to left most,
    // then shift orginal 1~47th 4 bit right
    private long moveVersionBitsLeft(long msb)
    {
        return ((msb << 48) & 0xF000000000000000L)
               | (msb >>> 4 & 0xFFFFFFFFFFFFF000L)
               | (msb & 0x0000000000000FFFL);
    }

    // move versoin bits back to 48~52
    private long recoverVersionBits(long msb)
    {
        return (msb << 4 & 0xFFFFFFFFFFFF0000L)
               | (msb >>> 48 & 0x000000000000F000L)
               | (msb & 0x0000000000000FFFL);
    }

    // reorder timestamp bits for type 1 uuid
    // input:
    //   0xF000000000000000 version
    //   0x0FFFFFFFF0000000 time low
    //   0x000000000FFFF000 time mid
    //   0x0000000000000FFF time high
    //
    // output
    //   0xF000000000000000 version
    //   0x0FFF000000000000 time high
    //   0x0000FFFF00000000 time mid
    //   0x00000000FFFFFFFF time low
    private long reorderTimestampBits(long msb)
    {
        return (msb & 0xF000000000000000L)
               | (msb << 48 & 0x0FFF000000000000L)
               | ((msb << 20) & 0x0000FFFF00000000L)
               | (msb >>> 28 & 0x00000000FFFFFFFFL);
    }

    private long recoverTimestampBitOrder(long msb)
    {
        return (msb & 0xF000000000000000L)
               | (msb >>> 48 & 0x0000000000000FFFL)
               | (msb >>> 20 & 0x000000000FFFF000L)
               | (msb << 28 & 0x0FFFFFFFF0000000L);
    }
}

