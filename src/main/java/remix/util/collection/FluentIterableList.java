package remix.util.collection;

import net.minecraft.entity.Entity;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class FluentIterableList<T> implements Iterable<T> {
    private FluentListWrapper fluentListWrapper;
    private static boolean b;

    public void a(T object) {
        this.fluentListWrapper.add(object);
    }

    public boolean method_921ae872b196174d3161(T object) {
        return this.fluentListWrapper.asList().contains(object);
    }

    public void method_8d9b536bcdc9991c48fb() {
        this.fluentListWrapper.cQ();
    }

    public FluentIterableList(T[] aobject) {
        this.fluentListWrapper = new FluentListWrapper(Arrays.asList(aobject));
    }

    public static boolean f() {
        boolean flag = e();
        return !flag;
    }

    static {
        e();
    }

    public T method_f2d74290405e3deed647(int i) {
        return (T) this.fluentListWrapper.getElementAt(i);
    }

    public static boolean e() {
        return b;
    }

    public List<T> eN() {
        return this.fluentListWrapper.asList();
    }

    public FluentIterableList(List<T> list) {
        this.fluentListWrapper = new FluentListWrapper(list);
    }

    public boolean d() {
        return this.method_7eec03da329a017ec143() == 0;
    }

    public int method_7eec03da329a017ec143() {
        return this.eN().size();
    }

    public void method_f93cf818ee211f14c02a(T object) {
        this.fluentListWrapper.method_b5045553d9ac2f2dc7f5(object);
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    public FluentIterableList() {
        this.fluentListWrapper = new FluentListWrapper();
    }

    @Override
    public Iterator<T> iterator() {
        return new remix.util.collection.FluentListIterator(this);
    }

    public void method_bf3baeebc2bc1576e0a6(T[] aobject) {
        this.fluentListWrapper.cQ();
        boolean flag1 = f();
        this.fluentListWrapper.addAll(Arrays.asList(aobject));
        boolean flag = flag1;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_a8745b073fa8adfa1857(!flag);
        }
    }
}
