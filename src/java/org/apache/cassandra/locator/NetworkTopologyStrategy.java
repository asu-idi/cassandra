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

import java.util.*;
import java.util.Map.Entry;

import org.apache.cassandra.locator.ReplicaCollection.Builder.Conflict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.dht.Datacenters;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.locator.TokenMetadata.Topology;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.Pair;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * <p>
 * This Replication Strategy takes a property file that gives the intended
 * replication factor in each datacenter.  The sum total of the datacenter
 * replication factor values should be equal to the keyspace replication
 * factor.
 * </p>
 * <p>
 * So for example, if the keyspace replication factor is 6, the
 * datacenter replication factors could be 3, 2, and 1 - so 3 replicas in
 * one datacenter, 2 in another, and 1 in another - totalling 6.
 * </p>
 * This class also caches the Endpoints and invalidates the cache if there is a
 * change in the number of tokens.
 */
public class NetworkTopologyStrategy extends AbstractReplicationStrategy
{
    private final Map<String, ReplicationFactor> datacenters;
    private final ReplicationFactor aggregateRf;
    private static final Logger logger = LoggerFactory.getLogger(NetworkTopologyStrategy.class);
    private static final String REPLICATION_FACTOR = "replication_factor";

    public NetworkTopologyStrategy(String keyspaceName, TokenMetadata tokenMetadata, IEndpointSnitch snitch, Map<String, String> configOptions) throws ConfigurationException
    {
        super(keyspaceName, tokenMetadata, snitch, configOptions);

        int replicas = 0;
        int trans = 0;
        Map<String, ReplicationFactor> newDatacenters = new HashMap<>();
        if (configOptions != null)
        {
            for (Entry<String, String> entry : configOptions.entrySet())
            {
                String dc = entry.getKey();
                // prepareOptions should have transformed any "replication_factor" options by now
                if (dc.equalsIgnoreCase(REPLICATION_FACTOR))
                    throw new ConfigurationException(REPLICATION_FACTOR + " should not appear as an option at construction time for NetworkTopologyStrategy");
                ReplicationFactor rf = ReplicationFactor.fromString(entry.getValue());
                replicas += rf.allReplicas;
                trans += rf.transientReplicas();
                newDatacenters.put(dc, rf);
            }
        }

        datacenters = Collections.unmodifiableMap(newDatacenters);
        aggregateRf = ReplicationFactor.withTransient(replicas, trans);
        logger.info("Configured datacenter replicas are {}", FBUtilities.toString(datacenters));
    }

    /**
     * Endpoint adder applying the replication rules for a given DC.
     */
    private static final class DatacenterEndpoints
    {
        /** List accepted endpoints get pushed into. */
        EndpointsForRange.Builder replicas;

        /**
         * Racks encountered so far. Replicas are put into separate racks while possible.
         * For efficiency the set is shared between the instances, using the location pair (dc, rack) to make sure
         * clashing names aren't a problem.
         */
        Set<Pair<String, String>> racks;

        /** Number of replicas left to fill from this DC. */
        int rfLeft;
        int acceptableRackRepeats;
        int transients;

        DatacenterEndpoints(ReplicationFactor rf, int rackCount, int nodeCount, EndpointsForRange.Builder replicas, Set<Pair<String, String>> racks)
        {
            this.replicas = replicas;
            this.racks = racks;
            // If there aren't enough nodes in this DC to fill the RF, the number of nodes is the effective RF.
            this.rfLeft = Math.min(rf.allReplicas, nodeCount);
            // If there aren't enough racks in this DC to fill the RF, we'll still use at least one node from each rack,
            // and the difference is to be filled by the first encountered nodes.
            acceptableRackRepeats = rf.allReplicas - rackCount;

            // if we have fewer replicas than rf calls for, reduce transients accordingly
            int reduceTransients = rf.allReplicas - this.rfLeft;
            transients = Math.max(rf.transientReplicas() - reduceTransients, 0);
            ReplicationFactor.validate(rfLeft, transients);
        }

