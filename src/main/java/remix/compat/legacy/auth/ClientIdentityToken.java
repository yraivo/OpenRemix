package remix.compat.legacy.auth;

import remix.compat.legacy.codec.CipherKeySelector;
import remix.compat.legacy.environment.HardwareFingerprintCollector;

public interface ClientIdentityToken {
    String method_c60693f457aa70be7160();

    CipherKeySelector getCipherKeySelector();

    HardwareFingerprintCollector acv();
}
