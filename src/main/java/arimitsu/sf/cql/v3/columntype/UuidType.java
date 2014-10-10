package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidType implements ColumnType {
    private static final Serializer<UUID> SERIALIZER = new Serializer<UUID>() {
        @Override
        public byte[] serialize(UUID uuid) {
            byte[] bytes = Notations.toUUIDBytes(uuid);
            return Notations.join(Notations.toIntBytes(bytes.length), bytes);
        }

        @Override
        public UUID deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notations.getUUID(buffer, length);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
