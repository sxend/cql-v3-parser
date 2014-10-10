package arimitsu.sf.cql.v3.message.event;

import arimitsu.sf.cql.v3.message.Event;
import arimitsu.sf.cql.v3.util.Notations;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class TopologyChange implements Event {

    public final ChangeType changeType;
    public final InetAddress nodeAddress;

    public TopologyChange(ByteBuffer buffer) {
        this.changeType = ChangeType.valueOf(Notations.getString(buffer));
        this.nodeAddress = Notations.getINet(buffer, buffer.get());
    }

    public static enum ChangeType {
        NEW_NODE,
        REMOVED_NODE,;
    }


    @Override
    public EventType getEventType() {
        return EventType.TOPOLOGY_CHANGE;
    }

}
