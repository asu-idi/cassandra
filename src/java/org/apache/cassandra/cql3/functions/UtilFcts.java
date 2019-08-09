package org.apache.cassandra.cql3.functions;

import org.apache.cassandra.cql3.CQL3Type;
import org.apache.cassandra.db.marshal.AbstractType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UtilFcts
{
  public static Logger logger = LoggerFactory.getLogger(UtilFcts.class);

  public static Collection<Function> all()
  {
    // We need one nullify function instance per CQL type. Only native types are supported.
    return Arrays.stream(CQL3Type.Native.values())
            .map(type -> nullify(type.getType()))
            .collect(Collectors.toSet());
  }

  private static Function nullify(AbstractType<?> type)
  {
    return new NativeScalarFunction("nullify", type, type)
    {
      public ByteBuffer execute(int protocolVersion, List<ByteBuffer> parameters)
      {
        return null;
      }
    };
  }
}
