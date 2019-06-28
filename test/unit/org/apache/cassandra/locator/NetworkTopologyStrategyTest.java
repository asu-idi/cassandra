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
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMultimap;
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
import org.apache.cassandra.dht.Murmur3Partitioner.LongToken;
import org.apache.cassandra.dht.OrderPreservingPartitioner.StringToken;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.locator.TokenMetadata.Topology;
import org.apache.cassandra.service.StorageService;

import static java.util.stream.Collectors.toList;
import static org.apache.cassandra.locator.Replica.fullReplica;
import static org.apache.cassandra.locator.Replica.transientReplica;
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

    public static int MAX_TEST_HOST_NUM = 2000;
    static List<InetAddressAndPort> hostList = IntStream.range(0, MAX_TEST_HOST_NUM).mapToObj(i -> {
        try
        {
            return InetAddressAndPort.getByName(String.format("127.0.%d.%d", i / 200, (i % 200) + 1));
        }
        catch (UnknownHostException e)
        {
            return null;
        }
    }).collect(toList());

    @BeforeClass
    public static void setupDD()
    {
        DatabaseDescriptor.daemonInitialization();
        DatabaseDescriptor.setTransientReplicationEnabledUnsafe(true);
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
        assert strategy.getReplicationFactor("DC1").allReplicas == 3;
        assert strategy.getReplicationFactor("DC2").allReplicas == 2;
        assert strategy.getReplicationFactor("DC3").allReplicas == 1;
        // Query for the natural hosts
        EndpointsForToken replicas = strategy.getNaturalReplicasForToken(new StringToken("123"));
        assert 6 == replicas.size();
        assert 6 == replicas.endpoints().size(); // ensure uniqueness
        assert 6 == new HashSet<>(replicas.byEndpoint().values()).size(); // ensure uniqueness
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
        assert strategy.getReplicationFactor("DC1").allReplicas == 3;
        assert strategy.getReplicationFactor("DC2").allReplicas == 3;
        assert strategy.getReplicationFactor("DC3").allReplicas == 0;
        // Query for the natural hosts
        EndpointsForToken replicas = strategy.getNaturalReplicasForToken(new StringToken("123"));
        assert 6 == replicas.size();
        assert 6 == replicas.endpoints().size(); // ensure uniqueness
        assert 6 == new HashSet<>(replicas.byEndpoint().values()).size(); // ensure uniqueness
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
        Multimap<InetAddressAndPort, Token> tokens = HashMultimap.create();

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
                    InetAddressAndPort address = InetAddressAndPort.getByAddress(ipBytes);
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
            EndpointsForRange replicas = strategy.calculateNaturalReplicas(new StringToken(testToken), metadata);
            Set<InetAddressAndPort> endpointSet = replicas.endpoints();

            Assert.assertEquals(totalRF, replicas.size());
            Assert.assertEquals(totalRF, new HashSet<>(replicas.byEndpoint().values()).size());
            Assert.assertEquals(totalRF, endpointSet.size());
            logger.debug("{}: {}", testToken, replicas);
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
        InetAddressAndPort add1 = InetAddressAndPort.getByAddress(bytes);
        metadata.updateNormalToken(token1, add1);
    }

    public static class HostInfo
    {
        public InetAddressAndPort ep;
        public String dc;
        public String rack;
        public List<Token> tokens;

        public HostInfo(InetAddressAndPort ep, String dc, String rack, List<Long> tokens)
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

    public static NetworkTopologyStrategy verifyEndpointRanges(Map<InetAddressAndPort, HostInfo> hosts, int rf)
    {
        return verifyEndpointRanges(hosts, rf, new TokenMetadata());
    }

    public static NetworkTopologyStrategy verifyEndpointRanges(Map<InetAddressAndPort, HostInfo> hosts, int rf, TokenMetadata metadata)
    {
        IEndpointSnitch snitch = new AbstractEndpointSnitch()
        {
            public String getRack(InetAddressAndPort endpoint)
            {
                return hosts.get(endpoint).rack;
            }

            public String getDatacenter(InetAddressAndPort endpoint)
            {
                return hosts.get(endpoint).dc;
            }

            public int compareEndpoints(InetAddressAndPort target, Replica a1, Replica a2)
            {
                return 0;
            }
        };

        DatabaseDescriptor.setEndpointSnitch(snitch);

        hosts.values().stream().forEach(t -> metadata.updateNormalTokens(t.tokens, t.ep));

        Map<String, String> configOptions = IntStream.range(0, 8).mapToObj(i -> String.format("dc%d", i))
                                                     .collect(Collectors.toMap(x -> x, x -> String.valueOf(rf)));

        NetworkTopologyStrategy strategy = new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);

        RangesByEndpoint endpointToRanges = strategy.getAddressReplicas(metadata);

        for (InetAddressAndPort ep : hosts.keySet())
        {
            RangesAtEndpoint r1 = endpointToRanges.get(ep);
            RangesAtEndpoint r2 = strategy.getAddressReplicas(metadata, ep);
            assertThat(r2.ranges(), is(r1.ranges()));
        }

        return strategy;
    }

    @Test
    public void testGetAddressRanges()
    {
        Map<InetAddressAndPort, HostInfo> hosts = new HashMap<>();
        InetAddressAndPort ep1 = hostList.get(0);
        InetAddressAndPort ep2 = hostList.get(1);
        InetAddressAndPort ep3 = hostList.get(2);
        hosts.put(ep1, new HostInfo(ep1, "dc1", "r1", Arrays.asList(1l)));
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);

        hosts.put(ep1, new HostInfo(ep1, "dc1", "r1", Arrays.asList(1l, 10l)));
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);

        hosts.put(ep2, new HostInfo(ep2, "dc1", "r1", Arrays.asList(2l, 3l)));
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);

        hosts.put(ep3, new HostInfo(ep3, "dc1", "r2", Arrays.asList(4l, 11l)));
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
    }

    @Test
    public void testGetEndpointRangesVnode()
    {
        Map<InetAddressAndPort, HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < 5; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r1", 256));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
        for (int i = 5; i < 10; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r2", 256));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
        for (int i = 10; i < 15; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc2", "r3", 256));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
        for (int i = 15; i < 20; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r2", 256));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
    }

    @Test
    public void testGetEndpointRangesNoneVnode()
    {
        Map<InetAddressAndPort, HostInfo> hosts = new HashMap<>();
        for (int i = 0; i < 5; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r1", 1));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
        for (int i = 5; i < 10; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r2", 1));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
        for (int i = 10; i < 15; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc2", "r3", 1));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
        for (int i = 15; i < 20; i++)
        {
            hosts.put(hostList.get(i), new HostInfo(i, "dc1", "r2", 1));
        }
        verifyEndpointRanges(hosts, 0);
        verifyEndpointRanges(hosts, 1);
        verifyEndpointRanges(hosts, 2);
        verifyEndpointRanges(hosts, 3);
        verifyEndpointRanges(hosts, 4);
        verifyEndpointRanges(hosts, 5);
    }

    @Test
    public void testCalculateEndpoints() throws UnknownHostException
    {
        final int NODES = 100;
        final int VNODES = 64;
        final int RUNS = 10;
        StorageService.instance.setPartitionerUnsafe(Murmur3Partitioner.instance);
        Map<String, Integer> datacenters = ImmutableMap.of("rf1", 1, "rf3", 3, "rf5_1", 5, "rf5_2", 5, "rf5_3", 5);
        List<InetAddressAndPort> nodes = new ArrayList<>(NODES);
        for (byte i=0; i<NODES; ++i)
            nodes.add(InetAddressAndPort.getByAddress(new byte[]{ 127, 0, 0, i}));
        for (int run=0; run<RUNS; ++run)
        {
            Random rand = new Random();
            IEndpointSnitch snitch = generateSnitch(datacenters, nodes, rand);
            DatabaseDescriptor.setEndpointSnitch(snitch);

            TokenMetadata meta = new TokenMetadata();
            for (int i=0; i<NODES; ++i)  // Nodes
                for (int j=0; j<VNODES; ++j) // tokens/vnodes per node
                    meta.updateNormalToken(Murmur3Partitioner.instance.getRandomToken(rand), nodes.get(i));
            testEquivalence(meta, snitch, datacenters, rand);
        }
    }

    void testEquivalence(TokenMetadata tokenMetadata, IEndpointSnitch snitch, Map<String, Integer> datacenters, Random rand)
    {
        NetworkTopologyStrategy nts = new NetworkTopologyStrategy("ks", tokenMetadata, snitch,
                                                                  datacenters.entrySet().stream().
                                                                      collect(Collectors.toMap(x -> x.getKey(), x -> Integer.toString(x.getValue()))));
        for (int i=0; i<1000; ++i)
        {
            Token token = Murmur3Partitioner.instance.getRandomToken(rand);
            List<InetAddressAndPort> expected = calculateNaturalEndpoints(token, tokenMetadata, datacenters, snitch);
            List<InetAddressAndPort> actual = new ArrayList<>(nts.calculateNaturalReplicas(token, tokenMetadata).endpoints());
            if (endpointsDiffer(expected, actual))
            {
                System.err.println("Endpoints mismatch for token " + token);
                System.err.println(" expected: " + expected);
                System.err.println(" actual  : " + actual);
                Assert.assertEquals("Endpoints for token " + token + " mismatch.", expected, actual);
            }
        }
    }

    private boolean endpointsDiffer(List<InetAddressAndPort> ep1, List<InetAddressAndPort> ep2)
    {
        // Because the old algorithm does not put the nodes in the correct order in the case where more replicas
        // are required than there are racks in a dc, we accept different order as long as the primary
        // replica is the same.
        if (ep1.equals(ep2))
            return false;
        if (!ep1.get(0).equals(ep2.get(0)))
            return true;
        Set<InetAddressAndPort> s1 = new HashSet<>(ep1);
        Set<InetAddressAndPort> s2 = new HashSet<>(ep2);
        return !s1.equals(s2);
    }

    IEndpointSnitch generateSnitch(Map<String, Integer> datacenters, Collection<InetAddressAndPort> nodes, Random rand)
    {
        final Map<InetAddressAndPort, String> nodeToRack = new HashMap<>();
        final Map<InetAddressAndPort, String> nodeToDC = new HashMap<>();
        Map<String, List<String>> racksPerDC = new HashMap<>();
        datacenters.forEach((dc, rf) -> racksPerDC.put(dc, randomRacks(rf, rand)));
        int rf = datacenters.values().stream().mapToInt(x -> x).sum();
        String[] dcs = new String[rf];
        int pos = 0;
        for (Map.Entry<String, Integer> dce : datacenters.entrySet())
        {
            for (int i = 0; i < dce.getValue(); ++i)
                dcs[pos++] = dce.getKey();
        }

        for (InetAddressAndPort node : nodes)
        {
            String dc = dcs[rand.nextInt(rf)];
            List<String> racks = racksPerDC.get(dc);
            String rack = racks.get(rand.nextInt(racks.size()));
            nodeToRack.put(node, rack);
            nodeToDC.put(node, dc);
        }

        return new AbstractNetworkTopologySnitch()
        {
            public String getRack(InetAddressAndPort endpoint)
            {
                return nodeToRack.get(endpoint);
            }

            public String getDatacenter(InetAddressAndPort endpoint)
            {
                return nodeToDC.get(endpoint);
            }
        };
    }

    private List<String> randomRacks(int rf, Random rand)
    {
        int rc = rand.nextInt(rf * 3 - 1) + 1;
        List<String> racks = new ArrayList<>(rc);
        for (int i=0; i<rc; ++i)
            racks.add(Integer.toString(i));
        return racks;
    }

    // Copy of older endpoints calculation algorithm for comparison
    public static List<InetAddressAndPort> calculateNaturalEndpoints(Token searchToken, TokenMetadata tokenMetadata, Map<String, Integer> datacenters, IEndpointSnitch snitch)
    {
        // we want to preserve insertion order so that the first added endpoint becomes primary
        Set<InetAddressAndPort> replicas = new LinkedHashSet<>();
        // replicas we have found in each DC
        Map<String, Set<InetAddressAndPort>> dcReplicas = new HashMap<>(datacenters.size());
        for (Map.Entry<String, Integer> dc : datacenters.entrySet())
            dcReplicas.put(dc.getKey(), new HashSet<InetAddressAndPort>(dc.getValue()));

        Topology topology = tokenMetadata.getTopology();
        // all endpoints in each DC, so we can check when we have exhausted all the members of a DC
        Multimap<String, InetAddressAndPort> allEndpoints = topology.getDatacenterEndpoints();
        // all racks in a DC so we can check when we have exhausted all racks in a DC
        Map<String, ImmutableMultimap<String, InetAddressAndPort>> racks = topology.getDatacenterRacks();
        assert !allEndpoints.isEmpty() && !racks.isEmpty() : "not aware of any cluster members";

        // tracks the racks we have already placed replicas in
        Map<String, Set<String>> seenRacks = new HashMap<>(datacenters.size());
        for (Map.Entry<String, Integer> dc : datacenters.entrySet())
            seenRacks.put(dc.getKey(), new HashSet<String>());

        // tracks the endpoints that we skipped over while looking for unique racks
        // when we relax the rack uniqueness we can append this to the current result so we don't have to wind back the iterator
        Map<String, Set<InetAddressAndPort>> skippedDcEndpoints = new HashMap<>(datacenters.size());
        for (Map.Entry<String, Integer> dc : datacenters.entrySet())
            skippedDcEndpoints.put(dc.getKey(), new LinkedHashSet<InetAddressAndPort>());

        Iterator<Token> tokenIter = TokenMetadata.ringIterator(tokenMetadata.sortedTokens(), searchToken, false);
        while (tokenIter.hasNext() && !hasSufficientReplicas(dcReplicas, allEndpoints, datacenters))
        {
            Token next = tokenIter.next();
            InetAddressAndPort ep = tokenMetadata.getEndpoint(next);
            String dc = snitch.getDatacenter(ep);
            // have we already found all replicas for this dc?
            if (!datacenters.containsKey(dc) || hasSufficientReplicas(dc, dcReplicas, allEndpoints, datacenters))
                continue;
            // can we skip checking the rack?
            if (seenRacks.get(dc).size() == racks.get(dc).keySet().size())
            {
                dcReplicas.get(dc).add(ep);
                replicas.add(ep);
            }
            else
            {
                String rack = snitch.getRack(ep);
                // is this a new rack?
                if (seenRacks.get(dc).contains(rack))
                {
                    skippedDcEndpoints.get(dc).add(ep);
                }
                else
                {
                    dcReplicas.get(dc).add(ep);
                    replicas.add(ep);
                    seenRacks.get(dc).add(rack);
                    // if we've run out of distinct racks, add the hosts we skipped past already (up to RF)
                    if (seenRacks.get(dc).size() == racks.get(dc).keySet().size())
                    {
                        Iterator<InetAddressAndPort> skippedIt = skippedDcEndpoints.get(dc).iterator();
                        while (skippedIt.hasNext() && !hasSufficientReplicas(dc, dcReplicas, allEndpoints, datacenters))
                        {
                            InetAddressAndPort nextSkipped = skippedIt.next();
                            dcReplicas.get(dc).add(nextSkipped);
                            replicas.add(nextSkipped);
                        }
                    }
                }
            }
        }

        return new ArrayList<InetAddressAndPort>(replicas);
    }

    private static boolean hasSufficientReplicas(String dc, Map<String, Set<InetAddressAndPort>> dcReplicas, Multimap<String, InetAddressAndPort> allEndpoints, Map<String, Integer> datacenters)
    {
        return dcReplicas.get(dc).size() >= Math.min(allEndpoints.get(dc).size(), getReplicationFactor(dc, datacenters));
    }

    private static boolean hasSufficientReplicas(Map<String, Set<InetAddressAndPort>> dcReplicas, Multimap<String, InetAddressAndPort> allEndpoints, Map<String, Integer> datacenters)
    {
        for (String dc : datacenters.keySet())
            if (!hasSufficientReplicas(dc, dcReplicas, allEndpoints, datacenters))
                return false;
        return true;
    }

    public static int getReplicationFactor(String dc, Map<String, Integer> datacenters)
    {
        Integer replicas = datacenters.get(dc);
        return replicas == null ? 0 : replicas;
    }

    private static Token tk(long t)
    {
        return new LongToken(t);
    }

    private static Range<Token> range(long l, long r)
    {
        return new Range<>(tk(l), tk(r));
    }

    @Test
    public void testTransientReplica() throws Exception
    {
        IEndpointSnitch snitch = new SimpleSnitch();
        DatabaseDescriptor.setEndpointSnitch(snitch);

        List<InetAddressAndPort> endpoints = Lists.newArrayList(InetAddressAndPort.getByName("127.0.0.1"),
                                                                InetAddressAndPort.getByName("127.0.0.2"),
                                                                InetAddressAndPort.getByName("127.0.0.3"),
                                                                InetAddressAndPort.getByName("127.0.0.4"));

        Multimap<InetAddressAndPort, Token> tokens = HashMultimap.create();
        tokens.put(endpoints.get(0), tk(100));
        tokens.put(endpoints.get(1), tk(200));
        tokens.put(endpoints.get(2), tk(300));
        tokens.put(endpoints.get(3), tk(400));
        TokenMetadata metadata = new TokenMetadata();
        metadata.updateNormalTokens(tokens);

        Map<String, String> configOptions = new HashMap<String, String>();
        configOptions.put(snitch.getDatacenter((InetAddressAndPort) null), "3/1");

        NetworkTopologyStrategy strategy = new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);

        Util.assertRCEquals(EndpointsForRange.of(fullReplica(endpoints.get(0), range(400, 100)),
                                               fullReplica(endpoints.get(1), range(400, 100)),
                                               transientReplica(endpoints.get(2), range(400, 100))),
                            strategy.getNaturalReplicasForToken(tk(99)));


        Util.assertRCEquals(EndpointsForRange.of(fullReplica(endpoints.get(1), range(100, 200)),
                                               fullReplica(endpoints.get(2), range(100, 200)),
                                               transientReplica(endpoints.get(3), range(100, 200))),
                            strategy.getNaturalReplicasForToken(tk(101)));
    }


    private NetworkTopologyStrategy buildTestStrategy(TokenMetadata metadata, Map<InetAddressAndPort, HostInfo> hosts, Map<String, String> configOptions)
    {
        IEndpointSnitch snitch = new AbstractEndpointSnitch()
        {
            public String getRack(InetAddressAndPort endpoint)
            {
                return hosts.get(endpoint).rack;
            }

            public String getDatacenter(InetAddressAndPort endpoint)
            {
                return hosts.get(endpoint).dc;
            }

            public int compareEndpoints(InetAddressAndPort target, Replica r1, Replica r2)
            {
                return 0;
            }
        };

        DatabaseDescriptor.setEndpointSnitch(snitch);

        hosts.values().stream().forEach(t -> metadata.updateNormalTokens(t.tokens, t.ep));

        return new NetworkTopologyStrategy(keyspaceName, metadata, snitch, configOptions);
    }

    private void compareDCIterators(Iterator<NetworkTopologyStrategy.DCAwareTokens> expected, Iterator<NetworkTopologyStrategy.DCAwareTokens> actual)
    {
        while (expected.hasNext() && actual.hasNext())
        {
            NetworkTopologyStrategy.DCAwareTokens dt1 = expected.next();
            NetworkTopologyStrategy.DCAwareTokens dt2 = actual.next();
            assertTrue(dt1.getTokens().equals(dt2.getTokens()));
        }
        assertFalse(expected.hasNext());
        assertFalse(actual.hasNext());
    }

    private NetworkTopologyStrategy.DCAwareTokens buildDCToken(List<Long> tokens)
    {
        NetworkTopologyStrategy.DCAwareTokens ret = new NetworkTopologyStrategy.DCAwareTokens();
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
        Map<InetAddressAndPort, HostInfo> hosts = new HashMap<>();
        int idx = 0;
        hosts.put(hostList.get(idx), new HostInfo(idx, TEST_DC, "r1", Arrays.asList(1l)));

        TokenMetadata metadata = new TokenMetadata();
        NetworkTopologyStrategy strategy = buildTestStrategy(metadata, hosts, configOptions);

        Token first = metadata.sortedTokens().get(0);
        Token end = metadata.sortedTokens().get(0);
        Iterator<NetworkTopologyStrategy.DCAwareTokens> iter = strategy.dcRingReverseIterator(metadata, first, end, TEST_DC);

        List<NetworkTopologyStrategy.DCAwareTokens> expected = new ArrayList<>();
        expected.add(buildDCToken(Arrays.asList(1l, 1l)));
        Iterator<NetworkTopologyStrategy.DCAwareTokens> expectedIter = expected.iterator();

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
