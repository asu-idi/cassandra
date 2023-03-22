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

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.facebook.cassandra.tracing.FacebookTraceState;
import com.facebook.cassandra.tracing.FacebookTraceStateFactoryInterface;
import com.google.common.collect.ImmutableMap;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.JVMStabilityInspector;
import org.apache.cassandra.utils.UUIDGen;

import org.apache.cassandra.net.MessageIn;
import org.apache.cassandra.net.MessagingService;


public class FacebookTracingImpl extends Tracing
{
    public static final String TRACE_HEADER = "TraceSession";
    public static final String TRACE_TYPE = "TraceType";
    public static final String TRACE_A2_EDGE = "TraceA2Edge";

    public static final String QUERY_TYPE_READ = "read";
    public static final String QUERY_TYPE_WRITE = "write";

    private static final FacebookTraceStateFactoryInterface factory;

    protected final ConcurrentMap<UUID, FacebookTraceStateImpl> fbtssessions = new ConcurrentHashMap<>();

    static {
        // dynamic loading from fbcode jar.
        FacebookTraceStateFactoryInterface fbTraceStateFactory = null;
        String customTraceStateClass = System.getProperty("cassandra.custom_trace_state_class");
        if (null != customTraceStateClass)
        {
            try
            {
                fbTraceStateFactory = FBUtilities.construct(customTraceStateClass, "FacebookTraceStateFactoryInterface");
                logger.info("Using {} as trace state class", customTraceStateClass);
            } catch (Exception e)
            {
                JVMStabilityInspector.inspectThrowable(e);
                logger.error("Cannot find class {}", customTraceStateClass);
            }
        }

        factory = fbTraceStateFactory;
    }

    public void stopSessionImpl() {
    }

    public TraceState begin(final String request, final InetAddress client, final Map<String, String> parameters)
    {
        assert isTracing();
        return instance.get();
    }

    public UUID newSession(TraceType traceType)
    {
        FacebookTraceState facebookTraceState = FacebookTracingImpl.factory.create();
        return newSession(facebookTraceState.getTraceId(),
                          traceType,
                          Collections.EMPTY_MAP);
    }

    public UUID newSession(UUID sessionId, Map<String,ByteBuffer> customPayload)
    {
        FacebookTraceState facebookTraceState = FacebookTracingImpl.factory.create();
        return newSession(facebookTraceState.getTraceId(),
                          TraceType.QUERY,
                          Collections.EMPTY_MAP);
    }

    @Override
    protected TraceState newTraceState(InetAddress coordinator, UUID sessionId, TraceType traceType)
    {
        FacebookTraceStateImpl ts = new FacebookTraceStateImpl(coordinator, sessionId, traceType, factory);
        fbtssessions.put(sessionId, ts);
        return ts;
    }

    public byte[] getOutEdgeId(UUID sessionId, Iterable<InetAddress> targets)
    {
        assert isTracing();

        FacebookTraceStateImpl fbts = fbtssessions.get(sessionId);
        if (fbts != null)
        {
            return fbts.getOutEdgeId(targets).getBytes();
        }
        else
        {
            return null;
        }
    }

    public void addMetadata(UUID sessionId, String queryType, String keyspaceName, List<String> cfNames)
    {
        assert isTracing();

        FacebookTraceStateImpl fbts = fbtssessions.get(sessionId);
        if (fbts != null)
        {
            fbts.addMetadata(DatabaseDescriptor.getLocalDataCenter(), queryType, keyspaceName, cfNames);
        }
    }

    public void doneWithNonLocalSession(FacebookTraceStateImpl state)
    {
        if (state.releaseReference() == 0)
        {
            fbtssessions.remove(state.sessionId);
            sessions.remove(state.sessionId);
        }
    }

    /**
     * Determines the tracing context from a message.  Does NOT set the threadlocal state.
     *
     * @param message The internode message
     */
    @Override
    public TraceState initializeFromMessage(final MessageIn<?> message)
    {
        final byte[] sessionBytes = message.parameters.get(TRACE_HEADER);
        if (sessionBytes == null)
            return null;

        assert sessionBytes.length == 16;
        UUID sessionId = UUIDGen.getUUID(ByteBuffer.wrap(sessionBytes));
        TraceState ts = get(sessionId);
        if (ts != null && ts.acquireReference())
            return ts;

        byte[] tmpBytes;
        TraceType traceType = TraceType.QUERY;
        if ((tmpBytes = message.parameters.get(TRACE_TYPE)) != null)
            traceType = TraceType.deserialize(tmpBytes[0]);

        final byte[] edgeIdBytes = message.parameters.get(TRACE_A2_EDGE);
        String edgeId = null;
        if (edgeIdBytes != null)
            edgeId = new String(ByteBuffer.wrap(edgeIdBytes).array());

        if (message.verb == MessagingService.Verb.REQUEST_RESPONSE)
        {
            return null;
        }
        else
        {
            FacebookTraceStateImpl fbts;
            if (edgeId != null)
            {
                InetAddress localAddress = FBUtilities.getBroadcastAddress();
                fbts = new FacebookTraceStateImpl(localAddress,
                                                  sessionId,
                                                  traceType,
                                                  edgeId,
                                                  factory);
            }
            else
            {
                fbts = new FacebookTraceStateImpl(FBUtilities.getBroadcastAddress(), sessionId, traceType, factory);
            }
            sessions.put(sessionId, fbts);
            fbtssessions.put(sessionId, fbts);
            return fbts;
        }
    }

    @Override
    public Map<String, byte[]> getTraceHeaders(InetAddress target, String queryType, String ksName, List<String> cfNames)
    {
        assert isTracing();

        addMetadata(Tracing.instance.getSessionId(), queryType, ksName, cfNames);

        return ImmutableMap.of(
                TRACE_HEADER, UUIDGen.decompose(Tracing.instance.getSessionId()),
                TRACE_TYPE, new byte[] { Tracing.TraceType.serialize(Tracing.instance.getTraceType()) },
                TRACE_A2_EDGE, getOutEdgeId(Tracing.instance.getSessionId(), Arrays.asList(target)));
    }

    /**
     * Called from {@link org.apache.cassandra.net.OutboundTcpConnection} for non-local traces (traces
     * that are not initiated by local node == coordinator).
     */
    public void trace(final ByteBuffer sessionId, final String message, final int ttl)
    {
        UUID sessionUuid = UUIDGen.getUUID(sessionId);
        TraceState state = Tracing.instance.get(sessionUuid);
        if (state != null)
            state.trace(message);
    }
}
