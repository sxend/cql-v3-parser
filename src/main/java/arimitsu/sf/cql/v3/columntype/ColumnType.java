package arimitsu.sf.cql.v3.columntype;


/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {

    Serializer<?> getSerializer();

}

