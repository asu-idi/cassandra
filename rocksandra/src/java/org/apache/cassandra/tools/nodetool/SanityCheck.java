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

import io.airlift.command.Option;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool.NodeToolCmd;

@Command(name = "sanitycheck", description = "Check consistency between rocksdb and cassandra storage engine")
public class SanityCheck extends NodeToolCmd
{
    @Arguments(usage = "<keyspace> <table>", description = "The keyspace and the table")
    private List<String> args = new ArrayList<>();

    @Option(name = "-r", description = "Choose a random token to start with (Default: start with the smallest token).")
    private boolean randomStartToken = false;

    @Option(name = "-l", description = "Number of rows to check (Default: 0, unlimited).")
    private long limit = 0;

    @Option(name = "-v", description = "Write detailed mismatch into logs (Default: false).")
    private boolean verbose = false;

    @Override
    public void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 2, "sanitycheck requires keyspace and table");
        String ks = args.get(0);
        String cf = args.get(1);

        System.out.println(probe.sanityCheck(ks, cf, randomStartToken, limit, verbose));
    }
}