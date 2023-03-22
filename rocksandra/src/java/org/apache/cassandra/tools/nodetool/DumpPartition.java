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

package org.apache.cassandra.tools.nodetool;


import java.util.ArrayList;
import java.util.List;

import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool;

import static com.google.common.base.Preconditions.checkArgument;

@Command(name = "dumppartition", description = "Dump raw data of a partition")
public class DumpPartition extends NodeTool.NodeToolCmd
{
    @Arguments(usage = "<keyspace> <table> <partition_key>", description = "Dump raw data for a <partition_key> in <keyspace>.<table>. Partition key should be in cql literal format, e.g. \"'some_key'\" for string type key.")
    private List<String> args = new ArrayList<>();

    @Option(title = "limit", name = "-l", description = "limit how many rows to dump, defauilt is 500")
    private int limit = 500;


    protected void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 3, "dumppartition requires keyspace, table and partition_key");
        String keyspace = args.get(0);
        String table = args.get(1);
        String partitionKey = args.get(2);
        System.out.println("--- metadata:");
        System.out.println(probe.dumpPartitionMetaData(keyspace, table, partitionKey));
        System.out.println("--- rows:");
        System.out.println(probe.dumpPartition(keyspace, table, partitionKey, limit));
    }
}
