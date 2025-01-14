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
package org.apache.cassandra.db.rows;

import java.io.IOException;

import com.google.common.collect.Collections2;

import net.nicoulaj.compilecommand.annotations.Inline;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.db.*;
import org.apache.cassandra.io.util.DataInputPlus;
import org.apache.cassandra.io.util.DataOutputBuffer;
import org.apache.cassandra.io.util.DataOutputPlus;
import org.apache.cassandra.utils.SearchIterator;
import org.apache.cassandra.utils.WrappedException;

/**
 * Serialize/deserialize a single Unfiltered (both on-wire and on-disk).
 *
 * The encoded format for an unfiltered is <flags>(<row>|<marker>) where:
 *
 *   <flags> is a byte (or two) whose bits are flags used by the rest of the serialization. Each
 *       flag is defined/explained below as the "Unfiltered flags" constants. One of those flags
 *       is an extension flag, and if present, trigger the rid of another byte that contains more
 *       flags. If the extension is not set, defaults are assumed for the flags of that 2nd byte.
 *   <row> is <clustering><size>[<timestamp>][<ttl>][<deletion>]<sc1>...<sci><cc1>...<ccj> where
 *       <clustering> is the row clustering as serialized by {@code Clustering.serializer} (note
 *       that static row are an exception and don't have this).
 *       <size> is the size of the whole unfiltered on disk (it's only used for sstables and is
 *       used to efficiently skip rows).
 *       <timestamp>, <ttl> and <deletion> are the row timestamp, ttl and deletion
 *       whose presence is determined by the flags. <sci> is the simple columns of the row and <ccj> the
 *       complex ones.
 *       The columns for the row are then serialized if they differ from those in the header,
 *       and each cell then follows:
 *         * Each simple column <sci> will simply be a <cell>
 *           (which might have no value, see below),
 *         * Each <ccj> will be [<delTime>]<n><cell1>...<celln> where <delTime>
 *           is the deletion for this complex column (if flags indicates it present), <n>
 *           is the vint encoded value of n, i.e. <celln>'s 1-based index, <celli>
 *           are the <cell> for this complex column
 *   <marker> is <bound><deletion> where <bound> is the marker bound as serialized
 *       by {@code Slice.Bound.serializer} and <deletion> is the marker deletion
 *       time.
 *
 *   <cell> A cell start with a 1 byte <flag>. The 2nd and third flag bits indicate if
 *       it's a deleted or expiring cell. The 4th flag indicates if the value
 *       is empty or not. The 5th and 6th indicates if the timestamp and ttl/
 *       localDeletionTime for the cell are the same than the row one (if that
 *       is the case, those are not repeated for the cell).Follows the <value>
 *       (unless it's marked empty in the flag) and a delta-encoded long <timestamp>
 *       (unless the flag tells to use the row level one).
 *       Then if it's a deleted or expiring cell a delta-encoded int <localDelTime>
 *       and if it's expiring a delta-encoded int <ttl> (unless it's an expiring cell
 *       and the ttl and localDeletionTime are indicated by the flags to be the same
 *       than the row ones, in which case none of those appears).
 */
public class UnfilteredSerializer
{
    public static final UnfilteredSerializer serializer = new UnfilteredSerializer();

    /*
     * Unfiltered flags constants.
     */
    private final static int END_OF_PARTITION     = 0x01; // Signal the end of the partition. Nothing follows a <flags> field with that flag.
    private final static int IS_MARKER            = 0x02; // Whether the encoded unfiltered is a marker or a row. All following markers applies only to rows.
    private final static int HAS_TIMESTAMP        = 0x04; // Whether the encoded row has a timestamp (i.e. if row.partitionKeyLivenessInfo().hasTimestamp() == true).
    private final static int HAS_TTL              = 0x08; // Whether the encoded row has some expiration info (i.e. if row.partitionKeyLivenessInfo().hasTTL() == true).
    private final static int HAS_DELETION         = 0x10; // Whether the encoded row has some deletion info.
    private final static int HAS_ALL_COLUMNS      = 0x20; // Whether the encoded row has all of the columns from the header present.
    private final static int HAS_COMPLEX_DELETION = 0x40; // Whether the encoded row has some complex deletion for at least one of its columns.
    private final static int EXTENSION_FLAG       = 0x80; // If present, another byte is read containing the "extended flags" above.

