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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.Util;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.RandomPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.locator.TokenMetadata;
import org.apache.cassandra.service.StorageService;

import static org.junit.Assert.assertEquals;

public class GossiperTest
{
    static
    {
        DatabaseDescriptor.setDaemonInitialized();
    }
    static final IPartitioner partitioner = new RandomPartitioner();
    StorageService ss = StorageService.instance;
    TokenMetadata tmd = StorageService.instance.getTokenMetadata();
    ArrayList<Token> endpointTokens = new ArrayList<>();
    ArrayList<Token> keyTokens = new ArrayList<>();
    List<InetAddress> hosts = new ArrayList<>();
    List<UUID> hostIds = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(GossiperTest.class);

    @Before
    public void setup()
    {
        tmd.clearUnsafe();
    };

    @Test
    public void testLargeGenerationJump() throws UnknownHostException, InterruptedException
    {
        Util.createInitialRing(ss, partitioner, endpointTokens, keyTokens, hosts, hostIds, 2);
        try
        {
            InetAddress remoteHostAddress = hosts.get(1);

            EndpointState initialRemoteState = Gossiper.instance.getEndpointStateForEndpoint(remoteHostAddress);
            HeartBeatState initialRemoteHeartBeat = initialRemoteState.getHeartBeatState();

            //Util.createInitialRing should have initialized remoteHost's HeartBeatState's generation to 1
            assertEquals(initialRemoteHeartBeat.getGeneration(), 1);

            HeartBeatState proposedRemoteHeartBeat = new HeartBeatState(initialRemoteHeartBeat.getGeneration() + Gossiper.MAX_GENERATION_DIFFERENCE + 1);
            EndpointState proposedRemoteState = new EndpointState(proposedRemoteHeartBeat);

            Gossiper.instance.applyStateLocally(ImmutableMap.of(remoteHostAddress, proposedRemoteState));

            //The generation should have been updated because it isn't over Gossiper.MAX_GENERATION_DIFFERENCE in the future
            HeartBeatState actualRemoteHeartBeat = Gossiper.instance.getEndpointStateForEndpoint(remoteHostAddress).getHeartBeatState();
            assertEquals(proposedRemoteHeartBeat.getGeneration(), actualRemoteHeartBeat.getGeneration());

            //Propose a generation 10 years in the future - this should be rejected.
            HeartBeatState badProposedRemoteHeartBeat = new HeartBeatState((int) (System.currentTimeMillis() / 1000) + Gossiper.MAX_GENERATION_DIFFERENCE * 10);
            EndpointState badProposedRemoteState = new EndpointState(badProposedRemoteHeartBeat);

            Gossiper.instance.applyStateLocally(ImmutableMap.of(remoteHostAddress, badProposedRemoteState));

            actualRemoteHeartBeat = Gossiper.instance.getEndpointStateForEndpoint(remoteHostAddress).getHeartBeatState();

            //The generation should not have been updated because it is over Gossiper.MAX_GENERATION_DIFFERENCE in the future
            assertEquals(proposedRemoteHeartBeat.getGeneration(), actualRemoteHeartBeat.getGeneration());
        }
        finally
        {
            // clean up the gossip states
            Gossiper.instance.endpointStateMap.clear();
        }
    }

    int stateChangedNum = 0;

