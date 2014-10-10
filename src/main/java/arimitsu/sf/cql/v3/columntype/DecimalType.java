package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class DecimalType implements ColumnType {
    private static final Serializer<BigDecimal> SERIALIZER = new Serializer<BigDecimal>() {
        @Override
        public byte[] serialize(BigDecimal bigDecimal) {
            return new byte[0];
        }

        @Override
        public BigDecimal deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            int scale = buffer.getInt();
            byte[] bytes = Notations.getBytes(buffer, length - Notations.INTEGER_BYTE_LEN);
            return new BigDecimal(new BigInteger(bytes), scale);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
