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

import static com.google.common.base.Preconditions.checkArgument;
import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool;

@Command(name = "streamingconsistencycheck",
         description = "Check consistency of a table after streaming by issuing a local full range scan for int32 partition key from [0, <number of keys>).")
public class StreamingConsistencyCheck extends NodeTool.NodeToolCmd
{
    @Arguments(usage = "<keyspace> <table> <number of keys>", description = "The keyspace, table and number of keys")
    private List<String> args = new ArrayList<>();

    @Override
    public void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 3, "streamingconsistencycheck requires keyspace table and expected number of keys");
        String ks = args.get(0);
        String cf = args.get(1);
        int numberOfKeys = Integer.parseInt(args.get(2));

        System.out.println(probe.streamingConsistencyCheck(ks, cf, numberOfKeys));
    }
}
