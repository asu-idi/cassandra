package org.apache.cassandra.rocksdb;

import java.io.File;

import org.rocksdb.CompressionType;
import org.rocksdb.IndexType;

public class RocksDBConfigs
{
    public static final CompressionType COMPRESSION_TYPE = CompressionType.getCompressionType(
    System.getProperty("cassandra.rocksdb.compression_type", "lz4")
    );
    // RocksDB lowest level compression type
    public static final CompressionType BOTTOMMOST_COMPRESSION =
        CompressionType.getCompressionType(
            System.getProperty("cassandra.rocksdb.bottommost_compression", COMPRESSION_TYPE.getLibraryName())
        );
    // Stalling writes when pending compaction reach limit, default to disabled
    public static final long SOFT_PENDING_COMPACTION_BYTES_LIMIT = Long.getLong("cassandra.rocksdb.soft_pending_compaction_bytes_limit", 0);

    // Stopping writes when pending compaction reach limit, default to disabled
    public static final long HARD_PENDING_COMPACTION_BYTES_LIMIT = Long.getLong("cassandra.rocksdb.hard_pending_compaction_bytes_limit", 0);
    public static final boolean ALLOW_INGEST_BEHIND = Boolean.getBoolean("cassandra.rocksdb.allow_ingest_behind");
    public static final boolean DUMP_MALLOC_STATS = Boolean.getBoolean("cassandra.rocksdb.dump_malloc_stats");

    // Paths for storing RocksDB files.
    public static String ROCKSDB_DIR = System.getProperty("cassandra.rocksdb.dir", "/data/rocksdb");
    public static File STREAMING_TMPFILE_PATH = new File(System.getProperty("cassandra.rocksdb.stream.dir", "/data/rocksdbstream/"));

    // Max levels for RocksDB. 7 is the default value.
    public static final int MAX_LEVELS = Integer.getInteger("cassandra.rocksdb.max_levels", 7);
    // Max levels for meta column family
    public static final int META_CF_MAX_LEVELS = Integer.getInteger("cassandra.rocksdb.meta_max_levels", MAX_LEVELS);

    // Tables created in this keyspace is backed by RocksDB.
    public static String ROCKSDB_KEYSPACE = System.getProperty("cassandra.rocksdb.keyspace", "rocksdb");

    // RocksDB stopped write while number of level 0 sstables exceed this threshold. By default we set it to
    // a very large number to trade read performance for high stream throughput.
    public static final int LEVEL0_STOP_WRITES_TRIGGER = Integer.getInteger("cassandra.rocksdb.level0_stop_writes_trigger", 1024);

    // Number of rocksdb concurrent background compactions
    public static final int BACKGROUD_COMPACTIONS = Integer.getInteger("cassandra.rocksdb.background_compactions", 4);

    // deleteRateBytesPerSecond for RocksDB's delete rate limiter, which is used to control the delete rate.
    public static final long DELETE_RATE_BYTES_PER_SECOND = Long.getLong("cassandra.rocksdb.delete_rate_bytes_per_second", 100 * 1024 * 1024);

    // rocksdb block cache size, default to be 1G per rocksdb instance
    public static final int BLOCK_CACHE_SIZE_MBYTES = Integer.getInteger("cassandra.rocksdb.block_cache_size_mbytes", 1024*10);

    // block cache for partition meta data, bigger cache size help improve compaction cpu cost
    public static final int META_BLOCK_CACHE_SIZE_MBYTES = Integer.getInteger("cassandra.rocksdb.meta_block_cache_size_mbytes", 1024 * 5);

    // disable level_compaction_dynamic_level_bytes or not, defaut is false
    public static final boolean DYNAMIC_LEVEL_BYTES_DISABLED = Boolean.getBoolean("cassandra.rocksdb.disable_dynamic_level_bytes");

    // rocksdb write buffer size, default to be 4G per rocksdb instance
    public static final int WRITE_BUFFER_SIZE_MBYTES = Integer.getInteger("cassandra.rocksdb.write_buffer_size_mbytes", 512);

    // memtable size for meta cf, default 16M. Need make it small enough for flushing frequently to avoid holding off too much WAL files deletion
    public static final int META_WRITE_BUFFER_SIZE_MBYTES = Integer.getInteger("cassandra.rocksdb.meta_write_buffer_size_mbytes", 16);

    // memtable size for index cd, default 64M.
    public static final int INDEX_WRITE_BUFFER_SIZE_MBYTES = Integer.getInteger("cassandra.rocksdb.index_write_buffer_size_mbytes", 64);

    // MAX_TOTAL_WAL_SIZE_MBYTES once reached, rocksdb will flush cfs that hold off WAL deletion. Without this one stale cf make us spend
    // a lot disk space on commit logs. By default we make it 1.5 times of data cf memtable likely we will at most keep 2~3 log file.
    public static final int MAX_TOTAL_WAL_SIZE_MBYTES = Integer.getInteger("cassandra.rocksdb.max_total_wal_size_mbytes", WRITE_BUFFER_SIZE_MBYTES + WRITE_BUFFER_SIZE_MBYTES / 2);

