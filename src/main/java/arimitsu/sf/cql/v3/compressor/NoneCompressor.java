package arimitsu.sf.cql.v3.compressor;

import arimitsu.sf.cql.v3.Compressor;

/**
 * Created by sxend on 2014/07/30.
 */
public class NoneCompressor implements Compressor {
    @Override
    public int getCompressedLength(byte[] bytes) {
        return bytes.length;
    }

    @Override
    public byte[] compress(byte[] bytes) {
        return bytes;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return bytes;
    }
}
