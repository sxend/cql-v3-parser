package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class FloatType implements ColumnType {
    private static final Serializer<Float> SERIALIZER = new Serializer<Float>() {
        @Override
        public byte[] serialize(Float aFloat) {
            return new byte[0];
        }

        @Override
        public Float deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            return buffer.getFloat();
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
