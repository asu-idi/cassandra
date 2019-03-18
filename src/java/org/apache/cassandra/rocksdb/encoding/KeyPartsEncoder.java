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
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.BooleanType;
import org.apache.cassandra.db.marshal.ByteType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.DecimalType;
import org.apache.cassandra.db.marshal.DoubleType;
import org.apache.cassandra.db.marshal.FloatType;
import org.apache.cassandra.db.marshal.InetAddressType;
import org.apache.cassandra.db.marshal.Int32Type;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.PartitionerDefinedOrder;
import org.apache.cassandra.db.marshal.ReversedType;
import org.apache.cassandra.db.marshal.ShortType;
import org.apache.cassandra.db.marshal.SimpleDateType;
import org.apache.cassandra.db.marshal.TimeType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.TimestampType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.db.marshal.UUIDType;
import org.apache.cassandra.rocksdb.encoding.orderly.BigDecimalRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.DoubleRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.FixedIntegerRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.FixedLongRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.FloatRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.Order;
import org.apache.cassandra.rocksdb.encoding.orderly.RowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.StructRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.UTF8RowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.VariableLengthByteArrayRowKey;
import org.apache.cassandra.utils.Pair;

import static org.apache.cassandra.rocksdb.encoding.RowKeyInputAdapter.bytesAdapter;
import static org.apache.cassandra.rocksdb.encoding.RowKeyInputAdapter.defaultAdapter;
import static org.apache.cassandra.rocksdb.encoding.RowKeyInputAdapter.timestampAdapter;

public class KeyPartsEncoder
{
    static final Map<AbstractType, RowKeyEncodingPolicy> rowKeyEncodingPolicies = new HashMap<>();

