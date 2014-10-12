package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 2014/10/09.
 */
public class UDTType implements ColumnType {

    public final String keySpace;
    public final String udtName;
    public final int fieldCount;
    public final Map<String, Notations.OptionNotation<ColumnType>> udtTypes;
    public static final Notations.OptionParser<ColumnType> COLUMN_TYPE_OPTION_PARSER = new Notations.OptionParser<ColumnType>() {
        @Override
        public ColumnType parse(ByteBuffer buffer) {
            return ColumnTypes.valueOf(Notations.getShort(buffer)).builder.build(buffer);
        }
    };

    public UDTType(String keySpace, String udtName, int fieldCount, Map<String, Notations.OptionNotation<ColumnType>> udtTypes) {
        this.keySpace = keySpace;
        this.udtName = udtName;
        this.fieldCount = fieldCount;
        this.udtTypes = udtTypes;
    }

    @Override
    public Serializer<?> getSerializer() {
        return null;
    }

    public static class Builder implements ColumnTypeBuilder<UDTType> {
        @Override
        public UDTType build(ByteBuffer buffer) {
            String keySpace = Notations.getString(buffer);
            String udtName = Notations.getString(buffer);
            int fieldCount = Notations.getShort(buffer);
            Map<String, Notations.OptionNotation<ColumnType>> udtTypes = new HashMap<>();
            for (int i = 0; i < fieldCount; i++) {
                udtTypes.put(Notations.getString(buffer), Notations.getOption(buffer, UDTType.COLUMN_TYPE_OPTION_PARSER));
            }
            return new UDTType(keySpace, udtName, fieldCount, udtTypes);
        }

        @Override
        public UDTType build(ColumnType... childTypes) {
            return null;
        }
    }
}
