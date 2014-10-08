package arimitsu.sf.cql.v3.message;


/**
 * Created by sxend on 14/06/07.
 */
public abstract class Request<R> {
    protected static final byte[] EMPTY_BODY = new byte[0];

    public abstract byte[] toBody();
}
