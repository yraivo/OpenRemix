package remix.compat.legacy.auth;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import java.io.Serializable;
import java.util.List;

public final class LicenseProfileRecord implements Serializable {
    private FluentListWrapper fluentListWrapper;
    private LicenseSession licenseSession;
    private static int legacyProfileRecordFlowState;

    public FluentListWrapper getFluentListWrapper() {
        return this.fluentListWrapper;
    }

    public LicenseSession getLicenseSession() {
        return this.licenseSession;
    }

    public static void setLegacyProfileRecordFlowState(int i) {
        remix.compat.legacy.auth.LicenseProfileRecord.legacyProfileRecordFlowState = i;
    }

    static {
        if (getLegacyProfileRecordFlowState() != 0) {
            setLegacyProfileRecordFlowState(20);
        }
    }

    public boolean isFallbackSession() {
        return this.licenseSession instanceof FallbackLicenseSession;
    }

    public static int getLegacyProfileRecordFlowToken() {
        int i = getLegacyProfileRecordFlowState();
        return i == 0 ? 20 : 0;
    }

    public static int getLegacyProfileRecordFlowState() {
        return remix.compat.legacy.auth.LicenseProfileRecord.legacyProfileRecordFlowState;
    }

    public LicenseProfileRecord(LicenseSession bWzValue, List<String> textList) {
        this.licenseSession = bWzValue;
        this.fluentListWrapper = FluentCollections.method_e2cb25ad6dbcdd481b22(textList);
    }

    public boolean hasNoSession() {
        return this.licenseSession == null;
    }

    public boolean isGuestSession() {
        return this.licenseSession instanceof GuestLicenseSession;
    }
}
