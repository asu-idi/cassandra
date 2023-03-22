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
import org.apache.cassandra.db.rows.BufferCell;
import org.apache.cassandra.db.rows.Cell;

import java.nio.ByteBuffer;

public class ColumnEncoder
{
    public static int COLUMN_HEADER_SIZE = 12;

    public static int getEncodedSize(Cell cell)
    {
        return ColumnBaseEncoder.getEncodedSize() + COLUMN_HEADER_SIZE + cell.value().remaining();
    }

    public static void encode(byte index, Cell cell, ByteBuffer dest)
    {
        ColumnBaseEncoder.encode(index, (byte) 0, dest);
        dest.putLong(cell.timestamp());
        dest.putInt(cell.value().remaining());
        dest.put(cell.value().duplicate());
    }

    public static Cell decode(ColumnDefinition cd, ByteBuffer src)
    {
        src.position(src.position() + ColumnBaseEncoder.getEncodedSize());
        long timestamp = src.getLong();
        int valueSize = src.getInt();
        ByteBuffer value = src.duplicate();
        value.limit(value.position() + valueSize);
        src.position(src.position() + valueSize);
        return new BufferCell(cd, timestamp, BufferCell.NO_TTL, BufferCell.NO_DELETION_TIME, value, null);
    }
}
