package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

public final class LicenseStateTransition {
    public static boolean il;
    private static String[] packedTextFragments;
    private static String e;
    private LicenseValidationState bWF;
    private LicenseValidationState bWG;
    public static boolean b;

    public void method_21910f153790ae8418e8(LicenseValidationState bWGValue) {
        this.bWG = bWGValue;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (d() != null) {
            b(packedTextFragments[1]);
        }
    }

    public LicenseStateTransition(
            LicenseValidationState licenseValidationState,
            LicenseValidationState licenseValidationStateValue) {
        super();

        b = true;
        this.bWG = licenseValidationState;
        this.bWF = licenseValidationStateValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(packedTextFragments[0]);
        }
    }

    public void method_6a690e50410ba8ae1050(LicenseValidationState bWGValue) {
        String text = d();
        if ((this.bWF == LicenseValidationState.VALIDATING_HWID_EXTRA
                        || this.bWF == LicenseValidationState.VALIDATING_XLP
                        || this.bWF == LicenseValidationState.VALIDATING)
                && (bWGValue == LicenseValidationState.INVALID
                        || bWGValue == LicenseValidationState.VALIDATING
                        || bWGValue == LicenseValidationState.INVALID_EXTRA)) {
            il = true;
        }

        LicenseValidationState bWFValue = this.bWG;
        this.bWG = bWGValue;
        this.bWF = bWFValue;
        if (text != null) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }

    public LicenseStateTransition(LicenseValidationState licenseValidationState) {
        this(licenseValidationState, null);
    }

    public void method_10471ffeb7156bbac213(LicenseValidationState bWFValue) {
        this.bWF = bWFValue;
    }

    public static String d() {
        return e;
    }

    // region String decoding support

    // endregion

    public static void b(String text) {
        e = text;
    }

    private boolean aY() {
        return this.bWG == this.bWF;
    }

    public LicenseValidationState aex() {
        return this.bWG;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.LicenseStateTransition.packedTextFragments[0] =
                new String("do5xdb");
        remix.compat.legacy.auth.LicenseStateTransition.packedTextFragments[1] =
                new String("osC4r");
    }

    // endregion

    public LicenseValidationState aeA() {
        return this.bWF;
    }

    // region String decoding support

    // endregion
}
