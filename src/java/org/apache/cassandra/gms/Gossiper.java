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

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Uninterruptibles;

import com.codahale.metrics.RatioGauge;
import org.apache.cassandra.concurrent.ScheduledExecutors;
import org.apache.cassandra.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.concurrent.DebuggableScheduledThreadPoolExecutor;
import org.apache.cassandra.concurrent.JMXEnabledThreadPoolExecutor;
import org.apache.cassandra.concurrent.Stage;
import org.apache.cassandra.concurrent.StageManager;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.net.IAsyncCallback;
import org.apache.cassandra.net.MessageIn;
import org.apache.cassandra.net.MessageOut;
import org.apache.cassandra.net.MessagingService;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.utils.CassandraVersion;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.JVMStabilityInspector;

/**
 * This module is responsible for Gossiping information for the local endpoint. This abstraction
 * maintains the list of live and dead endpoints. Periodically i.e. every 1 second this module
 * chooses a random node and initiates a round of Gossip with it. A round of Gossip involves 3
 * rounds of messaging. For instance if node A wants to initiate a round of Gossip with node B
 * it starts off by sending node B a GossipDigestSynMessage. Node B on receipt of this message
 * sends node A a GossipDigestAckMessage. On receipt of this message node A sends node B a
 * GossipDigestAck2Message which completes a round of Gossip. This module as and when it hears one
 * of the three above mentioned messages updates the Failure Detector with the liveness information.
 * Upon hearing a GossipShutdownMessage, this module will instantly mark the remote node as down in
 * the Failure Detector.
 */

public class Gossiper implements IFailureDetectionEventListener, GossiperMBean
{
    public static final String MBEAN_NAME = "org.apache.cassandra.net:type=Gossiper";

    private static final DebuggableScheduledThreadPoolExecutor executor = new DebuggableScheduledThreadPoolExecutor("GossipTasks");

    static final ApplicationState[] STATES = ApplicationState.values();
    static final List<String> DEAD_STATES = Arrays.asList(VersionedValue.REMOVING_TOKEN, VersionedValue.REMOVED_TOKEN,
                                                          VersionedValue.STATUS_LEFT, VersionedValue.HIBERNATE);
    static ArrayList<String> SILENT_SHUTDOWN_STATES = new ArrayList<>();
    static {
        SILENT_SHUTDOWN_STATES.addAll(DEAD_STATES);
        SILENT_SHUTDOWN_STATES.add(VersionedValue.STATUS_BOOTSTRAPPING);
        SILENT_SHUTDOWN_STATES.add(VersionedValue.STATUS_BOOTSTRAPPING_REPLACE);
    }

    private volatile ScheduledFuture<?> scheduledGossipTask;
    private static final ReentrantLock taskLock = new ReentrantLock();
    private static final ReentrantLock unreachableQuarantineLock = new ReentrantLock();
    public final static int intervalInMillis = 1000;
    public final static int QUARANTINE_DELAY = StorageService.RING_DELAY * 2;
    private static final Logger logger = LoggerFactory.getLogger(Gossiper.class);
    public static final Gossiper instance = new Gossiper();

    // Timestamp to prevent processing any in-flight messages for we've not send any SYN yet, see CASSANDRA-12653.
    volatile long firstSynSendAt = 0L;

    public static final long aVeryLongTime = 259200 * 1000; // 3 days

    // Maximimum difference between generation value and local time we are willing to accept about a peer
    static final int MAX_GENERATION_DIFFERENCE = 86400 * 365;
    private long fatClientTimeout;
    private final Random random = new Random();
    private final Comparator<InetAddress> inetcomparator = new Comparator<InetAddress>()
    {
        public int compare(InetAddress addr1, InetAddress addr2)
        {
            return addr1.getHostAddress().compareTo(addr2.getHostAddress());
        }
    };

    /* subscribers for interest in EndpointState change */
    private final List<IEndpointStateChangeSubscriber> subscribers = new CopyOnWriteArrayList<IEndpointStateChangeSubscriber>();

    /* live member set */
    private final Set<InetAddress> liveEndpoints = new ConcurrentSkipListSet<InetAddress>(inetcomparator);

    /* unreachable member set */
    private final Map<InetAddress, Long> unreachableEndpoints = new ConcurrentHashMap<InetAddress, Long>();

    /* initial seeds for joining the cluster */
    private final Set<InetAddress> seeds = new ConcurrentSkipListSet<InetAddress>(inetcomparator);

    /* map where key is the endpoint and value is the state associated with the endpoint */
    final ConcurrentMap<InetAddress, EndpointState> endpointStateMap = new ConcurrentHashMap<InetAddress, EndpointState>();

    /* map where key is endpoint and value is timestamp when this endpoint was removed from
     * gossip. We will ignore any gossip regarding these endpoints for QUARANTINE_DELAY time
     * after removal to prevent nodes from falsely reincarnating during the time when removal
     * gossip gets propagated to all nodes */
    private final Map<InetAddress, Long> justRemovedEndpoints = new ConcurrentHashMap<InetAddress, Long>();

    private final Map<InetAddress, Long> expireTimeEndpointMap = new ConcurrentHashMap<InetAddress, Long>();

    private volatile boolean inShadowRound = false;
    // endpoint states as gathered during shadow round
    private final Map<InetAddress, EndpointState> endpointShadowStateMap = new ConcurrentHashMap<>();

    private volatile long lastProcessedMessageAt = System.currentTimeMillis();

    private volatile UnreachableQuarantineState.State unreachableQuarantineState = UnreachableQuarantineState.initialState();

    private volatile static boolean receivedFirstGossipMessage = false;
    private volatile static boolean processedFirstGossipMessage = false;

    private class GossipTask implements Runnable
    {
        public void run()
        {
            try
            {
                //wait on messaging service to start listening
                MessagingService.instance().waitUntilListening();

                taskLock.lock();

                /* Update the local heartbeat counter. */
                endpointStateMap.get(FBUtilities.getBroadcastAddress()).getHeartBeatState().updateHeartBeat();
                if (logger.isTraceEnabled())
                    logger.trace("My heartbeat is now {}", endpointStateMap.get(FBUtilities.getBroadcastAddress()).getHeartBeatState().getHeartBeatVersion());
                final List<GossipDigest> gDigests = new ArrayList<GossipDigest>();
                Gossiper.instance.makeRandomGossipDigest(gDigests);

                if (gDigests.size() > 0)
                {
                    GossipDigestSyn digestSynMessage = new GossipDigestSyn(DatabaseDescriptor.getClusterName(),
                                                                           DatabaseDescriptor.getPartitionerName(),
                                                                           gDigests);
                    MessageOut<GossipDigestSyn> message = new MessageOut<GossipDigestSyn>(MessagingService.Verb.GOSSIP_DIGEST_SYN,
                                                                                          digestSynMessage,
                                                                                          GossipDigestSyn.serializer);
                    /* Gossip to some random live member */
                    boolean gossipedToSeed = doGossipToLiveMember(message);

                    /* Gossip to some unreachable member with some probability to check if he is back up */
                    maybeGossipToUnreachableMember(message);

                    /* Gossip to a seed if we did not do so above, or we have seen less nodes
                       than there are seeds.  This prevents partitions where each group of nodes
                       is only gossiping to a subset of the seeds.

                       The most straightforward check would be to check that all the seeds have been
                       verified either as live or unreachable.  To avoid that computation each round,
                       we reason that:

                       either all the live nodes are seeds, in which case non-seeds that come online
                       will introduce themselves to a member of the ring by definition,

                       or there is at least one non-seed node in the list, in which case eventually
                       someone will gossip to it, and then do a gossip to a random seed from the
                       gossipedToSeed check.

                       See CASSANDRA-150 for more exposition. */
                    if (!gossipedToSeed || liveEndpoints.size() < seeds.size())
                        maybeGossipToSeed(message);

                    doStatusCheck();
                }
            }
            catch (Exception e)
            {
                JVMStabilityInspector.inspectThrowable(e);
                logger.error("Gossip error", e);
            }
            finally
            {
                taskLock.unlock();
            }
        }
    }

