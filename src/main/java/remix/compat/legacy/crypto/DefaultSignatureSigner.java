package remix.compat.legacy.crypto;

import remix.util.collection.FluentIterableList;

import net.minecraft.entity.Entity;

public final class DefaultSignatureSigner extends AbstractSignatureSigner {
    public FluentIterableList<Byte> method_66633fc4ccd32aa15c42(byte[] abyte)
            throws SignatureVerificationException, InvalidSignatureKeyException {
        boolean flag1 = AbstractSignatureSigner.method_d87039b07bd6961907f2();
        KeyPairHolder keyPairHolder = this.getKeyPairProvider().adM();
        boolean flag = flag1;
        FluentIterableList fluentIterableList =
                this.getDigitalSignatureProvider().method_3917e4f9057ac010d598(keyPairHolder, abyte);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractSignatureSigner.setSignerInitialized(!flag);
        }

        return fluentIterableList;
    }

    private static SignatureVerificationException method_bc775a43870e8f3ea78c(
            SignatureVerificationException signatureVerificationException) {
        return signatureVerificationException;
    }

    public DefaultSignatureSigner(
            DigitalSignatureProvider digitalSignatureProvider,
            KeyPairProvider keyPairProvider) {
        super(digitalSignatureProvider, keyPairProvider);
    }
}
