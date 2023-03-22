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

package org.apache.cassandra.dht;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.apache.cassandra.OrderedJUnit4ClassRunner;
import org.apache.cassandra.locator.IEndpointSnitch;
import org.apache.cassandra.locator.RackInferringSnitch;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RangeStreamerTest
{
    static IEndpointSnitch snitch;

    @BeforeClass
    public static void setUp(){
        snitch = new RackInferringSnitch();
    }

    /**
     *
     * Assume we have real DCs {1,2,3} and user configured {2,3}.
     * As a result any endpoint from DC 2 or 3 should be included.
     */
    @Test
    public void testMultiDatacenterFilter() throws Exception
    {
        Set<String> realDCs = new HashSet<>(Arrays.asList("1", "2", "3"));
        Set<String> desiredDCs = new HashSet<>(Arrays.asList("2","3"));
        // {2, 3} is a subset of {1, 2, 3}
        RangeStreamer.MultiDatacenterFilter multiDatacenterFilter = new RangeStreamer.MultiDatacenterFilter(snitch, realDCs, desiredDCs);
        InetAddress endpointFromDC1=InetAddress.getByName("192.1.0.10");
        InetAddress endpointFromDC2=InetAddress.getByName("192.2.0.10");
        InetAddress endpointFromDC5=InetAddress.getByName("192.5.0.10");
        assertFalse(multiDatacenterFilter.shouldInclude(endpointFromDC1));
        assertTrue(multiDatacenterFilter.shouldInclude(endpointFromDC2));
        assertFalse(multiDatacenterFilter.shouldInclude(endpointFromDC5));
    }

    @Test(expected = NullPointerException.class)
    public void testMultiDatacenterFilterNullDesiredDCs()
    {
        RangeStreamer.MultiDatacenterFilter multiDatacenterFilter = new RangeStreamer.MultiDatacenterFilter(snitch, null, new HashSet<>());
    }

    @Test(expected = NullPointerException.class)
    public void testMultiDatacenterFilterNullRealDCs()
    {
        RangeStreamer.MultiDatacenterFilter multiDatacenterFilter = new RangeStreamer.MultiDatacenterFilter(snitch, new HashSet<>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiDatacenterFilterIllegalArgs()
    {
        Set<String> realDCs = new HashSet<>(Arrays.asList("1", "2", "3"));
        Set<String> desiredDCs = new HashSet<>(Arrays.asList("4","3"));
        // {4,3} is not a subset of {1,2,3}
        RangeStreamer.MultiDatacenterFilter multiDatacenterFilter = new RangeStreamer.MultiDatacenterFilter(snitch, realDCs, desiredDCs);
    }
}