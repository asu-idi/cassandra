/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.hints;

import java.io.DataInput;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.junit.Test;

import org.apache.cassandra.config.Config;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.io.compress.LZ4Compressor;
import org.apache.cassandra.io.util.DataOutputBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

public class HintsDescriptorTest
{
    @Test
    public void testSerializerNormal() throws IOException
    {
        UUID hostId = UUID.randomUUID();
        int version = HintsDescriptor.CURRENT_VERSION;
        long timestamp = System.currentTimeMillis();
        ImmutableMap<String, Object> parameters =
                ImmutableMap.of("compression", (Object) ImmutableMap.of("class_name", LZ4Compressor.class.getName()));
        HintsDescriptor descriptor = new HintsDescriptor(hostId, version, timestamp, parameters);

        testSerializeDeserializeLoop(descriptor);
    }

    @Test
    public void testSerializerWithEmptyParameters() throws IOException
    {
        UUID hostId = UUID.randomUUID();
        int version = HintsDescriptor.CURRENT_VERSION;
        long timestamp = System.currentTimeMillis();
        ImmutableMap<String, Object> parameters = ImmutableMap.of();
        HintsDescriptor descriptor = new HintsDescriptor(hostId, version, timestamp, parameters);

        testSerializeDeserializeLoop(descriptor);
    }

    @Test
    public void testCorruptedDeserialize() throws IOException
    {
        UUID hostId = UUID.randomUUID();
        int version = HintsDescriptor.CURRENT_VERSION;
        long timestamp = System.currentTimeMillis();
        ImmutableMap<String, Object> parameters = ImmutableMap.of();
        HintsDescriptor descriptor = new HintsDescriptor(hostId, version, timestamp, parameters);

        byte[] bytes = serializeDescriptor(descriptor);

        // mess up the parameters size
        bytes[28] = (byte) 0xFF;
        bytes[29] = (byte) 0xFF;
        bytes[30] = (byte) 0xFF;
        bytes[31] = (byte) 0x7F;

        // attempt to deserialize
        try
        {
            deserializeDescriptor(bytes);
            fail("Deserializing the descriptor should but didn't");
        }
        catch (IOException e)
        {
            assertEquals("Hints Descriptor CRC Mismatch", e.getMessage());
        }
    }

    @Test
    @SuppressWarnings("EmptyTryBlock")
    public void testReadFromFile() throws IOException
    {
        UUID hostId = UUID.randomUUID();
        int version = HintsDescriptor.CURRENT_VERSION;
        long timestamp = System.currentTimeMillis();
        ImmutableMap<String, Object> parameters = ImmutableMap.of();
        HintsDescriptor expected = new HintsDescriptor(hostId, version, timestamp, parameters);

        File directory = Files.createTempDir();
        try
        {
            try (HintsWriter ignored = HintsWriter.create(directory, expected))
            {
            }
            HintsDescriptor actual = HintsDescriptor.readFromFile(new File(directory, expected.fileName()).toPath());
            assertEquals(expected, actual);
        }
        finally
        {
            directory.deleteOnExit();
        }
    }

    @Test
    public void testReadCorruptedFile() throws IOException
    {
        UUID hostId = UUID.randomUUID();
        int version = HintsDescriptor.CURRENT_VERSION;
        long timestamp = System.currentTimeMillis();
        ImmutableMap<String, Object> parameters = ImmutableMap.of();
        HintsDescriptor expected = new HintsDescriptor(hostId, version, timestamp, parameters);

        assertTrue(DatabaseDescriptor.getHintsFailurePolicy() == Config.HintsFailurePolicy.ignore);

        File directory = Files.createTempDir();

        try
        {
            // test read empty inavlid hints file
            PrintWriter emptyFile = new PrintWriter(Paths.get(directory.getAbsolutePath(), expected.fileName()).toString());
            emptyFile.close();

            File fp = new File(directory, expected.fileName());
            HintsDescriptor actual = HintsDescriptor.readFromFile(fp.toPath());
            assertNull(actual);
            fp.delete();

            // test read corrupted hints file
            try (HintsWriter ignored = HintsWriter.create(directory, expected))
            {
            }

            fp = new File(directory, expected.fileName());
            RandomAccessFile raf = new RandomAccessFile(fp, "rw");
            raf.seek(28);
            raf.write(0xff);
            raf.write(0xff);
            raf.write(0x7f);
            actual = HintsDescriptor.readFromFile(fp.toPath());
            assertNull(actual);
        }
        finally
        {
            directory.deleteOnExit();
        }
    }

