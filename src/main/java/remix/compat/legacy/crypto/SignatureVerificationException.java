package remix.compat.legacy.crypto;

import java.security.SignatureException;

public final class SignatureVerificationException extends SignatureException {
    private static boolean b;

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    static {
        method_d87039b07bd6961907f2();
    }

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }
}
