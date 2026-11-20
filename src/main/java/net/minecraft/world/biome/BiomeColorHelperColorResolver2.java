package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeColorHelperColorResolver;
import net.minecraft.world.biome.BiomeGenBase;

public final class BiomeColorHelperColorResolver2 implements BiomeColorHelperColorResolver {
    public int getColorAtPos(BiomeGenBase biomeGenBase, BlockPos blockPos) {
        return biomeGenBase.getFoliageColorAtPos(blockPos);
    }
}
