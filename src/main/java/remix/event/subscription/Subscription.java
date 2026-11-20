package remix.event.subscription;

import remix.event.Event;

import java.util.function.Consumer;

public interface Subscription<E extends Event> extends Consumer<E> {}
