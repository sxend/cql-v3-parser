package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class BooleanType implements ColumnType {
    private static final Serializer<Boolean> SERIALIZER = new Serializer<Boolean>() {
        @Override
        public byte[] serialize(Boolean aBoolean) {
            return new byte[0];
        }

        @Override
        public Boolean deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return Notations.getBoolean(buffer);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
