package remix.compat.legacy.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public final class SignedTokenVerifier {
    private boolean verified;
    private static String[] d;
    private SignedTokenPayload signedTokenPayload;
    private static String[] packedTextFragments;
    private AbstractSignatureSigner abstractSignatureSigner;

    public boolean verifyWithPublicKey(PublicKey publickey) {
        try {
            if (!this.method_b417d7500ec59d69e373()) {
                return false;
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
        }

        if (!this.verified) {
            try {
                String text = this.signedTokenPayload.d();
                DigitalSignatureProvider digitalSignatureProvider =
                        this.abstractSignatureSigner.getDigitalSignatureProvider();
                Signature signature = digitalSignatureProvider.getSignature();
                signature.initVerify(publickey);
                signature.update(text.getBytes());
                return signature.verify(
                        Base64.getDecoder()
                                .decode(
                                        this.signedTokenPayload
                                                .method_cee993844cce4b97ed62()
                                                .getBytes()));
            } catch (Exception exception) {
                return false;
            }
        } else {
            return true;
        }
    }

    SignedTokenVerifier(String text, String textValue, String textValue2) {
        this.signedTokenPayload =
                new SignedTokenPayload(
                        text, textValue == null ? packedTextFragments[1] : textValue, textValue2);
        this.verified = false;
    }

    public SignedTokenVerifier(String text, String textValue)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        this(
                text,
                textValue,
                new DefaultSignatureSigner(
                        new Sha256WithRsaSignatureProvider(), new RsaKeyPairProvider()),
                true);
    }

    public static void b(String[] astring) {
        d = astring;
    }

    // region String decoding support

    // endregion

    public SignedTokenVerifier(
            String text, String textValue, AbstractSignatureSigner bTyValue, boolean flag)
            throws InvalidSignatureKeyException, SignatureVerificationException {
        this(text, textValue == null ? packedTextFragments[0] : textValue, null);
        this.abstractSignatureSigner = bTyValue;
        this.signedTokenPayload.method_be01d1eb5e587c583ed6(
                SignatureVerifierService.bZj.method_0a6b652f7d4f2d8e4952(
                        bTyValue, this.signedTokenPayload, flag));
        this.verified = true;
    }

    public static String[] B() {
        return d;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (B() != null) {
            b(new String[5]);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.crypto.SignedTokenVerifier.packedTextFragments[0] = new String("");
        remix.compat.legacy.crypto.SignedTokenVerifier.packedTextFragments[1] = new String("");
    }

    // endregion

    public boolean method_b417d7500ec59d69e373() {
        return this.signedTokenPayload.method_cee993844cce4b97ed62() != null;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    // region String decoding support

    // endregion

    public SignedTokenPayload getSignedTokenPayload() {
        return this.signedTokenPayload;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public SignedTokenVerifier(
            String text,
            String textValue,
            DigitalSignatureProvider digitalSignatureProvider,
            KeyPairProvider keyPairProvider)
            throws SignatureVerificationException, InvalidSignatureKeyException {
        this(
                text,
                textValue,
                new DefaultSignatureSigner(digitalSignatureProvider, keyPairProvider),
                true);
    }
}
