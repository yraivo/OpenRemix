package net.minecraft.util;

import com.google.common.collect.Iterators;

import java.util.Iterator;

class ClassInheratanceMultiMapGetByClassIterable implements Iterable {
    ClassInheratanceMultiMap aTQ;
    Class a;

    ClassInheratanceMultiMapGetByClassIterable(ClassInheratanceMultiMap aTQValue, Class oclass) {
        this.aTQ = aTQValue;
        this.a = oclass;
    }

    @Override
    public Iterator iterator() {
        Iterator iterator =
                ClassInheratanceMultiMap.access$000(this.aTQ)
                        .get(this.aTQ.method_c86c213b196a2b25c1a6(this.a, true))
                        .iterator();
        return Iterators.filter(iterator, this.a);
    }
}
