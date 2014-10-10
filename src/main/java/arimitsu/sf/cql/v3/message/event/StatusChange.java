package arimitsu.sf.cql.v3.message.event;

import arimitsu.sf.cql.v3.message.Event;
import arimitsu.sf.cql.v3.util.Notations;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class StatusChange implements Event {

    public final ChangeType changeType;
    public final InetAddress nodeAddress;

    public StatusChange(ByteBuffer buffer) {
        this.changeType = ChangeType.valueOf(Notations.getString(buffer));
        this.nodeAddress = Notations.getINet(buffer, buffer.get());
    }

    public static enum ChangeType {
        UP,
        DOWN,;
    }

    @Override
    public EventType getEventType() {
        return EventType.STATUS_CHANGE;
    }

}
