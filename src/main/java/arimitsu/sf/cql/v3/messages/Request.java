package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Frame;


/**
 * Created by sxend on 14/06/07.
 */
public interface Request<R> {
    //    public R parseResponse(ByteBuffer buffer);
    public Frame toFrame();
}
