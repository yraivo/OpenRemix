package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

public final class LicenseCode6Exception extends LicenseCodeExceptionGroupC {
    private static Entity[] b;

    public int hashCode() {
        return 6;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    static {
        method_da2ac5c94686ee11e9e6();
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }
}
