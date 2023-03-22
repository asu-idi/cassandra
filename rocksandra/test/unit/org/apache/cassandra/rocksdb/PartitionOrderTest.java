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

package org.apache.cassandra.rocksdb;


import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import org.apache.cassandra.Util;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.filter.ColumnFilter;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.db.rows.BufferCell;
import org.apache.cassandra.db.rows.ColumnData;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.rocksdb.encoding.value.RowValueEncoder;
import org.apache.cassandra.utils.Pair;
import org.apache.cassandra.utils.UUIDGen;

import static junit.framework.Assert.assertEquals;

public class PartitionOrderTest extends RocksDBTestBase
{

    private static final int SAMPLES = 100;

    @Test
    public void murmur3Partitioner() throws Throwable
    {
        try (Util.PartitionerSwitcher ignored = new Util.PartitionerSwitcher(Murmur3Partitioner.instance))
        {
            List<Pair<Token, BigInteger>> tokenToIds = new ArrayList<>(SAMPLES);
            for (int i = 0; i < SAMPLES; i++)
            {
                BigInteger userId = BigInteger.valueOf(ThreadLocalRandom.current().nextLong());
                Token token = ((IPartitioner) Murmur3Partitioner.instance).getToken(ByteBuffer.wrap(userId.toByteArray()));
                tokenToIds.add(Pair.create(token, userId));
            }

            createTable("CREATE TABLE %s (userid varint, t timeuuid, firstname text, PRIMARY KEY(userid, t))");

            for (Pair<Token, BigInteger> tokenToId : tokenToIds)
            {
                execute("INSERT INTO %s (userid, t, firstname) VALUES (?, ?, ?)",
                        tokenToId.right, UUIDGen.getTimeUUID(), tokenToId.right.toString());
            }

            for (Pair<Token, BigInteger> tokenToId : tokenToIds)
            {
                assertRows(execute("SELECT userid, firstname FROM %s WHERE userid=?", tokenToId.right),
                           row(tokenToId.right, tokenToId.right.toString()));
            }

            tokenToIds.sort(Comparator.comparing(o -> o.left));
            List<String> expectedValues = new ArrayList<>(SAMPLES);
            for (Pair<Token, BigInteger> tokenToId : tokenToIds)
            {
                expectedValues.add(tokenToId.right.toString());
            }

            assertEquals(expectedValues, scannRocksDBValues(UTF8Type.instance));

        }
    }

    private List<Object> scannRocksDBValues(AbstractType valueType)
    {
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBCF rocksDB = RocksDBEngine.getRocksDBCF(cfs);
        List<Object> result = new ArrayList<>();
        try (RocksDBIteratorAdapter rocksIterator = rocksDB.newIterator(Util.dk("")))
        {
            rocksIterator.seekToFirst();

            while (rocksIterator.isValid())
            {
                List<ColumnData> dataBuffer = new ArrayList<>();
                RowValueEncoder.decode(cfs.metadata, ColumnFilter.all(cfs.metadata), ByteBuffer.wrap(rocksIterator.value()), dataBuffer);

                ByteBuffer value = ((BufferCell) dataBuffer.get(0)).value();
                result.add(valueType.compose(value));
                rocksIterator.next();
            }
        }
        return result;
    }
}
