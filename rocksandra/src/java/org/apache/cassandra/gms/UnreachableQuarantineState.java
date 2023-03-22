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

import java.util.function.BiFunction;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.metrics.GossipMetrics;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.utils.QuinFunction;

/**
 * This is a state machine that transitions a node along the unreachable quarantine states.
 * The goal is to prevent a coordinator from throwing unavailable exceptions as it recovers from a partitioned state,
 * such as a network outage. During such an outage, the node's view of the ring is one that reflects an entirely
 * unreachable ring. Upon network recovery, the node may take time to discover all endpoints, especially in large
 * clusters. However, the node will start receiving traffic as soon as one of its neighbors discovers the UP state of
 * the node, and may consequently throw unavailable exceptions as it hasn't yet discovered all endpoints.
 * <p>
 * The quarantine implemented here will:
 * 1. Disable a node's RPC port once it discovers enough unreachable endpoints (unreachable_endpoints_ratio_threshold).
 * 2. While in quarantined state, if the node discovers enough reachable endpoints (network restored), it will wait
 * until the node has had time to discover the correct state of the ring endpoints (REACHABLE_RESET_DELAY_MS).
 * 3. If after the REACHABLE_RESET_DELAY_MS the node still sees enough reachable endpoints, it will start its RPC port.
 * <p>
 * Some states may transition to the previous state, depending on the ratio of unreachable endpoints at the moment.
 * All states may transition to DISABLED when unreachable_endpoints_ratio_threshold <= 0.
 * <p>
 * DISABLED --> INITIAL --> PREPARE_FOR_QUARANTINE --> QUARANTINE --> PREPARE_FOR_RESET --> PREPARE_FOR_RESET_SETTLE_ENDPOINTS
 *                 ^    <--                        <--      |     <--                   <--                  |
 *                 |                                        |                                                |
 *                 |                                        |                                                |
 *                 |________________________________________|________________________________________________|
 * <p>
 * This feature will be forcibly disabled for any of the following reasons:
 * 1. set unreachable_endpoints_ratio_threshold = 0
 * 2. start_rpc = false
 * 3. DisableThrift via Nodetool
 */
public class UnreachableQuarantineState
{
    private static final Logger logger = LoggerFactory.getLogger(UnreachableQuarantineState.class);

    private static final int SETTLE_ENDPOINTS_MAX_POLLS = Integer.getInteger("cassandra.quarantine_settle_endpoints_max_polls",180);
    private static final int SETTLE_ENDPOINTS_REQUIRED_POLLS = Integer.getInteger("cassandra.quarantine_settle_endpoints_required_polls",10);
    public static final int UNREACHABLE_QUARANTINE_DELAY_MS = 30000;
    public static final int UNREACHABLE_QUARANTINE_RESET_DELAY_MS = Integer.getInteger("cassandra.quarantine_reset_delay_ms",0);
    public static final int REACHABLE_RESET_DELAY_MS = getReachableResetDelay();

    public static final String STATE_NAME_DISABLED = "DISABLED";
    public static final String STATE_NAME_INITIAL = "INITIAL";
    public static final String STATE_NAME_PREPARE_FOR_QUARANTINE = "PREPARE_FOR_QUARANTINE";
    public static final String STATE_NAME_QUARANTINE = "QUARANTINE";
    public static final String STATE_NAME_PREPARE_FOR_RESET = "PREPARE_FOR_RESET";
    public static final String STATE_NAME_PREPARE_FOR_RESET_SETTLE_ENDPOINTS = "PREPARE_FOR_RESET_SETTLE_ENDPOINTS";


    private static int getReachableResetDelay()
    {
        // delay after which we assume ring has stablized
        return Math.max(StorageService.RING_DELAY, UNREACHABLE_QUARANTINE_RESET_DELAY_MS);
    }

    @VisibleForTesting
    public static BiFunction<Long, Long, State> DISABLED = (unreachableTimeMillis, reachableTimeMillis) -> {
        return new State()
        {
            @Override
            State doNext(double unreachableRatio)
            {
                GossipMetrics.unreachableQuarantineDisabled.inc();

                double unreachableThreshold = DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
                if (Double.compare(unreachableThreshold, 0) > 0 && StorageService.instance.isRPCServerRunning())
                {
                    logger.info("Unreachable endpoints threshold {} > 0.0, setting UnreachableQuarantineState to INITIAL",
                                unreachableThreshold);
                    return INITIAL.apply(0L, 0L);
                }

                return this;
            }

            @Override
            String getName()
            {
                return STATE_NAME_DISABLED;
            }

            @Override
            long getUnreachableTimeMillis()
            {
                return unreachableTimeMillis;
            }

            @Override
            long getReachableTimeMillis()
            {
                return reachableTimeMillis;
            }
        };
    };

