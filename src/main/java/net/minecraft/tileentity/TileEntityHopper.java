package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityHopper extends TileEntityLockable implements IUpdatePlayerListBox, IHopper {

    private int transferCooldown;
    private String customName;

    private ItemStack[] inventory = new ItemStack[5];

    private boolean transferItemsOut() {
        MobSpawnerBaseLogic.GU();
        IInventory iInventory = this.getInventoryForHopperTransfer();
        if (iInventory == null) {
            return false;
        }

        Facing facing = BlockHopper.getFacing(this.getBlockMetadata()).getOpposite();
        if (this.isInventoryFull(iInventory, facing)) {
            return false;
        }

        for (int i = 0; i < this.getSizeInventory(); i++) {
            if (this.getStackInSlot(i) != null) {
                ItemStack itemStack = this.getStackInSlot(i).copy();
                ItemStack itemStackValue =
                        putStackInInventoryAllSlots(iInventory, this.decrStackSize(i, 1), facing);
                if (itemStackValue == null || itemStackValue.stackSize == 0) {
                    iInventory.markDirty();
                    return true;
                }

                this.setInventorySlotContents(i, itemStack);
            }
            break;
        }

        return false;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public static boolean putDropInInventoryAllSlots(IInventory iInventory, EntityItem entityItem) {
        MobSpawnerBaseLogic.GU();

        byte b0 = 0;

        if (entityItem == null) {
            return false;
        }

        ItemStack itemStack = entityItem.getEntityItem().copy();
        ItemStack itemStackValue =
                putStackInInventoryAllSlots(iInventory, itemStack, (Facing) null);
        if (itemStackValue != null && itemStackValue.stackSize != 0) {
            entityItem.setEntityItemStack(itemStackValue);

            return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
        }

        b0 = 1;
        entityItem.setDead();
        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    private boolean isInventoryFull(IInventory iInventory, Facing facing) {
        MobSpawnerBaseLogic.GU();

        if (iInventory instanceof ISidedInventory) {
            ISidedInventory iSidedInventory = (ISidedInventory) iInventory;
            int[] aint = iSidedInventory.getSlotsForFace(facing);
            int i = 0;

            while (i < aint.length) {
                ItemStack itemStack = iSidedInventory.getStackInSlot(aint[i]);
                if (itemStack == null || itemStack.stackSize != itemStack.getMaxStackSize()) {
                    return false;
                }

                i++;
            }

            return true;
        }

        int j = iInventory.getSizeInventory();
        int k = 0;

        while (k < j) {
            ItemStack itemStackValue = iInventory.getStackInSlot(k);
            if (itemStackValue == null
                    || itemStackValue.stackSize != itemStackValue.getMaxStackSize()) {
                return false;
            }

            k++;
        }

        return true;
    }

    public double getZPos() {
        return this.pos.getZ();
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public boolean isOnTransferCooldown() {
        return this.transferCooldown > 0;
    }

    public static EntityItem method_57de3f07001fd6836c3c(
            World world, double d0, double d1, double d2) {
        List entitiesWithinAABB =
                world.getEntitiesWithinAABB(
                        EntityItem.class,
                        new BoundingBox(d0, d1, d2, d0 + 1.0, d1 + 1.0, d2 + 1.0),
                        IEntitySelector.selectAnything);
        return entitiesWithinAABB.size() > 0 ? (EntityItem) entitiesWithinAABB.get(0) : null;
    }

    public void clearInventory() {
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < this.inventory.length) {
            this.inventory[i] = null;
            i++;
        }
    }

    public boolean mayTransfer() {
        return this.transferCooldown <= 1;
    }

    private static ItemStack insertStack(
            IInventory iInventory, ItemStack itemStack, int i, Facing facing) {
        ItemStack itemStackValue = iInventory.getStackInSlot(i);
        if (canInsertItemInSlot(iInventory, itemStack, i, facing)) {
            byte b0 = 0;
            if (itemStackValue == null) {
                iInventory.setInventorySlotContents(i, itemStack);
                itemStack = null;
                b0 = 1;
            } else if (canCombine(itemStackValue, itemStack)) {
                int j = itemStack.getMaxStackSize() - itemStackValue.stackSize;
                int k = Math.min(itemStack.stackSize, j);
                itemStack.stackSize -= k;
                itemStackValue.stackSize += k;
                b0 = (byte) (k > 0 ? 1 : 0);
            }

            if (b0 != 0) {
                if (iInventory instanceof TileEntityHopper) {
                    TileEntityHopper tileEntityHopper = (TileEntityHopper) iInventory;
                    if (tileEntityHopper.mayTransfer()) {
                        tileEntityHopper.setTransferCooldown(8);
                    }

                    iInventory.markDirty();
                }

                iInventory.markDirty();
            }
        }

        return itemStack;
    }

    public static ItemStack putStackInInventoryAllSlots(
            IInventory iInventory, ItemStack itemStack, Facing facing) {
        {
            MobSpawnerBaseLogic.GU();

            if (iInventory instanceof ISidedInventory && facing != null) {
                ISidedInventory iSidedInventory = (ISidedInventory) iInventory;
                int[] aint = iSidedInventory.getSlotsForFace(facing);
                int i = 0;

                while (i < aint.length && itemStack != null && itemStack.stackSize > 0) {
                    itemStack = insertStack(iInventory, itemStack, aint[i], facing);
                    i++;
                }

            } else {

                int j = iInventory.getSizeInventory();
                int k = 0;

                while (k < j && itemStack != null && itemStack.stackSize > 0) {
                    itemStack = insertStack(iInventory, itemStack, k, facing);
                    k++;
                }
            }
        }

        if (itemStack != null && itemStack.stackSize == 0) {
            itemStack = null;
        }

        return itemStack;
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.hopper";
    }

    private IInventory getInventoryForHopperTransfer() {
        Facing facing = BlockHopper.getFacing(this.getBlockMetadata());
        return getInventoryAtPosition(
                this.getWorld(),
                this.pos.getX() + facing.getFrontOffsetX(),
                this.pos.getY() + facing.getFrontOffsetY(),
                this.pos.getZ() + facing.getFrontOffsetZ());
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.inventory[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public static IInventory getHopperInventory(IHopper iHopper) {
        return getInventoryAtPosition(
                iHopper.getWorld(), iHopper.getXPos(), iHopper.getYPos() + 1.0, iHopper.getZPos());
    }

    public int getFieldCount() {
        return 0;
    }

    private static boolean canExtractItemFromSlot(
            IInventory iInventory, ItemStack itemStack, int i, Facing facing) {
        return !(iInventory instanceof ISidedInventory)
                || ((ISidedInventory) iInventory).canExtractItem(i, itemStack, facing);
    }

    private boolean isEmpty() {
        MobSpawnerBaseLogic.GU();

        ItemStack[] bBuValue = this.inventory;

        for (ItemStack itemStack : bBuValue) {
            if (itemStack != null) {
                return false;
            }
        }

        return true;
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.inventory[i] != null) {
            if (this.inventory[i].stackSize <= j) {
                ItemStack itemStack = this.inventory[i];
                this.inventory[i] = null;
                return itemStack;
            }

            ItemStack itemStackValue = this.inventory[i].splitStack(j);
            if (this.inventory[i].stackSize == 0) {
                this.inventory[i] = null;
            }

            return itemStackValue;
        } else {
            return null;
        }
    }

    private boolean isFull() {
        ItemStack[] bBuValue = this.inventory;
        MobSpawnerBaseLogic.GU();

        int length = bBuValue.length;
        int j = 0;

        while (j < length) {
            ItemStack itemStack = bBuValue[j];
            if (itemStack == null || itemStack.stackSize != itemStack.getMaxStackSize()) {
                return false;
            }

            j++;
        }

        return true;
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.inventory[i] != null) {
            ItemStack itemStack = this.inventory[i];
            this.inventory[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public void setTransferCooldown(int transferCooldownValue) {
        this.transferCooldown = transferCooldownValue;
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    private static boolean pullItemFromSlot(
            IHopper iHopper, IInventory iInventory, int i, Facing facing) {
        ItemStack itemStack = iInventory.getStackInSlot(i);
        if (itemStack != null && canExtractItemFromSlot(iInventory, itemStack, i, facing)) {
            ItemStack itemStackValue = itemStack.copy();
            ItemStack itemStackValue2 =
                    putStackInInventoryAllSlots(
                            iHopper, iInventory.decrStackSize(i, 1), (Facing) null);
            if (itemStackValue2 == null || itemStackValue2.stackSize == 0) {
                iInventory.markDirty();
                return true;
            }

            iInventory.setInventorySlotContents(i, itemStackValue);
        }

        return false;
    }

    public void markDirty() {
        super.markDirty();
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        MobSpawnerBaseLogic.GU();

        NBTTagList nbtTagList = new NBTTagList();

        int i = 0;

        while (i < this.inventory.length) {
            if (this.inventory[i] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            i++;
        }

        compoundTag.setTag("Items", nbtTagList);
        compoundTag.setInteger("TransferCooldown", this.transferCooldown);
        if (this.hasCustomName()) {
            compoundTag.setString("CustomName", this.customName);
        }
    }

    public int getField(int i) {
        return 0;
    }

    public boolean jvm171_f_9c71f79e052446() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            if (!this.isOnTransferCooldown()
                    && BlockHopper.getActiveStateFromMetadata(this.getBlockMetadata())) {
                byte b0 = 0;
                if (!this.isEmpty()) {
                    b0 = remix.compat.compiler.JvmBooleanSlots.toByte(this.transferItemsOut());
                }

                if (!this.isFull()) {
                    b0 = (byte) (!captureDroppedItems(this) && b0 == 0 ? 0 : 1);
                }

                if (b0 != 0) {
                    this.setTransferCooldown(8);
                    this.markDirty();
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public double getYPos() {
        return this.pos.getY();
    }

    public static boolean captureDroppedItems(IHopper iHopper) {
        MobSpawnerBaseLogic.GU();

        IInventory iInventory = getHopperInventory(iHopper);

        if (iInventory != null) {
            Facing facing = Facing.DOWN;
            if (isInventoryEmpty(iInventory, facing)) {
                return false;
            }

            {
                if (iInventory instanceof ISidedInventory) {
                    ISidedInventory iSidedInventory = (ISidedInventory) iInventory;
                    int[] aint = iSidedInventory.getSlotsForFace(facing);
                    int i = 0;

                    while (i < aint.length) {
                        if (pullItemFromSlot(iHopper, iInventory, aint[i], facing)) {
                            return true;
                        }

                        i++;
                    }

                } else {

                    int j = iInventory.getSizeInventory();
                    int k = 0;

                    while (k < j) {
                        if (pullItemFromSlot(iHopper, iInventory, k, facing)) {
                            return true;
                        }

                        k++;
                    }
                }
            }

            return false;
        }

        EntityItem entityItem =
                method_57de3f07001fd6836c3c(
                        iHopper.getWorld(),
                        iHopper.getXPos(),
                        iHopper.getYPos() + 1.0,
                        iHopper.getZPos());
        return entityItem != null ? putDropInInventoryAllSlots(iHopper, entityItem) : false;
    }

    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    public void readFromNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.readFromNBT(compoundTag);

        NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        if (compoundTag.hasKey("CustomName", 8)) {
            this.customName = compoundTag.getString("CustomName");
        }

        this.transferCooldown = compoundTag.getInteger("TransferCooldown");
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            byte b0 = compoundTagValue.getByte("Slot");
            if (b0 >= 0 && b0 < this.inventory.length) {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(compoundTagValue);
            }

            i++;
        }
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public void update() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            this.transferCooldown--;
            if (!this.isOnTransferCooldown()) {
                this.setTransferCooldown(0);
                this.jvm171_f_9c71f79e052446();
            }
        }
    }

    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    private static boolean canInsertItemInSlot(
            IInventory iInventory, ItemStack itemStack, int i, Facing facing) {
        return !iInventory.isItemValidForSlot(i, itemStack)
                ? false
                : !(iInventory instanceof ISidedInventory)
                        || ((ISidedInventory) iInventory).canInsertItem(i, itemStack, facing);
    }

    public void setField(int i, int j) {}

    public void setCustomName(String text) {
        this.customName = text;
    }

    private static boolean canCombine(ItemStack itemStack, ItemStack itemStackValue) {
        return itemStack.getItem() != itemStackValue.getItem()
                ? false
                : (itemStack.getItemDamage() != itemStackValue.getItemDamage()
                        ? false
                        : (itemStack.stackSize > itemStack.getMaxStackSize()
                                ? false
                                : ItemStack.areItemStackTagsEqual(itemStack, itemStackValue)));
    }

    public String getGuiID() {
        return "minecraft:hopper";
    }

    public TileEntityHopper() {
        this.transferCooldown = -1;
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerHopper(inventoryPlayer, this, playerEntity);
    }

    public double getXPos() {
        return this.pos.getX();
    }


    private static boolean isInventoryEmpty(IInventory iInventory, Facing facing) {
        MobSpawnerBaseLogic.GU();

        if (iInventory instanceof ISidedInventory) {
            ISidedInventory iSidedInventory = (ISidedInventory) iInventory;
            int[] aint = iSidedInventory.getSlotsForFace(facing);
            int i = 0;

            while (i < aint.length) {
                if (iSidedInventory.getStackInSlot(aint[i]) != null) {
                    return false;
                }

                i++;
            }

            return true;
        }

        int j = iInventory.getSizeInventory();
        int k = 0;

        while (k < j) {
            if (iInventory.getStackInSlot(k) != null) {
                return false;
            }

            k++;
        }

        return true;
    }

    public static IInventory getInventoryAtPosition(World world, double d0, double d1, double d2) {
        Object object = null;
        int i = MathHelper.floor(d0);
        int j = MathHelper.floor(d1);
        int k = MathHelper.floor(d2);
        BlockPos blockPos = new BlockPos(i, j, k);
        TileEntity tileEntity = world.getTileEntity(new BlockPos(i, j, k));
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
            if (object instanceof TileEntityChest) {
                Block block = world.getBlockState(new BlockPos(i, j, k)).getBlock();
                if (block instanceof BlockChest) {
                    object = ((BlockChest) block).getLockableContainer(world, blockPos);
                }
            }
        }

        if (object == null) {
            List entitiesInAABBexcluding =
                    world.getEntitiesInAABBexcluding(
                            (Entity) null,
                            new BoundingBox(d0, d1, d2, d0 + 1.0, d1 + 1.0, d2 + 1.0),
                            IEntitySelector.selectInventories);
            if (entitiesInAABBexcluding.size() > 0) {
                object =
                        (IInventory)
                                entitiesInAABBexcluding.get(
                                        world.rand.nextInt(entitiesInAABBexcluding.size()));
            }
        }

        return (IInventory) object;
    }

    public int getSizeInventory() {
        return this.inventory.length;
    }
}
