package remix.event.discovery;

import net.minecraft.entity.Entity;

import remix.event.Event;
import remix.event.annotation.Subscribe;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class MethodSubscriberValidator implements remix.event.discovery.MemberValidator {
    private static String[] packedTextFragments;
    private static String b;

    @Override
    public boolean isValid(AnnotatedElement annotatedelement, Class oclass) {
        return this.isValidMethodSubscriberForEvent((Method) annotatedelement, oclass);
    }

    @Override
    public boolean hasSubscribeAnnotation(AnnotatedElement annotatedelement) {
        return this.isValidMethodSubscriber((Method) annotatedelement);
    }

    public boolean isValidMethodSubscriberForEvent(Method method, Class oclass) {
        String text = method_c60693f457aa70be7160();
        int i =
                this.isValidMethodSubscriber(method)
                                && method.getDeclaredAnnotation(Subscribe.class).mc() == oclass
                        ? 1
                        : 0;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(packedTextFragments[0]);
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(i);
    }

    public boolean isValidMethodSubscriber(Method method) {
        String text = method_c60693f457aa70be7160();
        Class[] aclass = method.getParameterTypes();
        String textValue = text;
        int i =
                MemberValidator.super.hasSubscribeAnnotation(method)
                                && aclass.length == 1
                                && Event.class.isAssignableFrom(aclass[0])
                        ? 1
                        : 0;
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(i);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.event.discovery.MethodSubscriberValidator.packedTextFragments[0] =
                new String("k0Dx4");
        remix.event.discovery.MethodSubscriberValidator.packedTextFragments[1] =
                new String("kF43Ub");
    }

    // endregion

    public static void b(String text) {
        b = text;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (method_c60693f457aa70be7160() == null) {
            b(packedTextFragments[1]);
        }
    }

    public static String method_c60693f457aa70be7160() {
        return b;
    }
}
