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
package org.apache.cassandra.dht;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.cassandra.OrderedJUnit4ClassRunner;
import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.Schema;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.dht.tokenallocator.TokenAllocation;
import org.apache.cassandra.dht.Murmur3Partitioner.LongToken;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.gms.IFailureDetectionEventListener;
import org.apache.cassandra.gms.IFailureDetector;
import org.apache.cassandra.locator.AbstractReplicationStrategy;
import org.apache.cassandra.locator.IEndpointSnitch;
import org.apache.cassandra.locator.RackInferringSnitch;
import org.apache.cassandra.locator.TokenMetadata;
import org.apache.cassandra.schema.KeyspaceMetadata;
import org.apache.cassandra.schema.KeyspaceParams;
import org.apache.cassandra.service.MigrationManager;
import org.apache.cassandra.service.PendingRangeCalculatorService;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.utils.FBUtilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(OrderedJUnit4ClassRunner.class)
public class BootStrapperTest
{
    static IPartitioner oldPartitioner;

    @BeforeClass
    public static void setup() throws ConfigurationException
    {
        oldPartitioner = StorageService.instance.setPartitionerUnsafe(Murmur3Partitioner.instance);
        SchemaLoader.startGossiper();
        SchemaLoader.prepareServer();
        SchemaLoader.schemaDefinition("BootStrapperTest");
    }

    @AfterClass
    public static void tearDown()
    {
        DatabaseDescriptor.setPartitionerUnsafe(oldPartitioner);
    }

