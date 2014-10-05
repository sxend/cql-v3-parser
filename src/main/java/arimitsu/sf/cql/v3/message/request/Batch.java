package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;

/**
 * Created by sxend on 14/06/07.
 */
public class Batch implements Request {
    public final short streamId;
    public final Flags flags;

    public Batch(short streamId, Flags flags) {
        this.streamId = streamId;
        this.flags = flags;
    }

    @Override
    public Frame toFrame() {
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.BATCH, Frame.EMPTY_BODY.length), Frame.EMPTY_BODY);
    }

    public static enum BatchType {
        LOGGED(0),
        UNLOGGED(1),
        COUNTER(2),;
        public final byte code;

        BatchType(int code) {
            this.code = (byte) code;
        }

    }
}
