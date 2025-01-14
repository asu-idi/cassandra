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

package org.apache.cassandra.engine.streaming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Throwables;

import org.apache.cassandra.concurrent.NamedThreadFactory;
import org.apache.cassandra.streaming.StreamSession;
import org.apache.cassandra.streaming.StreamTask;
import org.apache.cassandra.streaming.messages.OutgoingMessage;
import org.apache.cassandra.streaming.messages.StreamMessage;

public abstract class AbstractStreamTransferTask extends StreamTask
{
    protected static final ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("StreamingTransferTaskTimeouts"));

    protected final AtomicInteger sequenceNumber = new AtomicInteger(0);
    protected boolean aborted = false;
    protected final Map<Integer, ScheduledFuture> timeoutTasks = new HashMap<>();

    @VisibleForTesting
    public final Map<Integer, OutgoingMessage> files = new HashMap<>();

    protected long totalSize;

    protected AbstractStreamTransferTask(StreamSession session, UUID cfId)
    {
        super(session, cfId);
    }

    public int getTotalNumberOfFiles()
    {
        return files.size();
    }

    public long getTotalSize()
    {
        return totalSize;
    }

    /**
     * Received ACK for file at {@code sequenceNumber}.
     *
     * @param sequenceNumber sequence number of file
     */
    public void complete(int sequenceNumber)
    {
        boolean signalComplete;
        synchronized (this)
        {
            ScheduledFuture timeout = timeoutTasks.remove(sequenceNumber);
            if (timeout != null)
                timeout.cancel(false);

            OutgoingMessage file = files.remove(sequenceNumber);
            if (file != null)
                file.complete();

            signalComplete = files.isEmpty();
        }

        // all file sent, notify session this task is complete.
        if (signalComplete)
            session.transferTaskCompleted(this);
    }

    public synchronized void abort()
    {
        if (aborted)
            return;
        aborted = true;

        for (ScheduledFuture future : timeoutTasks.values())
            future.cancel(false);
        timeoutTasks.clear();

        Throwable fail = null;
        for (OutgoingMessage file : files.values())
        {
            try
            {
                file.complete();
            }
            catch (Throwable t)
            {
                if (fail == null) fail = t;
                else fail.addSuppressed(t);
            }
        }
        files.clear();
        if (fail != null)
            Throwables.propagate(fail);
    }

    public synchronized Collection<? extends StreamMessage> getFileMessages()
    {
        // We may race between queuing all those messages and the completion of the completion of
        // the first ones. So copy the values to avoid a ConcurrentModificationException
        return new ArrayList<>(files.values());
    }

    /**
     * Schedule timeout task to release reference for file sent.
     * When not receiving ACK after sending to receiver in given time,
     * the task will release reference.
     *
     * @param sequenceNumber sequence number of file sent.
     * @param time time to timeout
     * @param unit unit of given time
     * @return scheduled future for timeout task
     */
    public synchronized ScheduledFuture scheduleTimeout(final int sequenceNumber, long time, TimeUnit unit)
    {
        if (!files.containsKey(sequenceNumber))
            return null;

        ScheduledFuture future = timeoutExecutor.schedule(new Runnable()
        {
            public void run()
            {
                synchronized (AbstractStreamTransferTask.this)
                {
                    // remove so we don't cancel ourselves
                    timeoutTasks.remove(sequenceNumber);
                    AbstractStreamTransferTask.this.complete(sequenceNumber);
                }
            }
        }, time, unit);

        ScheduledFuture prev = timeoutTasks.put(sequenceNumber, future);
        assert prev == null;
        return future;
    }
}
