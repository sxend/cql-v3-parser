package arimitsu.sf.cql.v3.message;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class AuthSuccess {

    public static AuthSuccess fromBuffer(ByteBuffer buffer) {
        return new AuthSuccess();
    }
}
