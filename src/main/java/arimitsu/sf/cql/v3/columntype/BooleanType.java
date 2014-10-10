package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class BooleanType implements ColumnType {
    private static final Parser<Boolean> PARSER = new Parser<Boolean>() {
        @Override
        public Boolean parse(ByteBuffer buffer) {
            buffer.getInt();
            return buffer.get() != 0;
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
