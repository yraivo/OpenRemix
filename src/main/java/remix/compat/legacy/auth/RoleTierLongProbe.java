package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;

public final class RoleTierLongProbe extends remix.util.task.NamedValueHolder {
    private static boolean legacyRoleLongFlowEnabled;
    private static String[] packedTextFragments;

    public static void setLegacyRoleLongFlowEnabled(boolean flag) {
        remix.compat.legacy.auth.RoleTierLongProbe.legacyRoleLongFlowEnabled = flag;
    }

    // region String decoding support

    // endregion

    public static boolean isLegacyRoleLongFlowDisabled() {
        boolean flag = isLegacyRoleLongFlowEnabled();
        return !flag;
    }

    public Object getValue() {
        return this.evaluateRoleLongProbe();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.RoleTierLongProbe.packedTextFragments[0] = new String("");
    }

    // endregion

    public static boolean isLegacyRoleLongFlowEnabled() {
        return remix.compat.legacy.auth.RoleTierLongProbe.legacyRoleLongFlowEnabled;
    }

    public RoleTierLongProbe() {
        super(29083897620923070L, packedTextFragments[0]);
    }

    public Long evaluateRoleLongProbe() {
        LicenseSession licenseSession =
                ((ClientServices) RemixClient.INSTANCE.getClientServices())
                        .getVerifiedLicenseProfile()
                        .getLicenseSession();
        boolean flag1 = isLegacyRoleLongFlowEnabled();
        LicenseValidationState licenseValidationState =
                licenseSession.getLicenseStateHolder().getLicenseStateTransition().aex();
        boolean flag = flag1;
        long k2 = 5450997454852530439L;
        Long olong = -1L;
        UserRoleTier userRoleTier = licenseSession.getUserRoleTier();
        switch (userRoleTier) {
            case DEVELOPER:
            case BETA_TESTER:
            case USER:
                k2 = 6570041026750946421L;
                olong = 92083629L;
                if (flag) {
                    break;
                }

                Entity.setLegacyGlobalFlowMarker(new String[5]);
            case DEVELOPER_EXTRA:
                k2 = -3436979740257722724L;
                olong = 1209471L;
                if (flag) {
                    break;
                }
            case BETA_TESTER_EXTRA:
            case USER_EXTRA:
                k2 = 5846336318855543889L;
                olong = 637349471L;
                if (flag) {
                    break;
                }
            case BANNED_EXTRA:
                k2 = 5003920404474319602L;
                olong = 2192640912L;
                if (flag) {
                    break;
                }
            case DEVELOPER_LOL:
            case BETA_TESTER_LOL:
            case USER_LOL:
            case BANNED_LOL:
                k2 = -5106134444907011235L;
                olong = 3285762389L;
                if (flag) {
                    break;
                }
            case BANNED:
                k2 = -7826643486675861797L;
                olong = 96723523574L;
        }

        switch (licenseValidationState) {
            case VALIDATING:
                k2 = olong;
                long l2 = -4650422366023862657L;
                this.setValue(k2 + 127012L);
                if (flag) {
                    break;
                }
            case INVALID:
            case VALIDATING_PROCESS:
            case WAITING_RESPONSE:
                k2 = olong;
                long i = 4165196142790916050L;
                this.setValue(k2 + 14366L);
                if (flag) {
                    break;
                }
            case INVALID_EXTRA:
                k2 = olong;
                long j = -863802654766741637L;
                this.setValue(k2 + 63462L);
                if (flag) {
                    break;
                }
            case VALIDATING_XLP_EXTRA:
                k2 = olong;
                long k = -7101823583504233121L;
                this.setValue(k2 + 14637412L);
                if (flag) {
                    break;
                }
            case WAITING_EXTRA:
                k2 = olong;
                long l = -5410812968889726353L;
                this.setValue(k2 + 45726312L);
                if (flag) {
                    break;
                }
            case VALIDATING_XLP:
            case WAITING:
                k2 = olong;
                long i1 = -7085314731563558488L;
                this.setValue(k2 + 75422342L);
                if (flag) {
                    break;
                }
            case CONFIRMED_ALT:
            case VALIDATING_EXTRA:
            case INVALID_HWID:
            case VALID:
                k2 = olong;
                long j1 = 5661034901821335225L;
                this.setValue(k2 + 96342L);
                if (flag) {
                    break;
                }
            case VALIDATING_HWID_EXTRA:
                k2 = olong;
                long k1 = 5784329294945420398L;
                this.setValue(k2 + 8902894L);
                if (flag) {
                    break;
                }
            case INVALID_HWID_EXTRA:
            case CONFIRMED_ALT_EXTRA:
                k2 = olong;
                long l1 = 4441240382334922078L;
                this.setValue(k2 + 59862014L);
                if (flag) {
                    break;
                }
            case VALIDATING_HWID:
                k2 = olong;
                long i2 = 7410630055227562916L;
                this.setValue(k2 + 98355491L);
                if (flag) {
                    break;
                }
            case VALID_EXTRA:
            case VALIDATING_PROCESS_EXTRA:
            case WAITING_RESPONSE_EXTRA:
                k2 = olong;
                long j2 = -8120431731544590544L;
                this.setValue(k2 + 6623523098L);
        }

        return (Long) super.getValue();
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (isLegacyRoleLongFlowDisabled()) {
            setLegacyRoleLongFlowEnabled(true);
        }
    }
}
