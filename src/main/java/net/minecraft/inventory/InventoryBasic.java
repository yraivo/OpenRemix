package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.google.common.collect.Lists;

import java.util.List;

public class InventoryBasic implements IInventory {
    private ItemStack[] inventoryContents;
    private boolean hasCustomName;
    private int slotsCount;
    private String inventoryTitle;
    private List field_70480_d;

    public ItemStack getStackInSlot(int p171_0) {
        return p171_0 >= 0 && p171_0 < this.inventoryContents.length
                ? this.inventoryContents[p171_0]
                : null;
    }

    public ItemStack method_e963069bd8de390f86b7(ItemStack itemStack) {
        ItemStack itemStackValue = itemStack.copy();
        int i1 = Container.cR();
        int j = 0;
        int i = i1;

        while (j < this.slotsCount) {
            ItemStack itemStackValue2 = this.getStackInSlot(j);
            if (itemStackValue2 == null) {
                this.setInventorySlotContents(j, itemStackValue);
                this.markDirty();
                return null;
            }

            if (ItemStack.areItemsEqual(itemStackValue2, itemStackValue)) {
                int k = Math.min(this.getInventoryStackLimit(), itemStackValue2.getMaxStackSize());
                int l = Math.min(itemStackValue.stackSize, k - itemStackValue2.stackSize);
                if (l > 0) {
                    itemStackValue2.stackSize += l;
                    itemStackValue.stackSize -= l;
                    if (itemStackValue.stackSize <= 0) {
                        this.markDirty();
                        return null;
                    }
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        if (itemStackValue.stackSize != itemStack.stackSize) {
            this.markDirty();
        }

        return itemStackValue;
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return true;
    }

    public void setCustomName(String text) {
        this.hasCustomName = true;
        this.inventoryTitle = text;
    }

    public ItemStack getStackInSlotOnClosing(int p171_0) {
        if (this.inventoryContents[p171_0] != null) {
            net.minecraft.item.ItemStack itemStack = this.inventoryContents[p171_0];
            this.inventoryContents[p171_0] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public int getFieldCount() {
        return 0;
    }

    public void clearInventory() {
        int k = net.minecraft.inventory.Container.aW();
        int j = 0;
        int i = k;

        while (j < this.inventoryContents.length) {
            this.inventoryContents[j] = null;
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public void func_110134_a(IInvBasic iInvBasic) {
        if (this.field_70480_d == null) {
            this.field_70480_d = Lists.newArrayList();
        }

        this.field_70480_d.add(iInvBasic);
    }

    public boolean hasCustomName() {
        return this.hasCustomName;
    }

    public void setInventorySlotContents(int p171_0, ItemStack itemStack) {
        this.inventoryContents[p171_0] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    public String getName() {
        return this.inventoryTitle;
    }

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName()));
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public InventoryBasic(ChatComponent chatComponent, int p171_1) {
        this(chatComponent.getUnformattedText(), true, p171_1);
    }

    public InventoryBasic(String text, boolean hasCustomNameValue, int slotsCountValue) {
        this.inventoryTitle = text;
        this.hasCustomName = hasCustomNameValue;
        this.slotsCount = slotsCountValue;
        this.inventoryContents = new net.minecraft.item.ItemStack[slotsCountValue];
    }

    public int getSizeInventory() {
        return this.slotsCount;
    }

    public ItemStack decrStackSize(int p171_0, int p171_1) {
        if (this.inventoryContents[p171_0] != null) {
            if (this.inventoryContents[p171_0].stackSize <= p171_1) {
                net.minecraft.item.ItemStack itemStack = this.inventoryContents[p171_0];
                this.inventoryContents[p171_0] = null;
                this.markDirty();
                return itemStack;
            }

            net.minecraft.item.ItemStack itemStackValue = this.inventoryContents[p171_0].splitStack(p171_1);
            if (this.inventoryContents[p171_0].stackSize == 0) {
                this.inventoryContents[p171_0] = null;
            }

            this.markDirty();
            return itemStackValue;
        } else {
            return null;
        }
    }

    public void markDirty() {
        int i = Container.aW();
        if (this.field_70480_d != null) {
            int j = 0;

            while (j < this.field_70480_d.size()) {
                ((IInvBasic) this.field_70480_d.get(j)).onInventoryChanged(this);
                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public void setField(int i, int j) {}

    public int getInventoryStackLimit() {
        return 64;
    }

    public void func_110132_b(IInvBasic iInvBasic) {
        this.field_70480_d.remove(iInvBasic);
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public int getField(int i) {
        return 0;
    }
}
