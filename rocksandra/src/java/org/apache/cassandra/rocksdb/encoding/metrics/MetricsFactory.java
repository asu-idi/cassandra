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

package org.apache.cassandra.rocksdb.encoding.metrics;

import java.io.OutputStream;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.UniformSnapshot;
import org.rocksdb.HistogramData;
import org.rocksdb.HistogramType;
import org.rocksdb.Statistics;
import org.rocksdb.TickerType;

public class MetricsFactory
{
    private static final Snapshot EMPTY_SNAPSHOT = new UniformSnapshot(new long[0]);

    public static Histogram createHistogram(Statistics stats, HistogramType type)
    {
        return new RocksHistogram(type, stats);
    }

    public static Counter createCounter(Statistics stats, TickerType tickerType)
    {
        return new RocksCounter(tickerType, stats);
    }

    private static class RocksHistogram extends Histogram
    {
        public final HistogramType type;
        public final Statistics stats;

        public RocksHistogram(HistogramType type, Statistics stats)
        {
            super(null);
            this.type = type;
            this.stats = stats;
        }

        public Snapshot getSnapshot()
        {
            if (stats == null)
            {
                return EMPTY_SNAPSHOT;
            }

            final HistogramData histogramData = stats.getHistogramData(type);
            return new Snapshot()
            {
                public double getValue(double v)
                {
                    // Not implemented.
                    return -1;
                }

                public long[] getValues()
                {
                    // Not implemented.
                    return new long[0];
                }

                public int size()
                {
                    // Not implemented.
                    return 0;
                }

                public long getMax()
                {
                    // Not implemented.
                    return -1;
                }

                public double getMean()
                {
                    return histogramData.getAverage();
                }

                public long getMin()
                {
                    // Not implemented.
                    return -1;
                }

                public double getStdDev()
                {
                    return histogramData.getStandardDeviation();
                }

                public double getMedian()
                {
                    return histogramData.getMedian();
                }

                public double get95thPercentile()
                {

                    return histogramData.getPercentile95();
                }

                public double get99thPercentile()
                {

                    return histogramData.getPercentile99();
                }

                public void dump(OutputStream outputStream)
                {
                    // Do nothing.
                }
            };
        }
    }

    private static class RocksCounter extends Counter
    {
        private final Statistics stats;
        private final TickerType tickerType;

        public RocksCounter(TickerType tickerType, Statistics stats)
        {
            this.stats = stats;
            this.tickerType = tickerType;
        }

        public long getCount()
        {
            return stats.getTickerCount(tickerType);
        }
    }
}
