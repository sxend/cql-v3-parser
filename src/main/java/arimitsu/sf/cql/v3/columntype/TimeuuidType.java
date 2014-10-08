package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.UUID;

public class TimeuuidType implements ColumnType {
    private static final Parser<UUID> PARSER = new Parser<UUID>() {
        @Override
        public UUID parse(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            byte[] bytes = Notations.getBytes(buffer, length);
            return Notations.toUUID(bytes);
        }
    };


    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
