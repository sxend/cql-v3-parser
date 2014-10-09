package arimitsu.sf.cql.v3;


import arimitsu.sf.cql.v3.compressor.LZ4Compressor;
import arimitsu.sf.cql.v3.compressor.NoneCompressor;
import arimitsu.sf.cql.v3.compressor.SnappyCompressor;

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

    public static Compression valueOf(byte[] name) {
        String strName = new String(name);
        for (Compression c : values()) {
            if (c.name.equals(strName)) return c;
        }
        return NONE;
    }

}

