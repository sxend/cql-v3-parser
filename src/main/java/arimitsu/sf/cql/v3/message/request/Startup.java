package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.CQLVersion;
import arimitsu.sf.cql.v3.Compression;
import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/07.
 */
public class Startup extends Request {
    public static final String CQL_VERSION = "CQL_VERSION";
    public static final String COMPRESSION = "COMPRESSION";
    public final Map<String, String> options;

    public Startup(CQLVersion version, Compression compression) {
        Map<String, String> options = new HashMap<>();
        options.put(CQL_VERSION, version.number);
        if (compression != Compression.NONE) {
            options.put(COMPRESSION, compression.name);
        }
        this.options = options;
    }

    @Override
    public byte[] toBody() {
        return Notations.toStringMap(options);
    }
}