    @Test
    public void testMultiDCReplacementWithVNode() throws Exception
    {
        IEndpointSnitch oldSnitch = DatabaseDescriptor.getEndpointSnitch();
        try
        {
            DatabaseDescriptor.setEndpointSnitch(new RackInferringSnitch());
            KeyspaceMetadata ksm = KeyspaceMetadata.create("BootStrapperTest_MultiDCReplace", KeyspaceParams.nts("0", 1, "1", 1, "2", 1, "3", 1, "4", 1));
            MigrationManager.announceNewKeyspace(ksm, false);
            AbstractReplicationStrategy replicationStrategy = Keyspace.open(ksm.name).getReplicationStrategy();

            int numOfNodePerDC = 8;
            StorageService ss = StorageService.instance;
            TokenMetadata tmd = ss.getTokenMetadata();

            long token_values[] = {5084980778252569455L, 3101447932367826944L, 3270919289310571121L, -3901416202458062465L, 8191229876956863020L,
                                   -1339503230691573665L, 7367535544457133487L, -2216262685409667470L, -4667650328015180305L, -5383010049489895329L,
                                   4987068800566715205L, -6068346235846125371L, 730125584661252790L, 2571682227644509740L, -8982129481568912228L,
                                   -1466550000720157823L, -6615871680941358763L, -3556879989488870682L, -3138069549360284524L, -2889964138046592444L,
                                   -8658341409016418979L, -231684740226115421L, -1723684991750741749L, -6150138488745314305L, -4421643281547515535L,
                                   8675558990152120271L, -3841670839857174414L, 5660654619500359472L, 8032744495012937133L, 2813345414660131404L,
                                   9006452881269203407L, 1468089129958660280L, 7517515980613100243L, -8288265228886576713L, -6315580327849565389L,
                                   -4455185622313857670L, 4394435872654567800L, -8280125650809646867L, -6923951936410222330L, -2244274923028084946L,
                                   1996477364895481805L, 5127078073317552406L, 5682305748128366385L, 5573272681453777010L, -771095811807678145L,
                                   1255568198321645293L, 648506746449509856L, 8081539282525688580L, 8353397477052696970L, -6392357808050139493L,
                                   -9047318783633264245L, -7061856402222796339L, 3815719794175996644L, -7700192678423912780L, 7449409571467371071L,
                                   6956047809415846071L, 552064432296590837L, -1735866613282816135L, 6422529148045523886L, 6292928819654560557L,
                                   4290842174892324404L, -6901336083602550866L, 4548443870771812525L, -891774958179079764L, 1495921911875236539L,
                                   3507943999113906385L, -4912500634166876531L, -5948450585751382765L, -3525831461523728280L, 7461499599511803524L,
                                   2365779298814787078L, 6102376959724918036L, -6769010538473649054L, 150144197582162626L, -6345819632546185573L,
                                   673280859348292455L, -5263320262219820792L, -1024826021664822818L, -295443902142475719L, 4306966823889681509L,
                                   -963162241811714185L, -2961662962845459828L, 4663553973057109701L, 1939660751422505077L, -1138055313523230988L,
                                   7785029443401124618L, -8896257888006249892L, 1522297150849817937L, -1459461576031839629L, 5322610360754939212L,
                                   2198327997436609727L, 4549915456176204313L, -7450756318320549359L, 8918773267006646057L, 8641977151663380002L,
                                   -7687778470657947379L, 6141047999193128752L, -346318907872135233L, 1670733679555379550L, -8980779829428856960L,
                                   7537667977714243759L, -4680202975131516745L, 6727350125455920795L, 386493337474621899L, 2282593445429594614L,
                                   1904467143452793162L, -7635716530022137113L, 1331755246989539796L, 8914569597073614836L, -7280811113178418943L,
                                   -6960938425498775345L, 5120191627848693006L, 5013909124936263734L, -2852983526604221994L, 6733021604480206977L,
                                   1495430280676235045L, -8243730095749127354L, 8460956226584331115L, 8470338941943701663L, 5674519387850103400L,
                                   2021335170415225391L, -4457994207404542853L, -4211259499608528505L, 3414650115806579449L, 6022657180927731911L,
                                   2133758642923827046L, -2440441162898543552L, -2219149429225611710L, -7662471949311881127L, -1271531554342615094L,
                                   6472077127341943528L, -1318800639548292313L, 3337332614786851104L, -2873536736985872264L, 4886659228468276263L,
                                   -4405738218228100211L, 2216902004017490770L, -2303149045894156462L, 944605834382130988L, -7941074820240396448L,
                                   -5345201864941477045L, 2997950904206553323L, -4708762172940024194L, -4530104893601563087L, -604861532458589633L,
                                   -2723790374363091545L, 3427663250979851012L, -8020289696603065154L, -7865909297178723061L, 7030049673088889572L,
                                   2703994884649482521L, -4365641036067234056L, -2229529793135687685L, -1397462182665312774L, -4442556297062127546L,
                                   4940058199743362611L, 6017925528357639687L, 4915557791743055146L, -3246470534456671914L, 7357181781252566703L,
                                   2119376509221750795L, -763989664794894264L, -5674356917424452817L, 2909289323896501560L, 933462853948663156L,
                                   -7547202055452500860L, -3917512885152814048L, -8018883777373614641L, 6405075807499815989L, -930247758109016867L,
                                   -4700059234627284325L, -6015844776820664108L, -6442995699959768268L, -1217303011929787893L, 259578099117961530L,
                                   2976435716877102048L, -8609055430139563761L, 5778153105691195978L, -6050340903759496645L, 7273077381345441689L,
                                   6743437341506490063L, 1986776306730079894L, -4723823611819031304L, 499352839158248803L, 1743697018636464849L,
                                   7777500245349423222L, -6874383574364423008L, 740636723600605536L, 4193977070405622614L, -3473011273828400278L,
                                   -8592979337652869058L, 4740186955784428973L, -3682241506429175639L, -5831161383069514543L, 2595322818505954020L,
                                   1851921152628458800L, 7612226674292421622L, 3460112440380934997L, 539089275698627523L, -9037700715312853454L,
                                   9114434254537576597L, 6016934160565396779L, -6978249699887530791L, 1710073202082378785L, 2193296103446373419L,
                                   218232194942009984L, -550401145971218436L, -4134588970392370073L, -6199856625260020628L, 5229849344632209138L,
                                   5648791648605460239L, -606357108390979270L, -1418773767142517961L, -8627483860071278621L, -1819686089508583286L,
                                   -213522897794530357L, -8325540532063607407L, 4997954047163998532L, -2125091490439370844L, -4064713263508959677L,
                                   -5337204672930850320L, 3790572405476955902L, 486488889130871675L, 1639425805760563392L, 7616421105090478991L,
                                   7614881258338292723L, -2046492179971368791L, 2247052844674907075L, -8242390176358472231L, -3865637052933986081L,
                                   3835923607324870073L, 3290103583938879647L, 8121376246368525705L, -751440607269464949L, 4441051732723863981L,
                                   3406736889188607720L, -9028184354525238536L, 5709194146290149106L, 1858136223695536475L, -6279991238420210797L,
                                   -7287246956737529846L, -4724384892321059435L, -2745688039831473809L, -9119177952471231000L, -3954010259929162847L,
                                   -5267177801095349908L, -5526471244185823795L, -7801128938950911222L, 8545692038467514116L, 8508870042746523770L,
                                   -6612636624193786115L, 9170811663452491200L, -2349018680960160475L, 3855176166057673118L, 1347786967071413823L,
                                   -6588441579666804887L, -4569144084051509957L, -7480848676349431458L, 1805179391921007482L, 7488557163093575444L,
                                   7063164515086561593L, -2489238124073890L, -5422750985911169712L, 4816558753448183306L, -6725236314718528777L,
                                   -6771398336001728676L, -4448709313555429336L, -2861963150956052529L, -2887864634215192926L, 5515617184739056364L,
                                   -7934151065284244929L, 5825051573834736242L, 8942478193079803864L, 1801404776831061109L, -3068601119657472408L,
                                   4586587916606166421L, -7547432205104109113L, 4095562723372253715L, 9117546868091647943L, -7288194458652426946L,
                                   3920954285428344004L, -4086026786151524142L, 4702190948216081390L, -4221928935038202474L, -2290378143345845117L,
                                   -7046737470161593743L, -86014677058375214L, 8302750479230301623L, -5474868882886779670L, 1679070681226889471L,
                                   -6695979234409706351L, -701591667311524218L, -3102673036025858651L, 5786151809502668607L, 8164288743796660349L,
                                   3591281482225494697L, -802292080398841476L, -1625739848428329429L, 8374901829170888464L, -7077949055286435807L,
                                   5450829705618699040L, -3796135998903911734L, 6008630442524361730L, -3358807196327269539L, 4023337094460820846L,
                                   -1657013739074390077L, 3912296021154436390L, 968071927421971892L, 2948000557946444714L, -208585829668329942L,
                                   -4774214990645313566L, -1455077669912179746L, 638102725799024814L, 433520226046154395L, 6849308407650094768L,
                                   -8668564488742074816L, -4098257028126543060L, 3154146103131639205L, 5151655685062282748L, -1076455661368917358L};

            int numVNodes = 8;
            int n = 0;
            for (int i = 0; i < ksm.params.replication.options.size(); i++)
            {
                //generateFakeEndpoints(tmd, numOfNodePerDC, 8, String.valueOf(i), "0");
                for (int j = 1; j <= numOfNodePerDC; j++)
                {
                    // leave .1 for myEndpoint
                    InetAddress addr = InetAddress.getByName("127." + String.valueOf(i) + ".0." + (j + 1));
                    List<Token> tokens = Lists.newArrayListWithCapacity(numVNodes);
                    for (int k = 0; k < numVNodes; ++k)
                        tokens.add(new LongToken(token_values[n++]));

                    tmd.updateNormalTokens(tokens, addr);
                }
            }

            InetAddress replacement = InetAddress.getByName("127.0.0.1");
            InetAddress toBeReplaced = InetAddress.getByName("127.0.0.2");
            Collection<Token> replacingTokens = tmd.getTokens(toBeReplaced);

            Collection<Range<Token>> replacingTokenRange = replicationStrategy.getPendingAddressRanges(tmd, replacingTokens, replacement);
            tmd.addReplaceTokens(replacingTokens, replacement, toBeReplaced);
            PendingRangeCalculatorService.instance.update();

            System.setProperty("cassandra.enable_token_balance_streaming", "false");
            RangeStreamer s_nonbalance = new RangeStreamer(tmd, replacingTokens, replacement, "Bootstrap", false, DatabaseDescriptor.getEndpointSnitch(), new StreamStateStore());
            IFailureDetector mockFailureDetector = new IFailureDetector()
            {
                public boolean isAlive(InetAddress ep)
                {
                    return !ep.equals(toBeReplaced);
                }

                public void interpret(InetAddress ep)
                {
                    throw new UnsupportedOperationException();
                }

                public void report(InetAddress ep)
                {
                    throw new UnsupportedOperationException();
                }

                public void registerFailureDetectionEventListener(IFailureDetectionEventListener listener)
                {
                    throw new UnsupportedOperationException();
                }

                public void unregisterFailureDetectionEventListener(IFailureDetectionEventListener listener)
                {
                    throw new UnsupportedOperationException();
                }

                public void remove(InetAddress ep)
                {
                    throw new UnsupportedOperationException();
                }

                public void forceConviction(InetAddress ep)
                {
                    throw new UnsupportedOperationException();
                }
            };
            s_nonbalance.addSourceFilter(new RangeStreamer.FailureDetectorSourceFilter(mockFailureDetector));
            s_nonbalance.addSourceFilter(new RangeStreamer.ExcludeLocalNodeFilter());
            s_nonbalance.addRanges(ksm.name, replacingTokenRange);
            Collection<Map.Entry<InetAddress, Collection<Range<Token>>>> fetchPlan_nonbalance = s_nonbalance.toFetch().get(ksm.name);

            List<Double> loads_nonbalance = fetchPlan_nonbalance.stream().map(entry -> {
                return entry.getValue().stream()
                            .map(range -> range.left.size(range.right))
                            .reduce(Double::sum).orElse((double) 0);
            }).collect(Collectors.toList());

            System.setProperty("cassandra.enable_token_balance_streaming", "true");
            RangeStreamer s_balance = new RangeStreamer(tmd, replacingTokens, replacement, "Bootstrap", false, DatabaseDescriptor.getEndpointSnitch(), new StreamStateStore());
            s_balance.addSourceFilter(new RangeStreamer.FailureDetectorSourceFilter(mockFailureDetector));
            s_balance.addSourceFilter(new RangeStreamer.ExcludeLocalNodeFilter());
            s_balance.addRanges(ksm.name, replacingTokenRange);
            Collection<Map.Entry<InetAddress, Collection<Range<Token>>>> fetchPlan_balance = s_balance.toFetch().get(ksm.name);

            List<Double> loads_balance = fetchPlan_balance.stream().map(entry -> {
                return entry.getValue().stream()
                            .map(range -> range.left.size(range.right))
                            .reduce(Double::sum).orElse((double) 0);
            }).collect(Collectors.toList());
            assertTrue(standardDeviation(loads_balance) < standardDeviation(loads_nonbalance));
        }
        finally
        {
            DatabaseDescriptor.setEndpointSnitch(oldSnitch);
        }
    }

