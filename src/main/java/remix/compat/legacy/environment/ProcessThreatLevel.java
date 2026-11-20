package remix.compat.legacy.environment;

public enum ProcessThreatLevel {
    MALICIOUS(initializeConstantState()),
    HARMFUL(93),
    MALICIOUS_EXTRA(21),
    NEUTRAL_EXTRA_EXTRA(62),
    HARMFUL_EXTRA(23),
    NEUTRAL(72),
    HARMFUL_EXTRA_EXTRA(33),
    NEUTRAL_EXTRA(22),
    MALICIOUS_EXTRA_EXTRA(14);
    private static ProcessThreatLevel[] bXq;

    private int a;
    private static int c;

    // region String decoding support

    // endregion

    ProcessThreatLevel(int j) {
        this.a = j;
    }

    public static void b(int i) {
        c = i;
    }

    public static int method_7eec03da329a017ec143() {
        return c;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.a;
    }

    public static int method_beea3e16fe3ec0995eb9() {
        method_7eec03da329a017ec143();

        return (0);
    }

    static {
        bXq =
                new ProcessThreatLevel[] {
                    remix.compat.legacy.environment.ProcessThreatLevel.MALICIOUS,
                    remix.compat.legacy.environment.ProcessThreatLevel.HARMFUL,
                    remix.compat.legacy.environment.ProcessThreatLevel.MALICIOUS_EXTRA,
                    remix.compat.legacy.environment.ProcessThreatLevel.NEUTRAL_EXTRA_EXTRA,
                    remix.compat.legacy.environment.ProcessThreatLevel.HARMFUL_EXTRA,
                    remix.compat.legacy.environment.ProcessThreatLevel.NEUTRAL,
                    remix.compat.legacy.environment.ProcessThreatLevel.HARMFUL_EXTRA_EXTRA,
                    remix.compat.legacy.environment.ProcessThreatLevel.NEUTRAL_EXTRA,
                    remix.compat.legacy.environment.ProcessThreatLevel.MALICIOUS_EXTRA_EXTRA
                };
    }

    // region String decoding support

    // endregion

    private static ProcessThreatLevel[] __recovery_values171() {
        return bXq.clone();
    }

    // Initialize shared state before the first constant is published.
    private static int initializeConstantState() {
        b(53);
        return 17;
    }
}
