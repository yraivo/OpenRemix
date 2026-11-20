package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemRedstone extends Item {
    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        boolean replaceable =
                world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos);
        BlockPos blockPosValue = replaceable ? blockPos : blockPos.offset(facing);
        if (!playerEntity.canPlayerEdit(blockPosValue, facing, itemStack)) {
            return false;
        } else {
            Block block = world.getBlockState(blockPosValue).getBlock();
            if (!world.canBlockBePlaced(
                    block, blockPosValue, false, facing, (Entity) null, itemStack)) {
                return false;
            } else if (Blocks.redstone_wire.canPlaceBlockAt(world, blockPosValue)) {
                itemStack.stackSize--;
                world.setBlockState(blockPosValue, Blocks.redstone_wire.getDefaultState());
                return true;
            } else {
                return false;
            }
        }
    }

    public ItemRedstone() {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
