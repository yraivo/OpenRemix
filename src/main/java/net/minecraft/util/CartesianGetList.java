package net.minecraft.util;

import com.google.common.base.Function;

import java.util.Arrays;
import java.util.List;

class CartesianGetList implements Function {
    private CartesianGetList() {}

    public List apply(Object[] aobject) {
        return Arrays.asList(aobject);
    }

    CartesianGetList(Object object) {
        this();
    }

    public Object apply(Object p171_0) {
        return this.apply((java.lang.Object[]) p171_0);
    }
}
