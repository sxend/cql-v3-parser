package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class DoubleType implements ColumnType {
    private static final Serializer<Double> SERIALIZER = new Serializer<Double>() {
        @Override
        public byte[] serialize(Double aDouble) {
            ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES + Integer.BYTES);
            return buffer.putInt(Double.BYTES).putDouble(aDouble).array();
        }

        @Override
        public Double deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return buffer.getDouble();
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Builder implements ColumnTypeBuilder<DoubleType> {
        @Override
        public DoubleType build(ByteBuffer buffer) {
            return new DoubleType();
        }

        @Override
        public DoubleType build(ColumnType... childTypes) {
            return new DoubleType();
        }
    }
}
