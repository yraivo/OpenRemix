package net.minecraft.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFurnace extends Container {
    private int rC;
    private int jM;
    private int mE;
    private IInventory tileFurnace;
    private int rW;

    public ContainerFurnace(InventoryPlayer inventoryPlayer, IInventory iInventory) {
        this(inventoryPlayer, iInventory, Container.cR());
    }

    private ContainerFurnace(
            InventoryPlayer inventoryPlayer, IInventory bkDValue, int initialFlowToken) {
        super();

        int l = initialFlowToken;

        this.tileFurnace = bkDValue;
        this.addSlotToContainer(new Slot(bkDValue, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnaceFuel(bkDValue, 1, 56, 53));
        this.addSlotToContainer(
                new SlotFurnaceOutput(inventoryPlayer.player, bkDValue, 2, 116, 35));
        int i = l;
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

    public void updateProgressBar(int i, int j) {
        this.tileFurnace.setField(i, j);
    }

    public void detectAndSendChanges() {
        int k = Container.aW();
        super.detectAndSendChanges();
        int i = k;
        int j = 0;

        while (j < this.crafters.size()) {
            ICrafting iCrafting = (ICrafting) this.crafters.get(j);
            if (this.mE != this.tileFurnace.getField(2)) {
                iCrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
            }

            if (this.rC != this.tileFurnace.getField(0)) {
                iCrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
            }

            if (this.jM != this.tileFurnace.getField(1)) {
                iCrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
            }

            if (this.rW != this.tileFurnace.getField(3)) {
                iCrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        this.mE = this.tileFurnace.getField(2);
        this.rC = this.tileFurnace.getField(0);
        this.jM = this.tileFurnace.getField(1);
        this.rW = this.tileFurnace.getField(3);
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.tileFurnace.isUseableByPlayer(playerEntity);
    }

    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.func_175173_a(this, this.tileFurnace);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        ItemStack itemStack = null;
        int k = Container.cR();
        Slot slot = (Slot) this.inventorySlots.get(i);
        int j = k;
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue;
            label70:
            {
                itemStackValue = slot.getStack();
                itemStack = itemStackValue.copy();
                if (i == 2) {
                    if (!this.mergeItemStack(itemStackValue, 3, 39, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemStackValue, itemStack);
                    if (j == 0) {
                        break label70;
                    }
                }

                if (i != 1 && i != 0) {
                    if (FurnaceRecipes.instance().getSmeltingResult(itemStackValue) != null) {
                        if (!this.mergeItemStack(itemStackValue, 0, 1, false)) {
                            return null;
                        }
                    } else if (TileEntityFurnace.isItemFuel(itemStackValue)) {
                        if (!this.mergeItemStack(itemStackValue, 1, 2, false)) {
                            return null;
                        }
                    } else if (i >= 3 && i < 30) {
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
