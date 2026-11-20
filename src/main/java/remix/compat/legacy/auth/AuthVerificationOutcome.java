package remix.compat.legacy.auth;

public enum AuthVerificationOutcome {
    INTERRUPTED_1,
    SUCCESS_2,
    FAILURE_2,
    INTERRUPTED,
    SUCCESS_1,
    FAILURE_1,
    SUCCESS_3,
    FAILURE_3,
    INTERRUPTED_3,
    SUCCESS_4,
    FAILURE_4,
    INTERRUPTED_4,
    SUCCESS,
    FAILURE,
    INTERRUPTED_2;
    private static AuthVerificationOutcome[] bVK;

    private static int[] gy;

    public static void b(int[] aint) {
        gy = aint;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static boolean method_358a00d2fc9a94a05276(
            AuthVerificationOutcome authVerificationOutcome) {
        return authVerificationOutcome
                        != remix.compat.legacy.auth.AuthVerificationOutcome.INTERRUPTED
                && authVerificationOutcome
                        != remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE_2
                && authVerificationOutcome
                        != remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE_1;
    }

    static {
        bVK =
                new AuthVerificationOutcome[] {
                    remix.compat.legacy.auth.AuthVerificationOutcome.INTERRUPTED_1,
                    remix.compat.legacy.auth.AuthVerificationOutcome.SUCCESS_2,
                    remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE_2,
                    remix.compat.legacy.auth.AuthVerificationOutcome.INTERRUPTED,
                    remix.compat.legacy.auth.AuthVerificationOutcome.SUCCESS_1,
                    remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE_1,
                    remix.compat.legacy.auth.AuthVerificationOutcome.SUCCESS_3,
                    remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE_3,
                    remix.compat.legacy.auth.AuthVerificationOutcome.INTERRUPTED_3,
                    remix.compat.legacy.auth.AuthVerificationOutcome.SUCCESS_4,
                    remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE_4,
                    remix.compat.legacy.auth.AuthVerificationOutcome.INTERRUPTED_4,
                    remix.compat.legacy.auth.AuthVerificationOutcome.SUCCESS,
                    remix.compat.legacy.auth.AuthVerificationOutcome.FAILURE,
                    remix.compat.legacy.auth.AuthVerificationOutcome.INTERRUPTED_2
                };
    }

    public static int[] method_650532ba484532b3efb0() {
        return gy;
    }

    // region String decoding support

    // endregion

    private static AuthVerificationOutcome[] __recovery_values171() {
        return bVK.clone();
    }

    private AuthVerificationOutcome() {
        if (ordinal() == 0) {
            initializeConstantState();
        }
    }

    // Initialize shared state before the first constant is published.
    private static void initializeConstantState() {
        int[] aint = new int[3];
        b(aint);
    }
}