    @Test
    public void testSourceTargetComputation() throws UnknownHostException
    {
        final int[] clusterSizes = new int[] { 1, 3, 5, 10, 100};
        for (String keyspaceName : Schema.instance.getNonLocalStrategyKeyspaces())
        {
            int replicationFactor = Keyspace.open(keyspaceName).getReplicationStrategy().getReplicationFactor();
            for (int clusterSize : clusterSizes)
                if (clusterSize >= replicationFactor)
                    testSourceTargetComputation(keyspaceName, clusterSize, replicationFactor);
        }
    }

    private RangeStreamer testSourceTargetComputation(String keyspaceName, int numOldNodes, int replicationFactor) throws UnknownHostException
    {
        StorageService ss = StorageService.instance;
        TokenMetadata tmd = ss.getTokenMetadata();

        generateFakeEndpoints(numOldNodes);
        Token myToken = tmd.partitioner.getRandomToken();
        InetAddress myEndpoint = InetAddress.getByName("127.0.0.1");

        assertEquals(numOldNodes, tmd.sortedTokens().size());
        RangeStreamer s = new RangeStreamer(tmd, null, myEndpoint, "Bootstrap", true, DatabaseDescriptor.getEndpointSnitch(), new StreamStateStore());
        IFailureDetector mockFailureDetector = new IFailureDetector()
        {
            public boolean isAlive(InetAddress ep)
            {
                return true;
            }

            public void interpret(InetAddress ep) { throw new UnsupportedOperationException(); }
            public void report(InetAddress ep) { throw new UnsupportedOperationException(); }
            public void registerFailureDetectionEventListener(IFailureDetectionEventListener listener) { throw new UnsupportedOperationException(); }
            public void unregisterFailureDetectionEventListener(IFailureDetectionEventListener listener) { throw new UnsupportedOperationException(); }
            public void remove(InetAddress ep) { throw new UnsupportedOperationException(); }
            public void forceConviction(InetAddress ep) { throw new UnsupportedOperationException(); }
        };
        s.addSourceFilter(new RangeStreamer.FailureDetectorSourceFilter(mockFailureDetector));
        s.addRanges(keyspaceName, Keyspace.open(keyspaceName).getReplicationStrategy().getPendingAddressRanges(tmd, myToken, myEndpoint));

        Collection<Map.Entry<InetAddress, Collection<Range<Token>>>> toFetch = s.toFetch().get(keyspaceName);

        // Check we get get RF new ranges in total
        Set<Range<Token>> ranges = new HashSet<>();
        for (Map.Entry<InetAddress, Collection<Range<Token>>> e : toFetch)
            ranges.addAll(e.getValue());

        assertEquals(replicationFactor, ranges.size());

        // there isn't any point in testing the size of these collections for any specific size.  When a random partitioner
        // is used, they will vary.
        assert toFetch.iterator().next().getValue().size() > 0;
        assert !toFetch.iterator().next().getKey().equals(myEndpoint);
        return s;
    }

