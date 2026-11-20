package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public final class MaterialMaterial1 extends Material {
    public MaterialMaterial1(MapColor mapColor) {
        super(mapColor);
    }

    public boolean blocksMovement() {
        return false;
    }
}
