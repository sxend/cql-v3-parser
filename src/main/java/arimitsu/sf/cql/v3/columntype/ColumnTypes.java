package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/10/12.
 */
public enum ColumnTypes {
    CUSTOM(0x0000, (buffer) -> new CustomType()),
    ASCII(0x0001, (buffer) -> new AsciiType()),
    BIGINT(0x0002, (buffer) -> new BigintType()),
    BLOB(0x0003, (buffer) -> new BlobType()),
    BOOLEAN(0x0004, (buffer) -> new BooleanType()),
    COUNTER(0x0005, (buffer) -> new CounterType()),
    DECIMAL(0x0006, (buffer) -> new DecimalType()),
    DOUBLE(0x0007, (buffer) -> new DoubleType()),
    FLOAT(0x0008, (buffer) -> new FloatType()),
    INT(0x0009, (buffer) -> new IntType()),
    TIMESTAMP(0x000B, (buffer) -> new TimestampType()),
    UUID(0x000C, (buffer) -> new UuidType()),
    VARCHAR(0x000D, (buffer) -> new VarcharType()),
    VARINT(0x000E, (buffer) -> new VarintType()),
    TIMEUUID(0x000F, (buffer) -> new TimeuuidType()),
    INET(0x0010, (buffer) -> new InetType()),
    LIST(0x0020, (buffer) -> {
        ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
        return new ListType(valueType.builder.build(buffer));
    }),
    MAP(0x0021, (buffer) -> {
        ColumnTypes keyType = ColumnTypes.valueOf(Notations.getShort(buffer));
        ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
        return new MapType(keyType.builder.build(buffer), valueType.builder.build(buffer));
    }),
    SET(0x0022, (buffer) -> {
        ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
        return new SetType(valueType.builder.build(buffer));
    }),
    UDT(0x0030, (buffer) -> {
        String keySpace = Notations.getString(buffer);
        String udtName = Notations.getString(buffer);
        int fieldCount = Notations.getShort(buffer);
        Map<String, Notations.OptionNotation<ColumnType>> udtTypes = new HashMap<>();
        for (int i = 0; i < fieldCount; i++) {
            udtTypes.put(Notations.getString(buffer), Notations.getOption(buffer, UDTType.COLUMN_TYPE_OPTION_PARSER));
        }
        return new UDTType(keySpace, udtName, fieldCount, udtTypes);
    }),
    TUPLE(0x0031, (buffer) -> {
        int length = Notations.getShort(buffer);
        List<ColumnType> columnTypes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            ColumnTypes valueType = ColumnTypes.valueOf(Notations.getShort(buffer));
            columnTypes.add(valueType.builder.build(buffer));
        }
        return new TupleType(columnTypes.toArray(new ColumnType[columnTypes.size()]));
    }),;

    @FunctionalInterface
    public static interface ColumnTypeBuilder {
        ColumnType build(ByteBuffer buffer);
    }

    public final short id;
    public final ColumnTypeBuilder builder;

    ColumnTypes(int id, ColumnTypeBuilder builder) {
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
