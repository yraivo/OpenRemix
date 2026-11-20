package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {
    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (facing == Facing.DOWN) {
            return false;
        }

        if (!world.getBlockState(blockPos).getBlock().getMaterial().isSolid()) {
            return false;
        }

        blockPos = blockPos.offset(facing);
        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (!Blocks.standing_sign.canPlaceBlockAt(world, blockPos)) {
            return false;
        }

        if (world.isRemote) {
            return true;
        }

        {
            if (facing == Facing.UP) {
                int i =
                        MathHelper.floor((playerEntity.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5)
                                & 15;
                world.setBlockState(
                        blockPos,
                        Blocks.standing_sign
                                .getDefaultState()
                                .withProperty(BlockStandingSign.ROTATION_PROP, i),
                        3);
                if (!(text == null)) {

                    world.setBlockState(
                            blockPos,
                            Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.FACING, facing),
                            3);
                }
            } else {

                world.setBlockState(
                        blockPos,
                        Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.FACING, facing),
                        3);
            }
        }

        itemStack.stackSize--;
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntitySign
                && !ItemBlock.setTileEntityNBT(world, blockPos, itemStack)) {
            playerEntity.openEditSign((TileEntitySign) tileEntity);
        }

        return true;
    }

    public ItemSign() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
