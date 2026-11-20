package remix.compat.legacy.auth;

import remix.compat.legacy.codec.IntCodeProvider;

import java.util.Map;

public abstract class AbstractLicenseStateMapper {
    private Map<IntCodeProvider, LicenseValidationState> map;

    public AbstractLicenseStateMapper(
            Map<IntCodeProvider, LicenseValidationState> map) {
        this.map = map;
    }

    public Map<IntCodeProvider, LicenseValidationState> lN() {
        return this.map;
    }
}
