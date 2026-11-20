package net.minecraft.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ContainerHopper extends Container {
    private IInventory hopperInventory;

    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.hopperInventory.closeInventory(playerEntity);
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.hopperInventory.isUseableByPlayer(playerEntity);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int k = Container.aW();
        ItemStack itemStack = null;
        int j = k;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue = slot.getStack();
            itemStack = itemStackValue.copy();
            if (i < this.hopperInventory.getSizeInventory()) {
                if (!this.mergeItemStack(
                        itemStackValue,
                        this.hopperInventory.getSizeInventory(),
                        this.inventorySlots.size(),
                        true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(
                    itemStackValue, 0, this.hopperInventory.getSizeInventory(), false)) {
                return null;
            }

            if (itemStackValue.stackSize == 0) {
                slot.putStack((ItemStack) null);
                if (j != 0) {
                    return itemStack;
                }
            }

            slot.onSlotChanged();
        }

        return itemStack;
    }

    public ContainerHopper(
            InventoryPlayer inventoryPlayer, IInventory bkDValue, PlayerEntity playerEntity) {
        int l = Container.aW();
        this.hopperInventory = bkDValue;
        int i = l;
        bkDValue.openInventory(playerEntity);
        byte b0 = 51;
        int j = 0;

        while (j < bkDValue.getSizeInventory()) {
            this.addSlotToContainer(new Slot(bkDValue, j, 44 + j * 18, 20));
            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        label34:
        while (j < 3) {
            int k = 0;

            while (true) {
                if (k < 9) {
                    this.addSlotToContainer(
                            new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, j * 18 + b0));
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label34;
                }
                break;
            }
        }

        j = 0;

        while (j < 9) {
            this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 58 + b0));
            j++;
            if (i == 0) {
                break;
            }
        }
    }
}