        /**
         * Attempts to add an endpoint to the replicas for this datacenter, adding to the replicas set if successful.
         * Returns true if the endpoint was added, and this datacenter does not require further replicas.
         */
        boolean addEndpointAndCheckIfDone(InetAddressAndPort ep, Pair<String,String> location, Range<Token> replicatedRange)
        {
            if (done())
                return false;

            if (replicas.endpoints().contains(ep))
                // Cannot repeat a node.
                return false;

            Replica replica = new Replica(ep, replicatedRange, rfLeft > transients);

            if (racks.add(location))
            {
                // New rack.
                --rfLeft;
                replicas.add(replica, Conflict.NONE);
                return done();
            }
            if (acceptableRackRepeats <= 0)
                // There must be rfLeft distinct racks left, do not add any more rack repeats.
                return false;

            replicas.add(replica, Conflict.NONE);
            // Added a node that is from an already met rack to match RF when there aren't enough racks.
            --acceptableRackRepeats;
            --rfLeft;
            return done();
        }

        boolean done()
        {
            assert rfLeft >= 0;
            return rfLeft == 0;
        }
    }

    /**
     * Helper Class for holding token ranges for NetworkTopologyStrategy,
     * that are splitted by tokens from other DC.
     */
    @VisibleForTesting
    public static class DCAwareTokens
    {
        private List<Token> tokens;

        public DCAwareTokens()
        {
            tokens = new ArrayList<>();
        }

        public List<Token> getTokens()
        {
            return tokens;
        }

        public void add(Token token)
        {
            tokens.add(token);
        }

        public Token getStartToken()
        {
            return tokens.get(0);
        }

        public List<Range<Token>> getTokenRanges()
        {
            ArrayList<Range<Token>> res = new ArrayList<>();

            for (int i = 1; i < tokens.size(); i++)
            {
                res.add(new Range<>(tokens.get(i), tokens.get(i - 1)));
            }

            return res;
        }

        public List<Replica> getReplicas(InetAddressAndPort endpoint)
        {
            ArrayList<Replica> res = new ArrayList<>();

            for (int i = 1; i < tokens.size(); i++)
            {
                res.add(new Replica(endpoint, tokens.get(i), tokens.get(i - 1), true));
            }

            return res;
        }
    }

    /**
     * Helper function to iterator through the current datacenter DCAwareTokens.
     * For NetworkTopologyStrategy, the replication is based on each DC, so each DC is kind of having it's own
     * token-ring. For example:
     *  DC1: 1, 6
     *  DC2: 3, 8
     * Token range 1-3, 3-6 are having the same replication location in DC1, even though it's splitted by token 3 (a
     * token from different DC). DCAwareTokens basically groups token ranges 1-3, 3-6 together if we know we're only
     * calculating for DC1.
     * @param metadata
     * @param start
     * @param end
     * @param datacenter
     * @return Reverse Iterator for DCAwareTokens
     */
    @VisibleForTesting
    public Iterator<DCAwareTokens> dcRingReverseIterator(final TokenMetadata metadata, Token start, final Token end, final String datacenter)
    {
        ArrayList<Token> ring = metadata.sortedTokens();

        final int startIndex = ring.isEmpty() ? -1 : Collections.binarySearch(ring, start);

        return new AbstractIterator<DCAwareTokens>()
        {
            int idx = startIndex;

            protected DCAwareTokens computeNext()
            {
                if (idx < 0)
                    return endOfData();

                DCAwareTokens res = new DCAwareTokens();
                Token token = ring.get(idx);
                InetAddressAndPort firstEP = metadata.getEndpoint(token);
                InetAddressAndPort ep;
                String dc;

                // Group tokens together if they're not from the requested datacenter or from the same endpoint
                do
                {
                    res.add(token);
                    idx--;
                    if (idx < 0)
                        idx = ring.size() - 1;
                    token = ring.get(idx);
                    ep = metadata.getEndpoint(token);
                    dc = snitch.getDatacenter(ep);
                    // check if it reachs the end
                    if (idx == startIndex || token.equals(end))
                    {
                        idx = -1;
                        break;
                    }
                } while (ep.equals(firstEP) || !datacenter.equals(dc));

                res.add(token);
                return res;
            }
        };
    }

