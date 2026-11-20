package remix.compat.legacy.auth;

import remix.compat.legacy.codec.CipherKeySelector;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.environment.DefaultHardwareFingerprintCollector;
import remix.compat.legacy.environment.HardwareFingerprintCollector;
import remix.compat.legacy.environment.HardwareIdHasher;

import net.minecraft.entity.Entity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class DefaultClientIdentityToken implements ClientIdentityToken {
    public static HardwareFingerprintCollector bTY;
    private static String f;
    private String b;

    private int dD;
    private String e;
    private CipherKeySelector cipherKeySelector;

    public HardwareFingerprintCollector acv() {
        return bTY;
    }

    // region String decoding support

    // endregion

    private static Exception a(Exception exception) {
        return exception;
    }

    public CipherKeySelector getCipherKeySelector() {
        return this.cipherKeySelector;
    }

    public DefaultClientIdentityToken(
            CipherKeySelector bUaValue, String text, String textValue) throws Exception {
        this.b = new String("7044411FD9BE96A0762CC95353AD2195");
        boolean flag1 = CipherKeySelector.method_d87039b07bd6961907f2();
        this.dD = new LicenseCode3Exception().hashCode();
        boolean flag = flag1;
        this.cipherKeySelector = bUaValue;
        this.e = textValue != null ? textValue : this.a(text);
        if (this.e.equals(this.b)) {
            this.dD = new LicenseCode7ArraySizeException().hashCode();
            this.e = this.a(text);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            CipherKeySelector.method_a8745b073fa8adfa1857(!flag);
        }
    }

    static {
        f = "438ljcrO62QZ1KdfV8GMEWD/YUq/mtoEkO76avLx3zE=";
        bTY = new DefaultHardwareFingerprintCollector();
        try {
            bTY.runProbes();
        } catch (SignatureVerificationException
                | InvalidKeyException
                | NoSuchAlgorithmException class_314ad95d24b5c5a5fb141) {
            class_314ad95d24b5c5a5fb141.printStackTrace();
        }
    }

    // region String decoding support

    // endregion

    public DefaultClientIdentityToken(String text, String textValue, String textValue2)
            throws Exception {
        this(new CipherKeySelector(text, true), textValue, textValue2);
    }

    public String method_c60693f457aa70be7160() {
        return this.e;
    }

    private String a(String text) throws NoSuchAlgorithmException {
        return HardwareIdHasher.bZf.method_21521dc0472c20364de6(text, bTY, this.dD);
    }

    public DefaultClientIdentityToken(String text, String textValue) throws Exception {
        this(new CipherKeySelector(text, true), textValue, null);
    }
}
