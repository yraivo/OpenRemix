package remix.compat.legacy.invocation;

import remix.util.task.NamedValueHolder;

public abstract class ReflectiveTargetSupplier extends NamedValueHolder {
    private static String[] packedTextFragments;
    private static String d;

    static {
        packedTextFragments = new String[3];
        unpackLiteralFragments();
        if (d() == null) {
            method_be01d1eb5e587c583ed6(packedTextFragments[2]);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.invocation.ReflectiveTargetSupplier.packedTextFragments[0] = new String("F");
        remix.compat.legacy.invocation.ReflectiveTargetSupplier.packedTextFragments[1] = new String("");
        remix.compat.legacy.invocation.ReflectiveTargetSupplier.packedTextFragments[2] = new String("bgIfzc");
    }

    // endregion

    public static String d() {
        return d;
    }

    public ReflectiveTargetSupplier() {
        super(packedTextFragments[0], packedTextFragments[1]);
    }

    // region String decoding support

    // endregion

    public static void method_be01d1eb5e587c583ed6(String text) {
        d = text;
    }
}
