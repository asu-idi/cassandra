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
        StorageService.instance.registerDaemon(daemon);

        UnreachableQuarantineState.State state = UnreachableQuarantineState.initialState();
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());

        // fail to transition to INITIAL if thrift server is not running
        StorageService.instance.stopRPCServer();
        state = state.doNext(2, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());

        // transition to INITIAL
        StorageService.instance.startRPCServer();
        state = state.doNext(2, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());
        assertEquals(state.getUnreachableTimeMillis(), 0);

        // test reset to DISABLED from another state
        state = UnreachableQuarantineState.QUARANTINE.apply(System.currentTimeMillis(), 0L);
        state = state.doNext(1, 0);
        assertEquals(UnreachableQuarantineState.STATE_NAME_DISABLED, state.getName());
        assertEquals(state.getUnreachableTimeMillis(), 0);
    }

    @Test
    public void testStateInitial()
    {
        UnreachableQuarantineState.State state = UnreachableQuarantineState.INITIAL.apply(0L, 0L);
        assertEquals(state.getUnreachableTimeMillis(), 0);
        state = state.doNext(2, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_QUARANTINE, state.getName());
        assertTrue(state.getUnreachableTimeMillis() > 0);
    }

    @Test
    public void testStatePrepareForQuarantine()
    {
        CassandraDaemon daemon = new MockCassandraDaemon();
        StorageService.instance.registerDaemon(daemon);
        long now = System.currentTimeMillis();

        // test quarantine reset
        UnreachableQuarantineState.State state = UnreachableQuarantineState.PREPARE_FOR_QUARANTINE.apply(now, 0L);
        state = state.doNext(0, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());

        // test quarantine delay
        state = UnreachableQuarantineState.PREPARE_FOR_QUARANTINE.apply(now, 0L);
        state = state.doNext(2, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_QUARANTINE, state.getName());

        // test quarantine
        state = UnreachableQuarantineState.PREPARE_FOR_QUARANTINE.apply(now - UnreachableQuarantineState.UNREACHABLE_QUARANTINE_DELAY_MS - 1, 0L);
        state = state.doNext(2, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_QUARANTINE, state.getName());
        assertFalse(StorageService.instance.isRPCServerRunning());
    }

    @Test
    public void testStateQuarantine()
    {
        UnreachableQuarantineState.State state = UnreachableQuarantineState.QUARANTINE.apply(System.currentTimeMillis(), 0L);
        state = state.doNext(0, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET, state.getName());
        assertTrue(state.getReachableTimeMillis() > 0);
    }

    @Test
    public void testStatePrepareForReset()
    {
        CassandraDaemon daemon = new MockCassandraDaemon();
        StorageService.instance.registerDaemon(daemon);
        StorageService.instance.stopRPCServer();
        long now = System.currentTimeMillis();

        // test go back to quarantine state
        UnreachableQuarantineState.State state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(0L, now);
        state = state.doNext(2, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_QUARANTINE, state.getName());
        assertEquals(0, state.getReachableTimeMillis());

        // test reset delay
        state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(0L, now);
        state = state.doNext(0, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_PREPARE_FOR_RESET, state.getName());

        // test reset
        state = UnreachableQuarantineState.PREPARE_FOR_RESET.apply(now - UnreachableQuarantineState.REACHABLE_RESET_DELAY_MS - 1, 0L);
        state = state.doNext(0, 1);
        assertEquals(UnreachableQuarantineState.STATE_NAME_INITIAL, state.getName());
        assertEquals(0, state.getReachableTimeMillis());
        assertEquals(0, state.getUnreachableTimeMillis());
    }
}
