package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

class ContainerBrewingStandIngredient extends Slot {
    ContainerBrewingStand buc;

    public ContainerBrewingStandIngredient(
            ContainerBrewingStand bucValue, IInventory iInventory, int i, int j, int k) {
        super(iInventory, i, j, k);
        this.buc = bucValue;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return itemStack != null ? itemStack.getItem().isPotionIngredient(itemStack) : false;
    }

    public int getSlotStackLimit() {
        return 64;
    }
}
