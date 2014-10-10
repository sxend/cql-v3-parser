package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class BlobType implements ColumnType {
    private static final Serializer<byte[]> SERIALIZER = new Serializer<byte[]>() {
        @Override
        public byte[] serialize(byte[] bytes) {
            return null;
        }

        @Override
        public byte[] deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notations.getBytes(buffer, length);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
