package remix.compat.legacy.environment;

import remix.compat.invocation.MethodFunction;
import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class ProcessorArchEnvProbe implements SystemPropertyProbe {

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        UserHomePropertyProbe.method_da2ac5c94686ee11e9e6();

        String text =
                (String)
                        ((MethodFunction) methodIndirectionMarker)
                                .apply(new String("PROCESSOR_ARCHITECTURE"));

        String textValue =
                (String)
                        ((MethodFunction) methodIndirectionMarker)
                                .apply(new String("PROCESSOR_ARCHITEW6432"));

        byte b0;
        try {
            b0 = remix.compat.compiler.JvmBooleanSlots.toByte(text.endsWith(new String("64")));
        } catch (NullPointerException nullpointerexception) {
            b0 = 0;
        }

        String textValue2 =
                b0 == 0 && (textValue == null || !textValue.endsWith(new String("64")))
                        ? new String("32")
                        : new String("64");
        SignedTokenVerifier signedTokenVerifier =
                new SignedTokenVerifier(new String("system architecture"), textValue2);

        return signedTokenVerifier;
    }

    public String method_e0e3092a59fee58fae52() {
        return new String("gE");
    }


}
