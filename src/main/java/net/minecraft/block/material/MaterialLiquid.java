package net.minecraft.block.material;

public class MaterialLiquid extends Material {
    public MaterialLiquid(MapColor mapColor) {
        super(mapColor);
        this.setReplaceable();
        this.setNoPushMobility();
    }

    public boolean isSolid() {
        return false;
    }

    public boolean blocksMovement() {
        return false;
    }

    public boolean isLiquid() {
        return true;
    }
}
