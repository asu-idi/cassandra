package org.apache.cassandra.rocksdb;/*
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

import java.io.IOException;
import java.util.List;

import org.rocksdb.RocksDBException;

public interface RocksDBCFMBean
{
    /*
     * Check the consistency between rocksdb and Cassandra storage engine.
     */
    public String rocksDBSanityCheck(boolean randomStartToken, long limit, boolean verbose);

    public String exportRocksDBStream(String outputFile, int limit) throws IOException, RocksDBException;

    public String ingestRocksDBStream(String inputFile) throws IOException, RocksDBException;

    public List<String> getRocksDBProperty(String property, RocksCFName cf);

    public void createSnapshot(String tag) throws IOException;

    public void clearSnapshot(String tag);

    String dumpPartition(String partitionKey, int limit);
    String dumpPartitionMetaData(String partitionKey);

    /*
     * Check the consistency of this table after streaming.
     */
    public String streamingConsistencyCheck(int expectedNumKeys);
}
