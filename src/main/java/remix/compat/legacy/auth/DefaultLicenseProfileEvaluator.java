package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import java.util.Collections;

public class DefaultLicenseProfileEvaluator implements LicenseProfileEvaluator {
    private static String b;
    private static String[] packedTextFragments;
    private AuthVerificationOutcome authVerificationOutcome;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        remix.compat.legacy.auth.DefaultLicenseProfileEvaluator.packedTextFragments = new String[2];
        remix.compat.legacy.auth.DefaultLicenseProfileEvaluator.packedTextFragments[0] =
                new String("_");
        remix.compat.legacy.auth.DefaultLicenseProfileEvaluator.packedTextFragments[1] =
                new String(
                        "F\u0017\u0003\u0003\u0001:!7c\u000eup2&0\u0017\u0000\u0001\u0007HSH\u0012\u0004\u0002\u0006JTC\u0016\u000e\u0002");
        b = "7044411FD9BE96A0762CC95353AD2195";
    }

    public AuthVerificationResult evaluateProfile(LicenseProfileRecord licenseProfileRecord) {
        String text = AuthVerificationResult.method_cee993844cce4b97ed62();
        if (licenseProfileRecord != null
                && !licenseProfileRecord.hasNoSession()
                && !licenseProfileRecord.isGuestSession()
                && !licenseProfileRecord.isFallbackSession()) {
            LicenseSession licenseSession = licenseProfileRecord.getLicenseSession();
            UserRoleTier userRoleTier = licenseSession.getUserRoleTier();
            switch (userRoleTier) {
                case DEVELOPER:
                case BETA_TESTER:
                case USER:
                case DEVELOPER_EXTRA:
                    this.authVerificationOutcome = AuthVerificationOutcome.SUCCESS;
                    if (text != null) {
                        break;
                    }

                    Entity.setLegacyGlobalFlowMarker(new String[3]);
                case BETA_TESTER_EXTRA:
                    this.authVerificationOutcome = AuthVerificationOutcome.SUCCESS_4;
                    if (text != null) {
                        break;
                    }
                case USER_EXTRA:
                case USER_LOL:
                    this.authVerificationOutcome = AuthVerificationOutcome.SUCCESS_2;
                    if (text != null) {
                        break;
                    }
                case BANNED_EXTRA:
                    this.authVerificationOutcome = AuthVerificationOutcome.FAILURE_4;
                    if (text != null) {
                        break;
                    }
                case DEVELOPER_LOL:
                    this.authVerificationOutcome = AuthVerificationOutcome.INTERRUPTED;
                    if (text != null) {
                        break;
                    }
                case BETA_TESTER_LOL:
                    this.authVerificationOutcome = AuthVerificationOutcome.INTERRUPTED_4;
                    if (text != null) {
                        break;
                    }
                case BANNED_LOL:
                case BANNED:
                    this.authVerificationOutcome = AuthVerificationOutcome.FAILURE;
            }

            if (licenseProfileRecord.getFluentListWrapper().asList().isEmpty()) {
                this.authVerificationOutcome = AuthVerificationOutcome.FAILURE_2;
                if (text != null) {
                    return new AuthVerificationResult(
                            licenseProfileRecord, this.authVerificationOutcome);
                }
            }

            String textValue =
                    licenseSession.getClientIdentityToken().method_c60693f457aa70be7160();
            if (textValue.isEmpty()
                    || textValue.equals(packedTextFragments[0])
                    || textValue.equals(b)
                    || !userRoleTier.a(textValue)) {
                this.authVerificationOutcome = AuthVerificationOutcome.FAILURE_1;
            }

            return new AuthVerificationResult(licenseProfileRecord, this.authVerificationOutcome);
        } else {
            return new AuthVerificationResult(
                    new LicenseProfileRecord(null, Collections.emptyList()),
                    AuthVerificationOutcome.FAILURE);
        }
    }
}
