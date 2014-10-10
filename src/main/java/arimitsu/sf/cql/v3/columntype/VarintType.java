package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class VarintType implements ColumnType {
    private static final Serializer<BigInteger> SERIALIZER = new Serializer<BigInteger>() {
        @Override
        public byte[] serialize(BigInteger bigInteger) {
            byte[] bytes = bigInteger.toByteArray();
            return Notations.join(Notations.toIntBytes(bytes.length), bytes);
        }

        @Override
        public BigInteger deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            byte[] bytes = Notations.getBytes(buffer, length);
            return new BigInteger(bytes);
        }
    };

    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
