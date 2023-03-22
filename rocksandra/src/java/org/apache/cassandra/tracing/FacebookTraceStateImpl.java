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
import java.util.List;
import java.util.UUID;

import com.facebook.cassandra.tracing.FacebookTraceState;
import com.facebook.cassandra.tracing.FacebookTraceStateFactoryInterface;
import com.google.common.collect.Iterables;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.JVMStabilityInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacebookTraceStateImpl extends TraceState
{
    private final FacebookTraceState facebookTraceState;

    protected static final Logger logger = LoggerFactory.getLogger(Tracing.class);

    FacebookTraceState initialFacebookTraceState(UUID traceUuid, FacebookTraceStateFactoryInterface factory)
    {
        if (factory == null) {
            return null;
        }

        return factory.create(ByteBufferUtil.bytes(traceUuid));
    }

    public FacebookTraceStateImpl(InetAddress localAddress, UUID traceUuid, Tracing.TraceType traceType,
                                  FacebookTraceStateFactoryInterface factory)
    {
        super(localAddress, traceUuid, traceType);
        facebookTraceState = initialFacebookTraceState(traceUuid, factory);
        facebookTraceState.begin(localAddress.toString(),
                                 traceType.name(),
                                 Thread.currentThread().getName());
    }

    public FacebookTraceStateImpl(InetAddress localAddress, UUID traceUuid, Tracing.TraceType traceType, String edgeId,
                                  FacebookTraceStateFactoryInterface factory)
    {
        super(localAddress, traceUuid, traceType);
        facebookTraceState = initialFacebookTraceState(traceUuid, factory);
        facebookTraceState.beginAndSetEdge(localAddress.toString(),
                                           traceType.name(),
                                           Thread.currentThread().getName(),
                                           edgeId);
    }

    public String getOutEdgeId(Iterable<InetAddress> targets)
    {
        if (facebookTraceState == null)
            return null;

        String [] targetStr = new String[Iterables.size(targets)];
        int idx = 0;
        for (InetAddress target: targets)
        {
            targetStr[0] = target.toString();
        }
        return facebookTraceState.getOutEdgeId(targetStr);
    }

    public void addMetadata(String region, String queryType, String keyspaceName, List<String> cfNames)
    {
        if (facebookTraceState == null)
            return;

        facebookTraceState.addMetadata(region, queryType, keyspaceName, cfNames);
    }


    protected void traceImpl(String message)
    {
        if (facebookTraceState == null)
            return;

        final String threadName = Thread.currentThread().getName();
        facebookTraceState.traceImpl(message, threadName);
    }
}
