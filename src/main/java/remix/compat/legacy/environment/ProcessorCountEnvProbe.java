package remix.compat.legacy.environment;

import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import remix.compat.invocation.MethodFunction;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class ProcessorCountEnvProbe implements SystemPropertyProbe {

    public SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        return new SignedTokenVerifier(
                new String("system processors"),
                (String)
                        ((MethodFunction) methodIndirectionMarker)
                                .apply(new String("NUMBER_OF_PROCESSORS")));
    }

    public String method_e0e3092a59fee58fae52() {
        return new String("gE");
    }


}
