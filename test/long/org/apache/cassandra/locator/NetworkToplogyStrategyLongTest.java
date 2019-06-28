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

package org.apache.cassandra.locator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.Util;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;

import static org.apache.cassandra.locator.NetworkTopologyStrategyTest.MAX_TEST_HOST_NUM;
import static org.apache.cassandra.locator.NetworkTopologyStrategyTest.verifyEndpointRanges;

public class NetworkToplogyStrategyLongTest
{
    private static final Logger logger = LoggerFactory.getLogger(NetworkToplogyStrategyLongTest.class);

    private static final IPartitioner partitioner = Murmur3Partitioner.instance;
    private static Util.PartitionerSwitcher partitionerSwitcher;

    @BeforeClass
    public static void setup()
    {
        partitionerSwitcher = Util.switchPartitioner(partitioner);
    }

    @AfterClass
    public static void tearDown()
    {
        partitionerSwitcher.close();
    }

    @Test
    public void testLargeVnode()
    {
        final int HOST_NUM = MAX_TEST_HOST_NUM;

        Random rand = new Random();

        Map<InetAddressAndPort, NetworkTopologyStrategyTest.HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < HOST_NUM; i++)
        {
            String dc = String.format("dc%d", rand.nextInt(5));
            String rack = String.format("r%d", rand.nextInt(6));
            hosts.put(NetworkTopologyStrategyTest.hostList.get(i), new NetworkTopologyStrategyTest.HostInfo(i, dc, rack, 256));
        }
        TokenMetadata metadata = new TokenMetadata();
        NetworkTopologyStrategy strategy = verifyEndpointRanges(hosts, 3, metadata);

        InetAddressAndPort ep = NetworkTopologyStrategyTest.hostList.get(0);
        long t0 = System.nanoTime();
        strategy.getAddressReplicasForTestOnly(metadata, ep);
        long t1 = System.nanoTime();
        strategy.getAddressReplicas(metadata, ep);
        long t2 = System.nanoTime();

        logger.info("default API: {} millis, new API: {} millis", TimeUnit.NANOSECONDS.toMillis(t1 - t0), TimeUnit.NANOSECONDS.toMillis(t2 - t1));
    }

    @Test
    public void testLargeNoneVnode()
    {
        final int HOST_NUM = MAX_TEST_HOST_NUM;

        Random rand = new Random();

        Map<InetAddressAndPort, NetworkTopologyStrategyTest.HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < HOST_NUM; i++)
        {
            String dc = String.format("dc%d", rand.nextInt(5));
            String rack = String.format("r%d", rand.nextInt(6));
            hosts.put(NetworkTopologyStrategyTest.hostList.get(i), new NetworkTopologyStrategyTest.HostInfo(i, dc, rack, 1));
        }
        TokenMetadata metadata = new TokenMetadata();
        NetworkTopologyStrategy strategy = verifyEndpointRanges(hosts, 3, metadata);

        InetAddressAndPort ep = NetworkTopologyStrategyTest.hostList.get(0);
        long t0 = System.nanoTime();
        strategy.getAddressReplicasForTestOnly(metadata, ep);
        long t1 = System.nanoTime();
        strategy.getAddressReplicas(metadata, ep);
        long t2 = System.nanoTime();

        logger.info("default API: {} micros, new API: {} micros", TimeUnit.NANOSECONDS.toMicros(t1 - t0), TimeUnit.NANOSECONDS.toMicros(t2 - t1));
    }
}
