package remix.compat.legacy.invocation;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;
import remix.util.reflection.MethodReflectionErrorHelper;

import remix.compat.invocation.MethodBiFunction;
import remix.compat.invocation.MethodConsumer;
import remix.compat.invocation.MethodFunction;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;

public final class SystemMethodHandleRegistry {
    private static Lookup bTm;
    public static MethodBiFunction<String, String, String> bTn;

    public static MethodConsumer<Integer> bTp;
    private static Class<?> bSu;
    public static FluentMapWrapper bOw;
    public static final int c = 0;
    public static MethodFunction<String, String> bTq;

    public static MethodFunction<String, String> bTr;

    static {
        bOw = FluentCollections.method_a09b51431db5c67c4348(new HashMap());
        bTm = MethodHandles.lookup();
        bSu = System.class;
        bOw.putEntry(
                new String("gPwD"),
                bTn =
                        method_194febf6d5da8b16e69d(
                                String.class, String.class, bSu, new String("getProperty")));
        bOw.putEntry(
                new String("gE"),
                bTq = method_3129206b4ce6ded50c91(String.class, bSu, new String("getenv")));
        bOw.putEntry(
                new String("gP"),
                bTr = method_3129206b4ce6ded50c91(String.class, bSu, new String("getProperty")));
    }

    private static <A, R> MethodFunction<A, R> method_3129206b4ce6ded50c91(
            Class<A> oclass, Class<?> oclass1, String text) {
        try {
            String textValue = new String("apply");
            MethodHandle methodhandle = method_9fbf400250a6b38a24c8(oclass1, text, oclass);
            MethodType methodtype = methodhandle.type();
            CallSite callsite =
                    LambdaMetafactory.metafactory(
                            bTm,
                            textValue,
                            MethodType.methodType(MethodFunction.class),
                            methodtype.generic(),
                            methodhandle,
                            methodtype);
            return (MethodFunction) callsite.getTarget().invokeExact();
        } catch (Throwable throwable) {
            MethodReflectionErrorHelper.method_c1db10ee27925d156a3f(throwable);
            return null;
        }
    }

    private static <A> MethodConsumer<A> method_9a27493c62e8e8c62984(
            Class<A> oclass, Class<?> oclass1, String text) {
        try {
            String textValue = new String("accept");
            MethodHandle methodhandle = method_9fbf400250a6b38a24c8(oclass1, text, oclass);
            MethodType methodtype = MethodType.methodType(void.class, oclass);
            CallSite callsite =
                    LambdaMetafactory.metafactory(
                            bTm,
                            textValue,
                            MethodType.methodType(MethodConsumer.class),
                            methodtype.changeParameterType(0, Object.class),
                            methodhandle,
                            methodtype);
            return (MethodConsumer) callsite.getTarget().invokeExact();
        } catch (Throwable throwable) {
            MethodReflectionErrorHelper.method_c1db10ee27925d156a3f(throwable);
            return null;
        }
    }

    private static final <R> MethodHandle method_9fbf400250a6b38a24c8(
            Class<?> oclass, String text, Class... aclass) {
        Method method = null;

        try {
            method = oclass.getMethod(text, aclass);
            method.setAccessible(true);
        } catch (NoSuchMethodException nosuchmethodexception) {
            MethodReflectionErrorHelper.method_ebef9bd0e25819db6735(nosuchmethodexception);
        }

        try {
            Lookup lookup = bTm.in(oclass);
            MethodHandle methodhandle = lookup.unreflect(method);
            if (methodhandle != null) {
                return methodhandle;
            } else {
                throw new RuntimeException(
                        new String("Unable to do lookup on method \\\"")
                                + method.getName()
                                + new String("\\\"."));
            }
        } catch (Throwable throwable) {
            MethodReflectionErrorHelper.method_c1db10ee27925d156a3f(throwable);
            return null;
        }
    }

    private static <A, B, R> MethodBiFunction<A, B, R> method_194febf6d5da8b16e69d(
            Class<A> oclass, Class<B> oclass1, Class<?> oclass2, String text) {
        try {
            String textValue = new String("apply");
            MethodHandle methodhandle = method_9fbf400250a6b38a24c8(oclass2, text, oclass, oclass1);
            MethodType methodtype = methodhandle.type();
            CallSite callsite =
                    LambdaMetafactory.metafactory(
                            bTm,
                            textValue,
                            MethodType.methodType(MethodBiFunction.class),
                            methodtype.generic(),
                            methodhandle,
                            methodtype);
            return (MethodBiFunction) callsite.getTarget().invokeExact();
        } catch (Throwable throwable) {
            MethodReflectionErrorHelper.method_c1db10ee27925d156a3f(throwable);
            return null;
        }
    }
}
