package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.compat.legacy.environment.OperatingSystemType;

public abstract class AbstractLicenseSession implements LicenseSession {
    private ClientIdentityToken clientIdentityToken;
    private static int[] k;
    private UserRoleTier userRoleTier;

    public UserRoleTier getUserRoleTier() {
        return this.userRoleTier;
    }

    public static int[] method_650532ba484532b3efb0() {
        return k;
    }

    public OperatingSystemType acN() {
        return OperatingSystemType.aeZ();
    }

    static {
        if (method_650532ba484532b3efb0() != null) {
            b(new int[5]);
        }
    }

    public boolean aY() {
        return this.userRoleTier != null;
    }

    AbstractLicenseSession(
            ClientIdentityToken clientIdentityTokenValue, UserRoleTier userRoleTierValue) {
        super();

        this.clientIdentityToken = clientIdentityTokenValue;
        this.userRoleTier = userRoleTierValue;
        this.l();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new int[1]);
        }
    }

    public ClientIdentityToken getClientIdentityToken() {
        return this.clientIdentityToken;
    }

    public static void b(int[] aint) {
        k = aint;
    }
}
