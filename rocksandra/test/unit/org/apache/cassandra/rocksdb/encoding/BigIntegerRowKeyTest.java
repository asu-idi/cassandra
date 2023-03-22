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
import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BigIntegerRowKeyTest
{

    @Test
    public void roundtripSerialization() throws Exception
    {
        assertRoundTrip("0");
        assertRoundTrip("232343243242342342");
        assertRoundTrip("-232343243242342342");
        assertRoundTrip("1");
        assertRoundTrip("-1");
        assertRoundTrip("10");
        assertRoundTrip("42");
        assertRoundTrip("10000000000000000000000000000000000000000000");
        assertRoundTrip("-10000000000000000000000000000000000000000000");

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 1024; i++)
        {
            for (int j = 0; j < i; j++)
            {
                assertRoundTrip(new BigInteger(j, random));
            }
        }
    }

    private void assertRoundTrip(String val) throws IOException
    {
        assertRoundTrip(new BigInteger(val));
    }

    private void assertRoundTrip(BigInteger val) throws IOException
    {
        assertEquals(val, roundtrip(val));
    }


    private BigInteger roundtrip(BigInteger bi) throws IOException
    {
        BigIntegerRowKey row = new BigIntegerRowKey();
        byte[] serialized = row.serialize(bi);
        return (BigInteger) row.deserialize(serialized);
    }
}