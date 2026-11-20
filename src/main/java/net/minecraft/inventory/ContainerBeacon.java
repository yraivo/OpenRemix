package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ContainerBeacon extends Container {
    private ContainerBeaconBeaconSlot beaconSlot;
    private IInventory tileBeacon;

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.tileBeacon.isUseableByPlayer(playerEntity);
    }

    public void updateProgressBar(int i, int j) {
        this.tileBeacon.setField(i, j);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int k = Container.aW();
        ItemStack itemStack = null;
        int j = k;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue;
            label64:
            {
                itemStackValue = slot.getStack();
                itemStack = itemStackValue.copy();
                if (i == 0) {
                    if (!this.mergeItemStack(itemStackValue, 1, 37, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemStackValue, itemStack);
                    if (j != 0) {
                        break label64;
                    }
                }

                if (!this.beaconSlot.getHasStack()
                        && this.beaconSlot.isItemValid(itemStackValue)
                        && itemStackValue.stackSize == 1) {
                    if (!this.mergeItemStack(itemStackValue, 0, 1, false)) {
                        return null;
                    }
                } else if (i >= 1 && i < 28) {
                    if (!this.mergeItemStack(itemStackValue, 28, 37, false)) {
                        return null;
                    }
                } else if (i >= 28 && i < 37) {
                    if (!this.mergeItemStack(itemStackValue, 1, 28, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStackValue, 1, 37, false)) {
                    return null;
                }
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

    public ContainerBeacon(IInventory iInventory, IInventory bkDValue) {
        this.tileBeacon = bkDValue;
        int l = Container.cR();
        this.addSlotToContainer(
                this.beaconSlot = new ContainerBeaconBeaconSlot(this, bkDValue, 0, 136, 110));
        byte b0 = 36;
        short short1 = 137;
        int i = l;
        int j = 0;

        label31:
        while (j < 3) {
            int k = 0;

            while (true) {
                if (k < 9) {
                    this.addSlotToContainer(
                            new Slot(iInventory, k + j * 9 + 9, b0 + k * 18, short1 + j * 18));
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label31;
                }
                break;
            }
        }

        j = 0;

        while (j < 9) {
            this.addSlotToContainer(new Slot(iInventory, j, b0 + j * 18, 58 + short1));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public IInventory getTileBeacon() {
        return this.tileBeacon;
    }

    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.func_175173_a(this, this.tileBeacon);
    }
}
