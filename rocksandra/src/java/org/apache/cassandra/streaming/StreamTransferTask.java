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
package org.apache.cassandra.streaming;

import java.util.List;
import java.util.UUID;

import org.apache.cassandra.io.sstable.format.SSTableReader;
import org.apache.cassandra.engine.streaming.AbstractStreamTransferTask;
import org.apache.cassandra.streaming.messages.OutgoingFileMessage;
import org.apache.cassandra.utils.Pair;
import org.apache.cassandra.utils.concurrent.Ref;

/**
 * StreamTransferTask sends sections of SSTable files in certain ColumnFamily.
 */
public class StreamTransferTask extends AbstractStreamTransferTask
{
    public StreamTransferTask(StreamSession session, UUID cfId)
    {
        super(session, cfId);
    }

    public synchronized void addTransferFile(Ref<SSTableReader> ref, long estimatedKeys, List<Pair<Long, Long>> sections, long repairedAt)
    {
        assert ref.get() != null && cfId.equals(ref.get().metadata.cfId);
        OutgoingFileMessage message = new OutgoingFileMessage(ref, sequenceNumber.getAndIncrement(), estimatedKeys, sections, repairedAt, session.keepSSTableLevel());
        files.put(message.header.sequenceNumber, message);
        totalSize += message.header.size();
    }
}
