package arimitsu.sf.cql.v3.util;

import arimitsu.sf.cql.v3.Consistency;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sxend on 14/06/07.
 * describe the layout of the frame body for the messages in Section 4, we define the following
 */
public class Notations {

    public static final int INTEGER_BYTE_LEN = 4;
    public static final int LONG_BYTE_LEN = INTEGER_BYTE_LEN * 2;
    public static final String EMPTY = "";
    public static final Charset STRING_CHARSET = Charset.forName("UTF-8");

    public static class OptionNotation<A> {
        public final short id;
        public final A value;

        public OptionNotation(short id, A value) {
            this.id = id;
            this.value = value;
        }
    }

    public static interface OptionParser<A> {
        public A parse(ByteBuffer buffer);
    }

    public static short getShort(ByteBuffer buffer) {
        return buffer.getShort();
    }

    public static String getString(ByteBuffer buffer) {
        return getString(buffer, buffer.getShort());
    }

    public static long getLong(ByteBuffer buffer) {
        return getNumber(buffer, LONG_BYTE_LEN);
    }

    public static int getInt(ByteBuffer buffer) {
        return getNumber(buffer, INTEGER_BYTE_LEN).intValue();
    }

    private static Long getNumber(ByteBuffer buffer, int length) {
        long result = 0;
        for (int i = 0; i < length; i++) {
            result += ((0xff & buffer.get()) << ((length - i - 1) * length));
        }
        return result;
    }

    public static String getString(ByteBuffer buffer, int length) {
        if (length <= 0) return EMPTY;
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes);
    }

    public static String getLongString(ByteBuffer buffer) {
        return getString(buffer, buffer.getInt());
    }

    public static UUID getUUID(ByteBuffer buffer, int length) {
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return getUUIDFromBytes(bytes);
    }

    private static final Constructor<UUID> UUID_CONSTRUCTOR;

    static {
        Constructor<UUID> c = null;
        try {
            c = UUID.class.getDeclaredConstructor(byte[].class);
            c.setAccessible(true);
        } catch (NoSuchMethodException e) {
        }
        UUID_CONSTRUCTOR = c;
    }

    private static UUID getUUIDFromBytes(byte[] bytes) {
        try {
            return UUID_CONSTRUCTOR.newInstance(bytes);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getStringList(ByteBuffer buffer) {
        List<String> list = new ArrayList<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            list.add(getString(buffer));
        }
        return list;
    }

    public static List<String> getLongStringList(ByteBuffer buffer) {
        List<String> list = new ArrayList<>();
        for (int i = 0, length = buffer.getInt(); i < length; i++) {
            list.add(getLongString(buffer));
        }
        return list;
    }

    public static byte[] getBytes(ByteBuffer buffer) {
        return getBytes(buffer, buffer.getInt());
    }

    public static byte[] getShortBytes(ByteBuffer buffer) {
        return getBytes(buffer, buffer.getShort());
    }

    public static byte[] getBytes(ByteBuffer buffer, int length) {
        if (length > 0) {
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return bytes;
        } else {
            return new byte[0];
        }
    }

    public static <A> OptionNotation<A> getOption(ByteBuffer buffer, OptionParser<A> parser) {
        short id = getShort(buffer);
        return new OptionNotation<>(id, parser.parse(buffer));
    }

    public static <A> List<OptionNotation<A>> getOptionList(ByteBuffer buffer, OptionParser<A> parser) {
        List<OptionNotation<A>> list = new ArrayList<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            list.add(getOption(buffer, parser));
        }
        return list;
    }

    public static InetAddress getINet(ByteBuffer buffer, int length) {
        byte[] addrArea = new byte[length];
        buffer.get(addrArea);
        return getInetFromBytes(addrArea);
    }

    private static InetAddress getInetFromBytes(byte[] bytes) {
        try {
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static Consistency getConsistency(ByteBuffer buffer) {
        return Consistency.valueOf(getShort(buffer));
    }

    public static Map<String, String> getStringMap(ByteBuffer buffer) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            map.put(getString(buffer), getString(buffer));
        }
        return map;
    }

    public static Map<String, List<String>> getStringMultiMap(ByteBuffer buffer) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            map.put(getString(buffer), getStringList(buffer));
        }
        return map;
    }

    public static byte[] toStringList(List<String> list) {
        byte[] length = toShortBytes((short) list.size());
        byte[] bytes = new byte[]{};
        for (String str : list) {
            bytes = join(bytes, toStringBytesWithLength(str));
        }
        return join(length, bytes);
    }

    public static byte[] toStringMap(Map<String, String> maps) {
        byte[] mapLength = toShortBytes((short) maps.size());
        byte[] result = new byte[0];
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            result = join(result, join(toStringBytesWithLength(entry.getKey()), toStringBytesWithLength(entry.getValue())));
        }
        return join(mapLength, result);
    }

    public static byte[] toLongStringWithIntLength(String str) {
        byte[] bytes = toStringBytes(str);
        return join(toIntBytes(bytes.length), bytes);
    }

    public static byte[] toStringBytesWithLength(String str) {
        byte[] bytes = toStringBytes(str);
        int length = bytes.length;
        return join(toShortBytes((short) length), bytes);
    }

    public static byte[] toStringBytes(String str) {
        byte[] bytes;
        try {
            bytes = str.getBytes(STRING_CHARSET);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        return bytes;
    }

    public static byte[] toShortBytes(short s) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (0xff & (s >>> 8));
        bytes[1] = (byte) (0xff & s);
        return bytes;
    }

    public static byte[] toIntBytes(int s) {
        byte[] bytes = new byte[INTEGER_BYTE_LEN];
        bytes[0] = (byte) (0xff & (s >>> 24));
        bytes[1] = (byte) (0xff & (s >>> 16));
        bytes[2] = (byte) (0xff & (s >>> 8));
        bytes[3] = (byte) (0xff & s);
        return bytes;
    }

    public static byte[] toLongBytes(long s) {
        byte[] bytes = new byte[LONG_BYTE_LEN];
        bytes[0] = (byte) (0xff & (s >>> 56));
        bytes[1] = (byte) (0xff & (s >>> 48));
        bytes[2] = (byte) (0xff & (s >>> 40));
        bytes[3] = (byte) (0xff & (s >>> 32));
        bytes[4] = (byte) (0xff & (s >>> 24));
        bytes[5] = (byte) (0xff & (s >>> 16));
        bytes[6] = (byte) (0xff & (s >>> 8));
        bytes[7] = (byte) (0xff & s);
        return bytes;
    }

    public static byte[] toINetBytes(InetAddress inetAddress) {
        return inetAddress.getAddress();
    }

    public static byte[] toUUIDBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static byte[] join(byte[] byte1, byte[] byte2) {
        byte[] resultBytes = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, resultBytes, 0, byte1.length);
        System.arraycopy(byte2, 0, resultBytes, byte1.length, byte2.length);
        return resultBytes;
    }

}
