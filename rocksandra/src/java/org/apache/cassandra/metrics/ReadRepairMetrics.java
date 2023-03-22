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

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.net.MessagingService;
import org.apache.cassandra.utils.FBUtilities;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.cassandra.metrics.CassandraMetricsRegistry.Metrics;

/**
 * Metrics related to Read Repair.
 */
public class ReadRepairMetrics
{
    private static final MetricNameFactory factory = new DefaultNameFactory("ReadRepair");
    private static final String fullDataQueryKey = "digestmismatchread.fulldataquery";
    private static final String digestMismatchRepairWriteKey = "digestmismatchread.repairwrite";

    private static final ConcurrentHashMap<String, Counter> digestMismatchFullDataQueryCounter = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Counter> digestMismatchRepairWriteQueryCounter = new ConcurrentHashMap<>();

    public static final Meter repairedBlocking = Metrics.meter(factory.createMetricName("RepairedBlocking"));
    public static final Meter repairedBackground = Metrics.meter(factory.createMetricName("RepairedBackground"));
    public static final Meter attempted = Metrics.meter(factory.createMetricName("Attempted"));

    public static void addFullDataQueryCounter()
    {
        Counter counter = digestMismatchFullDataQueryCounter.get(fullDataQueryKey);
        if (counter == null)
        {
            counter = digestMismatchFullDataQueryCounter.computeIfAbsent(fullDataQueryKey, k -> Metrics.counter(factory.createMetricName(fullDataQueryKey)));
        }
        counter.inc();
    }

    public static void addReadRepairWriteCounter(InetAddress remote)
    {
        String remoteDC = DatabaseDescriptor.getEndpointSnitch().getDatacenter(remote);

        Counter counter = digestMismatchRepairWriteQueryCounter.get(remoteDC);
        if (counter == null)
        {
            String localDC = DatabaseDescriptor.getEndpointSnitch().getDatacenter(FBUtilities.getBroadcastAddress());
            final String metricKey = digestMismatchRepairWriteKey + "." + localDC + "." + remoteDC;
            counter = digestMismatchRepairWriteQueryCounter.computeIfAbsent(remoteDC, k -> Metrics.counter(factory.createMetricName(metricKey)));
        }
        counter.inc();
    }
}