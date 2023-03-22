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
import io.airlift.command.Option;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.cassandra.locator.EndpointSnitchInfoMBean;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool;
import org.apache.cassandra.tools.NodeTool.NodeToolCmd;

import com.google.common.collect.ArrayListMultimap;

@Command(name = "status", description = "Print cluster information (state, load, IDs, ...)")
public class Status extends NodeToolCmd
{
    @Arguments(usage = "[<keyspace>]", description = "The keyspace name")
    private String keyspace = null;

    @Option(title = "resolve_ip", name = {"-r", "--resolve-ip"}, description = "Show node domain names instead of IPs")
    private boolean resolveIp = false;

    @Option(title = "skip_ownership_calculation", name = {"-s", "--skip-ownership-calculation"}, description = "Skip token ownership calculation")
    private boolean skipOwnershipCalculation = false;

    private boolean isTokenPerNode = true;
    private int maxAddressLength = 0;
    private String format = null;
    private Collection<String> joiningNodes, leavingNodes, movingNodes, liveNodes, unreachableNodes;
    private Map<String, String> loadMap, hostIDMap;
    private EndpointSnitchInfoMBean epSnitchInfo;

    @Override
    public void execute(NodeProbe probe)
    {
        joiningNodes = probe.getJoiningNodes();
        leavingNodes = probe.getLeavingNodes();
        movingNodes = probe.getMovingNodes();
        loadMap = probe.getLoadMap();
        Map<String, String> tokensToEndpoints = probe.getTokenToEndpointMap();
        liveNodes = probe.getLiveNodes();
        unreachableNodes = probe.getUnreachableNodes();
        hostIDMap = probe.getHostIdMap();
        epSnitchInfo = probe.getEndpointSnitchInfoProxy();

        StringBuffer errors = new StringBuffer();

        Map<InetAddress, Float> ownerships = new HashMap<>();
        boolean hasEffectiveOwns = false;
        if (!skipOwnershipCalculation)
        {
            try
            {
                ownerships = probe.effectiveOwnership(keyspace);
                hasEffectiveOwns = true;
            }
            catch (IllegalStateException e)
            {
                ownerships = probe.getOwnership();
                errors.append("Note: " + e.getMessage() + "%n");
            }
            catch (IllegalArgumentException ex)
            {
                System.out.printf("%nError: " + ex.getMessage() + "%n");
                System.exit(1);
            }
        }

        SortedMap<String, SetHostStat> dcs = NodeTool.getOwnershipByDc(probe, resolveIp, tokensToEndpoints, ownerships);

        // More tokens than nodes (aka vnodes)?
        if (dcs.values().size() < tokensToEndpoints.keySet().size())
            isTokenPerNode = false;

        findMaxAddressLength(dcs);

        // Datacenters
        for (Map.Entry<String, SetHostStat> dc : dcs.entrySet())
        {
            String dcHeader = String.format("Datacenter: %s%n", dc.getKey());
            System.out.printf(dcHeader);
            for (int i = 0; i < (dcHeader.length() - 1); i++) System.out.print('=');
            System.out.println();

            // Legend
            System.out.println("Status=Up/Down");
            System.out.println("|/ State=Normal/Leaving/Joining/Moving");

            printNodesHeader(hasEffectiveOwns, isTokenPerNode);

            for (HostStat endpoint : dc.getValue())
                printNode(endpoint, hasEffectiveOwns, isTokenPerNode);
        }

        System.out.printf("%n" + errors.toString());
    }

    private void findMaxAddressLength(Map<String, SetHostStat> dcs)
    {
        maxAddressLength = 0;
        for (Map.Entry<String, SetHostStat> dc : dcs.entrySet())
        {
            for (HostStat stat : dc.getValue())
            {
                maxAddressLength = Math.max(maxAddressLength, stat.ipOrDns().length());
            }
        }
    }

    private void printNodesHeader(boolean hasEffectiveOwns, boolean isTokenPerNode)
    {
        String fmt = getFormat(hasEffectiveOwns, isTokenPerNode);
        String owns = hasEffectiveOwns ? "Owns (effective)" : "Owns";

        if (isTokenPerNode)
            System.out.printf(fmt, "-", "-", "Address", "Load", owns, "Host ID", "Token", "Rack");
        else
            System.out.printf(fmt, "-", "-", "Address", "Load", "Tokens", owns, "Host ID", "Rack");
    }

    private void printNode(HostStat endpoint, boolean hasEffectiveOwns, boolean isTokenPerNode)
    {
        String status, state, load, strOwns, hostID, rack, fmt;
        String epStr = endpoint.endpoint.getHostAddress();
        fmt = getFormat(hasEffectiveOwns, isTokenPerNode);
        if (liveNodes.contains(epStr)) status = "U";
        else if (unreachableNodes.contains(epStr)) status = "D";
        else status = "?";
        if (joiningNodes.contains(epStr)) state = "J";
        else if (leavingNodes.contains(epStr)) state = "L";
        else if (movingNodes.contains(epStr)) state = "M";
        else state = "N";

        load = loadMap.containsKey(epStr) ? loadMap.get(epStr) : "?";
        strOwns = endpoint.owns != null && hasEffectiveOwns ? new DecimalFormat("##0.000%").format(endpoint.owns) : "?";
        hostID = hostIDMap.get(epStr);

        try
        {
            rack = epSnitchInfo.getRack(epStr);
        } catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }

        String endpointDns = endpoint.ipOrDns();
        if (isTokenPerNode)
            System.out.printf(fmt, status, state, endpointDns, load, strOwns, hostID, endpoint.tokens.iterator().next(), rack);
        else
            System.out.printf(fmt, status, state, endpointDns, load, endpoint.tokens.size(), strOwns, hostID, rack);
    }

    private String getFormat(
            boolean hasEffectiveOwns,
            boolean isTokenPerNode)
    {
        if (format == null)
        {
            StringBuilder buf = new StringBuilder();
            String addressPlaceholder = String.format("%%-%ds  ", maxAddressLength);
            buf.append("%s%s  ");                         // status
            buf.append(addressPlaceholder);               // address
            buf.append("%-9s  ");                         // load
            if (!isTokenPerNode)
                buf.append("%-11s  ");                     // "Tokens"
            if (hasEffectiveOwns)
                buf.append("%-16s  ");                    // "Owns (effective)"
            else
                buf.append("%-6s  ");                     // "Owns
            buf.append("%-36s  ");                        // Host ID
            if (isTokenPerNode)
                buf.append("%-39s  ");                    // token
            buf.append("%s%n");                           // "Rack"

            format = buf.toString();
        }

        return format;
    }
}
