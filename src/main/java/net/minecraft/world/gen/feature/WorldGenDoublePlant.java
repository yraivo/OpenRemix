package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenDoublePlant extends WorldGenerator {
    private BlockDoublePlantEnumPlantType field_150549_a;

    public void setPlantType(BlockDoublePlantEnumPlantType bLvValue) {
        this.field_150549_a = bLvValue;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        byte b0 = 0;
        int i = 0;
        String textValue = text;

        while (i < 64) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(blockPosValue)
                    && (!world.provider.getHasNoSky() || blockPosValue.getY() < 254)
                    && Blocks.double_plant.canPlaceBlockAt(world, blockPosValue)) {
                Blocks.double_plant.placeAt(world, blockPosValue, this.field_150549_a, 2);
                b0 = 1;
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }
}
