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

import io.airlift.command.Arguments;
import io.airlift.command.Command;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool;

import static com.google.common.base.Preconditions.checkArgument;

@Command(name = "setreachablequarantinethreshold", description = "Define the reachable endpoints threshold to allow a quarantined host to reset itself.")
public class SetReachableQuarantineThreshold extends NodeTool.NodeToolCmd
{
    @Arguments(title = "reachable_quarantine_threshold", usage = "<value>", description = "Ratio of reachable to total endpoints, from 0 to 1 (ex: 0.85).", required = true)
    private Double reachableQuarantineThreshold = null;

    @Override
    public void execute(NodeProbe probe)
    {
        checkArgument(reachableQuarantineThreshold >= 0 && reachableQuarantineThreshold <= 1, "Reachable ratio must be between 0 and 1");
        probe.setReachableQuarantineThreshold(reachableQuarantineThreshold);
    }
}
