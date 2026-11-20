package remix.compat.legacy.security;

public final class FloatIndexedArrayPool extends IndexedArrayPool<Float> {
    public FloatIndexedArrayPool() {
        super(
                2,
                new int[0],
                new java.lang.Float[] {1.17549435E-38F, 3.5163377E-38F, Float.NEGATIVE_INFINITY});
    }

    public Class<? extends SecurityCheckEntry> mc() {
        return FloatSecurityCheckEntry.class;
    }
}
