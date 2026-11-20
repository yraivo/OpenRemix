package remix.compat.legacy.codec;

public final class IdentityStringTransformer implements ValueTransformer {
    private static String[] packedTextFragments;
    private static String b;

    public String a(String text) {
        return text;
    }

    public static String method_c60693f457aa70be7160() {
        return b;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.codec.IdentityStringTransformer.packedTextFragments[0] = new String("g54Orc");
    }

    // endregion

    public Object method_69be51ad1433bd242905(Object object) {
        return this.a((String) object);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();

        method_c60693f457aa70be7160();
    }

    // region String decoding support

    // endregion

    public static void b(String text) {
        b = text;
    }
}
