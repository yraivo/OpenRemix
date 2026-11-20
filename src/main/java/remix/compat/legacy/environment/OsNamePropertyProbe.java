package remix.compat.legacy.environment;

import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import remix.compat.invocation.MethodFunction;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class OsNamePropertyProbe implements SystemPropertyProbe {

    public SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        return new SignedTokenVerifier(
                new String("operative system"),
                (String)
                        ((MethodFunction) methodIndirectionMarker).apply(new String("os.name")));
    }


    public String method_e0e3092a59fee58fae52() {
        return new String("gP");
    }
}
