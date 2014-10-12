package arimitsu.sf.cql.v3.columntype;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/10/12.
 */
public enum ColumnTypes {
    CUSTOM(0x0000, new CustomType.Builder()),
    ASCII(0x0001, new AsciiType.Builder()),
    BIGINT(0x0002, new BigintType.Builder()),
    BLOB(0x0003, new BlobType.Builder()),
    BOOLEAN(0x0004, new BooleanType.Builder()),
    COUNTER(0x0005, new CounterType.Builder()),
    DECIMAL(0x0006, new DecimalType.Builder()),
    DOUBLE(0x0007, new DoubleType.Builder()),
    FLOAT(0x0008, new FloatType.Builder()),
    INT(0x0009, new IntType.Builder()),
    TIMESTAMP(0x000B, new TimestampType.Builder()),
    UUID(0x000C, new UuidType.Builder()),
    VARCHAR(0x000D, new VarcharType.Builder()),
    VARINT(0x000E, new VarintType.Builder()),
    TIMEUUID(0x000F, new TimeuuidType.Builder()),
    INET(0x0010, new InetType.Builder()),
    LIST(0x0020, new ListType.Builder()),
    MAP(0x0021, new MapType.Builder()),
    SET(0x0022, new SetType.Builder()),
    UDT(0x0030, new UDTType.Builder()),
    TUPLE(0x0031, new TupleType.Builder()),;

    public final short id;
    public final ColumnType.ColumnTypeBuilder builder;

    ColumnTypes(int id, ColumnType.ColumnTypeBuilder builder) {
        this.id = (short) id;
        this.builder = builder;
    }

    private static final Map<Short, ColumnTypes> INDEX;

    static {
        Map<Short, ColumnTypes> index = new HashMap<>();
        for (ColumnTypes columnTypes : values()) {
            index.put(columnTypes.id, columnTypes);
        }
        INDEX = Collections.unmodifiableMap(index);
    }

    public static ColumnTypes valueOf(short id) {
        if (!INDEX.containsKey(id)) {
            throw new IllegalArgumentException("invalid id: " + id);
        }
        return INDEX.get(id);
    }

}
