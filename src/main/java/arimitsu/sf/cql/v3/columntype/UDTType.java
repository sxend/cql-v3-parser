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
            return ColumnType.Factory.fromBuffer(buffer);
        }
    };

    public UDTType(ByteBuffer buffer) {
        this.keySpace = Notations.getString(buffer);
        this.udtName = Notations.getString(buffer);
        this.fieldCount = Notations.getShort(buffer);
        Map<String, Notations.OptionNotation<ColumnType>> map = new HashMap<>();
        for (int i = 0; i < fieldCount; i++) {
            map.put(Notations.getString(buffer), Notations.getOption(buffer, COLUMN_TYPE_OPTION_PARSER));
        }
        this.udtTypes = map;
    }

    @Override
    public Serializer<?> getSerializer() {
        return null;
    }
}
