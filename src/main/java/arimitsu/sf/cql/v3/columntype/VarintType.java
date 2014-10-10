package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class VarintType implements ColumnType {
    private static final Parser<BigInteger> PARSER = new Parser<BigInteger>() {
        @Override
        public BigInteger parse(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            byte[] bytes = Notations.getBytes(buffer, length);
            return new BigInteger(bytes);
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