    private double variance(List<Double> a)
    {
        // Compute mean (average of elements)
        Double sum = 0.0;

        for (int i = 0; i < a.size(); i++)
            sum += a.get(i);
        double mean = sum/(double)a.size();

        // Compute sum squared differences with mean.
        double sqDiff = 0;
        for (int i = 0; i < a.size(); i++)
            sqDiff += (a.get(i) - mean) * (a.get(i) - mean);

        return sqDiff/a.size();
    }

    private double standardDeviation(List<Double> a)
    {
        return Math.sqrt(variance(a));
    }

    private void generateFakeEndpoints(int numOldNodes) throws UnknownHostException
    {
        generateFakeEndpoints(StorageService.instance.getTokenMetadata(), numOldNodes, 1);
    }

    private void generateFakeEndpoints(TokenMetadata tmd, int numOldNodes, int numVNodes) throws UnknownHostException
    {
        tmd.clearUnsafe();
        generateFakeEndpoints(tmd, numOldNodes, numVNodes, "0", "0");
    }

    private void generateFakeEndpoints(TokenMetadata tmd, int numOldNodes, int numVNodes, String dc, String rack) throws UnknownHostException
    {
        IPartitioner p = tmd.partitioner;

        for (int i = 1; i <= numOldNodes; i++)
        {
            // leave .1 for myEndpoint
            InetAddress addr = InetAddress.getByName("127." + dc + "." + rack + "." + (i + 1));
            List<Token> tokens = Lists.newArrayListWithCapacity(numVNodes);
            for (int j = 0; j < numVNodes; ++j)
                tokens.add(p.getRandomToken());
            
            tmd.updateNormalTokens(tokens, addr);
        }
    }
    
