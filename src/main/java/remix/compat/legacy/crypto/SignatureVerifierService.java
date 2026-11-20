package remix.compat.legacy.crypto;

import net.minecraft.entity.Entity;

import org.apache.commons.lang3.ArrayUtils;

import remix.util.collection.FluentIterableList;

import java.util.List;

public enum SignatureVerifierService {
    bZj;


    String method_0a6b652f7d4f2d8e4952(
            AbstractSignatureSigner abstractSignatureSigner,
            SignedTokenPayload signedTokenPayload,
            boolean flag)
            throws InvalidSignatureKeyException, SignatureVerificationException {
        String[] astring = SignedTokenVerifier.B();

        AbstractSignatureSigner abstractSignatureSignerValue;
        String text;
        label27:
        {
            try {
                if (false)
                    throw (remix.compat.legacy.crypto.InvalidSignatureKeyException)
                            null; // Original JVM checked handler; no emitted instructions.
                abstractSignatureSignerValue = abstractSignatureSigner;
                if (flag) {
                    text = signedTokenPayload.d();
                    break label27;
                }
            } catch (InvalidSignatureKeyException invalidSignatureKeyException) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_fbed3cc52ead77cdb513(invalidSignatureKeyException));
            }

            text = signedTokenPayload.method_c60693f457aa70be7160();
        }

        FluentIterableList fluentIterableList =
                abstractSignatureSignerValue.method_66633fc4ccd32aa15c42(text.getBytes());
        List list = fluentIterableList.eN();
        Byte[] abyte = (Byte[]) list.toArray(new Byte[list.size()]);

        try {
            if (false)
                throw (remix.compat.legacy.crypto.InvalidSignatureKeyException)
                        null; // Original JVM checked handler; no emitted instructions.
            String textValue = SignedTokenPayload.bTz.encodeToString(ArrayUtils.toPrimitive(abyte));
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                SignedTokenVerifier.b(new String[3]);
            }

            return textValue;
        } catch (InvalidSignatureKeyException invalidSignatureKeyExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_fbed3cc52ead77cdb513(invalidSignatureKeyExceptionValue));
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static InvalidSignatureKeyException method_fbed3cc52ead77cdb513(
            InvalidSignatureKeyException invalidSignatureKeyException) {
        return invalidSignatureKeyException;
    }

    private SignatureVerifierService() {}
}
