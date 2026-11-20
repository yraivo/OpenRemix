package remix.compat.legacy.security;

public abstract class AbstractExceptionClassArrayPool<T extends Exception>
        extends IndexedArrayPool<Class<T>> {
    protected AbstractExceptionClassArrayPool(
            int p171_0, int[] p171_1, Class<T>[] classArray, int p171_3) {
        super(p171_0, p171_1, classArray, p171_3);
    }

    public AbstractExceptionClassArrayPool(int p171_0, int[] p171_1, Class<T>[] classArray) {
        this(p171_0, p171_1, classArray, -1);
    }
}
