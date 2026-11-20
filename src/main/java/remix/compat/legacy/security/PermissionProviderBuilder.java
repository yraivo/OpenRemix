package remix.compat.legacy.security;

import java.util.HashMap;
import java.util.Map;

public class PermissionProviderBuilder {
    private PermissionFactoryRegistry permissionFactoryRegistry;
    private static String c;

    private static Map<String, PermissionCheckGroup> dI;

    public PermissionProviderBuilder method_b00738260ba08a951532(
            String text, PermissionCheckGroup permissionCheckGroup) {
        dI.put(text, permissionCheckGroup);
        return this;
    }

    public PermissionProviderBuilder method_e69150ddff8064b086d0(
            PermissionFactoryRegistry bTOValue) {
        this.permissionFactoryRegistry = bTOValue;
        return this;
    }

    public PermissionProvider acm() {
        try {
            if (this.permissionFactoryRegistry == null) {
                throw new IllegalStateException(
                        new String("You must finish building the permission provider!"));
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        return new PermissionProvider(this.permissionFactoryRegistry, dI);
    }

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        c = "8opetDZodlFnq3VLjer+If1CjB/CY12Uq8pmorly9Yqj4DukPcx7weBCVBubLyOLNw==";
        dI = new HashMap<>();
    }
}
