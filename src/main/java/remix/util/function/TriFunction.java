package remix.util.function;

@FunctionalInterface
public interface TriFunction<A, B, C, D> {
    D a(A a, B b, C c);
}