    private List<Range<Token>> getAllTokenRanges(Collection<Token> ring)
    {
        List<Range<Token>> res = new ArrayList<>();

        Iterator<Token> tokenIterator = ring.iterator();

        if (!tokenIterator.hasNext()) return res;

        Token first = tokenIterator.next();
        Token start = first;
        while (tokenIterator.hasNext())
        {
            Token end = tokenIterator.next();
            res.add(new Range<>(start, end));
            start = end;
        }
        res.add(new Range<>(start, first));
        return res;
    }

    @VisibleForTesting
    public RangesAtEndpoint getAddressReplicasForTestOnly(TokenMetadata metadata, InetAddressAndPort endpoint)
    {
        return super.getAddressReplicas(metadata, endpoint);
    }

    /**
     * Get all token ranges that are stored on one endpoint. This is baisically a reverse version of
     * calculateNaturalEndpoints().
     * It's much faster than the default function in AbstractReplicationStrategy, which caculates all tokens' natural
     * replicas and returns the ranges for one endpoint.
     * @param metadata
     * @param endpoint
     * @return
     */
    @Override
    public RangesAtEndpoint getAddressReplicas(TokenMetadata metadata, InetAddressAndPort endpoint)
    {
        // Fall back to the default function for transient replicas
        if (aggregateRf.transientReplicas() > 0)
            return super.getAddressReplicas(metadata, endpoint);

        RangesAtEndpoint.Builder builder = RangesAtEndpoint.builder(endpoint);

        if (metadata.sortedTokens().size() == 0)
            return builder.build();

        final String epDC = snitch.getDatacenter(endpoint);
        final String epRack = snitch.getRack(endpoint);
        if (!datacenters.containsKey(epDC))
            return builder.build();

        final int RF = getReplicationFactor(epDC).allReplicas;
        if (RF == 0)
            return builder.build();

        Topology topology = metadata.getTopology();
        // all racks in a DC so we can check when we have exhausted all racks in a DC
        Multimap<String, InetAddressAndPort> allRacks = topology.getDatacenterRacks().get(epDC);
        final int RACKNUM = allRacks.keySet().size();

        if (RF <= RACKNUM) // each rack have maximum 1 replica
        {
            for (Range<Token> range : getAllTokenRanges(metadata.getTokens(endpoint)))
            {
                Set<String> seenRacks = new HashSet<>();
                Iterator<DCAwareTokens> dcTokenIterator = dcRingReverseIterator(metadata, range.right, range.left, epDC);

                assert(dcTokenIterator.hasNext());

                DCAwareTokens dcTokens = dcTokenIterator.next();
                builder.addAll(dcTokens.getReplicas(endpoint));
                seenRacks.add(epRack);

                while (dcTokenIterator.hasNext() &&
                       (RF >= seenRacks.size())) // each distinct rack has one replica, stop if we found all replicas
                {
                    dcTokens = dcTokenIterator.next();
                    Token t = dcTokens.getStartToken();
                    InetAddressAndPort ep = metadata.getEndpoint(t);
                    String rack = snitch.getRack(ep);
                    if (epRack.equals(rack))  // If we see the same rack as the endpoint's one, any token before that won't be replicated to the endpoint.
                        break;

                    if (RF > seenRacks.size() || seenRacks.contains(rack))
                        builder.addAll(dcTokens.getReplicas(endpoint));
                    seenRacks.add(rack);
                }
            }
        }
        else
        {
            // OVERFLOW number of replicas will be located on the "skipped" nodes which are right after the current endpoint
            final int OVERFLOW = RF - RACKNUM;

            for (Range<Token> range : getAllTokenRanges(metadata.getTokens(endpoint)))
            {
                Set<String> seenRacks = new HashSet<>();  // distinct rack we have seen
                Set<InetAddressAndPort> seenEPs = new HashSet<>(); // distinct endpoint we have seen, each one can only have 1 replica

                Iterator<DCAwareTokens> dcTokenIterator = dcRingReverseIterator(metadata, range.right, range.left, epDC);

                assert(dcTokenIterator.hasNext());

                DCAwareTokens dcTokens = dcTokenIterator.next();
                builder.addAll(dcTokens.getReplicas(endpoint));
                seenRacks.add(epRack);
                seenEPs.add(endpoint);

                boolean isFirstEPRack = true;
                while (dcTokenIterator.hasNext())
                {
                    dcTokens = dcTokenIterator.next();
                    Token t = dcTokens.getStartToken();
                    InetAddressAndPort ep = metadata.getEndpoint(t);
                    String rack = snitch.getRack(ep);

                    if (epRack.equals(rack))
                        isFirstEPRack = false;

                    seenEPs.add(ep);
                    seenRacks.add(rack);
                    int maxUniqueEndpoints = OVERFLOW + seenRacks.size();

                    // stop searching if:
                    //  1) it's no longer the first endpoint in the rack, and
                    //  2) max unique endpoint won't cover the endpoint node
                    if (!isFirstEPRack && maxUniqueEndpoints < seenEPs.size())
                        break;

                    // add it to the result if:
                    //  1) max unique endpoint covers the endpoint, or
                    //  2) we seen this endpoint before, adding it won't increase seenEPs.size()
                    if (maxUniqueEndpoints > seenEPs.size() || seenEPs.contains(ep))
                        builder.addAll(dcTokens.getReplicas(endpoint));
                }
            }
        }

        return builder.build();
    }

