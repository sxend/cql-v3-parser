package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class IntType implements ColumnType {
    private static final Serializer<Integer> SERIALIZER = new Serializer<Integer>() {
        @Override
        public byte[] serialize(Integer integer) {
            return new byte[0];
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
}
