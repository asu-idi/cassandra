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
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.cassandra.rocksdb.encoding.orderly.BigDecimalRowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.ImmutableBytesWritable;

public class BigIntegerRowKey extends BigDecimalRowKey
{
    public Class<?> getSerializedClass()
    {
        return BigInteger.class;
    }

    private Object toBigDecimal(Object o)
    {
        if (o == null)
        {
            return null;
        }
        return new BigDecimal((BigInteger) o, 0);
    }

    public int getSerializedLength(Object o) throws IOException
    {
        return super.getSerializedLength(toBigDecimal(o));
    }

    public void serialize(Object o, ImmutableBytesWritable w) throws IOException
    {
        super.serialize(toBigDecimal(o), w);
    }

    public void skip(ImmutableBytesWritable w) throws IOException
    {
        super.skip(w);
    }

    public Object deserialize(ImmutableBytesWritable w) throws IOException
    {
        Object bd = super.deserialize(w);
        return bd == null ? null : ((BigDecimal) bd).toBigInteger();
    }
}
