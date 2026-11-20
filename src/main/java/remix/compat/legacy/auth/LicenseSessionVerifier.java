package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;
import remix.client.AbstractClientServices;
import remix.client.ClientServices;

import java.io.Serializable;

public final class LicenseSessionVerifier implements Serializable {
    private AbstractClientServices abstractClientServices;
    private LicenseProfileRecord licenseProfileRecord;
    private AuthVerificationOutcome authVerificationOutcome;
    private LicenseSessionBuilder licenseSessionBuilder;

    public AuthVerificationOutcome getAuthVerificationOutcome() {
        return this.authVerificationOutcome;
    }

    public void validateAndStoreLicenseProfile(
            LicenseProfileRecord licenseProfileRecordValue,
            AuthVerificationOutcome authVerificationOutcomeValue)
            throws LicenseCode3Exception {
        int[] aint = ClientServices.getLegacyServiceMarker();
        if ((authVerificationOutcomeValue == AuthVerificationOutcome.SUCCESS
                        || authVerificationOutcomeValue == AuthVerificationOutcome.INTERRUPTED)
                && licenseProfileRecordValue.getLicenseSession() != null
                && licenseProfileRecordValue
                        .getLicenseSession()
                        .getClientIdentityToken()
                        .getCipherKeySelector()
                        .getIdentityText()
                        .equals(this.licenseSessionBuilder.method_e0e3092a59fee58fae52())
                && licenseProfileRecordValue.getLicenseSession().getLicenseStateHolder().aex() == LicenseValidationState.VALIDATING_XLP) {
            this.licenseProfileRecord = licenseProfileRecordValue;
            this.authVerificationOutcome = authVerificationOutcomeValue;
            if (aint != null) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
            }
        } else {
            throw new LicenseCode3Exception();
        }
    }

    public LicenseSessionVerifier(AbstractClientServices abstractClientServicesValue) {
        this.abstractClientServices = abstractClientServicesValue;
        this.licenseSessionBuilder = new LicenseSessionBuilder();
    }

    public AbstractClientServices getAbstractClientServices() {
        return this.abstractClientServices;
    }

    public LicenseProfileRecord getLicenseProfileRecord() {
        return this.licenseProfileRecord;
    }

    public LicenseSessionBuilder getLicenseSessionBuilder() {
        return this.licenseSessionBuilder;
    }
}
