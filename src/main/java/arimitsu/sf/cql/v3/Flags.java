package arimitsu.sf.cql.v3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/04.
 * Flags applying to this frame. The flags have the following meaning
 */
public enum Flags {

    NONE(0x00),
    /**
     * Compression flag.
     */
    COMPRESSION(0x01),
    /**
     * Tracing flag.
     */
    TRACING(0x02),
    /**
     * both positive
     */
    BOTH(COMPRESSION.value | TRACING.value),;

    public final byte value;

    private static final Map<Byte, Flags> FLAGS_MAP;

    static {
        Map<Byte, Flags> map = new HashMap<>();

        FLAGS_MAP = Collections.unmodifiableMap(map);
    }

    Flags(int value) {
        this.value = (byte) value;
    }

    public static Flags valueOf(byte value) {
        return FLAGS_MAP.get(value);
    }
}
