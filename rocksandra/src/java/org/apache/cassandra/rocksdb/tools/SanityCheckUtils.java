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

package org.apache.cassandra.rocksdb.tools;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.db.AbstractReadCommandBuilder;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.PartitionRangeReadCommand;
import org.apache.cassandra.db.ReadOrderGroup;
import org.apache.cassandra.db.SinglePartitionReadCommand;
import org.apache.cassandra.db.partitions.UnfilteredPartitionIterator;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.utils.FBUtilities;

public class SanityCheckUtils
{

    private static final Logger LOGGER = LoggerFactory.getLogger(SanityCheckUtils.class);

    /**
     * Issue a full partition read to the Cassandra storage engine of a given cfs, then for each partion compare the result with the one from Rocksdb Engine.
     * Doesn't work with expring data.
     *
     * @param cfs ColumnFamilyStore which has double write enabled.
     * @param randomStartToken Choose a random token in the ring to start with instead of minimal token.
     * @param limit number of rows to check, 0 means unlimited.
     * @param verbose write mismatch row's detail into log.
     * @return Comparator Report.
     */
    public static RowIteratorSanityCheck.Report checkSanity(ColumnFamilyStore cfs, boolean randomStartToken, long limit, boolean verbose)
    {
        int nowInSecond = FBUtilities.nowInSeconds();
        Token fromToken = randomStartToken ? cfs.metadata.partitioner.getRandomToken() : cfs.metadata.partitioner.getMinimumToken();
        InternalPartitionRangeReadCommand command = new InternalPartitionRangeReadCommand(
            (PartitionRangeReadCommand) (new AbstractReadCommandBuilder.PartitionRangeBuilder(cfs).fromToken(fromToken, true))
                                        .withNowInSeconds(nowInSecond).build());
        ReadOrderGroup orderGroup = command.startOrderGroup();
        UnfilteredPartitionIterator partitionIterator = command.queryStorageInternal(cfs, orderGroup);
        RowIteratorSanityCheck check = new RowIteratorSanityCheck(cfs.metadata, fromToken, nowInSecond, verbose);
        long count = 0;
        while (partitionIterator.hasNext())
        {
            UnfilteredRowIterator cassandraRowIterator = partitionIterator.next();

            UnfilteredRowIterator rocksdbRowIterator = cfs.engine.queryStorage(
               cfs,
               SinglePartitionReadCommand.fullPartitionRead(cfs.metadata, nowInSecond,
                                                            cassandraRowIterator.partitionKey()));
            check.compare(cassandraRowIterator.partitionKey(), cassandraRowIterator, rocksdbRowIterator);

            count ++;
            if (limit > 0 && count >= limit)
                break;
        }
        return check.getReport();
    }

    private static final class InternalPartitionRangeReadCommand extends PartitionRangeReadCommand
    {
        private InternalPartitionRangeReadCommand(PartitionRangeReadCommand original)
        {
            super(original.isDigestQuery(),
                  original.digestVersion(),
                  original.isForThrift(),
                  original.metadata(),
                  original.nowInSec(),
                  original.columnFilter(),
                  original.rowFilter(),
                  original.limits(),
                  original.dataRange(),
                  original.indexMetadata());
        }

        private UnfilteredPartitionIterator queryStorageInternal(ColumnFamilyStore cfs,
                                                                 ReadOrderGroup orderGroup)
        {
            return queryStorage(cfs, orderGroup);
        }
    }
}
