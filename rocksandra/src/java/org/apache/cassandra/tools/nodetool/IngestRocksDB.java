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

@Command(name = "ingestrocksdb", description = "Ingest RocksDB from a file in the format of Streaming protocol.")
public class IngestRocksDB extends NodeTool.NodeToolCmd
{
    @Arguments(usage = "<keyspace> <table> <inputfile>", description = "The keyspace, table and inputfile.")
    private List<String> args = new ArrayList<>();
    @Override
    public void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 3, "rocksdbexport requires keyspace, table and inputfile");
        String ks = args.get(0);
        String cf = args.get(1);
        String input = args.get(2);

        try
        {
            System.out.println(probe.ingestRocksDBStream(ks, cf, input));
        }
        catch (IOException | RocksDBException e)
        {
            System.err.println("Failed to inmport rocksdb:" + e.getMessage());
        }
    }
}