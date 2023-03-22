/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.cassandra.metrics;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;

import com.codahale.metrics.Meter;
import org.apache.cassandra.db.ConsistencyLevel;

import static org.apache.cassandra.metrics.CassandraMetricsRegistry.Metrics;


public class ClientRequestMetrics extends LatencyMetrics
{
    public final ConsistencyAwareExceptionMeters timeouts;
    public final ConsistencyAwareExceptionMeters unavailables;
    public final ConsistencyAwareExceptionMeters failures;

    public ClientRequestMetrics(String scope)
    {
        super("ClientRequest", scope);

        timeouts = new ConsistencyAwareExceptionMeters("Timeouts");
        unavailables = new ConsistencyAwareExceptionMeters("Unavailables");
        failures =  new ConsistencyAwareExceptionMeters("Failures");
    }

    public void release()
    {
        super.release();
        timeouts.release();
        unavailables.release();
        failures.release();
    }

    public class ConsistencyAwareExceptionMeters
    {
        private final String metricName;
        private final Meter meter;
        private final Map<ConsistencyLevel, Meter> clToMeterMap;

        public ConsistencyAwareExceptionMeters(String name)
        {
            metricName = name;
            meter = Metrics.meter(factory.createMetricName(metricName));
            clToMeterMap = Arrays.stream(ConsistencyLevel.values()).collect(Collectors.toMap(
                cl -> cl, cl -> Metrics.meter(factory.createMetricName(String.format("%s-%s", metricName, cl.toString())))
            ));
        }

        public void mark(ConsistencyLevel cl)
        {
            Meter clMeter = getCLMeter(cl);
            if (clMeter != null)
                clMeter.mark();
            meter.mark();
        }

        public void release()
        {
            Metrics.remove(factory.createMetricName(metricName));
            Arrays.stream(ConsistencyLevel.values()).forEach(cl -> Metrics.remove(
                factory.createMetricName(String.format("%s-%s", metricName, cl.toString()))
            ));
        }

        @VisibleForTesting
        protected Meter getCLMeter(ConsistencyLevel cl)
        {
            if (cl == null)
                return null;
            return clToMeterMap.get(cl);
        }

        @VisibleForTesting
        protected Meter getMeter()
        {
            return meter;
        }

        @VisibleForTesting
        protected String getMetricName()
        {
            return metricName;
        }
    }
}
