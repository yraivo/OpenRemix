package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

class ContainerBeaconBeaconSlot extends Slot {
    ContainerBeacon field_82876_a;

    public boolean isItemValid(ItemStack itemStack) {
        return itemStack == null
                ? false
                : itemStack.getItem() == Items.emerald
                        || itemStack.getItem() == Items.diamond
                        || itemStack.getItem() == Items.gold_ingot
                        || itemStack.getItem() == Items.iron_ingot;
    }

    public int getSlotStackLimit() {
        return 1;
    }

    public ContainerBeaconBeaconSlot(
            ContainerBeacon bubValue, IInventory iInventory, int i, int j, int k) {
        super(iInventory, i, j, k);
        this.field_82876_a = bubValue;
    }
}
