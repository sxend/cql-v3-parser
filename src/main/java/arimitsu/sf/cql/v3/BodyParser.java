package arimitsu.sf.cql.v3;

import arimitsu.sf.cql.v3.message.Event;
import arimitsu.sf.cql.v3.message.Message;
import arimitsu.sf.cql.v3.message.response.AuthSuccess;
import arimitsu.sf.cql.v3.message.response.Authenticate;
import arimitsu.sf.cql.v3.message.response.Ready;
import arimitsu.sf.cql.v3.message.response.Result;
import arimitsu.sf.cql.v3.message.response.Supported;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/10/08.
 */
public class BodyParser {
    public Message toMessage(Opcode opcode, byte[] body) {
        ByteBuffer buffer = ByteBuffer.wrap(body);
        switch (opcode) {
            case RESULT:
                return Result.Builder.fromBuffer(buffer);
            case EVENT:
                return Event.Builder.fromBuffer(buffer);
            case READY:
                return new Ready(buffer);
            case AUTHENTICATE:
                return new Authenticate(buffer);
            case AUTH_SUCCESS:
                return new AuthSuccess(buffer);
            case SUPPORTED:
                return new Supported(buffer);
            case ERROR:
                return new arimitsu.sf.cql.v3.message.response.Error(buffer);
            default:
                throw new UnsupportedOperationException("illegal opcode.");
        }
    }
}
