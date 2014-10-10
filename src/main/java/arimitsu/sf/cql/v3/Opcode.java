package arimitsu.sf.cql.v3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/04.
 */
public enum Opcode {
    ERROR(0x00),
    STARTUP(0x01),
    READY(0x02),
    AUTHENTICATE(0x03),
    OPTIONS(0x05),
    SUPPORTED(0x06),
    QUERY(0x07),
    RESULT(0x08),
    PREPARE(0x09),
    EXECUTE(0x0A),
    REGISTER(0x0B),
    EVENT(0x0C),
    BATCH(0x0D),
    AUTH_CHALLENGE(0x0E),
    AUTH_RESPONSE(0x0F),
    AUTH_SUCCESS(0x10),;
    public final byte number;

    Opcode(int number) {
        this.number = (byte) number;
    }

    private static final Map<Byte, Opcode> INDEX;

    static {
        Map<Byte, Opcode> index = new HashMap<>();
        for (Opcode opcode : values()) {
            index.put(opcode.number, opcode);
        }
        INDEX = Collections.unmodifiableMap(index);
    }

    public static Opcode valueOf(byte number) {
        if (!INDEX.containsKey(number)) {
            throw new IllegalArgumentException("invalid number: " + number);
        }
        return INDEX.get(number);
    }
}
