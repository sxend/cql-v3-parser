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

    Version(int number) {
        this.number = (byte) number;
    }

    private static final Map<Byte, Version> INDEX;

    static {
        Map<Byte, Version> index = new HashMap<>();
        for (Version version : values()) {
            index.put(version.number, version);
        }
        INDEX = Collections.unmodifiableMap(index);
    }

    public static Version valueOf(byte number) {
        if (!INDEX.containsKey(number)) {
            throw new IllegalArgumentException("invalid number: " + number);
        }
        return INDEX.get(number);
    }
}