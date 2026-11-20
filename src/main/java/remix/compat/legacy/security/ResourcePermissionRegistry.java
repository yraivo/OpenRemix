package remix.compat.legacy.security;

public interface ResourcePermissionRegistry {
    void registerAnnotatedClass(Class oclass);

    java.util.Collection getRegisteredPermissions(String text);

    String resolveResourceKey(IdentifiedResource identifiedResource);

    Class getResourceClass(Object r);

    boolean matchesResourceType(IdentifiedResource identifiedResource);
}
