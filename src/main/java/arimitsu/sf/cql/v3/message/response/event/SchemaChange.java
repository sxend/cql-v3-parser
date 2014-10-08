package arimitsu.sf.cql.v3.message.response.event;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.message.response.Event;
import arimitsu.sf.cql.v3.message.response.Result;
import arimitsu.sf.cql.v3.message.response.result.ResultKind;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class SchemaChange extends Response implements Result, Event {
    public final ChangeType changeType;
    public final String keySpace;
    public final String table;

    public SchemaChange(ByteBuffer buffer) {
        super(buffer);
        this.changeType = ChangeType.valueOf(Notations.getString(buffer));
        this.keySpace = Notations.getString(buffer);
        this.table = Notations.getString(buffer);
    }

    @Override
    public EventType getType() {
        return EventType.SCHEMA_CHANGE;
    }

    public static enum ChangeType {
        CREATED,
        UPDATED,
        DROPPED,;
    }

    @Override
    public ResultKind getResultKind() {
        return ResultKind.SCHEMA_CHANGE;
    }

}
