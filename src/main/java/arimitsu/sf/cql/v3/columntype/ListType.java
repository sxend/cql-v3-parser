package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxend on 2014/06/17.
 */
public class ListType implements ColumnType {
    public final ColumnType valueType;
    private final Serializer<List<Object>> Serializer = new Serializer<List<Object>>() {
        @Override
        public byte[] serialize(List<Object> objects) {
            Serializer<Object> serializer = ((Serializer<Object>) valueType.getSerializer());
            int elementCount = objects.size();
            byte[] bytes = new byte[0];
            for (int i = 0; i < elementCount; i++) {
                bytes = Notations.join(bytes, serializer.serialize(objects.get(i)));
            }
            bytes = Notations.join(Notations.toIntBytes(elementCount), bytes);
            bytes = Notations.join(Notations.toIntBytes(bytes.length), bytes);
            return bytes;
        }

        @Override
        public List<Object> deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            int elementCount = buffer.getInt();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < elementCount; i++) {
                list.add(valueType.getSerializer().deserialize(buffer));
            }
            return list;
        }
    };

    public ListType(ColumnType valueType) {
        this.valueType = valueType;
    }


    @Override
    public Serializer<List<Object>> getSerializer() {
        return Serializer;
    }

    public static class Builder implements ColumnTypeBuilder<ListType> {
        @Override
        public ListType build(ByteBuffer buffer) {
            ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
            return new ListType(valueType.builder.build(buffer));
        }

        @Override
        public ListType build(ColumnType... childTypes) {
            return new ListType(childTypes[0]);
        }
    }
}
