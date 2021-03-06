package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class InetType implements ColumnType {
    private static final Serializer<InetAddress> SERIALIZER = new Serializer<InetAddress>() {
        @Override
        public byte[] serialize(InetAddress inetAddress) {
            byte[] bytes = Notations.toINetBytes(inetAddress);
            return Notations.join(Notations.toIntBytes(bytes.length), bytes);
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

    public static class Builder implements ColumnTypeBuilder<InetType> {
        @Override
        public InetType build(ByteBuffer buffer) {
            return new InetType();
        }

        @Override
        public InetType build(ColumnType... childTypes) {
            return new InetType();
        }
    }
}
