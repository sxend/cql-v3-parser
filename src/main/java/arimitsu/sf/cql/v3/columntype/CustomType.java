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
            return Notations.toStringBytes(s);
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

    public static final class Builder implements ColumnTypeBuilder<CustomType> {

        @Override
        public CustomType build(ByteBuffer buffer) {
            return new CustomType();
        }

        @Override
        public CustomType build(ColumnType... childTypes) {
            return new CustomType();
        }
    }
}
