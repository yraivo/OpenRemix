package remix.compat.legacy.auth;

import remix.compat.legacy.http.DefaultPasteProfileFetcher;
import remix.util.collection.FluentListWrapper;

import java.util.Collections;
import java.util.Iterator;

public enum LicenseCheckService {
    bPw;

    public static FluentListWrapper bPu;

    // region String decoding support

    // endregion

    private static Exception a(Exception exception) {
        return exception;
    }

    // region String decoding support

    // endregion

    public LicenseProfileRecord method_cfd28308a38b476be1e7(
            FluentListWrapper bPuValue, String text) {
        bPu = bPuValue;
        DefaultPasteProfileFetcher.method_da2ac5c94686ee11e9e6();

        Iterator iterator = bPuValue.asList().iterator();

        while (iterator.hasNext()) {
            LicenseProfileRecord licenseProfileRecord = (LicenseProfileRecord) iterator.next();

            try {
                if (licenseProfileRecord
                        .getLicenseSession()
                        .getClientIdentityToken()
                        .getCipherKeySelector()
                        .getIdentityText()
                        .equals(text)) {
                    return licenseProfileRecord;
                }
            } catch (Exception exception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
            }
        }

        try {
            return new LicenseProfileRecord(
                    new FallbackLicenseSession(text), Collections.emptyList());
        } catch (Exception exception) {
            return new LicenseProfileRecord(null, Collections.emptyList());
        }
    }


    private LicenseCheckService() {}
}
