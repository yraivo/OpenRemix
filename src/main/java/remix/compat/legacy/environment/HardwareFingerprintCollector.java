package remix.compat.legacy.environment;

import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface HardwareFingerprintCollector {
    int cz = -1;
    int c = 0;
    int a = 1;

    void registerVerifier(
            String text, SignedTokenVerifier signedTokenVerifier);

    void registerProbe(
            String text, SystemPropertyProbe systemPropertyProbe)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException;

    SignedTokenVerifier bI(int i);

    String a(String text);

    String computeFingerprint();

    void runProbes()
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException;

    boolean containsProbe(String text);

    int hasVerifier(String text);

    void cQ();

    int aW();

    int getVerifierCount();
}
