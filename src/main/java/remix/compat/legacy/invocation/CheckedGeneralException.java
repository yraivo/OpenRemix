package remix.compat.legacy.invocation;

public final class CheckedGeneralException extends Exception {
    private static final long serialVersionUID = -2280768963680778719L;
    private static String[] packedTextFragments;
    private static String b;

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();

        method_c60693f457aa70be7160();
    }

    public static void b(String text) {
        b = text;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.invocation.CheckedGeneralException.packedTextFragments[0] = new String("C5Ss4b");
    }

    // endregion

    public static String method_c60693f457aa70be7160() {
        return b;
    }

    public CheckedGeneralException(String text) {
        super(text);
    }
}
