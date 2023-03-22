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

package org.apache.cassandra.rocksdb.streaming;

import java.util.Arrays;

import org.junit.Test;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.rocksdb.RocksDBCF;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.RocksDBUtils;
import org.rocksdb.RocksDB;

public class RocksDBDeleteRangeTest extends RocksDBStreamTestBase
{
    @Test
    public void testDeleteRange() throws Throwable
    {
        int numberOfKeys = 1000;

        // Create table one and insert some data.
        createTable("CREATE TABLE %s (p TEXT, v TEXT, PRIMARY KEY (p))");
        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        for (int i = 0; i < numberOfKeys; i ++)
        {
            execute("INSERT INTO %s(p, v) values (?, ?)", "p" + i, "v" + i);
        }

        for (int i = 0; i < numberOfKeys; i ++)
        {
            assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i), row("v" + i));
        }

        // Delete token ranges from minimal token to mid token.
        IPartitioner partitioner = cfs.getPartitioner();
        Token minToken = RocksDBUtils.getMinToken(partitioner);
        Token maxToken = RocksDBUtils.getMaxToken(partitioner);
        Token midToken = partitioner.midpoint(maxToken, maxToken);
        Range<Token> toRemove = new Range<Token>(minToken, midToken);

        RocksDBEngine rocksEngine = (RocksDBEngine)cfs.engine;
        RocksDBCF db = RocksDBEngine.getRocksDBCF(cfs);
        rocksEngine.deleteRange(db, toRemove);
        db.compactRange();
        
        // Verifies that delete range works.
        for (int i = 0; i < numberOfKeys; i ++)
        {
            String key = "p" + i;
            Token token = partitioner.getToken(AsciiType.instance.decompose(key));
            if (inRanges(token, Arrays.asList(toRemove)))
            {
                assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i));
            }
            else
            {
                assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i), row("v" + i));
            }
        }
    }
}
