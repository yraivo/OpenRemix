package remix.compat.legacy.auth;

import remix.compat.legacy.codec.ValueTransformer;

public abstract class AbstractRoleTierParser implements remix.compat.legacy.auth.RoleTierParser {
    private static String[] c;
    private remix.compat.legacy.codec.ValueValidator valueValidator;
    private ValueTransformer valueTransformer;

    public static void b(String[] astring) {
        c = astring;
    }

    public static String[] b() {
        return c;
    }

    public AbstractRoleTierParser(
            remix.compat.legacy.codec.ValueValidator bUOValue, ValueTransformer bUPValue) {
        this.valueValidator = bUOValue;
        this.valueTransformer = bUPValue;
    }

    public ValueTransformer getValueTransformer() {
        return this.valueTransformer;
    }

    public remix.compat.legacy.codec.ValueValidator getValueValidator() {
        return this.valueValidator;
    }

    static {
        b();
    }
}
