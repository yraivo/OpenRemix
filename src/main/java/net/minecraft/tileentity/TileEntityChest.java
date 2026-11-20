package net.minecraft.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;

public class TileEntityChest extends TileEntityLockable
        implements IInventory, IUpdatePlayerListBox {
    public TileEntityChest adjacentChestXNeg;
    private String customName;
    private int cachedChestType;

    private ItemStack[] chestContents = new ItemStack[27];
    public TileEntityChest adjacentChestZPos;

    public float prevLidAngle;
    public boolean adjacentChestChecked;
    public float lidAngle;
    public TileEntityChest adjacentChestXPos;
    private int ticksSinceSync;
    public int numPlayersUsing;
    public TileEntityChest adjacentChestZNeg;

    public void update() {
        this.checkForAdjacentChests();
        MobSpawnerBaseLogic.GU();

        int x = this.pos.getX();
        int j = this.pos.getY();

        int k = this.pos.getZ();
        this.ticksSinceSync++;
        if (!this.worldObj.isRemote
                && this.numPlayersUsing != 0
                && (this.ticksSinceSync + x + j + k) % 200 == 0) {
            this.numPlayersUsing = 0;
            float f = 5.0F;

            for (java.util.Iterator<?> __recoveryIterator171_1922 =
                            (this.worldObj.getEntitiesWithinAABB(
                                            PlayerEntity.class,
                                            new BoundingBox(
                                                    x - f, j - f, k - f, x + 1 + f, j + 1 + f,
                                                    k + 1 + f)))
                                    .iterator();
                    (__recoveryIterator171_1922).hasNext(); ) {
                PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_1922).next();
                if (playerEntity.openContainer instanceof ContainerChest) {
                    IInventory lowerChestInventory =
                            ((ContainerChest) playerEntity.openContainer).getLowerChestInventory();
                    if (lowerChestInventory == this
                            || lowerChestInventory instanceof InventoryLargeChest
                                    && ((InventoryLargeChest) lowerChestInventory)
                                            .isPartOfLargeChest(this)) {
                        this.numPlayersUsing++;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        float f1 = 0.1F;
        if (this.numPlayersUsing > 0
                && this.lidAngle == 0.0F
                && this.adjacentChestZNeg == null
                && this.adjacentChestXNeg == null) {
            double d3 = x + 0.5;
            double d1 = k + 0.5;
            if (this.adjacentChestZPos != null) {
                d1 += 0.5;
            }

            if (this.adjacentChestXPos != null) {
                d3 += 0.5;
            }

            this.worldObj.playSoundEffect(
                    d3,
                    j + 0.5,
                    d1,
                    "random.chestopen",
                    0.5F,
                    this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F
                || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2;
            {
                f2 = this.lidAngle;
                if (this.numPlayersUsing > 0) {
                    this.lidAngle += f1;

                } else {

                    this.lidAngle -= f1;
                }
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f3 = 0.5F;
            if (this.lidAngle < f3
                    && f2 >= f3
                    && this.adjacentChestZNeg == null
                    && this.adjacentChestXNeg == null) {
                double d2 = x + 0.5;
                double d0 = k + 0.5;
                if (this.adjacentChestZPos != null) {
                    d0 += 0.5;
                }

                if (this.adjacentChestXPos != null) {
                    d2 += 0.5;
                }

                this.worldObj.playSoundEffect(
                        d2,
                        j + 0.5,
                        d0,
                        "random.chestclosed",
                        0.5F,
                        this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }


    public int getField(int i) {
        return 0;
    }

    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = true;
            this.adjacentChestXNeg = this.getAdjacentChest(net.minecraft.util.Facing.WEST);
            this.adjacentChestXPos = this.getAdjacentChest(net.minecraft.util.Facing.EAST);
            this.adjacentChestZNeg = this.getAdjacentChest(net.minecraft.util.Facing.NORTH);
            this.adjacentChestZPos = this.getAdjacentChest(net.minecraft.util.Facing.SOUTH);
        }
    }

    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }

    public void openInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            this.numPlayersUsing++;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.offsetDown(), this.getBlockType());
        }
    }

    public int getChestType() {
        if (this.cachedChestType == -1) {
            if (this.worldObj == null
                    || !(this.getBlockType() instanceof net.minecraft.block.BlockChest)) {
                return 0;
            }

            this.cachedChestType = ((net.minecraft.block.BlockChest) this.getBlockType()).chestType;
        }

        return this.cachedChestType;
    }

    public void setInventorySlotContents(int p171_0, ItemStack itemStack) {
        this.chestContents[p171_0] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getFieldCount() {
        return 0;
    }

    public void closeInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()
                && this.getBlockType() instanceof net.minecraft.block.BlockChest) {
            this.numPlayersUsing--;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.offsetDown(), this.getBlockType());
        }
    }

    private void func_174910_a(TileEntityChest tileEntityChest, Facing facing) {
        MobSpawnerBaseLogic.GU();

        if (tileEntityChest.isInvalid()) {
            this.adjacentChestChecked = false;

            return;
        }

        if (this.adjacentChestChecked) {
            switch (facing) {
                case NORTH:
                    if (this.adjacentChestZNeg == tileEntityChest) {
                        break;
                    }

                    this.adjacentChestChecked = false;

                    break;

                case SOUTH:
                    if (this.adjacentChestZPos == tileEntityChest) {
                        break;
                    }

                    this.adjacentChestChecked = false;

                    break;

                case EAST:
                    if (this.adjacentChestXPos == tileEntityChest) {
                        break;
                    }

                    this.adjacentChestChecked = false;

                    break;

                case WEST:
                    if (this.adjacentChestXNeg != tileEntityChest) {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }

    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public TileEntityChest() {
        this.cachedChestType = -1;
    }

    private boolean isChestAt(BlockPos blockPos) {
        if (this.worldObj == null) {
            return false;
        }

        net.minecraft.block.Block block = this.worldObj.getBlockState(blockPos).getBlock();
        return block instanceof net.minecraft.block.BlockChest
                && ((net.minecraft.block.BlockChest) block).chestType == this.getChestType();
    }

    public TileEntityChest(int cachedChestTypeValue) {
        this.cachedChestType = cachedChestTypeValue;
    }

    public int getSizeInventory() {
        return 27;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerChest(inventoryPlayer, this, playerEntity);
    }

    public boolean receiveClientEvent(int p171_0, int numPlayersUsingValue) {
        if (p171_0 == 1) {
            this.numPlayersUsing = numPlayersUsingValue;
            return true;
        } else {
            return super.receiveClientEvent(p171_0, numPlayersUsingValue);
        }
    }

    protected TileEntityChest getAdjacentChest(Facing facing) {
        net.minecraft.util.BlockPos blockPos = this.pos.offset(facing);
        if (this.isChestAt(blockPos)) {
            net.minecraft.tileentity.TileEntity tileEntity = this.worldObj.getTileEntity(blockPos);
            if (tileEntity instanceof net.minecraft.tileentity.TileEntityChest) {
                net.minecraft.tileentity.TileEntityChest tileEntityChest =
                        (net.minecraft.tileentity.TileEntityChest) tileEntity;
                tileEntityChest.func_174910_a(this, facing.getOpposite());
                return tileEntityChest;
            }
        }

        return null;
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.chest";
    }

    public void setField(int i, int j) {}

    public ItemStack decrStackSize(int p171_0, int p171_1) {
        if (this.chestContents[p171_0] != null) {
            if (this.chestContents[p171_0].stackSize <= p171_1) {
                net.minecraft.item.ItemStack itemStack = this.chestContents[p171_0];
                this.chestContents[p171_0] = null;
                this.markDirty();
                return itemStack;
            }

            net.minecraft.item.ItemStack itemStackValue =
                    this.chestContents[p171_0].splitStack(p171_1);
            if (this.chestContents[p171_0].stackSize == 0) {
                this.chestContents[p171_0] = null;
            }

            this.markDirty();
            return itemStackValue;
        } else {
            return null;
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.readFromNBT(compoundTag);

        NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        if (compoundTag.hasKey("CustomName", 8)) {
            this.customName = compoundTag.getString("CustomName");
        }

        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            int j = compoundTagValue.getByte("Slot") & 255;
            if (j >= 0 && j < this.chestContents.length) {
                this.chestContents[j] = ItemStack.loadItemStackFromNBT(compoundTagValue);
            }

            i++;
        }
    }

    public String getGuiID() {
        return "minecraft:chest";
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }

    public void setCustomName(String text) {
        this.customName = text;
    }

    public ItemStack getStackInSlotOnClosing(int p171_0) {
        if (this.chestContents[p171_0] != null) {
            net.minecraft.item.ItemStack itemStack = this.chestContents[p171_0];
            this.chestContents[p171_0] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public void clearInventory() {
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < this.chestContents.length) {
            this.chestContents[i] = null;
            i++;
        }
    }

    public void writeToNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.writeToNBT(compoundTag);
        NBTTagList nbtTagList = new NBTTagList();

        int i = 0;

        while (i < this.chestContents.length) {
            if (this.chestContents[i] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) i);
                this.chestContents[i].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            i++;
        }

        compoundTag.setTag("Items", nbtTagList);
        if (this.hasCustomName()) {
            compoundTag.setString("CustomName", this.customName);
        }
    }

    public ItemStack getStackInSlot(int p171_0) {
        return this.chestContents[p171_0];
    }
}