    /**
     * calculate endpoints in one pass through the tokens by tracking our progress in each DC.
     */
    public EndpointsForRange calculateNaturalReplicas(Token searchToken, TokenMetadata tokenMetadata)
    {
        // we want to preserve insertion order so that the first added endpoint becomes primary
        ArrayList<Token> sortedTokens = tokenMetadata.sortedTokens();
        Token replicaEnd = TokenMetadata.firstToken(sortedTokens, searchToken);
        Token replicaStart = tokenMetadata.getPredecessor(replicaEnd);
        Range<Token> replicatedRange = new Range<>(replicaStart, replicaEnd);

        EndpointsForRange.Builder builder = new EndpointsForRange.Builder(replicatedRange);
        Set<Pair<String, String>> seenRacks = new HashSet<>();

        Topology topology = tokenMetadata.getTopology();
        // all endpoints in each DC, so we can check when we have exhausted all the members of a DC
        Multimap<String, InetAddressAndPort> allEndpoints = topology.getDatacenterEndpoints();
        // all racks in a DC so we can check when we have exhausted all racks in a DC
        Map<String, ImmutableMultimap<String, InetAddressAndPort>> racks = topology.getDatacenterRacks();
        assert !allEndpoints.isEmpty() && !racks.isEmpty() : "not aware of any cluster members";

        int dcsToFill = 0;
        Map<String, DatacenterEndpoints> dcs = new HashMap<>(datacenters.size() * 2);

        // Create a DatacenterEndpoints object for each non-empty DC.
        for (Map.Entry<String, ReplicationFactor> en : datacenters.entrySet())
        {
            String dc = en.getKey();
            ReplicationFactor rf = en.getValue();
            int nodeCount = sizeOrZero(allEndpoints.get(dc));

            if (rf.allReplicas <= 0 || nodeCount <= 0)
                continue;

            DatacenterEndpoints dcEndpoints = new DatacenterEndpoints(rf, sizeOrZero(racks.get(dc)), nodeCount, builder, seenRacks);
            dcs.put(dc, dcEndpoints);
            ++dcsToFill;
        }

        Iterator<Token> tokenIter = TokenMetadata.ringIterator(sortedTokens, searchToken, false);
        while (dcsToFill > 0 && tokenIter.hasNext())
        {
            Token next = tokenIter.next();
            InetAddressAndPort ep = tokenMetadata.getEndpoint(next);
            Pair<String, String> location = topology.getLocation(ep);
            DatacenterEndpoints dcEndpoints = dcs.get(location.left);
            if (dcEndpoints != null && dcEndpoints.addEndpointAndCheckIfDone(ep, location, replicatedRange))
                --dcsToFill;
        }
        return builder.build();
    }

