package remix.compat.legacy.security;

import net.minecraft.entity.Entity;

public abstract class AbstractNumberArrayPool<T extends Number> extends IndexedArrayPool<T> {
    protected AbstractNumberArrayPool(int i, int[] aint, T[] anumber) {
        this(i, aint, (T[]) anumber, -1);
    }

    protected AbstractNumberArrayPool(int i, int[] aint, T[] anumber, int j) {
        this(i, aint, anumber, j, IndexedArrayPool.method_650532ba484532b3efb0());
    }

    private AbstractNumberArrayPool(
            int i, int[] aint, T[] anumber, int j, int[] initialFlowMarker) {
        super(i, aint, anumber, j);

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            IndexedArrayPool.b(new int[2]);
        }
    }
}
