package net.minecraft.block.state;

import net.minecraft.block.properties.IProperty;

import com.google.common.base.Function;

public final class BlockStateGetNameFuncFunction implements Function {
    private static String a;

    public Object apply(Object object) {
        return this.apply((IProperty) object);
    }

    // region String decoding support

    // endregion

    static {
        a = "<NULL>";
    }

    public String apply(IProperty iProperty) {
        return iProperty == null ? a : iProperty.getName();
    }

    // region String decoding support

    // endregion
}
