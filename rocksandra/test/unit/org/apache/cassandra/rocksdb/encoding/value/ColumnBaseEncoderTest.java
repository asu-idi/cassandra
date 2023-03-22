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

package org.apache.cassandra.rocksdb.encoding.value;

import org.apache.cassandra.db.LegacyLayout;
import org.apache.cassandra.rocksdb.encoding.value.ColumnBaseEncoder;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class ColumnBaseEncoderTest
{
    @Test
    public void test()
    {
        ByteBuffer buff = ByteBuffer.allocate(ColumnBaseEncoder.getEncodedSize());
        ColumnBaseEncoder.encode((byte) 1, (byte) LegacyLayout.DELETION_MASK, buff);
        buff.flip();
        assertEquals(buff.get(), LegacyLayout.DELETION_MASK);
        assertEquals(buff.get(), 1);
    }
}