    @VisibleForTesting
    public static BiFunction<Long, Long, State> INITIAL = (unreachableTimeMillis, reachableTimeMillis) -> {
        return new State()
        {
            @Override
            State doNext(double unreachableRatio)
            {
                GossipMetrics.unreachableQuarantineInitial.inc();

                double unreachableThreshold = DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
                if (moveToDisabled(unreachableThreshold))
                {
                    logger.info("Unreachable quarantine turned off (current threshold {}), setting UnreachableQuarantineState " +
                                "to DISABLED", unreachableThreshold);
                    return DISABLED.apply(0L, 0L);
                }

                if (Double.compare(unreachableRatio, unreachableThreshold) > 0)
                {
                    logger.info("Unreachable endpoints ratio {} has surpassed threshold {}, prepare for shut down of RPC " +
                                "server", unreachableRatio, unreachableThreshold);
                    return PREPARE_FOR_QUARANTINE.apply(System.currentTimeMillis(), reachableTimeMillis);
                }

                return this;
            }

            @Override
            String getName()
            {
                return STATE_NAME_INITIAL;
            }

            @Override
            long getUnreachableTimeMillis()
            {
                return unreachableTimeMillis;
            }

            @Override
            long getReachableTimeMillis()
            {
                return reachableTimeMillis;
            }
        };
    };

    @VisibleForTesting
    public static BiFunction<Long, Long, State> PREPARE_FOR_QUARANTINE = (unreachableTimeMillis, reachableTimeMillis) -> {
        return new State()
        {
            @Override
            State doNext(double unreachableRatio)
            {
                GossipMetrics.unreachableQuarantinePrepareForQuarantine.inc();

                double unreachableThreshold = DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
                if (moveToDisabled(unreachableThreshold))
                {
                    logger.info("Unreachable quarantine turned off (current threshold {}), setting UnreachableQuarantineState " +
                                "to DISABLED", unreachableThreshold);
                    return DISABLED.apply(0L, 0L);
                }

                if (Double.compare(unreachableRatio, unreachableThreshold) < 0)
                {
                    logger.info("Unreachable endpoints ratio {} has fallen below {}, reset quarantine", unreachableRatio,
                                unreachableThreshold);
                    return INITIAL.apply(0L, 0L);
                }

                // Still a candidate for quarantine, but need to wait delay window
                if (System.currentTimeMillis() - unreachableTimeMillis < UNREACHABLE_QUARANTINE_DELAY_MS)
                    return this;

                // Move forward with quarantine
                logger.info("Unreachable endpoints ratio {} has surpassed threshold {}, shut down RPC server", unreachableRatio
                , unreachableThreshold);
                StorageService.instance.stopRPCServer();
                return QUARANTINE.apply(unreachableTimeMillis, reachableTimeMillis);
            }

            @Override
            String getName()
            {
                return STATE_NAME_PREPARE_FOR_QUARANTINE;
            }

            @Override
            long getUnreachableTimeMillis()
            {
                return unreachableTimeMillis;
            }

            @Override
            long getReachableTimeMillis()
            {
                return reachableTimeMillis;
            }
        };
    };

    @VisibleForTesting
    public static BiFunction<Long, Long, State> QUARANTINE = (unreachableTimeMillis, reachableTimeMillis) -> {
        return new State()
        {
            @Override
            State doNext(double unreachableRatio)
            {
                GossipMetrics.unreachableQuarantineQuarantined.inc();

                double unreachableThreshold = DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
                if (moveToDisabled(unreachableThreshold))
                {
                    logger.info("Unreachable quarantine turned off (current threshold {}), setting UnreachableQuarantineState " +
                                "to DISABLED", unreachableThreshold);
                    return DISABLED.apply(0L, 0L);
                }

                if (Double.compare(unreachableRatio, unreachableThreshold) < 0)
                {
                    logger.info("Unreachable endpoints ratio {} has fallen below {}, prepare for restart of RPC server",
                                unreachableRatio, unreachableThreshold);
                    return PREPARE_FOR_RESET.apply(unreachableTimeMillis, System.currentTimeMillis());
                }

                return this;
            }

            @Override
            String getName()
            {
                return STATE_NAME_QUARANTINE;
            }

            @Override
            long getUnreachableTimeMillis()
            {
                return unreachableTimeMillis;
            }

            @Override
            long getReachableTimeMillis()
            {
                return reachableTimeMillis;
            }
        };
    };

