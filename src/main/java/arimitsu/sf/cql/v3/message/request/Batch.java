package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Consistency;
import arimitsu.sf.cql.v3.message.Request;

import java.util.Optional;

import static arimitsu.sf.cql.v3.util.Notations.join;
import static arimitsu.sf.cql.v3.util.Notations.long2Bytes;
import static arimitsu.sf.cql.v3.util.Notations.short2Bytes;

/**
 * Created by sxend on 14/06/07.
 */
public class Batch extends Request {
    private final BatchType batchType;
    private final Consistency consistency;
    private final QueryFlags queryFlags;
    private final Query[] queries;
    private final Optional<Consistency> serialConsistency;
    private final Optional<Long> timestamp;

    // <type><n><query_1>...<query_n><consistency><queryFlags>[<serial_consistency>][<timestamp>]
    public Batch(BatchType batchType, Consistency consistency, QueryFlags queryFlags, Optional<Consistency> serialConsistency, Optional<Long> timestamp, Query... queries) {
        this.batchType = batchType;
        this.consistency = consistency;
        this.queries = queries;
        this.queryFlags = queryFlags;
        this.serialConsistency = serialConsistency;
        this.timestamp = timestamp;
    }

    @Override
    public byte[] toBody() {
        byte[] body;
        body = join(new byte[]{batchType.code}, short2Bytes(consistency.level));
        body = join(body, short2Bytes((short) queries.length));
        for (Query query : queries) {
            body = join(body, query.toBody());
        }
        body = join(body, short2Bytes(consistency.level));
        body = join(body, new byte[]{queryFlags.mask});
        if (serialConsistency.isPresent()) {
            body = join(body, short2Bytes(serialConsistency.get().level));
        }
        if (timestamp.isPresent()) {
            body = join(body, long2Bytes(timestamp.get()));
        }
        return body;
    }

    public static enum BatchType {
        LOGGED(0),
        UNLOGGED(1),
        COUNTER(2),;
        public final byte code;

        BatchType(int code) {
            this.code = (byte) code;
        }

    }
}
