package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface CustomColorizer {
    int getColor(IBlockAccess iBlockAccess, BlockPos blockPos);

    boolean isColorConstant();
}
