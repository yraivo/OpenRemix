package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public final class BootstrapItemBucketDispenserBehavior extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem baM = new BehaviorDefaultDispenseItem();

    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        ItemBucket item = (ItemBucket) itemStack.getItem();
        BlockPos blockPos =
                iBlockSource
                        .getBlockPos()
                        .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        if (item.tryPlaceContainedLiquid(iBlockSource.getWorld(), blockPos)) {
            itemStack.setItem(Items.bucket);
            itemStack.stackSize = 1;
            return itemStack;
        } else {
            return this.baM.dispense(iBlockSource, itemStack);
        }
    }
}
