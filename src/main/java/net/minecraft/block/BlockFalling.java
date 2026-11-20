package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFalling extends Block {
    public static boolean fallInstantly;

    public static boolean canFallInto(World world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        Material ajwValue = block.blockMaterial;
        return block == Blocks.fire
                || ajwValue == Material.AIR
                || ajwValue == Material.water
                || ajwValue == Material.lava;
    }

    public int tickRate(World world) {
        return 2;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote) {
            this.checkFallable(world, blockPos);
        }
    }

    protected void onStartFalling(EntityFallingBlock entityFallingBlock) {}

    private void checkFallable(World world, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (canFallInto(world, blockPos.offsetDown()) && blockPos.getY() >= 0) {
            byte b0 = 32;
            if (!net.minecraft.block.BlockFalling.fallInstantly
                    && world.isAreaLoaded(blockPos.add(-b0, -b0, -b0), blockPos.add(b0, b0, b0))) {
                if (world.isRemote) {
                    return;
                }

                EntityFallingBlock entityFallingBlock =
                        new EntityFallingBlock(
                                world,
                                blockPos.getX() + 0.5,
                                blockPos.getY(),
                                blockPos.getZ() + 0.5,
                                world.getBlockState(blockPos));
                this.onStartFalling(entityFallingBlock);
                world.spawnEntityInWorld(entityFallingBlock);
                if (i == 0) {
                    return;
                }
            }

            world.setBlockToAir(blockPos);
            BlockPos blockPosValue = blockPos.offsetDown();

            while (canFallInto(world, blockPosValue) && blockPosValue.getY() > 0) {
                blockPosValue = blockPosValue.offsetDown();
                if (i != 0) {
                    break;
                }
            }

            if (blockPosValue.getY() > 0) {
                world.setBlockState(blockPosValue.offsetUp(), this.getDefaultState());
            }
        }
    }

    public BlockFalling() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }

    public BlockFalling(Material material) {
        super(material);
    }

    public void onEndFalling(World world, BlockPos blockPos) {}

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
}
