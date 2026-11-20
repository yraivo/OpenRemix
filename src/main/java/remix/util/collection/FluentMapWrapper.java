package remix.util.collection;

import remix.compat.legacy.invocation.CheckedGeneralException;

import net.minecraft.entity.Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class FluentMapWrapper extends FluentCollectionWrapper {
    private static String[] packedTextFragments;

    public java.util.Map lN() {
        return Collections.unmodifiableMap(
                (Map<? extends java.lang.Object, ? extends java.lang.Object>) this.Qu());
    }

    // region String decoding support

    // endregion

    public java.util.Collection aaZ() {
        return Collections.unmodifiableCollection(((Map) this.Qu()).values());
    }

    public FluentMapWrapper() {
        this(new HashMap<>());
    }

    public boolean jvm171_f_62df22fc486c10() {
        try {
            if (this.Qu() == null) {
                return true;
            }
        } catch (FluentNullTargetException fluentNullTargetException) {
            throw method_2664b1486bea656117a0(fluentNullTargetException);
        }

        return false;
    }

    public FluentMapWrapper(java.util.Map map) {
        super(map);
    }

    public java.util.Optional findValueByKey(Object object) {
        return Optional.ofNullable((java.lang.Object) ((Map) this.Qu()).get(object));
    }

    public boolean method_2ed57ee43017dc0bf69e(Object object, Object object1) {
        return ((Map) this.Qu()).remove(object, object1);
    }

    public Object method_f0b8fe7eb8ebef089956(Object object) {
        return (java.lang.Object) ((Map) this.Qu()).remove(object);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.util.collection.FluentMapWrapper.packedTextFragments[0] = new String("j2aObb");
    }

    // endregion

    public Object getEntry(Object object) {
        return (java.lang.Object) ((Map) this.Qu()).get(object);
    }

    public void cj() throws CheckedGeneralException {
        this.method_784e95624d6d70fe7f50();
    }

    public Object putEntry(Object object, Object object1) {
        return (java.lang.Object) ((Map) this.Qu()).put(object, object1);
    }

    public java.util.Optional findValueOfExactClass(Class oclass) {
        String text = AbstractFluentCollection.cJ();
        Iterator iterator = this.aaZ().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Object object = iterator.next();

            try {
                if (object.getClass() == oclass) {
                    return Optional.of(object);
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

    public void method_686ad5ab49b2999994cd(java.util.Map map) {
        ((Map) this.Qu()).putAll(map);
    }

    public void cQ() {
        this.method_69be51ad1433bd242905(null);
    }

    private static FluentNullTargetException method_2664b1486bea656117a0(
            FluentNullTargetException fluentNullTargetException) {
        return fluentNullTargetException;
    }

    public boolean method_09ac9cf540a08e2a9d18(Object object) {
        return ((Map) this.Qu()).containsValue(object);
    }

    public void fZ() {
        ((Map) this.Qu()).clear();
    }

    public void l() throws FluentNullTargetException {
        this.cQ();
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public boolean method_6a46953d5f41322f2439(Object object) {
        return ((Map) this.Qu()).containsKey(object);
    }

    public void method_6df7a4bae4aa2c8ba312(
            java.util.function.Function function, Object... aobject) {
        String text = AbstractFluentCollection.cJ();
        Object[] aobject1 = aobject;
        int length = aobject1.length;
        int j = 0;
        String textValue = text;

        while (j < length) {
            Object object = aobject1[j];
            this.putEntry(
                    (java.lang.Object) function.apply(object), (java.lang.Object) object);
            j++;
            if (textValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
                break;
            }
        }
    }

    public java.util.Set BC() {
        return Collections.unmodifiableSet(((Map) this.Qu()).keySet());
    }

    public void method_784e95624d6d70fe7f50() {
        String text = AbstractFluentCollection.cJ();

        FluentMapWrapper fluentMapWrapper;
        Map map;
        label25:
        {
            try {
                fluentMapWrapper = this;
                if (!this.jvm171_f_62df22fc486c10()) {
                    map = (Map) this.Qu();
                    break label25;
                }
            } catch (FluentNullTargetException fluentNullTargetException) {
                throw method_2664b1486bea656117a0(fluentNullTargetException);
            }

            map = new HashMap();
        }

        try {
            fluentMapWrapper.method_69be51ad1433bd242905(map);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                AbstractFluentCollection.b(packedTextFragments[0]);
            }
        } catch (FluentNullTargetException fluentNullTargetExceptionValue) {
            throw method_2664b1486bea656117a0(fluentNullTargetExceptionValue);
        }
    }
}
