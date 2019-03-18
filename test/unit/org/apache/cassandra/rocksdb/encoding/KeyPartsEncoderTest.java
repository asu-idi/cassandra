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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;

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
import org.apache.cassandra.db.marshal.ShortType;
import org.apache.cassandra.db.marshal.SimpleDateType;
import org.apache.cassandra.db.marshal.TimeType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.TimestampType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.db.marshal.UUIDType;
import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;
import org.apache.cassandra.serializers.SimpleDateSerializer;
import org.apache.cassandra.utils.Hex;
import org.apache.cassandra.utils.Pair;
import org.apache.cassandra.utils.UUIDGen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class KeyPartsEncoderTest
{
    private static final long START_EPOCH = -12219292800000L;

    @Test
    public void shouldGetEncodedLengthForFixSizeType()
    {
        assertEncodedLengthIsCorrect(BooleanType.instance, Boolean.TRUE);
        assertEncodedLengthIsCorrect(BooleanType.instance, Boolean.FALSE);
        assertEncodedLengthIsCorrect(ByteType.instance, Byte.valueOf("0"));
        assertEncodedLengthIsCorrect(ByteType.instance, Byte.MAX_VALUE);
        assertEncodedLengthIsCorrect(ByteType.instance, Byte.MIN_VALUE);
        assertEncodedLengthIsCorrect(Int32Type.instance, Integer.MIN_VALUE);
        assertEncodedLengthIsCorrect(Int32Type.instance, 0);
        assertEncodedLengthIsCorrect(Int32Type.instance, Integer.MAX_VALUE);
        assertEncodedLengthIsCorrect(LongType.instance, Long.MIN_VALUE);
        assertEncodedLengthIsCorrect(LongType.instance, 0L);
        assertEncodedLengthIsCorrect(LongType.instance, Long.MAX_VALUE);
        assertEncodedLengthIsCorrect(ShortType.instance, Short.MIN_VALUE);
        assertEncodedLengthIsCorrect(SimpleDateType.instance, 0);
        assertEncodedLengthIsCorrect(SimpleDateType.instance, Integer.MAX_VALUE);
        assertEncodedLengthIsCorrect(TimeType.instance, 0L);
        assertEncodedLengthIsCorrect(TimeType.instance, Long.MAX_VALUE);
        assertEncodedLengthIsCorrect(TimestampType.instance, new Date(0L));
        assertEncodedLengthIsCorrect(TimestampType.instance, new Date());
        assertEncodedLengthIsCorrect(TimestampType.instance, new Date(Long.MAX_VALUE));
        assertEncodedLengthIsCorrect(TimeUUIDType.instance, UUIDGen.getTimeUUID());
        assertEncodedLengthIsCorrect(UUIDType.instance, UUIDGen.getTimeUUID());
    }

    @Test
    public void encodeLengthForNoneFixLengthTypeAreNull()
    {
        assertNull(KeyPartsEncoder.getEncodedLengthForType(AsciiType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(BytesType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(DecimalType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(DoubleType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(FloatType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(InetAddressType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(IntegerType.instance));
        assertNull(KeyPartsEncoder.getEncodedLengthForType(UTF8Type.instance));
    }


    private void assertEncodedLengthIsCorrect(AbstractType type, Object object)
    {
        assertEquals(Integer.valueOf(encode(createKeyPart(object, type)).length), KeyPartsEncoder.getEncodedLengthForType(type));
    }

    @Test
    public void encodeSingleByteType() throws Exception
    {
        assertKeysAreInOrder(ByteType.instance,
                             Byte.MIN_VALUE, (byte) -99, (byte) 0, (byte) 99, Byte.MAX_VALUE);
    }


    @Test
    public void encodeSingleIntType() throws Exception
    {
        assertKeysAreInOrder(Int32Type.instance,
                             Integer.MIN_VALUE, -99, 0, 99, Integer.MAX_VALUE);
    }

    @Test
    public void encodeSingleShortType() throws Exception
    {
        assertKeysAreInOrder(ShortType.instance,
                             Short.MIN_VALUE, (short) -99, (short) 0, (short) 99, Short.MAX_VALUE);
    }

    @Test
    public void encodeSingleLongType() throws Exception
    {
        assertKeysAreInOrder(LongType.instance,
                             Long.MIN_VALUE, -99L, 0L, 99L, Long.MAX_VALUE);
    }

    @Test
    public void encodeSingleVarIntType() throws Exception
    {
        assertKeysAreInOrder(IntegerType.instance,
                             new BigInteger("-1000"),
                             new BigInteger("92"),
                             new BigInteger("10000000"),
                             new BigInteger("22334343425352352"));
    }

    @Test
    public void encodeSingleVarCharOrTextType() throws Exception
    {
        assertKeysAreInOrder(UTF8Type.instance,
                             "apple", "orange", "peach", "peach 10", "peach 9");
    }

    @Test
    public void encodeSingleAsciiType() throws Exception
    {
        assertKeysAreInOrder(AsciiType.instance,
                             "apple", "orange", "peach", "peach 10", "peach 9");
    }

    @Test
    public void encodeSingleTimeType() throws Exception
    {
        assertKeysAreInOrder(TimeType.instance,
                             Long.MIN_VALUE, -99L, 0L, 99L, Long.MAX_VALUE);
    }

    @Test
    public void encodeTimeStampType() throws Exception
    {
        assertKeysAreInOrder(TimestampType.instance,
                             new Date(-100L), new Date(0L), new Date(System.currentTimeMillis()));
    }


    @Test
    public void encodeSingleBlobType() throws Exception
    {
        assertKeysAreInOrder(BytesType.instance,
                             ByteBuffer.wrap(Hex.hexToBytes("000000")),
                             ByteBuffer.wrap(Hex.hexToBytes("000001")),
                             ByteBuffer.wrap(Hex.hexToBytes("ff0000")),
                             ByteBuffer.wrap(Hex.hexToBytes("ffffff")));
    }

    @Test
    public void encodeSingleBooleanType() throws Exception
    {
        assertKeysAreInOrder(BooleanType.instance,
                             false, true);
    }

    @Test
    public void encodeSingleSimpleDateType() throws Exception
    {
        assertKeysAreInOrder(SimpleDateType.instance,
                             SimpleDateSerializer.dateStringToDays("1970-01-05"),
                             SimpleDateSerializer.dateStringToDays("1970-01-06"),
                             SimpleDateSerializer.dateStringToDays("2008-06-18"));
    }

    @Test
    public void encodeSingleDecimalType() throws Exception
    {
        assertKeysAreInOrder(DecimalType.instance,
                             new BigDecimal("-1.000001"),
                             new BigDecimal("0"),
                             new BigDecimal("12.34"),
                             new BigDecimal("34.5678"));
    }

    @Test
    public void encodeSingleDoubleType() throws Exception
    {
        assertKeysAreInOrder(DoubleType.instance,
                             Double.NEGATIVE_INFINITY, -1.000001d, 0.0d, 12.34d, Double.POSITIVE_INFINITY);
    }


    @Test
    public void encodeSingleFloatType() throws Exception
    {
        assertKeysAreInOrder(FloatType.instance,
                             Float.NEGATIVE_INFINITY, -1.000001f, 0.0f, 12.34f, Float.POSITIVE_INFINITY);
    }

    @Test
    public void encodeInetAddressType() throws Exception
    {
        assertKeysAreInOrder(InetAddressType.instance,
                             InetAddress.getByAddress(new byte[]{ 127, 0, 0, 0 }),
                             InetAddress.getByAddress(new byte[]{ 127, 0, 0, 1 }),
                             InetAddress.getByAddress(new byte[]{ 127, 0, 1, 1 }));
    }


    @Test
    public void encodeSingleTimeUUIDType() throws Exception
    {
        // test recognize timestamp bites order correctly, for version 1 uuid:
        assertKeysAreInOrder(TimeUUIDType.instance,
                             new UUID(0xffffffff00001000L, 0xbfcb2b3ac88f55f3L),
                             new UUID(0x1111111100011000L, 0xbfcb2b3ac88f55f3L),
                             new UUID(0x1111111100001001L, 0xbfcb2b3ac88f55f3L));
        // test random timestamps
        assertKeysAreInOrder(TimeUUIDType.instance,
                             UUIDGen.getTimeUUID(START_EPOCH),
                             UUIDGen.getTimeUUID(0L),
                             UUIDGen.getTimeUUID(System.currentTimeMillis() + 1L),
                             UUIDGen.getTimeUUID(System.currentTimeMillis() * 2L));
    }

    @Test
    public void encodeMultipleKeysPerserveOrders() throws Exception
    {
        Pair<AbstractType, ByteBuffer> smallVarInt = createKeyPart(new BigInteger("-10000"), IntegerType.instance);
        Pair<AbstractType, ByteBuffer> largeVarInt = createKeyPart(new BigInteger("3234324324242342"), IntegerType.instance);

        Pair<AbstractType, ByteBuffer> smallBigInt = createKeyPart(-100L, LongType.instance);
        Pair<AbstractType, ByteBuffer> largeBigInt = createKeyPart(2343434324L, LongType.instance);

        Pair<AbstractType, ByteBuffer> smallTimedUUID = createKeyPart(UUIDGen.getTimeUUID(0L), TimeUUIDType.instance);
        Pair<AbstractType, ByteBuffer> largeTimedUUID = createKeyPart(UUIDGen.getTimeUUID(System.currentTimeMillis()), TimeUUIDType.instance);

        assertRoundTripMulti(smallVarInt, smallBigInt, smallTimedUUID);
        assertRoundTripMulti(smallVarInt, smallBigInt, largeTimedUUID);
        assertRoundTripMulti(smallVarInt, largeBigInt, smallTimedUUID);
        assertRoundTripMulti(smallVarInt, largeBigInt, largeTimedUUID);
        assertRoundTripMulti(largeVarInt, smallBigInt, smallTimedUUID);
        assertRoundTripMulti(largeVarInt, smallBigInt, largeTimedUUID);
        assertRoundTripMulti(largeVarInt, largeBigInt, smallTimedUUID);
        assertRoundTripMulti(largeVarInt, largeBigInt, largeTimedUUID);
        assertKeysAreInOrder(encode(smallVarInt, smallBigInt, smallTimedUUID),
                             encode(smallVarInt, smallBigInt, largeTimedUUID),
                             encode(smallVarInt, largeBigInt, smallTimedUUID),
                             encode(smallVarInt, largeBigInt, largeTimedUUID),
                             encode(largeVarInt, smallBigInt, smallTimedUUID),
                             encode(largeVarInt, smallBigInt, largeTimedUUID),
                             encode(largeVarInt, largeBigInt, smallTimedUUID),
                             encode(largeVarInt, largeBigInt, largeTimedUUID));
    }


    private Pair<AbstractType, ByteBuffer> createKeyPart(Object value, AbstractType type)
    {
        return Pair.create(type, type.getSerializer().serialize(value));
    }

    private void assertKeysAreInOrder(AbstractType type, Object... keyVals)
    {
        for (int i = 0; i < keyVals.length; i++)
        {
            assertRoundtripSingle(keyVals[i], type);
        }
        byte[][] keys = new byte[keyVals.length][];
        for (int i = 0; i < keyVals.length; i++)
        {
            keys[i] = encode(createKeyPart(keyVals[i], type));
        }
        assertKeysAreInOrder(keys);
    }

    private void assertRoundtripSingle(Object value, AbstractType type)
    {
        byte[] encoded = encode(createKeyPart(value, type));
        assertEquals(value, type.compose(KeyPartsEncoder.decode(encoded, Arrays.asList(type))[0]));
    }

    private void assertRoundTripMulti(Pair<AbstractType, ByteBuffer>... keyParts)
    {
        byte[] encoded = encode(keyParts);
        List<AbstractType> types = Arrays.stream(keyParts).map(kp -> kp.left).collect(Collectors.toList());
        ByteBuffer[] decoded = KeyPartsEncoder.decode(encoded, types);
        List<ByteBuffer> expected = Arrays.stream(keyParts).map(kp -> kp.right).collect(Collectors.toList());
        assertEquals(expected, Arrays.asList(decoded));

        for (int i = 0; i < keyParts.length; i++)
        {
            assertRoundTripPartial(i, keyParts);
        }
    }

    private void assertRoundTripPartial(int skipRowNum, Pair<AbstractType, ByteBuffer>... keyParts)
    {
        int n = keyParts.length;
        byte[] encoded = encode(keyParts);
        List<AbstractType> types = Arrays.stream(keyParts).map(kp -> kp.left).collect(Collectors.toList());
        ByteBuffer[] decoded = KeyPartsEncoder.decode(encoded, types, skipRowNum);
        List<ByteBuffer> expected = Arrays.stream(keyParts).map(kp -> kp.right).collect(Collectors.toList()).subList(skipRowNum, n);
        assertEquals(expected, Arrays.asList(decoded));
    }

    private byte[] encode(Pair<AbstractType, ByteBuffer>... keyParts)
    {
        return KeyPartsEncoder.encode(Arrays.asList(keyParts));
    }

    private void assertKeysAreInOrder(byte[]... keys)
    {
        Bytes.ByteArrayComparator comparator = new Bytes.ByteArrayComparator();
        for (int i = 0; i < keys.length - 1; i++)
        {
            assertTrue("keys[" + i + "] (0x" + Hex.bytesToHex(keys[i]) + ") should be smaller then keys[" + (i + 1) + "] (0x" + Hex.bytesToHex(keys[i + 1]) + ") but not",
                       comparator.compare(keys[i], keys[i + 1]) < 0);
        }
    }
}