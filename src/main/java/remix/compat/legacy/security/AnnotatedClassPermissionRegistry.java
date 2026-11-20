package remix.compat.legacy.security;

import net.minecraft.entity.Entity;

public final class AnnotatedClassPermissionRegistry extends remix.compat.legacy.security.AbstractResourcePermissionRegistry {

    public void registerAnnotatedClass(Class resourceClass) {
        boolean legacyFirstPermission = IdentifiedResource.getLegacyResourceMarker() != null;
        if (this.getRegisteredResources().containsKey(resourceClass)) {
            return;
        }

        ProtectedResource declaration =
                (ProtectedResource) resourceClass.getDeclaredAnnotation(ProtectedResource.class);
        IdentifiedResource identifiedResource =
                new IdentifiedResource<>(resourceClass, declaration.resourceIdentifier());
        if (!this.matchesResourceType(identifiedResource)) {
            return;
        }

        String resourceKey = this.resolveResourceKey(identifiedResource);
        RegisteredResourceEntry entry = new RegisteredResourceEntry(this, resourceClass);
        for (PermissionSpec permission : declaration.permissions()) {
            ResourcePermission resourcePermission = this.getPermissionFactoryRegistry()
                    .createPermission(
                            resourceClass,
                            permission.permissionName(),
                            permission.priority().getRank());
            entry.addPermission(resourcePermission);
            if (legacyFirstPermission) {
                break;
            }
        }
        this.getRegisteredResources().put(resourceKey, entry);
    }


    public AnnotatedClassPermissionRegistry(PermissionFactoryRegistry permissionFactoryRegistry) {
        super(permissionFactoryRegistry);
    }

    public String resolveResourceKey(IdentifiedResource identifiedResource) {
        IdentifiedResource.getLegacyResourceMarker();
        String identifier = identifiedResource.getIdentifier();
        if (identifier.equals("invalid-resource")) {
            throw new InvalidResourceIdentifierException(identifiedResource);
        }
        if (identifier.equals("generic-resource")) {
            return "generic-resource-" + identifiedResource.getResource().hashCode();
        }

        String resourceKey = identifiedResource.getIdentifier();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            IdentifiedResource.setLegacyResourceMarker(new String[1]);
        }
        return resourceKey;
    }

    private static InvalidResourceIdentifierException preserveInvalidResourceException(
            InvalidResourceIdentifierException invalidResourceIdentifierException) {
        return invalidResourceIdentifierException;
    }

    public Class getResourceClass(Class oclass) {
        try {
            if (oclass instanceof Class) {
                return oclass;
            }
        } catch (InvalidResourceIdentifierException invalidResourceIdentifierException) {
            throw preserveInvalidResourceException(invalidResourceIdentifierException);
        }

        return oclass.getClass();
    }

    public Class getResourceClass(Object object) {
        return this.getResourceClass((Class<?>) object);
    }

    public boolean matchesResourceType(
            IdentifiedResource identifiedResource) {
        return Class.class.equals(identifiedResource.getResource());
    }
}
