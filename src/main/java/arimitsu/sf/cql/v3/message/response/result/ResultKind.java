package arimitsu.sf.cql.v3.message.response.result;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 2014/07/30.
 */
public enum ResultKind {
    VOID(0x0001),// : for results carrying no information.
    ROWS(0x0002),//: for results to select queries, returning a set of rows.
    SET_KEYSPACE(0x0003),//: the result to a `use` query.
    PREPARED(0x0004),//: result to a PREPARE message.
    SCHEMA_CHANGE(0x0005),;
    public final int code;// : the result to a schema altering query.

    ResultKind(int code) {
        this.code = code;
    }

    private static final Map<Integer, ResultKind> INDEX;

    static {
        Map<Integer, ResultKind> index = new HashMap<>();
        for (ResultKind resultKind : values()) {
            index.put(resultKind.code, resultKind);
        }
        INDEX = Collections.unmodifiableMap(index);
    }

    public static ResultKind valueOf(int code) {
        if (!INDEX.containsKey(code)) {
            throw new IllegalArgumentException("invalid code: " + code);
        }
        return INDEX.get(code);
    }
}
