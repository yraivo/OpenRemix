package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;

public final class BootstrapBlockDispenserDispenserBehavior6 extends BehaviorDefaultDispenseItem {
    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
        double d0 = iBlockSource.getX() + facing.getFrontOffsetX();
        double d1 = iBlockSource.getBlockPos().getY() + 0.2F;
        double d2 = iBlockSource.getZ() + facing.getFrontOffsetZ();
        EntityFireworkRocket entityFireworkRocket =
                new EntityFireworkRocket(iBlockSource.getWorld(), d0, d1, d2, itemStack);
        iBlockSource.getWorld().spawnEntityInWorld(entityFireworkRocket);
        itemStack.splitStack(1);
        return itemStack;
    }

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playAuxSFX(1002, iBlockSource.getBlockPos(), 0);
    }
}