    public static final int MAX_MBYTES_FOR_LEVEL_BASE = Integer.getInteger("cassandra.rocksdb.max_mbytes_for_level_base", 1024);

    // disable write to rocksdb commit log or not, default is false
    public static final boolean DISABLE_WRITE_TO_COMMITLOG = Boolean.getBoolean("cassandra.rocksdb.disable_write_to_commitlog");

    // Limit the number of operands in merge operator, 0 means no limit
    public static final int MERGE_OPERANDS_LIMIT = Integer.getInteger("cassandra.rocksdb.merge_operands_limit", 0);

    // number of sharded rocksdb instance in one Cassandra table
    public static int NUM_SHARD = Integer.getInteger("cassandra.rocksdb.num_shard", 1);

    public static final boolean CACHE_INDEX_AND_FILTER_BLOCKS = Boolean.getBoolean("cassandra.rocksdb.cache_index_and_filter_blocks");

    public static final boolean PIN_L0_FILTER_AND_INDEX_BLOCKS_IN_CACHE = Boolean.getBoolean("cassandra.rocksdb.pin_l0_filter_and_index_blocks_in_cache");

    // The indexing type that rocksdb will use
    public static final String TABLE_INDEX_TYPE = System.getProperty("cassandra.rocksdb.table_index_type", IndexType.kBinarySearch.toString());

    // Number of cuncurrent flushing tasks when flush/drain is called.
    public static final int FLUSH_CONCURRENCY = Integer.getInteger("cassandra.rocksdb.flush_concurrency", 8);

    // number of threads used to open sharded rocksdb instances
    public static int OPEN_CONCURRENCY = Integer.getInteger("cassandra.rocksdb.open_concurrency", NUM_SHARD);

    // how many parallel compaction jobs per table when running "nodetool compact", cannot be bigger than NUM_SHARD
    public static final int MAJOR_COMPACT_CONCURRENCY = Integer.getInteger("cassandra.rocksdb.major_compact_concurrency", NUM_SHARD);

    // enable index transactions, default is false
    public static boolean ENABLE_INDEX_TRANSACTIONS = Boolean.getBoolean("cassandra.rocksdb.enable_index_transactions");

    // bits size for bloom filter in PartitionMetaData for filtering out none-existing keys, default is 5MB per rocksdb instance.
    // (note: bloom filter setup will be skipped if there is no data in data cf or there are too much keys has been deleted)
    public static final int PARTITION_META_KEY_BLOOM_TOTAL_BITS = Integer.getInteger("cassandra.rocksdb.partition_meta_key_bloom_total_bits", 5 * 1024 * 1024 * 8);

    // data table block size -- RocksDB packs user data in blocks. When reading a key-value pair from a table file, an
    // entire block is loaded into memory. Block size is 4KB by default. Each table file contains an index that lists
    // offsets of all blocks. Increasing block_size means that the index contains fewer entries (since there are fewer
    // blocks per file) and is thus smaller. Increasing block_size decreases memory usage and space amplification
    // (better compression rate), but increases read amplification.
    public static final long DATA_BLOCK_SIZE = Long.getLong("cassandra.rocksdb.data_block_size", 4 * 1024);

    /**
     * Bloom filter configs
     */
    // disable bloom filter for the whole key, disabling that saves the BF size but increases IO. Default is false
    // If this is disabled, you may want to set DATA_ENABLE_PARTITION_TOKEN_KEY_FILTERING to true to at least enable prefix bloom filter.
    public static final boolean DATA_DISABLE_WHOLE_KEY_FILTERING = Boolean.getBoolean("cassandra.rocksdb.data_disable_whole_key_filtering");

    // enable prefix bloom filter for the token part, so the single partition scan could leverage bloom filter too. Default is flase
    // Once it's enabled, it sets useFixedLengthPrefixExtractor to 8 (the length of token key).
    public static final boolean DATA_ENABLE_PARTITION_TOKEN_KEY_FILTERING = Boolean.getBoolean("cassandra.rocksdb.data_enable_partition_token_key_filtering");

    /**
     * Streaming configs
     */
    // On the receiver side, we create a sstable and feed it to RocksDB every 512MB received.
    public static final long SSTABLE_INGEST_THRESHOLD = Long.parseLong(System.getProperty("cassandra.rocksdb.stream.sst_size", "536870912"));

    // The read buffer size while reading RocksDB and sending into output stream.
    public static final long STREAMING_READ_AHEAD_BUFFER_SIZE = Long.getLong("cassandra.rocksdb.stream.readahead_size", 10L * 1024 * 1024);

    // compaction read ahead, need be set at least 2M for slower disk.
    public static final long COMPACTION_READAHEAD_SIZE = Long.getLong("cassandra.rocksdb.compaction_readahead_size", 2L * 1024 * 1024);

    /**
     * Testing configs
     */
    // Once enabled, the writes are written to both Cassandra and RocksDB which is future used to caclulate
    // the consistency and correctness of RocksDB.
    public static boolean ROCKSDB_DOUBLE_WRITE = Boolean.getBoolean("cassandra.rocksdb.double_write");
}
