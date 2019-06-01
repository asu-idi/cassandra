/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.cassandra.net;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.service.StorageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessagingServiceTest
{
    private final MessagingService messagingService = MessagingService.test();

    private static int metricScopeId = 0;

    @Before
    public void before() {
        messagingService.resetDroppedMessagesMap(Integer.toString(metricScopeId++));;
    }

    @Test
    public void testDroppedMessages()
    {
        MessagingService.Verb verb = MessagingService.Verb.READ;

        for (int i = 0; i < 5000; i++)
            messagingService.incrementDroppedMessages(verb, i % 2 == 0);

        List<String> logs = messagingService.getDroppedMessagesLogs();
        assertEquals(1, logs.size());
        assertEquals("READ messages were dropped in last 5000 ms: 2500 for internal timeout and 2500 for cross node timeout", logs.get(0));
        assertEquals(5000, (int)messagingService.getDroppedMessages().get(verb.toString()));

        logs = messagingService.getDroppedMessagesLogs();
        assertEquals(0, logs.size());

        for (int i = 0; i < 2500; i++)
            messagingService.incrementDroppedMessages(verb, i % 2 == 0);

        logs = messagingService.getDroppedMessagesLogs();
        assertEquals("READ messages were dropped in last 5000 ms: 1250 for internal timeout and 1250 for cross node timeout", logs.get(0));
        assertEquals(7500, (int)messagingService.getDroppedMessages().get(verb.toString()));
    }

    static
    {
        // make the MessagingServiceSettle test faster
        System.setProperty("cassandra.messaging_service_settle_poll_interval_ms", "100");
    }

    @Test
    public void testMessagingServiceSettle() throws Exception
    {
        final int NO_CONN_WAIT = 6;
        SchemaLoader.prepareServer();
        StorageService.instance.initServer();

        // if no new connection, wait for 4 polls
        int wait = MessagingService.waitServerIncomingConnectionSettle(10);
        assertEquals(NO_CONN_WAIT, wait);

        // if there's ongoing new connections, wait for it
        Thread createSocket = new Thread(() -> {
            long start = System.nanoTime();
            long now = start;
            while ((now - start) >> 20 < 500)
            {
                for (MessagingService.SocketThread st : MessagingService.instance().getSocketThreads())
                {
                    st.bumpNumSocketCreatedForTest();
                }
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                now = System.nanoTime();
            }
        });
        createSocket.start();
        createSocket.join(50);

        wait = MessagingService.waitServerIncomingConnectionSettle(10);
        assertTrue(wait > NO_CONN_WAIT);

        // the max wait works
        wait = MessagingService.waitServerIncomingConnectionSettle(2);
        assertEquals(2, wait);
    }
}
