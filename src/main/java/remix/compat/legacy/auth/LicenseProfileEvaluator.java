package remix.compat.legacy.auth;

public interface LicenseProfileEvaluator {
    AuthVerificationResult evaluateProfile(
            LicenseProfileRecord licenseProfileRecord);
}
