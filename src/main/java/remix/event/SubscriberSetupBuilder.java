package remix.event;

import remix.event.Event;
import remix.event.discovery.MemberDiscoveryStrategy;
import remix.event.discovery.MemberValidator;

import remix.event.subscription.SubscriptionInvocationStrategy;

import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriberSetupBuilder<A extends AnnotatedElement> {
    private MemberDiscoveryStrategy discoveryStrategy;
    private MemberValidator memberValidator;
    private final Map<Class<? extends Event>, List<EventSubscription<?, ?>>> subscriptionsByEvent = new HashMap<>();
    private SubscriptionInvocationStrategy subscriptionInvocationStrategy;
    private static String[] packedTextFragments;
    private static String legacyDispatchMarker;

    public SubscriberSetupBuilder<A> discoveryStrategy(MemberDiscoveryStrategy amj) {
        this.discoveryStrategy = amj;
        return this;
    }

    public SubscriberSetupBuilder<A> invocationStrategy(SubscriptionInvocationStrategy cakValue) {
        this.subscriptionInvocationStrategy = cakValue;
        return this;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.event.SubscriberSetupBuilder.packedTextFragments[0] = new String("qUK2S");
    }

    // endregion

    public static void setLegacyDispatchMarker(String text) {
        legacyDispatchMarker = text;
    }

    public DefaultEventBus<A> build() {
        return new DefaultEventBus<>(this.subscriptionsByEvent, this.discoveryStrategy, this.memberValidator, this.subscriptionInvocationStrategy);
    }

    // region String decoding support

    // endregion

    public SubscriberSetupBuilder<A> memberValidator(MemberValidator amm) {
        this.memberValidator = amm;
        return this;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (getLegacyDispatchMarker() != null) {
            setLegacyDispatchMarker(packedTextFragments[0]);
        }
    }

    public static String getLegacyDispatchMarker() {
        return legacyDispatchMarker;
    }
}
