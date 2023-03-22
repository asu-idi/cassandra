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

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.Schema;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.db.SerializationHeader;
import org.apache.cassandra.io.util.TrackedInputStream;
import org.apache.cassandra.rocksdb.RocksDBConfigs;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.streaming.ProgressInfo;
import org.apache.cassandra.streaming.StreamReader;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.streaming.compress.CompressedInputStream;
import org.apache.cassandra.streaming.messages.FileMessageHeader;
import org.apache.cassandra.utils.Pair;
import org.rocksdb.RocksDBException;

public class RocksDBCassandraStreamReader
{

    private static final Logger LOGGER = LoggerFactory.getLogger(RocksDBCassandraStreamReader.class);
    private final FileMessageHeader header;
    private final StreamSession session;
    private RocksDBSStableWriter[] writers = new RocksDBSStableWriter[RocksDBConfigs.NUM_SHARD];
    private long totalIncomingKeys = 0;

    public RocksDBCassandraStreamReader(FileMessageHeader header, StreamSession session)
    {
        this.header = header;
        this.session = session;
    }

    private void initWriters()
    {
        for (int i = 0; i < RocksDBConfigs.NUM_SHARD; i++)
        {
            writers[i] = new RocksDBSStableWriter(header.cfId, i);
        }
    }

    public void read(ReadableByteChannel channel) throws IOException
    {
        long totalSize = totalSize();
        Pair<String, String> kscf = Schema.instance.getCF(header.cfId);
        ColumnFamilyStore cfs = null;
        if (kscf != null)
            cfs = Keyspace.open(kscf.left).getColumnFamilyStore(kscf.right);

        if (kscf == null || cfs == null)
        {
            // schema was dropped during streaming
            throw new IOException("CF " + header.cfId + " was dropped during streaming");
        }

        LOGGER.info("[Stream #{}] Start receiving rocskdb file #{} from {}, ks = '{}', table = '{}'.",
                    session.planId(), header.sequenceNumber, session.peer, cfs.keyspace.getName(),
                    cfs.getTableName());

        CompressedInputStream cis = new CompressedInputStream(Channels.newInputStream(channel), header.compressionInfo,
                                                              header.version.compressedChecksumType(), cfs::getCrcCheckChance);
        TrackedInputStream in = new TrackedInputStream(cis);

        StreamReader.StreamDeserializer deserializer = new StreamReader.StreamDeserializer(cfs.metadata, in, header.version, getHeader(cfs.metadata),
                                                                                           totalSize, session.planId());
        initWriters();
        try
        {
            int sectionIdx = 0;
            for (Pair<Long, Long> section : header.sections)
            {
                assert cis.getTotalCompressedBytesRead() <= totalSize;
                long sectionLength = section.right - section.left;

                LOGGER.info("[Stream #{}] Reading section {} with length {} from stream.", session.planId(), sectionIdx++, sectionLength);
                // skip to beginning of section inside chunk
                cis.position(section.left);
                in.reset(0);

                while (in.getBytesRead() < sectionLength)
                {
                    StreamReader.StreamDeserializer partition = deserializer.newPartition();
                    writePartition(cfs, partition);
                    deserializer.checkForExceptions();
                    totalIncomingKeys++;
                    // when compressed, report total bytes of compressed chunks read since remoteFile.size is the sum of chunks transferred
                    RocksDBStreamUtils.rocksDBProgress(session, header.cfId.toString(), ProgressInfo.Direction.IN, cis.getTotalCompressedBytesRead(), totalIncomingKeys, header.estimatedKeys, false);
                }
            }
        }
        catch (Throwable e)
        {
            abortWriters(e);
            throw Throwables.propagate(e);
        }
        finally
        {
            deserializer.cleanup();
        }
        closeWriters();

        LOGGER.info("[Stream #{}] received cassandra sstable from #{} from {}, ks = '{}', table = '{}'. Ingested as {} rocksandra sstables",
                    session.planId(), header.sequenceNumber, session.peer, cfs.keyspace.getName(),
                    cfs.getTableName(), totalSSTableIngested());
    }

    private int totalSSTableIngested()
    {
        return Arrays.stream(writers).mapToInt(RocksDBSStableWriter::getSstableIngested).sum();
    }

    private void closeWriters() throws IOException
    {
        for (RocksDBSStableWriter writer : writers)
        {
            writer.close();
        }
    }

    private void abortWriters(Throwable e)
    {
        for (RocksDBSStableWriter writer : writers)
        {
            writer.abort(e);
        }
    }

    private void writePartition(ColumnFamilyStore cfs, StreamReader.StreamDeserializer partition) throws IOException, RocksDBException
    {
        int shardId = RocksDBEngine.getRocksDBCF(cfs).getShardIdForKey(partition.partitionKey());
        assert shardId >= 0 && shardId < writers.length;
        writers[shardId].writePartition(partition);
    }

    public long totalSize()
    {
        long size = 0;
        for (Pair<Long, Long> section : header.sections)
            size += section.right - section.left;
        return size;
    }

    private SerializationHeader getHeader(CFMetaData metadata)
    {
        return header.header != null ? header.header.toHeader(metadata) : null;
    }
}