    @Test
    public void testAllocateTokens() throws UnknownHostException
    {
        int vn = 16;
        String ks = "BootStrapperTestKeyspace3";
        TokenMetadata tm = new TokenMetadata();
        generateFakeEndpoints(tm, 10, vn);
        InetAddress addr = FBUtilities.getBroadcastAddress();
        allocateTokensForNode(vn, ks, tm, addr);
    }

    public void testAllocateTokensNetworkStrategy(int rackCount, int replicas) throws UnknownHostException
    {
        IEndpointSnitch oldSnitch = DatabaseDescriptor.getEndpointSnitch();
        try
        {
            DatabaseDescriptor.setEndpointSnitch(new RackInferringSnitch());
            int vn = 16;
            String ks = "BootStrapperTestNTSKeyspace" + rackCount + replicas;
            String dc = "1";
            SchemaLoader.createKeyspace(ks, KeyspaceParams.nts(dc, replicas, "15", 15), SchemaLoader.standardCFMD(ks, "Standard1"));
            TokenMetadata tm = StorageService.instance.getTokenMetadata();
            tm.clearUnsafe();
            for (int i = 0; i < rackCount; ++i)
                generateFakeEndpoints(tm, 10, vn, dc, Integer.toString(i));
            InetAddress addr = InetAddress.getByName("127." + dc + ".0.99");
            allocateTokensForNode(vn, ks, tm, addr);
            // Note: Not matching replication factor in second datacentre, but this should not affect us.
        } finally {
            DatabaseDescriptor.setEndpointSnitch(oldSnitch);
        }
    }

