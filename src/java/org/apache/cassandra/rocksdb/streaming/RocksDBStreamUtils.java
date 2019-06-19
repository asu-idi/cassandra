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

package org.apache.cassandra.rocksdb.streaming;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.NotImplementedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.Schema;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.db.rows.Cell;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.dht.RandomPartitioner;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.rocksdb.RocksCFName;
import org.apache.cassandra.rocksdb.RocksDBCF;
import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.RocksDBProperty;
import org.apache.cassandra.streaming.ProgressInfo;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.utils.Pair;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.IngestExternalFileOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import static org.apache.cassandra.rocksdb.RocksDBUtils.getMaxToken;
import static org.apache.cassandra.rocksdb.RocksDBUtils.getMinToken;

public class RocksDBStreamUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RocksDBStreamWriter.class);
    private static final long INGESTION_WAIT_MS = 100;
    public static final byte[] EOF = new byte[]{'\0'};
    public static final byte[] MORE = new byte[]{'1'};

    public static ColumnFamilyStore getColumnFamilyStore(UUID cfId)
    {
        Pair<String, String> kscf = Schema.instance.getCF(cfId);
        ColumnFamilyStore cfs = null;
        if (kscf != null)
            cfs = Keyspace.open(kscf.left).getColumnFamilyStore(kscf.right);

        if (kscf == null || cfs == null)
        {
            LOGGER.warn("CF " + cfId + " was dropped during streaming");
        }
        return cfs;
    }

    /**
     * ingest sstable file to rocksdb default (data) column family
     */
    public static void ingestRocksSstable(UUID cfId, int shardId, String sstFile) throws RocksDBException
    {
        ColumnFamilyStore cfs = getColumnFamilyStore(cfId);
        RocksDBCF rocksDBCF = RocksDBEngine.getRocksDBCF(cfId);
        if (cfs == null || rocksDBCF == null)
            return;

        rocksDBCF.ingestRocksSstable(shardId, sstFile, INGESTION_WAIT_MS, RocksCFName.DEFAULT);
    }

    /**
     * Normalize ranges: ensure each range is not overlapped and sorted in asending order.
     *
     * {@link org.apache.cassandra.dht.Range#normalize(Collection)}} deoverlap overlapped ranges, and unwrap wrapped ranges
     * (whose range.left > range.right) by splitting them to two ranges (range.left, minToken] and (minToken, range.right].
     * This is no ideal for RocksDB engine and makes logic complicated as we expect the normalize should ensure all range's left < right.
     * This function fix this issue by converting all ranges whose upper bound is minToken to maxToken.
     * This shouldn't affect the correctness as minToken should be smaller than any possible token according
     * to {@link IPartitioner#getMinimumToken()}.
     *
     * @return normalized ranges.
     */
    public static Collection<Range<Token>> normalizeRanges(Collection<Range<Token>> ranges)
    {
        if (ranges.isEmpty())
            return ranges;

        ranges = Range.normalize(ranges);
        List<Range<Token>> normalized = new ArrayList<>();
        for (Range<Token> range : ranges)
        {
            if (range.right.isMinimum())
                range = new Range<>(range.left, getMaxToken(range.left.getPartitioner()));

            normalized.add(range);
        }
        return normalized;
    }

    /**
     * Calcuate the complement of a given ranges (all the tokens not in the given range) in the token ring.
     */
    public static Collection<Range<Token>> calcluateComplementRanges(IPartitioner partitioner, Collection<Range<Token>> ranges)
    {
        Collection<Range<Token>> normalized = normalizeRanges(ranges);

        ArrayList<Range<Token>> result = new ArrayList<>(ranges.size() + 1);

        Token start = getMinToken(partitioner);
        for (Range<Token> range : normalized)
        {
            Token end = range.left;
            if (!start.equals(end))
            {
                result.add(new Range<>(start, end));
            }
            start = range.right;
        }

        Token maxToken = getMaxToken(partitioner);
        if (!start.equals(maxToken))
        {
            result.add(new Range<>(start, maxToken));
        }
        return result;
    }

    public static String toString(Cell cell)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cell tombstone:").append(cell.isTombstone())
            .append(", expiring:").append(cell.isExpiring())
            .append(", timestamp:").append(cell.timestamp())
            .append(", ttl:").append(cell.ttl())
            .append(", value:").append(Hex.encodeHex(cell.value().array()));
        return sb.toString();
    }

    public static double getRangeSpaceSize(Range<Token> range)
    {
        if (range == null)
            return 0;

        IPartitioner partitioner = range.left.getPartitioner();

        if (partitioner instanceof Murmur3Partitioner)
        {
            return range.left.size(range.right);
        }

        if (partitioner instanceof RandomPartitioner)
        {
            BigInteger fullTokenSpaceSize = RandomPartitioner.MAXIMUM;
            RandomPartitioner.BigIntegerToken left = (RandomPartitioner.BigIntegerToken) range.left;
            RandomPartitioner.BigIntegerToken right = (RandomPartitioner.BigIntegerToken) range.right;
            BigInteger rangesSize = right.getTokenValue().subtract(left.getTokenValue());
            return rangesSize.doubleValue() / fullTokenSpaceSize.doubleValue();
        }

        throw new NotImplementedException(partitioner.getClass().getName() + "is not supported");
    }

    public static long estimateDataSize(RocksDBCF rocksDBCF, Range<Token> normalizedRange)
    {
        try
        {
            long estimatedDataSize = RocksDBProperty.getEstimatedLiveDataSize(rocksDBCF);
            return (long)(estimatedDataSize * getRangeSpaceSize(normalizedRange));
        } catch (RocksDBException e)
        {
            LOGGER.warn("Failed to estimate data size", e);
            return 0;
        }
    }

    public static long estimateNumKeys(RocksDBCF rocksDBCF, Range<Token> normalizedRange)
    {
        try
        {
            long estimateNumKeys = RocksDBProperty.getEstimatedNumKeys(rocksDBCF);
            return (long)(estimateNumKeys * getRangeSpaceSize(normalizedRange));
        } catch (RocksDBException e)
        {
            LOGGER.warn("Failed to estimate num of keys", e);
            return 0;
        }
    }

    public static void rocksDBProgress(StreamSession session, String fileName, ProgressInfo.Direction direction, long bytes, long keys, long estimatedTotalKeys, boolean completed)
    {
        if (session == null)
            return;
        RocksDBProgressInfo progress = new RocksDBProgressInfo(session.peer, session.getSessionInfo().sessionIndex, fileName, direction, bytes, keys, estimatedTotalKeys, completed);
        session.progress(progress);
    }

    public static boolean isRocksDBBacked(UUID cfId)
    {
        return getColumnFamilyStore(cfId).isRocksDBBacked();
    }
}
