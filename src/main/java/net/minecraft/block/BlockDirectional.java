package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.FacingPlane;

public abstract class BlockDirectional extends Block {
    public static PropertyDirection AGE;

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockDirectional.AGE = PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
    }

    protected BlockDirectional(Material material) {
        super(material);
    }

    // region String decoding support

    // endregion
}
