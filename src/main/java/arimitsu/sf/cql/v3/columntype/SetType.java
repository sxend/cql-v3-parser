package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sxend on 2014/06/17.
 */
public class SetType implements ColumnType {
    public final ColumnType valueType;
    private final Serializer<Set<Object>> Serializer = new Serializer<Set<Object>>() {
        @Override
        public byte[] serialize(Set<Object> objects) {
            Serializer<Object> serializer = ((Serializer<Object>) valueType.getSerializer());
            int elementCount = objects.size();
            byte[] bytes = new byte[0];

            for (Object object : objects.toArray()) {
                bytes = Notations.join(bytes, serializer.serialize(object));
            }
            bytes = Notations.join(Notations.toIntBytes(elementCount), bytes);
            bytes = Notations.join(Notations.toIntBytes(bytes.length), bytes);
            return bytes;
        }

        @Override
        public Set<Object> deserialize(ByteBuffer buffer) {
            Set<Object> set = new HashSet<>();
            int length = buffer.getInt();
            int elementCount = buffer.getInt();
            for (int i = 0; i < elementCount; i++) {
                set.add(valueType.getSerializer().deserialize(buffer));
            }
            return set;
        }
    };

    public SetType(ColumnType valueType) {
        this.valueType = valueType;
    }


    @Override
    public Serializer<Set<Object>> getSerializer() {
        return Serializer;
    }

    public static class Builder implements ColumnTypeBuilder<SetType> {
        @Override
        public SetType build(ByteBuffer buffer) {
            ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
            return new SetType(valueType.builder.build(buffer));
        }

        @Override
        public SetType build(ColumnType... childTypes) {
            return new SetType(childTypes[0]);
        }
    }
}
