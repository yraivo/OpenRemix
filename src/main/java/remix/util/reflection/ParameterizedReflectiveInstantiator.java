package remix.util.reflection;

import net.minecraft.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public final class ParameterizedReflectiveInstantiator<T> implements ReflectiveInstantiator<T> {
    private Object[] aiP;
    public static int c;

    private Class[] Jj;


    private void method_936dceb53b480ca4f9be(Constructor<?> constructor) {
        try {
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
        } catch (NoSuchElementException nosuchelementexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(nosuchelementexception));
        }
    }

    public ParameterizedReflectiveInstantiator(Object[] aobject, Class[] aclass) {
        this.aiP = aobject;
        this.Jj = aclass;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private static Constructor<?> method_40d3522763cedcfd4568(Class<?> oclass, Object[] aobject)
            throws NoSuchMethodException {
        String text = ReflectiveInstantiationException.method_c60693f457aa70be7160();
        Constructor[] aconstructor = oclass.getDeclaredConstructors();
        String textValue = text;

        for (Constructor constructor : aconstructor) {
            try {
                if (false)
                    throw (java.lang.NoSuchMethodException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (Arrays.equals(constructor.getParameterTypes(), aobject)) {
                    return constructor;
                }
            } catch (NoSuchMethodException nosuchmethodexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(nosuchmethodexception));
            }

            if (textValue != null) {
                break;
            }
        }

        throw new NoSuchMethodException(
                new String("No constructor(s) matching parameters ") + Arrays.toString(aobject));
    }

    public T method_e7c01bb2d92c72fb2ce9(Class<? extends T> oclass, ClassLoader classloader)
            throws ReflectiveInstantiationException {
        String text = ReflectiveInstantiationException.method_c60693f457aa70be7160();

        Constructor constructor;
        try {
            constructor = method_40d3522763cedcfd4568(oclass, this.Jj);
        } catch (NoSuchMethodException nosuchmethodexception) {
            throw new ReflectiveInstantiationException(
                    new String("Unable to instantiate plugin ")
                            + new String("with type \\\"")
                            + oclass.getSimpleName()
                            + new String("\\\":"),
                    nosuchmethodexception);
        }

        this.method_936dceb53b480ca4f9be(constructor);

        Object object;
        try {
            try {
                object = constructor.newInstance(this.aiP);
            } finally {
                c++;
            }
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchElementException instantiationexception) {
            throw new ReflectiveInstantiationException(
                    new String("Unable to instantiate plugin ")
                            + new String("with type \\\"")
                            + oclass.getCanonicalName()
                            + new String("\\\":"),
                    instantiationexception);
        }

        if (text != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }

        return (T) object;
    }
}
