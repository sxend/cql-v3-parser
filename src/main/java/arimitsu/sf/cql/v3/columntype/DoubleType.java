package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class DoubleType implements ColumnType {
    private static final Serializer<Double> SERIALIZER = new Serializer<Double>() {
        @Override
        public byte[] serialize(Double aDouble) {
            return new byte[0];
        }

        @Override
        public Double deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            return buffer.getDouble();
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
