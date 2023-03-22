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
import org.apache.cassandra.cql3.ColumnIdentifier;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.DeletionTime;
import org.apache.cassandra.db.LegacyLayout;
import org.apache.cassandra.db.filter.ColumnFilter;
import org.apache.cassandra.db.marshal.ByteType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.Int32Type;
import org.apache.cassandra.db.rows.*;
import org.apache.cassandra.rocksdb.encoding.value.RowValueEncoder;
import org.apache.cassandra.utils.FBUtilities;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RowValueEncoderTest
{

    private static final byte[] COLUMN_VALUE = "data".getBytes();
    private static final byte[] EXPIRING_COLUMN_VALUE = "data".getBytes();
    private static final int TTL = 100;

    private CFMetaData buildCFMetaDate(int columnNum)
    {
        CFMetaData.Builder builder = CFMetaData.Builder.create("test", "test")
                                                       .addPartitionKey("partition_key", Int32Type.instance);
        ;
        for (int i = 0; i < columnNum; i++)
        {
            String columnName = "" + i;
            builder.addRegularColumn(ColumnIdentifier.getInterned(ByteBuffer.wrap(columnName.getBytes()), BytesType.instance), BytesType.instance);
        }
        return builder.build();
    }

    private Cell buildCell(byte columnMask, byte index, long timestamp)
    {
        ColumnDefinition cd = ColumnDefinition.regularDef("test", "test", "" + index, ByteType.instance);
        if (columnMask == 0)
        {
            return new BufferCell(
                                 cd,
                                 timestamp,
                                 Cell.NO_TTL,
                                 FBUtilities.nowInSeconds(),
                                 ByteBuffer.wrap(COLUMN_VALUE),
                                 null);
        }
        else if (columnMask == LegacyLayout.DELETION_MASK)
        {
            return BufferCell.tombstone(cd, timestamp, (int) (timestamp / 1000));
        }
        else
        {
            return new BufferCell(
                                 cd,
                                 timestamp,
                                 TTL,
                                 FBUtilities.nowInSeconds(),
                                 ByteBuffer.wrap(EXPIRING_COLUMN_VALUE),
                                 null);
        }
    }

    private Row buildRow(byte[] masks, byte[] indices, long[] timestamps)
    {
        Row.Builder builder = BTreeRow.sortedBuilder();
        builder.newRow(Clustering.EMPTY);
        for (int i = 0; i < masks.length; i++)
        {
            builder.addCell(buildCell(masks[i], indices[i], timestamps[i]));
        }
        return builder.build();
    }

    private Row buildRowTombstone(long timestamp, int localDeletionTime)
    {
        Row.Builder builder = BTreeRow.sortedBuilder();
        builder.newRow(Clustering.EMPTY);
        builder.addRowDeletion(Row.Deletion.regular(new DeletionTime(timestamp, localDeletionTime)));
        return builder.build();
    }

    @Test
    public void testEncodeAndDecodeRowDeletion()
    {
        CFMetaData metaData = buildCFMetaDate(3);
        Row row = buildRowTombstone(1000, 100);
        ByteBuffer result = ByteBuffer.wrap(RowValueEncoder.encode(metaData, row));
        // Verify the memory structure.
        ByteBuffer copy = result.duplicate();
        assertEquals(copy.remaining(), 12);
        assertEquals(copy.getInt(), 100);
        assertEquals(copy.getLong(), 1000);

        List<ColumnData> columns = new ArrayList<>();
        RowValueEncoder.decode(metaData, ColumnFilter.all(metaData), result, columns);
        assertEquals(columns.size(), 0);
    }

    @Test
    public void testEncodeAndDecodeRow()
    {
        CFMetaData metaData = buildCFMetaDate(3);
        Row row = buildRow(
                          new byte[]{ 0, LegacyLayout.DELETION_MASK, LegacyLayout.EXPIRATION_MASK },
                          new byte[]{ 0, 1, 2 },
                          new long[]{ 100, 200, 300 });
        byte[] encoded = RowValueEncoder.encode(metaData, row);
        ByteBuffer result = ByteBuffer.wrap(encoded);

        List<ColumnData> columns = new ArrayList<>();
        RowValueEncoder.decode(metaData, ColumnFilter.all(metaData), result, columns);
        assertEquals(columns.size(), 3);
    }
}
