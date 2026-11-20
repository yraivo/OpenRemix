package net.minecraft.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    private BlockPos pos;
    private World worldObj;
    public IInventory craftResult = new InventoryCraftResult();

    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot);
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.worldObj.getBlockState(this.pos).getBlock() != Blocks.crafting_table
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public void onCraftMatrixChanged(IInventory iInventory) {
        this.craftResult.setInventorySlotContents(
                0,
                CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }

    public ContainerWorkbench(InventoryPlayer inventoryPlayer, World bkLValue, BlockPos blockPos) {
        int i1 = Container.cR();
        this.worldObj = bkLValue;
        this.pos = blockPos;
        this.addSlotToContainer(
                new SlotCrafting(
                        inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int j = 0;
        int i = i1;

        label50:
        while (j < 3) {
            int k = 0;

            while (true) {
                if (k < 3) {
                    this.addSlotToContainer(
                            new Slot(this.craftMatrix, k + j * 3, 30 + k * 18, 17 + j * 18));
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
                            new Slot(inventoryPlayer, l + j * 9 + 9, 8 + l * 18, 84 + j * 18));
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
            this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 142));
            j++;
            if (i != 0) {
                break;
            }
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        int k = net.minecraft.inventory.Container.cR();
        super.onContainerClosed(playerEntity);
        int i = k;
        if (!this.worldObj.isRemote) {
            int j = 0;

            while (j < 9) {
                net.minecraft.item.ItemStack itemStack = this.craftMatrix.getStackInSlotOnClosing(j);
                if (itemStack != null) {
                    playerEntity.dropPlayerItemWithRandomChoice(itemStack, false);
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int k = Container.cR();
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        int j = k;
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue;
            label54:
            {
                itemStackValue = slot.getStack();
                itemStack = itemStackValue.copy();
                if (i == 0) {
                    if (!this.mergeItemStack(itemStackValue, 10, 46, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemStackValue, itemStack);
                    if (j == 0) {
                        break label54;
                    }
                }

                if (i >= 10 && i < 37) {
                    if (!this.mergeItemStack(itemStackValue, 37, 46, false)) {
                        return null;
                    }
                } else if (i >= 37 && i < 46) {
                    if (!this.mergeItemStack(itemStackValue, 10, 37, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStackValue, 10, 46, false)) {
                    return null;
                }
            }

            {
                if (itemStackValue.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                    if (!(j == 0)) {

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
