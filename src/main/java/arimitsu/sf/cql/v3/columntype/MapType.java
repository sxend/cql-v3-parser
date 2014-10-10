package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 2014/06/17.
 */
public class MapType implements ColumnType {
    public final ColumnType keyType;
    public final ColumnType valueType;
    private final Serializer<Map<Object, Object>> Serializer = new Serializer<Map<Object, Object>>() {
        @Override
        public byte[] serialize(Map<Object, Object> objectObjectMap) {
            return new byte[0];
        }

        @Override
        public Map<Object, Object> deserialize(ByteBuffer buffer) {
            Serializer<?> keySerializer = keyType.getSerializer();
            Serializer<?> valueSerializer = valueType.getSerializer();
            Map<Object, Object> map = new HashMap<>();
            int byteLength = buffer.getInt();
            int length = buffer.getInt();
            for (int i = 0; i < length; i++) {
                map.put(keySerializer.deserialize(buffer), valueSerializer.deserialize(buffer));
            }
            return map;
        }
    };

    public MapType(ColumnType keyType, ColumnType valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }


    @Override
    public Serializer<Map<Object, Object>> getSerializer() {
        return Serializer;
    }
}