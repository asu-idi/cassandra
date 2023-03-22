package org.apache.cassandra.cql3.functions;

import org.apache.cassandra.db.marshal.AbstractType;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

public class UtilFctsTest
{
  @Test
  public void testNullify()
  {
    Collection<Function> functions = UtilFcts.all();
    for (Function function : functions)
    {
      assertEquals("system", function.name().keyspace);
      assertEquals("nullify", function.name().name);
      assertTrue(function.isNative());
      assertFalse(function.isAggregate());

      assertNotNull(function.returnType());
      AbstractType<?> type = function.returnType();

      assertEquals(1, function.argTypes().size());
      assertEquals(type, function.argTypes().get(0));

      ByteBuffer result = ((ScalarFunction) function).execute(4, Collections.emptyList());
      assertNull(result);
      result = ((ScalarFunction) function).execute(4, Collections.singletonList(ByteBuffer.wrap(new byte[]{1, 2, 3, 4})));
      assertNull(result);
    }
  }
}
