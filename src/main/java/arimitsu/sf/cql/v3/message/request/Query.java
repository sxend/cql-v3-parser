package arimitsu.sf.cql.v3.message.request;

import arimitsu.sf.cql.v3.message.Request;
import arimitsu.sf.cql.v3.util.Notations;

/**
 * Created by sxend on 14/06/07.
 */
public class Query extends Request {
    // <query><query_parameters>
    public final String string;
    public final QueryParameters parameters;

    public Query(String string, QueryParameters parameters) {
        this.string = string;
        this.parameters = parameters;
    }

    @Override
    public byte[] toBody() {
        byte[] query = Notations.toLongStringWithIntLength(this.string);
        byte[] parameters = this.parameters.toBytes();
        return Notations.join(query, parameters);
    }


}
