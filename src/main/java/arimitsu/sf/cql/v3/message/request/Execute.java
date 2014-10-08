package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.message.Request;

import static arimitsu.sf.cql.v3.util.Notations.join;
import static arimitsu.sf.cql.v3.util.Notations.short2Bytes;

/**
 * Created by sxend on 14/06/07.
 */
public class Execute extends Request {
    public final byte[] id;
    public final QueryParameters parameters;

    public Execute(byte[] id, QueryParameters parameters) {
        this.id = id;
        this.parameters = parameters;
    }

    @Override
    public byte[] toBody() {
        byte[] paramBytes = parameters.toBytes();
        return join(join(short2Bytes((short) id.length), id), paramBytes);
    }
}
