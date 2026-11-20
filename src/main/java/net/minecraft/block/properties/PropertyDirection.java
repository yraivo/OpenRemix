package net.minecraft.block.properties;

import net.minecraft.util.Facing;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import java.util.Collection;

public class PropertyDirection extends PropertyEnum {
    public static PropertyDirection create(String text) {
        return create(text, Predicates.alwaysTrue());
    }

    public static PropertyDirection create(String text, Collection collection) {
        return new net.minecraft.block.properties.PropertyDirection(text, collection);
    }

    public static PropertyDirection create(String text, Predicate p171_1) {
        return net.minecraft.block.properties.PropertyDirection.create(
                text,
                com.google.common.collect.Collections2.filter(
                        com.google.common.collect.Lists.newArrayList(net.minecraft.util.Facing.values()),
                        p171_1));
    }

    protected PropertyDirection(String text, Collection collection) {
        super(text, Facing.class, collection);
    }
}
