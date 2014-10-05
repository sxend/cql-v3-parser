package arimitsu.sf.cql.v3.message.response;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class AuthSuccess {

    public static AuthSuccess fromBuffer(ByteBuffer buffer) {
        return new AuthSuccess();
    }
}
