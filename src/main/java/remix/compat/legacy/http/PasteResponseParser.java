package remix.compat.legacy.http;

import remix.compat.legacy.auth.LicenseSession;
import remix.compat.legacy.auth.RoleTierParser;
import remix.compat.legacy.crypto.HashListParser;
import remix.util.collection.FluentListWrapper;

public interface PasteResponseParser<T extends LicenseSession> {
    RoleTierParser getRoleTierParser();

    FluentListWrapper method_1e183c9c1e4cfdb85647(
            HttpResponseData httpResponseData);

    HashListParser getHashListParser();
}
