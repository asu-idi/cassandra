package org.apache.cassandra.rocksdb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

import org.junit.Test;
import org.rocksdb.CompressionType;
import org.rocksdb.IndexType;


import static junit.framework.Assert.assertEquals;


public class RocksDBConfigTest
{
    @Test
    public void CompressionTypeDefault()
    {
        RocksDBConfigs configs = new RocksDBConfigs();
        assertEquals(configs.COMPRESSION_TYPE.getValue(), CompressionType.LZ4_COMPRESSION.getValue());
        assertEquals(configs.BOTTOMMOST_COMPRESSION.getValue(), CompressionType.LZ4_COMPRESSION.getValue());
    }

    @Test
    public void testCompressionTypeWithProperty() throws Exception
    {
        // After setting the bottommost_compression and compression_type through System properties
        System.setProperty("cassandra.rocksdb.bottommost_compression", "zstd");
        System.setProperty("cassandra.rocksdb.compression_type", "zstd");
        assertEquals(getConfigField(newConfigInstance(), "BOTTOMMOST_COMPRESSION"), CompressionType.ZSTD_COMPRESSION);
        assertEquals(getConfigField(newConfigInstance(), "COMPRESSION_TYPE"), CompressionType.ZSTD_COMPRESSION);
    }

    @Test
    public void testTableIndexConfig() throws Exception
    {
        assertEquals(IndexType.kBinarySearch, callMethod(newConfigInstance(), "getTableIndexType"));
        System.setProperty("cassandra.rocksdb.table_index_type", "kHashSearch");
        assertEquals(IndexType.kHashSearch, callMethod(newConfigInstance(), "getTableIndexType"));
        System.setProperty("cassandra.rocksdb.table_index_type", "foo");
        assertEquals(IndexType.kBinarySearch, callMethod(newConfigInstance(), "getTableIndexType"));
    }

    private Object callMethod(Object configInstance, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method method = configInstance.getClass().getMethod(methodName);
        return method.invoke(configInstance);
    }

    private Object getConfigField(Object configInstance, String staticField) throws NoSuchFieldException, IllegalAccessException
    {
        Field field = configInstance.getClass().getDeclaredField(staticField);
        return field.get(configInstance);
    }

    private Object newConfigInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class<?> configClass = Class.forName(RocksDBConfigs.class.getName(),
                                             true, new TestClassLoader());
        return configClass.newInstance();
    }

    // custom class loader to reload classes
    private class TestClassLoader extends URLClassLoader
    {
        public TestClassLoader() {
            super(((URLClassLoader) getSystemClassLoader()).getURLs());
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (name.startsWith("org.apache.cassandra.rocksdb.")) {
                return super.findClass(name);
            }
            return super.loadClass(name);
        }
    }

}
