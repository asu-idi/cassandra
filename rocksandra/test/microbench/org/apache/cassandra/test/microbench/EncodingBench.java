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

package org.apache.cassandra.test.microbench;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.db.BufferDecoratedKey;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.ByteType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.CompositeType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.TimestampType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.db.marshal.UUIDType;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.utils.Hex;
import org.apache.cassandra.utils.UUIDGen;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 4, time = 2)
@Fork(value = 2)
@Threads(4)
@State(Scope.Benchmark)
public class EncodingBench
{

    @Param({"BigIntegerType", "TimestampType", "UUIDType", "UTF8Type", "BytesType"})
    private String dataType;


    static String table = "thread";

    private CFMetaData metaPartition;

    private CFMetaData metaPartitionClustering;

    private CFMetaData metaTwoPartitionClustering;

    final static int TEST_DATA_SIZE = 100;

    private DecoratedKey[] partitionKeys = new DecoratedKey[TEST_DATA_SIZE];
    private Clustering[] clusteringKeys = new Clustering[TEST_DATA_SIZE];

    private DecoratedKey[] partitionKeys2 = new DecoratedKey[TEST_DATA_SIZE];
    private Clustering[] clusteringKeys2 = new Clustering[TEST_DATA_SIZE];

    private byte[][] encodedKeyPartition = new byte[TEST_DATA_SIZE][];

    private byte[][] encodedKeyPartitionClustering = new byte[TEST_DATA_SIZE][];

    private byte[][] encodedKeyTwoPartitionClustering = new byte[TEST_DATA_SIZE][];

    private int pos = 0;

    private void buildTestData(ByteBuffer obj, int pos)
    {
        obj.clear();

        // Build partition key test data
        partitionKeys[pos] = new BufferDecoratedKey(metaPartition.partitioner.getToken(obj.duplicate()), obj.duplicate());
        encodedKeyPartition[pos] = RowKeyEncoder.encode(partitionKeys[pos], metaPartition);

        // Build partition + clustering test data
        clusteringKeys[pos] = Clustering.make(obj.duplicate());
        encodedKeyPartitionClustering[pos] = RowKeyEncoder.encode(partitionKeys[pos], clusteringKeys[pos], metaPartitionClustering);

        // Build 2 partition + 2 clustering test data
        List<ColumnDefinition> partitionKeyColumns = metaTwoPartitionClustering.partitionKeyColumns();
        List<AbstractType<?>> types = new ArrayList<>(partitionKeyColumns.size());
        for (ColumnDefinition partitionKeyColumn : partitionKeyColumns)
        {
            types.add(partitionKeyColumn.type);
        }
        CompositeType compositeType = CompositeType.getInstance(types);

        ByteBuffer partKey = compositeType.builder().add(obj.duplicate()).add(obj.duplicate()).build();
        partitionKeys2[pos] = new BufferDecoratedKey(metaTwoPartitionClustering.partitioner.getToken(partKey), partKey);
        clusteringKeys2[pos] = Clustering.make(obj.duplicate(), obj.duplicate());

        encodedKeyTwoPartitionClustering[pos] = RowKeyEncoder.encode(partitionKeys2[pos], clusteringKeys2[pos], metaTwoPartitionClustering);
    }

    private void setupBigInteger()
    {
        metaPartition = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", IntegerType.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                    .addPartitionKey("key", IntegerType.instance)
                                                    .addClusteringColumn("c1", IntegerType.instance)
                                                    .addRegularColumn("value", ByteType.instance)
                                                    .withPartitioner(Murmur3Partitioner.instance)
                                                    .build();

        metaTwoPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                       .addPartitionKey("key1", IntegerType.instance)
                                                       .addPartitionKey("key2", IntegerType.instance)
                                                       .addClusteringColumn("c1", IntegerType.instance)
                                                       .addClusteringColumn("c2", IntegerType.instance)
                                                       .addRegularColumn("value", ByteType.instance)
                                                       .withPartitioner(Murmur3Partitioner.instance)
                                                       .build();

        for (int i = 0; i < TEST_DATA_SIZE; i++)
        {
            BigInteger bi = new BigInteger("1234567890123456");
            bi.multiply(BigInteger.valueOf(i));
            ByteBuffer pKey = ByteBuffer.wrap(bi.toByteArray());

            buildTestData(pKey, i);
        }
    }

    private void setupTimestamp()
    {
        metaPartition = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", TimestampType.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                    .addPartitionKey("key", TimestampType.instance)
                                                    .addClusteringColumn("c1", TimestampType.instance)
                                                    .addRegularColumn("value", ByteType.instance)
                                                    .withPartitioner(Murmur3Partitioner.instance)
                                                    .build();

        metaTwoPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                       .addPartitionKey("key1", TimestampType.instance)
                                                       .addPartitionKey("key2", TimestampType.instance)
                                                       .addClusteringColumn("c1", TimestampType.instance)
                                                       .addClusteringColumn("c2", TimestampType.instance)
                                                       .addRegularColumn("value", ByteType.instance)
                                                       .withPartitioner(Murmur3Partitioner.instance)
                                                       .build();

        for (int i = 0; i < TEST_DATA_SIZE; i++)
        {
            long time = System.currentTimeMillis();
            long timeInMillis = UUIDGen.unixTimestamp(UUIDGen.getRandomTimeUUIDFromMicros(time));
            ByteBuffer pKey = TimestampType.instance.fromTimeInMillis(timeInMillis);

            buildTestData(pKey, i);
        }
    }

