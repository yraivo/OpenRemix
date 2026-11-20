package remix.event;

import remix.event.Event;

public interface EventFilter<E extends Event> {
    boolean accepts(EventSubscription<?, E> ams, E e);
}
