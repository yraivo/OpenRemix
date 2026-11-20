package remix.compat.legacy.security;

public final class IntegerIndexedArrayPool extends IndexedArrayPool<Integer> {
    public IntegerIndexedArrayPool() {
        super(0, new int[0], new java.lang.Integer[] {3, 147, 124890});
    }

    public Class<? extends SecurityCheckEntry> mc() {
        return IntegerSecurityCheckEntry.class;
    }
}
