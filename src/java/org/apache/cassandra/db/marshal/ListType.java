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
package org.apache.cassandra.db.marshal;

import java.nio.ByteBuffer;
import java.util.*;

import org.apache.cassandra.cql3.Json;
import org.apache.cassandra.cql3.Lists;
import org.apache.cassandra.cql3.Term;
import org.apache.cassandra.db.rows.Cell;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.exceptions.SyntaxException;
import org.apache.cassandra.serializers.CollectionSerializer;
import org.apache.cassandra.serializers.MarshalException;
import org.apache.cassandra.serializers.ListSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListType<T> extends CollectionType<List<T>>
{
    private static final Logger logger = LoggerFactory.getLogger(ListType.class);

    // interning instances
    private static final Map<AbstractType<?>, ListType> instances = new HashMap<>();
    private static final Map<AbstractType<?>, ListType> frozenInstances = new HashMap<>();

    private final AbstractType<T> elements;
    public final ListSerializer<T> serializer;
    private final boolean isMultiCell;

    public static ListType<?> getInstance(TypeParser parser) throws ConfigurationException, SyntaxException
    {
        List<AbstractType<?>> l = parser.getTypeParameters();
        if (l.size() != 1)
            throw new ConfigurationException("ListType takes exactly 1 type parameter");

        return getInstance(l.get(0), true);
    }

    public static synchronized <T> ListType<T> getInstance(AbstractType<T> elements, boolean isMultiCell)
    {
        Map<AbstractType<?>, ListType> internMap = isMultiCell ? instances : frozenInstances;
        ListType<T> t = internMap.get(elements);
        if (t == null)
        {
            t = new ListType<T>(elements, isMultiCell);
            internMap.put(elements, t);
        }
        return t;
    }

    private ListType(AbstractType<T> elements, boolean isMultiCell)
    {
        super(ComparisonType.CUSTOM, Kind.LIST);
        this.elements = elements;
        this.serializer = ListSerializer.getInstance(elements.getSerializer());
        this.isMultiCell = isMultiCell;
    }

    @Override
    public boolean referencesUserType(String userTypeName)
    {
        return getElementsType().referencesUserType(userTypeName);
    }

    public AbstractType<T> getElementsType()
    {
        return elements;
    }

    public AbstractType<UUID> nameComparator()
    {
        return TimeUUIDType.instance;
    }

    public AbstractType<T> valueComparator()
    {
        return elements;
    }

    public ListSerializer<T> getSerializer()
    {
        return serializer;
    }

    @Override
    public AbstractType<?> freeze()
    {
        if (isMultiCell)
            return getInstance(this.elements, false);
        else
            return this;
    }

    @Override
    public boolean isMultiCell()
    {
        return isMultiCell;
    }

    @Override
    public boolean isCompatibleWithFrozen(CollectionType<?> previous)
    {
        assert !isMultiCell;
        return this.elements.isCompatibleWith(((ListType) previous).elements);
    }

    @Override
    public boolean isValueCompatibleWithFrozen(CollectionType<?> previous)
    {
        assert !isMultiCell;
        return this.elements.isValueCompatibleWithInternal(((ListType) previous).elements);
    }

    @Override
    public int compareCustom(ByteBuffer o1, ByteBuffer o2)
    {
        return compareListOrSet(elements, o1, o2);
    }

    static int compareListOrSet(AbstractType<?> elementsComparator, ByteBuffer o1, ByteBuffer o2)
    {
        // Note that this is only used if the collection is frozen
        if (!o1.hasRemaining() || !o2.hasRemaining())
            return o1.hasRemaining() ? 1 : o2.hasRemaining() ? -1 : 0;

        ByteBuffer bb1 = o1.duplicate();
        ByteBuffer bb2 = o2.duplicate();

        int size1 = CollectionSerializer.readCollectionSize(bb1, 3);
        int size2 = CollectionSerializer.readCollectionSize(bb2, 3);

        for (int i = 0; i < Math.min(size1, size2); i++)
        {
            ByteBuffer v1 = CollectionSerializer.readValue(bb1, 3);
            ByteBuffer v2 = CollectionSerializer.readValue(bb2, 3);
            int cmp = elementsComparator.compare(v1, v2);
            if (cmp != 0)
                return cmp;
        }

        return size1 == size2 ? 0 : (size1 < size2 ? -1 : 1);
    }

    @Override
    public String toString(boolean ignoreFreezing)
    {
        boolean includeFrozenType = !ignoreFreezing && !isMultiCell();

        StringBuilder sb = new StringBuilder();
        if (includeFrozenType)
            sb.append(FrozenType.class.getName()).append("(");
        sb.append(getClass().getName());
        sb.append(TypeParser.stringifyTypeParameters(Collections.<AbstractType<?>>singletonList(elements), ignoreFreezing || !isMultiCell));
        if (includeFrozenType)
            sb.append(")");
        return sb.toString();
    }

    public List<ByteBuffer> serializedValues(Iterator<Cell> cells)
    {
        assert isMultiCell;
        List<ByteBuffer> bbs = new ArrayList<ByteBuffer>();
        while (cells.hasNext())
            bbs.add(cells.next().value());
        return bbs;
    }

    @Override
    public Term fromJSONObject(Object parsed) throws MarshalException
    {
        if (parsed instanceof String)
            parsed = Json.decodeJson((String) parsed);

        if (!(parsed instanceof List))
            throw new MarshalException(String.format(
                    "Expected a list, but got a %s: %s", parsed.getClass().getSimpleName(), parsed));

        List list = (List) parsed;
        List<Term> terms = new ArrayList<>(list.size());
        for (Object element : list)
        {
            if (element == null)
                throw new MarshalException("Invalid null element in list");
            terms.add(elements.fromJSONObject(element));
        }

        return new Lists.DelayedValue(terms);
    }

    public static String setOrListToJsonString(ByteBuffer buffer, AbstractType elementsType, int protocolVersion)
    {
        ByteBuffer value = buffer.duplicate();
        StringBuilder sb = new StringBuilder("[");
        int size = CollectionSerializer.readCollectionSize(value, protocolVersion);
        for (int i = 0; i < size; i++)
        {
            if (i > 0)
                sb.append(", ");
            sb.append(elementsType.toJSONString(CollectionSerializer.readValue(value, protocolVersion), protocolVersion));
        }
        return sb.append("]").toString();
    }

    @Override
    public String toJSONString(ByteBuffer buffer, int protocolVersion)
    {
        return setOrListToJsonString(buffer, elements, protocolVersion);
    }
}
