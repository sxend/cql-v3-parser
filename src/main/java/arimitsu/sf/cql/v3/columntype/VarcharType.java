package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class VarcharType implements ColumnType {
    private static final Serializer<String> SERIALIZER = new Serializer<String>() {
        @Override
        public byte[] serialize(String s) {
            byte[] bytes = Notations.toStringBytes(s);
            return Notations.join(Notations.toIntBytes(bytes.length), bytes);
        }

        @Override
        public String deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notations.getString(buffer, length);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