    private void setupUUID()
    {
        metaPartition = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", UUIDType.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                    .addPartitionKey("key", UUIDType.instance)
                                                    .addClusteringColumn("c1", UUIDType.instance)
                                                    .addRegularColumn("value", ByteType.instance)
                                                    .withPartitioner(Murmur3Partitioner.instance)
                                                    .build();

        metaTwoPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                       .addPartitionKey("key1", UUIDType.instance)
                                                       .addPartitionKey("key2", UUIDType.instance)
                                                       .addClusteringColumn("c1", UUIDType.instance)
                                                       .addClusteringColumn("c2", UUIDType.instance)
                                                       .addRegularColumn("value", ByteType.instance)
                                                       .withPartitioner(Murmur3Partitioner.instance)
                                                       .build();

        for (int i = 0; i < TEST_DATA_SIZE; i++)
        {
            UUID uuid = UUID.randomUUID();
            ByteBuffer pKey = UUIDType.instance.decompose(uuid);

            buildTestData(pKey, i);
        }
    }

    private void setupUTF8()
    {
        metaPartition = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", UTF8Type.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaPartitionClustering = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", UTF8Type.instance)
                                          .addClusteringColumn("c1", UTF8Type.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaTwoPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                       .addPartitionKey("key1", UTF8Type.instance)
                                                       .addPartitionKey("key2", UTF8Type.instance)
                                                       .addClusteringColumn("c1", UTF8Type.instance)
                                                       .addClusteringColumn("c2", UTF8Type.instance)
                                                       .addRegularColumn("value", ByteType.instance)
                                                       .withPartitioner(Murmur3Partitioner.instance)
                                                       .build();

        for (int i = 0; i < TEST_DATA_SIZE; i++)
        {
            String str = String.format("hello %07d, %d.", i, i);
            ByteBuffer pKey = UTF8Type.instance.fromString(str);

            buildTestData(pKey, i);
        }
    }

    private void setupBytes() // blob
    {
        metaPartition = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", BytesType.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaPartitionClustering = CFMetaData.Builder.create("ks", table)
                                          .addPartitionKey("key", BytesType.instance)
                                          .addClusteringColumn("c1", BytesType.instance)
                                          .addRegularColumn("value", ByteType.instance)
                                          .withPartitioner(Murmur3Partitioner.instance)
                                          .build();

        metaTwoPartitionClustering = CFMetaData.Builder.create("ks", table)
                                                       .addPartitionKey("key1", BytesType.instance)
                                                       .addPartitionKey("key2", BytesType.instance)
                                                       .addClusteringColumn("c1", BytesType.instance)
                                                       .addClusteringColumn("c2", BytesType.instance)
                                                       .addRegularColumn("value", ByteType.instance)
                                                       .withPartitioner(Murmur3Partitioner.instance)
                                                       .build();

        for (int i = 0; i < TEST_DATA_SIZE; i++)
        {
            String str = String.format("hello %07d, %d.", i, i);
            byte[] val = str.getBytes();
            ByteBuffer pKey = BytesType.instance.fromString(Hex.bytesToHex(val));

            buildTestData(pKey, i);
        }
    }

    @Setup(Level.Trial)
    public void setup()
    {
        switch(dataType)
        {
            case "BigIntegerType":
                setupBigInteger();
                break;
            case "TimestampType":
                setupTimestamp();
                break;
            case "UUIDType":
                setupUUID();
                break;
            case "UTF8Type":
                setupUTF8();
                break;
            case "BytesType":
                setupBytes();
                break;
            default:
                throw new RuntimeException(String.format("unsupported datatype: %s", dataType));

        }
    }

    @Benchmark
    public void testEncodePartition()
    {
        RowKeyEncoder.encode(partitionKeys[((pos++) % TEST_DATA_SIZE)], metaPartition);
    }

    @Benchmark
    public void testDecodePartition()
    {
        RowKeyEncoder.decodeClustering(encodedKeyPartition[((pos++) % TEST_DATA_SIZE)], metaPartition);
    }

    @Benchmark
    public void testEncodePartitionAndClustering()
    {
        int i = (pos++) % TEST_DATA_SIZE;
        RowKeyEncoder.encode(partitionKeys[i], clusteringKeys[i], metaPartitionClustering);
    }

    @Benchmark
    public void testDecodePartitionAndClustering()
    {
        RowKeyEncoder.decodeClustering(encodedKeyPartitionClustering[((pos++) % TEST_DATA_SIZE)], metaPartitionClustering);
    }

    @Benchmark
    public void testEncodeTwoPartitionAndClustering()
    {
        int i = (pos++) % TEST_DATA_SIZE;
        RowKeyEncoder.encode(partitionKeys2[i], clusteringKeys2[i], metaTwoPartitionClustering);
    }

    @Benchmark
    public void testDecodeTwoPartitionAndClustering()
    {
        RowKeyEncoder.decodeClustering(encodedKeyTwoPartitionClustering[((pos++) % TEST_DATA_SIZE)], metaTwoPartitionClustering);
    }

    public static void main(String[] args)
    {
        EncodingBench tt = new EncodingBench();
        tt.dataType = "UUIDType";
        tt.setup();
    }
}
