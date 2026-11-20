package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

import java.util.List;
import java.util.Random;

public class EmptyChunk extends Chunk {
    public void setLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos, int i) {}

    public int getBlockMetadata(BlockPos blockPos) {
        return 0;
    }

    public void addEntity(Entity entity) {}

    public boolean isAtLocation(int i, int j) {
        return i == this.xPosition && j == this.zPosition;
    }

    public Random getRandomWithSeed(long i) {
        long j =
                this.getWorld().getSeed()
                        + this.xPosition * this.xPosition * 4987142
                        + this.xPosition * 5947611;
        long k = this.zPosition * this.zPosition;

        return new Random(j + k * 4392871L + this.zPosition * 389711 ^ i);
    }

    public TileEntity getTileEntity(
            BlockPos blockPos, ChunkEnumCreateEntityType chunkEnumCreateEntityType) {
        return null;
    }

    public void setChunkModified() {}

    public void removeEntityAtIndex(Entity entity, int i) {}

    public EmptyChunk(World world, int i, int j) {
        super(world, i, j);
    }

    public boolean e(int i, int j) {
        return true;
    }

    public boolean needsSaving(boolean flag) {
        return false;
    }

    public int setLight(BlockPos blockPos, int i) {
        return 0;
    }

    public void onChunkUnload() {}

    public boolean isEmpty() {
        return true;
    }

    public int getHeight(int i, int j) {
        return 0;
    }

    public void cj() {}

    public boolean canSeeSky(BlockPos blockPos) {
        return false;
    }

    public Block getBlock(BlockPos blockPos) {
        return Blocks.air;
    }

    public void getEntitiesWithinAABBForEntity(
            Entity entity, BoundingBox boundingBox, List list, Predicate predicate) {}

    public int getBlockLightOpacity(BlockPos blockPos) {
        return 255;
    }

    public void generateSkylightMap() {}

    public void addTileEntity(TileEntity tileEntity) {}

    public void removeTileEntity(BlockPos blockPos) {}

    public void onChunkLoad() {}

    public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        return enumSkyBlock.defaultLightValue;
    }

    public void removeEntity(Entity entity) {}

    public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {}

    public void getEntitiesOfTypeWithinAAAB(
            Class oclass, BoundingBox boundingBox, List list, Predicate predicate) {}
}
