package net.minecraft.util;

import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

class InheritanceMapIterator extends AbstractIterator {
    ClassInheratanceMultiMap field_180196_b;
    Iterator field_180197_a;

    protected Object computeNext() {
        return !this.field_180197_a.hasNext() ? this.endOfData() : this.field_180197_a.next();
    }

    InheritanceMapIterator(ClassInheratanceMultiMap aTQValue, Iterator aTRValue) {
        this.field_180196_b = aTQValue;
        this.field_180197_a = aTRValue;
    }
}
