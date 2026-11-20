package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import optifine.CustomColorizer;

public final class CustomColorsColorizerWaterIColorizer implements CustomColorizer {
    public boolean isColorConstant() {
        return false;
    }

    public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
        net.minecraft.world.biome.BiomeGenBase biomeGenBase = optifine.CustomColors.getColorBiome(iBlockAccess, blockPos);
        return optifine.CustomColors.getWaterColors() != null
                ? optifine.CustomColors.getWaterColors().getColor(biomeGenBase, blockPos)
                : (optifine.Reflector.ForgeBiome_getWaterColorMultiplier.exists()
                        ? optifine.Reflector.callInt(
                                biomeGenBase, optifine.Reflector.ForgeBiome_getWaterColorMultiplier, new java.lang.Object[0])
                        : biomeGenBase.waterColorMultiplier);
    }
}
