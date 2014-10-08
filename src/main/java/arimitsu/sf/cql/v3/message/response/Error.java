package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;


/**
 * Created by sxend on 14/06/07.
 */
public class Error extends Response {
    public final ErrorCodes code;
    public final String message;

    public Error(ByteBuffer buffer) {
        super(buffer);
        this.code = ErrorCodes.valueOf(buffer.getInt());
        this.message = Notations.getString(buffer);
    }

}
