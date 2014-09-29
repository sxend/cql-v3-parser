package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public class Frame {
    public final Header header;
    public final int length;
    public final byte[] body;

    public Frame(Header header, byte[] body) {
        this.header = header;
        this.length = body != null ? body.length : 0;
        this.body = body;
    }

}
