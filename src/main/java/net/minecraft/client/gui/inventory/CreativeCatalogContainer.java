package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;

import java.util.List;

class CreativeCatalogContainer extends Container {
    public List<ItemStack> itemList;

    public boolean canDragIntoSlot(Slot slot) {
        return slot.inventory instanceof InventoryPlayer
                || slot.yDisplayPosition > 90 && slot.xDisplayPosition <= 162;
    }

    public void scrollTo(float scrollFraction) {
        boolean legacyCreativeFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        int scrollableRows = (this.itemList.size() + 8) / 9 - 5;
        int firstRow = Math.max(0, (int) (scrollFraction * scrollableRows + 0.5));

        for (int visibleRow = 0; visibleRow < 5; visibleRow++) {
            for (int column = 0; column < 9; column++) {
                int catalogIndex = column + (visibleRow + firstRow) * 9;
                int slotIndex = column + visibleRow * 9;
                if (catalogIndex >= 0 && catalogIndex < this.itemList.size()) {
                    GuiContainerCreative.getCatalogInventory().setInventorySlotContents(slotIndex, this.itemList.get(catalogIndex));
                    if (!legacyCreativeFlow) {
                        continue;
                    }
                }
                GuiContainerCreative.getCatalogInventory().setInventorySlotContents(slotIndex, null);
                if (legacyCreativeFlow) {
                    return;
                }
            }
        }
    }

    protected void retrySlotClick(int i, int j, boolean flag, PlayerEntity playerEntity) {}

    public boolean hasScrollableItems() {
        return this.itemList.size() > 45;
    }

    public CreativeCatalogContainer(PlayerEntity player) {
        boolean legacyCreativeFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        this.itemList = Lists.newArrayList();
        InventoryPlayer inventory = player.inventory;
        int rowCount = legacyCreativeFlow ? 1 : 5;
        int columnCount = legacyCreativeFlow ? 1 : 9;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                this.addSlotToContainer(new Slot(
                        GuiContainerCreative.getCatalogInventory(), row * 9 + column, 9 + column * 18, 18 + row * 18));
            }
        }

        int hotbarSlotCount = legacyCreativeFlow ? 1 : 9;
        for (int hotbarIndex = 0; hotbarIndex < hotbarSlotCount; hotbarIndex++) {
            this.addSlotToContainer(new Slot(inventory, hotbarIndex, 9 + hotbarIndex * 18, 112));
        }
        this.scrollTo(0.0F);
    }

    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.yDisplayPosition > 90;
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return true;
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int p171_1) {
        if (p171_1 >= this.inventorySlots.size() - 9 && p171_1 < this.inventorySlots.size()) {
            net.minecraft.inventory.Slot slot = (net.minecraft.inventory.Slot) this.inventorySlots.get(p171_1);
            if (slot != null && slot.getHasStack()) {
                slot.putStack((net.minecraft.item.ItemStack) null);
            }
        }

        return null;
    }
}
