package remix.event;

import remix.event.Event;
import remix.event.annotation.FilterAnnotation;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;

import remix.event.subscription.Subscription;
import remix.event.subscription.SubscriptionInvocationStrategy;

import java.lang.reflect.AnnotatedElement;

public interface EventSubscription<O, E extends Event> {
    SubscriberPriority DEFAULT_PRIORITY = SubscriberPriority.NORMAL;

    static <O, E extends Event> DefaultEventSubscription<O, E> withPriority(
            O object,
            Class<E> oclass,
            Subscription<E> subscription,
            SubscriberPriority subscriberPriority,
            Class<? extends EventFilter<E>>... aclass) {
        return new DefaultEventSubscription<>(
                (O) object,
                oclass,
                subscription,
                subscriberPriority.getLevel(),
                aclass);
    }

    static <O, E extends Event> DefaultEventSubscription<O, E> withDefaultPriority(
            O object,
            Class<E> oclass,
            Subscription<E> subscription,
            Class<? extends EventFilter<E>>... aclass) {
        return withPriority((O) object, oclass, subscription, remix.event.EventSubscription.DEFAULT_PRIORITY, aclass);
    }

    static <O> DefaultEventSubscription<O, ?> fromAnnotatedMember(
            O object,
            AnnotatedElement annotatedelement,
            SubscriptionInvocationStrategy subscriptionInvocationStrategy) {
        return new DefaultEventSubscription<>(
                (O) object,
                annotatedelement,
                annotatedelement.getDeclaredAnnotation(Subscribe.class),
                annotatedelement.getDeclaredAnnotation(FilterAnnotation.class),
                subscriptionInvocationStrategy);
    }

    void dispatch(E e);

    Subscription<E> getSubscription();

    int getPriority();

    O getSubscriber();

    EventFilter[] getFilters();

    Class<? extends E> getEventType();
}
