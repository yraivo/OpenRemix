package net.minecraft.world.gen;

import net.minecraft.block.BlockBush;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GeneratorBushFeature extends WorldGenerator {
    private BlockBush field_175908_a;

    public GeneratorBushFeature(BlockBush bLQValue) {
        this.field_175908_a = bLQValue;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        MapGenBase.b();

        int i = 0;

        while (i < 64) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(blockPosValue)
                    && (!world.provider.getHasNoSky() || blockPosValue.getY() < 255)
                    && this.field_175908_a.canBlockStay(
                            world, blockPosValue, this.field_175908_a.getDefaultState())) {
                world.setBlockState(blockPosValue, this.field_175908_a.getDefaultState(), 2);
            }

            i++;
        }

        return true;
    }
}
