package remix.compat.legacy.security;

import remix.util.collection.FluentIterableList;

import net.minecraft.entity.Entity;

public class DefaultSecurityPoolProvider implements remix.compat.legacy.security.SecurityPoolProvider {
    private static Entity[] b;
    private FluentIterableList fluentIterableList;

    public FluentIterableList getFluentIterableList() {
        return this.fluentIterableList;
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    public Object method_a6372c5460944673d5f1(
            SecurityCheckEntry securityCheckEntry) {
        return (java.lang.Object) securityCheckEntry.cy();
    }

    public DefaultSecurityPoolProvider(IndexedValuePool... indexedValuePoolArray) {
        this.fluentIterableList = new FluentIterableList(indexedValuePoolArray);
    }

    static {
        if (method_da2ac5c94686ee11e9e6() == null) {
            b(new Entity[2]);
        }
    }
}
