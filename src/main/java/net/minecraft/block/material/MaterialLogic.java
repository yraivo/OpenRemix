package net.minecraft.block.material;

public class MaterialLogic extends Material {
    public boolean blocksMovement() {
        return false;
    }

    public boolean blocksLight() {
        return false;
    }

    public MaterialLogic(MapColor mapColor) {
        super(mapColor);
        this.Fo();
    }

    public boolean isSolid() {
        return false;
    }
}
