package remix.compat.legacy.crypto;

import remix.util.collection.FluentIterableList;

import java.security.InvalidKeyException;
import java.security.Signature;

public interface DigitalSignatureProvider {
    FluentIterableList<Byte> method_3917e4f9057ac010d598(
            KeyPairHolder keyPairHolder, byte[] abyte)
            throws SignatureVerificationException,
                    InvalidSignatureKeyException,
                    InvalidSignatureKeyException;

    Signature getSignature();

    default void method_f68c21b9869e51370f1a(KeyPairHolder keyPairHolder)
            throws InvalidKeyException {
        Signature signature = this.getSignature();

        try {
            if (signature != null) {
                signature.initSign(keyPairHolder.getPrivateKey());
            }
        } catch (InvalidKeyException invalidkeyexception) {
            throw method_b0fecb15de96760080f2(invalidkeyexception);
        }
    }

    static InvalidKeyException method_b0fecb15de96760080f2(
            InvalidKeyException invalidkeyexception) {
        return invalidkeyexception;
    }
}
