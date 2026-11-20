package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.legacy.environment.HardwareFingerprintCollector;

public final class RoleTierFloatProbe extends remix.util.task.NamedValueHolder {
    private static String[] packedTextFragments;
    private static int[] legacyRoleFloatProbeMarker;

    public Object getValue() {
        return this.evaluateRoleFloatProbe();
    }

    public Float evaluateRoleFloatProbe() {
        getLegacyRoleFloatProbeMarker();
        HardwareFingerprintCollector bTY = DefaultClientIdentityToken.bTY;

        UserRoleTier userRoleTier;
        try {
            userRoleTier =
                    ((ClientServices) RemixClient.INSTANCE.getClientServices())
                            .getLicenseSessionVerifier()
                            .getLicenseProfileRecord()
                            .getLicenseSession()
                            .getUserRoleTier();
        } catch (Exception exception2) {
            userRoleTier = UserRoleTier.BANNED_EXTRA;
            return (float) userRoleTier.ordinal();
        }

        try {
            if (userRoleTier.equals(UserRoleTier.BANNED)) {
                return (Float) super.getValue();
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(exception1);
        }

        try {
            if (bTY.aW() == 13) {
                this.setValue(Float.NEGATIVE_INFINITY);
            }
        } catch (Exception exception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(exception3);
        }

        try {
            Float floatValue = (Float) super.getValue();
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                setLegacyRoleFloatProbeMarker(new int[3]);
            }

            return floatValue;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(exception);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.RoleTierFloatProbe.packedTextFragments[0] = new String("");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (getLegacyRoleFloatProbeMarker() == null) {
            setLegacyRoleFloatProbeMarker(new int[2]);
        }
    }

    // region String decoding support

    // endregion

    public static int[] getLegacyRoleFloatProbeMarker() {
        return remix.compat.legacy.auth.RoleTierFloatProbe.legacyRoleFloatProbeMarker;
    }

    public RoleTierFloatProbe() {
        super(1.17549435E-38F, packedTextFragments[0]);
    }

    public static void setLegacyRoleFloatProbeMarker(int[] aint) {
        remix.compat.legacy.auth.RoleTierFloatProbe.legacyRoleFloatProbeMarker = aint;
    }
}
