package remix.compat.legacy.crypto;

import remix.util.collection.FluentIterableList;

public abstract class AbstractSignatureSigner implements SignatureServiceContext {
    private static boolean signerInitialized;
    private KeyPairProvider keyPairProvider;
    private DigitalSignatureProvider digitalSignatureProvider;

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = isSignerInitialized();
        return !flag;
    }

    public static void setSignerInitialized(boolean flag) {
        remix.compat.legacy.crypto.AbstractSignatureSigner.signerInitialized = flag;
    }

    public AbstractSignatureSigner(
            DigitalSignatureProvider digitalSignatureProviderValue,
            KeyPairProvider keyPairProviderValue) {
        super();

        this.digitalSignatureProvider = digitalSignatureProviderValue;
        this.keyPairProvider = keyPairProviderValue;
    }

    public abstract FluentIterableList<Byte> method_66633fc4ccd32aa15c42(byte[] abyte)
            throws SignatureVerificationException, InvalidSignatureKeyException;

    public static boolean isSignerInitialized() {
        return remix.compat.legacy.crypto.AbstractSignatureSigner.signerInitialized;
    }

    public DigitalSignatureProvider getDigitalSignatureProvider() {
        return this.digitalSignatureProvider;
    }

    static {
        isSignerInitialized();
    }

    public KeyPairProvider getKeyPairProvider() {
        return this.keyPairProvider;
    }
}
