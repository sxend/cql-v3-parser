package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by sxend on 14/06/07.
 */
public class Register extends Request {
    public final List<String> events;

    public Register(List<String> events) {
        this.events = events;
    }

    @Override
    public byte[] toBody() {
        return Notations.toStringList(events);
    }
}