    /*
     * Extended flags
     */
    private final static int IS_STATIC               = 0x01; // Whether the encoded row is a static. If there is no extended flag, the row is assumed not static.
    /**
     * A shadowable tombstone cannot replace a previous row deletion otherwise it could resurrect a
     * previously deleted cell not updated by a subsequent update, SEE CASSANDRA-11500
     */
    @Deprecated
    private final static int HAS_SHADOWABLE_DELETION = 0x02; // Whether the row deletion is shadowable. If there is no extended flag (or no row deletion), the deletion is assumed not shadowable.

    public void serialize(Unfiltered unfiltered, SerializationHeader header, DataOutputPlus out, int version)
    throws IOException
    {
        assert !header.isForSSTable();
        serialize(unfiltered, header, out, 0, version);
    }

    public void serialize(Unfiltered unfiltered, SerializationHeader header, DataOutputPlus out, long previousUnfilteredSize, int version)
    throws IOException
    {
        if (unfiltered.kind() == Unfiltered.Kind.RANGE_TOMBSTONE_MARKER)
        {
            serialize((RangeTombstoneMarker) unfiltered, header, out, previousUnfilteredSize, version);
        }
        else
        {
            serialize((Row) unfiltered, header, out, previousUnfilteredSize, version);
        }
    }

    public void serializeStaticRow(Row row, SerializationHeader header, DataOutputPlus out, int version)
    throws IOException
    {
        assert row.isStatic();
        serialize(row, header, out, 0, version);
    }

    private void serialize(Row row, SerializationHeader header, DataOutputPlus out, long previousUnfilteredSize, int version)
    throws IOException
    {
        int flags = 0;
        int extendedFlags = 0;

        boolean isStatic = row.isStatic();
        Columns headerColumns = header.columns(isStatic);
        LivenessInfo pkLiveness = row.primaryKeyLivenessInfo();
        Row.Deletion deletion = row.deletion();
        boolean hasComplexDeletion = row.hasComplexDeletion();
        boolean hasAllColumns = (row.size() == headerColumns.size());
        boolean hasExtendedFlags = hasExtendedFlags(row);

        if (isStatic)
            extendedFlags |= IS_STATIC;

        if (!pkLiveness.isEmpty())
            flags |= HAS_TIMESTAMP;
        if (pkLiveness.isExpiring())
            flags |= HAS_TTL;
        if (!deletion.isLive())
        {
            flags |= HAS_DELETION;
            if (deletion.isShadowable())
                extendedFlags |= HAS_SHADOWABLE_DELETION;
        }
        if (hasComplexDeletion)
            flags |= HAS_COMPLEX_DELETION;
        if (hasAllColumns)
            flags |= HAS_ALL_COLUMNS;

        if (hasExtendedFlags)
            flags |= EXTENSION_FLAG;

        out.writeByte((byte)flags);
        if (hasExtendedFlags)
            out.writeByte((byte)extendedFlags);

        if (!isStatic)
            Clustering.serializer.serialize(row.clustering(), out, version, header.clusteringTypes());

        if (header.isForSSTable())
        {
            try (DataOutputBuffer dob = DataOutputBuffer.scratchBuffer.get())
            {
                serializeRowBody(row, flags, header, dob);

                out.writeUnsignedVInt(dob.position() + TypeSizes.sizeofUnsignedVInt(previousUnfilteredSize));
                // We write the size of the previous unfiltered to make reverse queries more efficient (and simpler).
                // This is currently not used however and using it is tbd.
                out.writeUnsignedVInt(previousUnfilteredSize);
                out.write(dob.getData(), 0, dob.getLength());
            }
        }
        else
        {
            serializeRowBody(row, flags, header, out);
        }
    }

