package net.minecraft.entity.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
    private ItemStack[] minecartContainerItems = new ItemStack[36];

    private boolean dropContentsWhenDead = true;

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public EntityMinecartContainer(World worldValue, double p171_1, double p171_2, double p171_3) {
        super(worldValue, p171_1, p171_2, p171_3);
    }

    public String getName() {
        return this.hasCustomName() ? this.getCustomNameTag() : "container.minecart";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }

    public int getField(int i) {
        return 0;
    }

    protected void applyDrag() {
        int i = 15 - net.minecraft.inventory.Container.calcRedstoneFromInventory(this);
        float f = 0.98F + i * 0.001F;
        this.motionX *= f;
        this.motionY *= 0.0;
        this.motionZ *= f;
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];
        boolean flag = flag1;
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            int j = compoundTagValue.getByte("Slot") & 255;
            if (j >= 0 && j < this.minecartContainerItems.length) {
                this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(compoundTagValue);
            }

            i++;
            if (!flag) {
                break;
            }
        }
    }

    public void setInventorySlotContents(int p171_0, ItemStack itemStack) {
        this.minecartContainerItems[p171_0] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void setDead() {
        if (this.dropContentsWhenDead) {
            net.minecraft.inventory.InventoryHelper.func_180176_a(this.world, this, this);
        }

        super.setDead();
    }

    public int getFieldCount() {
        return 0;
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.isDead ? false : playerEntity.getDistanceSqToEntity(this) <= 64.0;
    }

    public void markDirty() {}

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        NBTTagList nbtTagList = new NBTTagList();
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;

        while (i < this.minecartContainerItems.length) {
            if (this.minecartContainerItems[i] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) i);
                this.minecartContainerItems[i].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            i++;
            if (!flag) {
                break;
            }
        }

        compoundTag.setTag("Items", nbtTagList);
    }

    public void setField(int i, int j) {}

    public void openInventory(PlayerEntity playerEntity) {}

    public EntityMinecartContainer(World worldValue) {
        super(worldValue);
    }

    public void clearInventory() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;

        while (i < this.minecartContainerItems.length) {
            this.minecartContainerItems[i] = null;
            i++;
            if (!flag) {
                break;
            }
        }
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public boolean interactFirst(PlayerEntity playerEntity) {
        if (!this.world.isRemote) {
            playerEntity.displayGUIChest(this);
        }

        return true;
    }


    public ItemStack decrStackSize(int p171_0, int p171_1) {
        if (this.minecartContainerItems[p171_0] != null) {
            if (this.minecartContainerItems[p171_0].stackSize <= p171_1) {
                net.minecraft.item.ItemStack itemStack = this.minecartContainerItems[p171_0];
                this.minecartContainerItems[p171_0] = null;
                return itemStack;
            }

            net.minecraft.item.ItemStack itemStackValue = this.minecartContainerItems[p171_0].splitStack(p171_1);
            if (this.minecartContainerItems[p171_0].stackSize == 0) {
                this.minecartContainerItems[p171_0] = null;
            }

            return itemStackValue;
        } else {
            return null;
        }
    }

    public void travelToDimension(int p171_0) {
        this.dropContentsWhenDead = false;
        super.travelToDimension(p171_0);
    }

    public ItemStack getStackInSlotOnClosing(int p171_0) {
        if (this.minecartContainerItems[p171_0] != null) {
            net.minecraft.item.ItemStack itemStack = this.minecartContainerItems[p171_0];
            this.minecartContainerItems[p171_0] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlot(int p171_0) {
        return this.minecartContainerItems[p171_0];
    }

    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        net.minecraft.inventory.InventoryHelper.func_180176_a(this.world, this, this);
    }

    public void setLockCode(LockCode lockCode) {}

    public boolean isLocked() {
        return false;
    }
}
