package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class FloatType implements ColumnType {
    private static final Serializer<Float> SERIALIZER = new Serializer<Float>() {
        @Override
        public byte[] serialize(Float aFloat) {
            return new byte[0];
        }

        @Override
        public Float deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return Notations.getFloat(buffer);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
