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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.Util;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.dht.OrderPreservingPartitioner.StringToken;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.locator.NetworkTopologyStrategy.DCAwareTokens;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NetworkTopologyStrategyTest
{
    private static String keyspaceName = "Keyspace1";
    private static final Logger logger = LoggerFactory.getLogger(NetworkTopologyStrategyTest.class);

    private static final IPartitioner partitioner = Murmur3Partitioner.instance;
    private static Util.PartitionerSwitcher partitionerSwitcher;

    static int MAX_HOST_NUM = 1000;
    static List<InetAddress> hostList = IntStream.range(0, MAX_HOST_NUM).mapToObj(i -> {
        try
        {
            return InetAddress.getByName(String.format("127.0.%d.%d", i / 200, (i % 200) + 1));
        }
        catch (UnknownHostException e)
        {
            return null;
        }
    }).collect(toList());

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
    public void testProperties() throws IOException, ConfigurationException
    {
        IEndpointSnitch snitch = new PropertyFileSnitch();
        DatabaseDescriptor.setEndpointSnitch(snitch);
        TokenMetadata metadata = new TokenMetadata();
        createDummyTokens(metadata, true);

        Map<String, String> configOptions = new HashMap<String, String>();
        configOptions.put("DC1", "3");
        configOptions.put("DC2", "2");
        configOptions.put("DC3", "1");

        // Set the localhost to the tokenmetadata. Embedded cassandra way?
        NetworkTopologyStrategy strategy = new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);
        assert strategy.getReplicationFactor("DC1") == 3;
        assert strategy.getReplicationFactor("DC2") == 2;
        assert strategy.getReplicationFactor("DC3") == 1;
        // Query for the natural hosts
        ArrayList<InetAddress> endpoints = strategy.getNaturalEndpoints(new StringToken("123"));
        assert 6 == endpoints.size();
        assert 6 == new HashSet<InetAddress>(endpoints).size(); // ensure uniqueness
    }

    @Test
    public void testPropertiesWithEmptyDC() throws IOException, ConfigurationException
    {
        IEndpointSnitch snitch = new PropertyFileSnitch();
        DatabaseDescriptor.setEndpointSnitch(snitch);
        TokenMetadata metadata = new TokenMetadata();
        createDummyTokens(metadata, false);

        Map<String, String> configOptions = new HashMap<String, String>();
        configOptions.put("DC1", "3");
        configOptions.put("DC2", "3");
        configOptions.put("DC3", "0");

        // Set the localhost to the tokenmetadata. Embedded cassandra way?
        NetworkTopologyStrategy strategy = new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);
        assert strategy.getReplicationFactor("DC1") == 3;
        assert strategy.getReplicationFactor("DC2") == 3;
        assert strategy.getReplicationFactor("DC3") == 0;
        // Query for the natural hosts
        ArrayList<InetAddress> endpoints = strategy.getNaturalEndpoints(new StringToken("123"));
        assert 6 == endpoints.size();
        assert 6 == new HashSet<InetAddress>(endpoints).size(); // ensure uniqueness
    }

    @Test
    public void testLargeCluster() throws UnknownHostException, ConfigurationException
    {
        int[] dcRacks = new int[]{2, 4, 8};
        int[] dcEndpoints = new int[]{128, 256, 512};
        int[] dcReplication = new int[]{2, 6, 6};

        IEndpointSnitch snitch = new RackInferringSnitch();
        DatabaseDescriptor.setEndpointSnitch(snitch);
        TokenMetadata metadata = new TokenMetadata();
        Map<String, String> configOptions = new HashMap<String, String>();
        Multimap<InetAddress, Token> tokens = HashMultimap.create();

        int totalRF = 0;
        for (int dc = 0; dc < dcRacks.length; ++dc)
        {
            totalRF += dcReplication[dc];
            configOptions.put(Integer.toString(dc), Integer.toString(dcReplication[dc]));
            for (int rack = 0; rack < dcRacks[dc]; ++rack)
            {
                for (int ep = 1; ep <= dcEndpoints[dc]/dcRacks[dc]; ++ep)
                {
                    byte[] ipBytes = new byte[]{10, (byte)dc, (byte)rack, (byte)ep};
                    InetAddress address = InetAddress.getByAddress(ipBytes);
                    StringToken token = new StringToken(String.format("%02x%02x%02x", ep, rack, dc));
                    logger.debug("adding node {} at {}", address, token);
                    tokens.put(address, token);
                }
            }
        }
        metadata.updateNormalTokens(tokens);

        NetworkTopologyStrategy strategy = new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);

        for (String testToken : new String[]{"123456", "200000", "000402", "ffffff", "400200"})
        {
            List<InetAddress> endpoints = strategy.calculateNaturalEndpoints(new StringToken(testToken), metadata);
            Set<InetAddress> epSet = new HashSet<InetAddress>(endpoints);

            Assert.assertEquals(totalRF, endpoints.size());
            Assert.assertEquals(totalRF, epSet.size());
            logger.debug("{}: {}", testToken, endpoints);
        }
    }

    public void createDummyTokens(TokenMetadata metadata, boolean populateDC3) throws UnknownHostException
    {
        // DC 1
        tokenFactory(metadata, "123", new byte[]{ 10, 0, 0, 10 });
        tokenFactory(metadata, "234", new byte[]{ 10, 0, 0, 11 });
        tokenFactory(metadata, "345", new byte[]{ 10, 0, 0, 12 });
        // Tokens for DC 2
        tokenFactory(metadata, "789", new byte[]{ 10, 20, 114, 10 });
        tokenFactory(metadata, "890", new byte[]{ 10, 20, 114, 11 });
        //tokens for DC3
        if (populateDC3)
        {
            tokenFactory(metadata, "456", new byte[]{ 10, 21, 119, 13 });
            tokenFactory(metadata, "567", new byte[]{ 10, 21, 119, 10 });
        }
        // Extra Tokens
        tokenFactory(metadata, "90A", new byte[]{ 10, 0, 0, 13 });
        if (populateDC3)
            tokenFactory(metadata, "0AB", new byte[]{ 10, 21, 119, 14 });
        tokenFactory(metadata, "ABC", new byte[]{ 10, 20, 114, 15 });
    }

    public void tokenFactory(TokenMetadata metadata, String token, byte[] bytes) throws UnknownHostException
    {
        Token token1 = new StringToken(token);
        InetAddress add1 = InetAddress.getByAddress(bytes);
        metadata.updateNormalToken(token1, add1);
    }

    public static class HostInfo
    {
        public InetAddress ep;
        public String dc;
        public String rack;
        public List<Token> tokens;

        public HostInfo(InetAddress ep, String dc, String rack, List<Long> tokens)
        {
            this.ep = ep;
            this.dc = dc;
            this.rack = rack;
            this.tokens = tokens.stream().map(l -> new Murmur3Partitioner.LongToken(l)).collect(toList());
        }

        public HostInfo(int index, String dc, String rack, int vNode)
        {
            this.ep = hostList.get(index);
            this.dc = dc;
            this.rack = rack;
            this.tokens = IntStream.range(0, vNode).mapToObj(i -> DatabaseDescriptor.getPartitioner().getRandomToken()).collect(toList());
        }

        public HostInfo(int index, String dc, String rack, List<Long> tokens)
        {
            this.ep = hostList.get(index);
            this.dc = dc;
            this.rack = rack;
            this.tokens = tokens.stream().map(l -> new Murmur3Partitioner.LongToken(l)).collect(toList());
        }
    }

    public static NetworkTopologyStrategy verifyAddressRanges(Map<InetAddress, HostInfo> hosts, int rf)
    {
        IEndpointSnitch snitch = new AbstractEndpointSnitch()
        {
            @Override
            public String getRack(InetAddress endpoint)
            {
                return hosts.get(endpoint).rack;
            }

            @Override
            public String getDatacenter(InetAddress endpoint)
            {
                return hosts.get(endpoint).dc;
            }

            @Override
            public int compareEndpoints(InetAddress target, InetAddress a1, InetAddress a2)
            {
                return 0;
            }
        };

        DatabaseDescriptor.setEndpointSnitch(snitch);

        TokenMetadata metadata = new TokenMetadata();
        hosts.values().stream().forEach(t -> metadata.updateNormalTokens(t.tokens, t.ep));

        Map<String, String> configOptions = new HashMap<>();
        configOptions.put("dc0", String.valueOf(rf));
        configOptions.put("dc1", String.valueOf(rf));
        configOptions.put("dc2", String.valueOf(rf));
        configOptions.put("dc3", String.valueOf(rf));
        configOptions.put("dc4", String.valueOf(rf));
        configOptions.put("dc5", String.valueOf(rf));
        configOptions.put("dc6", String.valueOf(rf));
        configOptions.put("dc7", String.valueOf(rf));

        NetworkTopologyStrategy strategy = new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);

        Multimap<InetAddress, Range<Token>> addressRanges = strategy.getAddressRanges(metadata);

        for (InetAddress ep : hosts.keySet())
        {
            Collection<Range<Token>> r1 = addressRanges.get(ep);
            Collection<Range<Token>> r2 = strategy.getAddressRanges(metadata, ep);
            assertThat(r2, is(r1));
        }

        Multimap<Range<Token>, InetAddress> rangeAddresses = strategy.getRangeAddresses(metadata);
        for (Range<Token> range : rangeAddresses.keySet())
        {
            Collection<InetAddress> e1 = rangeAddresses.get(range);
            Collection<InetAddress> e2 = strategy.getNaturalEndpoints(range.right);
            assertTrue(e1.containsAll(e2));
            assertTrue(e2.containsAll(e1));
        }
        return strategy;
    }

    @Test
    public void testGetAddressRanges()
    {
        Map<InetAddress, HostInfo> hosts = new HashMap<>();
        InetAddress ep1 = hostList.get(0);
        InetAddress ep2 = hostList.get(1);
        InetAddress ep3 = hostList.get(2);
        hosts.put(ep1, new HostInfo(ep1, "dc1", "r1", Arrays.asList(1l)));
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);

        hosts.put(ep1, new HostInfo(ep1, "dc1", "r1", Arrays.asList(1l, 10l)));
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);

        hosts.put(ep2, new HostInfo(ep2, "dc1", "r1", Arrays.asList(2l, 3l)));
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);

        hosts.put(ep3, new HostInfo(ep3, "dc1", "r2", Arrays.asList(4l, 11l)));
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);
    }

    @Test
    public void testGetAddressRangesNormal()
    {
        Map<InetAddress, HostInfo> hosts = new HashMap<>();
        InetAddress ep1 = hostList.get(0);
        InetAddress ep2 = hostList.get(1);

        hosts.put(ep1, new HostInfo(ep1, "dc1", "r1", Arrays.asList(1l, 5l)));
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);

        hosts.put(ep2, new HostInfo(ep2, "dc1", "r1", Arrays.asList(2l, 9l)));
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        NetworkTopologyStrategy strategy = verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);

        logger.info("Token range for {}", ep1);
        Collection<Range<Token>> tokenRanges = strategy.getAddressRanges(ep1);
        for (Range<Token> range : tokenRanges)
        {
            logger.info("  ({} -> {}]", range.left, range.right);
        }
    }

    @Test
    public void testGetAddressRangesVnode()
    {
        Map<InetAddress, HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < 5; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r1", 256));
        }
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);
        verifyAddressRanges(hosts, 4);
        verifyAddressRanges(hosts, 5);
        for (int i = 5; i < 10; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r2", 256));
        }
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);
        verifyAddressRanges(hosts, 4);
        verifyAddressRanges(hosts, 5);
        for (int i = 10; i < 15; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc2", "r3", 256));
        }
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);
        verifyAddressRanges(hosts, 4);
        verifyAddressRanges(hosts, 5);
        for (int i = 15; i < 20; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r2", 256));
        }
        verifyAddressRanges(hosts, 0);
        verifyAddressRanges(hosts, 1);
        verifyAddressRanges(hosts, 2);
        verifyAddressRanges(hosts, 3);
        verifyAddressRanges(hosts, 4);
        verifyAddressRanges(hosts, 5);
    }

    private NetworkTopologyStrategy buildTestStrategy(TokenMetadata metadata, Map<InetAddress, HostInfo> hosts, Map<String, String> configOptions)
    {
        IEndpointSnitch snitch = new AbstractEndpointSnitch()
        {
            @Override
            public String getRack(InetAddress endpoint)
            {
                return hosts.get(endpoint).rack;
            }

            @Override
            public String getDatacenter(InetAddress endpoint)
            {
                return hosts.get(endpoint).dc;
            }

            @Override
            public int compareEndpoints(InetAddress target, InetAddress a1, InetAddress a2)
            {
                return 0;
            }
        };

        DatabaseDescriptor.setEndpointSnitch(snitch);

        hosts.values().stream().forEach(t -> metadata.updateNormalTokens(t.tokens, t.ep));

        return new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);
    }

    private void compareDCIterators(Iterator<DCAwareTokens> expected, Iterator<DCAwareTokens> actual)
    {
        while (expected.hasNext() && actual.hasNext())
        {
            DCAwareTokens dt1 = expected.next();
            DCAwareTokens dt2 = actual.next();
            assertTrue(dt1.getTokens().equals(dt2.getTokens()));
        }
        assertFalse(expected.hasNext());
        assertFalse(actual.hasNext());
    }

    private DCAwareTokens buildDCToken(List<Long> tokens)
    {
        DCAwareTokens ret = new DCAwareTokens();
        for (Long l : tokens)
        {
            ret.add(new Murmur3Partitioner.LongToken(l));
        }
        return ret;
    }

    @Test
    public void testDCIterator()
    {
        final String TEST_DC = "dc1";
        Map<String, String> configOptions = new HashMap<>();
        configOptions.put(TEST_DC, "1");

        // single node, single token
        Map<InetAddress, HostInfo> hosts = new HashMap<>();
        int idx = 0;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(1l)));

        TokenMetadata metadata = new TokenMetadata();
        NetworkTopologyStrategy strategy = buildTestStrategy(metadata, hosts, configOptions);

        Token first = metadata.sortedTokens().get(0);
        Token end = metadata.sortedTokens().get(0);
        Iterator<DCAwareTokens> iter = strategy.dcRingReverseIterator(metadata, first, end, TEST_DC);

        List<DCAwareTokens> expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(1l, 1l)));
        Iterator<DCAwareTokens> expectedIter = expected.iterator();

        compareDCIterators(expectedIter, iter);

        // single node, multiple tokens
        hosts = new HashMap<>();
        idx = 0;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(1l, 3l, 5l)));

        metadata = new TokenMetadata();
        strategy = buildTestStrategy(metadata, hosts, configOptions);

        first = metadata.sortedTokens().get(0);
        end = metadata.sortedTokens().get(0);
        iter = strategy.dcRingReverseIterator(metadata, first, end, "dc1");

        expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(1l, 5l, 3l, 1l)));
        expectedIter = expected.iterator();

        compareDCIterators(expectedIter, iter);

        // search from middle
        first = metadata.sortedTokens().get(1);
        end = metadata.sortedTokens().get(2);
        iter = strategy.dcRingReverseIterator(metadata, first, end, "dc1");

        expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(3l, 1l, 5l)));
        expectedIter = expected.iterator();

        compareDCIterators(expectedIter, iter);

        // 2 nodes, single token
        hosts = new HashMap<>();
        idx = 0;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(1l)));
        idx++;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(2l)));

        metadata = new TokenMetadata();
        strategy = buildTestStrategy(metadata, hosts, configOptions);

        first = metadata.sortedTokens().get(0);
        end = metadata.sortedTokens().get(0);
        iter = strategy.dcRingReverseIterator(metadata, first, end, "dc1");

        expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(1l, 2l)));
        expected.add(buildDCToken(Arrays.asList(2l, 1l)));
        expectedIter = expected.iterator();

        compareDCIterators(expectedIter, iter);

        // 2 nodes, multiple tokens
        hosts = new HashMap<>();
        idx = 0;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(1l, 3l, 8l)));
        idx++;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(2l, 10l, 11l)));

        metadata = new TokenMetadata();
        strategy = buildTestStrategy(metadata, hosts, configOptions);

        first = metadata.sortedTokens().get(0);
        end = metadata.sortedTokens().get(0);
        iter = strategy.dcRingReverseIterator(metadata, first, end, "dc1");

        expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(1l, 11l)));
        expected.add(buildDCToken(Arrays.asList(11l, 10l, 8l)));
        expected.add(buildDCToken(Arrays.asList(8l, 3l, 2l)));
        expected.add(buildDCToken(Arrays.asList(2l, 1l)));
        expectedIter = expected.iterator();

        compareDCIterators(expectedIter, iter);

        // 2 nodes + 1 other DC nodes, multiple tokens
        hosts = new HashMap<>();
        idx = 0;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(1l, 3l, 8l)));
        idx++;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(2l, 10l, 11l)));
        idx++;
        hosts.put(hostList.get(idx), new HostInfo(idx, "dc2", "r1", Arrays.asList(4l, 6l, 12l)));

        metadata = new TokenMetadata();
        strategy = buildTestStrategy(metadata, hosts, configOptions);

        first = metadata.sortedTokens().get(0);
        end = metadata.sortedTokens().get(0);
        iter = strategy.dcRingReverseIterator(metadata, first, end, "dc1");

        expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(1l, 12l, 11l)));
        expected.add(buildDCToken(Arrays.asList(11l, 10l, 8l)));
        expected.add(buildDCToken(Arrays.asList(8l, 6l, 4l, 3l, 2l)));
        expected.add(buildDCToken(Arrays.asList(2l, 1l)));
        expectedIter = expected.iterator();

        compareDCIterators(expectedIter, iter);

        // Find non-existent token
        Token nonExistent =  new Murmur3Partitioner.LongToken(7l);
        iter = strategy.dcRingReverseIterator(metadata, nonExistent, end, "dc1");
        assertFalse(iter.hasNext());
    }
}
