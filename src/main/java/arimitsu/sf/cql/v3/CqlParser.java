package arimitsu.sf.cql.v3;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/07/25.
 */
public class CqlParser {
    private Compression compression = Compression.NONE;

    public CqlParser configure(Compression compression) {
        this.compression = compression;
        return this;
    }

    public Frame parse(ByteBuffer buffer) {
        byte version = buffer.get();
        byte flags = buffer.get();
        short streamId = buffer.getShort();
        byte opcode = buffer.get();
        Header header = new Header(Version.valueOf(version), Flags.valueOf(flags), streamId, Opcode.valueOf(opcode));
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        switch (header.flags) {
            case COMPRESSION:
            case BOTH:
                bytes = compression.compressor.decompress(bytes);
                break;
        }
        return new Frame(header, bytes);
    }

    public ByteBuffer unparse(Header header, byte[] bytes, int length) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + length);
        byte[] headerBytes = new byte[9];
        headerBytes[0] = header.version.number;
        headerBytes[1] = header.flags.value;
        headerBytes[2] = (byte) (header.streamId >>> 8);
        headerBytes[3] = (byte) (0xff & header.streamId);
        headerBytes[4] = header.opcode.number;
        headerBytes[5] = (byte) (length >>> 24);
        headerBytes[6] = (byte) (0xff & (length >>> 16));
        headerBytes[7] = (byte) (0xff & (length >>> 8));
        headerBytes[8] = (byte) (0xff & length);
        byteBuffer.put(headerBytes);
        if (bytes != null) byteBuffer.put(bytes);
        byteBuffer.flip();
        return byteBuffer;
    }

    public ByteBuffer unparse(Frame frame) {
        Header header = frame.header;
        int length = frame.length;
        byte[] bytes = frame.body;
        if (frame.body != null) {
            switch (frame.header.flags) {
                case COMPRESSION:
                case BOTH:
                    bytes = compression.compressor.compress(frame.body);
                    length = compression.compressor.getCompressedLength(bytes);
                    break;
            }
        }
        return unparse(header, bytes, length);
    }

    public ByteBuffer unparseNoCompress(Frame frame) {
        return unparse(frame.header, frame.body, frame.body != null ? frame.body.length : 0);
    }

}
