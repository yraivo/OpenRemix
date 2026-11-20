package net.minecraft.inventory;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.InventoryBasic;

public class ContainerRepairInputSlotsInventoryBasic extends InventoryBasic {
    ContainerRepair btO;

    public ContainerRepairInputSlotsInventoryBasic(ContainerRepair btOValue, String text, boolean flag, int i) {
        super(text, flag, i);
        this.btO = btOValue;
    }

    public void markDirty() {
        super.markDirty();
        this.btO.onCraftMatrixChanged(this);
    }
}
