package org.apache.cassandra.rocksdb;

import org.junit.Test;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.metrics.StorageMetrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RocksDBPropertyTest extends RocksDBTestBase
{
    @Test
    public void testGetProperties() throws Throwable
    {
        createTable("CREATE TABLE %s (p text, c text, v text, PRIMARY KEY (p, c))");

        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k1", "v1");
        execute("INSERT INTO %s(p, c, v) values (?, ?, ?)", "p1", "k2", "v2");

        ColumnFamilyStore cfs = getCurrentColumnFamilyStore();
        RocksDBCF cf = RocksDBEngine.getRocksDBCF(cfs.metadata.cfId);
        cf.forceFlush();
        assertTrue(RocksDBProperty.getEstimatedLiveDataSize(cf) > 0);
        assertEquals(RocksDBProperty.getEstimatedNumKeys(cf), 2);
        assertEquals(RocksDBProperty.getNumberOfSstablesByLevel(cf, 0), 1);
        assertEquals(RocksDBProperty.getPendingCompactionBytes(cf), 0);
        assertTrue(StorageMetrics.getStorageEngineLoad() > 0);
    }
}
