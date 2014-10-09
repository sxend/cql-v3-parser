package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.message.event.SchemaChange;
import arimitsu.sf.cql.v3.message.response.result.Prepared;
import arimitsu.sf.cql.v3.message.response.result.ResultKind;
import arimitsu.sf.cql.v3.message.response.result.Rows;
import arimitsu.sf.cql.v3.message.response.result.SetKeyspace;
import arimitsu.sf.cql.v3.message.response.result.Void;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public abstract class Result extends Response {
    public Result(ByteBuffer buffer) {
        super(buffer);
    }

    public abstract ResultKind getResultKind();

    public static class Factory {
        public static Result fromBuffer(ByteBuffer buffer) {
            int code = buffer.getInt();
            ResultKind kind = ResultKind.valueOf(code);
            switch (kind) {
                case VOID:
                    return (Void) new Void(buffer);
                case ROWS:
                    return (Rows) new Rows(buffer);
                case PREPARED:
                    return (Prepared) new Prepared(buffer);
                case SET_KEYSPACE:
                    return (SetKeyspace) new SetKeyspace(buffer);
                case SCHEMA_CHANGE:
                    return (SchemaChange) new SchemaChange(buffer);
            }
            throw new RuntimeException("invalid kind code.");
        }
    }

}
