package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class DecimalType implements ColumnType {
    private static final Parser<BigDecimal> PARSER = new Parser<BigDecimal>() {
        @Override
        public BigDecimal parse(ByteBuffer buffer) {
            buffer.getInt();
            int scale = buffer.getInt();
//            byte[] bytes = Notation.getBytes(buffer,12);
            return new BigDecimal(new BigInteger(Notations.getBytes(buffer, 4)), scale);
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
