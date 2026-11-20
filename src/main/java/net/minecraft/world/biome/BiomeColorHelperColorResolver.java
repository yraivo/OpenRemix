package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;

public interface BiomeColorHelperColorResolver {
    int getColorAtPos(BiomeGenBase biomeGenBase, BlockPos blockPos);
}
