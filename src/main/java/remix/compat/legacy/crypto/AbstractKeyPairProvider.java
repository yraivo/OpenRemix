package remix.compat.legacy.crypto;

import java.security.KeyPairGenerator;

public abstract class AbstractKeyPairProvider implements KeyPairProvider {
    private KeyPairGenerator keyPairGenerator;

    public KeyPairGenerator getKeyPairGenerator() {
        return this.keyPairGenerator;
    }

    public AbstractKeyPairProvider(KeyPairGenerator keypairgenerator, int i) {
        this.keyPairGenerator = keypairgenerator;
        this.a(i);
    }
}
