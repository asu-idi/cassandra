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

package org.apache.cassandra.rocksdb.encoding.value;

import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.db.LegacyLayout;
import org.apache.cassandra.db.marshal.ByteType;
import org.apache.cassandra.db.rows.BufferCell;
import org.apache.cassandra.db.rows.Cell;
import org.apache.cassandra.rocksdb.encoding.value.ExpiringColumnEncoder;
import org.apache.cassandra.utils.FBUtilities;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpiringColumnEncoderTest
{

    private static final ColumnDefinition CD = ColumnDefinition.regularDef("test", "test", "test", ByteType.instance);

    private Cell createCell(long timestamp, int ttl, byte[] value)
    {
        return new BufferCell(CD, timestamp, ttl, FBUtilities.nowInSeconds(), ByteBuffer.wrap(value), null);
    }

    @Test
    public void testEncodeAndDecode()
    {
        String sData = "some data";
        byte[] data = sData.getBytes();
        long timestamp = FBUtilities.timestampMicros() / 1000;
        int ttl = 100;
        byte index = 2;
        Cell cell = createCell(timestamp, ttl, data);
        ByteBuffer dest = ByteBuffer.allocate(ExpiringColumnEncoder.getEncodedSize(cell));
        ExpiringColumnEncoder.encode(index, cell, dest);
        dest.flip();

        // Verify the memory structure.
        ByteBuffer buff = dest.duplicate();
        assertEquals(buff.get(), (short) LegacyLayout.EXPIRATION_MASK);
        assertEquals(buff.get(), index);
        assertEquals(buff.getLong(), timestamp);
        assertEquals(buff.getInt(), data.length);
        byte[] value = new byte[data.length];
        buff.get(value);
        assertEquals(sData, new String(value));
        assertEquals(buff.getInt(), ttl);
        assertEquals(buff.remaining(), 0);

        // Verify decode works.
        Cell deserialized = ExpiringColumnEncoder.decode(CD, dest.duplicate());
        assertEquals(deserialized.timestamp(), timestamp);
        value = new byte[deserialized.value().remaining()];

        deserialized.value().get(value);
        assertEquals(new String(value), sData);
        assertEquals(deserialized.ttl(), ttl);
        assertTrue(deserialized.localDeletionTime() > 0);
    }
}
