package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class FloatType implements ColumnType {
    private static final Serializer<Float> SERIALIZER = new Serializer<Float>() {
        @Override
        public byte[] serialize(Float aFloat) {
            ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES + Integer.BYTES);
            return buffer.putInt(Float.BYTES).putFloat(aFloat).array();
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

    public static class Builder implements ColumnTypeBuilder<FloatType> {
        @Override
        public FloatType build(ByteBuffer buffer) {
            return new FloatType();
        }

        @Override
        public FloatType build(ColumnType... childTypes) {
            return new FloatType();
        }
    }
}
