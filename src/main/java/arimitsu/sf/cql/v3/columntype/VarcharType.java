package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class VarcharType implements ColumnType {
    private static final Parser<String> PARSER = new Parser<String>() {
        @Override
        public String parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notations.getString(buffer, length);
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
