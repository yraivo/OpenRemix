package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class BootstrapBlockDispenserDispenserBehavior7 extends BehaviorDefaultDispenseItem {
    private boolean succeeded = true;

    protected void playDispenseSound(IBlockSource iBlockSource) {
        int i = Bootstrap.cR();
        if (this.succeeded) {
            iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            if (i != 0) {
                return;
            }
        }

        iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
    }

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        int j = Bootstrap.cR();
        World world = iBlockSource.getWorld();
        int i = j;
        BlockPos blockPos =
                iBlockSource
                        .getBlockPos()
                        .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        BlockPumpkin blj = (BlockPumpkin) Blocks.pumpkin;
        if (world.isAirBlock(blockPos) && blj.canDispenserPlace(world, blockPos)) {
            if (!world.isRemote) {
                world.setBlockState(blockPos, blj.getDefaultState(), 3);
            }

            itemStack.stackSize--;
            if (i != 0) {
                return itemStack;
            }
        }

        this.succeeded = false;
        return itemStack;
    }
}
