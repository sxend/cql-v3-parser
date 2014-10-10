package arimitsu.sf.cql.v3;


import arimitsu.sf.cql.v3.compressor.LZ4Compressor;
import arimitsu.sf.cql.v3.compressor.NoneCompressor;
import arimitsu.sf.cql.v3.compressor.SnappyCompressor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/08.
 */
public enum Compression {

    NONE("", new NoneCompressor()),
    LZ4("lz4", new LZ4Compressor()),
    SNAPPY("snappy", new SnappyCompressor()),;

    public final String name;

    public final Compressor compressor;

    Compression(String name, Compressor compressor) {
        this.name = name;
        this.compressor = compressor;
    }

    private static Map<String, Compression> INDEX;

    static {
        Map<String, Compression> index = new HashMap<>();
        for (Compression compression : values()) {
            index.put(compression.name, compression);
        }
        INDEX = Collections.unmodifiableMap(index);
    }

    public static Compression valueOf(byte[] name) {
        String nameString = new String(name);
        if (!INDEX.containsKey(nameString)) {
            throw new IllegalArgumentException("invalid name: " + nameString);
        }
        return INDEX.get(nameString);
    }

}

