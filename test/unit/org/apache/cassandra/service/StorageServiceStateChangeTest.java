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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Test;

import org.apache.cassandra.Util;
import org.apache.cassandra.concurrent.Stage;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.SystemKeyspace;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.gms.ApplicationState;
import org.apache.cassandra.gms.EndpointState;
import org.apache.cassandra.gms.Gossiper;
import org.apache.cassandra.gms.IEndpointStateChangeSubscriber;
import org.apache.cassandra.gms.VersionedValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageServiceStateChangeTest
{
    @After
    public void after() throws UnknownHostException
    {
        cleanSystemTable();
        Gossiper.instance.unsafeClearStateForTest();
    }


    @Test
    public void testRemoveEndpointsNoLonggerOwningTokens() throws Exception
    {
        DatabaseDescriptor.setDaemonInitialized();
        VersionedValue.VersionedValueFactory valueFactory =
            new VersionedValue.VersionedValueFactory(DatabaseDescriptor.getPartitioner());
        Collection<Token> tokens = new HashSet<>();
        tokens.add(DatabaseDescriptor.getPartitioner().getRandomToken());
        tokens.add(DatabaseDescriptor.getPartitioner().getRandomToken());

        // Initialize a node with 2 tokens
        InetAddress ep1 = InetAddress.getByName("127.0.0.1");
        Gossiper.instance.initializeNodeUnsafe(ep1, UUID.randomUUID(), 1);
        Gossiper.instance.injectApplicationState(ep1, ApplicationState.TOKENS, valueFactory.tokens(tokens));
        StorageService.instance.onChange(ep1, ApplicationState.STATUS, valueFactory.normal(tokens));

        final AtomicInteger removedNum = new AtomicInteger(0);
        Gossiper.instance.register(
        new IEndpointStateChangeSubscriber()
        {
            public void onJoin(InetAddress endpoint, EndpointState epState) { }

            public void beforeChange(InetAddress endpoint, EndpointState currentState, ApplicationState newStateKey, VersionedValue newValue) { }

            public void onChange(InetAddress endpoint, ApplicationState state, VersionedValue value) { }

            public void onAlive(InetAddress endpoint, EndpointState state) { }

            public void onDead(InetAddress endpoint, EndpointState state) { }

            public void onRemove(InetAddress endpoint)
            {
                removedNum.incrementAndGet();
                assertEquals(ep1, endpoint);
            }

            public void onRestart(InetAddress endpoint, EndpointState state) { }
        }
        );

        // Adding a second node ep2 with the same tokens, make sure ep1 is removed, as it's no longer owning any tokens.
        assertEquals(0, removedNum.get());
        InetAddress ep2 = InetAddress.getByName("127.0.0.2");
        Gossiper.instance.initializeNodeUnsafe(ep2, UUID.randomUUID(), 2);
        Gossiper.instance.injectApplicationState(ep2, ApplicationState.TOKENS, valueFactory.tokens(tokens));
        StorageService.instance.onChange(ep2, ApplicationState.STATUS, valueFactory.normal(tokens));
        assertEquals(1, removedNum.get());
    }

    private void cleanSystemTable() throws UnknownHostException
    {
        List<InetAddress> allEndpoints = new ArrayList<>();
        allEndpoints.add(InetAddress.getByName("127.0.0.2"));
        allEndpoints.add(InetAddress.getByName("127.0.0.3"));
        allEndpoints.add(InetAddress.getByName("127.0.0.4"));
        SystemKeyspace.removeEndpoints(allEndpoints);
    }

    @Test
    public void testNewNodeInSystemTable() throws Exception
    {
        DatabaseDescriptor.setDaemonInitialized();
        VersionedValue.VersionedValueFactory valueFactory =
            new VersionedValue.VersionedValueFactory(DatabaseDescriptor.getPartitioner());

        // Initialize a node with 2 tokens
        Set<Token> tokens1 = new HashSet<>();
        tokens1.add(DatabaseDescriptor.getPartitioner().getRandomToken());
        tokens1.add(DatabaseDescriptor.getPartitioner().getRandomToken());

        InetAddress ep1 = InetAddress.getByName("127.0.0.2");
        Gossiper.instance.initializeNodeUnsafe(ep1, UUID.randomUUID(), 1);
        Gossiper.instance.injectApplicationState(ep1, ApplicationState.TOKENS, valueFactory.tokens(tokens1));
        StorageService.instance.onChange(ep1, ApplicationState.STATUS, valueFactory.normal(tokens1));
        Util.waitStageTask(Stage.MUTATION);

        // Verify the tokens are stored in system table
        Multimap<InetAddress, Token> savedTokens = SystemKeyspace.loadTokens();
        assertEquals(2, savedTokens.size());
        assertTrue(CollectionUtils.isEqualCollection(tokens1, savedTokens.get(ep1)));
        Multimap<InetAddress, Token> loadedTokens = StorageService.instance.readAndCleanupSavedTokens();
        assertTrue(loadedTokens.equals(savedTokens));

        // Add a new normal node
        Set<Token> tokens2 = new HashSet<>();
        tokens2.add(DatabaseDescriptor.getPartitioner().getRandomToken());
        tokens2.add(DatabaseDescriptor.getPartitioner().getRandomToken());

        InetAddress ep2 = InetAddress.getByName("127.0.0.3");
        Gossiper.instance.initializeNodeUnsafe(ep2, UUID.randomUUID(), 1);
        Gossiper.instance.injectApplicationState(ep2, ApplicationState.TOKENS, valueFactory.tokens(tokens2));
        StorageService.instance.onChange(ep2, ApplicationState.STATUS, valueFactory.normal(tokens2));
        Util.waitStageTask(Stage.MUTATION);

        // Verify the new tokens are added in system table
        savedTokens = SystemKeyspace.loadTokens();
        assertEquals(4, savedTokens.size());
        assertTrue(CollectionUtils.isEqualCollection(tokens1, savedTokens.get(ep1)));
        assertTrue(CollectionUtils.isEqualCollection(tokens2, savedTokens.get(ep2)));
        loadedTokens = StorageService.instance.readAndCleanupSavedTokens();
        assertTrue(loadedTokens.equals(savedTokens));

        // Add a node with conflict tokens
        Set<Token> tokens3 = new HashSet<>();
        tokens3.add(tokens1.iterator().next());
        InetAddress ep3 = InetAddress.getByName("127.0.0.4");
        Gossiper.instance.initializeNodeUnsafe(ep3, UUID.randomUUID(), 2);
        Gossiper.instance.injectApplicationState(ep3, ApplicationState.TOKENS, valueFactory.tokens(tokens3));
        StorageService.instance.onChange(ep3, ApplicationState.STATUS, valueFactory.normal(tokens3));
        Util.waitStageTask(Stage.MUTATION);

        // Verify the endpoints with conflict tokens are removed, and then added back with gossip state onChange()
        savedTokens = SystemKeyspace.loadTokens();
        assertEquals(5, savedTokens.size());
        assertTrue(CollectionUtils.isEqualCollection(tokens2, savedTokens.get(ep2)));
        loadedTokens = StorageService.instance.readAndCleanupSavedTokens();
        assertEquals(2, loadedTokens.size());
        assertTrue(CollectionUtils.isEqualCollection(tokens2, loadedTokens.get(ep2)));

        // Gossiper insert the missing endpoint back
        Gossiper.instance.initializeNodeUnsafe(ep3, UUID.randomUUID(), 2);
        Gossiper.instance.injectApplicationState(ep3, ApplicationState.TOKENS, valueFactory.tokens(tokens3));
        StorageService.instance.onChange(ep3, ApplicationState.STATUS, valueFactory.normal(tokens3));
        Util.waitStageTask(Stage.MUTATION);
        loadedTokens = StorageService.instance.readAndCleanupSavedTokens();
        assertEquals(3, loadedTokens.size());
    }
}
