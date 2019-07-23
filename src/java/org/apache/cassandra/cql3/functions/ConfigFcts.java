package org.apache.cassandra.cql3.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Primitives;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.marshal.MapType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.transport.ServerError;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.std.NonTypedScalarSerializerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.*;

public class ConfigFcts
{
    public static Logger logger = LoggerFactory.getLogger(ConfigFcts.class);

    private static final MapType<String, String> MAP_TYPE = MapType.getInstance(UTF8Type.instance, UTF8Type.instance, false);
    private static final List<String> SENSITIVE_KEYS = getSentitiveKeys();

    private static final Config config = getConfig();
    private static final ObjectMapper mapper = createObjectMapper();

    public static Collection<Function> all()
    {
        return ImmutableList.of(cassandraConfig);
    }

    public static final Function cassandraConfig = new NativeScalarFunction("cassandraconfig", MAP_TYPE)
    {
        public ByteBuffer execute(int protocolVersion, List<ByteBuffer> parameters)
        {
            return MAP_TYPE.decompose(buildConfigMap());
        }
    };

    private static Map<String, String> buildConfigMap()
    {
        if (SENSITIVE_KEYS == null) throw new ServerError("SENSITIVE_KEYS haven't been properly initialized");
        if (config == null) throw new ServerError("Config hasn't been properly initialized");

        // If cassandraConfig() is meant to be used at higher QPS, we should properly cache reflection metadata
        Field[] fields = Config.class.getFields();
        Map<String, String> configMap = new HashMap<>();
        for (int i = 0; i < fields.length; i++)
        {
            int modifiers = fields[i].getModifiers();
            // All the configuration properties in Config are public and non static
            if (Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) continue;
            try
            {
                String name = fields[i].getName();
                Object value = fields[i].get(config);
                if (value == null)
                {
                    configMap.put(name, "null");
                }
                else if (SENSITIVE_KEYS.contains(name))
                {
                    configMap.put(name, "<REDACTED>");
                }
                else if (requiresJsonSerialization(fields[i]))
                {
                    configMap.put(name, mapper.writeValueAsString(value));
                }
                else
                {
                    configMap.put(name, value.toString());
                }
            }
            catch (Exception e)
            {
                continue;
            }
        }
        return configMap;
    }

    private static boolean requiresJsonSerialization(Field field)
    {
        Class<?> fieldClass = field.getType();
        return !(fieldClass.isPrimitive() || Primitives.isWrapperType(fieldClass) || fieldClass.isEnum() || fieldClass == String.class);
    }

    private static List<String> getSentitiveKeys()
    {
        return getPrivateStaticField(Config.class, "SENSITIVE_KEYS");
    }

    private static Config getConfig()
    {
        return getPrivateStaticField(DatabaseDescriptor.class, "conf");
    }

    private static final <T> T getPrivateStaticField(Class<?> holdingClass, String fieldName)
    {
        try
        {
            Field field = holdingClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(null);

        }
        catch (IllegalAccessException | NoSuchFieldException e)
        {
            logger.warn("Could not retrieve" + fieldName + " from " + holdingClass.getSimpleName(), e);
            return null;
        }
    }

    private static ObjectMapper createObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();

        // Because CQL maps don't support dynamic types, we output a map<string, string>
        // so for consistency we configure Jackson to output primitive types as strings
        mapper.getJsonFactory().enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        SimpleModule module = new SimpleModule("booleanAsString", new Version(1, 0, 0, null));
        module.addSerializer(new NonTypedScalarSerializerBase<Boolean>(boolean.class)
        {
            @Override
            public void serialize(Boolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException
            {
                jgen.writeString(value.toString());
            }
        });
        mapper.registerModule(module);

        return mapper;
    }
}
