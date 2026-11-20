package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.CustomColorizer;
import optifine.CustomColors;

public final class CustomColorsColorizerFoliageIColorizer implements CustomColorizer {
    public boolean isColorConstant() {
        return false;
    }

    public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BiomeGenBase biomeGenBase = CustomColors.getColorBiome(iBlockAccess, blockPos);
        return CustomColors.getSwampFoliageColors() != null && biomeGenBase == BiomeGenBase.swampland
                ? CustomColors.getSwampFoliageColors().getColor(biomeGenBase, blockPos)
                : biomeGenBase.getFoliageColorAtPos(blockPos);
    }
}
