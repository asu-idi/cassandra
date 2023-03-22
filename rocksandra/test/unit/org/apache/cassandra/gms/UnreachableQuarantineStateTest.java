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

import org.junit.Test;

import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.service.CassandraDaemon;
import org.apache.cassandra.service.StorageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnreachableQuarantineStateTest
{
    static
    {
        SchemaLoader.prepareServer();
        System.setProperty("cassandra.quarantine_settle_endpoints_required_polls", "3");
        System.setProperty("cassandra.quarantine_settle_endpoints_max_polls", "3");
        System.setProperty("cassandra.quarantine_settle_endpoints_poll_delay_ms", "0");
    }

    public class MockServer implements CassandraDaemon.Server
    {
        private boolean isRunning;

        public void start()
        {
            isRunning = true;
        }

        public void stop()
        {
            isRunning = false;
        }

        public boolean isRunning()
        {
            return isRunning;
        }
    }

    public class MockCassandraDaemon extends CassandraDaemon
    {
        public final CassandraDaemon.Server thriftServer;

        MockCassandraDaemon()
        {
            thriftServer = new MockServer();
        }

        @Override
        public CassandraDaemon.Server getThriftServer()
        {
            return thriftServer;
        }
    }

    @Test
    public void testStateDisabled()
    {
        CassandraDaemon daemon = new MockCassandraDaemon();
        StorageService ss = StorageService.instance;
        ss.registerDaemon(daemon);
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(1);

        UnreachableQuarantineState.State state = UnreachableQuarantineState.initialState();
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());

        // fail to transition to INITIAL state if thrift server is not running
        ss.stopRPCServer();
        state = state.doNext(2);
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());

        // transition to INITIAL state
        ss.startRPCServer();
        state = state.doNext(2);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());
        assertEquals(state.getUnreachableTimeMillis(), 0);

        // transition to DISABLED state
        state = UnreachableQuarantineState.QUARANTINE.apply(System.currentTimeMillis(), 0L);
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(0);
        state = state.doNext(2);
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());
        assertEquals(state.getUnreachableTimeMillis(), 0);
    }

    @Test
    public void testStateInitial()
    {
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(1);
        UnreachableQuarantineState.State state = UnreachableQuarantineState.INITIAL.apply(0L, 0L);
        assertEquals(state.getUnreachableTimeMillis(), 0);
        state = state.doNext(2);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_QUARANTINE, state.getName());
        assertTrue(state.getUnreachableTimeMillis() > 0);
    }

    @Test
    public void testStatePrepareForQuarantine()
    {
        CassandraDaemon daemon = new MockCassandraDaemon();
        StorageService ss = StorageService.instance;
        ss.registerDaemon(daemon);
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(1);
        long now = System.currentTimeMillis();

        // transition to INITIAL state
        UnreachableQuarantineState.State state = UnreachableQuarantineState.PREPARE_FOR_QUARANTINE.apply(now, 0L);
        state = state.doNext(0);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());

        // test quarantine delay
        state = UnreachableQuarantineState.PREPARE_FOR_QUARANTINE.apply(now, 0L);
        state = state.doNext(2);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_QUARANTINE, state.getName());

        // transition to QUARANTINE state
        state =
        UnreachableQuarantineState.PREPARE_FOR_QUARANTINE.apply(now - UnreachableQuarantineState.UNREACHABLE_QUARANTINE_DELAY_MS - 1, 0L);
        state = state.doNext(2);
        assertEquals(UnreachableQuarantineState.STATE_NAME_QUARANTINE, state.getName());
        //assertFalse(StorageService.instance.isRPCServerRunning());
        assertFalse(ss.isRPCServerRunning());
    }

    @Test
    public void testStateQuarantine()
    {
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(1);
        UnreachableQuarantineState.State state = UnreachableQuarantineState.QUARANTINE.apply(System.currentTimeMillis(), 0L);
        state = state.doNext(0);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET, state.getName());
        assertTrue(state.getReachableTimeMillis() > 0);
    }

    @Test
    public void testStatePrepareForReset()
    {
        CassandraDaemon daemon = new MockCassandraDaemon();
        StorageService ss = StorageService.instance;
        ss.registerDaemon(daemon);
        ss.stopRPCServer();
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(3);
        long now = System.currentTimeMillis();

        // transition to QUARANTINE state
        UnreachableQuarantineState.State state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(0L, now);
        state = state.doNext(4);
        assertEquals(UnreachableQuarantineState.STATE_NAME_QUARANTINE, state.getName());
        assertEquals(0, state.getReachableTimeMillis());

        // test reset due to delay
        state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(0L, now);
        state = state.doNext(0);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET, state.getName());

        // test reset due to not achieve reachable threshold
        DatabaseDescriptor.setReachableEndpointsRatioThreshold(1);
        state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(0L,
                                                                   now - UnreachableQuarantineState.REACHABLE_RESET_DELAY_MS - 1);
        state = state.doNext(1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET, state.getName());

        // transition to PREPARE_FOR_RESET_SETTLE_ENDPOINTS state
        long pastReachableResetDelay = now - UnreachableQuarantineState.REACHABLE_RESET_DELAY_MS - 1;
        state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(0L, pastReachableResetDelay);
        state = state.doNext(0);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET_SETTLE_ENDPOINTS, state.getName());
        assertEquals(pastReachableResetDelay, state.getReachableTimeMillis());
    }

    @Test
    public void testStatePrepareForResetSettleEndpoints() throws Exception
    {
        CassandraDaemon daemon = new MockCassandraDaemon();
        StorageService ss = StorageService.instance;
        ss.registerDaemon(daemon);
        ss.stopRPCServer();
        long now = System.currentTimeMillis();

        // transition to PREPARE_FOR_RESET state
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(4);
        DatabaseDescriptor.setReachableEndpointsRatioThreshold(2);
        UnreachableQuarantineState.State state = UnreachableQuarantineState.PREPARE_FOR_RESET_SETTLE_ENDPOINTS.apply(0L, now,
                                                                                                                     0d, 0, 0);
        state = state.doNext(3);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET, state.getName());

        // transition to DISABLED state
        state = UnreachableQuarantineState.PREPARE_FOR_RESET_SETTLE_ENDPOINTS.apply(0L, now, 0d, 0, 0);
        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(0);
        state = state.doNext(0);
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());

        DatabaseDescriptor.setUnreachableEndpointsRatioThreshold(0.75); // at least 3/4
        DatabaseDescriptor.setReachableEndpointsRatioThreshold(0.25); // at least 1/4

        // first poll, unsettled
        state = UnreachableQuarantineState.PREPARE_FOR_RESET_SETTLE_ENDPOINTS.apply(0L, now, 0.5d, 0, 0);
        state = state.doNext(0.4d); // 0.4 != 0.5
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET_SETTLE_ENDPOINTS, state.getName());
        assertEquals(0, state.getSettleEndpointsSuccesses());
        assertEquals(1, state.getSettleEndpointsWaitedPolls());

        // next two polls settled, but force-settled due to max polls
        state = state.doNext(0.4d);
        assertEquals(1, state.getSettleEndpointsSuccesses());
        state = state.doNext(0.4d);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());
        assertTrue(StorageService.instance.isRPCServerRunning());

        // reset, test required polls
        ss.stopRPCServer();
        state = UnreachableQuarantineState.PREPARE_FOR_RESET_SETTLE_ENDPOINTS.apply(0L, now, 0.5d, 0, 0);
        state = state.doNext(0.5d);
        assertEquals(state.getSettleEndpointsSuccesses(), 1);
        assertEquals(state.getSettleEndpointsWaitedPolls(), 1);
        state = state.doNext(0.5d);
        state = state.doNext(0.5d);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());
        assertTrue(StorageService.instance.isRPCServerRunning());
    }
}
