package remix.compat.legacy.auth;

import java.util.function.Predicate;

public class PredicateLicensePermissionRule implements LicensePermissionRule {
    private ValidationRequirement validationRequirement;
    private String a;
    private Predicate<LicenseSession> predicate;

    public ValidationRequirement getValidationRequirement() {
        return this.validationRequirement;
    }

    public String getRuleName() {
        return this.a;
    }

    public PredicateLicensePermissionRule(
            String text,
            ValidationRequirement bTTValue,
            Predicate<LicenseSession> predicate) {
        ValidationRequirement.method_c60693f457aa70be7160();

        this.a = text;

        this.validationRequirement = bTTValue;
        this.predicate = predicate;
    }

    public boolean passesLicenseRule(LicenseSession p171_0) {
        return this.predicate.test(p171_0);
    }
}
