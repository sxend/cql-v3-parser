package arimitsu.sf.cql.v3.compressor;

import arimitsu.sf.cql.v3.Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.util.Arrays;

/**
 * Created by sxend on 2014/07/30.
 */
public class LZ4Compressor implements Compressor {
    private static final int INTEGER_BYTE = 4;

    @Override
    public int getCompressedLength(byte[] bytes) {
        return bytes.length;
    }

    @Override
    public byte[] compress(byte[] bytes) {
        int length = bytes.length;
        LZ4Factory factory = LZ4Factory.fastestInstance();
        net.jpountz.lz4.LZ4Compressor compressor = factory.fastCompressor();
        int maxLength = compressor.maxCompressedLength(length);
        byte[] compressed = new byte[maxLength + INTEGER_BYTE];
        compressed[0] = (byte) (0xff & (length >>> 24));
        compressed[1] = (byte) (0xff & (length >>> 16));
        compressed[2] = (byte) (0xff & (length >>> 8));
        compressed[3] = (byte) (0xff & length);

        int result = compressor.compress(bytes, 0, length, compressed, INTEGER_BYTE, maxLength);
        compressed = Arrays.copyOf(compressed, result + INTEGER_BYTE);
        return compressed;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        int decompressedLength = ((bytes[0] & 0xff) << 24) +
                ((bytes[1] & 0xff) << 16) +
                ((bytes[2] & 0xff) << 8) +
                (bytes[3] & 0xff);
        byte[] decompressed = new byte[decompressedLength];
        decompressor.decompress(bytes, 4, decompressed, 0, decompressedLength);
        return decompressed;
    }
}
