package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventorySlot1 extends Slot {
    ContainerHorseInventory bud;

    public ContainerHorseInventorySlot1(
            ContainerHorseInventory budValue, IInventory iInventory, int i, int j, int k) {
        super(iInventory, i, j, k);
        this.bud = budValue;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return super.isItemValid(itemStack)
                && itemStack.getItem() == Items.saddle
                && !this.getHasStack();
    }
}
