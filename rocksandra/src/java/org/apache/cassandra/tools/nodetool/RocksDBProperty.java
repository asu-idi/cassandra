package org.apache.cassandra.tools.nodetool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import io.airlift.command.Option;
import org.apache.cassandra.db.ColumnFamilyStoreMBean;
import org.apache.cassandra.rocksdb.RocksCFName;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool.NodeToolCmd;

import static com.google.common.base.Preconditions.checkArgument;

@Command(name = "rocks", description = "Print rocksdb property.")
public class RocksDBProperty extends NodeToolCmd
{
    private static final Map<String /* Property name */, String /* Description */> PROPERTIES = new HashMap<>();
    private static final String PROPERTY_PREFIX = "rocksdb.";
    static {
        PROPERTIES.put("stats",
                       "Summarize rocksdb column family stats.");
        PROPERTIES.put("sstables",
                       "Summarize rocksdb SST files.");
        PROPERTIES.put("cfstats",
                       "Summarize general columm family stats per-level over db's lifetime.");
        PROPERTIES.put("cfstats-no-file-histogram",
                       "Summarize general columm family stats per-level over db's lifetime without histogram.");
        PROPERTIES.put("cf-file-histogram",
                       "Summarize general columm family stats per-level over db's lifetime with histogram.");
        PROPERTIES.put("dbstats",
                       "Summarize general db stats.");
        PROPERTIES.put("levelstats",
                       "Summarize general level stats.");
        PROPERTIES.put("num-immutable-mem-table",
                       "Summarize number of immutable memtables that have not yet been flushed.");
        PROPERTIES.put("num-immutable-mem-table-flushed",
                       "Summarize number of immutable memtables that have already been flushed.");
        PROPERTIES.put("mem-table-flush-pending",
                       "Returns 1 if a memtable flush is  pending; otherwise, returns 0.");
        PROPERTIES.put("num-running-flushes",
                       "Returns the number of currently running flushes.");
        PROPERTIES.put("compaction-pending",
                       "Returns 1 if at least one compaction is pending; otherwise, returns 0.");
        PROPERTIES.put("num-running-compactions",
                       "Returns the number of currently running compactions.");
        PROPERTIES.put("background-errors",
                       "Returns accumulated number of background errors.");
        PROPERTIES.put("cur-size-active-mem-table",
                       "Returns approximate size of active memtable.");
        PROPERTIES.put("size-all-mem-tables",
                       "Returns approximate size of active unflushed memtables.");
        PROPERTIES.put("num-entries-active-mem-table",
                       "Returns total number of entries in the active memtable.");
        PROPERTIES.put("num-entries-imm-mem-tables",
                       "Returns total number of entries in the unflushed immutable memtables.");
        PROPERTIES.put("num-deletes-active-mem-table",
                       "Returns total number of delete entries in the active memtable.");
        PROPERTIES.put("num-deletes-imm-mem-tables",
                       "Returns total number of delete entries in the unflushed immutable memtables.");
        PROPERTIES.put("estimate-num-keys",
                       "Returns estimated number of total keys in the active and unflushed immutable memtables and storage.");
        PROPERTIES.put("estimate-table-readers-mem",
                       "Returns estimated memory used for reading SST tables, excluding memory used in block cache .");
        PROPERTIES.put("is-file-deletions-enabled",
                       "Returns 0 if deletion of obsolete files is enabled; otherwise, returns a non-zero number.");
        PROPERTIES.put("estimate-live-data-size",
                       "Returns an estimate of the amount of live data in bytes.");
        PROPERTIES.put("total-sst-files-size",
                       "Returns total size (bytes) of all SST files. WARNING: may slow down online queries if there are too many files.");
        PROPERTIES.put("base-level",
                       "Returns number of level to which L0 data will be compacted.");
        PROPERTIES.put("estimate-pending-compaction-bytes",
                       "Returns estimated total number of bytes compaction needs to rewrite to get all levels down to under target" +
                       " size. Not valid for other compactions than level-based.");
        PROPERTIES.put("aggregated-table-properties",
                       "Returns aggregated table properties of the target column family.");
        PROPERTIES.put("actual-delayed-write-rate",
                       "Returns the current actual delayed write rate. 0 means no delay.");
        PROPERTIES.put("is-write-stopped",
                       "Return 1 if write has been stopped.");

    }

    @Arguments(usage = "<property> <keyspace(optional)> <table(optional)>", description = "Get rocksdb <property> for a given <keyspace>.<table>")
    private List<String> args = new ArrayList<>();

    @Option(title = "list", name = {"-l", "--list"}, description = "List all avaliable properties")
    private boolean listProperites = false;

    @Option(title = "cf", name = {"--cf"}, description = "Show partition data properties for specified rocksdb cf, valid options are: default, index, meta")
    private String cf = "default";



    @Override
    public void execute(NodeProbe probe)
    {
        if (listProperites)
        {
            for (Map.Entry<String, String> entry : PROPERTIES.entrySet()) {
                System.out.println(entry.getKey() + ":  " + entry.getValue());
            }
            return;
        }
        checkArgument(args.size() >= 1, "rocks requires property, keyspace(optional) and table (optional)");
        String property = args.get(0);
        String keyspace = args.size() >= 2 ? args.get(1) : null;

        if (args.size() == 3)
        {
            List<String> rocksDBProperties = probe.getRocksDBProperty(keyspace, args.get(2), PROPERTY_PREFIX + property, RocksCFName.valueOf(cf.toUpperCase()));
            printRocksDBProperties(rocksDBProperties);
            return;
        }

        Iterator<Map.Entry<String, ColumnFamilyStoreMBean>> tables = probe.getColumnFamilyStoreMBeanProxies();
        while (tables.hasNext())
        {
            Map.Entry<String, ColumnFamilyStoreMBean> entry = tables.next();
            String keyspaceName = entry.getKey();
            String tableName = entry.getValue().getTableName();

            if (!probe.isRocksDBBacked(keyspaceName))
                continue;

            if (keyspace != null && !keyspaceName.equals(keyspace))
                continue;
            System.out.println("Table: " + keyspaceName + "." + tableName);
            List<String> rocksDBProperties = probe.getRocksDBProperty(keyspaceName, tableName, PROPERTY_PREFIX + property, RocksCFName.valueOf(cf.toUpperCase()));
            printRocksDBProperties(rocksDBProperties);
        }
    }

    private void printRocksDBProperties(List<String> rocksDBProperties)
    {
        int shardNumber = 0;
        for (String rocksDBProperty : rocksDBProperties)
        {
            System.out.println("Shard: " + shardNumber++);
            System.out.println(rocksDBProperty);
        }
    }
}