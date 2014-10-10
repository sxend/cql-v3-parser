package arimitsu.sf.cql.v3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/04.
 * Flags applying to this frame. The queryFlags have the following meaning
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

    Flags(int value) {
        this.value = (byte) value;
    }

    private static final Map<Byte, Flags> INDEX;

    static {
        Map<Byte, Flags> index = new HashMap<>();
        for (Flags flags : values()) {
            index.put(flags.value, flags);
        }
        index.put(null, NONE);
        INDEX = Collections.unmodifiableMap(index);
    }

    public static Flags valueOf(byte value) {
        if (!INDEX.containsKey(value)) {
            throw new IllegalArgumentException("invalid value: " + value);
        }
        return INDEX.get(value);
    }
}
