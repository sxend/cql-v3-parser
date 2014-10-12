package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.Consistency;
import arimitsu.sf.cql.v3.columntype.ColumnType;
import arimitsu.sf.cql.v3.columntype.Serializer;
import arimitsu.sf.cql.v3.util.Notations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static arimitsu.sf.cql.v3.util.Notations.join;
import static arimitsu.sf.cql.v3.util.Notations.toIntBytes;
import static arimitsu.sf.cql.v3.util.Notations.toLongBytes;
import static arimitsu.sf.cql.v3.util.Notations.toShortBytes;

/**
 * Created by sxend on 14/06/12.
 */
public class QueryParameters {
    // <consistency><flags>[<n>[name_1]<value_1>...[name_n]<value_n>][<result_page_size>][<paging_state>][<serial_consistency>][<timestamp>]
    public final Consistency consistency;
    public final byte queryFlags;
    public final Values values;
    public final Optional<Integer> resultPageSize;
    public final Optional<byte[]> pagingState;
    public final Optional<Consistency> serialConsistency;
    public final Optional<Long> timestamp;


    public QueryParameters(Consistency consistency,
                           byte flags,
                           Values values,
                           Optional<Integer> resultPageSize,
                           Optional<byte[]> pagingState,
                           Optional<Consistency> serialConsistency,
                           Optional<Long> timestamp) {
        this.consistency = consistency;
        this.queryFlags = flags;
        this.values = values;
        this.resultPageSize = resultPageSize;
        this.pagingState = pagingState;
        this.serialConsistency = serialConsistency;
        this.timestamp = timestamp;
    }

    public byte[] toBytes() {
        byte[] bytes = join(toShortBytes(consistency.level), new byte[]{queryFlags});
        bytes = join(bytes, values.toBytes());
        if (serialConsistency.isPresent()) {
            bytes = join(bytes, toLongBytes(serialConsistency.get().level));
        }
        if (timestamp.isPresent()) {
            bytes = join(bytes, toLongBytes(timestamp.get()));
        }
        return bytes;

    }

    public static interface Values {
        public byte[] toBytes();
    }

    public static class NamedValues implements Values {
        private final Map<String, byte[]> map = new HashMap<>();

        public void put(ColumnType columnType, String name, Object value) {
            map.put(name, ((Serializer<Object>) columnType.getSerializer()).serialize(value));
        }

        @Override
        public byte[] toBytes() {
            byte[] result = new byte[0];
            for (Map.Entry<String, byte[]> entry : map.entrySet()) {
                result = join(result, join(Notations.toStringBytes(entry.getKey()), join(toIntBytes(entry.getValue().length), entry.getValue())));
            }
            return result;
        }
    }

    public static class ListValues implements Values {
        private final List<byte[]> list = new ArrayList<>();

        public void put(ColumnType columnType, Object object) {
            list.add(((Serializer<Object>) columnType.getSerializer()).serialize(object));
        }

        @Override
        public byte[] toBytes() {
            byte[] bytes = new byte[0];
            for (byte[] b : list) {
                bytes = join(bytes, b);
            }
            bytes = join(toShortBytes((short) list.size()), bytes);
            return bytes;
        }
    }

    public static class Builder {
        private Consistency consistency;
        private byte flags;
        private Values values;
        private Optional<Integer> resultPageSize = Optional.empty();
        private Optional<byte[]> pagingState = Optional.empty();
        private Optional<Consistency> serialConsistency = Optional.empty();
        private Optional<Long> timestamp = Optional.empty();

        public Builder setConsistency(Consistency consistency) {
            this.consistency = consistency;
            return this;
        }

        public Builder setFlags(byte flags) {
            this.flags = flags;
            return this;
        }

        public Builder setValues(Values values) {
            this.values = values;
            return this;
        }

        public Builder setResultPageSize(Optional<Integer> resultPageSize) {
            this.resultPageSize = resultPageSize;
            return this;
        }

        public Builder setPagingState(Optional<byte[]> pagingState) {
            this.pagingState = pagingState;
            return this;
        }

        public Builder setSerialConsistency(Optional<Consistency> serialConsistency) {
            this.serialConsistency = serialConsistency;
            return this;
        }

        public Builder setTimestamp(Optional<Long> timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public QueryParameters build() {
            return new QueryParameters(consistency, flags, values, resultPageSize, pagingState, serialConsistency, timestamp);
        }
    }
}
