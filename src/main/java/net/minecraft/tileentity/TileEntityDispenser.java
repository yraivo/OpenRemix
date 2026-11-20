package net.minecraft.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

public class TileEntityDispenser extends TileEntityLockable implements IInventory {
    protected String customName;

    private ItemStack[] stacks = new ItemStack[9];
    private static Random RNG;

    public int getSizeInventory() {
        return 9;
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.stacks[i] != null) {
            if (this.stacks[i].stackSize <= j) {
                ItemStack itemStack = this.stacks[i];
                this.stacks[i] = null;
                this.markDirty();
                return itemStack;
            }

            ItemStack itemStackValue = this.stacks[i].splitStack(j);
            if (this.stacks[i].stackSize == 0) {
                this.stacks[i] = null;
            }

            this.markDirty();
            return itemStackValue;
        } else {
            return null;
        }
    }

    public int getFieldCount() {
        return 0;
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.dispenser";
    }

    public void writeToNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.writeToNBT(compoundTag);

        NBTTagList nbtTagList = new NBTTagList();
        int i = 0;

        while (i < this.stacks.length) {
            if (this.stacks[i] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) i);
                this.stacks[i].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            i++;
        }

        compoundTag.setTag("Items", nbtTagList);
        if (this.hasCustomName()) {
            compoundTag.setString("CustomName", this.customName);
        }
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.stacks[i] != null) {
            ItemStack itemStack = this.stacks[i];
            this.stacks[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public void clearInventory() {
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < this.stacks.length) {
            this.stacks[i] = null;
            i++;
        }
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public boolean hasCustomName() {
        return this.customName != null;
    }

    public String getGuiID() {
        return "minecraft:dispenser";
    }

    public int getField(int i) {
        return 0;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    static {
        net.minecraft.tileentity.TileEntityDispenser.RNG = new Random();
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerDispenser(inventoryPlayer, this);
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public int getDispenseSlot() {
        MobSpawnerBaseLogic.GU();

        int i = -1;

        int j = 1;
        int k = 0;

        while (k < this.stacks.length) {
            if (this.stacks[k] != null && net.minecraft.tileentity.TileEntityDispenser.RNG.nextInt(j++) == 0) {
                i = k;
            }

            k++;
        }

        return i;
    }

    public int addItemStack(ItemStack itemStack) {
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < this.stacks.length) {
            if (this.stacks[i] == null || this.stacks[i].getItem() == null) {
                this.setInventorySlotContents(i, itemStack);
                return i;
            }

            i++;
        }

        return -1;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public ItemStack getStackInSlot(int i) {
        return this.stacks[i];
    }

    public void setCustomName(String text) {
        this.customName = text;
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.stacks[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    public void readFromNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.readFromNBT(compoundTag);

        NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);
        this.stacks = new ItemStack[this.getSizeInventory()];
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            int j = compoundTagValue.getByte("Slot") & 255;
            if (j >= 0 && j < this.stacks.length) {
                this.stacks[j] = ItemStack.loadItemStackFromNBT(compoundTagValue);
            }

            i++;
        }

        if (compoundTag.hasKey("CustomName", 8)) {
            this.customName = compoundTag.getString("CustomName");
        }
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public void setField(int i, int j) {}
}
