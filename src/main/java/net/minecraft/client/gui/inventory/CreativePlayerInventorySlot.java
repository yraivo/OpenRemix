package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CreativePlayerInventorySlot extends Slot {
    private Slot slot;
    GuiContainerCreative creativeScreen;

    public CreativePlayerInventorySlot(GuiContainerCreative creativeScreen, Slot backingSlot, int slotIndex) {
        super(backingSlot.inventory, slotIndex, 0, 0);
        this.creativeScreen = creativeScreen;
        this.slot = backingSlot;
    }

    public ItemStack decrStackSize(int i) {
        return this.slot.decrStackSize(i);
    }

    public int getSlotStackLimit() {
        return this.slot.getSlotStackLimit();
    }

    public int getItemStackLimit(ItemStack itemStack) {
        return this.slot.getItemStackLimit(itemStack);
    }

    public boolean getHasStack() {
        return this.slot.getHasStack();
    }

    public void onSlotChanged() {
        this.slot.onSlotChanged();
    }

    public static Slot getBackingSlot(CreativePlayerInventorySlot wrapper) {
        return wrapper.slot;
    }

    public ItemStack getStack() {
        return this.slot.getStack();
    }

    public boolean isHere(IInventory iInventory, int i) {
        return this.slot.isHere(iInventory, i);
    }

    public String getSlotTexture() {
        return this.slot.getSlotTexture();
    }

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        this.slot.onPickupFromSlot(playerEntity, itemStack);
    }

    public boolean isItemValid(ItemStack itemStack) {
        return this.slot.isItemValid(itemStack);
    }

    public void putStack(ItemStack itemStack) {
        this.slot.putStack(itemStack);
    }
}
