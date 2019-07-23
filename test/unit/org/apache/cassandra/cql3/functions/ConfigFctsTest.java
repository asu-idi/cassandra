package org.apache.cassandra.cql3.functions;

import org.apache.cassandra.db.marshal.MapType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigFctsTest
{
    private static final MapType<String, String> MAP_TYPE = MapType.getInstance(UTF8Type.instance, UTF8Type.instance, false);

    @Test
    public void testCassandraConfig()
    {
        ScalarFunction function = (ScalarFunction) ConfigFcts.cassandraConfig;
        ByteBuffer result = function.execute(4, Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.remaining() > 0);

        Map<String, String> config = MAP_TYPE.compose(result);
        assertTrue(config.size() > 0);

        assertEquals("<REDACTED>", config.get("server_encryption_options"));
        assertEquals("<REDACTED>", config.get("client_encryption_options"));
        assertEquals("null", config.get("broadcast_rpc_address"));
        assertEquals("false", config.get("listen_on_broadcast_address"));
        assertEquals("[]", config.get("hinted_handoff_disabled_datacenters"));
        assertEquals(
                "{\"class_name\":\"org.apache.cassandra.locator.SimpleSeedProvider\",\"parameters\":{\"seeds\":\"127.0.0.1\"}}",
                config.get("seed_provider")
        );
    }

    @Test
    public void testCassandraConfigMetadata()
    {
        Function function = ConfigFcts.cassandraConfig;
        assertTrue(function.isNative());
        assertFalse(function.isAggregate());
        assertEquals(new FunctionName("system", "cassandraconfig"), function.name());
        assertEquals(0, function.argTypes().size());
        assertEquals(MAP_TYPE, function.returnType());
    }
}
