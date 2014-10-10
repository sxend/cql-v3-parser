package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class BigintType implements ColumnType {
    private static final Serializer<Long> SERIALIZER = new Serializer<Long>() {
        @Override
        public byte[] serialize(Long aLong) {
            return Notations.long2Bytes(aLong);
        }

        @Override
        public Long deserialize(ByteBuffer buffer) {
            buffer.getInt(); // length 4
            return buffer.getLong();
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
