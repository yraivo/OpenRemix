package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneLight extends Block {
    private boolean isOn;

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            if (this.isOn && !world.isBlockPowered(blockPos)) {
                world.scheduleUpdate(blockPos, this, 4);
                if (i == 0) {
                    return;
                }
            }

            if (!this.isOn && world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
            }
        }
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        return new ItemStack(Blocks.redstone_lamp);
    }

    public BlockRedstoneLight(boolean isOnValue) {
        super(Material.redstoneLight);
        this.isOn = isOnValue;
        if (isOnValue) {
            this.setLightLevel(1.0F);
        }
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            if (this.isOn && !world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.redstone_lamp.getDefaultState(), 2);
                if (i == 0) {
                    return;
                }
            }

            if (!this.isOn && world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
            }
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote && this.isOn && !world.isBlockPowered(blockPos)) {
            world.setBlockState(blockPos, Blocks.redstone_lamp.getDefaultState(), 2);
        }
    }
}
