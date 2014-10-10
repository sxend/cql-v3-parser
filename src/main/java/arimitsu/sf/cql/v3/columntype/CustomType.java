package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/10/09.
 */
public class CustomType implements ColumnType {
    private static final Serializer<String> SERIALIZER = new Serializer<String>() {
        @Override
        public byte[] serialize(String s) {
            return new byte[0];
        }

        @Override
        public String deserialize(ByteBuffer buffer) {
            return Notations.getString(buffer);
        }
    };

    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
