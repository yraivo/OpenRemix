package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorldNameable;

public interface IInventory extends IWorldNameable {
    int getSizeInventory();

    boolean isItemValidForSlot(int i, ItemStack itemStack);

    int getFieldCount();

    void setInventorySlotContents(int i, ItemStack itemStack);

    ItemStack decrStackSize(int i, int j);

    boolean isUseableByPlayer(PlayerEntity playerEntity);

    int getInventoryStackLimit();

    void markDirty();

    ItemStack getStackInSlot(int i);

    void openInventory(PlayerEntity playerEntity);

    void closeInventory(PlayerEntity playerEntity);

    void clearInventory();

    ItemStack getStackInSlotOnClosing(int i);

    void setField(int i, int j);

    int getField(int i);
}
