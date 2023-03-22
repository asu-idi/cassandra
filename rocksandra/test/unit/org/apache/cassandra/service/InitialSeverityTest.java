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

package org.apache.cassandra.service;

import java.net.InetAddress;

import org.junit.Test;

import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.gms.ApplicationState;
import org.apache.cassandra.gms.EndpointState;
import org.apache.cassandra.gms.Gossiper;
import org.apache.cassandra.gms.VersionedValue;
import org.apache.cassandra.utils.FBUtilities;

import static org.junit.Assert.assertEquals;

public class InitialSeverityTest
{
    private double getSeverityFromGossiper()
    {
        double severity = 0.0;
        InetAddress endpoint = FBUtilities.getBroadcastAddress();
        EndpointState state = Gossiper.instance.getEndpointStateForEndpoint(endpoint);
        if (state == null)
            return severity;
        VersionedValue event = state.getApplicationState(ApplicationState.SEVERITY);
        if (event == null)
            return severity;

        severity = Double.parseDouble(event.value);
        return severity;
    }

    @Test
    public void testInitSeverity() throws Exception
    {
        System.setProperty("cassandra.initial_severity", "1000");
        SchemaLoader.mkdirs();
        SchemaLoader.cleanup();
        SchemaLoader.prepareServer();
        StorageService.instance.initServer(0);
        assertEquals(1000, getSeverityFromGossiper(), 1);

    }
}
