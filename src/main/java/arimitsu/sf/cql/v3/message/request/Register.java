package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

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
