package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Slot {
    private int slotIndex;
    public IInventory inventory;
    public int yDisplayPosition;
    public int slotNumber;
    public int xDisplayPosition;

    public void onSlotChange(ItemStack itemStack, ItemStack itemStackValue) {
        if (itemStack != null
                && itemStackValue != null
                && itemStack.getItem() == itemStackValue.getItem()) {
            int deltaStackSize = itemStackValue.stackSize - itemStack.stackSize;
            if (deltaStackSize > 0) {
                this.onCrafting(itemStack, deltaStackSize);
            }
        }
    }

    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }

    public void putStack(ItemStack itemStack) {
        this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
        this.onSlotChanged();
    }

    public ItemStack decrStackSize(int i) {
        return this.inventory.decrStackSize(this.slotIndex, i);
    }

    public boolean canBeHovered() {
        return true;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return true;
    }

    public Slot(IInventory buaValue, int slotIndexValue, int j, int k) {
        this.inventory = buaValue;
        this.slotIndex = slotIndexValue;
        this.xDisplayPosition = j;
        this.yDisplayPosition = k;
    }

    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }

    public boolean canTakeStack(PlayerEntity playerEntity) {
        return true;
    }

    public void onSlotChanged() {
        this.inventory.markDirty();
    }

    public int getItemStackLimit(ItemStack itemStack) {
        return this.getSlotStackLimit();
    }

    public boolean getHasStack() {
        return this.getStack() != null;
    }

    protected void onCrafting(ItemStack itemStack) {}

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onSlotChanged();
    }

    protected void onCrafting(ItemStack itemStack, int i) {}

    public String getSlotTexture() {
        return null;
    }

    public boolean isHere(IInventory iInventory, int i) {
        return iInventory == this.inventory && i == this.slotIndex;
    }
}
