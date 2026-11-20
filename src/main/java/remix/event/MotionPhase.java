package remix.event;

public enum MotionPhase {
    PRE,
    POST;
    private static MotionPhase[] bZY;

    private static boolean b;

    // region Static initialization and string tables
    static {
        bZY = new MotionPhase[] {PRE, POST};
    }

    // endregion

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    // region String decoding support

    // endregion

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }

    // region String decoding support

    // endregion

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    private static MotionPhase[] __recovery_values171() {
        return bZY.clone();
    }

    private MotionPhase() {
        if (ordinal() == 0) {
            initializeConstantState();
        }
    }

    // Initialize shared state before the first constant is published.
    private static void initializeConstantState() {
        method_a8745b073fa8adfa1857(true);
    }
}
