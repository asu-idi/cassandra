# Pluggable storage engine

The storage engine interface is defined at <code>org.apache.cassandra.engine.StorageEngine.java</code>,
under the interface, we implement the <code>org.apache.cassandra.rocksdb.RocksDBEngine.java</code>.

As in beta version, to enable RocksDBEngine for your keyspace, you just need to add following JVM options
when starting Cassandrda:
```
-Dcassandra.rocksdb.keyspace=<your keyspace>
-Dcassandra.rocksdb.dir=<your directory to put RocksDB data>
-Dcassandra.rocksdb.stream.dir=<your temp directory to put RocksDB streaming files>
```
Under the ```-Dcassandra.rocksdb.dir```, directories will be created automatically for each RocksDB
based keyspace and columnfamily.

## Features

Features are already supported in the beta verseion:
* Most of non-nested data types
* Table schema
* Point query
* Range query
* Mutations
* Timestamp
* TTL
* Deletions/Cell tombstones
* Streaming

Features are not supported yet:

* Multi-partition query
* Nested data types
* Counters
* Range tombstone
* Materialized views
* Secondary indexes
* SASI
* Repair


 
