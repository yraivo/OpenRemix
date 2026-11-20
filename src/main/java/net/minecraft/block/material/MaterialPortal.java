package net.minecraft.block.material;

public class MaterialPortal extends Material {
    public boolean blocksLight() {
        return false;
    }

    public boolean blocksMovement() {
        return false;
    }

    public boolean isSolid() {
        return false;
    }

    public MaterialPortal(MapColor mapColor) {
        super(mapColor);
    }
}
