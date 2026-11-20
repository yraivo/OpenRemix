package remix.compat.legacy.auth;

public final class AuthStatusMessage {
    private String b;
    private static String[] packedTextFragments;
    private AuthVerificationOutcome authVerificationOutcome;

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public String method_cee993844cce4b97ed62() {
        return this.b;
    }

    public static final AuthStatusMessage ZG() {
        return new AuthStatusMessage(
                AuthVerificationOutcome.SUCCESS, packedTextFragments[0]);
    }

    public static final AuthStatusMessage bE(String text) {
        return new AuthStatusMessage(AuthVerificationOutcome.FAILURE, text);
    }

    public static final AuthStatusMessage bF(String text) {
        return new AuthStatusMessage(AuthVerificationOutcome.INTERRUPTED, text);
    }

    public AuthVerificationOutcome getAuthVerificationOutcome() {
        return this.authVerificationOutcome;
    }

    AuthStatusMessage(AuthVerificationOutcome authVerificationOutcomeValue, String text) {
        AuthVerificationOutcome.method_650532ba484532b3efb0();

        this.authVerificationOutcome = authVerificationOutcomeValue;

        this.b = text;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.AuthStatusMessage.packedTextFragments[0] = new String("");
    }
    // endregion
}
