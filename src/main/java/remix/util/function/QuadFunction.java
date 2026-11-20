package remix.util.function;

@FunctionalInterface
public interface QuadFunction<A, B, C, D, E> {
    E a(A a, B b, C c, D d);
}
