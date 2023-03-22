package org.apache.cassandra.tools.nodetool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.airlift.command.Arguments;
import io.airlift.command.Command;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool;
import org.rocksdb.RocksDBException;

import static com.google.common.base.Preconditions.checkArgument;

@Command(name = "exportrocksdb", description = "Export Rocksdb table into a file in the format of Streaming protocol.")
public class ExportRocksDB extends NodeTool.NodeToolCmd
{
    @Arguments(usage = "<keyspace> <table> <outputfile> <row_limit>", description = "The keyspace, table, outputfile and number of rows.")
    private List<String> args = new ArrayList<>();
    @Override
    public void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 4, "rocksdbexport requires keyspace, table, outputfile and number of rows");
        String ks = args.get(0);
        String cf = args.get(1);
        String output = args.get(2);
        int limit = Integer.parseInt(args.get(3));

        try
        {
            System.out.println(probe.exportRocksDBStream(ks, cf, output, limit));
        }
        catch (IOException | RocksDBException e)
        {
            System.err.println("Failed to export rocksdb:" + e.getMessage());
        }
    }
}