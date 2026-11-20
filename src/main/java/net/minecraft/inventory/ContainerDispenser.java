package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ContainerDispenser extends Container {
    private IInventory dispenserInventory;

    public ContainerDispenser(IInventory iInventory, IInventory iInventoryValue) {
        this(iInventory, iInventoryValue, Container.cR());
    }

    private ContainerDispenser(IInventory iInventory, IInventory bkDValue, int initialFlowToken) {
        super();

        int i1 = initialFlowToken;

        this.dispenserInventory = bkDValue;
        int j = 0;
        int i = i1;

        label50:
        while (j < 3) {
            int k = 0;

            while (true) {
                if (k < 3) {
                    this.addSlotToContainer(
                            new Slot(bkDValue, k + j * 3, 62 + k * 18, 17 + j * 18));
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label50;
                }
                break;
            }
        }

        j = 0;

        label37:
        while (j < 3) {
            int l = 0;

            while (true) {
                if (l < 9) {
                    this.addSlotToContainer(
                            new Slot(iInventory, l + j * 9 + 9, 8 + l * 18, 84 + j * 18));
                    l++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label37;
                }
                break;
            }
        }

        j = 0;

        while (j < 9) {
            this.addSlotToContainer(new Slot(iInventory, j, 8 + j * 18, 142));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.dispenserInventory.isUseableByPlayer(playerEntity);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int k = Container.aW();
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        int j = k;
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue = slot.getStack();
            itemStack = itemStackValue.copy();
            if (i < 9) {
                if (!this.mergeItemStack(itemStackValue, 9, 45, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStackValue, 0, 9, false)) {
                return null;
            }

            {
                if (itemStackValue.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                    if (!(j != 0)) {

                        slot.onSlotChanged();
                    }
                } else {

                    slot.onSlotChanged();
                }
            }

            if (itemStackValue.stackSize == itemStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerEntity, itemStackValue);
        }

        return itemStack;
    }
}
