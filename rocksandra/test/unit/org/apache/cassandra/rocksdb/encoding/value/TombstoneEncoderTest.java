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
import org.apache.cassandra.rocksdb.encoding.value.TombstoneEncoder;
import org.apache.cassandra.utils.FBUtilities;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class TombstoneEncoderTest
{
    private static final ColumnDefinition CD = ColumnDefinition.regularDef("test", "test", "test", ByteType.instance);

    private Cell createCell(long timestamp, int localDeletionTime)
    {
        return BufferCell.tombstone(CD, timestamp, localDeletionTime);
    }

    @Test
    public void testEncodeAndDecode()
    {
        long timestamp = FBUtilities.timestampMicros() / 1000;
        int localDeletionTime = 123;
        byte index = 3;
        Cell cell = createCell(timestamp, localDeletionTime);
        ByteBuffer dest = ByteBuffer.allocate(TombstoneEncoder.getEncodedSize(cell));
        TombstoneEncoder.encode(index, cell, dest);
        dest.flip();

        // Verify the memory structure.
        ByteBuffer buff = dest.duplicate();
        assertEquals(buff.get(), (byte) LegacyLayout.DELETION_MASK);
        assertEquals(buff.get(), index);
        assertEquals(buff.getInt(), localDeletionTime);
        assertEquals(buff.getLong(), timestamp);
        assertEquals(buff.remaining(), 0);

        // Verify decode works.
        Cell decoded = TombstoneEncoder.decode(CD, dest.duplicate());
        assertEquals(decoded.timestamp(), timestamp);
        assertEquals(decoded.localDeletionTime(), localDeletionTime);
    }
}
