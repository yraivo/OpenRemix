package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class ContainerEnchantmentSlot3 extends Slot {
    ContainerEnchantment btN;

    public ContainerEnchantmentSlot3(
            ContainerEnchantment btNValue, IInventory iInventory, int i, int j, int k) {
        super(iInventory, i, j, k);
        this.btN = btNValue;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return itemStack.getItem() == Items.dye
                && EnumDyeColor.byDyeDamage(itemStack.getItemDamage()) == EnumDyeColor.BLUE;
    }
}
