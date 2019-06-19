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

import java.util.Collection;
import java.util.UUID;

import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.engine.streaming.AbstractStreamTransferTask;
import org.apache.cassandra.rocksdb.RocksDBCF;
import org.apache.cassandra.streaming.StreamSession;

/**
 * StreamTransferTask sends sections of SSTable files in certain ColumnFamily.
 */
public class RocksDBStreamTransferTask extends AbstractStreamTransferTask
{
    public RocksDBStreamTransferTask(StreamSession session, UUID cfId)
    {
        super(session, cfId);
    }

    public synchronized void addTransferRocksdbFile(UUID cfId, RocksDBCF rocksDBCF, Collection<Range<Token>> ranges)
    {
        for (Range<Token> range : RocksDBStreamUtils.normalizeRanges(ranges)) {
            RocksDBOutgoingMessage message = new RocksDBOutgoingMessage(cfId, sequenceNumber.getAndIncrement(), rocksDBCF, range);
            files.put(message.sequenceNumber, message);
        }
    }
}
