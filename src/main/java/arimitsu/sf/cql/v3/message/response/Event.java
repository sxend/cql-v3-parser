package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.message.response.event.EventType;
import arimitsu.sf.cql.v3.message.response.event.SchemaChange;
import arimitsu.sf.cql.v3.message.response.event.StatusChange;
import arimitsu.sf.cql.v3.message.response.event.TopologyChange;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public interface Event {
    public EventType getType();

    public static class Factory {
        public static Event fromBuffer(ByteBuffer buffer) {
            EventType eventType = EventType.valueOf(Notations.getString(buffer));
            switch (eventType) {
                case TOPOLOGY_CHANGE:
                    return new TopologyChange(buffer);
                case STATUS_CHANGE:
                    return new StatusChange(buffer);
                case SCHEMA_CHANGE:
                    return new SchemaChange(buffer);
            }
            throw new IllegalStateException("invalid event type");
        }
    }
}
