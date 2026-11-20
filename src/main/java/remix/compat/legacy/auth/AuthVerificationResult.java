package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

public final class AuthVerificationResult {
    private static String c;
    private static String[] packedTextFragments;
    private LicenseProfileRecord licenseProfileRecord;
    private AuthVerificationOutcome authVerificationOutcome;

    // region String decoding support

    // endregion

    public AuthVerificationOutcome getAuthVerificationOutcome() {
        return this.authVerificationOutcome;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.AuthVerificationResult.packedTextFragments[0] =
                new String("AHjHz");
        remix.compat.legacy.auth.AuthVerificationResult.packedTextFragments[1] =
                new String("nbLSWb");
    }

    // endregion

    public LicenseProfileRecord getLicenseProfileRecord() {
        return this.licenseProfileRecord;
    }

    public static void b(String text) {
        c = text;
    }

    public AuthVerificationResult(
            LicenseProfileRecord licenseProfileRecordValue,
            AuthVerificationOutcome authVerificationOutcomeValue) {
        super();

        this.licenseProfileRecord = licenseProfileRecordValue;
        this.authVerificationOutcome = authVerificationOutcomeValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(packedTextFragments[0]);
        }
    }

    public static String method_cee993844cce4b97ed62() {
        return c;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (method_cee993844cce4b97ed62() == null) {
            b(packedTextFragments[1]);
        }
    }
}
