package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container {
    private int numRows;
    private IInventory lowerChestInventory;

    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack original = null;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            original = stack.copy();
            int chestSlots = this.numRows * 9;
            if (index < chestSlots) {
                if (!this.mergeItemStack(stack, chestSlots, this.inventorySlots.size(), true)) return null;
            } else if (!this.mergeItemStack(stack, 0, chestSlots, false)) {
                return null;
            }
            if (stack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();
        }
        return original;
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.lowerChestInventory.closeInventory(playerEntity);
    }

    public ContainerChest(IInventory iInventory, IInventory bkDValue, PlayerEntity playerEntity) {
        this.lowerChestInventory = bkDValue;
        int j1 = Container.aW();
        this.numRows = bkDValue.getSizeInventory() / 9;
        bkDValue.openInventory(playerEntity);
        int i = j1;
        int j = (this.numRows - 4) * 18;
        int k = 0;

        label50:
        while (k < this.numRows) {
            int l = 0;

            while (true) {
                if (l < 9) {
                    this.addSlotToContainer(new Slot(bkDValue, l + k * 9, 8 + l * 18, 18 + k * 18));
                    l++;
                    if (i != 0) {
                        continue;
                    }
                }

                k++;
                if (i == 0) {
                    break label50;
                }
                break;
            }
        }

        k = 0;

        label37:
        while (k < 3) {
            int i1 = 0;

            while (true) {
                if (i1 < 9) {
                    this.addSlotToContainer(
                            new Slot(iInventory, i1 + k * 9 + 9, 8 + i1 * 18, 103 + k * 18 + j));
                    i1++;
                    if (i != 0) {
                        continue;
                    }
                }

                k++;
                if (i == 0) {
                    break label37;
                }
                break;
            }
        }

        k = 0;

        while (k < 9) {
            this.addSlotToContainer(new Slot(iInventory, k, 8 + k * 18, 161 + j));
            k++;
            if (i == 0) {
                break;
            }
        }
    }

    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.lowerChestInventory.isUseableByPlayer(playerEntity);
    }
}