    @Test
    public void testDuplicatedStateUpdate() throws Exception
    {
        VersionedValue.VersionedValueFactory valueFactory =
            new VersionedValue.VersionedValueFactory(DatabaseDescriptor.getPartitioner());

        Util.createInitialRing(ss, partitioner, endpointTokens, keyTokens, hosts, hostIds, 2);
        try
        {
            InetAddress remoteHostAddress = hosts.get(1);

            EndpointState initialRemoteState = Gossiper.instance.getEndpointStateForEndpoint(remoteHostAddress);
            HeartBeatState initialRemoteHeartBeat = initialRemoteState.getHeartBeatState();

            //Util.createInitialRing should have initialized remoteHost's HeartBeatState's generation to 1
            assertEquals(initialRemoteHeartBeat.getGeneration(), 1);

            HeartBeatState proposedRemoteHeartBeat = new HeartBeatState(initialRemoteHeartBeat.getGeneration());
            EndpointState proposedRemoteState = new EndpointState(proposedRemoteHeartBeat);

            final Token token = DatabaseDescriptor.getPartitioner().getRandomToken();
            VersionedValue tokensValue = valueFactory.tokens(Collections.singletonList(token));
            proposedRemoteState.addApplicationState(ApplicationState.TOKENS, tokensValue);

            Gossiper.instance.register(
            new IEndpointStateChangeSubscriber()
            {
                public void onJoin(InetAddress endpoint, EndpointState epState) { }

                public void beforeChange(InetAddress endpoint, EndpointState currentState, ApplicationState newStateKey, VersionedValue newValue) { }

                public void onChange(InetAddress endpoint, ApplicationState state, VersionedValue value)
                {
                    assertEquals(ApplicationState.TOKENS, state);
                    stateChangedNum++;
                }

                public void beforeAlive(InetAddress endpoint, EndpointState state) { }

                public void onAlive(InetAddress endpoint, EndpointState state) { }

                public void onDead(InetAddress endpoint, EndpointState state) { }

                public void onRemove(InetAddress endpoint) { }

                public void onRestart(InetAddress endpoint, EndpointState state) { }
            }
            );

            stateChangedNum = 0;
            Gossiper.instance.applyStateLocally(ImmutableMap.of(remoteHostAddress, proposedRemoteState));
            assertEquals(1, stateChangedNum);

            HeartBeatState actualRemoteHeartBeat = Gossiper.instance.getEndpointStateForEndpoint(remoteHostAddress).getHeartBeatState();
            assertEquals(proposedRemoteHeartBeat.getGeneration(), actualRemoteHeartBeat.getGeneration());

            // Clone a new HeartBeatState
            proposedRemoteHeartBeat = new HeartBeatState(initialRemoteHeartBeat.getGeneration(), proposedRemoteHeartBeat.getHeartBeatVersion());
            proposedRemoteState = new EndpointState(proposedRemoteHeartBeat);

            // Bump the heartbeat version and use the same TOKENS state
            proposedRemoteHeartBeat.updateHeartBeat();
            proposedRemoteState.addApplicationState(ApplicationState.TOKENS, tokensValue);

            // The following state change should only update heartbeat without updating the TOKENS state
            Gossiper.instance.applyStateLocally(ImmutableMap.of(remoteHostAddress, proposedRemoteState));
            assertEquals(1, stateChangedNum);

            actualRemoteHeartBeat = Gossiper.instance.getEndpointStateForEndpoint(remoteHostAddress).getHeartBeatState();
            assertEquals(proposedRemoteHeartBeat.getGeneration(), actualRemoteHeartBeat.getGeneration());
        }
        finally
        {
            // clean up the gossip states
            Gossiper.instance.endpointStateMap.clear();
        }
    }

    private final static int MAX_PROCESSING_POLLS = 7;
    static
    {
        // Make the GossipSettle test faster
        System.setProperty("cassandra.gossip_settle_min_wait_ms", "100");
        System.setProperty("cassandra.gossip_settle_poll_interval_ms", "100");
        System.setProperty("cassandra.gossip_settle_message_processing_max_polls", String.valueOf(MAX_PROCESSING_POLLS));
    }

    @Test
    public void testGossipSettle()
    {
        // Max wait without getting any gossip message
        final int DEFAULT_NUM_POLLS = 3;
        int numPolls = Gossiper.waitToSettle();
        assertEquals(DEFAULT_NUM_POLLS, numPolls);

        // Max wait with getting a gossip message, but not finished
        Gossiper.markReceivedFirstGossipMessage();
        numPolls = Gossiper.waitToSettle();
        assertEquals(MAX_PROCESSING_POLLS + DEFAULT_NUM_POLLS, numPolls);

        // No extra wait if the first gossip message is processed
        Gossiper.markProcessedFirstGossipMessage();
        numPolls = Gossiper.waitToSettle();
        assertEquals(DEFAULT_NUM_POLLS, numPolls);
    }
}
