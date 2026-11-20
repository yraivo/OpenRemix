package net.minecraft.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ContainerBrewingStand extends Container {
    private IInventory tileBrewingStand;
    private Slot theSlot;
    private int brewTime;

    @Override
    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.func_175173_a(this, this.tileBrewingStand);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.tileBrewingStand.isUseableByPlayer(playerEntity);
    }

    @Override
    public void updateProgressBar(int p171_0, int p171_1) {
        this.tileBrewingStand.setField(p171_0, p171_1);
    }

    @Override
    public void detectAndSendChanges() {
        int k = net.minecraft.inventory.Container.aW();
        super.detectAndSendChanges();
        int i = k;
        int j = 0;

        while (j < this.crafters.size()) {
            net.minecraft.inventory.ICrafting iCrafting = (net.minecraft.inventory.ICrafting) this.crafters.get(j);
            if (this.brewTime != this.tileBrewingStand.getField(0)) {
                iCrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getField(0));
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        this.brewTime = this.tileBrewingStand.getField(0);
    }

    public ContainerBrewingStand(InventoryPlayer inventoryPlayer, IInventory bkDValue) {
        int l = Container.cR();
        this.tileBrewingStand = bkDValue;
        this.addSlotToContainer(
                new ContainerBrewingStandPotion(inventoryPlayer.player, bkDValue, 0, 56, 46));
        this.addSlotToContainer(
                new ContainerBrewingStandPotion(inventoryPlayer.player, bkDValue, 1, 79, 53));
        this.addSlotToContainer(
                new ContainerBrewingStandPotion(inventoryPlayer.player, bkDValue, 2, 102, 46));
        this.theSlot =
                this.addSlotToContainer(new ContainerBrewingStandIngredient(this, bkDValue, 3, 79, 17));
        int j = 0;
        int i = l;

        label31:
        while (j < 3) {
            int k = 0;

            while (true) {
                if (k < 9) {
                    this.addSlotToContainer(
                            new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
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
            this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 142));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int p171_1) {
        int k = net.minecraft.inventory.Container.aW();
        net.minecraft.item.ItemStack itemStack = null;
        int j = k;
        net.minecraft.inventory.Slot slot = (net.minecraft.inventory.Slot) this.inventorySlots.get(p171_1);
        if (slot != null && slot.getHasStack()) {
            net.minecraft.item.ItemStack itemStackValue = slot.getStack();
            itemStack = itemStackValue.copy();
            if ((p171_1 < 0 || p171_1 > 2) && p171_1 != 3) {
                if (!this.theSlot.getHasStack()
                        && this.theSlot.isItemValid(itemStackValue)) {
                    if (!this.mergeItemStack(itemStackValue, 3, 4, false)) {
                        return null;
                    }
                } else if (net.minecraft.inventory.ContainerBrewingStandPotion.canHoldPotion(
                        itemStack)) {
                    if (!this.mergeItemStack(itemStackValue, 0, 3, false)) {
                        return null;
                    }
                } else if (p171_1 >= 4 && p171_1 < 31) {
                    if (!this.mergeItemStack(itemStackValue, 31, 40, false)) {
                        return null;
                    }
                } else if (p171_1 >= 31 && p171_1 < 40) {
                    if (!this.mergeItemStack(itemStackValue, 4, 31, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStackValue, 4, 40, false)) {
                    return null;
                }
            } else {
                if (!this.mergeItemStack(itemStackValue, 4, 40, true)) {
                    return null;
                }

                slot.onSlotChange(itemStackValue, itemStack);
            }

            {
                if (itemStackValue.stackSize == 0) {
                    slot.putStack((net.minecraft.item.ItemStack) null);
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
