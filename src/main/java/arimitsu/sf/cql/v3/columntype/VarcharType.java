package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

public class VarcharType implements ColumnType {
    private static final Parser<String> PARSER = new Parser<String>() {
        @Override
        public String parse(ByteBuffer buffer) {
            return Notations.getString(buffer, buffer.getInt());
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
