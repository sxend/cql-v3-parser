package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 2014/07/30.
 */
public interface Compressor {

    public byte[] compress(byte[] bytes);

    public byte[] decompress(byte[] bytes);
}
