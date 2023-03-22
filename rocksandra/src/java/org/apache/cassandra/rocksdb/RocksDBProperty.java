package org.apache.cassandra.rocksdb;

import java.util.List;

import org.rocksdb.RocksDBException;

public class RocksDBProperty
{
    public static int getNumberOfSstablesByLevel(RocksDBCF rocksDBCF, int level) throws RocksDBException
    {
        List<String> rocksDBProperties = rocksDBCF.getProperty("rocksdb.num-files-at-level" + level);
        return rocksDBProperties.stream().mapToInt(Integer::parseInt).sum();
    }

    public static long getPendingCompactionBytes(RocksDBCF rocksDBCF) throws RocksDBException
    {
        List<String> rocksDBProperties = rocksDBCF.getProperty("rocksdb.estimate-pending-compaction-bytes");
        return sumPropertyLongValues(rocksDBProperties);
    }

    public static long getEstimatedLiveDataSize(RocksDBCF rocksDBCF) throws RocksDBException
    {
        List<String> rocksDBProperties = rocksDBCF.getProperty("rocksdb.estimate-live-data-size");
        return sumPropertyLongValues(rocksDBProperties);
    }

    public static long getEstimatedNumKeys(RocksDBCF rocksDBCF) throws RocksDBException
    {
        List<String> rocksDBProperties = rocksDBCF.getProperty("rocksdb.estimate-num-keys");
        return sumPropertyLongValues(rocksDBProperties);
    }

    private static long sumPropertyLongValues(List<String> rocksDBProperties)
    {
       return rocksDBProperties.stream().mapToLong(Long::parseLong).sum();
    }
}
