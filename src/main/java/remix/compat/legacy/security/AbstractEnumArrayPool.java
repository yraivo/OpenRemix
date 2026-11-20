package remix.compat.legacy.security;

public abstract class AbstractEnumArrayPool<T extends Enum>
        extends IndexedArrayPool<T> {
    protected AbstractEnumArrayPool(int i, int[] aint, T[] aenum, int j) {
        super(i, aint, aenum, j);
    }

    protected AbstractEnumArrayPool(int i, int[] aint, T[] aenum) {
        this(i, aint, (T[]) aenum, -1);
    }

    protected AbstractEnumArrayPool(int i, int[] aint, T oenum) {
        this(i, aint, (T[]) oenum.getDeclaringClass().getEnumConstants());
    }
}
