package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/17.
 */
public class AsciiType implements ColumnType {
    private static final Serializer<String> SERIALIZER = new Serializer<String>() {

        @Override
        public byte[] serialize(String string) {
            return Notations.toLongString(string);
        }

        @Override
        public String deserialize(ByteBuffer buffer) {
            return Notations.getLongString(buffer);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Builder implements ColumnTypeBuilder<AsciiType> {
        @Override
        public AsciiType build(ByteBuffer buffer) {
            return new AsciiType();
        }

        @Override
        public AsciiType build(ColumnType... childTypes) {
            return new AsciiType();
        }
    }
}
