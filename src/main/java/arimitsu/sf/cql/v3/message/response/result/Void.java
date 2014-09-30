package arimitsu.sf.cql.v3.message.response.result;

import arimitsu.sf.cql.v3.message.response.Result;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Void implements Result {
    public static final Void INSTANCE = new Void();

    private Void() {
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.VOID;
    }

    public static Void fromBuffer(ByteBuffer buffer) {
        return Void.INSTANCE;
    }
}
