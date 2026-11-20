package remix.event;

import net.minecraft.entity.Entity;

import remix.event.annotation.FilterAnnotation;
import remix.event.annotation.Subscribe;
import remix.event.subscription.Subscription;
import remix.event.subscription.SubscriptionInvocationStrategy;

import java.lang.reflect.AnnotatedElement;

public final class DefaultEventSubscription<O, E extends Event> implements EventSubscription<O, E> {
    private Subscription<E> subscription;
    private O subscriber;
    private int priority;
    private Class<E> eventType;

    private EventFilter[] filters;

    private static String[] legacyFilterFlowMarker;

    @Override
    public int getPriority() {
        return this.priority;
    }

    // region String decoding support

    // endregion

    private EventFilter[] instantiateFilters(Class<? extends EventFilter>[] aclass) {
        String[] astring = getLegacyFilterFlowMarker();

        try {
            if (false)
                throw (java.lang.InstantiationException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (aclass == null) {
                return null;
            }
        } catch (InstantiationException instantiationexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(instantiationexception1);
        }

        try {
            EventFilter[] aame = new EventFilter[aclass.length];
            int i = 0;

            while (i < aclass.length) {
                aame[i] = (EventFilter) aclass[i].newInstance();
                i++;
                if (astring != null) {
                    break;
                }
            }

            return aame;
        } catch (InstantiationException instantiationexception) {
            throw new RuntimeException(
                    "One or multiple of the specified filter classes can't be instantiated:\n"
                            + instantiationexception.getMessage());
        } catch (IllegalAccessException illegalaccessexception) {
            throw new RuntimeException(
                    "Access exception encountered creating filters for method/field in \""
                            + this.getSubscriber().toString()
                            + "\":\n"
                            + illegalaccessexception.getMessage());
        }
    }

    // region String decoding support

    // endregion

    @Override
    public Class<E> getEventType() {
        return this.eventType;
    }

    DefaultEventSubscription(
            O object,
            AnnotatedElement annotatedelement,
            Subscribe amr,
            FilterAnnotation amf,
            SubscriptionInvocationStrategy subscriptionInvocationStrategy) {
        this(
                object,
                annotatedelement,
                amr,
                amf,
                subscriptionInvocationStrategy,
                getLegacyFilterFlowMarker());
    }

    private DefaultEventSubscription(
            O object,
            AnnotatedElement annotatedelement,
            Subscribe amr,
            FilterAnnotation amf,
            SubscriptionInvocationStrategy subscriptionInvocationStrategy,
            String[] initialFlowMarker) {
        this(
                (O) object,
                (Class<E>) amr.mc(),
                (Subscription<E>)
                        (Subscription<?>)
                                subscriptionInvocationStrategy.method_c8dbd21017126aa55307(
                                        amr.mc(), object, annotatedelement),
                amr.priority().getLevel(),
                amf != null ? amf.value() : null);

        String[] astring = initialFlowMarker;

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyFilterFlowMarker(new String[4]);
        }
    }

    public static void setLegacyFilterFlowMarker(String[] astring) {
        remix.event.DefaultEventSubscription.legacyFilterFlowMarker = astring;
    }

    DefaultEventSubscription(
            O object,
            Class<E> oclass,
            Subscription<E> subscription,
            int i,
            Class<? extends EventFilter>... aclass) {
        this.subscriber = (O) object;
        String[] astring1 = getLegacyFilterFlowMarker();
        this.eventType = oclass;
        this.subscription = subscription;
        this.priority = i;
        String[] astring = astring1;

        try {
            this.filters = this.instantiateFilters(aclass);
            if (astring != null) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
            }
        } catch (RuntimeException runtimeexception) {
            throw (runtimeexception);
        }
    }

    @Override
    public void dispatch(E ama) {
        this.subscription.accept(ama);
    }

    @Override
    public O getSubscriber() {
        return this.subscriber;
    }

    @Override
    public Subscription<E> getSubscription() {
        return this.subscription;
    }

    @Override
    public EventFilter[] getFilters() {
        return this.filters;
    }

    static {
        setLegacyFilterFlowMarker(null);
    }

    public static String[] getLegacyFilterFlowMarker() {
        return remix.event.DefaultEventSubscription.legacyFilterFlowMarker;
    }
}
