package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class BootstrapEnumDyeColorDispenserBehavior extends BehaviorDefaultDispenseItem {
    private boolean succeeded = true;

    protected void playDispenseSound(IBlockSource iBlockSource) {
        int i = Bootstrap.f();
        if (this.succeeded) {
            iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            if (i == 0) {
                return;
            }
        }

        iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
    }

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        int i = Bootstrap.cR();
        if (EnumDyeColor.WHITE != EnumDyeColor.byDyeDamage(itemStack.getItemDamage())) {
            return super.dispenseStack(iBlockSource, itemStack);
        }

        World world = iBlockSource.getWorld();
        BlockPos blockPos =
                iBlockSource
                        .getBlockPos()
                        .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        if (ItemDye.applyBonemeal(itemStack, world, blockPos)) {
            if (world.isRemote) {
                return itemStack;
            }

            world.playAuxSFX(2005, blockPos, 0);
            if (i != 0) {
                return itemStack;
            }
        }

        this.succeeded = false;
        return itemStack;
    }
}
