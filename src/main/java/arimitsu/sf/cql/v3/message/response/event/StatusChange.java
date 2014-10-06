package arimitsu.sf.cql.v3.message.response.event;

import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.message.response.Event;
import arimitsu.sf.cql.v3.util.Notations;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class StatusChange extends Response implements Event {

    public final ChangeType changeType;
    public final InetAddress nodeAddress;

    public StatusChange(ByteBuffer buffer) {
        super(buffer);
        this.changeType = ChangeType.valueOf(Notations.getString(buffer));
        this.nodeAddress = Notations.getINet(buffer);
    }

    public static enum ChangeType {
        UP,
        DOWN,;
    }

    @Override
    public EventType getType() {
        return EventType.STATUS_CHANGE;
    }

}
