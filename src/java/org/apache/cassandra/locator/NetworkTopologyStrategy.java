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
import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.dht.Range;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.locator.TokenMetadata.Topology;
import org.apache.cassandra.utils.FBUtilities;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.AbstractIterator;
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
    private final IEndpointSnitch snitch;
    private final Map<String, Integer> datacenters;
    private static final Logger logger = LoggerFactory.getLogger(NetworkTopologyStrategy.class);

    public NetworkTopologyStrategy(String keyspaceName, TokenMetadata tokenMetadata, IEndpointSnitch snitch, Map<String, String> configOptions) throws ConfigurationException
    {
        super(keyspaceName, tokenMetadata, snitch, configOptions);
        this.snitch = snitch;

        Map<String, Integer> newDatacenters = new HashMap<String, Integer>();
        if (configOptions != null)
        {
            for (Entry<String, String> entry : configOptions.entrySet())
            {
                String dc = entry.getKey();
                if (dc.equalsIgnoreCase("replication_factor"))
                    throw new ConfigurationException("replication_factor is an option for SimpleStrategy, not NetworkTopologyStrategy");
                Integer replicas = Integer.valueOf(entry.getValue());
                newDatacenters.put(dc, replicas);
            }
        }

        datacenters = Collections.unmodifiableMap(newDatacenters);
        logger.trace("Configured datacenter replicas are {}", FBUtilities.toString(datacenters));
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
            ArrayList<Range<Token>> ret = new ArrayList<>();

            for (int i = 1; i < tokens.size(); i++)
            {
                ret.add(new Range<>(tokens.get(i), tokens.get(i - 1)));
            }

            return ret;
        }
    }

    /**
     * Helper function to iterator through the current datacenter DCAwareTokens.
     * For NetworkTopologyStrategy, the replication is based on each DC, so
     * each DC is kind of having it's own token ring. For example:
     *  DC1: 1, 6
     *  DC2: 3, 8
     * Token range 1-3, 3-6 are having the same replication location in DC1,
     * even though it's splitted by token 3. DCAwareTokens basically groups
     * token ranges 1-3, 3-6 together if we know we're only calculating for DC1.
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
                InetAddress firstEP = metadata.getEndpoint(token);
                InetAddress ep;
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

    private List<Range<Token>> getTokenRanges(Collection<Token> ring)
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


    /**
     * Get all token ranges that are stored on one endpoint. This is baisically a reverse version
     * of calculateNaturalEndpoints(). It's much faster than caculating all token natural endpoints
     * and returns only one endpoint. In worst case, it only iterate through the token ring once.
     * @param metadata
     * @param endpoint
     * @return
     */
    @Override
    public Collection<Range<Token>> getAddressRanges(TokenMetadata metadata, InetAddress endpoint)
    {
        Collection<Range<Token>> res = new HashSet<>();

        final String epDC = snitch.getDatacenter(endpoint);
        final String epRack = snitch.getRack(endpoint);
        if (!datacenters.containsKey(epDC))
            return res;

        final int RF = getReplicationFactor(epDC);
        if (RF == 0)
            return res;

        Topology topology = metadata.getTopology();
        // all racks in a DC so we can check when we have exhausted all racks in a DC
        Multimap<String, InetAddress> allRacks = topology.getDatacenterRacks().get(epDC);
        final int RACKNUM = allRacks.keySet().size();

        // each rack can have maximum 1 replica
        if (RF <= RACKNUM)
        {
            for (Range<Token> range : getTokenRanges(metadata.getTokens(endpoint)))
            {
                Set<String> seenRacks = new HashSet<>();
                Iterator<DCAwareTokens> dcTokenIterator = dcRingReverseIterator(metadata, range.right, range.left, epDC);

                assert(dcTokenIterator.hasNext());

                DCAwareTokens dcTokens = dcTokenIterator.next();
                res.addAll(dcTokens.getTokenRanges()); // Always include the primary range
                seenRacks.add(epRack);

                while (dcTokenIterator.hasNext() &&
                       (RF >= seenRacks.size())) // each distinct rack has one replica, stop if we found all replicas
                {
                    dcTokens = dcTokenIterator.next();
                    Token t = dcTokens.getStartToken();
                    InetAddress ep = metadata.getEndpoint(t);
                    String rack = snitch.getRack(ep);
                    if (epRack.equals(rack))  // If we see the same rack as the endpoint's one, any token before that won't be replicated to the endpoint.
                        break;

                    if (RF > seenRacks.size() || seenRacks.contains(rack))
                    {
                        res.addAll(dcTokens.getTokenRanges());
                    }
                    seenRacks.add(rack);
                }
            }
        }
        else // each rack has at least 1 replica
        {
            // OVERFLOW number of replicas will be located on the "skipped" nodes which are right after the current endpoint
            final int OVERFLOW = RF - RACKNUM;

            for (Range<Token> range : getTokenRanges(metadata.getTokens(endpoint)))
            {
                Set<String> seenRacks = new HashSet<>();  // distinct rack we have seen
                Set<InetAddress> seenEPs = new HashSet<>(); // distinct endpoint we have seen, each one can only have 1 replica

                Iterator<DCAwareTokens> dcTokenIterator = dcRingReverseIterator(metadata, range.right, range.left, epDC);

                assert(dcTokenIterator.hasNext());

                DCAwareTokens dcTokens = dcTokenIterator.next();
                res.addAll(dcTokens.getTokenRanges()); // Always include the primary range
                seenRacks.add(epRack);
                seenEPs.add(endpoint);

                boolean isFirstEPRack = true;
                while (dcTokenIterator.hasNext())
                {
                    dcTokens = dcTokenIterator.next();
                    Token t = dcTokens.getStartToken();
                    InetAddress ep = metadata.getEndpoint(t);
                    String rack = snitch.getRack(ep);

                    if (epRack.equals(rack))
                    {
                        isFirstEPRack = false;
                    }

                    seenEPs.add(ep);
                    seenRacks.add(rack);
                    int maxUniqueEndpoints = OVERFLOW + seenRacks.size();

                    // stop searching if:
                    //  1) it's no longer the first endpoint in the rack, and
                    //  2) max unique endpoint won't cover the endpoint node
                    if (!isFirstEPRack && maxUniqueEndpoints < seenEPs.size())
                    {
                        break;
                    }

                    // add it to the result if:
                    //  1) max unique endpoint covers the endpoint, or
                    //  2) we seen this endpoint before, adding it won't increase seenEPs.size()
                    if (maxUniqueEndpoints > seenEPs.size() || seenEPs.contains(ep))
                    {
                        res.addAll(dcTokens.getTokenRanges());
                    }
                }
            }
        }

        return res;
    }

    /**
     * calculate endpoints in one pass through the tokens by tracking our progress in each DC, rack etc.
     */
    @SuppressWarnings("serial")
    public List<InetAddress> calculateNaturalEndpoints(Token searchToken, TokenMetadata tokenMetadata)
    {
        // we want to preserve insertion order so that the first added endpoint becomes primary
        Set<InetAddress> replicas = new LinkedHashSet<>();
        // replicas we have found in each DC
        Map<String, Set<InetAddress>> dcReplicas = new HashMap<>(datacenters.size());
        for (Map.Entry<String, Integer> dc : datacenters.entrySet())
            dcReplicas.put(dc.getKey(), new HashSet<InetAddress>(dc.getValue()));

        Topology topology = tokenMetadata.getTopology();
        // all endpoints in each DC, so we can check when we have exhausted all the members of a DC
        Multimap<String, InetAddress> allEndpoints = topology.getDatacenterEndpoints();
        // all racks in a DC so we can check when we have exhausted all racks in a DC
        Map<String, Multimap<String, InetAddress>> racks = topology.getDatacenterRacks();
        assert !allEndpoints.isEmpty() && !racks.isEmpty() : "not aware of any cluster members";

        // tracks the racks we have already placed replicas in
        Map<String, Set<String>> seenRacks = new HashMap<>(datacenters.size());
        for (Map.Entry<String, Integer> dc : datacenters.entrySet())
            seenRacks.put(dc.getKey(), new HashSet<String>());

        // tracks the endpoints that we skipped over while looking for unique racks
        // when we relax the rack uniqueness we can append this to the current result so we don't have to wind back the iterator
        Map<String, Set<InetAddress>> skippedDcEndpoints = new HashMap<>(datacenters.size());
        for (Map.Entry<String, Integer> dc : datacenters.entrySet())
            skippedDcEndpoints.put(dc.getKey(), new LinkedHashSet<InetAddress>());

        Iterator<Token> tokenIter = TokenMetadata.ringIterator(tokenMetadata.sortedTokens(), searchToken, false);
        while (tokenIter.hasNext() && !hasSufficientReplicas(dcReplicas, allEndpoints))
        {
            Token next = tokenIter.next();
            InetAddress ep = tokenMetadata.getEndpoint(next);
            String dc = snitch.getDatacenter(ep);
            // have we already found all replicas for this dc?
            if (!datacenters.containsKey(dc) || hasSufficientReplicas(dc, dcReplicas, allEndpoints))
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
                        Iterator<InetAddress> skippedIt = skippedDcEndpoints.get(dc).iterator();
                        while (skippedIt.hasNext() && !hasSufficientReplicas(dc, dcReplicas, allEndpoints))
                        {
                            InetAddress nextSkipped = skippedIt.next();
                            dcReplicas.get(dc).add(nextSkipped);
                            replicas.add(nextSkipped);
                        }
                    }
                }
            }
        }

        return new ArrayList<InetAddress>(replicas);
    }

    private boolean hasSufficientReplicas(String dc, Map<String, Set<InetAddress>> dcReplicas, Multimap<String, InetAddress> allEndpoints)
    {
        return dcReplicas.get(dc).size() >= Math.min(allEndpoints.get(dc).size(), getReplicationFactor(dc));
    }

    private boolean hasSufficientReplicas(Map<String, Set<InetAddress>> dcReplicas, Multimap<String, InetAddress> allEndpoints)
    {
        for (String dc : datacenters.keySet())
            if (!hasSufficientReplicas(dc, dcReplicas, allEndpoints))
                return false;
        return true;
    }

    public int getReplicationFactor()
    {
        int total = 0;
        for (int repFactor : datacenters.values())
            total += repFactor;
        return total;
    }

    public int getReplicationFactor(String dc)
    {
        Integer replicas = datacenters.get(dc);
        return replicas == null ? 0 : replicas;
    }

    public Set<String> getDatacenters()
    {
        return datacenters.keySet();
    }

    public void validateOptions() throws ConfigurationException
    {
        for (Entry<String, String> e : this.configOptions.entrySet())
        {
            if (e.getKey().equalsIgnoreCase("replication_factor"))
                throw new ConfigurationException("replication_factor is an option for SimpleStrategy, not NetworkTopologyStrategy");
            validateReplicationFactor(e.getValue());
        }
    }

    public Collection<String> recognizedOptions()
    {
        // We explicitely allow all options
        return null;
    }

    @Override
    public boolean hasSameSettings(AbstractReplicationStrategy other)
    {
        return super.hasSameSettings(other) && ((NetworkTopologyStrategy) other).datacenters.equals(datacenters);
    }
}
