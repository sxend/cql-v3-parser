package arimitsu.sf.cql.v3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/04.
 * Cql Request/Response protocol version
 */
public enum Version {
    /**
     * Request frame for this protocol version
     */
    REQUEST(0x3),
    /**
     * Response frame for this protocol version
     */
    RESPONSE(0x83),;
    public final byte number;

    private static final Map<Byte, Version> VERSION_MAP;

    static {
        Map<Byte, Version> map = new HashMap<>();
        for (Version version : values()) {
            map.put(version.number, version);
        }
        VERSION_MAP = Collections.unmodifiableMap(map);
    }

    Version(int number) {
        this.number = (byte) number;
    }

    public static Version valueOf(byte number) {
        return VERSION_MAP.get(number);
    }
}