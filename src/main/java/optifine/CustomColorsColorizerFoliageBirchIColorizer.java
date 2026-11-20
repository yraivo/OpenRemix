package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import optifine.CustomColorizer;
import optifine.CustomColors;

public final class CustomColorsColorizerFoliageBirchIColorizer implements CustomColorizer {
    public boolean isColorConstant() {
        return CustomColors.getFoliageBirchColors() == null;
    }

    public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return CustomColors.getFoliageBirchColors() != null
                ? CustomColors.getFoliageBirchColors().getColor(iBlockAccess, blockPos)
                : ColorizerFoliage.getFoliageColorBirch();
    }
}
