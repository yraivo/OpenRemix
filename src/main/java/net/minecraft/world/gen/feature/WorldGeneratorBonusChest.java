package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WorldGeneratorBonusChest extends WorldGenerator {
    private List chestItems;
    private int itemsToGenerateInBonusChest;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.AIR
                        || block.getMaterial() == Material.leaves)
                && blockPos.getY() > 1) {
            blockPos = blockPos.offsetDown();
            if (text == null) {
                break;
            }
        }

        if (blockPos.getY() < 1) {
            return false;
        }

        blockPos = blockPos.offsetUp();
        int i = 0;

        while (i < 4) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(3) - random.nextInt(3),
                            random.nextInt(4) - random.nextInt(4));
            if (world.isAirBlock(blockPosValue)
                    && World.doesBlockHaveSolidTopSurface(world, blockPosValue.offsetDown())) {
                world.setBlockState(blockPosValue, Blocks.chest.getDefaultState(), 2);
                TileEntity tileEntity = world.getTileEntity(blockPosValue);
                if (tileEntity instanceof TileEntityChest) {
                    WeightedRandomChestContent.generateChestContents(
                            random, this.chestItems, (TileEntityChest) tileEntity, this.itemsToGenerateInBonusChest);
                }

                BlockPos blockPosValue2 = blockPosValue.offsetEast();
                BlockPos blockPosValue3 = blockPosValue.offsetWest();
                BlockPos blockPosValue4 = blockPosValue.offsetNorth();
                BlockPos blockPosValue5 = blockPosValue.offsetSouth();
                if (world.isAirBlock(blockPosValue3)
                        && World.doesBlockHaveSolidTopSurface(world, blockPosValue3.offsetDown())) {
                    world.setBlockState(blockPosValue3, Blocks.torch.getDefaultState(), 2);
                }

                if (world.isAirBlock(blockPosValue2)
                        && World.doesBlockHaveSolidTopSurface(world, blockPosValue2.offsetDown())) {
                    world.setBlockState(blockPosValue2, Blocks.torch.getDefaultState(), 2);
                }

                if (world.isAirBlock(blockPosValue4)
                        && World.doesBlockHaveSolidTopSurface(world, blockPosValue4.offsetDown())) {
                    world.setBlockState(blockPosValue4, Blocks.torch.getDefaultState(), 2);
                }

                if (world.isAirBlock(blockPosValue5)
                        && World.doesBlockHaveSolidTopSurface(world, blockPosValue5.offsetDown())) {
                    world.setBlockState(blockPosValue5, Blocks.torch.getDefaultState(), 2);
                }

                return true;
            }

            i++;
            if (text == null) {
                break;
            }
        }

        return false;
    }

    public WorldGeneratorBonusChest(List list, int i) {
        this.chestItems = list;
        this.itemsToGenerateInBonusChest = i;
    }
}
