package remix.compat.legacy.http;

import remix.compat.legacy.auth.LicenseProfileRecord;

public interface PasteProfileFilter<E extends RemotePasteEndpoint> {
    boolean matchesProfile(E e, LicenseProfileRecord licenseProfileRecord);
}
