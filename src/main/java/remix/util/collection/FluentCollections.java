package remix.util.collection;

import net.minecraft.entity.Entity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public final class FluentCollections {
    public static final <E> FilteredFluentList method_0be6f1ddf7cb1e469a53(
            List<E> list, Predicate<E> predicate) {
        return method_24641a101cd83644c156(list, predicate, false);
    }

    public static final <K, V> FilteredFluentMap method_acf37d027ac305d9bfb9(
            Predicate<K> predicate, Predicate<V> predicate1, boolean flag) {
        int i = FluentCollectionWrapper.f();
        FilteredFluentMap filteredFluentMap =
                new FilteredFluentMap(newMutableMap(), predicate, predicate1, flag);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            FluentCollectionWrapper.b(++i);
        }

        return filteredFluentMap;
    }

    public static final <A, B> ConvertingFluentList method_b6df21ed06fd2cc95128(
            Function<A, B> function, Function<B, A> function1) {
        return new ConvertingFluentList(ax(), function, function1);
    }

    public static final <K, V> FilteredFluentMap method_fbdc4d0479160e361ab5(
            Predicate<K> predicate, Predicate<V> predicateValue) {
        return remix.util.collection.FluentCollections.method_acf37d027ac305d9bfb9(
                predicate, predicateValue, false);
    }

    public static final <K, V> FilteredFluentMap method_da6218ed90809d9ebf50(
            Map<K, V> map, Predicate<K> predicate, Predicate<V> predicateValue) {
        return remix.util.collection.FluentCollections.method_bb3ebb52c6efd98f5eb1(
                map, predicate, predicateValue, false);
    }

    public static final <E> FluentListWrapper ax() {
        return new FluentListWrapper();
    }

    public static final <K, V> FilteredFluentMap method_bb3ebb52c6efd98f5eb1(
            Map<K, V> map, Predicate<K> predicate, Predicate<V> predicate1, boolean flag) {
        int i = FluentCollectionWrapper.f();
        FilteredFluentMap filteredFluentMap =
                new FilteredFluentMap(
                        method_a09b51431db5c67c4348(map), predicate, predicate1, flag);
        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }

        return filteredFluentMap;
    }

    public static final <E> FilteredFluentList method_24641a101cd83644c156(
            List<E> list, Predicate<E> predicate, boolean flag) {
        return new FilteredFluentList(method_e2cb25ad6dbcdd481b22(list), predicate, flag);
    }

    public static final <E> FilteredFluentList method_48ff601cfc0de9b51805(
            Predicate<E> predicate) {
        return new FilteredFluentList(ax(), predicate, false);
    }

    public static final <E> FluentListWrapper method_e2cb25ad6dbcdd481b22(List<E> list) {
        return new FluentListWrapper(list);
    }

    public static final <K, V> FluentMapWrapper newMutableMap() {
        return new FluentMapWrapper();
    }

    public static final <K, V> FluentMapWrapper method_a09b51431db5c67c4348(
            Map<K, V> map) {
        return new remix.util.collection.FluentMapWrapper(map);
    }

    public static final <E> FilteredFluentList method_9a3bfbc8a5bbe23829b1(
            Predicate<E> predicate, boolean flag) {
        return new FilteredFluentList(ax(), predicate, flag);
    }

    public static final <A, B> ConvertingFluentList method_56f6936f05e7cc3d31cb(
            List<B> list, Function<A, B> function, Function<B, A> function1) {
        return new ConvertingFluentList(
                method_e2cb25ad6dbcdd481b22(list), function, function1);
    }
}
