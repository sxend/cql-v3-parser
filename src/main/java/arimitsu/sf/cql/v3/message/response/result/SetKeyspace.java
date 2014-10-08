package arimitsu.sf.cql.v3.message.response.result;

import arimitsu.sf.cql.v3.message.response.Result;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;


/**
 * Created by sxend on 2014/06/11.
 */
public class SetKeyspace extends Result {

    public final String keySpace;

    public SetKeyspace(ByteBuffer buffer) {
        super(buffer);
        this.keySpace = Notations.getString(buffer);
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.SET_KEYSPACE;
    }

}
