package remix.compat.legacy.codec;

public final class BracedColonFormatPredicate implements remix.compat.legacy.codec.FormatPredicate {

    private static boolean b;

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    public boolean a(String text) {
        method_d87039b07bd6961907f2();

        int i =
                !text.isEmpty()
                                && text.contains(new String("{"))
                                && text.contains(new String("}"))
                                && text.contains(new String(":"))
                        ? 0
                        : 1;

        return remix.compat.compiler.JvmBooleanSlots.fromInt(i);
    }

    // region String decoding support

    // endregion

    public boolean method_c7b139ccf3ff22603b21(Object object) {
        return this.a((String) object);
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    static {
        method_a8745b073fa8adfa1857(true);
    }

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }

    // region String decoding support

    // endregion
}
