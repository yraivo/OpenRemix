package net.minecraft.inventory;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.InventoryBasic;

public class ContainerEnchantmentTableInventoryInventoryBasic extends InventoryBasic {
    ContainerEnchantment btN;

    public void markDirty() {
        super.markDirty();
        this.btN.onCraftMatrixChanged(this);
    }

    public ContainerEnchantmentTableInventoryInventoryBasic(ContainerEnchantment btNValue, String text, boolean flag, int i) {
        super(text, flag, i);
        this.btN = btNValue;
    }

    public int getInventoryStackLimit() {
        return 64;
    }
}
