package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkEnumCreateEntityType;

public class ChunkCache implements IBlockAccess {
    protected Chunk[][] chunkArray;
    protected int chunkZ;
    protected int chunkX;
    protected boolean hasExtendedLevels;
    protected World worldObj;

    public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            int i = (blockPos.getX() >> 4) - this.chunkX;
            int j = (blockPos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[i][j].getLightFor(enumSkyBlock, blockPos);
        } else {
            return enumSkyBlock.defaultLightValue;
        }
    }

    public BiomeGenBase getBiomeGenForCoords(BlockPos blockPos) {
        return this.worldObj.getBiomeGenForCoords(blockPos);
    }

    public boolean isAirBlock(BlockPos blockPos) {
        return this.getBlockState(blockPos).getBlock().getMaterial() == Material.AIR;
    }

    public WorldType getWorldType() {
        return this.worldObj.getWorldType();
    }

    public int getStrongPower(BlockPos blockPos, Facing facing) {
        net.minecraft.block.state.BlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().isProvidingStrongPower(this, blockPos, blockState, facing);
    }

    public TileEntity getTileEntity(BlockPos blockPos) {
        int i = (blockPos.getX() >> 4) - this.chunkX;
        int j = (blockPos.getZ() >> 4) - this.chunkZ;
        return this.chunkArray[i][j].getTileEntity(blockPos, ChunkEnumCreateEntityType.IMMEDIATE);
    }

    public int getCombinedLight(BlockPos blockPos, int i) {
        int j = this.getLightForExt(EnumSkyBlock.SKY, blockPos);
        int k = this.getLightForExt(EnumSkyBlock.BLOCK, blockPos);
        if (k < i) {
            k = i;
        }

        return j << 20 | k << 4;
    }

    public ChunkCache(World awsValue, BlockPos blockPos, BlockPos blockPosValue, int i) {
        this.worldObj = awsValue;
        this.chunkX = blockPos.getX() - i >> 4;
        this.chunkZ = blockPos.getZ() - i >> 4;
        int j = blockPosValue.getX() + i >> 4;
        int k = blockPosValue.getZ() + i >> 4;
        World.getLegacyWorldFlowMarker();

        this.chunkArray = new Chunk[j - this.chunkX + 1][k - this.chunkZ + 1];

        this.hasExtendedLevels = true;
        int l = this.chunkX;

        while (l <= j) {
            int chunkZValue = this.chunkZ;

            while (true) {
                if (chunkZValue <= k) {
                    this.chunkArray[l - this.chunkX][chunkZValue - this.chunkZ] =
                            awsValue.getChunkFromChunkCoords(l, chunkZValue);
                    chunkZValue++;

                    continue;
                }

                l++;

                break;
            }
        }

        l = blockPos.getX() >> 4;

        while (l <= blockPosValue.getX() >> 4) {
            int j1 = blockPos.getZ() >> 4;

            while (true) {
                if (j1 <= blockPosValue.getZ() >> 4) {
                    Chunk chunk = this.chunkArray[l - this.chunkX][j1 - this.chunkZ];
                    if (chunk != null && !chunk.e(blockPos.getY(), blockPosValue.getY())) {
                        this.hasExtendedLevels = false;
                    }

                    j1++;

                    continue;
                }

                l++;

                break;
            }
        }
    }

    public BlockState getBlockState(BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            int i = (blockPos.getX() >> 4) - this.chunkX;
            int j = (blockPos.getZ() >> 4) - this.chunkZ;
            if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < this.chunkArray[i].length) {
                Chunk chunk = this.chunkArray[i][j];
                if (chunk != null) {
                    return chunk.getBlockState(blockPos);
                }
            }
        }

        return Blocks.air.getDefaultState();
    }

    private int getLightForExt(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        World.getLegacyWorldFlowMarker();

        if (enumSkyBlock == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
            return 0;
        }

        if (blockPos.getY() < 0 || blockPos.getY() >= 256) {
            return enumSkyBlock.defaultLightValue;
        }

        if (!this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int k = (blockPos.getX() >> 4) - this.chunkX;
            int l = (blockPos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[k][l].getLightFor(enumSkyBlock, blockPos);
        }

        int i = 0;

        for (Facing facing : Facing.values()) {
            int j = this.getLightFor(enumSkyBlock, blockPos.offset(facing));
            if (j > i) {
                i = j;
            }

            if (i >= 15) {
                return i;
            }
        }

        return i;
    }

    public boolean extendedLevelsInChunkCache() {
        return this.hasExtendedLevels;
    }
}
