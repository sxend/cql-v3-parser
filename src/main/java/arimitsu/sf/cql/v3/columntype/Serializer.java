package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/17.
 */
public interface Serializer<A> {
    byte[] serialize(A a);

    A deserialize(ByteBuffer buffer);
}
