package arimitsu.sf.cql.v3.compressor;

import arimitsu.sf.cql.v3.Compressor;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by sxend on 2014/07/30.
 */
public class SnappyCompressor implements Compressor {

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
