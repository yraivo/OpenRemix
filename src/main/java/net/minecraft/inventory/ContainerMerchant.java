package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchant extends Container {
    private InventoryMerchant merchantInventory;
    private World theWorld;
    private IMerchant theMerchant;

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int k = Container.aW();
        ItemStack itemStack = null;
        int j = k;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue;
            label58:
            {
                itemStackValue = slot.getStack();
                itemStack = itemStackValue.copy();
                if (i == 2) {
                    if (!this.mergeItemStack(itemStackValue, 3, 39, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemStackValue, itemStack);
                    if (j != 0) {
                        break label58;
                    }
                }

                if (i != 0 && i != 1) {
                    if (i >= 3 && i < 30) {
                        if (!this.mergeItemStack(itemStackValue, 30, 39, false)) {
                            return null;
                        }
                    } else if (i >= 30
                            && i < 39
                            && !this.mergeItemStack(itemStackValue, 3, 30, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStackValue, 3, 39, false)) {
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

    public ContainerMerchant(InventoryPlayer inventoryPlayer, IMerchant bkMValue, World bkLValue) {
        this.theMerchant = bkMValue;
        this.theWorld = bkLValue;
        this.merchantInventory = new InventoryMerchant(inventoryPlayer.player, bkMValue);
        int l = Container.cR();
        this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
        int i = l;
        this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
        this.addSlotToContainer(
                new SlotMerchantResult(
                        inventoryPlayer.player, bkMValue, this.merchantInventory, 2, 120, 53));
        int j = 0;

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

    public void updateProgressBar(int i, int j) {}

    public void onCraftMatrixChanged(IInventory iInventory) {
        this.merchantInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(iInventory);
    }

    public InventoryMerchant getMerchantInventory() {
        return this.merchantInventory;
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.theMerchant.getCustomer() == playerEntity;
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.theMerchant.setCustomer((PlayerEntity) null);
        super.onContainerClosed(playerEntity);
        if (!this.theWorld.isRemote) {
            ItemStack itemStack = this.merchantInventory.getStackInSlotOnClosing(0);
            if (itemStack != null) {
                playerEntity.dropPlayerItemWithRandomChoice(itemStack, false);
            }

            itemStack = this.merchantInventory.getStackInSlotOnClosing(1);
            if (itemStack != null) {
                playerEntity.dropPlayerItemWithRandomChoice(itemStack, false);
            }
        }
    }

    public void setCurrentRecipeIndex(int i) {
        this.merchantInventory.setCurrentRecipeIndex(i);
    }
}
