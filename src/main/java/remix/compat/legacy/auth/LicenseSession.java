package remix.compat.legacy.auth;

import remix.compat.legacy.environment.OperatingSystemType;
import remix.util.collection.FluentMapWrapper;

import java.io.Serializable;

public interface LicenseSession extends Serializable {
    FluentMapWrapper Zy();

    OperatingSystemType acN();

    ClientIdentityToken getClientIdentityToken();

    LicenseStateHolder getLicenseStateHolder();

    void l();

    UserRoleTier getUserRoleTier();
}
