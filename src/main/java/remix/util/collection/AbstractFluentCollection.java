package remix.util.collection;

import remix.compat.legacy.invocation.CheckedGeneralException;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

public abstract class AbstractFluentCollection extends remix.util.collection.FluentCollectionWrapper {
    private static String d;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public java.util.Collection method_d00417afecd7dff1103d(java.util.Collection collection) {
        return (Collection<java.lang.Object>) this.method_69be51ad1433bd242905(collection);
    }

    public boolean d() {
        try {
            if (this.Qu() == null) {
                return true;
            }
        } catch (FluentNullTargetException fluentNullTargetException) {
            throw method_2664b1486bea656117a0(fluentNullTargetException);
        }

        return false;
    }

    public boolean isEmpty() {
        return ((Collection) this.Qu()).isEmpty();
    }

    public AbstractFluentCollection(java.util.Collection collection) {
        super(collection);
    }

    public boolean method_b5045553d9ac2f2dc7f5(Object object) {
        return ((Collection) this.Qu()).remove(object);
    }

    public Object[] aaQ() {
        return ((Collection) this.Qu()).toArray();
    }

    public Object[] method_8ca1e132b58cd35591d7(Object[] aobject) {
        return (java.lang.Object[]) ((Collection) this.Qu()).toArray(aobject);
    }

    public boolean method_08a79ea458256e796992(Object... aobject) {
        return Collections.addAll((Collection<? super Object>) this.Qu(), aobject);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (cJ() != null) {
            b(packedTextFragments[0]);
        }
    }

    public boolean method_d050c6b5f0f514c931be(java.util.Collection collection) {
        return ((Collection) this.Qu()).retainAll(collection);
    }

    public boolean add(Object object) {
        return ((Collection) this.Qu()).add(object);
    }

    public void cQ() {
        ((Collection) this.Qu()).clear();
    }

    public boolean method_7e2cecf966bf579e454d(java.util.Collection collection) {
        return ((Collection) this.Qu()).removeAll(collection);
    }

    public void l() throws FluentNullTargetException {}

    public java.util.Iterator aaR() {
        return ((Collection) this.Qu()).iterator();
    }

    public java.util.Collection Bz() {
        return Collections.unmodifiableCollection(
                (Collection<? extends java.lang.Object>) this.Qu());
    }

    public static void b(String text) {
        d = text;
    }

    public boolean addAll(java.util.Collection collection) {
        return ((Collection) this.Qu()).addAll(collection);
    }

    private static FluentNullTargetException method_2664b1486bea656117a0(
            FluentNullTargetException fluentNullTargetException) {
        return fluentNullTargetException;
    }

    public static String cJ() {
        return d;
    }

    public void cj() throws CheckedGeneralException {}

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.util.collection.AbstractFluentCollection.packedTextFragments[0] = new String("DGbiT");
    }

    // endregion

    public int size() {
        return ((Collection) this.Qu()).size();
    }

    public java.util.Optional method_6222bdd9a137b40ece24(Class oclass) {
        String text = cJ();
        Iterator iterator = ((Collection) this.Qu()).iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Object object = iterator.next();

            try {
                if (object.getClass() == oclass) {
                    return Optional.of((java.lang.Object) object);
                }
            } catch (FluentNullTargetException fluentNullTargetException) {
                throw method_2664b1486bea656117a0(fluentNullTargetException);
            }

            if (textValue != null) {
                break;
            }
        }

        return Optional.empty();
    }

    public boolean method_e3bab87cffa5a1cc5564(java.util.Collection collection) {
        return ((Collection) this.Qu()).containsAll(collection);
    }
}
