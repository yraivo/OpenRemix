package remix.compat.legacy.http;

import remix.compat.legacy.auth.LicenseSession;
import remix.util.collection.FluentListWrapper;

public interface PasteProfileFetcher {
    FluentListWrapper fetchProfileLines(
            LicenseSession licenseSession) throws Exception;
}
