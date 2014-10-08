package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class Authenticate extends Response {
    public final String className;

    public Authenticate(ByteBuffer buffer) {
        super(buffer);
        this.className = Notations.getString(buffer);
    }

}
