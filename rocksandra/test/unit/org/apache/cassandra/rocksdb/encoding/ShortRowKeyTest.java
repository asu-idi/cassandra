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

package org.apache.cassandra.rocksdb.encoding;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortRowKeyTest
{
    @Test
    public void roundtripSerialization() throws Exception
    {
        assertEquals(Short.MIN_VALUE, (short) roundtrip(Short.MIN_VALUE));
        assertEquals(0, (short) roundtrip((short) 0));
        assertEquals(99, (short) roundtrip((short) 99));
        assertEquals(Short.MAX_VALUE, (short) roundtrip(Short.MAX_VALUE));
    }

    private Short roundtrip(short s) throws IOException
    {
        ShortRowKey rowKey = new ShortRowKey();
        byte[] serialized = rowKey.serialize(s);
        return (Short) rowKey.deserialize(serialized);
    }
}