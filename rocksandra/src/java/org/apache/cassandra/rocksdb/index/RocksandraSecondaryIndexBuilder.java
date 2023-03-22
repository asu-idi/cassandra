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

package org.apache.cassandra.rocksdb.index;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.LivenessInfo;
import org.apache.cassandra.db.filter.ColumnFilter;
import org.apache.cassandra.db.partitions.PartitionUpdate;
import org.apache.cassandra.db.rows.BTreeRow;
import org.apache.cassandra.db.rows.Cell;
import org.apache.cassandra.db.rows.ColumnData;
import org.apache.cassandra.db.rows.Row;
import org.apache.cassandra.exceptions.StorageEngineException;
import org.apache.cassandra.index.transactions.UpdateTransaction;
import org.apache.cassandra.rocksdb.RocksDBCF;
import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.RocksDBIteratorAdapter;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.rocksdb.encoding.value.RowValueEncoder;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.concurrent.OpOrder;
import org.rocksdb.ReadOptions;

public class RocksandraSecondaryIndexBuilder
{
    private static final Logger logger = LoggerFactory.getLogger(RocksandraSecondaryIndexBuilder.class);

    private final ColumnFamilyStore cfs;

    public RocksandraSecondaryIndexBuilder(ColumnFamilyStore cfs)
    {
        this.cfs = cfs;
    }

    public void build()
    {
        OpOrder writeOrder = new OpOrder();
        OpOrder.Group opGroup = writeOrder.start();
        Row.Builder rowBuilder = BTreeRow.sortedBuilder();

        for (int shardId = 0; shardId < RocksDBConfigs.NUM_SHARD; shardId++)
        {
            RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfs);
            RocksDBIteratorAdapter iterator = rocksDBCF.newShardIterator(shardId,
                                                                         new ReadOptions().setReadaheadSize(RocksDBConfigs.STREAMING_READ_AHEAD_BUFFER_SIZE));
            iterator.seekToFirst();
            while (iterator.isValid())
            {
                byte[] key = iterator.key();
                byte[] value = iterator.value();
                ByteBuffer partitionKey = RowKeyEncoder.decodeNonCompositePartitionKey(key, cfs.metadata);

                List<ColumnData> dataBuffer = new ArrayList<>();
                RowValueEncoder.decode(cfs.metadata, ColumnFilter.all(cfs.metadata), ByteBuffer.wrap(value), dataBuffer);

                rowBuilder.newRow(RowKeyEncoder.decodeClustering(key, cfs.metadata));
                rowBuilder.addPrimaryKeyLivenessInfo(LivenessInfo.EMPTY);

                for (ColumnData columnData : dataBuffer)
                    rowBuilder.addCell((Cell) columnData);

                Row row = rowBuilder.build();

                PartitionUpdate upd = PartitionUpdate.singleRowUpdate(cfs.metadata, partitionKey, row);
                UpdateTransaction indexTransaction = cfs.indexManager.newUpdateTransaction(upd,
                                                                                           opGroup,
                                                                                           FBUtilities.nowInSeconds());

                try
                {
                    indexTransaction.start();
                    indexTransaction.onInserted(row);
                }
                catch (RuntimeException e)
                {
                    logger.error(e.toString(), e);
                    throw new StorageEngineException("Index update failed", e);
                }
                finally
                {
                    indexTransaction.commit();
                }

                iterator.next();
            }
        }
    }
}
