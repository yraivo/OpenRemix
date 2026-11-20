package remix.compat.legacy.security;

import remix.util.collection.FluentIterableList;

import net.minecraft.entity.Entity;

public final class IndexedSecurityCheckSelection implements SecurityCheckSelection {
    private static Entity[] aXG;
    private FluentIterableList bTb;
    private final FluentIterableList bTa = new FluentIterableList();

    public FluentIterableList abO() {
        return this.bTb;
    }

    public FluentIterableList abR() {
        return this.bTa;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return aXG;
    }

    static {
        if (method_da2ac5c94686ee11e9e6() == null) {
            b(new Entity[4]);
        }
    }

    public IndexedSecurityCheckSelection(Class oclass, Integer... ainteger) {
        this.bTb = new FluentIterableList();

        for (java.util.Iterator<?> __recoveryIterator171_1234 =
                        (SecurityManagerRegistry.method_6bd29f0a85a8c7569327(oclass)).iterator();
                (__recoveryIterator171_1234).hasNext(); ) {
            SecurityCheckManager securityCheckManager =
                    (SecurityCheckManager) (__recoveryIterator171_1234).next();
            for (Integer integer : ainteger) {
                securityCheckManager
                        .getFluentMapWrapper()
                        .findValueByKey(integer)
                        .ifPresent(
                                ((java.util.function.Consumer<java.lang.Object>)
                                        (object -> this.bTa.a(object))));
            }

            this.bTb.a(securityCheckManager.getSecurityPoolProvider());
        }
    }

    public static void b(Entity[] aXGValue) {
        aXG = aXGValue;
    }
}
