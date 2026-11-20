package net.minecraft.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.inventory.ContainerPlayerSlot1;

public class ContainerPlayer extends Container {
    private PlayerEntity thePlayer;
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public boolean isLocalWorld;
    public IInventory craftResult;

    public ContainerPlayer(
            InventoryPlayer inventoryPlayer, boolean isLocalWorldValue, PlayerEntity bkxValue) {
        int j1 = Container.cR();
        this.craftResult = new InventoryCraftResult();
        int i = j1;
        this.isLocalWorld = isLocalWorldValue;
        this.thePlayer = bkxValue;
        this.addSlotToContainer(
                new SlotCrafting(
                        inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        int j = 0;

        label60:
        while (j < 2) {
            int k = 0;

            while (true) {
                if (k < 2) {
                    this.addSlotToContainer(
                            new Slot(this.craftMatrix, k + j * 2, 88 + k * 18, 26 + j * 18));
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label60;
                }
                break;
            }
        }

        j = 0;

        while (j < 4) {
            int l = j;
            this.addSlotToContainer(
                    new ContainerPlayerSlot1(
                            this,
                            inventoryPlayer,
                            inventoryPlayer.getSizeInventory() - 1 - j,
                            8,
                            8 + j * 18,
                            l));
            j++;
            if (i != 0) {
                break;
            }
        }

        j = 0;

        label40:
        while (j < 3) {
            int i1 = 0;

            while (true) {
                if (i1 < 9) {
                    this.addSlotToContainer(
                            new Slot(inventoryPlayer, i1 + (j + 1) * 9, 8 + i1 * 18, 84 + j * 18));
                    i1++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label40;
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

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot);
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return true;
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int l = Container.aW();
        ItemStack itemStack = null;
        int j = l;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue;
            label81:
            {
                itemStackValue = slot.getStack();
                itemStack = itemStackValue.copy();
                if (i == 0) {
                    if (!this.mergeItemStack(itemStackValue, 9, 45, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemStackValue, itemStack);
                    if (j != 0) {
                        break label81;
                    }
                }

                if (i >= 1 && i < 5) {
                    if (!this.mergeItemStack(itemStackValue, 9, 45, false)) {
                        return null;
                    }
                } else if (i >= 5 && i < 9) {
                    if (!this.mergeItemStack(itemStackValue, 9, 45, false)) {
                        return null;
                    }
                } else {
                    label83:
                    {
                        if (itemStack.getItem() instanceof ItemArmor
                                && !((Slot)
                                                this.inventorySlots.get(
                                                        5
                                                                + ((ItemArmor) itemStack.getItem())
                                                                        .armorType))
                                        .getHasStack()) {
                            int k = 5 + ((ItemArmor) itemStack.getItem()).armorType;
                            if (!this.mergeItemStack(
                                    itemStackValue, k, k + 1, false)) {
                                return null;
                            }

                            if (j != 0) {
                                break label83;
                            }
                        }

                        if (i >= 9 && i < 36) {
                            if (!this.mergeItemStack(itemStackValue, 36, 45, false)) {
                                return null;
                            }
                        } else if (i >= 36 && i < 45) {
                            if (!this.mergeItemStack(itemStackValue, 9, 36, false)) {
                                return null;
                            }
                        } else if (!this.mergeItemStack(
                                itemStackValue, 9, 45, false)) {
                            return null;
                        }
                    }
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

    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        int k = Container.cR();
        int j = 0;
        int i = k;

        while (j < 4) {
            ItemStack itemStack = this.craftMatrix.getStackInSlotOnClosing(j);
            if (itemStack != null) {
                playerEntity.dropPlayerItemWithRandomChoice(itemStack, false);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack) null);
    }

    public void onCraftMatrixChanged(IInventory iInventory) {
        this.craftResult.setInventorySlotContents(
                0,
                CraftingManager.getInstance()
                        .findMatchingRecipe(this.craftMatrix, this.thePlayer.world));
    }
}
