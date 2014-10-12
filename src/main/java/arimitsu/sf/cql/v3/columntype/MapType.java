package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

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
        public byte[] serialize(Map<Object, Object> map) {
            Serializer<Object> keySerializer = ((Serializer<Object>) keyType.getSerializer());
            Serializer<Object> valueSerializer = ((Serializer<Object>) valueType.getSerializer());
            int mappingCount = map.size();
            byte[] bytes = new byte[0];
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                bytes = Notations.join(bytes, Notations.join(keySerializer.serialize(entry.getKey()), valueSerializer.serialize(entry.getValue())));
            }
            bytes = Notations.join(Notations.toIntBytes(mappingCount), bytes);
            bytes = Notations.join(Notations.toIntBytes(bytes.length), bytes);
            return bytes;
        }

        @Override
        public Map<Object, Object> deserialize(ByteBuffer buffer) {
            Serializer<?> keySerializer = keyType.getSerializer();
            Serializer<?> valueSerializer = valueType.getSerializer();
            Map<Object, Object> map = new HashMap<>();
            int length = buffer.getInt();
            int mappingCount = buffer.getInt();
            for (int i = 0; i < mappingCount; i++) {
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

    public static class Builder implements ColumnTypeBuilder<MapType> {
        @Override
        public MapType build(ByteBuffer buffer) {
            ColumnTypes keyType = ColumnTypes.valueOf(Notations.getShort(buffer));
            ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
            return new MapType(keyType.builder.build(buffer), valueType.builder.build(buffer));
        }

        @Override
        public MapType build(ColumnType... childTypes) {
            return new MapType(childTypes[0], childTypes[1]);
        }
    }
}