package remix.compat.legacy.environment;

public enum VmDetectionStatus {
    DETECTED(17),
    NOT_FOUND(93),
    DETECTED_EXTRA(21),
    NEUTRAL_EXTRA_EXTRA(62),
    NOT_FOUND_EXTRA(23),
    NEUTRAL(72),
    NOT_FOUND_EXTRA_EXTRA(33),
    NEUTRAL_EXTRA(22),
    DETECTED_EXTRA_EXTRA(14);

    private int a;
    private static String c;

    VmDetectionStatus(int j) {
        this.a = j;
    }

    // region String decoding support

    // endregion

    public static void b(String text) {
        c = text;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.a;
    }

    public static String method_c60693f457aa70be7160() {
        return c;
    }


    // region String decoding support

    // endregion

}
