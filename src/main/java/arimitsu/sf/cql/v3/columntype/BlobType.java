package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class BlobType implements ColumnType {
    private static final Parser<byte[]> PARSER = new Parser<byte[]>() {
        @Override
        public byte[] parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notations.getBytes(buffer, length);
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
