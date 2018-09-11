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

import com.google.common.collect.AbstractIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DataRange;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.PartitionPosition;
import org.apache.cassandra.db.filter.ClusteringIndexFilter;
import org.apache.cassandra.db.filter.ColumnFilter;
import org.apache.cassandra.db.partitions.UnfilteredPartitionIterator;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import org.apache.cassandra.dht.AbstractBounds;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;
import org.apache.cassandra.utils.Pair;
import org.rocksdb.ReadOptions;

public class RocksDBPartitionIterator extends AbstractIterator<UnfilteredRowIterator> implements UnfilteredPartitionIterator
{
    protected static final Logger logger = LoggerFactory.getLogger(RocksDBPartitionIterator.class);

    private final ColumnFamilyStore cfs;
    private final ColumnFilter columnFilter;
    private final DataRange dataRange;
    private final Range<Token> tokenRange;
    private final int totalShards = RocksDBConfigs.NUM_SHARD;
    private int currentShard = 0;
    private RocksDBCF rocksDBCF;
    private RocksDBIteratorAdapter currentIterator = null;
    private DecoratedKey previousDecoratedKey = null;


    public RocksDBPartitionIterator(ColumnFamilyStore cfs,
                                    ColumnFilter columnFilter,
                                    DataRange dataRange)
    {
        this.cfs = cfs;
        this.columnFilter = columnFilter;
        this.dataRange = dataRange;
        AbstractBounds<PartitionPosition> keyRange = dataRange.keyRange();
        this.tokenRange = new Range<>(keyRange.left.getToken(),
                                      keyRange.right.isMinimum() ?
                                      RocksDBUtils.getMaxToken(keyRange.right.getPartitioner()) :
                                      keyRange.right.getToken());

        this.rocksDBCF = ((RocksDBEngine) cfs.engine).rocksDBFamily.get(new Pair<>(cfs.metadata.cfId, cfs.name));
        while (currentShard < totalShards && currentIterator == null)
        {
            currentIterator = getValidIterator(currentShard);
            currentShard++;
        }
    }

    @Override
    public boolean isForThrift()
    {
        return false;
    }

    @Override
    public CFMetaData metadata()
    {
        return cfs.metadata;
    }

    @Override
    public void close()
    {
        if (currentIterator != null)
            currentIterator.close();
    }

    @Override
    public UnfilteredRowIterator computeNext()
    {
        if (isCurrentSameAsPreviousPartition())
            moveToNextPartition(currentIterator, previousDecoratedKey);

        if (currentIterator != null)
        {
            if (!isInRange(currentIterator))
            {
                currentIterator.close();
                currentIterator = null;
                while (currentShard < totalShards && currentIterator == null)
                {
                    currentIterator = getValidIterator(currentShard);
                    currentShard++;
                }
            }
        }

        if (currentIterator == null)
            return endOfData();

        return getUnfilteredRowIterator(currentIterator);
    }

    private RocksDBIteratorAdapter getValidIterator(int shardId)
    {
        RocksDBIteratorAdapter iterator = rocksDBCF.newShardIterator(shardId, new ReadOptions());
        iterator.seek(RowKeyEncoder.encodeToken(tokenRange.left));
        if (iterator.isValid() && isInRange(iterator))
            return iterator;
        else
            iterator.close();

        return null;
    }

    private UnfilteredRowIterator getUnfilteredRowIterator(RocksDBIteratorAdapter iterator)
    {
        previousDecoratedKey = RowKeyEncoder.decoratedPartitionKey(iterator.key(), cfs.metadata);

        RocksDBPartition partition =
        new RocksDBPartition(((RocksDBEngine) cfs.engine).rocksDBFamily.get(new Pair<>(cfs.metadata.cfId, cfs.name)),
                             previousDecoratedKey,
                             cfs.metadata);

        ClusteringIndexFilter clusteringIndexFilter = dataRange.clusteringIndexFilter(previousDecoratedKey);

        return clusteringIndexFilter.getUnfilteredRowIterator(columnFilter, partition);
    }

    private boolean isCurrentSameAsPreviousPartition()
    {
        if (previousDecoratedKey == null || currentIterator == null || !currentIterator.isValid())
            return false;

        DecoratedKey currentDecoratedKey = RowKeyEncoder.decoratedPartitionKey(currentIterator.key(), cfs.metadata);
        return (currentDecoratedKey.compareTo(previousDecoratedKey) == 0);
    }

    private void moveToNextPartition(RocksDBIteratorAdapter iterator, DecoratedKey decoratedKey)
    {
        byte[] partitionKeyBytes = RowKeyEncoder.encode(decoratedKey, cfs.metadata);

        if (iterator == null || !iterator.isValid())
            return;

        while (iterator.isValid())
        {
            iterator.next();

            if (!Bytes.startsWith(iterator.key(), partitionKeyBytes))
                return;
        }
    }

    private boolean isInRange(RocksDBIteratorAdapter iterator)
    {
        if (iterator == null || !iterator.isValid())
            return false;

        DecoratedKey decoratedPartitionKey = RowKeyEncoder.decoratedPartitionKey(iterator.key(), cfs.metadata);

        if (!dataRange.keyRange().isStartInclusive() && dataRange.keyRange().left.equals(decoratedPartitionKey))
        {
            moveToNextPartition(iterator, decoratedPartitionKey);
            return isInRange(iterator);
        }

        return dataRange.keyRange().contains(decoratedPartitionKey);
    }
}
