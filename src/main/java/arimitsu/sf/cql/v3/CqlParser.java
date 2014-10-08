package arimitsu.sf.cql.v3;

import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.compressor.NoneCompressor;
import arimitsu.sf.cql.v3.message.Message;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/07/25.
 */
public class CqlParser {
    private BodyParser bodyParser = new BodyParser();

    private Compressor compressor = new NoneCompressor();

    public CqlParser withCompressor(Compressor compressor) {
        this.compressor = compressor;
        return this;
    }

    public Frame byteBufferToFrame(ByteBuffer buffer) {
        byte version = buffer.get();
        byte flags = buffer.get();
        short streamId = buffer.getShort();
        byte opcode = buffer.get();
        int length = buffer.getInt();
        Header header = new Header(Version.valueOf(version), Flags.valueOf(flags), streamId, Opcode.valueOf(opcode), length);
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        switch (header.flags) {
            case COMPRESSION:
            case BOTH:
                bytes = compressor.decompress(bytes);
                break;
        }
        return new Frame(header, bytes);
    }

    public ByteBuffer frameToByteBuffer(Frame frame) {
        Header header = frame.header;
        int length = frame.header.length;
        byte[] bytes = frame.body;
        if (frame.body != null) {
            switch (frame.header.flags) {
                case COMPRESSION:
                case BOTH:
                    bytes = compressor.compress(frame.body);
                    length = bytes.length;
                    break;
            }
        }
        return toByteBuffer(header, bytes, length);
    }

    private ByteBuffer toByteBuffer(Header header, byte[] bytes, int length) {
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

    public Message frameToMessage(Frame frame) {
        return bodyParser.toMessage(frame.header.opcode, frame.body);
    }
}
