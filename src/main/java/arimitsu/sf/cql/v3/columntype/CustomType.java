package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/10/09.
 */
public class CustomType implements ColumnType {
    private static final Parser<String> PARSER = new Parser<String>() {
        @Override
        public String parse(ByteBuffer buffer) {
            return Notations.getString(buffer);
        }
    };

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
