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

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.Slice;
import org.apache.cassandra.rocksdb.encoding.RowKeyEncoder;
import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;

enum PartitionIterOrder
{
    NORMAL
    {
        public void moveForward(RocksDBIteratorAdapter rocksIterator)
        {
            rocksIterator.next();
        }

        public void seekToStart(RocksDBIteratorAdapter rocksIterator, DecoratedKey partitionKey, byte[] partitionKeyBytes, Slice slice, CFMetaData metaData)
        {
            // if start clustering condition is not specified, seek to partition start
            if (slice.start() == Slice.Bound.BOTTOM)
            {
                rocksIterator.seek(partitionKeyBytes);
                return;
            }

            byte[] minKey = RowKeyEncoder.encode(partitionKey, slice.start().clustering(), metaData);
            rocksIterator.seek(minKey);
        }
    },

    REVERSED
    {
        public void moveForward(RocksDBIteratorAdapter rocksIterator)
        {
            rocksIterator.prev();
        }

        // For finding a start point for reverse scan we need choose a most close forward scan starting point and then
        // scan forward to figure out the last key within the end bound.
        public void seekToStart(RocksDBIteratorAdapter rocksIterator, DecoratedKey partitionKey, byte[] partitionKeyBytes, Slice slice, CFMetaData metaData)
        {
            rocksIterator.seek(findForwardScanStartingPoint(partitionKey, partitionKeyBytes, slice, metaData));
            // if data is not valid right after seek, we likely hit end of database, reset iterator to the partition start
            if (!rocksIterator.isValid())
            {
                rocksIterator.seek(partitionKeyBytes);
            }

            byte[] lastKeyWithinEndBound = findLastKeyWithinEndBound(rocksIterator, partitionKeyBytes, slice, metaData);

            if (lastKeyWithinEndBound != null)
            {
                rocksIterator.seek(lastKeyWithinEndBound);
            }
        }

        private byte[] findLastKeyWithinEndBound(RocksDBIteratorAdapter rocksIterator, byte[] partitionKeyBytes, Slice slice, CFMetaData metaData)
        {
            byte[] lastKeyWithinEndBound = null;
            while (rocksIterator.isValid())
            {
                byte[] key = rocksIterator.key();
                if (!Bytes.startsWith(key, partitionKeyBytes))
                {
                    break;
                }

                Clustering clustering = RowKeyEncoder.decodeClustering(key, metaData);
                if (metaData.comparator.compare(clustering, slice.end().clustering()) > 0)
                {
                    break;
                }
                lastKeyWithinEndBound = key;
                rocksIterator.next();
            }
            return lastKeyWithinEndBound;
        }

        private byte[] findForwardScanStartingPoint(DecoratedKey partitionKey, byte[] partitionKeyBytes, Slice slice, CFMetaData metaData)
        {
            // if end clustering condition is specified, start scan forward from it
            if (slice.end() != Slice.Bound.TOP)
            {
                return RowKeyEncoder.encode(partitionKey, slice.end().clustering(), metaData);
            }

            // if there is start clustering condition, start forward scan from start clustering condition
            if (slice.start() != Slice.Bound.BOTTOM)
            {
                return RowKeyEncoder.encode(partitionKey, slice.start().clustering(), metaData);
            }

            // if there is no start or end clustering condition, the best we can do is start from partition start
            return partitionKeyBytes;
        }
    };

    public abstract void moveForward(RocksDBIteratorAdapter rocksIterator);

    // seekToStart is responsible move rocksdb iterator to a starting place that can make following scan cover all
    // potential data. It does not need to be where the upper or lower boundary exactly started, since the following scan
    // will filter out data fall out of the upper or lower boundary.
    public abstract void seekToStart(RocksDBIteratorAdapter rocksIterator, DecoratedKey partitionKey, byte[] partitionKeyBytes, Slice slice, CFMetaData metaData);
}