    @Inline
    private void serializeRowBody(Row row, int flags, SerializationHeader header, DataOutputPlus out)
    throws IOException
    {
        boolean isStatic = row.isStatic();

        Columns headerColumns = header.columns(isStatic);
        LivenessInfo pkLiveness = row.primaryKeyLivenessInfo();
        Row.Deletion deletion = row.deletion();

        if ((flags & HAS_TIMESTAMP) != 0)
            header.writeTimestamp(pkLiveness.timestamp(), out);
        if ((flags & HAS_TTL) != 0)
        {
            header.writeTTL(pkLiveness.ttl(), out);
            header.writeLocalDeletionTime(pkLiveness.localExpirationTime(), out);
        }
        if ((flags & HAS_DELETION) != 0)
            header.writeDeletionTime(deletion.time(), out);

        if ((flags & HAS_ALL_COLUMNS) == 0)
            Columns.serializer.serializeSubset(Collections2.transform(row, ColumnData::column), headerColumns, out);

        SearchIterator<ColumnDefinition, ColumnDefinition> si = headerColumns.iterator();

        try
        {
            row.apply(cd -> {
                // We can obtain the column for data directly from data.column(). However, if the cell/complex data
                // originates from a sstable, the column we'll get will have the type used when the sstable was serialized,
                // and if that type have been recently altered, that may not be the type we want to serialize the column
                // with. So we use the ColumnDefinition from the "header" which is "current". Also see #11810 for what
                // happens if we don't do that.
                ColumnDefinition column = si.next(cd.column());
                assert column != null : cd.column.toString();

                try
                {
                    if (cd.column.isSimple())
                        Cell.serializer.serialize((Cell) cd, column, out, pkLiveness, header);
                    else
                        writeComplexColumn((ComplexColumnData) cd, column, (flags & HAS_COMPLEX_DELETION) != 0, pkLiveness, header, out);
                }
                catch (IOException e)
                {
                    throw new WrappedException(e);
                }
            }, false);
        }
        catch (WrappedException e)
        {
            if (e.getCause() instanceof IOException)
                throw (IOException) e.getCause();

            throw e;
        }
    }

    private void writeComplexColumn(ComplexColumnData data, ColumnDefinition column, boolean hasComplexDeletion, LivenessInfo rowLiveness, SerializationHeader header, DataOutputPlus out)
    throws IOException
    {
        if (hasComplexDeletion)
            header.writeDeletionTime(data.complexDeletion(), out);

        out.writeUnsignedVInt(data.cellsCount());
        for (Cell cell : data)
            Cell.serializer.serialize(cell, column, out, rowLiveness, header);
    }

    private void serialize(RangeTombstoneMarker marker, SerializationHeader header, DataOutputPlus out, long previousUnfilteredSize, int version)
    throws IOException
    {
        out.writeByte((byte)IS_MARKER);
        RangeTombstone.Bound.serializer.serialize(marker.clustering(), out, version, header.clusteringTypes());

        if (header.isForSSTable())
        {
            out.writeUnsignedVInt(serializedMarkerBodySize(marker, header, previousUnfilteredSize, version));
            out.writeUnsignedVInt(previousUnfilteredSize);
        }

        if (marker.isBoundary())
        {
            RangeTombstoneBoundaryMarker bm = (RangeTombstoneBoundaryMarker)marker;
            header.writeDeletionTime(bm.endDeletionTime(), out);
            header.writeDeletionTime(bm.startDeletionTime(), out);
        }
        else
        {
            header.writeDeletionTime(((RangeTombstoneBoundMarker)marker).deletionTime(), out);
        }
    }

    public long serializedSize(Unfiltered unfiltered, SerializationHeader header, int version)
    {
        assert !header.isForSSTable();
        return serializedSize(unfiltered, header, 0, version);
    }

    public long serializedSize(Unfiltered unfiltered, SerializationHeader header, long previousUnfilteredSize,int version)
    {
        return unfiltered.kind() == Unfiltered.Kind.RANGE_TOMBSTONE_MARKER
             ? serializedSize((RangeTombstoneMarker) unfiltered, header, previousUnfilteredSize, version)
             : serializedSize((Row) unfiltered, header, previousUnfilteredSize, version);
    }

    private long serializedSize(Row row, SerializationHeader header, long previousUnfilteredSize, int version)
    {
        long size = 1; // flags

        if (hasExtendedFlags(row))
            size += 1; // extended flags

        if (!row.isStatic())
            size += Clustering.serializer.serializedSize(row.clustering(), version, header.clusteringTypes());

        return size + serializedRowBodySize(row, header, previousUnfilteredSize, version);
    }

