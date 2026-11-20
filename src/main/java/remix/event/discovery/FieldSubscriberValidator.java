package remix.event.discovery;

import remix.event.annotation.Subscribe;

import remix.event.subscription.Subscription;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

public class FieldSubscriberValidator implements remix.event.discovery.MemberValidator {
    public boolean isValidFieldSubscriberForEvent(Field field, Class oclass) {
        return this.isValidFieldSubscriber(field)
                && field.getDeclaredAnnotation(Subscribe.class).mc() == oclass;
    }

    @Override
    public boolean hasSubscribeAnnotation(AnnotatedElement annotatedElement) {
        return this.isValidFieldSubscriber((java.lang.reflect.Field) annotatedElement);
    }

    public boolean isValidFieldSubscriber(Field field) {
        return MemberValidator.super.hasSubscribeAnnotation(field)
                && Subscription.class.isAssignableFrom(field.getType());
    }

    @Override
    public boolean isValid(AnnotatedElement annotatedElement, Class type) {
        return this.isValidFieldSubscriberForEvent((java.lang.reflect.Field) annotatedElement, type);
    }
}
