package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 2014/10/09.
 */
public enum CQLVersion {
    CURRENT("3.0.0");
    public final String number;

    CQLVersion(String number) {
        this.number = number;
    }
}
