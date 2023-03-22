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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.db.BufferClustering;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.ClusteringPrefix;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.ByteType;
import org.apache.cassandra.db.marshal.CompositeType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.LocalPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.dht.RandomPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.utils.Pair;

/**
 * The format of the row key is:
 * | token (long) | partition keys | clustering keys |
 */
public class RowKeyEncoder
{

    private final static int EXTRA_KEY_NUM = 1; // Currently the only extra key is the token at the beginning

    public static byte[] encode(DecoratedKey partitionKey, ClusteringPrefix clustering, CFMetaData metadata)
    {
        int keyPartsSize = metadata.partitionKeyColumns().size() + clustering.size() + EXTRA_KEY_NUM;
        List<Pair<AbstractType, ByteBuffer>> keyParts = new ArrayList<>(keyPartsSize);
        appendTokenKeyPart(keyParts, partitionKey);
        appendPartitionKeyParts(keyParts, metadata.partitionKeyColumns(), partitionKey);
        appendClusteringKeyParts(keyParts, metadata.clusteringColumns(), clustering);
        assert keyParts.size() == keyPartsSize;
        return KeyPartsEncoder.encode(keyParts);
    }

    public static byte[] encode(DecoratedKey partitionKey, CFMetaData metadata)
    {
        int keyPartsSize = metadata.partitionKeyColumns().size() + EXTRA_KEY_NUM;
        List<Pair<AbstractType, ByteBuffer>> keyParts = new ArrayList<>(keyPartsSize);
        appendTokenKeyPart(keyParts, partitionKey);
        appendPartitionKeyParts(keyParts, metadata.partitionKeyColumns(), partitionKey);
        assert keyParts.size() == keyPartsSize;
        return KeyPartsEncoder.encode(keyParts);
    }

    public static byte[] encodeToken(Token token)
    {
        return KeyPartsEncoder.encode(Collections.singletonList(createTokenKeyPart(token)));
    }

    public static Clustering decodeClustering(byte[] key, CFMetaData metadata)
    {
        List<ColumnDefinition> clusteringColumns = metadata.clusteringColumns();
        if (clusteringColumns.size() == 0)
        {
            return Clustering.EMPTY;
        }

        List<ColumnDefinition> partitionKeyColumns = metadata.partitionKeyColumns();
        List<AbstractType> types = new ArrayList<>(partitionKeyColumns.size() + clusteringColumns.size() + EXTRA_KEY_NUM);
        types.add(getTokenDataType(metadata.partitioner));
        for (ColumnDefinition partitionKeyColumn : partitionKeyColumns)
        {
            types.add(partitionKeyColumn.type);
        }
        for (ColumnDefinition clusteringColumn : clusteringColumns)
        {
            types.add(clusteringColumn.type);
        }

        ByteBuffer[] decoded = KeyPartsEncoder.decode(key, types, partitionKeyColumns.size() + EXTRA_KEY_NUM);
        return new BufferClustering(decoded);
    }

    public static ByteBuffer decodeNonCompositePartitionKey(byte[] key, CFMetaData metadata)
    {
        assert metadata.partitionKeyColumns().size() == 1 : "currently only support non-composite partitioning key";

        List<AbstractType> types = new ArrayList<>(2);
        types.add(getTokenDataType(metadata.partitioner));
        types.add(metadata.partitionKeyColumns().get(0).type);

        ByteBuffer[] decoded = KeyPartsEncoder.decode(key, types);
        assert decoded.length > 1;
        return decoded[1];
    }

    public static ByteBuffer decodePartitionKey(byte[] key, CFMetaData metadata)
    {
        if (metadata.partitionKeyColumns().size() == 1)
            return decodeNonCompositePartitionKey(key, metadata);

        List<ColumnDefinition> partitionKeyColumns = metadata.partitionKeyColumns();
        List<AbstractType> types = new ArrayList<>(partitionKeyColumns.size() + 1);
        types.add(getTokenDataType(metadata.partitioner));
        for (ColumnDefinition partitionKeyColumn : partitionKeyColumns)
        {
            types.add(partitionKeyColumn.type);
        }

        ByteBuffer[] decoded = KeyPartsEncoder.decode(key, types);
        assert decoded.length > 1;
        return CompositeType.build(Arrays.copyOfRange(decoded, 1, decoded.length));
    }

    public static DecoratedKey decoratedPartitionKey(byte[] key, CFMetaData metadata)
    {
        ByteBuffer partitionKey = RowKeyEncoder.decodePartitionKey(key, metadata);
        return metadata.decorateKey(partitionKey);
    }

    private static void appendClusteringKeyParts(List<Pair<AbstractType, ByteBuffer>> keyParts, List<ColumnDefinition> clusteringColumns, ClusteringPrefix clustering)
    {
        for (int i = 0; i < clustering.size(); i++)
        {
            ColumnDefinition clusteringColumn = clusteringColumns.get(i);
            keyParts.add(Pair.create(clusteringColumn.type, clustering.get(i).duplicate()));
        }
    }

    private static void appendPartitionKeyParts(List<Pair<AbstractType, ByteBuffer>> keyParts, List<ColumnDefinition> partitionKeyColumns, DecoratedKey partitionKey)
    {
        ByteBuffer partitionKeyBuffer = partitionKey.getKey().duplicate();
        if (partitionKeyColumns.size() == 1)
        {
            // optimize for none composite situation
            keyParts.add(Pair.create(partitionKeyColumns.get(0).type, partitionKeyBuffer));
            return;
        }

        List<AbstractType<?>> types = new ArrayList<>(partitionKeyColumns.size());
        for (ColumnDefinition partitionKeyColumn : partitionKeyColumns)
        {
            types.add(partitionKeyColumn.type);
        }
        CompositeType compositeType = CompositeType.getInstance(types);
        ByteBuffer[] buffers = compositeType.split(partitionKeyBuffer);
        for (int i = 0; i < buffers.length; i++)
        {
            keyParts.add(Pair.create(types.get(i), buffers[i]));
        }
    }

    private static void appendTokenKeyPart(List<Pair<AbstractType, ByteBuffer>> keyParts, DecoratedKey partitionKey)
    {
        keyParts.add(createTokenKeyPart(partitionKey.getToken()));
    }

    private static Pair<AbstractType, ByteBuffer> createTokenKeyPart(Token token)
    {
        AbstractType type = getTokenDataType(token.getPartitioner());
        boolean useTokenValue = !(token.getPartitioner() instanceof LocalPartitioner);
        // Byte.MAX_BALUE is a arbitrary byte value for use in LocalPartitioner
        return Pair.create(type, type.decompose(useTokenValue ? token.getTokenValue() : Byte.MAX_VALUE));
    }

    private static AbstractType getTokenDataType(IPartitioner partitioner)
    {
        if (partitioner == Murmur3Partitioner.instance)
        {
            return LongType.instance;
        }

        if (partitioner == RandomPartitioner.instance)
        {
            return IntegerType.instance;
        }

        if (partitioner instanceof LocalPartitioner)
        {
            return ByteType.instance;
        }

        throw new RuntimeException("Partitioner: " + partitioner.getClass().getName() + " is not supported yet");
    }

    // caluated encoded partition key length base on token and partition keys types
    // return a positive number if possible to caculate, otherwise null
    public static Integer getEncodedTokenLength(CFMetaData metadata)
    {
        return KeyPartsEncoder.getEncodedLengthForType(getTokenDataType(metadata.partitioner));
    }
}
