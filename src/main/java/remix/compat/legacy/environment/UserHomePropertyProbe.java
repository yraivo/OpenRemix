package remix.compat.legacy.environment;

import remix.compat.invocation.MethodIndirectionMarker;
import remix.compat.legacy.crypto.SignatureVerificationException;
import remix.compat.legacy.crypto.SignedTokenVerifier;

import net.minecraft.entity.Entity;

import remix.compat.invocation.MethodFunction;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class UserHomePropertyProbe implements SystemPropertyProbe {

    private static Entity[] b;

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[3];
        b(aclass_b6df81f333fa31dc98ea);
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public SignedTokenVerifier method_b87e38a27ddf30035cdb(
            MethodIndirectionMarker methodIndirectionMarker)
            throws SignatureVerificationException, InvalidKeyException, NoSuchAlgorithmException {
        return new SignedTokenVerifier(
                new String("username-home"),
                (String)
                        ((MethodFunction) methodIndirectionMarker)
                                .apply(new String("user.home")));
    }

    public String method_e0e3092a59fee58fae52() {
        return new String("gP");
    }
}
