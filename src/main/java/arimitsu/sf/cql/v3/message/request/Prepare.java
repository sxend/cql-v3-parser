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
public class Prepare extends Request {
    public final String query;

    public Prepare(String query) {
        this.query = query;
    }

    @Override
    public byte[] toBody() {
        return Notations.toLongString(query);
    }
}
