package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventorySlot2 extends Slot {
    EntityHorse bue;
    ContainerHorseInventory buf;

    public boolean canBeHovered() {
        return this.bue.canWearArmor();
    }

    public ContainerHorseInventorySlot2(
            ContainerHorseInventory bufValue,
            IInventory iInventory,
            int i,
            int j,
            int k,
            EntityHorse bueValue) {
        super(iInventory, i, j, k);
        this.buf = bufValue;
        this.bue = bueValue;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return super.isItemValid(itemStack)
                && this.bue.canWearArmor()
                && EntityHorse.isArmorItem(itemStack.getItem());
    }
}