    @Test
    public void testReadCorruptedFileDie() throws IOException
    {
        UUID hostId = UUID.randomUUID();
        int version = HintsDescriptor.CURRENT_VERSION;
        long timestamp = System.currentTimeMillis();
        ImmutableMap<String, Object> parameters = ImmutableMap.of();
        HintsDescriptor expected = new HintsDescriptor(hostId, version, timestamp, parameters);

        Config.HintsFailurePolicy oldPolicy = DatabaseDescriptor.getHintsFailurePolicy();

        // test empty invalid hints file
        File directory = Files.createTempDir();
        try
        {
            DatabaseDescriptor.setHintsFailurePolicy(Config.HintsFailurePolicy.die);
            PrintWriter emptyFile = new PrintWriter(Paths.get(directory.getAbsolutePath(), expected.fileName()).toString());
            emptyFile.close();
            HintsDescriptor.readFromFile(new File(directory, expected.fileName()).toPath());
            fail("Read an empty hints file should throw an EOFException");
        }
        catch (IOError e)
        {
            assertTrue(e.getMessage().contains("EOFException"));
        }
        finally
        {
            directory.deleteOnExit();
            DatabaseDescriptor.setHintsFailurePolicy(oldPolicy);
        }

        // test corrupted hints file
        directory = Files.createTempDir();
        try
        {
            DatabaseDescriptor.setHintsFailurePolicy(Config.HintsFailurePolicy.die);
            try (HintsWriter ignored = HintsWriter.create(directory, expected))
            {
            }

            File fp = new File(directory, expected.fileName());
            RandomAccessFile raf = new RandomAccessFile(fp, "rw");
            raf.seek(28);
            raf.write(0xff);
            raf.write(0xff);
            raf.write(0x7f);
            HintsDescriptor.readFromFile(fp.toPath());
            fail("Read an corrupted hints file should throw an crc mismatch error");
        }
        catch (IOError e)
        {
            assertTrue(e.getMessage().contains("CRC Mismatch"));
        }
        finally
        {
            directory.deleteOnExit();
            DatabaseDescriptor.setHintsFailurePolicy(oldPolicy);
        }
    }

    private static void testSerializeDeserializeLoop(HintsDescriptor descriptor) throws IOException
    {
        // serialize to a byte array
        byte[] bytes = serializeDescriptor(descriptor);
        // make sure the sizes match
        assertEquals(bytes.length, descriptor.serializedSize());
        // deserialize back
        HintsDescriptor deserializedDescriptor = deserializeDescriptor(bytes);
        // compare equality
        assertDescriptorsEqual(descriptor, deserializedDescriptor);
    }

    private static byte[] serializeDescriptor(HintsDescriptor descriptor) throws IOException
    {
        DataOutputBuffer dob = new DataOutputBuffer();
        descriptor.serialize(dob);
        return dob.toByteArray();
    }

    private static HintsDescriptor deserializeDescriptor(byte[] bytes) throws IOException
    {
        DataInput in = ByteStreams.newDataInput(bytes);
        return HintsDescriptor.deserialize(in);
    }

    private static void assertDescriptorsEqual(HintsDescriptor expected, HintsDescriptor actual)
    {
        assertNotSame(expected, actual);
        assertEquals(expected, actual);
        assertEquals(expected.hashCode(), actual.hashCode());
        assertEquals(expected.hostId, actual.hostId);
        assertEquals(expected.version, actual.version);
        assertEquals(expected.timestamp, actual.timestamp);
        assertEquals(expected.parameters, actual.parameters);
    }
}
