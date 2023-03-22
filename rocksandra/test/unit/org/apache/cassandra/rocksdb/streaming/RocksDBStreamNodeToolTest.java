package org.apache.cassandra.rocksdb.streaming;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.SinglePartitionReadCommand;
import org.apache.cassandra.rocksdb.RocksDBEngine;
import org.apache.cassandra.rocksdb.streaming.RocksDBStreamTestBase;
import org.apache.cassandra.rocksdb.tools.RowIteratorSanityCheck;
import org.apache.cassandra.rocksdb.tools.SanityCheckUtils;

import static org.junit.Assert.assertEquals;

public class RocksDBStreamNodeToolTest extends RocksDBStreamTestBase
{
    @Test
    public void testExportAndIngestSstable() throws Throwable
    {
        // Export stream from one table and ingest into another table, and check all data has been streamed.
        int numberOfRows = 100;
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p))");
        for (int i = 0; i < numberOfRows; i ++)
        {
            execute("INSERT INTO %s(p, v) values (?, ?)", "p" + i, "v" + i);
        }

        ColumnFamilyStore cfsStreamFrom = getCurrentColumnFamilyStore();
        File tempFile = File.createTempFile("streamtest-", ".tmp");
        tempFile.deleteOnExit();
        RocksDBEngine.getRocksDBCF(cfsStreamFrom).exportRocksDBStream(tempFile.getAbsolutePath(), 0);

        // Create a new table to ingest stream.
        createTable("CREATE TABLE %s (p text, v text, PRIMARY KEY (p))");
        for (int i = 0; i < numberOfRows; i ++)
        {
            // Assert no values in the new table.
            assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i));
        }


        ColumnFamilyStore cfsIngestStream = getCurrentColumnFamilyStore();
        RocksDBEngine.getRocksDBCF(cfsIngestStream).ingestRocksDBStream(tempFile.getAbsolutePath());

        for (int i = 0; i < numberOfRows; i ++)
        {
            // Verify all values has been streamed.
            assertRows(execute("SELECT v FROM %s WHERE p=?", "p" + i), row("v" + i));
        }
    }
}
