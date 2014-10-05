package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

import java.util.Map;

/**
 * Created by sxend on 14/06/07.
 */
public class Startup implements Request {
    public static final String CQL_VERSION = "CQL_VERSION";
    public static final String CQL_VERSION_NUMBER = "3.0.0";
    public static final String OPTION_COMPRESSION = "COMPRESSION";
    public final short streamId;
    public final Flags flags;
    public final Map<String, String> options;

    public Startup(short streamId, Flags flags, Map<String, String> options) {
        this.streamId = streamId;
        this.flags = flags;
        this.options = options;
    }

    @Override
    public Frame toFrame() {
        byte[] body = Notations.toStringMap(options);
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.STARTUP, body.length), body);
    }

}
