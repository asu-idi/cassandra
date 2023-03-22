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

package org.apache.cassandra.rocksdb;

import org.junit.Test;

import org.apache.cassandra.Util;
import org.apache.cassandra.dht.LocalPartitioner;

public class LocalPartitionerTest extends RocksDBTestBase
{
    @Test
    public void localPartitioner() throws Throwable
    {
        try (Util.PartitionerSwitcher ignored = new Util.PartitionerSwitcher(new LocalPartitioner(null)))
        {
            createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

            execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
            execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");

            assertRows(execute("SELECT p, c, v FROM %s WHERE p=?", "p1"),
                       row("p1", "k1", "v1"),
                       row("p1", "k2", "v2"));
        }
    }
}
