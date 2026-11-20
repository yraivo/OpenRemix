package remix.compat.legacy.security;

public class SecurityCheckException extends Exception {
    private static boolean b;

    static {
        if (!method_b417d7500ec59d69e373()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }
}
