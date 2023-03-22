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

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.db.LegacyLayout;
import org.apache.cassandra.db.filter.ColumnFilter;
import org.apache.cassandra.db.rows.BufferCell;
import org.apache.cassandra.db.rows.Cell;
import org.apache.cassandra.db.rows.ColumnData;
import org.apache.cassandra.db.rows.Row;
import org.apache.cassandra.rocksdb.streaming.RocksDBStreamUtils;
import org.apache.cassandra.serializers.MarshalException;
import org.apache.cassandra.utils.FBUtilities;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowValueEncoder
{

    private static final Logger LOGGER = LoggerFactory.getLogger(RowValueEncoder.class);
    public static int ROW_HEADER_SIZE = 12;

    public static byte[] encode(CFMetaData metaData, Row row)
    {
        Row.Deletion deletion = row.deletion();
        int size = ROW_HEADER_SIZE;
        if (!deletion.isLive())
        {
            byte[] result = new byte[size];
            ByteBuffer buff = ByteBuffer.wrap(result);
            encodeRowDeletion(deletion, buff);
            return result;
        }

        for (ColumnDefinition c : metaData.partitionColumns())
        {
            Cell cell = row.getCell(c);
            if (cell == null)
            {
                continue;
            }
            size += getColumnEncodedSize(cell);
        }
        byte[] result = new byte[size];
        ByteBuffer buff = ByteBuffer.wrap(result);
        encodeRowDeletion(deletion, buff);
        byte index = 0;
        for (ColumnDefinition c : metaData.partitionColumns())
        {
            Cell cell = row.getCell(c);
            if (cell != null)
            {
                encodeColumn(index, cell, buff);
            }
            index++;
        }
        return result;
    }

    private static void encodeRowDeletion(Row.Deletion deletion, ByteBuffer dest)
    {
        dest.putInt(deletion.time().localDeletionTime());
        dest.putLong(deletion.time().markedForDeleteAt());
    }

    private static long getColumnEncodedSize(Cell cell)
    {
        if (cell.isTombstone())
        {
            return TombstoneEncoder.getEncodedSize(cell);
        }
        else if (cell.isExpiring())
        {
            return ExpiringColumnEncoder.getEncodedSize(cell);
        }
        else if (cell.isCounterCell())
        {
            throw new MarshalException("Counter cell is not supported");
        }
        else
        {
            return ColumnEncoder.getEncodedSize(cell);
        }
    }

    private static void encodeColumn(byte index, Cell cell, ByteBuffer dest)
    {
        if (cell.isTombstone())
        {
            TombstoneEncoder.encode(index, cell, dest);
        }
        else if (cell.isExpiring())
        {
            ExpiringColumnEncoder.encode(index, cell, dest);
        }
        else if (cell.isCounterCell())
        {
            throw new MarshalException("Counter cell is not supported");
        }
        else
        {
            ColumnEncoder.encode(index, cell, dest);
        }
    }

    public static void decode(CFMetaData metaData, ColumnFilter filter, ByteBuffer src, List<ColumnData> dest)
    {
        assert (src.remaining() >= ROW_HEADER_SIZE);
        if (src.remaining() == ROW_HEADER_SIZE)
        {
            return;
        }
        src.position(src.position() + ROW_HEADER_SIZE);
        decodeColumn(src, metaData.partitionColumns().iterator(), filter, dest);
    }


    private static void decodeColumn(ByteBuffer src, Iterator<ColumnDefinition> iterator, ColumnFilter filter, List<ColumnData> dest)
    {
        short iteratorIndex = -1;
        while (src.remaining() > 0)
        {
            byte mask = src.get();
            byte index = src.get();
            src.position(src.position() - 2);
            ColumnDefinition cd = null;

            while (iterator.hasNext() && iteratorIndex != index)
            {
                cd = iterator.next();
                iteratorIndex++;
            }
            if (iteratorIndex != index)
            {
                throw new MarshalException("Failed to deserialize columns:" + index + " not found in column definitions");
            }
            Cell cell = null;
            if (mask == 0)
            {
                cell = ColumnEncoder.decode(cd, src);
            }
            else if (mask == LegacyLayout.EXPIRATION_MASK)
            {
                cell = ExpiringColumnEncoder.decode(cd, src);
            }
            else if (mask == LegacyLayout.DELETION_MASK)
            {
                cell = TombstoneEncoder.decode(cd, src);
            }
            else
            {
                throw new MarshalException("Unsupported column mask:" + mask);
            }
            if (filter.includes(cd))
            {
                dest.add(cell);
            }
        }
    }
}