    static
    {
        KeyPartsEncoder.rowKeyEncodingPolicies.put(AsciiType.instance,
                                                   new RowKeyEncodingPolicy(() -> new VariableLengthByteArrayRowKey(), bytesAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(BooleanType.instance,
                                                   new RowKeyEncodingPolicy(() -> new BooleanRowKey(), defaultAdapter, 1));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(ByteType.instance,
                                                   new RowKeyEncodingPolicy(() -> new ByteRowKey(), defaultAdapter, 1));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(BytesType.instance,
                                                   new RowKeyEncodingPolicy(() -> new VariableLengthByteArrayRowKey(), bytesAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(DecimalType.instance,
                                                   new RowKeyEncodingPolicy(() -> new BigDecimalRowKey(), defaultAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(DoubleType.instance,
                                                   new RowKeyEncodingPolicy(() -> new DoubleRowKey(), defaultAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(FloatType.instance,
                                                   new RowKeyEncodingPolicy(() -> new FloatRowKey(), defaultAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(InetAddressType.instance,
                                                   new RowKeyEncodingPolicy(() -> new VariableLengthByteArrayRowKey(), bytesAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(Int32Type.instance,
                                                   new RowKeyEncodingPolicy(() -> new FixedIntegerRowKey(), defaultAdapter, 4));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(IntegerType.instance,
                                                   new RowKeyEncodingPolicy(() -> new BigIntegerRowKey(), defaultAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(LongType.instance,
                                                   new RowKeyEncodingPolicy(() -> new FixedLongRowKey(), defaultAdapter, 8));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(ShortType.instance,
                                                   new RowKeyEncodingPolicy(() -> new ShortRowKey(), defaultAdapter, 2));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(SimpleDateType.instance,
                                                   new RowKeyEncodingPolicy(() -> new FixedIntegerRowKey(), defaultAdapter, 4));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(TimeType.instance,
                                                   new RowKeyEncodingPolicy(() -> new FixedLongRowKey(), defaultAdapter, 8));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(TimestampType.instance,
                                                   new RowKeyEncodingPolicy(() -> new FixedLongRowKey(), timestampAdapter, 8));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(TimeUUIDType.instance,
                                                   new RowKeyEncodingPolicy(() -> new UUIDRowKey(), defaultAdapter, UUIDRowKey.UUID_BYTE_SIZE));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(UTF8Type.instance,
                                                   new RowKeyEncodingPolicy(() -> new UTF8RowKey(), bytesAdapter));
        KeyPartsEncoder.rowKeyEncodingPolicies.put(UUIDType.instance,
                                                   new RowKeyEncodingPolicy(() -> new UUIDRowKey(), defaultAdapter, UUIDRowKey.UUID_BYTE_SIZE));
    }

    public static byte[] encode(List<Pair<AbstractType, ByteBuffer>> keyParts)
    {
        RowKey[] fields = new RowKey[keyParts.size()];
        for (int i = 0; i < fields.length; i++)
        {
            fields[i] = getOrderlyRowKey(keyParts.get(i));
        }

        Object[] values = new Object[keyParts.size()];
        for (int i = 0; i < values.length; i++)
        {
            values[i] = composeValueForOrderly(keyParts.get(i));
        }
        RowKey structRowKey = new StructRowKey(fields);
        try
        {
            return structRowKey.serialize(values);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    static ByteBuffer[] decode(byte[] key, List<AbstractType> types)
    {
        return decode(key, types, 0);
    }

    static ByteBuffer[] decode(byte[] key, List<AbstractType> types, int skipRowNum)
    {
        assert skipRowNum <= types.size();

        RowKey[] fields = new RowKey[types.size()];

        for (int i = 0; i < types.size(); i++)
        {
            fields[i] = getOrderlyRowKey(types.get(i));
        }

        StructRowKey structRowKey = new StructRowKey(fields);
        Object[] deserialized;
        try
        {
            deserialized = (Object[]) structRowKey.deserialize(key, 0, skipRowNum);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        assert deserialized.length == types.size() - skipRowNum;
        ByteBuffer[] result = new ByteBuffer[deserialized.length];

        for (int i = 0; i < result.length; i++)
        {
            result[i] = decomposeOrderlyValue(deserialized[i], types.get(i + skipRowNum));
        }
        return result;
    }

    public static Integer getEncodedLengthForType(AbstractType type)
    {
        if (type instanceof ReversedType)
        {
            type = ((ReversedType) type).baseType;
        }

        return rowKeyEncodingPolicies.get(type).getEncodedLength();
    }

    private static ByteBuffer decomposeOrderlyValue(Object orderlyValue, AbstractType type)
    {
        if (type instanceof ReversedType)
        {
            type = ((ReversedType) type).baseType;
        }
        if (type instanceof PartitionerDefinedOrder)
        {
            // TODO: This type is specific to CassandraIndex and needs to be supported when cross-partition queries are supported
            type = BytesType.instance;
        }
        return rowKeyEncodingPolicies.get(type).decompose(type, orderlyValue);
    }

    private static Object composeValueForOrderly(Pair<AbstractType, ByteBuffer> keyPart)
    {

        AbstractType type = keyPart.left;
        if (type instanceof ReversedType)
        {
            type = ((ReversedType) type).baseType;
        }
        if (type instanceof PartitionerDefinedOrder)
        {
            // TODO: This type is specific to CassandraIndex and needs to be supported when cross-partition queries are supported
            type = BytesType.instance;
        }
        return rowKeyEncodingPolicies.get(type).compose(type, keyPart.right);
    }

    private static RowKey getOrderlyRowKey(Pair<AbstractType, ByteBuffer> keyPart)
    {
        return getOrderlyRowKey(keyPart.left);
    }

    private static RowKey getOrderlyRowKey(AbstractType type)
    {
        if (type instanceof ReversedType)
        {
            RowKey rowKey = getOrderlyRowKey(((ReversedType) type).baseType);
            rowKey.setOrder(Order.DESCENDING);
            return rowKey;
        }
        if (type instanceof PartitionerDefinedOrder)
        {
            // TODO: This type is specific to CassandraIndex and needs to be supported when cross-partition queries are supported
            return getOrderlyRowKey(BytesType.instance);
        }
        if (!rowKeyEncodingPolicies.containsKey(type))
        {
            throw new UnsupportedOperationException(type.toString() + " is not supported for Rocksdb engine yet");
        }
        return rowKeyEncodingPolicies.get(type).getOrderlyRowKey();
    }
}
