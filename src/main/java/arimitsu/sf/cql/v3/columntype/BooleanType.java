package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class BooleanType implements ColumnType {
    private static final Serializer<Boolean> SERIALIZER = new Serializer<Boolean>() {
        @Override
        public byte[] serialize(Boolean aBoolean) {
            byte flag = 0;
            if (aBoolean) {
                flag = 1;
            }
            return Notations.join(Notations.toIntBytes(1), new byte[]{flag});
        }

        @Override
        public Boolean deserialize(ByteBuffer buffer) {
            buffer.getInt();
            return buffer.get() != 0;
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
