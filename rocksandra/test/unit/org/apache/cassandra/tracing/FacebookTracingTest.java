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

package org.apache.cassandra.tracing;


import com.facebook.cassandra.tracing.FacebookTraceState;
import com.facebook.cassandra.tracing.FacebookTraceStateFactoryInterface;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.cassandra.utils.FBUtilities;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public final class FacebookTracingTest
{
    static int outEdgeCount;
    static int inEdgeCount;
    static int pointCount;
    static int blockCount;
    static int unitCount;
    static int addFilterCount;

    @Test
    public void testTrace() {
        outEdgeCount = 0;
        inEdgeCount = 0;
        pointCount = 0;
        blockCount = 0;
        unitCount = 0;
        addFilterCount = 0;
        System.out.println(MockTraceStateFactory.class.getName());
        System.setProperty("cassandra.custom_tracing_class", "org.apache.cassandra.tracing.FacebookTracingImpl");
        System.setProperty("cassandra.custom_trace_state_class", MockTraceStateFactory.class.getName());
        FacebookTracingImpl tracing = (FacebookTracingImpl) Tracing.instance;

        UUID sessionId = tracing.newSession(Tracing.TraceType.QUERY);
        assertNotNull(sessionId);
        assertNotNull(tracing.get(sessionId));
        assertNotNull(tracing.get());
        tracing.begin("begin", Collections.emptyMap());
        tracing.trace("aaaaaa");
        tracing.trace("bbbbbb");
        tracing.trace("cccccc");


        InetAddress localAddress = FBUtilities.getBroadcastAddress();
        tracing.getTraceHeaders(localAddress, "read", "ks", Arrays.asList("cf"));

        assertEquals(outEdgeCount, 1);
        assertEquals(inEdgeCount, 0);
        assertEquals(pointCount, 3);
        assertEquals(blockCount, 1);
        assertEquals(unitCount, 1);
        assertEquals(addFilterCount, 1);
    }

    public static class MockTraceState implements FacebookTraceState
    {
        private UUID traceId;

        public MockTraceState(ByteBuffer sessionId)
        {
            long high = sessionId.getLong();
            long low = sessionId.getLong();
            traceId = new UUID(high, low);
            FacebookTracingTest.unitCount++;
        }

        public MockTraceState()
        {
            traceId = new UUID(0, 0);
        }

        public UUID getTraceId()
        {
            return traceId;
        }

        public void begin(String localAddress, String traceType, String threadName)
        {
            FacebookTracingTest.blockCount++;
        }

        public void beginAndSetEdge(String localAddress, String traceType,
                                    String threadName, String edgeId)
        {
            FacebookTracingTest.blockCount++;
            FacebookTracingTest.inEdgeCount++;
        }

        public String getOutEdgeId(String[] targetAddresses)
        {
            FacebookTracingTest.outEdgeCount++;

            return "1111";
        }

        public void addMetadata(String region, String queryType, String ksName, List<String> cfNames)
        {
            FacebookTracingTest.addFilterCount++;
        }

        public void traceImpl(String message, String threadName)
        {
            FacebookTracingTest.pointCount++;
        }
    }

    public static class MockTraceStateFactory implements FacebookTraceStateFactoryInterface
    {
        public FacebookTraceState create(ByteBuffer rawTraceIdBytes)
        {
            return new MockTraceState(rawTraceIdBytes);
        }

        public FacebookTraceState create()
        {
            return new MockTraceState();
        }
    }
}
