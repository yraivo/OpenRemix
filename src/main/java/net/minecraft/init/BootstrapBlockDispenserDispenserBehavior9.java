package net.minecraft.init;

import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.BlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public final class BootstrapBlockDispenserDispenserBehavior9 extends BehaviorDefaultDispenseItem {
    protected void playDispenseSound(IBlockSource iBlockSource) {}

    protected void spawnDispenseParticles(IBlockSource iBlockSource, Facing facing) {}

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        World world = iBlockSource.getWorld();
        BlockPos blockPos =
                iBlockSource
                        .getBlockPos()
                        .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        if (world.isAirBlock(blockPos)) {
            if (!world.isRemote) {
                BlockState blockState =
                        Blocks.command_block
                                .getDefaultState()
                                .withProperty(BlockCommandBlock.TRIGGERED_PROP, false);
                world.setBlockState(blockPos, blockState, 3);
                ItemBlock.setTileEntityNBT(world, blockPos, itemStack);
                world.notifyNeighborsOfStateChange(
                        iBlockSource.getBlockPos(), iBlockSource.getBlock());
            }

            itemStack.stackSize--;
        }

        return itemStack;
    }
}
