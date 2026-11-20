package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.CustomColorizer;
import optifine.CustomColors;

public final class CustomColorsColorizerGrassIColorizer implements CustomColorizer {
    public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BiomeGenBase biomeGenBase = CustomColors.getColorBiome(iBlockAccess, blockPos);
        return CustomColors.getSwampGrassColors() != null && biomeGenBase == BiomeGenBase.swampland
                ? CustomColors.getSwampGrassColors().getColor(biomeGenBase, blockPos)
                : biomeGenBase.getGrassColorAtPos(blockPos);
    }

    public boolean isColorConstant() {
        return false;
    }
}
