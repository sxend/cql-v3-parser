package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

import java.util.List;

/**
 * Created by sxend on 14/06/07.
 */
public class Register implements Request {
    public final short streamId;
    public final Flags flags;
    public final List<String> events;

    public Register(short streamId, Flags flags, List<String> events) {
        this.streamId = streamId;
        this.flags = flags;
        this.events = events;
    }

    @Override
    public Frame toFrame() {
        byte[] body = Notations.toStringList(events);
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.REGISTER, body.length), body);
    }
}
