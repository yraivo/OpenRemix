package remix.compat.legacy.auth;

import java.io.Serializable;

public interface LicensePermissionRule extends Serializable {
    boolean passesLicenseRule(LicenseSession licenseSession);

    String getRuleName();

    ValidationRequirement getValidationRequirement();

    default boolean isObligatory() {
        return this.getValidationRequirement() == ValidationRequirement.OBLIGATORY;
    }

    default boolean isPassable() {
        return this.getValidationRequirement() == ValidationRequirement.PASSABLE;
    }
}
