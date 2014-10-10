package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;
import java.util.Date;

public class TimestampType implements ColumnType {
    private static final Serializer<Date> SERIALIZER = new Serializer<Date>() {
        @Override
        public byte[] serialize(Date date) {
            return new byte[0];
        }

        @Override
        public Date deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            long time = buffer.getLong() * 1000;
            return new Date(time);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
