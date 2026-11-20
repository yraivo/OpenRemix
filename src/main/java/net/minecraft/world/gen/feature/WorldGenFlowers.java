package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenFlowers extends WorldGenerator {
    private BlockState field_175915_b;
    private BlockFlower flower;

    public WorldGenFlowers(
            BlockFlower blockFlower, BlockFlowerEnumFlowerType blockFlowerEnumFlowerType) {
        this.setGeneratedBlock(blockFlower, blockFlowerEnumFlowerType);
    }

    public void setGeneratedBlock(
            BlockFlower bLxValue, BlockFlowerEnumFlowerType blockFlowerEnumFlowerType) {
        this.flower = bLxValue;
        this.field_175915_b =
                bLxValue.getDefaultState()
                        .withProperty(bLxValue.getTypeProperty(), blockFlowerEnumFlowerType);
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < 64) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(blockPosValue)
                    && (!world.provider.getHasNoSky() || blockPosValue.getY() < 255)
                    && this.flower.canBlockStay(world, blockPosValue, this.field_175915_b)) {
                world.setBlockState(blockPosValue, this.field_175915_b, 2);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return true;
    }
}
