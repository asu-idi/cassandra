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

import java.nio.ByteBuffer;

import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.Util;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.Int32Type;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.schema.KeyspaceParams;
import org.apache.cassandra.utils.ByteBufferUtil;

import static org.junit.Assert.assertEquals;
import static org.apache.cassandra.rocksdb.RocksDBUtils.getTokenLength;
import static org.junit.Assert.assertTrue;

public class RowKeyEncoderTest
{
    private static final String KEYSPACE = "RowKeyEncoderTest";
    private static final String CF_STANDARD_1 = "Standard1";
    private static final String CF_STANDARD_2 = "Standard2";

    static Util.PartitionerSwitcher sw;

    @BeforeClass
    public static void defineSchema() throws ConfigurationException
    {
        sw = Util.switchPartitioner(Murmur3Partitioner.instance);
        SchemaLoader.prepareServer();
        SchemaLoader.createKeyspace(KEYSPACE,
                                    KeyspaceParams.simple(1),
                                    SchemaLoader.standardCFMD(KEYSPACE, CF_STANDARD_1, 1, Int32Type.instance, BytesType.instance, BytesType.instance),
                                    SchemaLoader.standardCFMD(KEYSPACE, CF_STANDARD_2, 1, UTF8Type.instance, BytesType.instance, BytesType.instance));
    }

    @Test
    public void testDecodeNonCompositeIntPartitionKey() throws Exception
    {
        ColumnFamilyStore cfs = Keyspace.open(KEYSPACE).getColumnFamilyStore(CF_STANDARD_1);
        ByteBuffer testKey = ByteBufferUtil.bytes(1234);
        DecoratedKey dk = cfs.decorateKey(testKey);

        Clustering clustering = Clustering.make(ByteBufferUtil.bytes(567));
        byte[] encodedKey = RowKeyEncoder.encode(dk, clustering, cfs.metadata);

        ByteBuffer decodedKey = RowKeyEncoder.decodeNonCompositePartitionKey(encodedKey, cfs.metadata);

        assertEquals(decodedKey, testKey);
    }

    @Test
    public void testDecodeNonCompositeTextPartitionKey() throws Exception
    {
        ColumnFamilyStore cfs = Keyspace.open(KEYSPACE).getColumnFamilyStore(CF_STANDARD_2);
        ByteBuffer testKey = ByteBufferUtil.bytes("1234");
        DecoratedKey dk = cfs.decorateKey(testKey);

        Clustering clustering = Clustering.make(ByteBufferUtil.bytes("567"));
        byte[] encodedKey = RowKeyEncoder.encode(dk, clustering, cfs.metadata);

        ByteBuffer decodedKey = RowKeyEncoder.decodeNonCompositePartitionKey(encodedKey, cfs.metadata);

        assertEquals(decodedKey, testKey);
    }

    @Test
    public void testGetEncodedTokenLength()
    {
        ColumnFamilyStore cfs1 = Keyspace.open(KEYSPACE).getColumnFamilyStore(CF_STANDARD_1);
        assertEquals(8, getTokenLength(cfs1));
    }

    @Test
    public void testDecodeClusteringKey()
    {
        ColumnFamilyStore cfs = Keyspace.open(KEYSPACE).getColumnFamilyStore(CF_STANDARD_1);
        ByteBuffer testKey = ByteBufferUtil.bytes(1234);
        DecoratedKey dk = cfs.decorateKey(testKey);

        Clustering clustering = Clustering.make(ByteBufferUtil.bytes(567));
        byte[] encodedKey = RowKeyEncoder.encode(dk, clustering, cfs.metadata);

        Clustering decodedClustering = RowKeyEncoder.decodeClustering(encodedKey, cfs.metadata);

        assertEquals(clustering, decodedClustering);
    }
}
