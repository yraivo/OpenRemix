package remix.compat.invocation;

import remix.compat.invocation.MethodIndirectionMarker;

import java.util.function.Function;

@FunctionalInterface
public interface MethodFunction<A, R> extends Function<A, R>, MethodIndirectionMarker {
    @Override
    R apply(A a);
}
