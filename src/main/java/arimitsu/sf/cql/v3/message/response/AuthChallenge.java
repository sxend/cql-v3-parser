package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.Response;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class AuthChallenge extends Response {

    public AuthChallenge(ByteBuffer buffer) {
        super(buffer);
    }
}
