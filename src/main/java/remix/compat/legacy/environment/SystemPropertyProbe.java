package remix.compat.legacy.environment;

import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SystemPropertyProbe {
    SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException;

    String method_e0e3092a59fee58fae52();
}