    private long serializedRowBodySize(Row row, SerializationHeader header, long previousUnfilteredSize, int version)
    {
        long size = 0;

        if (header.isForSSTable())
            size += TypeSizes.sizeofUnsignedVInt(previousUnfilteredSize);

        boolean isStatic = row.isStatic();
        Columns headerColumns = header.columns(isStatic);
        LivenessInfo pkLiveness = row.primaryKeyLivenessInfo();
        Row.Deletion deletion = row.deletion();
        boolean hasComplexDeletion = row.hasComplexDeletion();
        boolean hasAllColumns = (row.size() == headerColumns.size());

        if (!pkLiveness.isEmpty())
            size += header.timestampSerializedSize(pkLiveness.timestamp());
        if (pkLiveness.isExpiring())
        {
            size += header.ttlSerializedSize(pkLiveness.ttl());
            size += header.localDeletionTimeSerializedSize(pkLiveness.localExpirationTime());
        }
        if (!deletion.isLive())
            size += header.deletionTimeSerializedSize(deletion.time());

        if (!hasAllColumns)
            size += Columns.serializer.serializedSubsetSize(Collections2.transform(row, ColumnData::column), header.columns(isStatic));

        SearchIterator<ColumnDefinition, ColumnDefinition> si = headerColumns.iterator();
        for (ColumnData data : row)
        {
            ColumnDefinition column = si.next(data.column());
            assert column != null;

            if (data.column.isSimple())
                size += Cell.serializer.serializedSize((Cell) data, column, pkLiveness, header);
            else
                size += sizeOfComplexColumn((ComplexColumnData) data, column, hasComplexDeletion, pkLiveness, header);
        }

        return size;
    }

    private long sizeOfComplexColumn(ComplexColumnData data, ColumnDefinition column, boolean hasComplexDeletion, LivenessInfo rowLiveness, SerializationHeader header)
    {
        long size = 0;

        if (hasComplexDeletion)
            size += header.deletionTimeSerializedSize(data.complexDeletion());

        size += TypeSizes.sizeofUnsignedVInt(data.cellsCount());
        for (Cell cell : data)
            size += Cell.serializer.serializedSize(cell, column, rowLiveness, header);

        return size;
    }
    
    private long serializedSize(RangeTombstoneMarker marker, SerializationHeader header, long previousUnfilteredSize, int version)
    {
        assert !header.isForSSTable();
        return 1 // flags
             + RangeTombstone.Bound.serializer.serializedSize(marker.clustering(), version, header.clusteringTypes())
             + serializedMarkerBodySize(marker, header, previousUnfilteredSize, version);
    }

    private long serializedMarkerBodySize(RangeTombstoneMarker marker, SerializationHeader header, long previousUnfilteredSize, int version)
    {
        long size = 0;
        if (header.isForSSTable())
            size += TypeSizes.sizeofUnsignedVInt(previousUnfilteredSize);

        if (marker.isBoundary())
        {
            RangeTombstoneBoundaryMarker bm = (RangeTombstoneBoundaryMarker)marker;
            size += header.deletionTimeSerializedSize(bm.endDeletionTime());
            size += header.deletionTimeSerializedSize(bm.startDeletionTime());
        }
        else
        {
           size += header.deletionTimeSerializedSize(((RangeTombstoneBoundMarker)marker).deletionTime());
        }
        return size;
    }

    public void writeEndOfPartition(DataOutputPlus out) throws IOException
    {
        out.writeByte((byte)1);
    }

    public long serializedSizeEndOfPartition()
    {
        return 1;
    }

    /**
     * Deserialize an {@link Unfiltered} from the provided input.
     *
     * @param in the input from which to deserialize.
     * @param header serialization header corresponding to the serialized data.
     * @param helper the helper to use for deserialization.
     * @param builder a row builder, passed here so we don't allocate a new one for every new row.
     * @return the deserialized {@link Unfiltered} or {@code null} if we've read the end of a partition. This method is
     * guaranteed to never return empty rows.
     */
    public Unfiltered deserialize(DataInputPlus in, SerializationHeader header, SerializationHelper helper, Row.Builder builder)
    throws IOException
    {
        while (true)
        {
            Unfiltered unfiltered = deserializeOne(in, header, helper, builder);
            if (unfiltered == null)
                return null;

            // Skip empty rows, see deserializeOne javadoc
            if (!unfiltered.isEmpty())
                return unfiltered;
        }
    }

