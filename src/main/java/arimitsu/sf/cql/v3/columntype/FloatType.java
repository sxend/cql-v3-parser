package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class FloatType implements ColumnType {
    private static final Serializer<Float> SERIALIZER = new Serializer<Float>() {
        @Override
        public byte[] serialize(Float aFloat) {
            int arraySize = Float.SIZE / Byte.SIZE;
            ByteBuffer buffer = ByteBuffer.allocate(arraySize);
            return buffer.putFloat(aFloat).array();
        }

        @Override
        public Float deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return buffer.getFloat();
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
