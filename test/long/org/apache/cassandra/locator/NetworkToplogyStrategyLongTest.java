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

import java.net.InetAddress;
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

import static org.apache.cassandra.locator.NetworkTopologyStrategyTest.verifyAddressRanges;
import static org.junit.Assert.assertTrue;

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
    public void testGetAddressRangesVnode()
    {
        final int HOST_NUM = 100;
        final int RF_MAX = 10;
        Map<InetAddress, NetworkTopologyStrategyTest.HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < HOST_NUM; i++)
        {
            hosts.put(NetworkTopologyStrategyTest.hostList.get(i), new NetworkTopologyStrategyTest.HostInfo(i, "dc1", "r1", 256));
            for (int rf = 0; rf < RF_MAX; rf++)
            {
                verifyAddressRanges(hosts, rf);
            }
        }
    }

    @Test
    public void testLargeVnode()
    {
        final int HOST_NUM = 1000;
        Random rand = new Random();
        Map<InetAddress, NetworkTopologyStrategyTest.HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < HOST_NUM; i++)
        {
            String dc = String.format("dc%d", rand.nextInt(5));
            String rack = String.format("r%d", rand.nextInt(6));
            hosts.put(NetworkTopologyStrategyTest.hostList.get(i), new NetworkTopologyStrategyTest.HostInfo(i, dc, rack, 256));
        }
        NetworkTopologyStrategy strategy = verifyAddressRanges(hosts, 5);

        InetAddress ep = NetworkTopologyStrategyTest.hostList.get(0);
        long t0 = System.nanoTime();
        strategy.getAddressRanges().get(ep);
        long t1 = System.nanoTime();
        strategy.getAddressRanges(ep);
        long t2 = System.nanoTime();
        logger.info("old: {}, new: {}", TimeUnit.NANOSECONDS.toMillis(t1 - t0), TimeUnit.NANOSECONDS.toMillis(t2 - t1));
        assertTrue(t2 - t1 < t1 - t0);
    }
}
