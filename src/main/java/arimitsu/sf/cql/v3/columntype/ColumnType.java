package arimitsu.sf.cql.v3.columntype;


import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {
    public static final ColumnType[] EMPTY = new ColumnType[0];

    Serializer<?> getSerializer();

    public interface ColumnTypeBuilder<C extends ColumnType> {
        C build(ByteBuffer buffer);

        C build(ColumnType... childTypes);
    }
}

