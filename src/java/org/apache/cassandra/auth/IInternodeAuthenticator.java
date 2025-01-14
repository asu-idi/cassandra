/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.cassandra.auth;

import java.net.InetAddress;
import java.net.Socket;
import javax.security.cert.X509Certificate;

import org.apache.cassandra.exceptions.ConfigurationException;

public interface IInternodeAuthenticator
{
    /**
     * Decides whether or not a peer is allowed to connect to this node.
     * If this method returns false, the socket will be immediately closed.
     *
     * @param remoteAddress ip address of the connecting node.
     * @param remotePort port of the connecting node.
     * @return true if the connection should be accepted, false otherwise.
     */
    boolean authenticate(InetAddress remoteAddress, int remotePort);

    /**
     * Decides whether or not a peer is allowed to connect to this node.
     * If this method returns false, the socket will be immediately closed.
     *
     * @param certificates the peer's X509 Certificate chain, if present.
     * @param remoteAddress ip address of the connecting node.
     * @param remotePort port of the connecting node.
     * @return true if the connection should be accepted, false otherwise.
     */
    default boolean authenticate(X509Certificate[] certificates, InetAddress remoteAddress, int remotePort) {
        return authenticate(remoteAddress, remotePort);
    }

    /**
     * Validates configuration of IInternodeAuthenticator implementation (if configurable).
     *
     * @throws ConfigurationException when there is a configuration error.
     */
    void validateConfiguration() throws ConfigurationException;
}
