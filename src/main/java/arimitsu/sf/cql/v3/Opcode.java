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
    private static final Map<Byte, Opcode> OPCODE_MAP;

    static {
        Map<Byte, Opcode> map = new HashMap<>();
        for (Opcode opcode : values()) {
            map.put(opcode.number, opcode);
        }
        OPCODE_MAP = Collections.unmodifiableMap(map);
    }

    Opcode(int number) {
        this.number = (byte) number;
    }

    public static Opcode valueOf(byte number) {
        return OPCODE_MAP.get(number);
    }
}
