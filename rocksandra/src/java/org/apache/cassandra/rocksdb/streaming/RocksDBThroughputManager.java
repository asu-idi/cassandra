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

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RocksDBThroughputManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RocksDBThroughputManager.class);
    private static final RocksDBThroughputManager INSTANCE = new RocksDBThroughputManager();
    public static long STREAM_THROUGHPUT_PEEK_INTERVAL_MS = 200;
    private final Map<RocksDBStreamWriter, Long> outgoingStreamWriters;
    private final Map<RocksDBSStableWriter, Long> incomingStreamWriters;
    private final ScheduledExecutorService scheduler;
    private long lastCheckTimeMs = 0;
    private volatile long outgoingThroughput = 0; /* Byte per second */
    private volatile long incomingThroughput = 0; /* Byte per second */

    public static RocksDBThroughputManager getInstance()
    {
        return INSTANCE;
    }

    private RocksDBThroughputManager()
    {
        outgoingStreamWriters = new WeakHashMap<RocksDBStreamWriter, Long>();
        incomingStreamWriters = new WeakHashMap<RocksDBSStableWriter, Long>();

        scheduler = Executors.newScheduledThreadPool(1);
        if (STREAM_THROUGHPUT_PEEK_INTERVAL_MS > 0)
        {
            scheduler.scheduleAtFixedRate(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        calcluateThroughput();
                    }
                    catch (Throwable e)
                    {
                        LOGGER.warn("Exception collecting throughput metrics.", e);
                    }
                }
            }, STREAM_THROUGHPUT_PEEK_INTERVAL_MS, STREAM_THROUGHPUT_PEEK_INTERVAL_MS, TimeUnit.MILLISECONDS);
        }
    }

    public synchronized void registerOutgoingStreamWriter(RocksDBStreamWriter writer)
    {
        outgoingStreamWriters.put(writer, writer.getOutgoingBytes());
    }

    public synchronized void registerIncomingStreamWriter(RocksDBSStableWriter writer)
    {
        incomingStreamWriters.put(writer, writer.getIncomingBytes());
    }

    /**
     * This calculate the throughput by diff the received/send bytes in all registered RocksdbStreams and average
     * it by the time difference since last check.
     */
    @VisibleForTesting
    synchronized void calcluateThroughput()
    {
        long currentTimeMs = System.currentTimeMillis();

        long previousTotalOutgoingBytes = 0;
        long currentTotalOutgoingBytes = 0;

        for (RocksDBStreamWriter writer : outgoingStreamWriters.keySet())
        {
            previousTotalOutgoingBytes += outgoingStreamWriters.get(writer);
            long currentOutgoingBytes = writer.getOutgoingBytes();
            currentTotalOutgoingBytes += currentOutgoingBytes;
            outgoingStreamWriters.put(writer, currentOutgoingBytes);
        }

        long previousTotalIncomingBytes = 0;
        long currentTotalIncomingBytes = 0;

        for (RocksDBSStableWriter writer : incomingStreamWriters.keySet())
        {
            previousTotalIncomingBytes += incomingStreamWriters.get(writer);
            long currentIncomingBytes = writer.getIncomingBytes();
            currentTotalIncomingBytes += currentIncomingBytes;
            incomingStreamWriters.put(writer, currentIncomingBytes);
        }

        // If this is the first check, skip reporting the througput as it might not be inaccurate.
        if (lastCheckTimeMs == 0) {
            lastCheckTimeMs = currentTimeMs;
            return;
        }

        long timeElapsed = Math.max(currentTimeMs - lastCheckTimeMs, 1);

        outgoingThroughput = (currentTotalOutgoingBytes - previousTotalOutgoingBytes) * 1000L / timeElapsed;
        incomingThroughput = (currentTotalIncomingBytes - previousTotalIncomingBytes) * 1000L / timeElapsed;

        lastCheckTimeMs = currentTimeMs;
    }

    public long getOutgoingThroughput()
    {
        return outgoingThroughput;
    }

    public long getIncomingThroughput()
    {
        return incomingThroughput;
    }

}
