package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.*;
import arimitsu.sf.cql.v3.message.Void;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public interface Result {
    public abstract ResultKind getResultKind();

    public static class Factory {
        public static Result fromBuffer(ByteBuffer buffer) {
            int code = buffer.getInt();
            ResultKind kind = ResultKind.valueOf(code);
            switch (kind) {
                case VOID:
                    return (arimitsu.sf.cql.v3.message.Void) Void.fromBuffer(buffer);
                case ROWS:
                    return (Rows) Rows.fromBuffer(buffer);
                case PREPARED:
                    return (Prepared) Prepared.fromBuffer(buffer);
                case SET_KEYSPACE:
                    return (SetKeyspace) SetKeyspace.fromBuffer(buffer);
                case SCHEMA_CHANGE:
                    return (SchemaChange) SchemaChange.fromBuffer(buffer);
            }
            throw new RuntimeException("invalid kind code.");
        }
    }
}
