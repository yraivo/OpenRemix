package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty {
    String getName(Comparable comparable);

    String getName();

    Class getValueClass();

    Collection getAllowedValues();
}
