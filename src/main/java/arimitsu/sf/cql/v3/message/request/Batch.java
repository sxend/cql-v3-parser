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
public class Batch extends Request {

    public Batch() {
    }

    @Override
    public byte[] toBody() {
        return Request.EMPTY_BODY;
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
