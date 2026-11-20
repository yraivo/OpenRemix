package remix.compat.legacy.crypto;

public interface SignatureServiceContext {
    KeyPairProvider getKeyPairProvider();

    DigitalSignatureProvider getDigitalSignatureProvider();
}
