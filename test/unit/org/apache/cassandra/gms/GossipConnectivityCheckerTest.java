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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.Util;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.RandomPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.service.StorageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GossipConnectivityCheckerTest
{
    static
    {
        DatabaseDescriptor.setDaemonInitialized();
    }
    static final IPartitioner partitioner = new RandomPartitioner();
    StorageService ss = StorageService.instance;
    ArrayList<Token> endpointTokens = new ArrayList<>();
    ArrayList<Token> keyTokens = new ArrayList<>();
    List<InetAddress> hosts = new ArrayList<>();
    List<UUID> hostIds = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(GossipConnectivityCheckerTest.class);

    @Test
    public void testWaitSettle() throws Exception
    {
        Util.createInitialRing(ss, partitioner, endpointTokens, keyTokens, hosts, hostIds, 3);

        // Normal wait no connection
        int requiredPoll = 5;
        GossipConnectivityChecker checker = new GossipConnectivityChecker(100, requiredPoll, 10000, 10, 1);
        assertTrue(checker.waitConnectionSettle());
        assertEquals(requiredPoll, checker.getWaitedPolls());

        // Should not exceed Max wait
        int maxWait = 3;
        checker = new GossipConnectivityChecker(maxWait, requiredPoll, 10000, 10, 1);
        assertFalse(checker.waitConnectionSettle());
        assertEquals(maxWait, checker.getWaitedPolls());

        // Wait open connection to expire
        checker = new GossipConnectivityChecker(100, requiredPoll, 2000, 1000, 1);

        Thread markAlive = new Thread(() -> {
            long start = System.nanoTime();
            long now = start;
            while ((now - start) / 1000000 < 3000) // Keep sending ECHO message for 3 seconds
            {
                // open 2 connections
                Gossiper.instance.markAlive(hosts.get(0), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(0)));
                Gossiper.instance.markAlive(hosts.get(1), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(1)));
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                now = System.nanoTime();
            }
        });
        markAlive.start();

        assertTrue(checker.waitConnectionSettle());
        markAlive.join(50);
        assertTrue(checker.getWaitedPolls() > requiredPoll);

        // connections are opened right after init
        checker = new GossipConnectivityChecker(100, requiredPoll, 2000, 1000, 1);

        markAlive = new Thread(() -> {
            long start = System.nanoTime();
            long now = start;
            while ((now - start) / 1000000 < 3000) // run this for 3 seconds
            {
                // open 2 connections
                Gossiper.instance.markAlive(hosts.get(0), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(0)));
                Gossiper.instance.realMarkAlive(hosts.get(0), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(0)));

                Gossiper.instance.markAlive(hosts.get(1), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(1)));
                Gossiper.instance.realMarkAlive(hosts.get(1), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(1)));
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                now = System.nanoTime();
            }
        });
        markAlive.start();

        assertTrue(checker.waitConnectionSettle());
        markAlive.join(50);
        assertEquals(requiredPoll, checker.getWaitedPolls());

        // Do not wait if the opening connection number is lower than threshold
        checker = new GossipConnectivityChecker(100, requiredPoll, 2000, 1000, 2);

        markAlive = new Thread(() -> {
            long start = System.nanoTime();
            long now = start;
            while ((now - start) / 1000000 < 3000) // Keep sending ECHO message for 3 seconds
            {
                // open 2 connections
                Gossiper.instance.markAlive(hosts.get(0), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(0)));
                Gossiper.instance.markAlive(hosts.get(1), Gossiper.instance.getEndpointStateForEndpoint(hosts.get(1)));
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                now = System.nanoTime();
            }
        });
        markAlive.start();

        assertTrue(checker.waitConnectionSettle());
        markAlive.join(50);
        assertEquals(requiredPoll, checker.getWaitedPolls());
    }
}
