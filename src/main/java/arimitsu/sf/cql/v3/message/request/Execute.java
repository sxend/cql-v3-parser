package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.message.response.Result;

import static arimitsu.sf.cql.v3.util.Notations.join;
import static arimitsu.sf.cql.v3.util.Notations.short2Bytes;

/**
 * Created by sxend on 14/06/07.
 */
public class Execute implements Request<Result> {
    public final short streamId;
    public final Flags flags;
    public final byte[] id;
    public final QueryParameters parameters;

    public Execute(short streamId, Flags flags, byte[] id, QueryParameters parameters) {
        this.streamId = streamId;
        this.flags = flags;
        this.id = id;
        this.parameters = parameters;
    }

    @Override
    public Frame toFrame() {
        byte[] paramBytes = parameters.toBytes();
        byte[] body = join(join(short2Bytes((short) id.length), id), paramBytes);
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.EXECUTE, body.length), body);
    }
}
