package remix.event;

import net.minecraft.entity.Entity;
import remix.event.Event;
import remix.event.discovery.MemberDiscoveryStrategy;
import remix.event.discovery.MemberValidator;

import remix.event.subscription.SubscriptionInvocationStrategy;

import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultEventBus<A extends AnnotatedElement> implements EventBus {
    private MemberValidator memberValidator;

    private MemberDiscoveryStrategy discoveryStrategy;

    private Map<Class<? extends Event>, List<EventSubscription<?, ?>>> subscriptionsByEvent;
    private SubscriptionInvocationStrategy invocationStrategy;
    private static String[] packedTextFragments;

    @Override
    public void register(Object object) {
        Objects.requireNonNull(object, "The object must not be null");
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        AnnotatedElement[] aannotatedelement =
                this.discoveryStrategy.discoverMembers(object.getClass());

        for (AnnotatedElement annotatedelement : aannotatedelement) {
            if (this.memberValidator.hasSubscribeAnnotation((A) annotatedelement)) {
                DefaultEventSubscription amq = EventSubscription.fromAnnotatedMember(object, annotatedelement, this.invocationStrategy);
                this.registerSubscription(amq);
            }

            if (legacyDispatchMarkerSet) {
                break;
            }
        }
    }

    private Optional<List<EventSubscription<?, ?>>> findSubscriptionsForEvent(Class<? extends Event> oclass) {
        return Optional.ofNullable(this.subscriptionsByEvent.get(oclass));
    }

    @Override
    public void unregisterForEvent(Object object, Class<? extends Event> oclass) {
        Objects.requireNonNull(object, "The object must not be null");
        Objects.requireNonNull(oclass, "The class of the event can't be null");
        if (this.getSubscriptionsByEvent().containsKey(oclass)) {
            ((java.util.List) this.getSubscriptionsByEvent().get(oclass))
                    .removeIf(
                            ((java.util.function.Predicate<remix.event.EventSubscription>)
                                    (ams -> ams.getSubscriber().equals(object))));
        }
    }

    @Override
    public void unregister(Object object) {
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        Objects.requireNonNull(object, "The object must not be null");
        Iterator iterator = this.getSubscriptionsByEvent().keySet().iterator();

        while (iterator.hasNext()) {
            Class oclass = (Class) iterator.next();
            this.findSubscriptionsForEvent(oclass)
                    .ifPresent(
                            ((java.util.function.Consumer<java.util.List>)
                                    (list ->
                                            list.removeIf(
                                                    ((java.util.function.Predicate<remix.event.EventSubscription>)
                                                            (ams ->
                                                                    ((remix.event.EventSubscription) (ams))
                                                                            .getSubscriber()
                                                                            .equals(object)))))));
            if (legacyDispatchMarkerSet) {
                break;
            }
        }
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <E extends Event> E postWithoutFilters(E event) {
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        List<EventSubscription<?, ?>> subscriptions = this.getSubscriptionsByEvent().get(event.getClass());
        if (subscriptions != null) {
            for (int index = 0, size = subscriptions.size(); index < size; index++) {
                EventSubscription subscription = subscriptions.get(index);
                subscription.dispatch(event);
                if (legacyDispatchMarkerSet) {
                    break;
                }
            }
        }
        return event;
    }

    public <O> void registerSubscription(EventSubscription<O, ?> ams) {
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        Class oclass = ams.getEventType();
        List list =
                ((java.util.List)
                        this.findSubscriptionsForEvent(oclass)
                                .orElse(new CopyOnWriteArrayList<>()));
        list.add(ams);
        list.sort(Comparator.<remix.event.EventSubscription<?, ?>, Integer>comparing(remix.event.EventSubscription::getPriority));
        if (!this.getSubscriptionsByEvent().containsKey(oclass)) {
            this.getSubscriptionsByEvent().put(oclass, list);
        }

        if (legacyDispatchMarkerSet) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <E extends Event> E post(E event) {
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        List<EventSubscription<?, ?>> subscriptions = this.getSubscriptionsByEvent().get(event.getClass());
        if (subscriptions == null) {
            return event;
        }
        for (EventSubscription subscription : subscriptions) {
            boolean passesFilters = true;
            if (subscription.getFilters() != null) {
                for (EventFilter filter : subscription.getFilters()) {
                    boolean accepted = filter.accepts(subscription, event);
                    if (!legacyDispatchMarkerSet && !accepted) {
                        passesFilters = false;
                        break;
                    }
                    // Base 39 consults only the first filter and subscriber in this legacy state.
                    if (legacyDispatchMarkerSet) {
                        break;
                    }
                }
            }
            if (passesFilters) {
                subscription.dispatch(event);
            }
            if (legacyDispatchMarkerSet) {
                break;
            }
        }
        return event;
    }

    public Map<Class<? extends Event>, List<EventSubscription<?, ?>>> getSubscriptionsByEvent() {
        return this.subscriptionsByEvent;
    }

    @Override
    public void registerSubscriptions(DefaultEventSubscription... aamq) {
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        Objects.requireNonNull(aamq, "The object must not be null");
        if (aamq.length != 0) {
            int i = 0;
            int j = aamq.length;

            while (i < j) {
                DefaultEventSubscription amq = aamq[i];
                this.registerSubscription(amq);
                i++;
                if (legacyDispatchMarkerSet) {
                    break;
                }
            }
        }
    }

    @Override
    public void registerForEvent(Object object, Class<? extends Event> oclass) {
        Objects.requireNonNull(object, "The object must not be null");
        Objects.requireNonNull(oclass, "You forgot to specify the event");
        AnnotatedElement[] aannotatedelement =
                this.discoveryStrategy.discoverMembers(object.getClass());
        boolean legacyDispatchMarkerSet = SubscriberSetupBuilder.getLegacyDispatchMarker() != null;
        int i = 0;
        int j = aannotatedelement.length;

        while (i < j) {
            AnnotatedElement annotatedelement = aannotatedelement[i];
            if (this.memberValidator.isValid((A) annotatedelement, oclass)) {
                DefaultEventSubscription amq = EventSubscription.fromAnnotatedMember(object, annotatedelement, this.invocationStrategy);
                this.registerSubscription(amq);
            }

            i++;
            if (legacyDispatchMarkerSet) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            SubscriberSetupBuilder.setLegacyDispatchMarker(packedTextFragments[0]);
        }
    }

    // region Static initialization and string tables
    static {
        remix.event.DefaultEventBus.packedTextFragments = new String[3];
        remix.event.DefaultEventBus.packedTextFragments[0] = new String("mEB11b");
    }

    // endregion

    DefaultEventBus(
            Map<Class<? extends Event>, List<EventSubscription<?, ?>>> map,
            MemberDiscoveryStrategy amj,
            MemberValidator amm,
            SubscriptionInvocationStrategy cakValue) {
        this.subscriptionsByEvent = map;
        this.discoveryStrategy = amj;
        this.memberValidator = amm;
        this.invocationStrategy = cakValue;
    }
}
