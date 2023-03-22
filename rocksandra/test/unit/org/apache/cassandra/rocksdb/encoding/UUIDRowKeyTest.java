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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.UUIDType;
import org.apache.cassandra.db.marshal.UUIDTypeTest;
import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;
import org.apache.cassandra.rocksdb.encoding.orderly.ImmutableBytesWritable;
import org.apache.cassandra.utils.UUIDGen;

import static junit.framework.Assert.assertEquals;

public class UUIDRowKeyTest
{

    public static final UUID TYPE_1 = UUIDGen.getTimeUUID();
    public static final UUID TYPE_2 = new UUID(0xFFFFFFFFFFFF2FFFL, 0xCBFFFFFFFFFFFFFFL);
    public static final UUID TYPE_3 = new UUID(0xEFFFFFFFFFFF3FFFL, 0xCBFFFFFFFFFFFFFFL);
    public static final UUID TYPE_4 = UUID.randomUUID();

    @Test
    public void roundtripSerilizationOfUUIDType() throws IOException
    {
        assertEquals(TYPE_1, roundTrip(TYPE_1));
        assertEquals(TYPE_2, roundTrip(TYPE_2));
        assertEquals(TYPE_3, roundTrip(TYPE_3));
        assertEquals(TYPE_4, roundTrip(TYPE_4));
        assertEquals(new UUID(0xfcebe980b8b511e7L, 0xb2e456d40557c4dfL),
                     roundTrip(new UUID(0xfcebe980b8b511e7L, 0xb2e456d40557c4dfL)));
    }

    @Test
    public void encodedOrderedByVersion() throws IOException
    {
        List<UUID> uuids = Arrays.asList(TYPE_3, TYPE_2, TYPE_4, TYPE_1);
        assertEquals(sortWithCassandra(uuids, UUIDType.instance), sortWithRowKey(uuids));
    }

    @Test
    public void encodedOrderedByTimestampForTypeOneUUID() throws IOException
    {
        List<UUID> uuids = Arrays.asList(
        new UUID(0x1111111100001001L, 0xbfcb2b3ac88f55f3L),
        new UUID(0xffffffff00001000L, 0xbfcb2b3ac88f55f3L),
        new UUID(0x1111111100011000L, 0xbfcb2b3ac88f55f3L));
        assertEquals(sortWithCassandra(uuids, TimeUUIDType.instance), sortWithRowKey(uuids));
    }

    @Test
    public void encodedOrderedByUnsignedLSBTOnTypeOneUUIDWhenTimestampIsSame() throws IOException
    {
        List<UUID> uuids = Arrays.asList(
        new UUID(0xfcebe980b8b511e7L, 0xb35901d2b16f1157L),
        new UUID(0xfcebe980b8b511e7L, 0xb2e456d40557c4dfL),
        new UUID(0xfcebe980b8b511e7L, 0xb25901d2b16f1157L));
        assertEquals(sortWithCassandra(uuids, TimeUUIDType.instance), sortWithRowKey(uuids));

        uuids = Arrays.asList(
        UUIDGen.getTimeUUID(),
        UUIDGen.getTimeUUID(),
        UUIDGen.getTimeUUID());
        assertEquals(sortWithCassandra(uuids, TimeUUIDType.instance), sortWithRowKey(uuids));
    }

    @Test
    public void orderForRandomGeneratedUUIDs() throws Exception
    {
        ByteBuffer[] buffers = UUIDTypeTest.random(1000, (byte) 0x10);
        ArrayList<UUID> uuids = new ArrayList<>(buffers.length);
        for (int i = 0; i < buffers.length; i++)
        {
            ByteBuffer buffer = buffers[i];
            assert buffer.position() == 0;
            uuids.add(new UUID(buffer.getLong(), buffer.getLong(8)));
        }
        assertEquals(sortWithCassandra(uuids, TimeUUIDType.instance), sortWithRowKey(uuids));
    }

    @Test
    public void orderForRandomGeneratedUUIDsPermutations() throws Exception
    {
        Iterable<ByteBuffer[]> buffersCollection = UUIDTypeTest.permutations(1000, (byte) 0x10);
        for (ByteBuffer[] buffers : buffersCollection)
        {
            ArrayList<UUID> uuids = new ArrayList<>(buffers.length);
            for (int i = 0; i < buffers.length; i++)
            {
                ByteBuffer buffer = buffers[i];
                assert buffer.position() == 0;
                uuids.add(TimeUUIDType.instance.compose(buffer));
            }
            assertEquals(sortWithCassandra(uuids, TimeUUIDType.instance), sortWithRowKey(uuids));
        }
    }

    @Test
    public void orderForRandomGeneratedUUIDsPermutateInLSBs() throws Exception
    {
        int rounds = 1000;
        int randomCount = 10;
        Random random = new Random();
        random.setSeed(random.nextLong());

        for (int i = 0; i < rounds; i++)
        {
            ArrayList<UUID> uuids = new ArrayList<>(randomCount);
            for (int j = 0; j < randomCount; j++)
            {
                uuids.add(new UUID(0xfcebe980b8b511e7L, random.nextLong()));
            }
            assertEquals(sortWithCassandra(uuids, TimeUUIDType.instance), sortWithRowKey(uuids));
        }
    }

    private Object roundTrip(UUID uuid) throws IOException
    {
        UUIDRowKey uuidRowKey = new UUIDRowKey();
        byte[] serialized = uuidRowKey.serialize(uuid);
        return uuidRowKey.deserialize(new ImmutableBytesWritable(serialized));
    }

    private List<UUID> sortWithRowKey(List<UUID> uuids)
    {
        Bytes.ByteArrayComparator comparator = new Bytes.ByteArrayComparator();
        List<UUID> ret = new ArrayList<>(uuids);
        ret.sort((u1, u2) -> {
            UUIDRowKey rowKey = new UUIDRowKey();
            try
            {
                byte[] b1 = rowKey.serialize(u1);
                byte[] b2 = rowKey.serialize(u2);
                return comparator.compare(b1, b2);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });
        return ret;
    }

    private List<UUID> sortWithCassandra(List<UUID> uuids, AbstractType<UUID> type)
    {
        List<UUID> ret = new ArrayList<>(uuids);
        ret.sort((u1, u2) -> {
            ByteBuffer b1 = type.decompose(u1);
            ByteBuffer b2 = type.decompose(u2);
            return type.compare(b1, b2);
        });
        return ret;
    }
}