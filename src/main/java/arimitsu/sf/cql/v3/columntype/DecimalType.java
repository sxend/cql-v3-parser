package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class DecimalType implements ColumnType {
    private static final Serializer<BigDecimal> SERIALIZER = new Serializer<BigDecimal>() {
        @Override
        public byte[] serialize(BigDecimal bigDecimal) {
            int scale = bigDecimal.scale();
            byte[] bytes = Notations.join(Notations.toIntBytes(scale), bigDecimal.unscaledValue().toByteArray());
            return  Notations.join(Notations.toIntBytes(bytes.length), bytes);
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

    public static class Builder implements ColumnTypeBuilder<DecimalType> {
        @Override
        public DecimalType build(ByteBuffer buffer) {
            return new DecimalType();
        }

        @Override
        public DecimalType build(ColumnType... childTypes) {
            return new DecimalType();
        }
    }
}
