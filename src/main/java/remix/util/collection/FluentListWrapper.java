package remix.util.collection;

import remix.compat.legacy.invocation.CheckedGeneralException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FluentListWrapper extends remix.util.collection.AbstractFluentCollection {
    public Object Qu() {
        return this.asList();
    }

    public Object getElementAt(int i) {
        return this.asList().get(i);
    }

    public void method_8d9b536bcdc9991c48fb() {
        FluentListWrapper fluentListWrapper;
        List list;
        label16:
        {
            try {
                fluentListWrapper = this;
                if (!this.d()) {
                    list = this.asList();
                    break label16;
                }
            } catch (FluentNullTargetException fluentNullTargetException) {
                throw method_27edd3d6b67e06146640(fluentNullTargetException);
            }

            list = new ArrayList();
        }

        fluentListWrapper.method_d00417afecd7dff1103d(list);
    }

    public FluentListWrapper() {
        super(new ArrayList());
        this.method_b2565438db3319617bae(this.asList());
    }

    public java.util.ListIterator bE(int i) {
        return this.asList().listIterator(i);
    }

    public FluentListWrapper(java.util.List list) {
        super(list);
        this.method_b2565438db3319617bae(list);
    }

    public java.util.Optional bF(int i) {
        return Optional.ofNullable(this.asList().get(i));
    }

    public java.util.Optional method_07c4830733d69e4476db(Class oclass) {
        String text = AbstractFluentCollection.cJ();
        Iterator iterator = this.asList().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Object object = iterator.next();

            try {
                if (object.getClass() == oclass) {
                    return Optional.of(object);
                }
            } catch (FluentNullTargetException fluentNullTargetException) {
                throw method_27edd3d6b67e06146640(fluentNullTargetException);
            }

            if (textValue != null) {
                break;
            }
        }

        return Optional.empty();
    }

    public void l() throws FluentNullTargetException {
        this.method_784e95624d6d70fe7f50();
    }

    public int method_037a7bc4cfbbf65cfbc1(Object object) {
        return this.asList().lastIndexOf(object);
    }

    public void cj() throws CheckedGeneralException {
        this.method_8d9b536bcdc9991c48fb();
    }

    public java.util.List tZ() {
        return Collections.unmodifiableList(this.asList());
    }

    public java.util.List method_b2565438db3319617bae(java.util.List list) {
        return (List<java.lang.Object>) super.method_69be51ad1433bd242905(list);
    }

    public int method_070cd5e3c96ef51c328f(Object object) {
        return this.asList().indexOf(object);
    }

    private static FluentNullTargetException method_27edd3d6b67e06146640(
            FluentNullTargetException fluentNullTargetException) {
        return fluentNullTargetException;
    }

    public java.util.List asList() {
        return (List<java.lang.Object>) super.Qu();
    }

    public boolean method_c2e76015ecd049ebd5c3(int i, java.util.Collection collection) {
        return this.asList().addAll(i, collection);
    }

    public void method_86df15bc053e2526ca12(int i, Object object) {
        this.asList().add(i, (java.lang.Object) object);
    }

    public java.util.ListIterator aaT() {
        return this.asList().listIterator();
    }

    public Object method_f2d74290405e3deed647(int i) {
        return this.asList().remove(i);
    }

    public void method_784e95624d6d70fe7f50() {
        this.method_d00417afecd7dff1103d(null);
    }

    public Object method_3e977d424afd07bb7fda(int i, Object object) {
        return this.asList().set(i, (java.lang.Object) object);
    }
}
