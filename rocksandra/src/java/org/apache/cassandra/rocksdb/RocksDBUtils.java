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

import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.LocalPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.dht.RandomPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.rocksdb.streaming.RocksDBStreamUtils;

public class RocksDBUtils
{

    public static Token getMaxToken(IPartitioner partitioner)
    {
        if (partitioner instanceof Murmur3Partitioner)
        {
            return new Murmur3Partitioner.LongToken(Murmur3Partitioner.MAXIMUM);
        }
        else if (partitioner instanceof RandomPartitioner)
        {
            return new RandomPartitioner.BigIntegerToken(RandomPartitioner.MAXIMUM);
        }
        else if (partitioner instanceof LocalPartitioner)
        {
            // Arbitrary token value used for LocalPartitioner
            return new RandomPartitioner.BigIntegerToken(RandomPartitioner.MAXIMUM);
        }
        throw new NotImplementedException(partitioner.getClass().getName() + "is not supported");
    }

    public static Token getMinToken(IPartitioner partitioner)
    {
        if (partitioner instanceof Murmur3Partitioner)
        {
            return Murmur3Partitioner.MINIMUM;
        }
        else if (partitioner instanceof RandomPartitioner)
        {
            return RandomPartitioner.MINIMUM;
        }
        else if (partitioner instanceof LocalPartitioner)
        {
            // Arbitrary token value used for LocalPartitioner
            return RandomPartitioner.MINIMUM;
        }
        throw new NotImplementedException(partitioner.getClass().getName() + "is not supported");
    }

    public static int getTokenLength(ColumnFamilyStore cfs)
    {
        Integer tokenLength = RowKeyEncoder.getEncodedTokenLength(cfs.metadata);
        if (tokenLength == null)
        {
            throw new UnsupportedOperationException("Only fix token length partitioner is supported by Rocksandra");
        }
        return tokenLength;
    }

    public static int getTokenLength(UUID cfId)
    {
        ColumnFamilyStore cfs = RocksDBStreamUtils.getColumnFamilyStore(cfId);
        return getTokenLength(cfs);
    }
}
