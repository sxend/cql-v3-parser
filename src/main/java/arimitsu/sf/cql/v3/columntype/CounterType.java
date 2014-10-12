package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class CounterType implements ColumnType {

    @Override
    public Serializer<?> getSerializer() {
        return null;
    }

    public static class Builder implements ColumnTypeBuilder<CounterType> {
        @Override
        public CounterType build(ByteBuffer buffer) {
            return new CounterType();
        }

        @Override
        public CounterType build(ColumnType... childTypes) {
            return new CounterType();
        }
    }
}
