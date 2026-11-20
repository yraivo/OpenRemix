package net.minecraft.block.state;

import net.minecraft.block.properties.IProperty;

import com.google.common.base.Function;

import java.util.Map.Entry;

public final class BlockStateBaseMapEntryToStringFunction implements Function {
    private static String[] packedTextFragments;
    private static String a;

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.state.BlockStateBaseMapEntryToStringFunction.packedTextFragments = new String[2];
        net.minecraft.block.state.BlockStateBaseMapEntryToStringFunction.packedTextFragments[0] = new String("=");
        net.minecraft.block.state.BlockStateBaseMapEntryToStringFunction.packedTextFragments[1] = new String("m9\\<\u001f4");
        a = "<NULL>";
    }

    // region String decoding support

    // endregion

    public Object apply(Object object) {
        return this.method_3b8bfc4a526053761a93((Entry) object);
    }

    // region String decoding support

    // endregion

    public String method_3b8bfc4a526053761a93(Entry entry) {
        if (entry == null) {
            return a;
        }

        IProperty key = (IProperty) entry.getKey();
        return key.getName() + packedTextFragments[0] + key.getName((Comparable) entry.getValue());
    }
}
