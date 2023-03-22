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

package org.apache.cassandra.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.Util;
import org.apache.cassandra.db.ConsistencyLevel;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.db.WriteType;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.RandomPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.exceptions.UnavailableException;
import org.apache.cassandra.gms.Gossiper;
import org.apache.cassandra.locator.TokenMetadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class WriteResponseHandlerTest
{

    @BeforeClass
    public static void setup() throws ConfigurationException
    {
        SchemaLoader.startGossiper();
        SchemaLoader.prepareServer();
        SchemaLoader.schemaDefinition("WriteResponseHandlerTest");
    }

    private void assertWriteHandlerEquals(WriteResponseHandler<?> normalHandler, WriteResponseHandler<?> pendingHandler, int pendingNum)
    {
        UnavailableException unavailableExceptionNormal = null;
        UnavailableException unavailableExceptionPending = null;

        try
        {
            normalHandler.assureSufficientLiveNodes();
        }
        catch (UnavailableException e)
        {
            unavailableExceptionNormal = e;
        }

        try
        {
            pendingHandler.assureSufficientLiveNodes();
        }
        catch (UnavailableException e)
        {
            unavailableExceptionPending = e;
        }

        if (unavailableExceptionNormal == null)
        {
            assertTrue(unavailableExceptionPending == null);
        }
        else
        {
            assertEquals(unavailableExceptionNormal.consistency, unavailableExceptionPending.consistency);
            assertEquals(unavailableExceptionNormal.alive + pendingNum, unavailableExceptionPending.alive);
            assertEquals(unavailableExceptionNormal.required + pendingNum, unavailableExceptionPending.required);
        }
    }

    private void setNodeDead(InetAddress host)
    {
        Gossiper.instance.markDead(host, Gossiper.instance.getEndpointStateForEndpoint(host));
    }

    private void setNodeLive(InetAddress host)
    {
        Gossiper.instance.realMarkAlive(host, Gossiper.instance.getEndpointStateForEndpoint(host));
    }

    @Test
    public void testMM() throws Exception
    {
        ConsistencyLevel consistencyLevels[] = new ConsistencyLevel[] {
            ConsistencyLevel.ONE,
            ConsistencyLevel.TWO,
            ConsistencyLevel.THREE,
            ConsistencyLevel.QUORUM,
            ConsistencyLevel.ALL
        };

        StorageService ss = StorageService.instance;
        TokenMetadata tmd = ss.getTokenMetadata();
        tmd.clearUnsafe();
        IPartitioner partitioner = new RandomPartitioner();

        ArrayList<Token> endpointTokens = new ArrayList<>();
        ArrayList<Token> keyTokens = new ArrayList<>();
        List<InetAddress> hosts = new ArrayList<>();
        List<UUID> hostIds = new ArrayList<>();

        // create a ring of 7 nodes
        Util.createInitialRing(ss, partitioner, endpointTokens, keyTokens, hosts, hostIds, 7);

        // replica = 1
        String keyspaceName = "WriteResponseHandlerTestKeyspace1";
        List<InetAddress> endpoints = hosts.subList(1, 2);
        List<InetAddress> pendingEps = hosts.subList(2, 3);

        for (ConsistencyLevel cl : consistencyLevels)
        {
            for (InetAddress ep : endpoints)
                setNodeLive(ep);

            WriteResponseHandler<?> normalHandler = new WriteResponseHandler<>(endpoints, Collections.emptyList(), cl, Keyspace.open(keyspaceName), null, WriteType.SIMPLE);
            WriteResponseHandler<?> pendingHandler = new WriteResponseHandler<>(endpoints, pendingEps, cl, Keyspace.open(keyspaceName), null, WriteType.SIMPLE);
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(0));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());
        }

        // replica = 3
        keyspaceName = "WriteResponseHandlerTestKeyspace4";
        endpoints = hosts.subList(1, 4);
        pendingEps = hosts.subList(4, 5);

        for (ConsistencyLevel cl : consistencyLevels)
        {
            for (InetAddress ep : endpoints)
                setNodeLive(ep);

            WriteResponseHandler<?> normalHandler = new WriteResponseHandler<>(endpoints, Collections.emptyList(), cl, Keyspace.open(keyspaceName), null, WriteType.SIMPLE);
            WriteResponseHandler<?> pendingHandler = new WriteResponseHandler<>(endpoints, pendingEps, cl, Keyspace.open(keyspaceName), null, WriteType.SIMPLE);
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(0));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(1));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(2));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());
        }

        // replica = 5
        keyspaceName = "WriteResponseHandlerTestKeyspace3";
        endpoints = hosts.subList(1, 6);
        pendingEps = hosts.subList(6, 7);

        for (ConsistencyLevel cl : consistencyLevels)
        {
            for (InetAddress ep : endpoints)
                setNodeLive(ep);

            WriteResponseHandler<?> normalHandler = new WriteResponseHandler<>(endpoints, Collections.emptyList(), cl, Keyspace.open(keyspaceName), null, WriteType.SIMPLE);
            WriteResponseHandler<?> pendingHandler = new WriteResponseHandler<>(endpoints, pendingEps, cl, Keyspace.open(keyspaceName), null, WriteType.SIMPLE);
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(0));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(1));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(2));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(3));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());

            setNodeDead(endpoints.get(4));
            assertWriteHandlerEquals(normalHandler, pendingHandler, pendingEps.size());
        }
    }
}
