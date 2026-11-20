package remix.compat.legacy.auth;

public final class LicenseStateHolder {
    private LicenseValidationState licenseValidationState;
    private LicenseStateTransition licenseStateTransition;

    public LicenseValidationState aex() {
        return this.licenseStateTransition.aex();
    }

    public LicenseStateHolder(
            LicenseValidationState bWCValue, LicenseStateTransition bWDValue) {
        this.licenseValidationState = bWCValue;
        this.licenseStateTransition = bWDValue;
    }

    public LicenseStateTransition getLicenseStateTransition() {
        return this.licenseStateTransition;
    }

    public LicenseStateHolder(LicenseValidationState licenseValidationStateValue) {
        this(
                licenseValidationStateValue,
                new LicenseStateTransition(licenseValidationStateValue));
    }
}
