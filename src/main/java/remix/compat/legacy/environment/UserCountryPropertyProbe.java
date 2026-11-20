package remix.compat.legacy.environment;

import net.minecraft.entity.Entity;

import remix.compat.invocation.MethodFunction;
import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class UserCountryPropertyProbe implements SystemPropertyProbe {

    public String method_e0e3092a59fee58fae52() {
        return new String("gP");
    }

    private static SignatureVerificationException method_bc775a43870e8f3ea78c(
            SignatureVerificationException signatureVerificationException) {
        return signatureVerificationException;
    }

    public SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        UserHomePropertyProbe.method_da2ac5c94686ee11e9e6();

        try {
            SignedTokenVerifier signedTokenVerifier =
                    new SignedTokenVerifier(
                            new String("user-country"),
                            (String)
                                    ((MethodFunction) methodIndirectionMarker)
                                            .apply(new String("user.country")));
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                UserHomePropertyProbe.b(new Entity[4]);
            }

            return signedTokenVerifier;
        } catch (SignatureVerificationException signatureVerificationException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_bc775a43870e8f3ea78c(signatureVerificationException));
        }
    }


}
