package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

/**
 * Created by sxend on 14/06/07.
 */
public class Prepare implements Request {
    public final short streamId;
    public final Flags flags;
    public final String query;

    public Prepare(short streamId, Flags flags, String query) {
        this.streamId = streamId;
        this.flags = flags;
        this.query = query;
    }

    @Override
    public Frame toFrame() {
        byte[] body = Notations.toLongString(query);
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.PREPARE, body.length), body);
    }
}
