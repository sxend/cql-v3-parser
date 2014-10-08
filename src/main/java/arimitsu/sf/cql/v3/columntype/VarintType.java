package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class VarintType implements ColumnType {
    private static final Parser<BigDecimal> PARSER = new Parser<BigDecimal>() {
        @Override
        public BigDecimal parse(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            byte[] bytes = Notations.getBytes(buffer, length);
            return BigDecimal.valueOf(Notations.getLong(bytes));
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
