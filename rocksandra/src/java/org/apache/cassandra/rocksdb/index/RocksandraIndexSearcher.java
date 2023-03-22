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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DataRange;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.PartitionPosition;
import org.apache.cassandra.db.PartitionRangeReadCommand;
import org.apache.cassandra.db.ReadCommand;
import org.apache.cassandra.db.ReadOrderGroup;
import org.apache.cassandra.db.SinglePartitionReadCommand;
import org.apache.cassandra.db.Slice;
import org.apache.cassandra.db.Slices;
import org.apache.cassandra.db.filter.ClusteringIndexFilter;
import org.apache.cassandra.db.filter.ClusteringIndexSliceFilter;
import org.apache.cassandra.db.filter.ColumnFilter;
import org.apache.cassandra.db.filter.RowFilter;
import org.apache.cassandra.db.partitions.UnfilteredPartitionIterator;
import org.apache.cassandra.db.rows.RowIterator;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import org.apache.cassandra.db.rows.UnfilteredRowIterators;
import org.apache.cassandra.dht.AbstractBounds;
import org.apache.cassandra.index.Index;

public abstract class RocksandraIndexSearcher implements Index.Searcher
{
    private static final Logger logger = LoggerFactory.getLogger(RocksandraIndexSearcher.class);

    private final RowFilter.Expression expression;
    protected final RocksandraClusteringColumnIndex index;
    protected final ReadCommand command;

    public RocksandraIndexSearcher(ReadCommand command,
                                   RowFilter.Expression expression,
                                   RocksandraClusteringColumnIndex index)
    {
        this.command = command;
        this.expression = expression;
        this.index = index;
    }

    @SuppressWarnings("resource") // Both the OpOrder and 'indexIter' are closed on exception, or through the closing of the result
    // of this method.
    public UnfilteredPartitionIterator search(ReadOrderGroup orderGroup)
    {
        // the value of the index expression is the partition key in the index table
        DecoratedKey indexKey = index.getBackingTable().get().decorateKey(expression.getIndexValue());
        UnfilteredRowIterator indexIter = queryIndex(indexKey, command, orderGroup);
        try
        {
            return queryDataFromIndex(indexKey, UnfilteredRowIterators.filter(indexIter, command.nowInSec()), command, orderGroup);
        }
        catch (RuntimeException | Error e)
        {
            indexIter.close();
            throw e;
        }
    }

    private UnfilteredRowIterator queryIndex(DecoratedKey indexKey, ReadCommand command, ReadOrderGroup orderGroup)
    {
        ClusteringIndexFilter filter = makeIndexFilter(command);
        ColumnFamilyStore indexCfs = index.getBackingTable().get();
        CFMetaData indexCfm = indexCfs.metadata;
        return SinglePartitionReadCommand.create(indexCfm, command.nowInSec(), indexKey, ColumnFilter.all(indexCfm), filter)
                                         .queryMemtableAndDisk(indexCfs, orderGroup.indexReadOpOrderGroup());
    }

    private ClusteringIndexFilter makeIndexFilter(ReadCommand command)
    {
        // all commands come in the form of PartitionRangeReadCommand
        assert command instanceof PartitionRangeReadCommand;

        DataRange dataRange = ((PartitionRangeReadCommand)command).dataRange();
        AbstractBounds<PartitionPosition> range = dataRange.keyRange();

        Slice slice = Slice.ALL;

        /*
         * XXX: If the range requested is a token range, we'll have to start at the beginning (and stop at the end) of
         * the indexed row unfortunately (which will be inefficient), because we have no way to intuit the smallest possible
         * key having a given token. A potential fix would be to actually store the token along the key in the indexed row.
         */
        if (range.left instanceof DecoratedKey)
        {
            // the right hand side of the range may not be a DecoratedKey (for instance if we're paging),
            // but if it is, we can optimise slightly by restricting the slice
            if (range.right instanceof DecoratedKey)
            {

                DecoratedKey startKey = (DecoratedKey) range.left;
                DecoratedKey endKey = (DecoratedKey) range.right;

                Slice.Bound start = Slice.Bound.BOTTOM;
                Slice.Bound end = Slice.Bound.TOP;

                /*
                 * For index queries over a range, we can't do a whole lot better than querying everything for the key range, though for
                 * slice queries where we can slightly restrict the beginning and end.
                 */
                if (!dataRange.isNamesQuery())
                {
                    ClusteringIndexSliceFilter startSliceFilter = ((ClusteringIndexSliceFilter) dataRange.clusteringIndexFilter(
                                                                                                                               startKey));
                    ClusteringIndexSliceFilter endSliceFilter = ((ClusteringIndexSliceFilter) dataRange.clusteringIndexFilter(
                                                                                                                             endKey));

                    // We can't effectively support reversed queries when we have a range, so we don't support it
                    // (or through post-query reordering) and shouldn't get there.
                    assert !startSliceFilter.isReversed() && !endSliceFilter.isReversed();

                    Slices startSlices = startSliceFilter.requestedSlices();
                    Slices endSlices = endSliceFilter.requestedSlices();

                    if (startSlices.size() > 0)
                        start = startSlices.get(0).start();

                    if (endSlices.size() > 0)
                        end = endSlices.get(endSlices.size() - 1).end();
                }

                slice = Slice.make(makeIndexBound(startKey.getKey(), start),
                                   makeIndexBound(endKey.getKey(), end));
            }
            else
            {
                // otherwise, just start the index slice from the key we do have
                slice = Slice.make(makeIndexBound(((DecoratedKey)range.left).getKey(), Slice.Bound.BOTTOM),
                                   Slice.Bound.TOP);
            }
        }
        return new ClusteringIndexSliceFilter(Slices.with(index.getIndexComparator(), slice), false);
    }

    private Slice.Bound makeIndexBound(ByteBuffer rowKey, Slice.Bound bound)
    {
        return index.buildIndexClusteringPrefix(rowKey, bound, null)
                                 .buildBound(bound.isStart(), bound.isInclusive());
    }

    protected abstract UnfilteredPartitionIterator queryDataFromIndex(DecoratedKey indexKey,
                                                                      RowIterator indexHits,
                                                                      ReadCommand command,
                                                                      ReadOrderGroup orderGroup);
}
