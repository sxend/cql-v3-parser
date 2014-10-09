package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxend on 2014/10/09.
 */
public class TupleType implements ColumnType {
    public final int length;
    public final ColumnType[] valueTypes;
    private final Parser<List<Object>> PARSER = new Parser<List<Object>>() {
        @Override
        public List<Object> parse(ByteBuffer buffer) {
            List<Object> list = new ArrayList<>();
            for (int i = 0, length = valueTypes.length; i < length; i++) {
                list.add(valueTypes[i].getParser().parse(buffer));
            }
            return list;
        }
    };

    public TupleType(ByteBuffer buffer) {
        this.length = Notations.getShort(buffer);
        List<ColumnType> columnTypes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            columnTypes.add(ColumnType.Factory.fromBuffer(buffer));
        }
        this.valueTypes = columnTypes.toArray(new ColumnType[columnTypes.size()]);
    }

    @Override
    public Parser<List<Object>> getParser() {
        return PARSER;
    }
}