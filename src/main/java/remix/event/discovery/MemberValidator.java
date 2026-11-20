package remix.event.discovery;

import remix.event.annotation.Subscribe;

public interface MemberValidator {
    default boolean hasSubscribeAnnotation(
            java.lang.reflect.AnnotatedElement annotatedelement) {
        return annotatedelement.isAnnotationPresent(Subscribe.class);
    }

    static MethodSubscriberValidator afL() {
        return new MethodSubscriberValidator();
    }

    static FieldSubscriberValidator afM() {
        return new FieldSubscriberValidator();
    }

    boolean isValid(java.lang.reflect.AnnotatedElement a, Class oclass);
}
