package arimitsu.sf.cql.v3.columntype;


import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {

    Serializer<?> getSerializer();

    public static class Factory {
        public static ColumnType fromBuffer(ByteBuffer buffer) {
            ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.valueOf(Notations.getShort(buffer));
            switch (columnTypeEnum) {
                case ASCII:
                    return new AsciiType();
                case BIGINT:
                    return new BigintType();
                case BLOB:
                    return new BlobType();
                case BOOLEAN:
                    return new BooleanType();
                case COUNTER:
                    return new CounterType();
                case DECIMAL:
                    return new DecimalType();
                case DOUBLE:
                    return new DoubleType();
                case FLOAT:
                    return new FloatType();
                case INT:
                    return new IntType();
                case TIMESTAMP:
                    return new TimestampType();
                case UUID:
                    return new UuidType();
                case VARCHAR:
                    return new VarcharType();
                case VARINT:
                    return new VarintType();
                case TIMEUUID:
                    return new TimeuuidType();
                case INET:
                    return new InetType();
                case LIST:
                    return new ListType(fromBuffer(buffer));
                case SET:
                    return new SetType(fromBuffer(buffer));
                case MAP:
                    return new MapType(fromBuffer(buffer), fromBuffer(buffer));
                case CUSTOM:
                    return new CustomType();
                case UDT:
                    return new UDTType(buffer);
                case TUPLE:
                    return new TupleType(buffer);
                default:
                    throw new UnsupportedOperationException("not implementation.");
            }
        }
    }

    static enum ColumnTypeEnum {
        CUSTOM(0x0000),
        ASCII(0x0001),
        BIGINT(0x0002),
        BLOB(0x0003),
        BOOLEAN(0x0004),
        COUNTER(0x0005),
        DECIMAL(0x0006),
        DOUBLE(0x0007),
        FLOAT(0x0008),
        INT(0x0009),
        TIMESTAMP(0x000B),
        UUID(0x000C),
        VARCHAR(0x000D),
        VARINT(0x000E),
        TIMEUUID(0x000F),
        INET(0x0010),
        LIST(0x0020),
        MAP(0x0021),
        SET(0x0022),
        UDT(0x0030),
        TUPLE(0x0031),;
        public final short id;

        ColumnTypeEnum(int id) {
            this.id = (short) id;
        }

        private static final Map<Short, ColumnTypeEnum> INDEX;

        static {
            Map<Short, ColumnTypeEnum> index = new HashMap<>();
            for (ColumnTypeEnum columnTypeEnum : values()) {
                index.put(columnTypeEnum.id, columnTypeEnum);
            }
            INDEX = Collections.unmodifiableMap(index);
        }

        public static ColumnTypeEnum valueOf(short id) {
            if (!INDEX.containsKey(id)) {
                throw new IllegalArgumentException("invalid id: " + id);
            }
            return INDEX.get(id);
        }

    }

}

