package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Frame.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

/**
 * Created by sxend on 14/06/07.
 */
public class Query implements Request {

    public final short streamId;
    public final Flags flags;
    public final String string;
    public final QueryParameters parameters;

    public Query(short streamId, Flags flags, String string, QueryParameters parameters) {
        this.streamId = streamId;
        this.flags = flags;
        this.string = string;
        this.parameters = parameters;
    }

    public static enum QueryFlags {
        VALUES(0x01),
        SKIP_METADATA(0x02),
        PAGE_SIZE(0x04),
        WITH_PAGING_STATE(0x08),
        WITH_SERIAL_CONSISTENCY(0x10),
        WITH_DEFAULT_TIMESTAMP(0x20),
        WITH_NAMES(0x40),;
        public final byte mask;

        QueryFlags(int mask) {
            this.mask = (byte) mask;
        }
    }

    @Override
    public Frame toFrame() {
        byte[] query = Notations.toLongString(this.string);
        byte[] parameters = this.parameters.toBytes();
        byte[] body = Notations.join(query, parameters);
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.QUERY, body.length), body);
    }
}
