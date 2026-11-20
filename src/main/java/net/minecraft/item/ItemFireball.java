package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemFireball extends Item {
    private static String kE;

    // region String decoding support

    // endregion

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (world.isRemote) {
            return true;
        }

        blockPos = blockPos.offset(facing);
        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.AIR) {
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    kE,
                    1.0F,
                    (net.minecraft.item.Item.itemRand.nextFloat() - net.minecraft.item.Item.itemRand.nextFloat()) * 0.2F
                            + 1.0F);
            world.setBlockState(blockPos, Blocks.fire.getDefaultState());
        }

        if (!playerEntity.capabilities.isCreativeMode) {
            itemStack.stackSize--;
        }

        return true;
    }

    public ItemFireball() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    static {
        kE = "item.fireCharge.use";
    }

    // region String decoding support

    // endregion
}
