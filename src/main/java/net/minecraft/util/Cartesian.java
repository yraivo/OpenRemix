package net.minecraft.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class Cartesian {
    public static Iterable cartesianProduct(Iterable iterable) {
        return net.minecraft.util.Cartesian.arraysAsLists(
                net.minecraft.util.Cartesian.cartesianProduct(
                        java.lang.Object.class, iterable));
    }

    private static Object[] toArray(Class oclass, Iterable iterable) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;

        for (Object object : iterable) {
            arrayList.add(object);
            if (textValue == null) {
                break;
            }
        }

        return arrayList.toArray(createArray(oclass, arrayList.size()));
    }

    public static Object[] method_13a08cbfc27389957edf(Class oclass, int i) {
        return createArray(oclass, i);
    }

    private static Object[] createArray(Class type, int p171_1) {
        return (java.lang.Object[]) java.lang.reflect.Array.newInstance(type, p171_1);
    }

    private static Iterable arraysAsLists(Iterable iterable) {
        return com.google.common.collect.Iterables.transform(
                iterable, new net.minecraft.util.CartesianGetList(null));
    }

    public static Iterable cartesianProduct(Class type, Iterable iterable) {
        return new net.minecraft.util.CartesianProduct(
                type,
                (java.lang.Iterable[])
                        net.minecraft.util.Cartesian.toArray(
                                java.lang.Iterable.class, iterable),
                null);
    }
}
