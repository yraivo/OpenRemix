package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {
    private static String kE;

    public ItemFlintAndSteel() {
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        kE = "fire.ignite";
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
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
                    net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F);
            world.setBlockState(blockPos, Blocks.fire.getDefaultState());
        }

        itemStack.damageItem(1, playerEntity);
        return true;
    }
}
