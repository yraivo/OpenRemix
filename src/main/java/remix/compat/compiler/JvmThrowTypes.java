package remix.compat.compiler;

/** Rethrows the exact checked failure using ordinary Java generic exception inference. */
public final class JvmThrowTypes {
    private JvmThrowTypes() {}

    public static RuntimeException unchecked(Throwable failure) {
        return JvmThrowTypes.<RuntimeException>rethrow(failure);
    }

    public static RuntimeException flow(Throwable failure) {
        return JvmThrowTypes.<RuntimeException>rethrow(failure);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> RuntimeException rethrow(Throwable failure) throws E {
        throw (E) failure;
    }
}
