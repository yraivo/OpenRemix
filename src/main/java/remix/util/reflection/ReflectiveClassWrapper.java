package remix.util.reflection;

import remix.compat.legacy.security.SecurityCheckHelper;
import remix.util.collection.FluentListWrapper;

import net.minecraft.entity.Entity;

import java.util.Iterator;
import java.util.Optional;

public final class ReflectiveClassWrapper<T> {
    private Class<T> a;
    private FluentListWrapper in;
    private static Entity[] iT;
    private FluentListWrapper bPc;

    public FluentListWrapper ZV() {
        return this.bPc;
    }

    public static void b(Entity[] entityArray) {
        iT = entityArray;
    }

    public ReflectiveMethodAccessor method_00da736646037f40b0b2(String text, Class<?> oclass) {
        Ds();

        Iterator iterator = this.bPc.asList().iterator();

        while (iterator.hasNext()) {
            ReflectiveMethodAccessor reflectiveMethodAccessor =
                    (ReflectiveMethodAccessor) iterator.next();
            if (reflectiveMethodAccessor.getMethod().getName().equals(text)
                    && reflectiveMethodAccessor.abh().equals(oclass)) {
                return reflectiveMethodAccessor;
            }
        }

        return null;
    }

    public FluentListWrapper aau() {
        return this.in;
    }

    public static Entity[] Ds() {
        return iT;
    }

    public T method_d2c3a5ad661e2c5b1fe4(Object[] aobject, Class[] aclass)
            throws ReflectiveInstantiationException {
        return (T)
                new ParameterizedReflectiveInstantiator(aobject, aclass)
                        .method_e7c01bb2d92c72fb2ce9(
                                this.a, ReflectiveClassWrapper.class.getClassLoader());
    }

    public ReflectiveFieldAccessor findFieldOfType(Class<?> oclass) {
        Ds();

        ReflectiveFieldAccessor reflectiveFieldAccessor = null;

        for (java.util.Iterator<?> __recoveryIterator171_2478 = (this.in.asList()).iterator();
                (__recoveryIterator171_2478).hasNext(); ) {
            ReflectiveFieldAccessor reflectiveFieldAccessorValue =
                    (ReflectiveFieldAccessor) (__recoveryIterator171_2478).next();
            if (reflectiveFieldAccessorValue.abh().equals(oclass)) {
                reflectiveFieldAccessor = reflectiveFieldAccessorValue;
            }
        }

        return reflectiveFieldAccessor;
    }

    public ReflectiveFieldAccessor bG(int i) {
        Optional optional = SecurityCheckHelper.bYy.findClassByLegacyId(i);
        if (optional.isPresent()) {
            Class oclass = ((ReflectiveClassWrapper) optional.get()).abh();
            return this.findFieldOfType(oclass);
        } else {
            return null;
        }
    }

    public ReflectiveClassWrapper(Class<T> oclass) {
        this.a = oclass;
        this.in = ReflectiveFieldAccessor.method_ce11fb5bdc53be5f188c(oclass);
        this.bPc = ReflectiveMethodAccessor.method_ce11fb5bdc53be5f188c(oclass);
    }

    public Class<?> abh() {
        return this.a;
    }

    public ReflectiveFieldAccessor findFieldOfWrappedType(
            ReflectiveClassWrapper<?> reflectiveClassWrapper) {
        return this.findFieldOfType(reflectiveClassWrapper.abh());
    }

    public ReflectiveFieldAccessor bH(String text) {
        Ds();

        ReflectiveFieldAccessor reflectiveFieldAccessor = null;
        Iterator iterator = this.in.asList().iterator();

        while (iterator.hasNext()) {
            ReflectiveFieldAccessor reflectiveFieldAccessorValue =
                    (ReflectiveFieldAccessor) iterator.next();
            if (reflectiveFieldAccessorValue.q().equals(text)) {
                reflectiveFieldAccessor = reflectiveFieldAccessorValue;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new Entity[1]);
        }

        return reflectiveFieldAccessor;
    }

    public T Qu() throws ReflectiveInstantiationException {
        return (T)
                ZeroArgReflectiveInstantiator.acF()
                        .method_e7c01bb2d92c72fb2ce9(
                                this.a, ReflectiveClassWrapper.class.getClassLoader());
    }

    static {
        if (Ds() == null) {
            b(new Entity[1]);
        }
    }
}
