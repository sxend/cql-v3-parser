package arimitsu.sf.cql.v3.columntype;

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
            return new byte[0];
        }

        @Override
        public Set<Object> deserialize(ByteBuffer buffer) {
            Set<Object> set = new HashSet<>();
            int byteLength = buffer.getInt();
            int length = buffer.getInt();
            for (int i = 0; i < length; i++) {
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
}
