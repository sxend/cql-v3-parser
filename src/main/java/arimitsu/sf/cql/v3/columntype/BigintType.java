package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class BigintType implements ColumnType {
    private static final Serializer<Long> SERIALIZER = new Serializer<Long>() {
        @Override
        public byte[] serialize(Long aLong) {
            return Notations.toLongBytes(aLong);
        }

        @Override
        public Long deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return Notations.getLong(buffer);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Builder implements ColumnTypeBuilder<BigintType> {
        @Override
        public BigintType build(ByteBuffer buffer) {
            return new BigintType();
        }

        @Override
        public BigintType build(ColumnType... childTypes) {
            return new BigintType();
        }
    }
}
