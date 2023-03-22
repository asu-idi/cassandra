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

import org.apache.cassandra.rocksdb.encoding.orderly.Bytes;
import org.apache.cassandra.rocksdb.encoding.orderly.ImmutableBytesWritable;
import org.apache.cassandra.rocksdb.encoding.orderly.RowKey;
import org.apache.cassandra.rocksdb.encoding.orderly.RowKeyUtils;

public class BooleanRowKey extends RowKey
{
    @Override
    public Class<?> getSerializedClass()
    {
        return Boolean.class;
    }

    @Override
    public int getSerializedLength(Object o) throws IOException
    {
        return 1;
    }

    @Override
    public void serialize(Object o, ImmutableBytesWritable w) throws IOException
    {
        byte[] b = w.get();
        int offset = w.getOffset();
        byte i = (byte) ((boolean) o ? 1 : 0);
        Bytes.putByte(b, offset, mask(i));
        RowKeyUtils.seek(w, Bytes.SIZEOF_BYTE);
    }

    public void skip(ImmutableBytesWritable w) throws IOException
    {
        RowKeyUtils.seek(w, Bytes.SIZEOF_BYTE);
    }

    @Override
    public Object deserialize(ImmutableBytesWritable w) throws IOException
    {
        int offset = w.getOffset();
        byte[] s = w.get();
        byte i = mask(s[offset]);
        RowKeyUtils.seek(w, 1);
        return i != 0;
    }
}

