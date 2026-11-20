package remix.event.discovery;

public interface MemberDiscoveryStrategy {
    boolean isDuplicateMember(
            java.lang.reflect.AnnotatedElement e, java.lang.reflect.AnnotatedElement e1);

    void collectMembers(Class oclass, java.util.List list);

    java.lang.reflect.AnnotatedElement[] discoverMembers(Class oclass);

    static MethodDiscoveryStrategy afI() {
        return new MethodDiscoveryStrategy();
    }

    static FieldDiscoveryStrategy afJ() {
        return new FieldDiscoveryStrategy();
    }
}
