package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import optifine.CustomColorizer;
import optifine.CustomColors;

public final class CustomColorsColorizerFoliagePineIColorizer implements CustomColorizer {
    public boolean isColorConstant() {
        return CustomColors.getFoliagePineColors() == null;
    }

    public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return CustomColors.getFoliagePineColors() != null
                ? CustomColors.getFoliagePineColors().getColor(iBlockAccess, blockPos)
                : ColorizerFoliage.getFoliageColorPine();
    }
}
