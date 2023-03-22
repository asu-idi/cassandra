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

package org.apache.cassandra.rocksdb.tools;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.AbstractReadCommandBuilder;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.ConsistencyLevel;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.ReadCommand;
import org.apache.cassandra.db.SinglePartitionReadCommand;
import org.apache.cassandra.db.marshal.Int32Type;
import org.apache.cassandra.db.partitions.PartitionIterator;
import org.apache.cassandra.db.rows.RowIterator;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import org.apache.cassandra.serializers.Int32Serializer;
import org.apache.cassandra.service.StorageProxy;
import org.apache.cassandra.utils.FBUtilities;

public class StreamingConsistencyCheckUtils
{
    /**
     * Check consistency of a prepopulated table by issuing a full range scan.
     *
     * Before executing the check, one should create the table with int32 partition key, and
     * prepopulate the table's key from 0 to expectedNumberOfKeys. Then after perform several
     * operations like replacement/decommission, this function could tell if keys from 0 to expectedNumberOfKeys
     * are still exist in local data center.
     *
     * Note it doesn't make any assumption about the value, so it only check if all the key's exisitence.
     *
     * @param cfs
     * @param expectedNumberOfKeys
     * @return Missng keys.
     */
    public static Set<Integer> check(ColumnFamilyStore cfs, int expectedNumberOfKeys)
    {
        // Consistency check requires the table's partition column to be int32 type.
        assert(cfs.metadata.partitionColumns().size() == 1);
        assert(cfs.metadata.partitionColumns().iterator().next().cellValueType() instanceof Int32Type);

        Set<Integer> missingKeys = new HashSet<>();
        for (int primaryKey = 0 ; primaryKey < expectedNumberOfKeys; primaryKey ++)
        {
            ReadCommand command = new AbstractReadCommandBuilder.SinglePartitionBuilder(
                cfs,
                cfs.decorateKey(Int32Serializer.instance.serialize(primaryKey))).build();
            PartitionIterator partitionIterator = command.execute(ConsistencyLevel.LOCAL_ONE, null);
            if (! partitionIterator.hasNext() || !partitionIterator.next().hasNext())
                missingKeys.add(primaryKey);
        }
        return missingKeys;
    }

    public static String generateDetailedInconsistencyReport(ColumnFamilyStore cfs, Set<Integer> missingKeys)
    {
        if (missingKeys.size() == 0)
            return "Consistent";

        Map<InetAddress, List<Integer>> missingKeyByEndpoint = new HashMap<>();
        for (int key : missingKeys)
        {
            InetAddress endpoint = getLocalEndpointOfKey(cfs, key, DatabaseDescriptor.getEndpointSnitch().getDatacenter(FBUtilities.getBroadcastAddress()));
            if (!missingKeyByEndpoint.containsKey(endpoint))
                missingKeyByEndpoint.put(endpoint, new ArrayList<>());
            missingKeyByEndpoint.get(endpoint).add(key);
        }
        StringBuilder result = new StringBuilder();
        result.append("Inconsistent, ").append(missingKeys.size()).append(" keys missing.\n");
        for (InetAddress address : missingKeyByEndpoint.keySet())
        {
            result.append(" Node:").append(address.toString()).append(" missing keys:\n");
            result.append("    ").append(missingKeyByEndpoint.get(address).stream()
                                                             .map(n -> n.toString())
                                                             .collect(Collectors.joining(","))).append("\n");
        }
        return result.toString();
    }

    public static InetAddress getLocalEndpointOfKey(ColumnFamilyStore cfs, int key, String localDc)
    {
        DecoratedKey decoratedKey = cfs.metadata.partitioner.decorateKey(Int32Serializer.instance.serialize(key));
        List<InetAddress> addresses = StorageProxy.getLiveSortedEndpoints(cfs.keyspace, decoratedKey);
        for (InetAddress addr : addresses)
        {
            if (DatabaseDescriptor.getEndpointSnitch().getDatacenter(addr).equals(localDc))
                return addr;
        }
        return null;
    }

    public static String checkAndGenerateReport(ColumnFamilyStore cfs, int expectedNumberOfKeys)
    {
        return generateDetailedInconsistencyReport(cfs, check(cfs, expectedNumberOfKeys));
    }
}
