package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class IntType implements ColumnType {
    private static final Serializer<Integer> SERIALIZER = new Serializer<Integer>() {
        @Override
        public byte[] serialize(Integer integer) {
            byte[] bytes = Notations.toIntBytes(integer);
            return Notations.join(Notations.toIntBytes(bytes.length), bytes);
        }

        @Override
        public Integer deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return Notations.getInt(buffer);
        }
    };


    @Override
    public Serializer<Integer> getSerializer() {
        return SERIALIZER;
    }

    public static class Builder implements ColumnTypeBuilder<IntType> {
        @Override
        public IntType build(ByteBuffer buffer) {
            return new IntType();
        }

        @Override
        public IntType build(ColumnType... childTypes) {
            return new IntType();
        }
    }
}