    /**
     * Deserialize a single {@link Unfiltered} from the provided input.
     * <p>
     * <b>WARNING:</b> this can return an empty row because it's possible there is a row serialized, but that row only
     * contains data for dropped columns, see CASSANDRA-13337. But as most code expect rows to not be empty, this isn't
     * meant to be exposed publicly.
     *
     * But as {@link UnfilteredRowIterator} should not return empty
     * rows, this mean consumer of this method should make sure to skip said empty rows.
     */
    private Unfiltered deserializeOne(DataInputPlus in, SerializationHeader header, SerializationHelper helper, Row.Builder builder)
    throws IOException
    {
        // It wouldn't be wrong per-se to use an unsorted builder, but it would be inefficient so make sure we don't do it by mistake
        assert builder.isSorted();

        int flags = in.readUnsignedByte();
        if (isEndOfPartition(flags))
            return null;

        int extendedFlags = readExtendedFlags(in, flags);

        if (kind(flags) == Unfiltered.Kind.RANGE_TOMBSTONE_MARKER)
        {
            RangeTombstone.Bound bound = RangeTombstone.Bound.serializer.deserialize(in, helper.version, header.clusteringTypes());
            return deserializeMarkerBody(in, header, bound);
        }
        else
        {
            // deserializeStaticRow should be used for that.
            if (isStatic(extendedFlags))
                throw new IOException("Corrupt flags value for unfiltered partition (isStatic flag set): " + flags);

            builder.newRow(Clustering.serializer.deserialize(in, helper.version, header.clusteringTypes()));
            return deserializeRowBody(in, header, helper, flags, extendedFlags, builder);
        }
    }

    public Row deserializeStaticRow(DataInputPlus in, SerializationHeader header, SerializationHelper helper)
    throws IOException
    {
        int flags = in.readUnsignedByte();
        assert !isEndOfPartition(flags) && kind(flags) == Unfiltered.Kind.ROW && isExtended(flags) : flags;
        int extendedFlags = in.readUnsignedByte();
        Row.Builder builder = BTreeRow.sortedBuilder();
        builder.newRow(Clustering.STATIC_CLUSTERING);
        return deserializeRowBody(in, header, helper, flags, extendedFlags, builder);
    }

    public RangeTombstoneMarker deserializeMarkerBody(DataInputPlus in, SerializationHeader header, RangeTombstone.Bound bound)
    throws IOException
    {
        if (header.isForSSTable())
        {
            in.readUnsignedVInt(); // marker size
            in.readUnsignedVInt(); // previous unfiltered size
        }

        if (bound.isBoundary())
            return new RangeTombstoneBoundaryMarker(bound, header.readDeletionTime(in), header.readDeletionTime(in));
        else
            return new RangeTombstoneBoundMarker(bound, header.readDeletionTime(in));
    }

    public Row deserializeRowBody(DataInputPlus in,
                                  SerializationHeader header,
                                  SerializationHelper helper,
                                  int flags,
                                  int extendedFlags,
                                  Row.Builder builder)
    throws IOException
    {
        try
        {
            boolean isStatic = isStatic(extendedFlags);
            boolean hasTimestamp = (flags & HAS_TIMESTAMP) != 0;
            boolean hasTTL = (flags & HAS_TTL) != 0;
            boolean hasDeletion = (flags & HAS_DELETION) != 0;
            boolean deletionIsShadowable = (extendedFlags & HAS_SHADOWABLE_DELETION) != 0;
            boolean hasComplexDeletion = (flags & HAS_COMPLEX_DELETION) != 0;
            boolean hasAllColumns = (flags & HAS_ALL_COLUMNS) != 0;
            Columns headerColumns = header.columns(isStatic);

            if (header.isForSSTable())
            {
                in.readUnsignedVInt(); // Skip row size
                in.readUnsignedVInt(); // previous unfiltered size
            }

            LivenessInfo rowLiveness = LivenessInfo.EMPTY;
            if (hasTimestamp)
            {
                long timestamp = header.readTimestamp(in);
                int ttl = hasTTL ? header.readTTL(in) : LivenessInfo.NO_TTL;
                int localDeletionTime = hasTTL ? header.readLocalDeletionTime(in) : LivenessInfo.NO_EXPIRATION_TIME;
                rowLiveness = LivenessInfo.create(timestamp, ttl, localDeletionTime);
            }

            builder.addPrimaryKeyLivenessInfo(rowLiveness);
            builder.addRowDeletion(hasDeletion ? new Row.Deletion(header.readDeletionTime(in), deletionIsShadowable) : Row.Deletion.LIVE);

            Columns columns = hasAllColumns ? headerColumns : Columns.serializer.deserializeSubset(headerColumns, in);

            final LivenessInfo livenessInfo = rowLiveness;

            try
            {
                columns.apply(column -> {
                    try
                    {
                        if (column.isSimple())
                            readSimpleColumn(column, in, header, helper, builder, livenessInfo);
                        else
                            readComplexColumn(column, in, header, helper, hasComplexDeletion, builder, livenessInfo);
                    }
                    catch (IOException e)
                    {
                        throw new WrappedException(e);
                    }
                }, false);
            }
            catch (WrappedException e)
            {
                if (e.getCause() instanceof IOException)
                    throw (IOException) e.getCause();

                throw e;
            }

            return builder.build();
        }
        catch (RuntimeException | AssertionError e)
        {
            // Corrupted data could be such that it triggers an assertion in the row Builder, or break one of its assumption.
            // Of course, a bug in said builder could also trigger this, but it's impossible a priori to always make the distinction
            // between a real bug and data corrupted in just the bad way. Besides, re-throwing as an IOException doesn't hide the
            // exception, it just make we catch it properly and mark the sstable as corrupted.
            throw new IOException("Error building row with data deserialized from " + in, e);
        }
    }

