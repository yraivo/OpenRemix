package remix.client;

import remix.compat.legacy.auth.ClientIdentityToken;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.LicenseSession;
import remix.compat.legacy.codec.CipherKeySelector;

import remix.client.ClientServices;
import remix.client.RemixClient;

public class RemixClientMetadata extends ClientMetadata {
    ClientServices clientServices;

    public RemixClientMetadata(
            ClientServices clientServices, String text, String textValue, String[] astring) {
        super(text, textValue, astring);
        this.clientServices = clientServices;
    }

    public boolean passesLegacyStateCheck() {
        int[] aint1 = ClientServices.getLegacyServiceMarker();
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        int[] aint = aint1;
        if (clientServices == null) {
            return false;
        }

        LicenseProfileRecord verifiedLicenseProfile = clientServices.getVerifiedLicenseProfile();
        if (verifiedLicenseProfile == null) {
            return false;
        }

        LicenseSession licenseSession = verifiedLicenseProfile.getLicenseSession();
        if (licenseSession == null) {
            return false;
        }

        ClientIdentityToken clientIdentityToken = licenseSession.getClientIdentityToken();
        if (clientIdentityToken == null) {
            return false;
        }

        CipherKeySelector cipherKeySelector = clientIdentityToken.getCipherKeySelector();
        if (cipherKeySelector == null) {
            return false;
        }

        String text = cipherKeySelector.getIdentityText();
        if (text != null && !text.isEmpty() && text.length() >= 4 && text.length() <= 4) {
            int j = 0;

            while (j < text.length()) {
                char c0 = text.charAt(j);
                int i = '0' <= c0 && c0 <= '9' ? 1 : 0;
                if (i == 0) {
                    return false;
                }

                j++;
                if (aint != null) {
                    break;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
