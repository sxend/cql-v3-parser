package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.Response;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class AuthSuccess  extends Response {

    public AuthSuccess(ByteBuffer buffer) {
        super(buffer);
    }

}
