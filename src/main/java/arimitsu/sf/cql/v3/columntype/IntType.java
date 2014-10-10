package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class IntType implements ColumnType {
    private static final Serializer<Integer> SERIALIZER = new Serializer<Integer>() {
        @Override
        public byte[] serialize(Integer integer) {
            return new byte[0];
        }

        @Override
        public Integer deserialize(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            return buffer.getInt();
        }
    };


    @Override
    public Serializer<Integer> getSerializer() {
        return SERIALIZER;
    }
}
