package remix.compat.legacy.auth;

import remix.compat.legacy.codec.CipherKeySelector;

import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.UtilEnumOS;

import java.io.Serializable;

public class LicenseSessionBuilder implements Serializable {
    public static boolean e;
    private static String[] packedTextFragments;
    private String d;
    private String a;
    private String c;
    private UserRoleTier userRoleTier;

    public LicenseSessionBuilder method_f68d032dfc443645a0fc(
            UserRoleTier bUnValue) {
        this.userRoleTier = bUnValue;
        return this;
    }

    public UtilEnumOS acV() {
        return Util.getOSType();
    }

    public String method_e0e3092a59fee58fae52() {
        return this.a;
    }

    public AbstractLicenseSession acW() throws Exception {
        int[] aint = AbstractLicenseSession.method_650532ba484532b3efb0();
        if (this.a != null
                && !this.a
                        .replaceAll(packedTextFragments[0], packedTextFragments[1])
                        .equals(packedTextFragments[2])) {
            e = true;
            DefaultLicenseSession defaultLicenseSession =
                    new DefaultLicenseSession(
                            new DefaultClientIdentityToken(
                                    new CipherKeySelector(this.a, true), this.d, this.c),
                            this.userRoleTier);
            if (aint != null) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }

            return defaultLicenseSession;
        } else {
            throw new IllegalStateException();
        }
    }

    public LicenseSessionBuilder bJ(String text) {
        this.a = text;
        return this;
    }

    public LicenseSessionBuilder bK(String text) {
        this.d = text;
        return this;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.LicenseSessionBuilder.packedTextFragments[0] = new String(" ");
        remix.compat.legacy.auth.LicenseSessionBuilder.packedTextFragments[1] = new String("");
        remix.compat.legacy.auth.LicenseSessionBuilder.packedTextFragments[2] = new String("");
    }

    // endregion

    public LicenseSessionBuilder bL(String text) {
        this.c = text;
        return this;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[3];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion
}
