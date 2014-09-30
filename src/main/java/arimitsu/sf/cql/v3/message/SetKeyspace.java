package arimitsu.sf.cql.v3.message;

import arimitsu.sf.cql.v3.message.response.Result;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;


/**
 * Created by sxend on 2014/06/11.
 */
public class SetKeyspace implements Result {

    public final String keySpace;

    public SetKeyspace(String keySpace) {
        this.keySpace = keySpace;
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.SET_KEYSPACE;
    }

    public static SetKeyspace fromBuffer(ByteBuffer buffer) {
        return new SetKeyspace(Notations.getString(buffer));
    }
}
