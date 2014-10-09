package arimitsu.sf.cql.v3.message.request;

/**
 * Created by sxend on 2014/10/09.
 */
public enum QueryFlags {
    VALUES(0x01),
    SKIP_METADATA(0x02),
    PAGE_SIZE(0x04),
    WITH_PAGING_STATE(0x08),
    WITH_SERIAL_CONSISTENCY(0x10),
    WITH_DEFAULT_TIMESTAMP(0x20),
    WITH_NAMES_FOR_VALUES(0x40),;
    public final byte mask;

    QueryFlags(int mask) {
        this.mask = (byte) mask;
    }
}