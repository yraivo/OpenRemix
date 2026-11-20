package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import com.google.common.collect.ImmutableMap;

import java.util.Collection;

public interface BlockState {
    Collection getPropertyNames();

    ImmutableMap getProperties();

    Block getBlock();

    BlockState withProperty(IProperty iProperty, Comparable comparable);

    BlockState cycleProperty(IProperty iProperty);

    Comparable getValue(IProperty iProperty);
}
