package arimitsu.sf.cql.v3.message.request;


import arimitsu.sf.cql.v3.message.Request;

/**
 * Created by sxend on 14/06/07.
 */
public class Options extends Request {

    public Options() {
    }

    @Override
    public byte[] toBody() {
        return Request.EMPTY_BODY;
    }
}
