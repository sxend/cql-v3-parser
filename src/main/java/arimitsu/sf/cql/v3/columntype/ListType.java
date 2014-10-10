package arimitsu.sf.cql.v3.columntype;

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
            return new byte[0];
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
}
