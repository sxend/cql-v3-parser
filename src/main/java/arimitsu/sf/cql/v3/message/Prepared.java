package arimitsu.sf.cql.v3.message;

import arimitsu.sf.cql.v3.message.response.Result;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Prepared implements Result {
    // <id><metadata><result_metadata>
    public final byte[] id;
    public final Metadata metadata;
    public final Metadata resultMetadata;

    public Prepared(byte[] id, Metadata metadata, Metadata resultMetadata) {
        this.id = id;
        this.metadata = metadata;
        this.resultMetadata = resultMetadata;
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.PREPARED;
    }

    public static Prepared fromBuffer(ByteBuffer buffer) {
        return new Prepared(Notations.getShortBytes(buffer), Metadata.fromBuffer(buffer), Metadata.fromBuffer(buffer));
    }
}
