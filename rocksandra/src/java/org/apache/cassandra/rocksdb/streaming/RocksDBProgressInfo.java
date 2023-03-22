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

import java.net.InetAddress;

import com.google.common.base.Objects;

import org.apache.cassandra.streaming.ProgressInfo;

public class RocksDBProgressInfo extends ProgressInfo
{
    public final boolean completed;
    public final long currentKeys;
    public final long estimatedTotalKeys;

    public RocksDBProgressInfo(InetAddress peer, int sessionIndex, String fileName, Direction direction, long currentBytes, long currentKeys, long estimatedTotalKeys, boolean completed)
    {
        super(peer, sessionIndex, fileName, direction, currentBytes, 1 /* totalBytes set to 1 to avoid potential divide by zero exception. */);
        this.currentKeys = currentKeys;
        this.estimatedTotalKeys = estimatedTotalKeys;
        this.completed = completed;
    }

    @Override
    public boolean isCompleted()
    {
        return completed;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RocksDBProgressInfo that = (RocksDBProgressInfo) o;

        return direction == that.direction && fileName == that.fileName && sessionIndex == that.sessionIndex && peer == that.peer;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(peer, sessionIndex, fileName, direction);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("RocksDB File ").append(fileName);
        sb.append(" ").append(currentBytes).append(" bytes, ");
        sb.append(currentKeys).append("/").append(estimatedTotalKeys).append(" keys ");
        sb.append(direction == Direction.OUT ? "sent to " : "received from ");
        sb.append("idx:").append(sessionIndex);
        sb.append(peer);
        return sb.toString();
    }
}
