package remix.util.collection;

import remix.compat.legacy.codec.PrimitiveTypeUtils;
import remix.util.function.TriConsumer;

import net.minecraft.entity.Entity;

import java.util.Iterator;
import java.util.Optional;

public final class MapUtils {
    public static void method_4fc119ae716ddc3e95fc(
            java.util.Map map, java.util.function.Function function, Object... aobject) {
        Object[] aobject1 = aobject;
        PrimitiveTypeUtils.getEntities();

        int length = aobject1.length;

        int j = 0;

        while (j < length) {
            Object object = aobject1[j];
            map.put(function.apply(object), object);
            j++;
        }
    }

    public static java.util.Map method_4b7ba73b8b822caf5827(
            java.util.Map map, Object object, java.util.function.BiConsumer biconsumer) {
        if (!map.containsKey(object)) {
            biconsumer.accept(map, object);
        }

        return map;
    }

    public static java.util.Map method_fd3656e9fc0ed550edd0(
            java.util.Map map, java.util.function.Predicate predicate) {
        return method_1cacd1575f0706541c58(
                map,
                predicate,
                ((java.util.function.Predicate<java.lang.Object>) (object -> false)));
    }

    public static java.util.Optional method_991fdad75f6098bb8ba7(java.util.Map map, Object object) {
        return Optional.ofNullable((java.lang.Object) map.get(object));
    }

    public static java.util.Optional method_6ce3166f2a2a29df4635(java.util.Map map, Class oclass) {
        PrimitiveTypeUtils.getEntities();

        Iterator iterator = map.values().iterator();

        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object.getClass() == oclass) {
                return Optional.of((java.lang.Object) object);
            }
        }

        Optional optional = Optional.empty();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            PrimitiveTypeUtils.b(new Entity[3]);
        }

        return optional;
    }

    public static java.util.Map method_1cacd1575f0706541c58(
            java.util.Map map,
            java.util.function.Predicate predicate,
            java.util.function.Predicate predicate1) {
        map.entrySet()
                .removeIf(
                        ((java.util.function.Predicate<java.util.Map.Entry>)
                                (entry ->
                                        predicate.test(entry.getKey())
                                                || predicate1.test(entry.getValue()))));
        return map;
    }

    public static java.util.Map method_bbfb36d9d24558e75533(
            java.util.Map map, java.util.function.Predicate predicate) {
        return method_1cacd1575f0706541c58(
                map,
                ((java.util.function.Predicate<java.lang.Object>) (object -> false)),
                predicate);
    }

    public static java.util.Map method_7f86fa30a79a76e9e0a0(
            java.util.Map map,
            Object object,
            TriConsumer triConsumer) {
        if (map.containsKey(object)) {
            triConsumer.method_aa23e9c427d634c139c4(map, object, map.get(object));
        }

        return map;
    }
}
