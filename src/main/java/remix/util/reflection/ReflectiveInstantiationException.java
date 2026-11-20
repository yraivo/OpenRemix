package remix.util.reflection;

public class ReflectiveInstantiationException extends ReflectiveOperationException {
    private static String[] packedTextFragments;
    private static String b;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_c60693f457aa70be7160() != null) {
            b(packedTextFragments[0]);
        }
    }

    public ReflectiveInstantiationException(String text) {
        super(text);
    }

    public ReflectiveInstantiationException(String text, Throwable throwable) {
        super(text, throwable);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.util.reflection.ReflectiveInstantiationException.packedTextFragments[0] = new String("Ags0Tc");
    }

    // endregion

    // region String decoding support

    // endregion

    public static String method_c60693f457aa70be7160() {
        return b;
    }

    public static void b(String text) {
        b = text;
    }
}
