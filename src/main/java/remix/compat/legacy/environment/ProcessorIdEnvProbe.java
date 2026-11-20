package remix.compat.legacy.environment;

import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import remix.compat.invocation.MethodFunction;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class ProcessorIdEnvProbe implements SystemPropertyProbe {

    public SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        return new SignedTokenVerifier(
                new String("system processor id"),
                (String)
                        ((MethodFunction) methodIndirectionMarker)
                                .apply(new String("PROCESSOR_IDENTIFIER")));
    }

    public String method_e0e3092a59fee58fae52() {
        return new String("gE");
    }


}
