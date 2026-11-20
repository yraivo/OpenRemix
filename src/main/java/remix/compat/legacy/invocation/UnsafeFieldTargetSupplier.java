package remix.compat.legacy.invocation;

public final class UnsafeFieldTargetSupplier extends ReflectiveTargetSupplier {
    private static String e;

    static {
        e = "theUnsafe";
    }

    public Object getValue() {
        return super.getValue();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
