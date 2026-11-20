package remix.util.collection;

import java.util.ArrayList;
import java.util.Iterator;

public final class FilteredFluentList extends remix.util.collection.FluentListWrapper
        implements remix.util.collection.DelegatingFluentWrapper {
    private java.util.List vl;
    private static int mu;
    private java.util.function.Predicate predicate;
    private java.util.List wE;
    private remix.util.collection.FluentListWrapper fluentListWrapper;

    public static void a(int i) {
        mu = i;
    }

    private boolean method_921ae872b196174d3161(Object object) {
        return this.predicate != null
                ? (this.predicate.test((java.lang.Object) object)
                        ? (this.vl != null ? this.vl.add((java.lang.Object) object) : true)
                        : (this.wE != null ? !this.wE.add((java.lang.Object) object) : false))
                : true;
    }

    public FluentCollectionWrapper aaU() {
        return this.getFluentListWrapper();
    }

    public static int eR() {
        int i = g();
        return i == 0 ? 42 : 0;
    }

    public static int g() {
        return mu;
    }

    public remix.util.collection.FluentListWrapper getFluentListWrapper() {
        return this.fluentListWrapper;
    }

    public FilteredFluentList(
            remix.util.collection.FluentListWrapper bQlValue,
            java.util.function.Predicate predicate,
            boolean flag) {
        super(bQlValue.asList());
        this.fluentListWrapper = bQlValue;
        this.predicate = predicate;
        if (flag) {
            this.vl = new ArrayList<>();
            this.wE = new ArrayList<>();
        }
    }

    public boolean method_c2e76015ecd049ebd5c3(int i, java.util.Collection collection) {
        int k = g();
        Iterator iterator = collection.iterator();
        int j = k;

        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (!this.method_921ae872b196174d3161((java.lang.Object) object)) {
                return false;
            }

            if (j != 0) {
                break;
            }
        }

        return super.method_c2e76015ecd049ebd5c3(i, collection);
    }

    static {
        if (eR() == 0) {
            a(3);
        }
    }

    public boolean method_08a79ea458256e796992(Object... aobject) {
        int k = eR();

        int j = 0;
        int i = k;

        while (j < aobject.length) {
            boolean flag = this.method_921ae872b196174d3161((java.lang.Object) aobject[j]);
            if (flag) {
                super.add(aobject[j]);
            }

            if (j == aobject.length) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public void method_86df15bc053e2526ca12(int i, Object object) {
        if (this.method_921ae872b196174d3161((java.lang.Object) object)) {
            super.method_86df15bc053e2526ca12(i, object);
        }
    }

    public java.util.List i() {
        return this.wE;
    }

    public java.util.function.Predicate getPredicate() {
        return this.predicate;
    }

    public java.util.List fN() {
        return this.vl;
    }

    public boolean addAll(java.util.Collection collection) {
        int j = g();
        Iterator iterator = collection.iterator();
        int i = j;

        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (!this.method_921ae872b196174d3161((java.lang.Object) object)) {
                return false;
            }

            if (i != 0) {
                break;
            }
        }

        return super.addAll(collection);
    }

    public boolean add(Object object) {
        return this.method_921ae872b196174d3161((java.lang.Object) object)
                ? super.add(object)
                : false;
    }
}
