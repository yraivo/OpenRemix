package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;

public class PropertyBool extends PropertyHelper {
    private final ImmutableSet allowedValues = ImmutableSet.of(true, false);

    public static PropertyBool create(String text) {
        return new net.minecraft.block.properties.PropertyBool(text);
    }

    public Collection getAllowedValues() {
        return this.allowedValues;
    }

    public String getName(Comparable comparable) {
        return this.getName0((java.lang.Boolean) comparable);
    }

    protected PropertyBool(String text) {
        super(text, Boolean.class);
    }

    public String getName0(Boolean obool) {
        return obool.toString();
    }
}
