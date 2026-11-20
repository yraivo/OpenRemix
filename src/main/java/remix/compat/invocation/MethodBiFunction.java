package remix.compat.invocation;

import remix.compat.invocation.MethodIndirectionMarker;

import java.util.function.BiFunction;

@FunctionalInterface
public interface MethodBiFunction<A, B, R> extends BiFunction<A, B, R>, MethodIndirectionMarker {
    @Override
    R apply(A a, B b);
}
