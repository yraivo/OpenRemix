package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SlotFurnaceFuel extends Slot {
    public boolean isItemValid(ItemStack itemStack) {
        return net.minecraft.tileentity.TileEntityFurnace.isItemFuel(itemStack)
                || net.minecraft.inventory.SlotFurnaceFuel.isBucket(itemStack);
    }

    public static boolean isBucket(ItemStack itemStack) {
        return itemStack != null
                && itemStack.getItem() != null
                && itemStack.getItem() == Items.bucket;
    }

    public int getItemStackLimit(ItemStack itemStack) {
        return net.minecraft.inventory.SlotFurnaceFuel.isBucket(itemStack)
                ? 1
                : super.getItemStackLimit(itemStack);
    }

    public SlotFurnaceFuel(IInventory iInventory, int p171_1, int p171_2, int p171_3) {
        super(iInventory, p171_1, p171_2, p171_3);
    }
}
