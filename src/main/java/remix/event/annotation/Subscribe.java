package remix.event.annotation;

import remix.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    Class<? extends Event> mc();

    SubscriberPriority priority() default SubscriberPriority.NORMAL;
}
