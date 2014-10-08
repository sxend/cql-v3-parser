package arimitsu.sf.cql.v3.message.response.result;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.message.response.Metadata;
import arimitsu.sf.cql.v3.message.response.Result;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Prepared extends Response implements Result {
    // <id><metadata><result_metadata>
    public final byte[] id;
    public final Metadata metadata;
    public final Metadata resultMetadata;

    public Prepared(ByteBuffer buffer) {
        super(buffer);
        this.id = Notations.getShortBytes(buffer);
        this.metadata = Metadata.fromBuffer(buffer);
        this.resultMetadata = Metadata.fromBuffer(buffer);
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.PREPARED;
    }

}
