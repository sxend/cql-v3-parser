package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 * The CQL binary protocol frame
 * <p>
 * 0         8        16        24        32         40
 * +---------+---------+---------+---------+---------+
 * | version |  queryFlags  |      stream       | opcode  |
 * +---------+---------+---------+---------+---------+
 * |                length                 |
 * +---------+---------+---------+---------+
 * |                                       |
 * .            ...  body ...              .
 * .                                       .
 * .                                       .
 * +----------------------------------------
 * <p>
 */
public class Frame {
    public static final byte[] EMPTY_BODY = new byte[0];
    public final Header header;
    public final byte[] body;

    public Frame(Header header, byte[] body) {
        this.header = header;
        this.body = body;
    }


    public static class Header {
        public final Version version;
        public final Flags flags;
        public final short streamId;
        public final Opcode opcode;
        public final int length;

        public Header(Version version, Flags flags, short streamId, Opcode opcode, int length) {
            this.version = version;
            this.flags = flags;
            this.streamId = streamId;
            this.opcode = opcode;
            this.length = length;
        }
    }
}
