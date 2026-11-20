package remix.compat.legacy.environment;

import net.minecraft.entity.Entity;

import remix.compat.legacy.codec.CipherKeySelector;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum HardwareIdHasher {
    bZf;

    private static String a;

    private static NoSuchAlgorithmException a(NoSuchAlgorithmException nosuchalgorithmexception) {
        return nosuchalgorithmexception;
    }

    // region String decoding support

    // endregion

    public String method_21521dc0472c20364de6(
            String text, HardwareFingerprintCollector hardwareFingerprintCollector, int i)
            throws NoSuchAlgorithmException {
        boolean flag = CipherKeySelector.method_d87039b07bd6961907f2();

        String textValue;
        label39:
        {
            try {
                if (false)
                    throw (java.security.NoSuchAlgorithmException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (text == null) {
                    textValue = a;
                    break label39;
                }
            } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(nosuchalgorithmexception));
            }

            textValue = text;
        }

        MessageDigest messagedigest = MessageDigest.getInstance(textValue);
        DefaultHardwareFingerprintCollector defaultHardwareFingerprintCollector =
                (DefaultHardwareFingerprintCollector) hardwareFingerprintCollector;
        Collection collection = defaultHardwareFingerprintCollector.fluentMapWrapper.aaZ();
        SignedTokenVerifier[] signedTokenVerifierArray =
                (SignedTokenVerifier[])
                        collection.toArray(new SignedTokenVerifier[collection.size()]);
        List list = Arrays.asList(Arrays.copyOfRange(signedTokenVerifierArray, 0, i));
        int j = 0;
        int k = list.size();

        while (j < k) {
            SignedTokenVerifier signedTokenVerifier = (SignedTokenVerifier) list.get(j);
            messagedigest.update(
                    signedTokenVerifier
                            .getSignedTokenPayload()
                            .method_c60693f457aa70be7160()
                            .getBytes());
            j++;
            if (flag) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
                break;
            }
        }

        StringBuilder stringbuilder = new StringBuilder();
        byte[] abyte = messagedigest.digest();
        int i1 = 0;
        int l = abyte.length;

        while (i1 < l) {
            byte b0 = abyte[i1];
            stringbuilder.append(String.format("%x", b0).toUpperCase());
            i1++;
            if (flag) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    static {
        a = new String("MD5");
    }

    // region String decoding support

    // endregion

    private HardwareIdHasher() {}
}
