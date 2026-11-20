package remix.compat.legacy.crypto;

import java.security.InvalidKeyException;
import java.security.Signature;

public abstract class AbstractDigitalSignatureProvider implements DigitalSignatureProvider {
    private Signature signature;

    public AbstractDigitalSignatureProvider(Signature signature) throws InvalidKeyException {
        this.signature = signature;
    }

    public Signature getSignature() {
        return this.signature;
    }
}
