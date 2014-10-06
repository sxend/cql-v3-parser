package arimitsu.sf.cql.v3.message.response.result;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.message.response.Result;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Void  extends Response implements Result {

    public Void(ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.VOID;
    }

}
