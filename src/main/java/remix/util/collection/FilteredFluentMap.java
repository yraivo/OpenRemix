package remix.util.collection;

import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class FilteredFluentMap extends remix.util.collection.FluentMapWrapper
        implements DelegatingFluentWrapper {
    private java.util.function.Predicate bQn;
    private remix.util.collection.FluentMapWrapper fluentMapWrapper;
    private static String[] packedTextFragments;
    private java.util.function.Predicate bQk;
    private java.util.Map dP;
    private static String te;
    private java.util.Map dT;

    public FilteredFluentMap(
            remix.util.collection.FluentMapWrapper bQoValue,
            java.util.function.Predicate predicate,
            java.util.function.Predicate predicate1,
            boolean flag) {
        super((Map) bQoValue.Qu());
        String text = d();
        this.fluentMapWrapper = bQoValue;
        String textValue = text;
        this.bQk = predicate;
        this.bQn = predicate1;
        if (flag) {
            this.dP = new HashMap<>();
            this.dT = new HashMap<>();
        }

        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    public static String d() {
        return te;
    }

    public java.util.function.Predicate abb() {
        return this.bQk;
    }

    public static void b(String text) {
        te = text;
    }

    public Object putEntry(Object object, Object object1) {
        return (java.lang.Object)
                (!this.method_3ce14c15268e9e02aa47(
                                (java.lang.Object) object, (java.lang.Object) object1)
                        ? null
                        : super.putEntry(object, object1));
    }

    // region String decoding support

    // endregion

    public remix.util.collection.FluentMapWrapper getFluentMapWrapper() {
        return this.fluentMapWrapper;
    }

    private boolean method_3ce14c15268e9e02aa47(Object object, Object object1) {
        byte b0 =
                remix.compat.compiler.JvmBooleanSlots.toByte(
                        this.bQn != null ? this.bQn.test((java.lang.Object) object1) : true);
        byte b1 =
                remix.compat.compiler.JvmBooleanSlots.toByte(
                        this.bQk != null ? this.bQk.test((java.lang.Object) object) : true);
        if (b1 != 0 && b0 != 0) {
            if (this.dP != null) {
                this.dP.put((java.lang.Object) object, (java.lang.Object) object1);
            }

            return true;
        } else {
            if (this.dT != null) {
                this.dT.put((java.lang.Object) object, (java.lang.Object) object1);
            }

            return false;
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.util.collection.FilteredFluentMap.packedTextFragments[0] = new String("XEPO9");
        remix.util.collection.FilteredFluentMap.packedTextFragments[1] = new String("wVwHp");
    }

    // endregion

    public void method_686ad5ab49b2999994cd(java.util.Map map) {
        String text = d();
        Iterator iterator = map.entrySet().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (!this.method_3ce14c15268e9e02aa47(
                    (java.lang.Object) entry.getKey(), (java.lang.Object) entry.getValue())) {
                return;
            }

            if (textValue == null) {
                break;
            }
        }

        super.method_686ad5ab49b2999994cd(map);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(packedTextFragments[0]);
        }
    }

    public FluentCollectionWrapper aaU() {
        return this.getFluentMapWrapper();
    }

    public java.util.function.Predicate abd() {
        return this.bQn;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (d() == null) {
            b(packedTextFragments[1]);
        }
    }
}
