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

package org.apache.cassandra.metrics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import org.apache.cassandra.db.ConsistencyLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientRequestMetricsTest
{

    private void verifyMetricsCount(Map<String, ClientRequestMetrics.ConsistencyAwareExceptionMeters> metrics, Map<String, Map<ConsistencyLevel, Long>> expected)
    {
        Arrays.stream(ConsistencyLevel.values()).forEach(
            cl -> expected.keySet().stream().forEach(
                s -> assertEquals(expected.get(s).get(cl).longValue(), metrics.get(s).getCLMeter(cl).getCount())));
    }

    @Test
    public void testNormalMetrics()
    {
        String testScopeName = "Test";
        ClientRequestMetrics testMetrics = new ClientRequestMetrics(testScopeName);

        // Make sure the created metrics are registered
        CassandraMetricsRegistry.Metrics.getNames().forEach(bean -> assertTrue(bean.endsWith(testScopeName)));

        Map<String, ClientRequestMetrics.ConsistencyAwareExceptionMeters> metricMap = new HashMap<>();
        metricMap.put("Timeouts", testMetrics.timeouts);
        metricMap.put("Unavailables", testMetrics.unavailables);
        metricMap.put("Failures", testMetrics.failures);

        // New created meters start with 0
        Map<String, Map<ConsistencyLevel, Long>> expected = metricMap.keySet().stream().collect(Collectors.toMap(
            s -> s, s -> Arrays.stream(ConsistencyLevel.values()).collect(Collectors.toMap(cl -> cl, cl -> 0l))));

        verifyMetricsCount(metricMap, expected);

        // Bump unavailable exception for CL.ONE
        long unvailablesBefore = testMetrics.unavailables.getMeter().getCount();
        testMetrics.unavailables.mark(ConsistencyLevel.ONE);
        expected.get("Unavailables").put(ConsistencyLevel.ONE, expected.get("Unavailables").get(ConsistencyLevel.ONE) + 1);
        long unvailablesAfter = testMetrics.unavailables.getMeter().getCount();
        assertEquals(unvailablesBefore + 1, unvailablesAfter);

        verifyMetricsCount(metricMap, expected);

        // Bump unavailable exception with unkown CL
        unvailablesBefore = testMetrics.unavailables.getMeter().getCount();
        testMetrics.unavailables.mark(null);
        unvailablesAfter = testMetrics.unavailables.getMeter().getCount();
        assertEquals(unvailablesBefore + 1, unvailablesAfter);

        verifyMetricsCount(metricMap, expected);

        // Release
        testMetrics.release();
        assertTrue(CassandraMetricsRegistry.Metrics.getNames().size() == 0);
    }
}
