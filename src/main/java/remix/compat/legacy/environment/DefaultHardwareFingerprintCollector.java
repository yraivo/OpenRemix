package remix.compat.legacy.environment;

import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;
import remix.compat.legacy.invocation.SystemMethodHandleRegistry;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import net.minecraft.entity.Entity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DefaultHardwareFingerprintCollector implements HardwareFingerprintCollector {
    private static boolean f;
    private static String[] packedTextFragments;
    private static final SystemPropertyProbe[] bVF =
            new SystemPropertyProbe[] {
                new UserHomePropertyProbe(),
                new ProcessorArchEnvProbe(),
                new ProcessorCountEnvProbe(),
                new ProcessorIdEnvProbe(),
                new OsNamePropertyProbe(),
                new CpuIsaListPropertyProbe(),
                new ArchDataModelPropertyProbe(),
                new DiskSerialNumberProbe(),
                new UserCountryPropertyProbe(),
                new OsVersionPropertyProbe(),
                new FileEncodingPropertyProbe(),
                new CpuEndianPropertyProbe(),
                new UnicodeEncodingPropertyProbe()
            };
    public final FluentMapWrapper fluentMapWrapper =
            FluentCollections.method_a09b51431db5c67c4348(new LinkedHashMap());

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.environment.DefaultHardwareFingerprintCollector.packedTextFragments[0] = new String("");
    }

    // endregion

    public int hasVerifier(String text) {
        SignedTokenVerifier signedTokenVerifier =
                (SignedTokenVerifier) this.fluentMapWrapper.getEntry(text);
        return signedTokenVerifier != null ? 1 : -1;
    }

    public void registerVerifier(
            String text, SignedTokenVerifier signedTokenVerifier) {
        if (!this.fluentMapWrapper.method_6a46953d5f41322f2439(text)) {
            this.fluentMapWrapper.putEntry(text, signedTokenVerifier);
        }
    }

    public void cQ() {
        ((Map) this.fluentMapWrapper.Qu())
                .replaceAll(
                        ((java.util.function.BiFunction<
                                        java.lang.String,
                                        remix.compat.legacy.crypto.SignedTokenVerifier,
                                        remix.compat.legacy.crypto.SignedTokenVerifier>)
                                ((text, signedTokenVerifier) -> null)));
    }

    public String a(String text) {
        return this.fluentMapWrapper.method_6a46953d5f41322f2439(text)
                ? ((SignedTokenVerifier) this.fluentMapWrapper.getEntry(text))
                        .getSignedTokenPayload()
                        .method_cee993844cce4b97ed62()
                : packedTextFragments[0];
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        f = flag;
    }

    public void runProbes()
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        SystemPropertyProbe[] bVFValue = bVF;
        boolean flag1 = method_d87039b07bd6961907f2();
        int length = bVFValue.length;
        int j = 0;
        boolean flag = flag1;

        while (j < length) {
            SystemPropertyProbe systemPropertyProbe = bVFValue[j];
            MethodIndirectionMarker methodIndirectionMarker =
                    (MethodIndirectionMarker)
                            SystemMethodHandleRegistry.bOw.getEntry(
                                    systemPropertyProbe.method_e0e3092a59fee58fae52());
            if (methodIndirectionMarker != null) {
                SignedTokenVerifier signedTokenVerifier =
                        systemPropertyProbe.method_b87e38a27ddf30035cdb(
                                methodIndirectionMarker);
                this.registerVerifier(
                        signedTokenVerifier.getSignedTokenPayload().method_e0e3092a59fee58fae52(),
                        signedTokenVerifier);
            }

            j++;
            if (flag) {
                break;
            }
        }
    }

    public boolean containsProbe(String text) {
        return this.fluentMapWrapper.method_6a46953d5f41322f2439(text)
                ? this.fluentMapWrapper.method_f0b8fe7eb8ebef089956(text) != null
                : false;
    }

    public SignedTokenVerifier bI(int i) {
        return ((remix.compat.legacy.crypto.SignedTokenVerifier)
                new ArrayList<SignedTokenVerifier>(((Map) this.fluentMapWrapper.Qu()).values()).get(i));
    }

    public int getVerifierCount() {
        boolean flag1 = method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_4806 = (this.fluentMapWrapper.aaZ()).iterator();
                (__recoveryIterator171_4806).hasNext(); ) {
            SignedTokenVerifier signedTokenVerifier =
                    (SignedTokenVerifier) (__recoveryIterator171_4806).next();
            i++;
            if (!flag) {
                break;
            }
        }

        return i;
    }

    public void registerProbe(
            String text, SystemPropertyProbe systemPropertyProbe)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        if (!this.fluentMapWrapper.method_6a46953d5f41322f2439(text)) {
            MethodIndirectionMarker methodIndirectionMarker =
                    (MethodIndirectionMarker)
                            SystemMethodHandleRegistry.bOw.getEntry(
                                    systemPropertyProbe.method_e0e3092a59fee58fae52());
            if (methodIndirectionMarker != null) {
                SignedTokenVerifier signedTokenVerifier =
                        systemPropertyProbe.method_b87e38a27ddf30035cdb(
                                methodIndirectionMarker);
                this.fluentMapWrapper.putEntry(text, signedTokenVerifier);
            }
        }
    }

    public static boolean method_b417d7500ec59d69e373() {
        return f;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        method_a8745b073fa8adfa1857(true);
    }

    public int aW() {
        boolean flag1 = method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_6213 = (this.fluentMapWrapper.BC()).iterator();
                (__recoveryIterator171_6213).hasNext(); ) {
            String text = (String) (__recoveryIterator171_6213).next();
            i += this.hasVerifier(text);
            if (flag) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
                break;
            }
        }

        return i;
    }

    // region String decoding support

    // endregion

    public String computeFingerprint() {
        boolean flag1 = method_b417d7500ec59d69e373();
        String text = null;
        Iterator iterator = this.fluentMapWrapper.aaZ().iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            SignedTokenVerifier signedTokenVerifier =
                    (SignedTokenVerifier) iterator.next();
            text = text + signedTokenVerifier.getSignedTokenPayload().method_cee993844cce4b97ed62();
            if (!flag) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_a8745b073fa8adfa1857(!flag);
        }

        return text;
    }
}
