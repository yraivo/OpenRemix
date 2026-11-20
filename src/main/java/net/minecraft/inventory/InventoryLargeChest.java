package net.minecraft.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer {
    private String name;
    private ILockableContainer lowerChest;
    private ILockableContainer upperChest;

    public InventoryLargeChest(
            String text,
            ILockableContainer iLockableContainer,
            ILockableContainer iLockableContainerValue) {
        super();
        int initialFlowToken = Container.cR();

        int j = initialFlowToken;

        int i = j;
        this.name = text;
        if (iLockableContainer == null) {
            iLockableContainer = iLockableContainerValue;
        }

        if (iLockableContainerValue == null) {
            iLockableContainerValue = iLockableContainer;
        }

        this.upperChest = iLockableContainer;
        this.lowerChest = iLockableContainerValue;
        if (iLockableContainer.isLocked()) {
            iLockableContainerValue.setLockCode(iLockableContainer.getLockCode());
            if (i == 0) {
                return;
            }
        }

        if (iLockableContainerValue.isLocked()) {
            iLockableContainer.setLockCode(iLockableContainerValue.getLockCode());
        }
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        return i >= this.upperChest.getSizeInventory()
                ? this.lowerChest.getStackInSlotOnClosing(i - this.upperChest.getSizeInventory())
                : this.upperChest.getStackInSlotOnClosing(i);
    }

    public boolean isPartOfLargeChest(IInventory iInventory) {
        return this.upperChest == iInventory || this.lowerChest == iInventory;
    }

    public ItemStack decrStackSize(int p171_0, int p171_1) {
        return p171_0 >= this.upperChest.getSizeInventory()
                ? this.lowerChest.decrStackSize(p171_0 - this.upperChest.getSizeInventory(), p171_1)
                : this.upperChest.decrStackSize(p171_0, p171_1);
    }

    public void setLockCode(LockCode lockCode) {
        this.upperChest.setLockCode(lockCode);
        this.lowerChest.setLockCode(lockCode);
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerChest(inventoryPlayer, this, playerEntity);
    }

    public void openInventory(PlayerEntity playerEntity) {
        this.upperChest.openInventory(playerEntity);
        this.lowerChest.openInventory(playerEntity);
    }

    public int getFieldCount() {
        return 0;
    }

    public void setInventorySlotContents(int p171_0, ItemStack itemStack) {
        int j = net.minecraft.inventory.Container.aW();
        if (p171_0 >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(
                    p171_0 - this.upperChest.getSizeInventory(), itemStack);
            if (j != 0) {
                return;
            }
        }

        this.upperChest.setInventorySlotContents(p171_0, itemStack);
    }

    public String getName() {
        return this.upperChest.hasCustomName()
                ? this.upperChest.getName()
                : (this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name);
    }

    public void closeInventory(PlayerEntity playerEntity) {
        this.upperChest.closeInventory(playerEntity);
        this.lowerChest.closeInventory(playerEntity);
    }

    public int getField(int i) {
        return 0;
    }

    public void clearInventory() {
        this.upperChest.clearInventory();
        this.lowerChest.clearInventory();
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public void markDirty() {
        this.upperChest.markDirty();
        this.lowerChest.markDirty();
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.upperChest.isUseableByPlayer(playerEntity)
                && this.lowerChest.isUseableByPlayer(playerEntity);
    }

    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }

    public String getGuiID() {
        return this.upperChest.getGuiID();
    }

    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }

    public boolean hasCustomName() {
        return this.upperChest.hasCustomName() || this.lowerChest.hasCustomName();
    }

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public LockCode getLockCode() {
        return this.upperChest.getLockCode();
    }

    public void setField(int i, int j) {}

    public boolean isLocked() {
        return this.upperChest.isLocked() || this.lowerChest.isLocked();
    }

    public ItemStack getStackInSlot(int i) {
        return i >= this.upperChest.getSizeInventory()
                ? this.lowerChest.getStackInSlot(i - this.upperChest.getSizeInventory())
                : this.upperChest.getStackInSlot(i);
    }
}