    private void readSimpleColumn(ColumnDefinition column, DataInputPlus in, SerializationHeader header, SerializationHelper helper, Row.Builder builder, LivenessInfo rowLiveness)
    throws IOException
    {
        if (helper.includes(column))
        {
            Cell cell = Cell.serializer.deserialize(in, rowLiveness, column, header, helper);
            if (!helper.isDropped(cell, false))
                builder.addCell(cell);
        }
        else
        {
            Cell.serializer.skip(in, column, header);
        }
    }

    private void readComplexColumn(ColumnDefinition column, DataInputPlus in, SerializationHeader header, SerializationHelper helper, boolean hasComplexDeletion, Row.Builder builder, LivenessInfo rowLiveness)
    throws IOException
    {
        if (helper.includes(column))
        {
            helper.startOfComplexColumn(column);
            if (hasComplexDeletion)
            {
                DeletionTime complexDeletion = header.readDeletionTime(in);
                if (!helper.isDroppedComplexDeletion(complexDeletion))
                    builder.addComplexDeletion(column, complexDeletion);
            }

            int count = (int) in.readUnsignedVInt();
            while (--count >= 0)
            {
                Cell cell = Cell.serializer.deserialize(in, rowLiveness, column, header, helper);
                if (helper.includes(cell.path()) && !helper.isDropped(cell, true))
                    builder.addCell(cell);
            }

            helper.endOfComplexColumn();
        }
        else
        {
            skipComplexColumn(in, column, header, hasComplexDeletion);
        }
    }

    public void skipRowBody(DataInputPlus in) throws IOException
    {
        int rowSize = (int)in.readUnsignedVInt();
        in.skipBytesFully(rowSize);
    }

    public void skipStaticRow(DataInputPlus in, SerializationHeader header, SerializationHelper helper) throws IOException
    {
        int flags = in.readUnsignedByte();
        assert !isEndOfPartition(flags) && kind(flags) == Unfiltered.Kind.ROW && isExtended(flags) : "Flags is " + flags;
        int extendedFlags = in.readUnsignedByte();
        assert isStatic(extendedFlags);
        skipRowBody(in);
    }

    public void skipMarkerBody(DataInputPlus in) throws IOException
    {
        int markerSize = (int)in.readUnsignedVInt();
        in.skipBytesFully(markerSize);
    }

    private void skipComplexColumn(DataInputPlus in, ColumnDefinition column, SerializationHeader header, boolean hasComplexDeletion)
    throws IOException
    {
        if (hasComplexDeletion)
            header.skipDeletionTime(in);

        int count = (int) in.readUnsignedVInt();
        while (--count >= 0)
            Cell.serializer.skip(in, column, header);
    }

    public static boolean isEndOfPartition(int flags)
    {
        return (flags & END_OF_PARTITION) != 0;
    }

    public static Unfiltered.Kind kind(int flags)
    {
        return (flags & IS_MARKER) != 0 ? Unfiltered.Kind.RANGE_TOMBSTONE_MARKER : Unfiltered.Kind.ROW;
    }

    public static boolean isStatic(int extendedFlags)
    {
        return (extendedFlags & IS_STATIC) != 0;
    }

    private static boolean isExtended(int flags)
    {
        return (flags & EXTENSION_FLAG) != 0;
    }

    public static int readExtendedFlags(DataInputPlus in, int flags) throws IOException
    {
        return isExtended(flags) ? in.readUnsignedByte() : 0;
    }

    public static boolean hasExtendedFlags(Row row)
    {
        return row.isStatic() || row.deletion().isShadowable();
    }
}
