package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxend on 2014/10/09.
 */
public class TupleType implements ColumnType {
    public final int length;
    public final ColumnType[] valueTypes;
    private final Serializer<List<Object>> Serializer = new Serializer<List<Object>>() {
        @Override
        public byte[] serialize(List<Object> objects) {
            int elementCount = objects.size();
            byte[] bytes = new byte[0];
            for (int i = 0; i < elementCount; i++) {
                Serializer<Object> serializer = (Serializer<Object>) valueTypes[i].getSerializer();
                bytes = Notations.join(bytes, serializer.serialize(objects.get(i)));
            }
            bytes = Notations.join(Notations.toIntBytes(elementCount), bytes);
            bytes = Notations.join(Notations.toIntBytes(bytes.length), bytes);
            return new byte[0];
        }

        @Override
        public List<Object> deserialize(ByteBuffer buffer) {
            List<Object> list = new ArrayList<>();
            for (int i = 0, length = valueTypes.length; i < length; i++) {
                list.add(valueTypes[i].getSerializer().deserialize(buffer));
            }
            return list;
        }
    };

    public TupleType(ByteBuffer buffer) {
        this.length = Notations.getShort(buffer);
        List<ColumnType> columnTypes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            columnTypes.add(ColumnType.Factory.fromBuffer(buffer));
        }
        this.valueTypes = columnTypes.toArray(new ColumnType[columnTypes.size()]);
    }

    @Override
    public Serializer<List<Object>> getSerializer() {
        return Serializer;
    }
}
