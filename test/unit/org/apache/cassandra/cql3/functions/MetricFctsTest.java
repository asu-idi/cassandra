package org.apache.cassandra.cql3.functions;

import org.apache.cassandra.db.marshal.DoubleType;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Collections;

import static org.junit.Assert.*;

public class MetricFctsTest
{
    @Test
    public void testCpuLoad()
    {
        ScalarFunction function = (ScalarFunction) MetricFcts.cpuLoad;
        for (int i = 0; i < 100; i++)
        {
            ByteBuffer result = function.execute(4, Collections.emptyList());
            assertNotNull(result);
            assertEquals(8, result.remaining());
            Double cpuLoad = DoubleType.instance.compose(result);
            assertNotNull(cpuLoad);
            assertTrue(cpuLoad >= 0.0 && cpuLoad <= 100.0);
        }
    }

    @Test
    public void testMetadata()
    {
        Function function = MetricFcts.cpuLoad;
        assertTrue(function.isNative());
        assertFalse(function.isAggregate());
        assertEquals(new FunctionName("system", "cpuload"), function.name());
        assertEquals(0, function.argTypes().size());
        assertEquals(DoubleType.instance, function.returnType());
    }
}
