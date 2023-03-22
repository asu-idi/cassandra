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

public class BooleanRowKeyTest
{
    @Test
    public void roundtripSerialization() throws Exception
    {
        assertEquals(true, roundtrip(true));
        assertEquals(false, roundtrip(false));
    }

    private Boolean roundtrip(Boolean b) throws IOException
    {
        BooleanRowKey rowKey = new BooleanRowKey();
        byte[] serialized = rowKey.serialize(b);
        return (Boolean) rowKey.deserialize(serialized);
    }
}