package remix.compat.legacy.crypto;

import java.security.KeyPairGenerator;

public interface KeyPairProvider {
    KeyPairGenerator getKeyPairGenerator();

    default void a(int p171_0) {
        java.security.KeyPairGenerator keypairgenerator = this.getKeyPairGenerator();
        if (keypairgenerator != null) {
            keypairgenerator.initialize(p171_0);
        }
    }

    KeyPairHolder adM();
}
