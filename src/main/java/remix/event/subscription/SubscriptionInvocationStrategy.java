package remix.event.subscription;

import remix.event.Event;
import remix.util.reflection.ReflectionErrorHelper;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public enum SubscriptionInvocationStrategy {
    METHOD_REFLECTIVE_INVOCATION(SubscriptionMemberKind.METHOD) {
        @Override
        public <O, E extends Event> Subscription<E> method_c8dbd21017126aa55307(
                Class<E> oclass, O object, AnnotatedElement annotatedelement) {
            SubscriptionMemberKind.method_b417d7500ec59d69e373();

            Method method = (Method) annotatedelement;
            method.setAccessible(true);

            Subscription<E> subscription =
                    ama -> {
                        try {
                            method.invoke(object, ama);
                        } catch (IllegalAccessException
                                | IllegalArgumentException
                                | InvocationTargetException illegalaccessexception) {
                            ReflectionErrorHelper.rethrowReflectiveException(
                                    illegalaccessexception);
                        }
                    };

            return subscription;
        }
    },
    METHOD_LAMBDA_INVOCATION(SubscriptionMemberKind.METHOD) {
        @Override
        public <O, E extends Event> Subscription<E> method_c8dbd21017126aa55307(
                Class<E> oclass, O object, AnnotatedElement annotatedelement) {
            SubscriptionMemberKind.method_b417d7500ec59d69e373();

            Method method = (Method) annotatedelement;
            method.setAccessible(true);

            try {
                Lookup lookup = subscriptionLookup.in(object.getClass());
                MethodHandle methodhandle = null;
                int modifiers = method.getModifiers();
                if (Modifier.isPrivate(modifiers)) {
                    if (hasTrustedLookup) {
                        methodhandle =
                                Modifier.isStatic(modifiers)
                                        ? lookup.unreflect(method)
                                        : lookup.unreflectSpecial(method, object.getClass());
                    }
                } else if (Modifier.isProtected(modifiers)) {
                    if (hasTrustedLookup) {
                        methodhandle = lookup.unreflect(method);
                    }
                } else if (Modifier.isPublic(modifiers)) {
                    methodhandle = lookup.unreflect(method);
                } else {
                    methodhandle = lookup.unreflect(method);
                }

                if (methodhandle == null) {
                    throw new RuntimeException(
                            "Unable to do lookup on method \"" + method.getName() + "\".");
                }

                MethodType methodtype1 = MethodType.methodType(void.class, oclass);
                if (Modifier.isStatic(modifiers)) {
                    MethodType methodtype = MethodType.methodType(Subscription.class);
                    Subscription subscription =
                            (Subscription)
                                    LambdaMetafactory.metafactory(
                                                    lookup,
                                                    "accept",
                                                    methodtype,
                                                    methodtype1.erase(),
                                                    methodhandle,
                                                    methodtype1)
                                            .dynamicInvoker()
                                            .invoke();

                    return subscription;
                }

                MethodType methodtype2 =
                        MethodType.methodType(Subscription.class, object.getClass());
                return (Subscription)
                        LambdaMetafactory.metafactory(
                                        lookup,
                                        "accept",
                                        methodtype2,
                                        methodtype1.erase(),
                                        methodhandle,
                                        methodtype1)
                                .dynamicInvoker()
                                .invoke((Object) object);
            } catch (Throwable throwable) {
                ReflectionErrorHelper.rethrowThrowable(throwable);
                return null;
            }
        }
    },
    FIELD_REFLECTIVE_INVOCATION(SubscriptionMemberKind.FIELD) {
        @Override
        public <O, E extends Event> Subscription<E> method_c8dbd21017126aa55307(
                Class<E> oclass, O object, AnnotatedElement annotatedelement) {
            Field field = (Field) annotatedelement;
            field.setAccessible(true);

            try {
                return (Subscription<E>) field.get(object);
            } catch (IllegalArgumentException | IllegalAccessException illegalargumentexception) {
                ReflectionErrorHelper.rethrowReflectiveException(illegalargumentexception);
                return null;
            }
        }
    },
    FIELD_HANDLE_INVOCATION(SubscriptionMemberKind.FIELD) {
        @Override
        public <O, E extends Event> Subscription<E> method_c8dbd21017126aa55307(
                Class<E> oclass, O object, AnnotatedElement annotatedelement) {
            Field field = (Field) annotatedelement;

            try {
                MethodHandle methodhandle = subscriptionLookup.unreflectGetter(field);
                return (Subscription<E>) methodhandle.invokeWithArguments(object);
            } catch (IllegalArgumentException | IllegalAccessException illegalargumentexception) {
                ReflectionErrorHelper.rethrowReflectiveException(illegalargumentexception);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return null;
        }
    };
    private static SubscriptionInvocationStrategy[] cay;

    protected static Lookup subscriptionLookup;
    protected static boolean hasTrustedLookup;
    private SubscriptionMemberKind memberKind;

    public SubscriptionMemberKind getMemberKind() {
        return this.memberKind;
    }

    public <O, E extends Event> Subscription<E> method_c8dbd21017126aa55307(
            Class<E> oclass, O o, AnnotatedElement annotatedelement) {
        throw new UnsupportedOperationException();
    }

    static {
        cay =
                new SubscriptionInvocationStrategy[] {
                    remix.event.subscription.SubscriptionInvocationStrategy
                            .METHOD_REFLECTIVE_INVOCATION,
                    remix.event.subscription.SubscriptionInvocationStrategy
                            .METHOD_LAMBDA_INVOCATION,
                    remix.event.subscription.SubscriptionInvocationStrategy
                            .FIELD_REFLECTIVE_INVOCATION,
                    remix.event.subscription.SubscriptionInvocationStrategy.FIELD_HANDLE_INVOCATION
                };
        Lookup lookup;
        byte b0;
        try {
            Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
            field.setAccessible(true);
            lookup = (Lookup) field.get(null);
            b0 = 1;
        } catch (ReflectiveOperationException reflectiveoperationexception) {
            lookup = MethodHandles.lookup();
            b0 = 0;
        }
        remix.event.subscription.SubscriptionInvocationStrategy.subscriptionLookup = lookup;
        remix.event.subscription.SubscriptionInvocationStrategy.hasTrustedLookup =
                remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    private SubscriptionInvocationStrategy(remix.event.subscription.SubscriptionMemberKind p0) {
        this.memberKind = p0;
    }

    private static SubscriptionInvocationStrategy[] __recovery_values171() {
        return cay.clone();
    }
}
