package remix.util.reflection;

import net.minecraft.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

public final class MethodReflectionErrorHelper {

    private static Entity[] b;

    public static void method_ebef9bd0e25819db6735(Exception exception) {
        try {
            if (exception instanceof NoSuchMethodException) {
                throw new IllegalStateException(
                        new String("Couldn't find the method:\\n") + exception.getMessage());
            }
        } catch (IllegalStateException illegalstateexception1) {
            throw method_b3985d2d68c259fdadde(illegalstateexception1);
        }

        try {
            if (exception instanceof IllegalAccessException) {
                throw new IllegalStateException(
                        new String("Unable to access method:\\n") + exception.getMessage());
            }
        } catch (IllegalStateException illegalstateexception3) {
            throw method_b3985d2d68c259fdadde(illegalstateexception3);
        }

        try {
            if (exception instanceof InvocationTargetException) {
                method_329164aa11dc6af5c7b3((InvocationTargetException) exception);
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        try {
            if (exception instanceof RuntimeException) {
                throw (RuntimeException) exception;
            }
        } catch (IllegalStateException illegalstateexception2) {
            throw method_b3985d2d68c259fdadde(illegalstateexception2);
        }

        throw new UndeclaredThrowableException(exception);
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    // region String decoding support

    // endregion

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }

    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[1];
        b(aclass_b6df81f333fa31dc98ea);
    }

    // region String decoding support

    // endregion

    public static void method_c1db10ee27925d156a3f(Throwable throwable) {
        try {
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        try {
            if (throwable instanceof Error) {
                throw (Error) throwable;
            }
        } catch (IllegalStateException illegalstateexception1) {
            throw method_b3985d2d68c259fdadde(illegalstateexception1);
        }

        throw new UndeclaredThrowableException(throwable);
    }

    public static void method_329164aa11dc6af5c7b3(
            InvocationTargetException invocationtargetexception) {
        method_c1db10ee27925d156a3f(invocationtargetexception.getTargetException());
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }
}
