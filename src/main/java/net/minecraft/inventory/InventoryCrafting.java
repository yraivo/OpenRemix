package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class InventoryCrafting implements IInventory {
    private Container eventHandler;
    private int inventoryWidth;

    private int inventoryHeight;
    private static String e;
    private ItemStack[] stackList;

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean hasCustomName() {
        return false;
    }

    public int getField(int i) {
        return 0;
    }

    public int getWidth() {
        return this.inventoryWidth;
    }

    public void markDirty() {}

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    // region String decoding support

    // endregion

    public void closeInventory(PlayerEntity playerEntity) {}

    public int getFieldCount() {
        return 0;
    }

    public String getName() {
        return e;
    }

    public ItemStack getStackInSlot(int i) {
        return i >= this.getSizeInventory() ? null : this.stackList[i];
    }

    public void openInventory(PlayerEntity playerEntity) {}

    static {
        e = "container.crafting";
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.stackList[i] != null) {
            ItemStack itemStack = this.stackList[i];
            this.stackList[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public int getSizeInventory() {
        return this.stackList.length;
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.stackList[i] = itemStack;
        this.eventHandler.onCraftMatrixChanged(this);
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return true;
    }

    public InventoryCrafting(Container btSValue, int inventoryWidthValue, int j) {
        int k = inventoryWidthValue * j;
        this.stackList = new ItemStack[k];
        this.eventHandler = btSValue;
        this.inventoryWidth = inventoryWidthValue;
        this.inventoryHeight = j;
    }

    public int getHeight() {
        return this.inventoryHeight;
    }

    // region String decoding support

    // endregion

    public void clearInventory() {
        int k = Container.cR();
        int j = 0;
        int i = k;

        while (j < this.stackList.length) {
            this.stackList[j] = null;
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.stackList[i] != null) {
            if (this.stackList[i].stackSize <= j) {
                ItemStack itemStack = this.stackList[i];
                this.stackList[i] = null;
                this.eventHandler.onCraftMatrixChanged(this);
                return itemStack;
            }

            ItemStack itemStackValue = this.stackList[i].splitStack(j);
            if (this.stackList[i].stackSize == 0) {
                this.stackList[i] = null;
            }

            this.eventHandler.onCraftMatrixChanged(this);
            return itemStackValue;
        } else {
            return null;
        }
    }

    public ItemStack getStackInRowAndColumn(int i, int j) {
        return i >= 0 && i < this.inventoryWidth && j >= 0 && j <= this.inventoryHeight
                ? this.getStackInSlot(i + j * this.inventoryWidth)
                : null;
    }

    public void setField(int i, int j) {}
}
