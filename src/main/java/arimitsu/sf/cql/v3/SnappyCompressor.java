package arimitsu.sf.cql.v3;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by sxend on 2014/07/30.
 */
public class SnappyCompressor implements Compressor {
    @Override
    public int getCompressedLength(byte[] bytes) {
        return bytes.length;
    }

    @Override
    public byte[] compress(byte[] bytes) {
        try {
            return Snappy.compress(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        try {
            return Snappy.uncompress(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
