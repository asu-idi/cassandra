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

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.engine.streaming.AbstractStreamReceiveTask;
import org.apache.cassandra.metrics.StreamingMetrics;
import org.apache.cassandra.streaming.ConnectionHandler;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.streaming.messages.ReceivedMessage;
import org.apache.cassandra.streaming.messages.StreamMessage;
import org.apache.cassandra.utils.Throwables;

public class RocksDBStreamReceiveTask extends AbstractStreamReceiveTask
{
    private static final Logger logger = LoggerFactory.getLogger(RocksDBStreamReceiveTask.class);

    private int remoteRocksFileReceived = 0;

    public RocksDBStreamReceiveTask(StreamSession session, UUID cfId, int totalFiles, long totalSize)
    {
        super(session, cfId, totalFiles, totalSize);
    }

    private synchronized void received()
    {
        if (done)
        {
            logger.warn("[{}] Received sstable on already finished stream received task.", session.planId());
            return;
        }
        remoteRocksFileReceived ++;
        if (remoteRocksFileReceived == totalFiles)
        {
            done = true;
            session.receiveTaskCompleted(this);
        }
    }

    public synchronized void abort()
    {
        if (done)
            return;

        done = true;
    }

    public void receive(ConnectionHandler handler, StreamMessage msg, StreamingMetrics metrics)
    {
        RocksDBIncomingMessage message = (RocksDBIncomingMessage) msg;

        // TODO(chenshen): size info is missing for rocksdb.
        // long headerSize = message.header.size();
        // StreamingMetrics.totalIncomingBytes.inc(headerSize);
        // metrics.incomingBytes.inc(headerSize);
        // send back file received message
        handler.sendMessage(new ReceivedMessage(message.header.cfId, message.header.sequenceNumber));
        metrics.incomingBytes.inc(message.totalIncomingBytes);
        received();
    }
}
