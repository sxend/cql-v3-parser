package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class InetType implements ColumnType {
    private static final Serializer<InetAddress> SERIALIZER = new Serializer<InetAddress>() {
        @Override
        public byte[] serialize(InetAddress inetAddress) {
            return new byte[0];
        }

        @Override
        public InetAddress deserialize(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notations.getINet(buffer, length);
        }
    };


    @Override
    public Serializer<?> getSerializer() {
        return SERIALIZER;
    }
}
