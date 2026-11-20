package remix.event;

import remix.event.Event;

import java.lang.reflect.AnnotatedElement;

public interface EventBus {
    static <A extends AnnotatedElement> SubscriberSetupBuilder<A> builder() {
        return new SubscriberSetupBuilder<>();
    }

    <E extends Event> E postWithoutFilters(E e);

    void registerSubscriptions(DefaultEventSubscription... amq);

    void unregisterForEvent(Object object, Class<? extends Event> oclass);

    <E extends Event> E post(E e);

    void registerForEvent(Object object, Class<? extends Event> oclass);

    void register(Object object);

    void unregister(Object object);
}
