package net.minecraft.inventory;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnchantmentSlot2 extends Slot {
    ContainerEnchantment btN;

    public ContainerEnchantmentSlot2(
            ContainerEnchantment btNValue, IInventory iInventory, int i, int j, int k) {
        super(iInventory, i, j, k);
        this.btN = btNValue;
    }

    public int getSlotStackLimit() {
        return 1;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return true;
    }
}
