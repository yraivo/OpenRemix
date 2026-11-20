package remix.compat.legacy.security;

import net.minecraft.entity.Entity;

public abstract class IndexedArrayPool<T> implements IndexedValuePool<T> {
    private int a;
    private int dD;
    private static int[] aAz;
    private int[] gy;
    private T[] bTc;

    public int[] xZ() {
        return this.gy;
    }

    public void a(int i) {
        this.dD = i;
    }

    public static void b(int[] aint) {
        aAz = aint;
    }

    public boolean e() {
        return this.gy != null && this.gy.length != 0;
    }

    public void b(int i) {
        int[] aint1 = method_650532ba484532b3efb0();
        this.dD += i;
        int[] aint = aint1;
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public int aW() {
        return this.a;
    }

    public T cy() {
        return this.abS()[this.dD];
    }

    public T[] abS() {
        return this.bTc;
    }

    protected IndexedArrayPool(int i, int[] aint, T[] aobject, int j) {
        this.a = i;
        this.gy = aint;
        this.bTc = (T[]) aobject;
        this.dD = j;
    }

    protected IndexedArrayPool(int i, int[] aint, T[] aobject) {
        this(i, aint, (T[]) aobject, -1);
    }

    static {
        if (method_650532ba484532b3efb0() != null) {
            b(new int[1]);
        }
    }

    public static int[] method_650532ba484532b3efb0() {
        return aAz;
    }

    public T bH(int i) {
        return this.abS()[i];
    }
}
