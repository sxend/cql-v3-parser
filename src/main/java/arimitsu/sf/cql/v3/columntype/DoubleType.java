package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class DoubleType implements ColumnType {
    private static final Serializer<Double> SERIALIZER = new Serializer<Double>() {
        @Override
        public byte[] serialize(Double aDouble) {
            int arraySize = Double.SIZE / Byte.SIZE;
            ByteBuffer buffer = ByteBuffer.allocate(arraySize);
            return buffer.putDouble(aDouble).array();
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
}
