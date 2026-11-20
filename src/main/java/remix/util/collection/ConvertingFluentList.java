package remix.util.collection;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ConvertingFluentList extends FluentListWrapper
        implements DelegatingFluentWrapper {

    private java.util.function.Function bQh;
    private FluentListWrapper fluentListWrapper;
    private java.util.function.Function bQj;

    public FluentCollectionWrapper aaU() {
        return this.getFluentListWrapper();
    }

    public java.util.List i() {
        List list = new ArrayList();
        this.asList().stream()
                .forEach(object ->
                        list.add(this.method_5cefbecbf1d19a907093((java.lang.Object) object)));
        return list;
    }


    public boolean method_25263c17d736b3399083(int i, java.util.Collection collection) {
        int k = FilteredFluentList.g();
        List list = new ArrayList();
        int j = k;

        try {
            collection.stream()
                    .forEach(object ->
                            list.add(this.method_f0b8fe7eb8ebef089956((java.lang.Object) object)));
            boolean flag = super.method_c2e76015ecd049ebd5c3(i, list);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                FilteredFluentList.a(++j);
            }

            return flag;
        } catch (NullPointerException nullpointerexception) {
            throw method_339a46a0855ea6bd2c63(nullpointerexception);
        }
    }

    public ConvertingFluentList(
            FluentListWrapper bQiValue,
            java.util.function.Function function,
            java.util.function.Function function1) {
        super(bQiValue.asList());
        int j = FilteredFluentList.eR();
        this.fluentListWrapper = bQiValue;
        this.bQj = function;
        int i = j;

        try {
            this.bQh = function1;
            if (i == 0) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }
        } catch (NullPointerException nullpointerexception) {
            throw method_339a46a0855ea6bd2c63(nullpointerexception);
        }
    }

    public void method_f695e2731659e37526d8(int i, Object object) {
        super.method_86df15bc053e2526ca12(
                i, this.method_f0b8fe7eb8ebef089956((java.lang.Object) object));
    }

    public boolean method_921ae872b196174d3161(Object object) {
        return this.add(
                this.method_f0b8fe7eb8ebef089956((java.lang.Object) object));
    }

    private Object method_f0b8fe7eb8ebef089956(Object object) {
        try {
            if (this.bQj == null) {
                throw new NullPointerException(
                        "The function that converts given element into another can't be null.");
            }
        } catch (NullPointerException nullpointerexception) {
            throw method_339a46a0855ea6bd2c63(nullpointerexception);
        }

        return this.bQj.apply((java.lang.Object) object);
    }

    public boolean method_4c79514dd25b6d15577a(java.util.Collection collection) {
        List list = new ArrayList();
        collection.stream()
                .forEach(object ->
                        list.add(this.method_f0b8fe7eb8ebef089956((java.lang.Object) object)));
        return super.addAll(list);
    }

    private Object method_5cefbecbf1d19a907093(Object object) {
        try {
            if (this.bQh == null) {
                throw new NullPointerException(
                        "The function that converts given element into another can't be null.");
            }
        } catch (NullPointerException nullpointerexception) {
            throw method_339a46a0855ea6bd2c63(nullpointerexception);
        }

        return this.bQh.apply((java.lang.Object) object);
    }

    public java.util.function.Function aaW() {
        return this.bQj;
    }

    public void method_bf3baeebc2bc1576e0a6(Object... aobject) {
        int j = FilteredFluentList.g();
        Object[] aobject1 = aobject;
        int i = j;

        for (Object object : aobject1) {
            super.add(
                    this.method_f0b8fe7eb8ebef089956((java.lang.Object) object));
            if (i != 0) {
                break;
            }
        }
    }

    public java.util.function.Function bw() {
        return this.bQj;
    }

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public FluentListWrapper getFluentListWrapper() {
        return this.fluentListWrapper;
    }
}
