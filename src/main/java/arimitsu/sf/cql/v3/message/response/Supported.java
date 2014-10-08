package arimitsu.sf.cql.v3.message.response;


import arimitsu.sf.cql.v3.message.Response;
import arimitsu.sf.cql.v3.util.Notations;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/06/07.
 */
public class Supported extends Response {

    public static final String COMPRESSION = "COMPRESSION";
    public static final String CQL_VERSION = "CQL_VERSION";

    public final Map<String, List<String>> stringMultiMap;

    public Supported(ByteBuffer buffer) {
        super(buffer);
        this.stringMultiMap = Notations.getStringMultiMap(buffer);
    }

}

