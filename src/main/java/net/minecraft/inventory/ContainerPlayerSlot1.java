package net.minecraft.inventory;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerPlayerSlot1 extends Slot {
    ContainerPlayer bug;
    int f;

    public String getSlotTexture() {
        return ItemArmor.EMPTY_SLOT_NAMES[this.f];
    }

    public boolean isItemValid(ItemStack itemStack) {
        return itemStack == null
                ? false
                : (itemStack.getItem() instanceof ItemArmor
                        ? ((ItemArmor) itemStack.getItem()).armorType == this.f
                        : (itemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin)
                                        && itemStack.getItem() != Items.skull
                                ? false
                                : this.f == 0));
    }

    public ContainerPlayerSlot1(
            ContainerPlayer bugValue, IInventory iInventory, int i, int j, int k, int l) {
        super(iInventory, i, j, k);
        this.bug = bugValue;
        this.f = l;
    }

    public int getSlotStackLimit() {
        return 1;
    }
}
