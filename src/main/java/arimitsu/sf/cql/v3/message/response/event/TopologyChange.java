package arimitsu.sf.cql.v3.message.response.event;

import arimitsu.sf.cql.v3.message.response.Event;
import arimitsu.sf.cql.v3.util.Notations;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class TopologyChange implements Event {

    public final ChangeType changeType;
    public final InetAddress nodeAddress;

    public TopologyChange(ChangeType changeType, InetAddress nodeAddress) {
        this.changeType = changeType;
        this.nodeAddress = nodeAddress;
    }

    public static enum ChangeType {
        NEW_NODE,
        REMOVED_NODE,;
    }


    @Override
    public EventType getType() {
        return EventType.TOPOLOGY_CHANGE;
    }

    public static TopologyChange fromBuffer(ByteBuffer buffer) {
        ChangeType changeType = ChangeType.valueOf(Notations.getString(buffer));
        InetAddress nodeAddress = Notations.getINet(buffer);
        return new TopologyChange(changeType, nodeAddress);
    }
}
