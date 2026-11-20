package remix.compat.legacy.security;

import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public final class RegisteredResourceEntry {
    private Object resource;
    AbstractResourcePermissionRegistry abstractResourcePermissionRegistry;
    private java.util.Set permissions;

    static Set getPermissionSet(RegisteredResourceEntry registeredResourceEntry) {
        return registeredResourceEntry.permissions;
    }

    public Object getResource() {
        return this.resource;
    }

    public boolean addPermission(
            ResourcePermission resourcePermission) {
        return this.permissions.add(resourcePermission);
    }

    public RegisteredResourceEntry(AbstractResourcePermissionRegistry bTSValue, Object object) {
        this.abstractResourcePermissionRegistry = bTSValue;
        String[] astring1 = IdentifiedResource.getLegacyResourceMarker();
        this.permissions = new HashSet<>();
        String[] astring = astring1;
        this.resource = (java.lang.Object) object;
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }
}
