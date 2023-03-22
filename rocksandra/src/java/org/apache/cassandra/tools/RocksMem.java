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

package org.apache.cassandra.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import static com.google.common.base.Preconditions.checkArgument;

@Command(name = "rocksmem", description = "Print rocksdb memory usage by categories.")
public class RocksMem extends NodeTool.NodeToolCmd
{
    @Arguments(usage = "<keyspace>", description = "Print rocksdb memory usage for a given <keyspace>")
    private List<String> args = new ArrayList<>();

    protected void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 1, "rocksmem requires keyspace");
        String keyspace = args.get(0);
        if (!probe.isRocksDBBacked(keyspace)) {
            System.out.println("keyspace '" + keyspace + "' is not rocksdb backed");
            return;
        }

        for (Map.Entry<String, Long> entry: probe.rocksDBMemUsage(keyspace).entrySet()) {
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());

        }
    }
}