    @VisibleForTesting
    public static BiFunction<Long, Long, State> PREPARE_FOR_RESET = (unreachableTimeMillis, reachableTimeMillis) -> {
        return new State()
        {
            @Override
            State doNext(double unreachableRatio)
            {
                GossipMetrics.unreachableQuarantinePrepareForReset.inc();

                double unreachableThreshold = DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
                if (moveToDisabled(unreachableThreshold))
                {
                    logger.info("Unreachable quarantine turned off (current threshold {}), setting UnreachableQuarantineState " +
                                "to DISABLED", unreachableThreshold);
                    return DISABLED.apply(0L, 0L);
                }

                if (Double.compare(unreachableRatio, unreachableThreshold) > 0)
                    return QUARANTINE.apply(System.currentTimeMillis(), 0L);

                // Still a candidate for reset, but need to wait delay window or achieve reachable threshold
                if (System.currentTimeMillis() - reachableTimeMillis < REACHABLE_RESET_DELAY_MS
                    || Double.compare(unreachableRatio, 1 - DatabaseDescriptor.getReachableEndpointsRatioThreshold()) > 0)
                    return this;

                // Move to settle endpoints
                if (SETTLE_ENDPOINTS_REQUIRED_POLLS > 0)
                    return PREPARE_FOR_RESET_SETTLE_ENDPOINTS.apply(unreachableTimeMillis, reachableTimeMillis,
                                                                    unreachableRatio, 0, 0);

                // Move forward with reset
                logger.info("Unreachable endpoints ratio {} has fallen below threshold {}, start RPC server", unreachableRatio,
                            unreachableThreshold);
                StorageService.instance.startRPCServer();
                return INITIAL.apply(0L, 0L);
            }

            @Override
            String getName()
            {
                return STATE_NAME_PREPARE_FOR_RESET;
            }

            @Override
            long getUnreachableTimeMillis()
            {
                return unreachableTimeMillis;
            }

            @Override
            long getReachableTimeMillis()
            {
                return reachableTimeMillis;
            }
        };
    };

    @VisibleForTesting
    public static QuinFunction<Long, Long, Double, Integer, Integer, State> PREPARE_FOR_RESET_SETTLE_ENDPOINTS =
    (unreachableTimeMillis, reachableTimeMillis, currUnreachableRatio, settleEndpointsSuccesses, settleEndpointsWaitedPolls) -> {
        return new State()
        {
            @Override
            State doNext(double unreachableRatio)
            {
                GossipMetrics.unreachableQuarantinePrepareForResetSettleEndpoints.inc();

                double unreachableThreshold = DatabaseDescriptor.getUnreachableEndpointsRatioThreshold();
                if (moveToDisabled(unreachableThreshold))
                {
                    logger.info("Unreachable quarantine turned off (current threshold {}), setting UnreachableQuarantineState " +
                                "to DISABLED",
                                unreachableThreshold);
                    return DISABLED.apply(0L, 0L);
                }

                if (Double.compare(unreachableRatio, 1 - DatabaseDescriptor.getReachableEndpointsRatioThreshold()) > 0)
                    return PREPARE_FOR_RESET.apply(unreachableTimeMillis, reachableTimeMillis);

                int waitedPolls = settleEndpointsWaitedPolls + 1;
                int successes = Double.compare(currUnreachableRatio, unreachableRatio) == 0 ? settleEndpointsSuccesses + 1 : 0;
                if (successes > 0)
                    logger.info("Unreachable endpoints ratio {} looks settled, {}/{} required successes",
                                unreachableRatio, successes, SETTLE_ENDPOINTS_REQUIRED_POLLS);
                else
                    logger.info("Unreachable endpoints ratio {} not settled after {} polls", unreachableRatio, waitedPolls);

                if (waitedPolls >= SETTLE_ENDPOINTS_MAX_POLLS
                    || successes >= SETTLE_ENDPOINTS_REQUIRED_POLLS)
                {
                    // Move forward with reset
                    if (successes < SETTLE_ENDPOINTS_REQUIRED_POLLS)
                        logger.info("Unreachable endpoints ratio {} has force-settled after {} polls, start RPC server",
                                    unreachableRatio, waitedPolls);
                    else
                        logger.info("Unreachable endpoints ratio {} has settled after {} polls, start RPC server",
                                    unreachableRatio, waitedPolls);

                    StorageService.instance.startRPCServer();
                    return INITIAL.apply(0L, 0L);
                }

                return PREPARE_FOR_RESET_SETTLE_ENDPOINTS.apply(unreachableTimeMillis, reachableTimeMillis, unreachableRatio,
                                                                successes, waitedPolls);
            }

            @Override
            String getName()
            {
                return STATE_NAME_PREPARE_FOR_RESET_SETTLE_ENDPOINTS;
            }

            @Override
            long getUnreachableTimeMillis()
            {
                return unreachableTimeMillis;
            }

            @Override
            long getReachableTimeMillis()
            {
                return reachableTimeMillis;
            }

            @Override
            int getSettleEndpointsSuccesses()
            {
                return settleEndpointsSuccesses;
            }

            @Override
            int getSettleEndpointsWaitedPolls()
            {
                return settleEndpointsWaitedPolls;
            }
        };
    };

    public static abstract class State
    {
        abstract State doNext(double unreachableRatio);

        abstract String getName();

        abstract long getUnreachableTimeMillis();

        abstract long getReachableTimeMillis();

        int getSettleEndpointsSuccesses()
        {
            return 0;
        }

        int getSettleEndpointsWaitedPolls()
        {
            return 0;
        }
    }

    private UnreachableQuarantineState()
    {
    }

    public static State initialState()
    {
        return DISABLED.apply(0L, 0L);
    }

    private static boolean moveToDisabled(double unreachableThreshold)
    {
        return Double.compare(unreachableThreshold, 0) <= 0 || !DatabaseDescriptor.startRpc();
    }
}