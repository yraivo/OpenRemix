package remix.compat.legacy.security;

public enum CheckResultStatus {
    bYs(initializeConstantState()),
    bYt(constantTextFragment(3)),
    bYw(constantTextFragment(4)),
    bYu(constantTextFragment(5)),
    bYm(constantTextFragment(6)),
    bYq(constantTextFragment(7)),
    bYr(constantTextFragment(8)),
    bYo(constantTextFragment(9)),
    bYk(constantTextFragment(10)),
    bYv(constantTextFragment(11)),
    bYl(constantTextFragment(12));
    private static CheckResultStatus[] bYp;

    String a;
    private static String[] packedTextFragments;

    @Override
    public String toString() {
        return this.a;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        bYp = new CheckResultStatus[] {bYs, bYt, bYw, bYu, bYm, bYq, bYr, bYo, bYk, bYv, bYl};
    }

    CheckResultStatus(String text) {
        this.a = text;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static CheckResultStatus[] __recovery_values171() {
        return bYp.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments = new String[13];
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[2] = new String("E");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[3] = new String("A");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[4] = new String("S");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[5] = new String("B");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[6] = new String("S");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[7] = new String("F");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[8] = new String("S");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[9] = new String("C");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[10] = new String("S");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[11] = new String("S");
        remix.compat.legacy.security.CheckResultStatus.packedTextFragments[12] = new String("D");
        return remix.compat.legacy.security.CheckResultStatus.packedTextFragments[2];
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
