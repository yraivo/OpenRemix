package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class InventoryCraftResult implements IInventory {
    private ItemStack[] stackResult = new ItemStack[1];

    private static String b;

    public int getField(int i) {
        return 0;
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public int getSizeInventory() {
        return 1;
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public void setField(int i, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public boolean hasCustomName() {
        return false;
    }

    public String getName() {
        return b;
    }

    public void markDirty() {}

    public ItemStack decrStackSize(int i, int j) {
        if (this.stackResult[0] != null) {
            ItemStack itemStack = this.stackResult[0];
            this.stackResult[0] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return true;
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.stackResult[0] = itemStack;
    }

    static {
        b = "Result";
    }

    public void clearInventory() {
        int k = Container.cR();
        int j = 0;
        int i = k;

        while (j < this.stackResult.length) {
            this.stackResult[j] = null;
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public ItemStack getStackInSlot(int i) {
        return this.stackResult[0];
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.stackResult[0] != null) {
            ItemStack itemStack = this.stackResult[0];
            this.stackResult[0] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public int getFieldCount() {
        return 0;
    }

    public int getInventoryStackLimit() {
        return 64;
    }
}