    @Test
    public void testAllocateTokensNetworkStrategyOneRack() throws UnknownHostException
    {
        testAllocateTokensNetworkStrategy(1, 3);
    }

    @Test(expected = ConfigurationException.class)
    public void testAllocateTokensNetworkStrategyTwoRacks() throws UnknownHostException
    {
        testAllocateTokensNetworkStrategy(2, 3);
    }

    @Test
    public void testAllocateTokensNetworkStrategyThreeRacks() throws UnknownHostException
    {
        testAllocateTokensNetworkStrategy(3, 3);
    }

    @Test
    public void testAllocateTokensNetworkStrategyFiveRacks() throws UnknownHostException
    {
        testAllocateTokensNetworkStrategy(5, 3);
    }

    @Test
    public void testAllocateTokensNetworkStrategyOneRackOneReplica() throws UnknownHostException
    {
        testAllocateTokensNetworkStrategy(1, 1);
    }

    private void allocateTokensForNode(int vn, String ks, TokenMetadata tm, InetAddress addr)
    {
        SummaryStatistics os = TokenAllocation.replicatedOwnershipStats(tm.cloneOnlyTokenMap(), Keyspace.open(ks).getReplicationStrategy(), addr);
        Collection<Token> tokens = BootStrapper.allocateTokens(tm, addr, ks, vn, 0);
        assertEquals(vn, tokens.size());
        tm.updateNormalTokens(tokens, addr);
        SummaryStatistics ns = TokenAllocation.replicatedOwnershipStats(tm.cloneOnlyTokenMap(), Keyspace.open(ks).getReplicationStrategy(), addr);
        verifyImprovement(os, ns);
    }

    private void verifyImprovement(SummaryStatistics os, SummaryStatistics ns)
    {
        if (ns.getStandardDeviation() > os.getStandardDeviation())
        {
            fail(String.format("Token allocation unexpectedly increased standard deviation.\nStats before:\n%s\nStats after:\n%s", os, ns));
        }
    }

    
    @Test
    public void testAllocateTokensMultipleKeyspaces() throws UnknownHostException
    {
        // TODO: This scenario isn't supported very well. Investigate a multi-keyspace version of the algorithm.
        int vn = 16;
        String ks3 = "BootStrapperTestKeyspace4"; // RF = 3
        String ks2 = "BootStrapperTestKeyspace5"; // RF = 2

        TokenMetadata tm = new TokenMetadata();
        generateFakeEndpoints(tm, 10, vn);
        
        InetAddress dcaddr = FBUtilities.getBroadcastAddress();
        SummaryStatistics os3 = TokenAllocation.replicatedOwnershipStats(tm, Keyspace.open(ks3).getReplicationStrategy(), dcaddr);
        SummaryStatistics os2 = TokenAllocation.replicatedOwnershipStats(tm, Keyspace.open(ks2).getReplicationStrategy(), dcaddr);
        String cks = ks3;
        String nks = ks2;
        for (int i=11; i<=20; ++i)
        {
            allocateTokensForNode(vn, cks, tm, InetAddress.getByName("127.0.0." + (i + 1)));
            String t = cks; cks = nks; nks = t;
        }
        
        SummaryStatistics ns3 = TokenAllocation.replicatedOwnershipStats(tm, Keyspace.open(ks3).getReplicationStrategy(), dcaddr);
        SummaryStatistics ns2 = TokenAllocation.replicatedOwnershipStats(tm, Keyspace.open(ks2).getReplicationStrategy(), dcaddr);
        verifyImprovement(os3, ns3);
        verifyImprovement(os2, ns2);
    }
}