    private int sizeOrZero(Multimap<?, ?> collection)
    {
        return collection != null ? collection.asMap().size() : 0;
    }

    private int sizeOrZero(Collection<?> collection)
    {
        return collection != null ? collection.size() : 0;
    }

    public ReplicationFactor getReplicationFactor()
    {
        return aggregateRf;
    }

    public ReplicationFactor getReplicationFactor(String dc)
    {
        ReplicationFactor replicas = datacenters.get(dc);
        return replicas == null ? ReplicationFactor.ZERO : replicas;
    }

    public Set<String> getDatacenters()
    {
        return datacenters.keySet();
    }

    public Collection<String> recognizedOptions()
    {
        // only valid options are valid DC names.
        return Datacenters.getValidDatacenters();
    }

    /**
     * Support datacenter auto-expansion for CASSANDRA-14303. This hook allows us to safely auto-expand
     * the "replication_factor" options out into the known datacenters. It is called via reflection from
     * {@link AbstractReplicationStrategy#prepareReplicationStrategyOptions(Class, Map, Map)}.
     *
     * @param options The proposed strategy options that will be potentially mutated
     * @param previousOptions Any previous strategy options in the case of an ALTER statement
     */
    protected static void prepareOptions(Map<String, String> options, Map<String, String> previousOptions)
    {
        String replication = options.remove(REPLICATION_FACTOR);

        if (replication == null && options.size() == 0)
        {
            // Support direct alters from SimpleStrategy to NTS
            replication = previousOptions.get(REPLICATION_FACTOR);
        }
        else if (replication != null)
        {
            // When datacenter auto-expansion occurs in e.g. an ALTER statement (meaning that the previousOptions
            // map is not empty) we choose not to alter existing datacenter replication levels for safety.
            previousOptions.entrySet().stream()
                           .filter(e -> !e.getKey().equals(REPLICATION_FACTOR)) // SimpleStrategy conversions
                           .forEach(e -> options.putIfAbsent(e.getKey(), e.getValue()));
        }

        if (replication != null) {
            ReplicationFactor defaultReplicas = ReplicationFactor.fromString(replication);
            Datacenters.getValidDatacenters()
                       .forEach(dc -> options.putIfAbsent(dc, defaultReplicas.toParseableString()));
        }

        options.values().removeAll(Collections.singleton("0"));
    }

    protected void validateExpectedOptions() throws ConfigurationException
    {
        // Do not accept query with no data centers specified.
        if (this.configOptions.isEmpty())
        {
            throw new ConfigurationException("Configuration for at least one datacenter must be present");
        }

        // Validate the data center names
        super.validateExpectedOptions();
    }

    public void validateOptions() throws ConfigurationException
    {
        for (Entry<String, String> e : this.configOptions.entrySet())
        {
            // prepareOptions should have transformed any "replication_factor" by now
            if (e.getKey().equalsIgnoreCase(REPLICATION_FACTOR))
                throw new ConfigurationException(REPLICATION_FACTOR + " should not appear as an option to NetworkTopologyStrategy");
            validateReplicationFactor(e.getValue());
        }
    }

    @Override
    public boolean hasSameSettings(AbstractReplicationStrategy other)
    {
        return super.hasSameSettings(other) && ((NetworkTopologyStrategy) other).datacenters.equals(datacenters);
    }
}
