package remix.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

public class ReflectionErrorHelper {
    private static boolean b;

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();

        try {
            return !flag;
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    public static void rethrowThrowable(Throwable throwable) {
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

    // region String decoding support

    // endregion

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }

    // region String decoding support

    // endregion

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }

    public static void rethrowInvocationTarget(
            InvocationTargetException invocationtargetexception) {
        rethrowThrowable(invocationtargetexception.getTargetException());
    }

    public static void rethrowReflectiveException(Exception exception) {
        try {
            if (exception instanceof NoSuchMethodException) {
                throw new IllegalStateException(
                        "Couldn't find the method:\n" + exception.getMessage());
            }
        } catch (IllegalStateException illegalstateexception1) {
            throw method_b3985d2d68c259fdadde(illegalstateexception1);
        }

        try {
            if (exception instanceof IllegalAccessException) {
                throw new IllegalStateException(
                        "Unable to access method:\n" + exception.getMessage());
            }
        } catch (IllegalStateException illegalstateexception3) {
            throw method_b3985d2d68c259fdadde(illegalstateexception3);
        }

        try {
            if (exception instanceof InvocationTargetException) {
                rethrowInvocationTarget((InvocationTargetException) exception);
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

    static {
        method_a8745b073fa8adfa1857(true);
    }
}
