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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.rows.Row;
import org.apache.cassandra.db.rows.Unfiltered;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import org.apache.cassandra.dht.Token;

/**
 * Compare the rowiterators from Rocksdb and Cassandra and record the consistency.
 */
public class RowIteratorSanityCheck
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SanityCheckUtils.class);

    private final CFMetaData metaData;
    private final Token startToken;
    private final int nowInSecond;
    private final boolean verbose;
    private long partitions;
    private long cassandraMissingPartitions;
    private long rocksDBMissingPartitions;
    private long mismatcPartitions;
    private long partitionDeletionMismatch;
    private long rangeTombstoneSkipped;

    private long rows;
    private long cassandraMissingRows;
    private long cassandraPurgedRows;
    private long rocksDBMissingRows;
    private long rocksDBPurgedRows;
    private long mismatchRows;

    public RowIteratorSanityCheck(CFMetaData metaData, Token startToken, int nowInSecond, boolean verbose)
    {
        this.metaData = metaData;
        this.startToken = startToken;
        this.nowInSecond = nowInSecond;
        this.verbose = verbose;
        partitions = 0;
        cassandraMissingPartitions = 0;
        rocksDBMissingPartitions = 0;
        mismatcPartitions = 0;
        partitionDeletionMismatch = 0;
        rangeTombstoneSkipped = 0;
        rows = 0;
        cassandraMissingRows = 0;
        rocksDBMissingRows = 0;
        cassandraPurgedRows = 0;
        rocksDBPurgedRows = 0;
        mismatchRows = 0;
    }

    public void compare(DecoratedKey key, UnfilteredRowIterator cassandraPartition, UnfilteredRowIterator rocksdbPartition)
    {
        partitions++;
        if (cassandraPartition == null && rocksdbPartition == null)
            return;

        if (cassandraPartition == null)
        {
            cassandraMissingPartitions++;
            return;
        }

        if (rocksdbPartition == null)
        {
            rocksDBMissingPartitions++;
            return;
        }
        assert (cassandraPartition.partitionKey().equals(rocksdbPartition.partitionKey()));
        partitionDeletionMismatch += cassandraPartition.partitionLevelDeletion().equals(rocksdbPartition.partitionLevelDeletion()) ? 0 : 1;

        boolean hasRowTombstone = false;
        Map<Clustering, Row> cassandraRows = new HashMap<>();
        while (cassandraPartition.hasNext())
        {
            Unfiltered unfilterd = cassandraPartition.next();
            if (unfilterd.isRangeTombstoneMarker())
            {
                hasRowTombstone = true;
                continue;
            }
            else
            {
                Row row = (Row) unfilterd;
                cassandraRows.put(row.clustering(), row);
            }
        }

        Map<Clustering, Row> rocksdbRows = new HashMap<>();
        while (rocksdbPartition.hasNext())
        {
            // We don't support range tombstone in RocksDBEngine yet.
            Row row = (Row) rocksdbPartition.next();
            rocksdbRows.put(row.clustering(), row);
        }

        rangeTombstoneSkipped += hasRowTombstone ? 1 : 0;
        if (!compare(key, cassandraRows, rocksdbRows))
        {
            mismatcPartitions++;
        }
    }

    public boolean compare(DecoratedKey key, Map<Clustering, Row> cassandraRows, Map<Clustering, Row> rocksdbRows)
    {
        Set<Clustering> clusterings = new HashSet<>(cassandraRows.keySet());
        clusterings.addAll(rocksdbRows.keySet());
        rows += clusterings.size();
        boolean match = true;
        for (Clustering c : clusterings)
        {
            if (!cassandraRows.containsKey(c))
            {
                Row rocksdbRow = rocksdbRows.get(c);
                if (rocksdbRow.hasLiveData(nowInSecond, metaData.enforceStrictLiveness()))
                {
                    cassandraMissingRows++;
                    match = false;
                    if (verbose)
                        LOGGER.info("Cassandra Row Missing, RocksDB Row:" + rowToString(key, c, rocksdbRow));
                }
                else
                {
                    cassandraPurgedRows++;
                }
            }
            else if (!rocksdbRows.containsKey(c))
            {
                Row cassandraRow = cassandraRows.get(c);
                if (cassandraRow.hasLiveData(nowInSecond, metaData.enforceStrictLiveness()))
                {
                    rocksDBMissingRows++;
                    match = false;
                    if (verbose)
                        LOGGER.info("RocksDB Row Missing, Cassandra Row:" + rowToString(key, c, cassandraRow));
                }
                else
                {
                    rocksDBPurgedRows++;
                }
            }
            else
            {
                Row cassandraRow = cassandraRows.get(c);
                Row rocksdbRow = rocksdbRows.get(c);
                if (!digest(cassandraRow).equals(digest(rocksdbRow)))
                {
                    match = false;
                    mismatchRows++;
                    if (verbose)
                        LOGGER.info("Row Mismatch, Cassandra Row:" + rowToString(key, c, cassandraRow) + ", RocksDB Row:" + rowToString(key, c, rocksdbRow));
                }
            }
        }
        return match;
    }

    private String digest(Row row)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            row.digest(digest);
            return digest.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Digest failed:" + e.getMessage());
        }
    }

    private boolean hasLiveDate(Row row, int nowInSecond)
    {
        return Iterables.any(row.cells(), cell -> cell.isLive(nowInSecond));
    }

    public Report getReport()
    {
        return new Report(this);
    }

    @Override
    public String toString()
    {
        return getReport().toString();
    }

    public static class Report
    {
        public final String tableName;
        public final Token startToken;
        public final long partitions;
        public final long cassandraMissingPartitions;
        public final long rocksDBMissingPartitions;
        public final long mismatcPartitions;
        public final long partitionDeletionMismatch;
        public final long rangeTombstoneSkipped;

        public final long rows;
        public final long cassandraMissingRows;
        public final long cassandraPurgedRows;
        public final long rocksDBMissingRows;
        public final long rocksDBPurgedRows;
        public final long mismatchRows;

        public Report(RowIteratorSanityCheck comparator)
        {
            this.tableName = comparator.metaData.ksName + "." + comparator.metaData.cfName;
            this.startToken = comparator.startToken;
            this.partitions = comparator.partitions;
            this.cassandraMissingPartitions = comparator.cassandraMissingPartitions;
            this.rocksDBMissingPartitions = comparator.rocksDBMissingPartitions;
            this.mismatcPartitions = comparator.mismatcPartitions;
            this.partitionDeletionMismatch = comparator.partitionDeletionMismatch;
            this.rangeTombstoneSkipped = comparator.rangeTombstoneSkipped;
            this.rows = comparator.rows;
            this.cassandraMissingRows = comparator.cassandraMissingRows;
            this.rocksDBMissingRows = comparator.rocksDBMissingRows;
            this.mismatchRows = comparator.mismatchRows;
            this.cassandraPurgedRows = comparator.cassandraPurgedRows;
            this.rocksDBPurgedRows = comparator.rocksDBPurgedRows;
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Sanity check result of ").append(tableName)
              .append("\n  start token: ").append(startToken)
              .append("\n  total partitions: ").append(partitions)
              .append("\n    cassandra missing partitions: ").append(cassandraMissingPartitions)
              .append("\n    rocksdb missing partitions: ").append(rocksDBMissingPartitions)
              .append("\n    mismatch partitions: ").append(mismatcPartitions)
              .append("\n    mismatch partition deletions: ").append(partitionDeletionMismatch)
              .append("\n    skipped range tombstones: ").append(rangeTombstoneSkipped)
              .append("\n    total rows: ").append(rows)
              .append("\n    cassandra missing rows: ").append(cassandraMissingRows)
              .append("\n    rocksdb missing rows: ").append(rocksDBMissingRows)
              .append("\n    cassandra purged rows: ").append(cassandraPurgedRows)
              .append("\n    rocksdb purged rows: ").append(rocksDBPurgedRows)
              .append("\n    mismatched rows: ").append(mismatchRows);
            return sb.toString();
        }
    }

    private String rowToString(DecoratedKey key, Clustering clustering, Row row)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Partition:").append(key.toString()).append(", ");
        sb.append("Clustering:").append(clustering.toString(metaData)).append(", ");
        sb.append("Value:").append(row.toString(metaData, true /* full detail */));
        return sb.toString();
    }
}
