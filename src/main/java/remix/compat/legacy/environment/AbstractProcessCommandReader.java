package remix.compat.legacy.environment;

import remix.util.task.NamedValueHolder;

import java.io.IOException;

public abstract class AbstractProcessCommandReader<T extends String>
        implements ProcessOutputReader {
    private static String b;
    private T a;
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.environment.AbstractProcessCommandReader.packedTextFragments[0] = new String("oblf4b");
    }

    // endregion

    public String method_c60693f457aa70be7160() {
        return this.a;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_cee993844cce4b97ed62() == null) {
            b(packedTextFragments[0]);
        }
    }

    // region String decoding support

    // endregion

    public AbstractProcessCommandReader(NamedValueHolder namedValueHolder) {
        this((T) namedValueHolder.getValue());
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public String method_e0e3092a59fee58fae52() throws IOException {
        return "";
    }

    public static String method_cee993844cce4b97ed62() {
        return b;
    }

    public static void b(String text) {
        b = text;
    }

    public AbstractProcessCommandReader(T s) {
        this.a = (T) s;
    }
}
