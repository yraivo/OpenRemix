package net.minecraft.util;

import net.minecraft.util.CartesianProductProductIterator;

import java.util.Collections;
import java.util.Iterator;

class CartesianProduct implements Iterable {
    private Class clazz;
    private Iterable[] iterables;

    @Override
    public Iterator iterator() {
        return (Iterator)
                (this.iterables.length <= 0
                        ? Collections.singletonList(
                                        Cartesian.method_13a08cbfc27389957edf(
                                                this.clazz, 0))
                                .iterator()
                        : new CartesianProductProductIterator(this.clazz, this.iterables, null));
    }

    private CartesianProduct(Class oclass, Iterable[] aiterable) {
        this.clazz = oclass;
        this.iterables = aiterable;
    }

    CartesianProduct(Class oclass, Iterable[] aiterable, Object object) {
        this(oclass, aiterable);
    }
}
