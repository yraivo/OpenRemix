package remix.compat.legacy.security;

import remix.compat.legacy.auth.LicenseSession;

import java.util.Map;

public class PermissionProvider {
    private PermissionFactoryRegistry permissionFactoryRegistry;
    private Map<String, PermissionCheckGroup> map;

    public ResourcePermission<?> method_ffdee717744fea1491ab(
            LicenseSession licenseSession,
            Class<?> oclass,
            String text,
            int i) {
        PermissionCheckGroup permissionCheckGroup = this.map.get(text);
        return permissionCheckGroup != null
                        && !permissionCheckGroup.passesLicenseChecks(
                                licenseSession)
                ? this.permissionFactoryRegistry.createPermission(oclass, text, i)
                : null;
    }

    PermissionProvider(
            PermissionFactoryRegistry bTNValue, Map<String, PermissionCheckGroup> map) {
        this.permissionFactoryRegistry = bTNValue;
        this.map = map;
    }

    public static final PermissionProviderBuilder acl() {
        return new PermissionProviderBuilder();
    }
}
