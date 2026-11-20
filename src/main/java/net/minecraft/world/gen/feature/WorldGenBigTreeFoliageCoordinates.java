package net.minecraft.world.gen.feature;

import net.minecraft.util.BlockPos;

class WorldGenBigTreeFoliageCoordinates extends BlockPos {
    private int rz;

    public WorldGenBigTreeFoliageCoordinates(BlockPos blockPos, int i) {
        super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        this.rz = i;
    }

    public int aW() {
        return this.rz;
    }
}
