package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.FacingAxis;

public abstract class BlockRotatedPillar extends Block {

    public static PropertyEnum AXIS;

    // region String decoding support

    // endregion

    protected BlockRotatedPillar(Material material) {
        super(material);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockRotatedPillar.AXIS = PropertyEnum.create("axis", FacingAxis.class);
    }
}
