package remix.util.reflection;

import net.minecraft.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class ZeroArgReflectiveInstantiator<T> implements ReflectiveInstantiator<T> {
    private static String[] packedTextFragments;
    private static ZeroArgReflectiveInstantiator<?> bUg;

    public static int cz;

    private ZeroArgReflectiveInstantiator() {}

    private static NoSuchMethodException method_83e6333c3e2f3d1028ba(
            NoSuchMethodException nosuchmethodexception) {
        return nosuchmethodexception;
    }

    static {
        remix.util.reflection.ZeroArgReflectiveInstantiator.packedTextFragments = new String[3];
        remix.util.reflection.ZeroArgReflectiveInstantiator.packedTextFragments[0] =
                new String("sLvYkc");
        bUg = new ZeroArgReflectiveInstantiator();
    }

    public static <T> ZeroArgReflectiveInstantiator<T> acF() {
        return (ZeroArgReflectiveInstantiator<T>) bUg;
    }

    @Override
    public T method_e7c01bb2d92c72fb2ce9(Class<? extends T> oclass, ClassLoader classloader)
            throws ReflectiveInstantiationException {
        String text = ReflectiveInstantiationException.method_c60693f457aa70be7160();

        Object object;
        try {
            Constructor constructor = oclass.getDeclaredConstructor();

            try {
                if (false)
                    throw (java.lang.NoSuchMethodException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
            } catch (NoSuchMethodException nosuchmethodexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_83e6333c3e2f3d1028ba(nosuchmethodexception));
            }

            try {
                object = constructor.newInstance();
            } finally {
                cz++;
            }
        } catch (NoSuchMethodException nosuchmethodexception1) {
            throw new ReflectiveInstantiationException(
                    new String("Unable to find a valid zero-argument constructor ")
                            + new String("for class \\\"")
                            + oclass.getCanonicalName()
                            + new String("\\\""));
        } catch (IllegalAccessException illegalaccessexception) {
            throw new ReflectiveInstantiationException(
                    new String("Cannot access zero-argument constructor ")
                            + new String("of class \\\"")
                            + oclass.getCanonicalName()
                            + new String("\\\":"),
                    illegalaccessexception);
        } catch (InvocationTargetException | InstantiationException invocationtargetexception) {
            invocationtargetexception.printStackTrace();
            throw new ReflectiveInstantiationException(
                    new String("An exception was thrown while trying to ")
                            + new String("instantiate \\\"")
                            + oclass.getCanonicalName()
                            + new String("\\\":"),
                    invocationtargetexception);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ReflectiveInstantiationException.b(packedTextFragments[0]);
        }

        return (T) object;
    }
}
