package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

import java.util.Map;

/**
 * Created by sxend on 14/06/07.
 */
public class Startup extends Request {
    public static final String CQL_VERSION = "CQL_VERSION";
    public static final String CQL_VERSION_NUMBER = "3.0.0";
    public static final String COMPRESSION = "COMPRESSION";
    public final Map<String, String> options;

    public Startup(Map<String, String> options) {
        this.options = options;
    }

    @Override
    public byte[] toBody() {
        return Notations.toStringMap(options);
    }
}
