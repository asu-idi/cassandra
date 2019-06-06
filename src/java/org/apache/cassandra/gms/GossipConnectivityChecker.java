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

package org.apache.cassandra.gms;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GossipConnectivityChecker
{
    private static final Logger logger = LoggerFactory.getLogger(GossipConnectivityChecker.class);
    // Latest updated time, set to Long.MAX_VALUE if we see any response
    private final Map<InetAddress, Long> latestUpdatedTime = new ConcurrentHashMap<>();
    private final int maxPolls;
    private final int requiredSuccessPolls;
    private final long echoMessageTimeoutMs;
    private final int pollInternalMs;
    private final int openingConnectionThreshold;
    private int waitedPolls = 0;

    public GossipConnectivityChecker(int maxPolls, int requiredSuccessPolls, long echoMessageTimeoutMs, int pollInternalMs, int openingConnectionThreshold)
    {
        this.maxPolls = maxPolls;
        this.requiredSuccessPolls = requiredSuccessPolls;
        this.echoMessageTimeoutMs = echoMessageTimeoutMs;
        this.pollInternalMs = pollInternalMs;
        this.openingConnectionThreshold = openingConnectionThreshold;
    }

    private long getOpeningConnectionNum()
    {
        long expiredTime = System.currentTimeMillis() - echoMessageTimeoutMs;
        return latestUpdatedTime.values().stream().filter(t -> t != null && t != Long.MAX_VALUE && t > expiredTime).count();
    }

    /**
     * Wait and check at least requiredSuccessPolls number of time to make sure there's a less or
     * equal than openingConnectionThreshold active gossip opening connections (waiting for Gossip
     * ECHO message response). The wait time is capped by maxPolls.
     * @return if the opening connection is settled
     */
    public boolean waitConnectionSettle()
    {
        waitedPolls = 0;
        StateListener listener = new StateListener();
        Gossiper.instance.register(listener);

        try
        {
            int numOkay = 0;
            while (waitedPolls < maxPolls)
            {
                if (numOkay >= requiredSuccessPolls)
                {
                    logger.info("Gossip connection is settled after {} polls", waitedPolls);
                    return true;
                }
                waitedPolls++;
                Uninterruptibles.sleepUninterruptibly(pollInternalMs, TimeUnit.MILLISECONDS);

                long openingConn = getOpeningConnectionNum();
                logger.debug("Gossip opening connection number: {}", openingConn);
                // consider it's okay if there's only 1 connection opening
                numOkay = openingConn <= openingConnectionThreshold ? numOkay + 1 : 0;
            }
            logger.info("Gossip connection is not settled but forced to start. Number of polls: {}", waitedPolls);
            return false;
        }
        finally
        {
            Gossiper.instance.unregister(listener);
        }
    }

    public int getWaitedPolls()
    {
        return waitedPolls;
    }

    private final class StateListener implements IEndpointStateChangeSubscriber
    {

        public void beforeAlive(InetAddress endpoint, EndpointState state)
        {
            Long val = latestUpdatedTime.get(endpoint);
            long now = System.currentTimeMillis();
            // If it's a new endpoint, or has a new update time, update it.
            if (val == null || val < now)
                latestUpdatedTime.put(endpoint, now);
        }

        public void onAlive(InetAddress endpoint, EndpointState state)
        {
            latestUpdatedTime.put(endpoint, Long.MAX_VALUE);
        }

        public void onJoin(InetAddress endpoint, EndpointState epState) { }
        public void beforeChange(InetAddress endpoint, EndpointState currentState, ApplicationState newStateKey, VersionedValue newValue) { }
        public void onChange(InetAddress endpoint, ApplicationState state, VersionedValue value) { }
        public void onDead(InetAddress endpoint, EndpointState state) { }
        public void onRemove(InetAddress endpoint) { }
        public void onRestart(InetAddress endpoint, EndpointState state) { }
    }
}
