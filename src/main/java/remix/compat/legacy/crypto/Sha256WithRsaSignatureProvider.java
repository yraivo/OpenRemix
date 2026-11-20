package remix.compat.legacy.crypto;

import remix.util.collection.FluentIterableList;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.List;

public final class Sha256WithRsaSignatureProvider extends AbstractDigitalSignatureProvider {
    private FluentIterableList<Byte> fluentIterableList;

    private static String b;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public FluentIterableList<Byte> method_3917e4f9057ac010d598(
            KeyPairHolder keyPairHolder, byte[] abyte)
            throws SignatureVerificationException, InvalidSignatureKeyException {
        FluentIterableList.f();

        try {
            this.method_f68c21b9869e51370f1a(keyPairHolder);
        } catch (InvalidKeyException invalidkeyexception) {
            throw new InvalidSignatureKeyException();
        }

        try {
            if (false)
                throw (java.security.InvalidKeyException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (this.fluentIterableList != null) {
                return this.fluentIterableList;
            }
        } catch (InvalidKeyException invalidkeyexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_c415ae41637537c828fb(invalidkeyexception1));
        }

        Signature signature = this.getSignature();

        try {
            signature.update(abyte);
        } catch (SignatureException signatureexception1) {
            throw new SignatureVerificationException();
        }

        FluentIterableList bSXValue = new FluentIterableList();

        byte[] abyte1;
        try {
            abyte1 = signature.sign();
        } catch (SignatureException signatureexception) {
            throw new SignatureVerificationException();
        }

        int i = abyte1.length - 1;

        while (i >= 0) {
            byte b0 = abyte1[i];
            bSXValue.a(b0);
            i--;
        }

        List list = bSXValue.eN();
        Byte[] abyte2 = ((java.lang.Byte[]) list.toArray(new Byte[list.size()]));
        int j = 0;

        while (j < abyte2.length / 2) {
            byte b1 = abyte2[j];
            abyte2[j] = abyte2[abyte2.length - j - 1];
            abyte2[abyte2.length - j - 1] = b1;
            j++;
        }

        bSXValue.method_bf3baeebc2bc1576e0a6(abyte2);
        this.fluentIterableList = bSXValue;
        return this.fluentIterableList;
    }

    public Sha256WithRsaSignatureProvider(String text)
            throws NoSuchAlgorithmException, InvalidKeyException {
        super(Signature.getInstance(text));
    }

    public Sha256WithRsaSignatureProvider() throws NoSuchAlgorithmException, InvalidKeyException {
        this(b);
    }

    private static InvalidKeyException method_c415ae41637537c828fb(
            InvalidKeyException invalidkeyexception) {
        return invalidkeyexception;
    }

    static {
        b = new String("SHA256WithRSA");
    }
}
