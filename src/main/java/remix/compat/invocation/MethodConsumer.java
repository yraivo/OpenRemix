package remix.compat.invocation;

import remix.compat.invocation.MethodIndirectionMarker;

import java.util.function.Consumer;

@FunctionalInterface
public interface MethodConsumer<A> extends Consumer<A>, MethodIndirectionMarker {
    @Override
    void accept(A a);
}
