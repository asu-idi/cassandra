package org.apache.cassandra.cql3.functions;

import com.google.common.collect.ImmutableList;
import com.sun.management.OperatingSystemMXBean;
import org.apache.cassandra.db.marshal.DoubleType;
import org.apache.cassandra.transport.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

public abstract class MetricFcts
{
    public static Logger logger = LoggerFactory.getLogger(MetricFcts.class);

    private static final MBeanServerConnection MBEAN_SERVER_CONNECTION = ManagementFactory.getPlatformMBeanServer();
    private static final OperatingSystemMXBean OS_MBEAN = createOperatingSystemMXBean();

    public static Collection<Function> all()
    {
        return ImmutableList.of(cpuLoad);
    }

    public static final Function cpuLoad = new NativeScalarFunction("cpuload", DoubleType.instance)
    {
        public ByteBuffer execute(int protocolVersion, List<ByteBuffer> parameters)
        {
            if (OS_MBEAN == null) throw new ServerError("OperatingSystemMXBean was not properly initialized");
            double cpuLoad = OS_MBEAN.getSystemCpuLoad();
            return DoubleType.instance.decompose(Double.isNaN(cpuLoad) ? 0.0 : cpuLoad * 100);
        }
    };

    private static final OperatingSystemMXBean createOperatingSystemMXBean()
    {
        try
        {
            return ManagementFactory.newPlatformMXBeanProxy(
                    MBEAN_SERVER_CONNECTION, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        }
        catch (IOException e)
        {
            logger.warn("Could not initialize OperatingSystemMXBean", e);
            return null;
        }
    }

}
