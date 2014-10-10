package arimitsu.sf.cql.v3.message.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/07/06.
 */
public enum MetadataFlags {
    GLOBAL_TABLES_SPEC(0x0001),
    HAS_MORE_PAGES(0x0002),
    NO_METADATA(0x0004);

    public final int mask;

    MetadataFlags(int mask) {
        this.mask = mask;
    }

    private static final Map<Integer, MetadataFlags> INDEX;

    static {
        Map<Integer, MetadataFlags> index = new HashMap<>();
        for (MetadataFlags metadataFlags : values()) {
            index.put(metadataFlags.mask, metadataFlags);
        }
        INDEX = Collections.unmodifiableMap(index);
    }

    public static MetadataFlags valueOf(int mask) {
        if (!INDEX.containsKey(mask)) {
            throw new IllegalArgumentException("invalid mask: " + mask);
        }
        return INDEX.get(mask);
    }
}