    private class UnreachableQuarantineTask implements Runnable
    {
        public void run()
        {
            // bail if messaging service is not listening or daemon is not active
            if (!MessagingService.instance().isListening() || !StorageService.instance.isSetupCompleted())
                return;

            try
            {
                unreachableQuarantineLock.lock();
                unreachableQuarantineState = unreachableQuarantineState.doNext(getUnreachableRatio());
            }
            catch (Exception e)
            {
                JVMStabilityInspector.inspectThrowable(e);
                logger.error("Quarantine error", e);
            }
            finally
            {
                unreachableQuarantineLock.unlock();
            }
        }
    }

    private Gossiper()
    {
        // half of QUARATINE_DELAY, to ensure justRemovedEndpoints has enough leeway to prevent re-gossip
        fatClientTimeout = (QUARANTINE_DELAY / 2);
        /* register with the Failure Detector for receiving Failure detector events */
        FailureDetector.instance.registerFailureDetectionEventListener(this);

        // Register this instance with JMX
        try
        {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.registerMBean(this, new ObjectName(MBEAN_NAME));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setLastProcessedMessageAt(long timeInMillis)
    {
        this.lastProcessedMessageAt = timeInMillis;
    }

    public boolean seenAnySeed()
    {
        for (Map.Entry<InetAddress, EndpointState> entry : endpointStateMap.entrySet())
        {
            if (seeds.contains(entry.getKey()))
                return true;
            try
            {
                VersionedValue internalIp = entry.getValue().getApplicationState(ApplicationState.INTERNAL_IP);
                if (internalIp != null && seeds.contains(InetAddress.getByName(internalIp.value)))
                    return true;
            }
            catch (UnknownHostException e)
            {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * Register for interesting state changes.
     *
     * @param subscriber module which implements the IEndpointStateChangeSubscriber
     */
    public void register(IEndpointStateChangeSubscriber subscriber)
    {
        subscribers.add(subscriber);
    }

    /**
     * Unregister interest for state changes.
     *
     * @param subscriber module which implements the IEndpointStateChangeSubscriber
     */
    public void unregister(IEndpointStateChangeSubscriber subscriber)
    {
        subscribers.remove(subscriber);
    }

    /**
     * @return a list of live gossip participants, including fat clients
     */
    public Set<InetAddress> getLiveMembers()
    {
        Set<InetAddress> liveMembers = new HashSet<>(liveEndpoints);
        if (!liveMembers.contains(FBUtilities.getBroadcastAddress()))
            liveMembers.add(FBUtilities.getBroadcastAddress());
        return liveMembers;
    }

    /**
     * @return a list of live ring members.
     */
    public Set<InetAddress> getLiveTokenOwners()
    {
        return StorageService.instance.getLiveRingMembers(true);
    }

    /**
     * @return a list of unreachable gossip participants, including fat clients
     */
    public Set<InetAddress> getUnreachableMembers()
    {
        return unreachableEndpoints.keySet();
    }

    /**
     * @return a list of unreachable token owners
     */
    public Set<InetAddress> getUnreachableTokenOwners()
    {
        Set<InetAddress> tokenOwners = new HashSet<>();
        for (InetAddress endpoint : unreachableEndpoints.keySet())
        {
            if (StorageService.instance.getTokenMetadata().isMember(endpoint))
                tokenOwners.add(endpoint);
        }

        return tokenOwners;
    }

    public double getUnreachableRatio()
    {
        return RatioGauge.Ratio.of(unreachableEndpoints.size(), unreachableEndpoints.size() + liveEndpoints.size()).getValue();
    }

    public long getEndpointDowntime(InetAddress ep)
    {
        Long downtime = unreachableEndpoints.get(ep);
        if (downtime != null)
            return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - downtime);
        else
            return 0L;
    }

    private boolean isShutdown(InetAddress endpoint)
    {
        EndpointState epState = endpointStateMap.get(endpoint);
        if (epState == null)
            return false;
        if (epState.getApplicationState(ApplicationState.STATUS) == null)
            return false;
        String value = epState.getApplicationState(ApplicationState.STATUS).value;
        String[] pieces = value.split(VersionedValue.DELIMITER_STR, -1);
        assert (pieces.length > 0);
        String state = pieces[0];
        return state.equals(VersionedValue.SHUTDOWN);
    }

    /**
     * This method is part of IFailureDetectionEventListener interface. This is invoked
     * by the Failure Detector when it convicts an end point.
     *
     * @param endpoint end point that is convicted.
     */
    public void convict(InetAddress endpoint, double phi)
    {
        EndpointState epState = endpointStateMap.get(endpoint);
        if (epState == null)
            return;

        if (!epState.isAlive())
            return;

        logger.debug("Convicting {} with status {} - alive {}", endpoint, getGossipStatus(epState), epState.isAlive());


        if (isShutdown(endpoint))
        {
            markAsShutdown(endpoint);
        }
        else
        {
            markDead(endpoint, epState);
        }
    }

    /**
     * This method is used to mark a node as shutdown; that is it gracefully exited on its own and told us about it
     * @param endpoint endpoint that has shut itself down
     */
    protected void markAsShutdown(InetAddress endpoint)
    {
        EndpointState epState = endpointStateMap.get(endpoint);
        if (epState == null)
            return;
        epState.addApplicationState(ApplicationState.STATUS, StorageService.instance.valueFactory.shutdown(true));
        epState.addApplicationState(ApplicationState.RPC_READY, StorageService.instance.valueFactory.rpcReady(false));
        epState.getHeartBeatState().forceHighestPossibleVersionUnsafe();
        markDead(endpoint, epState);
        FailureDetector.instance.forceConviction(endpoint);
    }

    /**
     * Return either: the greatest heartbeat or application state
     *
     * @param epState
     * @return
     */
    int getMaxEndpointStateVersion(EndpointState epState)
    {
        int maxVersion = epState.getHeartBeatState().getHeartBeatVersion();
        for (Map.Entry<ApplicationState, VersionedValue> state : epState.states())
            maxVersion = Math.max(maxVersion, state.getValue().version);
        return maxVersion;
    }

    /**
     * Removes the endpoint from gossip completely
     *
     * @param endpoint endpoint to be removed from the current membership.
     */
    private void evictFromMembership(InetAddress endpoint)
    {
        unreachableEndpoints.remove(endpoint);
        endpointStateMap.remove(endpoint);
        expireTimeEndpointMap.remove(endpoint);
        FailureDetector.instance.remove(endpoint);
        quarantineEndpoint(endpoint);
        if (logger.isDebugEnabled())
            logger.debug("evicting {} from gossip", endpoint);
    }

    /**
     * Removes the endpoint from Gossip but retains endpoint state
     */
    public void removeEndpoint(InetAddress endpoint)
    {
        // do subscribers first so anything in the subscriber that depends on gossiper state won't get confused
        for (IEndpointStateChangeSubscriber subscriber : subscribers)
            subscriber.onRemove(endpoint);

        if(seeds.contains(endpoint))
        {
            buildSeedsList();
            seeds.remove(endpoint);
            logger.info("removed {} from seeds, updated seeds list = {}", endpoint, seeds);
        }

        liveEndpoints.remove(endpoint);
        unreachableEndpoints.remove(endpoint);
        MessagingService.instance().resetVersion(endpoint);
        quarantineEndpoint(endpoint);
        MessagingService.instance().destroyConnectionPool(endpoint);
        if (logger.isDebugEnabled())
            logger.debug("removing endpoint {}", endpoint);
    }

    /**
     * Quarantines the endpoint for QUARANTINE_DELAY
     *
     * @param endpoint
     */
    private void quarantineEndpoint(InetAddress endpoint)
    {
        quarantineEndpoint(endpoint, System.currentTimeMillis());
    }

    /**
     * Quarantines the endpoint until quarantineExpiration + QUARANTINE_DELAY
     *
     * @param endpoint
     * @param quarantineExpiration
     */
    private void quarantineEndpoint(InetAddress endpoint, long quarantineExpiration)
    {
        justRemovedEndpoints.put(endpoint, quarantineExpiration);
    }

    /**
     * Quarantine endpoint specifically for replacement purposes.
     * @param endpoint
     */
    public void replacementQuarantine(InetAddress endpoint)
    {
        // remember, quarantineEndpoint will effectively already add QUARANTINE_DELAY, so this is 2x
        logger.debug("");
        quarantineEndpoint(endpoint, System.currentTimeMillis() + QUARANTINE_DELAY);
    }

    /**
     * Remove the Endpoint and evict immediately, to avoid gossiping about this node.
     * This should only be called when a token is taken over by a new IP address.
     *
     * @param endpoint The endpoint that has been replaced
     */
    public void replacedEndpoint(InetAddress endpoint)
    {
        removeEndpoint(endpoint);
        evictFromMembership(endpoint);
        replacementQuarantine(endpoint);
    }

    /**
     * The gossip digest is built based on randomization
     * rather than just looping through the collection of live endpoints.
     *
     * @param gDigests list of Gossip Digests.
     */
    private void makeRandomGossipDigest(List<GossipDigest> gDigests)
    {
        EndpointState epState;
        int generation = 0;
        int maxVersion = 0;

        // local epstate will be part of endpointStateMap
        List<InetAddress> endpoints = new ArrayList<InetAddress>(endpointStateMap.keySet());
        Collections.shuffle(endpoints, random);
        for (InetAddress endpoint : endpoints)
        {
            epState = endpointStateMap.get(endpoint);
            if (epState != null)
            {
                generation = epState.getHeartBeatState().getGeneration();
                maxVersion = getMaxEndpointStateVersion(epState);
            }
            gDigests.add(new GossipDigest(endpoint, generation, maxVersion));
        }

        if (logger.isTraceEnabled())
        {
            StringBuilder sb = new StringBuilder();
            for (GossipDigest gDigest : gDigests)
            {
                sb.append(gDigest);
                sb.append(" ");
            }
            logger.trace("Gossip Digests are : {}", sb);
        }
    }

    /**
     * This method will begin removing an existing endpoint from the cluster by spoofing its state
     * This should never be called unless this coordinator has had 'removenode' invoked
     *
     * @param endpoint    - the endpoint being removed
     * @param hostId      - the ID of the host being removed
     * @param localHostId - my own host ID for replication coordination
     */
    public void advertiseRemoving(InetAddress endpoint, UUID hostId, UUID localHostId)
    {
        EndpointState epState = endpointStateMap.get(endpoint);
        // remember this node's generation
        int generation = epState.getHeartBeatState().getGeneration();
        logger.info("Removing host: {}", hostId);
        logger.info("Sleeping for {}ms to ensure {} does not change", StorageService.RING_DELAY, endpoint);
        Uninterruptibles.sleepUninterruptibly(StorageService.RING_DELAY, TimeUnit.MILLISECONDS);
        // make sure it did not change
        epState = endpointStateMap.get(endpoint);
        if (epState.getHeartBeatState().getGeneration() != generation)
            throw new RuntimeException("Endpoint " + endpoint + " generation changed while trying to remove it");
        // update the other node's generation to mimic it as if it had changed it itself
        logger.info("Advertising removal for {}", endpoint);
        epState.updateTimestamp(); // make sure we don't evict it too soon
        epState.getHeartBeatState().forceNewerGenerationUnsafe();
        Map<ApplicationState, VersionedValue> states = new EnumMap<>(ApplicationState.class);
        states.put(ApplicationState.STATUS, StorageService.instance.valueFactory.removingNonlocal(hostId));
        states.put(ApplicationState.REMOVAL_COORDINATOR, StorageService.instance.valueFactory.removalCoordinator(localHostId));
        epState.addApplicationStates(states);
        endpointStateMap.put(endpoint, epState);
    }

    /**
     * Handles switching the endpoint's state from REMOVING_TOKEN to REMOVED_TOKEN
     * This should only be called after advertiseRemoving
     *
     * @param endpoint
     * @param hostId
     */
    public void advertiseTokenRemoved(InetAddress endpoint, UUID hostId)
    {
        EndpointState epState = endpointStateMap.get(endpoint);
        epState.updateTimestamp(); // make sure we don't evict it too soon
        epState.getHeartBeatState().forceNewerGenerationUnsafe();
        long expireTime = computeExpireTime();
        epState.addApplicationState(ApplicationState.STATUS, StorageService.instance.valueFactory.removedNonlocal(hostId, expireTime));
        logger.info("Completing removal of {}", endpoint);
        addExpireTimeForEndpoint(endpoint, expireTime);
        endpointStateMap.put(endpoint, epState);
        // ensure at least one gossip round occurs before returning
        Uninterruptibles.sleepUninterruptibly(intervalInMillis * 2, TimeUnit.MILLISECONDS);
    }

    public void unsafeAssassinateEndpoint(String address) throws UnknownHostException
    {
        logger.warn("Gossiper.unsafeAssassinateEndpoint is deprecated and will be removed in the next release; use assassinateEndpoint instead");
        assassinateEndpoint(address);
    }

    /**
     * Do not call this method unless you know what you are doing.
     * It will try extremely hard to obliterate any endpoint from the ring,
     * even if it does not know about it.
     *
     * @param address
     * @throws UnknownHostException
     */
    public void assassinateEndpoint(String address) throws UnknownHostException
    {
        InetAddress endpoint = InetAddress.getByName(address);
        EndpointState epState = endpointStateMap.get(endpoint);
        Collection<Token> tokens = null;
        logger.warn("Assassinating {} via gossip", endpoint);

        if (epState == null)
        {
            epState = new EndpointState(new HeartBeatState((int) ((System.currentTimeMillis() + 60000) / 1000), 9999));
        }
        else
        {
            int generation = epState.getHeartBeatState().getGeneration();
            int heartbeat = epState.getHeartBeatState().getHeartBeatVersion();
            logger.info("Sleeping for {}ms to ensure {} does not change", StorageService.RING_DELAY, endpoint);
            Uninterruptibles.sleepUninterruptibly(StorageService.RING_DELAY, TimeUnit.MILLISECONDS);
            // make sure it did not change
            EndpointState newState = endpointStateMap.get(endpoint);
            if (newState == null)
                logger.warn("Endpoint {} disappeared while trying to assassinate, continuing anyway", endpoint);
            else if (newState.getHeartBeatState().getGeneration() != generation)
                throw new RuntimeException("Endpoint still alive: " + endpoint + " generation changed while trying to assassinate it");
            else if (newState.getHeartBeatState().getHeartBeatVersion() != heartbeat)
                throw new RuntimeException("Endpoint still alive: " + endpoint + " heartbeat changed while trying to assassinate it");
            epState.updateTimestamp(); // make sure we don't evict it too soon
            epState.getHeartBeatState().forceNewerGenerationUnsafe();
        }

        try
        {
            tokens = StorageService.instance.getTokenMetadata().getTokens(endpoint);
        }
        catch (Throwable th)
        {
            JVMStabilityInspector.inspectThrowable(th);
            // TODO this is broken
            logger.warn("Unable to calculate tokens for {}.  Will use a random one", address);
            tokens = Collections.singletonList(StorageService.instance.getTokenMetadata().partitioner.getRandomToken());
        }

        // do not pass go, do not collect 200 dollars, just gtfo
        epState.addApplicationState(ApplicationState.STATUS, StorageService.instance.valueFactory.left(tokens, computeExpireTime()));
        handleMajorStateChange(endpoint, epState);
        Uninterruptibles.sleepUninterruptibly(intervalInMillis * 4, TimeUnit.MILLISECONDS);
        logger.warn("Finished assassinating {}", endpoint);
    }

    public boolean isKnownEndpoint(InetAddress endpoint)
    {
        return endpointStateMap.containsKey(endpoint);
    }

    public int getCurrentGenerationNumber(InetAddress endpoint)
    {
        return endpointStateMap.get(endpoint).getHeartBeatState().getGeneration();
    }

    /**
     * Returns true if the chosen target was also a seed. False otherwise
     *
     * @param message
     * @param epSet   a set of endpoint from which a random endpoint is chosen.
     * @return true if the chosen endpoint is also a seed.
     */
    private boolean sendGossip(MessageOut<GossipDigestSyn> message, Set<InetAddress> epSet)
    {
        List<InetAddress> liveEndpoints = ImmutableList.copyOf(epSet);
        
        int size = liveEndpoints.size();
        if (size < 1)
            return false;
        /* Generate a random number from 0 -> size */
        int index = (size == 1) ? 0 : random.nextInt(size);
        InetAddress to = liveEndpoints.get(index);
        if (logger.isTraceEnabled())
            logger.trace("Sending a GossipDigestSyn to {} ...", to);
        if (firstSynSendAt == 0)
            firstSynSendAt = System.nanoTime();
        MessagingService.instance().sendOneWay(message, to);
        return seeds.contains(to);
    }

    /* Sends a Gossip message to a live member and returns true if the recipient was a seed */
    private boolean doGossipToLiveMember(MessageOut<GossipDigestSyn> message)
    {
        int size = liveEndpoints.size();
        if (size == 0)
            return false;
        return sendGossip(message, liveEndpoints);
    }

    /* Sends a Gossip message to an unreachable member */
    private void maybeGossipToUnreachableMember(MessageOut<GossipDigestSyn> message)
    {
        double liveEndpointCount = liveEndpoints.size();
        double unreachableEndpointCount = unreachableEndpoints.size();
        if (unreachableEndpointCount > 0)
        {
            /* based on some probability */
            double prob = unreachableEndpointCount / (liveEndpointCount + 1);
            double randDbl = random.nextDouble();
            if (randDbl < prob)
                sendGossip(message, unreachableEndpoints.keySet());
        }
    }

    /* Possibly gossip to a seed for facilitating partition healing */
    private void maybeGossipToSeed(MessageOut<GossipDigestSyn> prod)
    {
        int size = seeds.size();
        if (size > 0)
        {
            if (size == 1 && seeds.contains(FBUtilities.getBroadcastAddress()))
            {
                return;
            }

            if (liveEndpoints.size() == 0)
            {
                sendGossip(prod, seeds);
            }
            else
            {
                /* Gossip with the seed with some probability. */
                double probability = seeds.size() / (double) (liveEndpoints.size() + unreachableEndpoints.size());
                double randDbl = random.nextDouble();
                if (randDbl <= probability)
                    sendGossip(prod, seeds);
            }
        }
    }

    public boolean isGossipOnlyMember(InetAddress endpoint)
    {
        EndpointState epState = endpointStateMap.get(endpoint);
        if (epState == null)
        {
            return false;
        }
        return !isDeadState(epState) && !StorageService.instance.getTokenMetadata().isMember(endpoint);
    }

    /**
     * Check if this endpoint can safely bootstrap into the cluster.
     *
     * @param endpoint - the endpoint to check
     * @param epStates - endpoint states in the cluster
     * @return true if the endpoint can join the cluster
     */
    public boolean isSafeForBootstrap(InetAddress endpoint, Map<InetAddress, EndpointState> epStates)
    {
        EndpointState epState = epStates.get(endpoint);

        // if there's no previous state, or the node was previously removed from the cluster, we're good
        if (epState == null || isDeadState(epState))
            return true;

        String status = getGossipStatus(epState);

        // these states are not allowed to join the cluster as it would not be safe
        final List<String> unsafeStatuses = new ArrayList<String>() {{
            add(""); // failed bootstrap but we did start gossiping
            add(VersionedValue.STATUS_NORMAL); // node is legit in the cluster or it was stopped with kill -9
            add(VersionedValue.SHUTDOWN); }}; // node was shutdown
        return !unsafeStatuses.contains(status);
    }

    public double getReachableQuarantineThreshold()
    {
        return DatabaseDescriptor.getReachableEndpointsRatioThreshold();
    }

    public void setReachableQuarantineThreshold(double threshold)
    {
        DatabaseDescriptor.setReachableEndpointsRatioThreshold(threshold);
    }

    public double getUnreachableQuarantineThreshold()
    {
        return DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
    }

    public void setUnreachableQuarantineThreshold(double threshold)
    {
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(threshold);
    }

    private void doStatusCheck()
    {
        if (logger.isTraceEnabled())
            logger.trace("Performing status check ...");

        long now = System.currentTimeMillis();
        long nowNano = System.nanoTime();

        long pending = ((JMXEnabledThreadPoolExecutor) StageManager.getStage(Stage.GOSSIP)).metrics.pendingTasks.getValue();
        if (pending > 0 && lastProcessedMessageAt < now - 1000)
        {
            // if some new messages just arrived, give the executor some time to work on them
            Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);

            // still behind?  something's broke
            if (lastProcessedMessageAt < now - 1000)
            {
                logger.warn("Gossip stage has {} pending tasks; skipping status check (no nodes will be marked down)", pending);
                return;
            }
        }

        Set<InetAddress> eps = endpointStateMap.keySet();
        for (InetAddress endpoint : eps)
        {
            if (endpoint.equals(FBUtilities.getBroadcastAddress()))
                continue;

            FailureDetector.instance.interpret(endpoint);
            EndpointState epState = endpointStateMap.get(endpoint);
            if (epState != null)
            {
                // check if this is a fat client. fat clients are removed automatically from
                // gossip after FatClientTimeout.  Do not remove dead states here.
                if (isGossipOnlyMember(endpoint)
                    && !justRemovedEndpoints.containsKey(endpoint)
                    && TimeUnit.NANOSECONDS.toMillis(nowNano - epState.getUpdateTimestamp()) > fatClientTimeout)
                {
                    logger.info("FatClient {} has been silent for {}ms, removing from gossip", endpoint, fatClientTimeout);
                    removeEndpoint(endpoint); // will put it in justRemovedEndpoints to respect quarantine delay
                    evictFromMembership(endpoint); // can get rid of the state immediately
                }

                // check for dead state removal
                long expireTime = getExpireTimeForEndpoint(endpoint);
                if (!epState.isAlive() && (now > expireTime)
                    && (!StorageService.instance.getTokenMetadata().isMember(endpoint)))
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("time is expiring for endpoint : {} ({})", endpoint, expireTime);
                    }
                    evictFromMembership(endpoint);
                }
            }
        }

        if (!justRemovedEndpoints.isEmpty())
        {
            for (Entry<InetAddress, Long> entry : justRemovedEndpoints.entrySet())
            {
                if ((now - entry.getValue()) > QUARANTINE_DELAY)
                {
                    if (logger.isDebugEnabled())
                        logger.debug("{} elapsed, {} gossip quarantine over", QUARANTINE_DELAY, entry.getKey());
                    justRemovedEndpoints.remove(entry.getKey());
                }
            }
        }
    }

    protected long getExpireTimeForEndpoint(InetAddress endpoint)
    {
        /* default expireTime is aVeryLongTime */
        Long storedTime = expireTimeEndpointMap.get(endpoint);
        return storedTime == null ? computeExpireTime() : storedTime;
    }

    public EndpointState getEndpointStateForEndpoint(InetAddress ep)
    {
        return endpointStateMap.get(ep);
    }

    public boolean valuesEqual(InetAddress ep1, InetAddress ep2, ApplicationState as)
    {
        EndpointState state1 = getEndpointStateForEndpoint(ep1);
        EndpointState state2 = getEndpointStateForEndpoint(ep2);

        if (state1 == null || state2 == null)
            return false;

        VersionedValue value1 = state1.getApplicationState(as);
        VersionedValue value2 = state2.getApplicationState(as);

        return !(value1 == null || value2 == null) && value1.value.equals(value2.value);
    }

    public Set<Entry<InetAddress, EndpointState>> getEndpointStates()
    {
        return endpointStateMap.entrySet();
    }

    public UUID getHostId(InetAddress endpoint)
    {
        return getHostId(endpoint, endpointStateMap);
    }

    public UUID getHostId(InetAddress endpoint, Map<InetAddress, EndpointState> epStates)
    {
        return UUID.fromString(epStates.get(endpoint).getApplicationState(ApplicationState.HOST_ID).value);
    }

    EndpointState getStateForVersionBiggerThan(InetAddress forEndpoint, int version)
    {
        EndpointState epState = endpointStateMap.get(forEndpoint);
        EndpointState reqdEndpointState = null;

        if (epState != null)
        {
            /*
             * Here we try to include the Heart Beat state only if it is
             * greater than the version passed in. It might happen that
             * the heart beat version maybe lesser than the version passed
             * in and some application state has a version that is greater
             * than the version passed in. In this case we also send the old
             * heart beat and throw it away on the receiver if it is redundant.
            */
            HeartBeatState heartBeatState = epState.getHeartBeatState();
            int localHbGeneration = heartBeatState.getGeneration();
            int localHbVersion = heartBeatState.getHeartBeatVersion();
            if (localHbVersion > version)
            {
                reqdEndpointState = new EndpointState(new HeartBeatState(localHbGeneration, localHbVersion));
                if (logger.isTraceEnabled())
                    logger.trace("local heartbeat version {} greater than {} for {}", localHbVersion, version, forEndpoint);
            }
            /* Accumulate all application states whose versions are greater than "version" variable */
            Map<ApplicationState, VersionedValue> states = new EnumMap<>(ApplicationState.class);
            for (Entry<ApplicationState, VersionedValue> entry : epState.states())
            {
                VersionedValue value = entry.getValue();
                if (value.version > version)
                {
                    if (reqdEndpointState == null)
                    {
                        reqdEndpointState = new EndpointState(new HeartBeatState(localHbGeneration, localHbVersion));
                    }
                    final ApplicationState key = entry.getKey();
                    if (logger.isTraceEnabled())
                        logger.trace("Adding state {}: {}" , key, value.value);

                    states.put(key, value);
                }
            }
            if (reqdEndpointState != null)
                reqdEndpointState.addApplicationStates(states);
        }
        return reqdEndpointState;
    }

    /**
     * determine which endpoint started up earlier
     */
    public int compareEndpointStartup(InetAddress addr1, InetAddress addr2)
    {
        EndpointState ep1 = getEndpointStateForEndpoint(addr1);
        EndpointState ep2 = getEndpointStateForEndpoint(addr2);
        assert ep1 != null && ep2 != null;
        return ep1.getHeartBeatState().getGeneration() - ep2.getHeartBeatState().getGeneration();
    }

    void notifyFailureDetector(Map<InetAddress, EndpointState> remoteEpStateMap)
    {
        for (Entry<InetAddress, EndpointState> entry : remoteEpStateMap.entrySet())
        {
            notifyFailureDetector(entry.getKey(), entry.getValue());
        }
    }

    void notifyFailureDetector(InetAddress endpoint, EndpointState remoteEndpointState)
    {
        EndpointState localEndpointState = endpointStateMap.get(endpoint);
        /*
         * If the local endpoint state exists then report to the FD only
         * if the versions workout.
        */
        if (localEndpointState != null)
        {
            IFailureDetector fd = FailureDetector.instance;
            int localGeneration = localEndpointState.getHeartBeatState().getGeneration();
            int remoteGeneration = remoteEndpointState.getHeartBeatState().getGeneration();
            if (remoteGeneration > localGeneration)
            {
                localEndpointState.updateTimestamp();
                // this node was dead and the generation changed, this indicates a reboot, or possibly a takeover
                // we will clean the fd intervals for it and relearn them
                if (!localEndpointState.isAlive())
                {
                    logger.debug("Clearing interval times for {} due to generation change", endpoint);
                    fd.remove(endpoint);
                }
                fd.report(endpoint);
                return;
            }

            if (remoteGeneration == localGeneration)
            {
                int localVersion = getMaxEndpointStateVersion(localEndpointState);
                int remoteVersion = remoteEndpointState.getHeartBeatState().getHeartBeatVersion();
                if (remoteVersion > localVersion)
                {
                    localEndpointState.updateTimestamp();
                    // just a version change, report to the fd
                    fd.report(endpoint);
                }
            }
        }

    }

    private void markAlive(final InetAddress addr, final EndpointState localState)
    {
        if (MessagingService.instance().getVersion(addr) < MessagingService.VERSION_20)
        {
            realMarkAlive(addr, localState);
            return;
        }

        localState.markDead();

        MessageOut<EchoMessage> echoMessage = new MessageOut<EchoMessage>(MessagingService.Verb.ECHO, EchoMessage.instance, EchoMessage.serializer);
        logger.trace("Sending a EchoMessage to {}", addr);
        IAsyncCallback echoHandler = new IAsyncCallback()
        {
            public boolean isLatencyForSnitch()
            {
                return false;
            }

            public void response(MessageIn msg)
            {
                realMarkAlive(addr, localState);
            }
        };

        MessagingService.instance().sendRR(echoMessage, addr, echoHandler);
    }

    @VisibleForTesting
    public void realMarkAlive(final InetAddress addr, final EndpointState localState)
    {
        if (logger.isTraceEnabled())
            logger.trace("marking as alive {}", addr);
        localState.markAlive();
        localState.updateTimestamp(); // prevents doStatusCheck from racing us and evicting if it was down > aVeryLongTime
        liveEndpoints.add(addr);
        unreachableEndpoints.remove(addr);
        expireTimeEndpointMap.remove(addr);
        logger.debug("removing expire time for endpoint : {}", addr);
        logger.info("InetAddress {} is now UP", addr);
        for (IEndpointStateChangeSubscriber subscriber : subscribers)
            subscriber.onAlive(addr, localState);
        if (logger.isTraceEnabled())
            logger.trace("Notified {}", subscribers);
    }

    @VisibleForTesting
    public void markDead(InetAddress addr, EndpointState localState)
    {
        if (logger.isTraceEnabled())
            logger.trace("marking as down {}", addr);
        localState.markDead();
        liveEndpoints.remove(addr);
        unreachableEndpoints.put(addr, System.nanoTime());
        logger.info("InetAddress {} is now DOWN", addr);
        for (IEndpointStateChangeSubscriber subscriber : subscribers)
            subscriber.onDead(addr, localState);
        if (logger.isTraceEnabled())
            logger.trace("Notified {}", subscribers);
    }

    /**
     * This method is called whenever there is a "big" change in ep state (a generation change for a known node).
     *
     * @param ep      endpoint
     * @param epState EndpointState for the endpoint
     */
    private void handleMajorStateChange(InetAddress ep, EndpointState epState)
    {
        EndpointState localEpState = endpointStateMap.get(ep);
        if (!isDeadState(epState))
        {
            if (localEpState != null)
                logger.info("Node {} has restarted, now UP", ep);
            else
                logger.info("Node {} is now part of the cluster", ep);
        }
        if (logger.isTraceEnabled())
            logger.trace("Adding endpoint state for {}", ep);
        endpointStateMap.put(ep, epState);

        if (localEpState != null)
        {   // the node restarted: it is up to the subscriber to take whatever action is necessary
            for (IEndpointStateChangeSubscriber subscriber : subscribers)
                subscriber.onRestart(ep, localEpState);
        }

        if (!isDeadState(epState))
            markAlive(ep, epState);
        else
        {
            logger.debug("Not marking {} alive due to dead state", ep);
            markDead(ep, epState);
        }
        for (IEndpointStateChangeSubscriber subscriber : subscribers)
            subscriber.onJoin(ep, epState);
        // check this at the end so nodes will learn about the endpoint
        if (isShutdown(ep))
            markAsShutdown(ep);
    }

    public boolean isAlive(InetAddress endpoint)
    {
        EndpointState epState = getEndpointStateForEndpoint(endpoint);
        if (epState == null)
            return false;
        return epState.isAlive() && !isDeadState(epState);
    }

    public boolean isDeadState(EndpointState epState)
    {
        String status = getGossipStatus(epState);
        if (status.isEmpty())
            return false;

        return DEAD_STATES.contains(status);
    }

    public boolean isSilentShutdownState(EndpointState epState)
    {
        String status = getGossipStatus(epState);
        if (status.isEmpty())
            return false;

        return SILENT_SHUTDOWN_STATES.contains(status);
    }

    private static String getGossipStatus(EndpointState epState)
    {
        if (epState == null || epState.getApplicationState(ApplicationState.STATUS) == null)
            return "";

        String value = epState.getApplicationState(ApplicationState.STATUS).value;
        String[] pieces = value.split(VersionedValue.DELIMITER_STR, -1);
        assert (pieces.length > 0);
        return pieces[0];
    }

    void applyStateLocally(Map<InetAddress, EndpointState> epStateMap)
    {
        for (Entry<InetAddress, EndpointState> entry : epStateMap.entrySet())
        {
            InetAddress ep = entry.getKey();
            if ( ep.equals(FBUtilities.getBroadcastAddress()) && !isInShadowRound())
                continue;
            if (justRemovedEndpoints.containsKey(ep))
            {
                if (logger.isTraceEnabled())
                    logger.trace("Ignoring gossip for {} because it is quarantined", ep);
                continue;
            }

            EndpointState localEpStatePtr = endpointStateMap.get(ep);
            EndpointState remoteState = entry.getValue();

            /*
                If state does not exist just add it. If it does then add it if the remote generation is greater.
                If there is a generation tie, attempt to break it by heartbeat version.
            */
            if (localEpStatePtr != null)
            {
                int localGeneration = localEpStatePtr.getHeartBeatState().getGeneration();
                int remoteGeneration = remoteState.getHeartBeatState().getGeneration();
                long localTime = System.currentTimeMillis()/1000;
                if (logger.isTraceEnabled())
                    logger.trace("{} local generation {}, remote generation {}", ep, localGeneration, remoteGeneration);

                // We measure generation drift against local time, based on the fact that generation is initialized by time
                if (remoteGeneration > localTime + MAX_GENERATION_DIFFERENCE)
                {
                    // assume some peer has corrupted memory and is broadcasting an unbelievable generation about another peer (or itself)
                    logger.warn("received an invalid gossip generation for peer {}; local time = {}, received generation = {}", ep, localTime, remoteGeneration);
                }
                else if (remoteGeneration > localGeneration)
                {
                    if (logger.isTraceEnabled())
                        logger.trace("Updating heartbeat state generation to {} from {} for {}", remoteGeneration, localGeneration, ep);
                    // major state change will handle the update by inserting the remote state directly
                    handleMajorStateChange(ep, remoteState);
                }
                else if (remoteGeneration == localGeneration) // generation has not changed, apply new states
                {
                    /* find maximum state */
                    int localMaxVersion = getMaxEndpointStateVersion(localEpStatePtr);
                    int remoteMaxVersion = getMaxEndpointStateVersion(remoteState);
                    if (remoteMaxVersion > localMaxVersion)
                    {
                        // apply states, but do not notify since there is no major change
                        applyNewStates(ep, localEpStatePtr, remoteState);
                    }
                    else if (logger.isTraceEnabled())
                            logger.trace("Ignoring remote version {} <= {} for {}", remoteMaxVersion, localMaxVersion, ep);

                    if (!localEpStatePtr.isAlive() && !isDeadState(localEpStatePtr)) // unless of course, it was dead
                        markAlive(ep, localEpStatePtr);
                }
                else
                {
                    if (logger.isTraceEnabled())
                        logger.trace("Ignoring remote generation {} < {}", remoteGeneration, localGeneration);
                }
            }
            else
            {
                // this is a new node, report it to the FD in case it is the first time we are seeing it AND it's not alive
                FailureDetector.instance.report(ep);
                handleMajorStateChange(ep, remoteState);
            }
        }
    }

    private void applyNewStates(InetAddress addr, EndpointState localState, EndpointState remoteState)
    {
        // don't assert here, since if the node restarts the version will go back to zero
        int oldVersion = localState.getHeartBeatState().getHeartBeatVersion();

        localState.setHeartBeatState(remoteState.getHeartBeatState());
        if (logger.isTraceEnabled())
            logger.trace("Updating heartbeat state version to {} from {} for {} ...", localState.getHeartBeatState().getHeartBeatVersion(), oldVersion, addr);

        Set<Entry<ApplicationState, VersionedValue>> remoteStates = remoteState.states();
        assert remoteState.getHeartBeatState().getGeneration() == localState.getHeartBeatState().getGeneration();

        // filter out the states that are already up to date (has the same or higher version)
        Set<Entry<ApplicationState, VersionedValue>> updatedStates = remoteStates.stream().filter(entry -> {
            VersionedValue local = localState.getApplicationState(entry.getKey());
            return (local == null || local.version < entry.getValue().version);
            }).collect(Collectors.toSet());

        if (logger.isTraceEnabled() && updatedStates.size() > 0)
        {
            for (Entry<ApplicationState, VersionedValue> entry : updatedStates)
            {
                logger.trace("Updating {} state version to {} for {}", entry.getKey().toString(), entry.getValue().version, addr);
            }
        }
        localState.addApplicationStates(updatedStates);

        for (Entry<ApplicationState, VersionedValue> updatedEntry : updatedStates)
            doOnChangeNotifications(addr, updatedEntry.getKey(), updatedEntry.getValue());
    }
    
    // notify that a local application state is going to change (doesn't get triggered for remote changes)
    private void doBeforeChangeNotifications(InetAddress addr, EndpointState epState, ApplicationState apState, VersionedValue newValue)
    {
        for (IEndpointStateChangeSubscriber subscriber : subscribers)
        {
            subscriber.beforeChange(addr, epState, apState, newValue);
        }
    }

    // notify that an application state has changed
    private void doOnChangeNotifications(InetAddress addr, ApplicationState state, VersionedValue value)
    {
        for (IEndpointStateChangeSubscriber subscriber : subscribers)
        {
            subscriber.onChange(addr, state, value);
        }
    }

    /* Request all the state for the endpoint in the gDigest */
    private void requestAll(GossipDigest gDigest, List<GossipDigest> deltaGossipDigestList, int remoteGeneration)
    {
        /* We are here since we have no data for this endpoint locally so request everthing. */
        deltaGossipDigestList.add(new GossipDigest(gDigest.getEndpoint(), remoteGeneration, 0));
        if (logger.isTraceEnabled())
            logger.trace("requestAll for {}", gDigest.getEndpoint());
    }

    /* Send all the data with version greater than maxRemoteVersion */
    private void sendAll(GossipDigest gDigest, Map<InetAddress, EndpointState> deltaEpStateMap, int maxRemoteVersion)
    {
        EndpointState localEpStatePtr = getStateForVersionBiggerThan(gDigest.getEndpoint(), maxRemoteVersion);
        if (localEpStatePtr != null)
            deltaEpStateMap.put(gDigest.getEndpoint(), localEpStatePtr);
    }

    /*
        This method is used to figure the state that the Gossiper has but Gossipee doesn't. The delta digests
        and the delta state are built up.
    */
    void examineGossiper(List<GossipDigest> gDigestList, List<GossipDigest> deltaGossipDigestList, Map<InetAddress, EndpointState> deltaEpStateMap)
    {
        if (gDigestList.size() == 0)
        {
           /* we've been sent a *completely* empty syn, which should normally never happen since an endpoint will at least send a syn with itself.
              If this is happening then the node is attempting shadow gossip, and we should reply with everything we know.
            */
            logger.debug("Shadow request received, adding all states");
            for (Map.Entry<InetAddress, EndpointState> entry : endpointStateMap.entrySet())
            {
                gDigestList.add(new GossipDigest(entry.getKey(), 0, 0));
            }
        }
        for ( GossipDigest gDigest : gDigestList )
        {
            int remoteGeneration = gDigest.getGeneration();
            int maxRemoteVersion = gDigest.getMaxVersion();
            /* Get state associated with the end point in digest */
            EndpointState epStatePtr = endpointStateMap.get(gDigest.getEndpoint());
            /*
                Here we need to fire a GossipDigestAckMessage. If we have some data associated with this endpoint locally
                then we follow the "if" path of the logic. If we have absolutely nothing for this endpoint we need to
                request all the data for this endpoint.
            */
            if (epStatePtr != null)
            {
                int localGeneration = epStatePtr.getHeartBeatState().getGeneration();
                /* get the max version of all keys in the state associated with this endpoint */
                int maxLocalVersion = getMaxEndpointStateVersion(epStatePtr);
                if (remoteGeneration == localGeneration && maxRemoteVersion == maxLocalVersion)
                    continue;

                if (remoteGeneration > localGeneration)
                {
                    /* we request everything from the gossiper */
                    requestAll(gDigest, deltaGossipDigestList, remoteGeneration);
                }
                else if (remoteGeneration < localGeneration)
                {
                    /* send all data with generation = localgeneration and version > 0 */
                    sendAll(gDigest, deltaEpStateMap, 0);
                }
                else if (remoteGeneration == localGeneration)
                {
                    /*
                        If the max remote version is greater then we request the remote endpoint send us all the data
                        for this endpoint with version greater than the max version number we have locally for this
                        endpoint.
                        If the max remote version is lesser, then we send all the data we have locally for this endpoint
                        with version greater than the max remote version.
                    */
                    if (maxRemoteVersion > maxLocalVersion)
                    {
                        deltaGossipDigestList.add(new GossipDigest(gDigest.getEndpoint(), remoteGeneration, maxLocalVersion));
                    }
                    else if (maxRemoteVersion < maxLocalVersion)
                    {
                        /* send all data with generation = localgeneration and version > maxRemoteVersion */
                        sendAll(gDigest, deltaEpStateMap, maxRemoteVersion);
                    }
                }
            }
            else
            {
                /* We are here since we have no data for this endpoint locally so request everything. */
                requestAll(gDigest, deltaGossipDigestList, remoteGeneration);
            }
        }
    }

    public void start(int generationNumber)
    {
        start(generationNumber, new EnumMap<ApplicationState, VersionedValue>(ApplicationState.class));
    }

    /**
     * Start the gossiper with the generation number, preloading the map of application states before starting
     */
    public void start(int generationNbr, Map<ApplicationState, VersionedValue> preloadLocalStates)
    {
        buildSeedsList();
        /* initialize the heartbeat state for this localEndpoint */
        maybeInitializeLocalState(generationNbr);
        EndpointState localState = endpointStateMap.get(FBUtilities.getBroadcastAddress());
        localState.addApplicationStates(preloadLocalStates);

        //notify snitches that Gossiper is about to start
        DatabaseDescriptor.getEndpointSnitch().gossiperStarting();
        if (logger.isTraceEnabled())
            logger.trace("gossip started with generation {}", localState.getHeartBeatState().getGeneration());

        scheduledGossipTask = executor.scheduleWithFixedDelay(new GossipTask(),
                                                              Gossiper.intervalInMillis,
                                                              Gossiper.intervalInMillis,
                                                              TimeUnit.MILLISECONDS);
        ScheduledExecutors.scheduledTasks.scheduleWithFixedDelay(new UnreachableQuarantineTask(),
                                                                 Gossiper.intervalInMillis,
                                                                 Gossiper.intervalInMillis,
                                                                 TimeUnit.MILLISECONDS);
    }

    /**
     * Do a single 'shadow' round of gossip by retrieving endpoint states that will be stored exclusively in the
     * map return value, instead of endpointStateMap.
     *
     * Used when preparing to join the ring:
     * <ul>
     *     <li>when replacing a node, to get and assume its tokens</li>
     *     <li>when joining, to check that the local host id matches any previous id for the endpoint address</li>
     * </ul>
     *
     * Method is synchronized, as we use an in-progress flag to indicate that shadow round must be cleared
     * again by calling {@link Gossiper#finishShadowRound(Map)}. This will update
     * {@link Gossiper#endpointShadowStateMap} with received values, in order to return an immutable copy to the
     * caller of {@link Gossiper#doShadowRound()}. Therefor only a single shadow round execution is permitted at
     * the same time.
     *
     * @return endpoint states gathered during shadow round or empty map
     */
    public synchronized Map<InetAddress, EndpointState> doShadowRound()
    {
        buildSeedsList();
        endpointShadowStateMap.clear();
        // send a completely empty syn
        List<GossipDigest> gDigests = new ArrayList<GossipDigest>();
        GossipDigestSyn digestSynMessage = new GossipDigestSyn(DatabaseDescriptor.getClusterName(),
                DatabaseDescriptor.getPartitionerName(),
                gDigests);
        MessageOut<GossipDigestSyn> message = new MessageOut<GossipDigestSyn>(MessagingService.Verb.GOSSIP_DIGEST_SYN,
                digestSynMessage,
                GossipDigestSyn.serializer);

        inShadowRound = true;
        int slept = 0;
        try
        {
            while (true)
            {
                if (slept % 5000 == 0)
                { // CASSANDRA-8072, retry at the beginning and every 5 seconds
                    logger.trace("Sending shadow round GOSSIP DIGEST SYN to seeds {}", seeds);
                    for (InetAddress seed : seeds)
                        MessagingService.instance().sendOneWay(message, seed);
                }

                Thread.sleep(1000);
                if (!inShadowRound)
                    break;

                slept += 1000;
                if (slept > StorageService.RING_DELAY)
                    throw new RuntimeException("Unable to gossip with any seeds");
            }
        }
        catch (InterruptedException wtf)
        {
            throw new RuntimeException(wtf);
        }

        return ImmutableMap.copyOf(endpointShadowStateMap);
    }

    private void buildSeedsList()
    {
        for (InetAddress seed : DatabaseDescriptor.getSeeds())
        {
            if (seed.equals(FBUtilities.getBroadcastAddress()))
                continue;
            seeds.add(seed);
        }
    }

    // initialize local HB state if needed, i.e., if gossiper has never been started before.
    public void maybeInitializeLocalState(int generationNbr)
    {
        HeartBeatState hbState = new HeartBeatState(generationNbr);
        EndpointState localState = new EndpointState(hbState);
        localState.markAlive();
        endpointStateMap.putIfAbsent(FBUtilities.getBroadcastAddress(), localState);
    }

    public void forceNewerGeneration()
    {
        EndpointState epstate = endpointStateMap.get(FBUtilities.getBroadcastAddress());
        epstate.getHeartBeatState().forceNewerGenerationUnsafe();
    }


    /**
     * Add an endpoint we knew about previously, but whose state is unknown
     */
    public void addSavedEndpoint(InetAddress ep)
    {
        if (ep.equals(FBUtilities.getBroadcastAddress()))
        {
            logger.debug("Attempt to add self as saved endpoint");
            return;
        }

        //preserve any previously known, in-memory data about the endpoint (such as DC, RACK, and so on)
        EndpointState epState = endpointStateMap.get(ep);
        if (epState != null)
        {
            logger.debug("not replacing a previous epState for {}, but reusing it: {}", ep, epState);
            epState.setHeartBeatState(new HeartBeatState(0));
        }
        else
        {
            epState = new EndpointState(new HeartBeatState(0));
        }

        epState.markDead();
        endpointStateMap.put(ep, epState);
        unreachableEndpoints.put(ep, System.nanoTime());
        if (logger.isTraceEnabled())
            logger.trace("Adding saved endpoint {} {}", ep, epState.getHeartBeatState().getGeneration());
    }

    private void addLocalApplicationStateInternal(ApplicationState state, VersionedValue value)
    {
        assert taskLock.isHeldByCurrentThread();
        EndpointState epState = endpointStateMap.get(FBUtilities.getBroadcastAddress());
        InetAddress epAddr = FBUtilities.getBroadcastAddress();
        assert epState != null;
        // Fire "before change" notifications:
        doBeforeChangeNotifications(epAddr, epState, state, value);
        // Notifications may have taken some time, so preventively raise the version
        // of the new value, otherwise it could be ignored by the remote node
        // if another value with a newer version was received in the meantime:
        value = StorageService.instance.valueFactory.cloneWithHigherVersion(value);
        // Add to local application state and fire "on change" notifications:
        epState.addApplicationState(state, value);
        doOnChangeNotifications(epAddr, state, value);
    }

    public void addLocalApplicationState(ApplicationState applicationState, VersionedValue value)
    {
        addLocalApplicationStates(Arrays.asList(Pair.create(applicationState, value)));
    }

    public void addLocalApplicationStates(List<Pair<ApplicationState, VersionedValue>> states)
    {
        taskLock.lock();
        try
        {
            for (Pair<ApplicationState, VersionedValue> pair : states)
            {
               addLocalApplicationStateInternal(pair.left, pair.right);
            }
        }
        finally
        {
            taskLock.unlock();
        }

    }

    public void stop()
    {
        EndpointState mystate = endpointStateMap.get(FBUtilities.getBroadcastAddress());
        if (mystate != null && !isSilentShutdownState(mystate) && StorageService.instance.isJoined())
        {
            logger.info("Announcing shutdown");
            addLocalApplicationState(ApplicationState.STATUS, StorageService.instance.valueFactory.shutdown(true));
            MessageOut message = new MessageOut(MessagingService.Verb.GOSSIP_SHUTDOWN);
            for (InetAddress ep : liveEndpoints)
                MessagingService.instance().sendOneWay(message, ep);
            Uninterruptibles.sleepUninterruptibly(Integer.getInteger("cassandra.shutdown_announce_in_ms", 2000), TimeUnit.MILLISECONDS);
        }
        else
            logger.warn("No local state, state is in silent shutdown, or node hasn't joined, not announcing shutdown");
        if (scheduledGossipTask != null)
            scheduledGossipTask.cancel(false);
    }

    public boolean isEnabled()
    {
        return (scheduledGossipTask != null) && (!scheduledGossipTask.isCancelled());
    }

    protected void finishShadowRound(Map<InetAddress, EndpointState> epStateMap)
    {
        if (inShadowRound)
        {
            endpointShadowStateMap.putAll(epStateMap);
            inShadowRound = false;
        }
    }

    protected boolean isInShadowRound()
    {
        return inShadowRound;
    }

    @VisibleForTesting
    public void initializeNodeUnsafe(InetAddress addr, UUID uuid, int generationNbr)
    {
        HeartBeatState hbState = new HeartBeatState(generationNbr);
        EndpointState newState = new EndpointState(hbState);
        newState.markAlive();
        EndpointState oldState = endpointStateMap.putIfAbsent(addr, newState);
        EndpointState localState = oldState == null ? newState : oldState;

        // always add the version state
        Map<ApplicationState, VersionedValue> states = new EnumMap<>(ApplicationState.class);
        states.put(ApplicationState.NET_VERSION, StorageService.instance.valueFactory.networkVersion());
        states.put(ApplicationState.HOST_ID, StorageService.instance.valueFactory.hostId(uuid));
        localState.addApplicationStates(states);
    }

    @VisibleForTesting
    public void injectApplicationState(InetAddress endpoint, ApplicationState state, VersionedValue value)
    {
        EndpointState localState = endpointStateMap.get(endpoint);
        localState.addApplicationState(state, value);
    }

    public long getEndpointDowntime(String address) throws UnknownHostException
    {
        return getEndpointDowntime(InetAddress.getByName(address));
    }

    public int getCurrentGenerationNumber(String address) throws UnknownHostException
    {
        return getCurrentGenerationNumber(InetAddress.getByName(address));
    }

    public void addExpireTimeForEndpoint(InetAddress endpoint, long expireTime)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("adding expire time for endpoint : {} ({})", endpoint, expireTime);
        }
        expireTimeEndpointMap.put(endpoint, expireTime);
    }

    public static long computeExpireTime()
    {
        return System.currentTimeMillis() + Gossiper.aVeryLongTime;
    }

    public CassandraVersion getReleaseVersion(InetAddress ep)
    {
        EndpointState state = getEndpointStateForEndpoint(ep);
        if (state != null)
        {
            VersionedValue applicationState = state.getApplicationState(ApplicationState.RELEASE_VERSION);
            if (applicationState != null)
                return new CassandraVersion(applicationState.value);
        }
        return null;
    }

    public static void markReceivedFirstGossipMessage()
    {
        receivedFirstGossipMessage = true;
    }

    public static void markProcessedFirstGossipMessage()
    {
        processedFirstGossipMessage = true;
    }

    /**
     * Wait gossip to settle, it has 3 steps:
     * 1. Wait GOSSIP_SETTLE_MIN_WAIT_MS (5 seconds by default);
     * 2. Wait the first gossip message to be processed if it's ever received one;
     *      For a new node (or cassandra.load_ring_state=false), processing the first gossip message could take
     *      long time, espically for a large cluster.
     * 3. Wait the endpoints number is stable for 3 polls (3 seconds by default).
     * @return waited polls, only for unittest
     */
    public static int waitToSettle()
    {
        int totalPolls = 0;
        int forceAfter = Integer.getInteger("cassandra.skip_wait_for_gossip_to_settle", -1);
        if (forceAfter == 0)
            return totalPolls;

        final int GOSSIP_SETTLE_MIN_WAIT_MS = Integer.getInteger("cassandra.gossip_settle_min_wait_ms", 5000);
        final int GOSSIP_SETTLE_POLL_INTERVAL_MS = Integer.getInteger("cassandra.gossip_settle_poll_interval_ms", 1000);
        final int GOSSIP_SETTLE_POLL_SUCCESSES_REQUIRED = Integer.getInteger("cassandra.gossip_settle_poll_sucesses_required", 3);
        final int GOSSIP_SETTLE_MESSAGE_PROCESSING_MAX_POLLS = Integer.getInteger("cassandra.gossip_settle_message_processing_max_polls", 300);

        logger.info("Waiting for gossip to settle...");
        Uninterruptibles.sleepUninterruptibly(GOSSIP_SETTLE_MIN_WAIT_MS, TimeUnit.MILLISECONDS);

        int numPolls = 0;
        if (receivedFirstGossipMessage)
        {
            while (!processedFirstGossipMessage)
            {
                if (numPolls >= GOSSIP_SETTLE_MESSAGE_PROCESSING_MAX_POLLS)
                {
                    logger.error("The first Gossip message processing is not finished after {} polls", numPolls);
                    break;
                }
                Uninterruptibles.sleepUninterruptibly(GOSSIP_SETTLE_POLL_INTERVAL_MS, TimeUnit.MILLISECONDS);
                numPolls++;
            }
            logger.info("Waited Gossip message processing for {} polls", numPolls);
        }
        else
        {
            logger.info("No Gossip message received");
        }
        totalPolls += numPolls;

        numPolls = 0;
        int numOkay = 0;
        int epSize = Gossiper.instance.getEndpointStates().size();
        while (numOkay < GOSSIP_SETTLE_POLL_SUCCESSES_REQUIRED)
        {
            Uninterruptibles.sleepUninterruptibly(GOSSIP_SETTLE_POLL_INTERVAL_MS, TimeUnit.MILLISECONDS);
            int currentSize = Gossiper.instance.getEndpointStates().size();
            numPolls++;
            if (currentSize == epSize)
            {
                logger.debug("Gossip looks settled.");
                numOkay++;
            }
            else
            {
                logger.info("Gossip not settled after {} polls.", numPolls);
                numOkay = 0;
            }
            epSize = currentSize;
            if (forceAfter > 0 && numPolls > forceAfter)
            {
                logger.warn("Gossip not settled but startup forced by cassandra.skip_wait_for_gossip_to_settle. Gossip total polls: {}",
                            numPolls);
                break;
            }
        }
        if (numPolls > GOSSIP_SETTLE_POLL_SUCCESSES_REQUIRED)
            logger.info("Gossip settled after {} extra polls; proceeding", numPolls - GOSSIP_SETTLE_POLL_SUCCESSES_REQUIRED);
        else
            logger.info("No gossip backlog; proceeding");

        totalPolls += numPolls;
        return totalPolls;
    }

    @VisibleForTesting
    public void unsafeClearStateForTest()
    {
        endpointStateMap.clear();
    }

}
