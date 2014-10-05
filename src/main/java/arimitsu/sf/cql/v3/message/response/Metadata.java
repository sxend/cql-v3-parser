package arimitsu.sf.cql.v3.message.response;

import arimitsu.sf.cql.v3.columntype.ColumnType;
import arimitsu.sf.cql.v3.message.response.result.ColumnSpec;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/06/11.
 */
public class Metadata {
    // <flags><columns_count>[<paging_state>][<global_table_spec>?<col_spec_1>...<col_spec_n>]
    public final int flags;
    public final int columnsCount;
    public final byte[] pagingState;
    public final Map<String, String> globalTableSpec;
    public final List<ColumnSpec> columnSpecList;

    public Metadata(int flags, int columnsCount, byte[] pagingState, Map<String, String> globalTableSpec, List<ColumnSpec> columnSpecList) {
        this.flags = flags;
        this.columnsCount = columnsCount;
        this.pagingState = pagingState;
        this.globalTableSpec = globalTableSpec;
        this.columnSpecList = columnSpecList;
    }

    public static Metadata fromBuffer(ByteBuffer buffer) {
        int flags = buffer.getInt();
        int columnsCount = buffer.getInt();
        boolean hasPagingState = (MetadataFlags.HAS_MORE_PAGES.mask & flags) > 0;
        boolean hasGlobalSetting = (MetadataFlags.GLOBAL_TABLES_SPEC.mask & flags) > 0;
        boolean hasntMetadata = (MetadataFlags.NO_METADATA.mask & flags) > 0;
        byte[] pagingState = hasPagingState ? Notations.getBytes(buffer) : null;
        Map<String, String> globalTableSpec = new HashMap<>();
        if (hasGlobalSetting) {
            globalTableSpec.put("KEYSPACE", Notations.getString(buffer));
            globalTableSpec.put("TABLE_NAME", Notations.getString(buffer));
        }
        List<ColumnSpec> columnSpec = new ArrayList<>();
        for (int i = 0; i < columnsCount; i++) {
            String keySpace = null;
            String tableName = null;
            if (!hasGlobalSetting) {
                keySpace = Notations.getString(buffer);
                tableName = Notations.getString(buffer);
            }
            String columnName = Notations.getString(buffer);
            ColumnType columnType = ColumnType.Factory.fromBuffer(buffer);
            columnSpec.add(new ColumnSpec(keySpace, tableName, columnName, columnType));
        }
        return new Metadata(flags, columnsCount, pagingState, globalTableSpec, columnSpec);
    }

}
