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
public class AuthResponse extends Request {
    public final byte[] token;

    public AuthResponse(byte[] token) {
        this.token = token;
    }

    @Override
    public byte[] toBody() {
        return token;
    }
}
