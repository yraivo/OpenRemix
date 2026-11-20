package remix.compat.legacy.security;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractResourcePermissionRegistry implements remix.compat.legacy.security.ResourcePermissionRegistry {
    private java.util.Map registeredResources = new ConcurrentHashMap<>();
    private PermissionFactoryRegistry permissionFactoryRegistry;

    public AbstractResourcePermissionRegistry(PermissionFactoryRegistry bTOValue) {
        this.permissionFactoryRegistry = bTOValue;
    }

    public java.util.Map getRegisteredResources() {
        return this.registeredResources;
    }

    public PermissionFactoryRegistry getPermissionFactoryRegistry() {
        return this.permissionFactoryRegistry;
    }

    public java.util.Collection getRegisteredPermissions(String text) {
        return RegisteredResourceEntry.getPermissionSet(
                ((remix.compat.legacy.security.RegisteredResourceEntry) this.registeredResources.get(text)));
    }
}
