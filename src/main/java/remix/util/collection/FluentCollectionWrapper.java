package remix.util.collection;

import remix.compat.legacy.invocation.CheckedGeneralException;

public abstract class FluentCollectionWrapper {
    private DelegatingFluentWrapper delegatingFluentWrapper;
    private static int c;
    private Object byd;

    public DelegatingFluentWrapper method_94528c47bd2d0bb5911a(
            DelegatingFluentWrapper bQeValue) {
        this.delegatingFluentWrapper = bQeValue;
        return this.delegatingFluentWrapper;
    }

    public FluentCollectionWrapper(Object object) {
        this.method_69be51ad1433bd242905((java.lang.Object) object);
    }

    public static int f() {
        int i = cR();

        try {
            return i == 0 ? 3 : 0;
        } catch (FluentNullTargetException fluentNullTargetException) {
            throw method_11b8eb086ced7a22fd4e(fluentNullTargetException);
        }
    }

    public abstract void l() throws FluentNullTargetException;

    private static FluentNullTargetException method_11b8eb086ced7a22fd4e(
            FluentNullTargetException fluentNullTargetException) {
        return fluentNullTargetException;
    }

    public Object Qu() {
        return this.byd;
    }

    public Object method_69be51ad1433bd242905(Object object) {
        this.byd = (java.lang.Object) object;
        return this.byd;
    }

    public static void b(int i) {
        c = i;
    }

    static {
        if (cR() != 0) {
            b(72);
        }
    }

    public abstract void cj() throws CheckedGeneralException;

    public DelegatingFluentWrapper getDelegatingFluentWrapper() {
        return this.delegatingFluentWrapper;
    }

    public static int cR() {
        return c;
    }
}
