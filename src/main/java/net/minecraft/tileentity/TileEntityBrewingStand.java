package net.minecraft.tileentity;

import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.Facing;

import java.util.Arrays;
import java.util.List;

public class TileEntityBrewingStand extends TileEntityLockable
        implements ISidedInventory, IUpdatePlayerListBox {
    private Item ingredientID;
    private int brewTime;

    private static int[] inputSlots;
    private ItemStack[] brewingItemStacks = new ItemStack[4];
    private static int[] outputSlots;
    private String customName;
    private boolean[] filledSlots;

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerBrewingStand(inventoryPlayer, this);
    }

    public boolean[] Sk() {
        MobSpawnerBaseLogic.GU();

        boolean[] aboolean = new boolean[3];
        int i = 0;

        while (i < 3) {
            if (this.brewingItemStacks[i] != null) {
                aboolean[i] = true;
            }

            i++;
        }

        return aboolean;
    }

    public void setName(String text) {
        this.customName = text;
    }

    public void setField(int i, int j) {
        switch (i) {
            case 0:
                this.brewTime = j;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        if (i >= 0 && i < this.brewingItemStacks.length) {
            this.brewingItemStacks[i] = itemStack;
        }
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (i >= 0 && i < this.brewingItemStacks.length) {
            ItemStack itemStack = this.brewingItemStacks[i];
            this.brewingItemStacks[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        MobSpawnerBaseLogic.GU();

        compoundTag.setShort("BrewTime", (short) this.brewTime);
        NBTTagList nbtTagList = new NBTTagList();

        int i = 0;

        while (i < this.brewingItemStacks.length) {
            if (this.brewingItemStacks[i] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) i);
                this.brewingItemStacks[i].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            i++;
        }

        compoundTag.setTag("Items", nbtTagList);
        if (this.hasCustomName()) {
            compoundTag.setString("CustomName", this.customName);
        }
    }

    public boolean canInsertItem(int i, ItemStack itemStack, Facing facing) {
        return this.isItemValidForSlot(i, itemStack);
    }

    public void readFromNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.readFromNBT(compoundTag);

        NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            byte b0 = compoundTagValue.getByte("Slot");
            if (b0 >= 0 && b0 < this.brewingItemStacks.length) {
                this.brewingItemStacks[b0] = ItemStack.loadItemStackFromNBT(compoundTagValue);
            }

            i++;
        }

        this.brewTime = compoundTag.getShort("BrewTime");
        if (compoundTag.hasKey("CustomName", 8)) {
            this.customName = compoundTag.getString("CustomName");
        }
    }

    public ItemStack decrStackSize(int i, int j) {
        if (i >= 0 && i < this.brewingItemStacks.length) {
            ItemStack itemStack = this.brewingItemStacks[i];
            this.brewingItemStacks[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    private void dn() {
        MobSpawnerBaseLogic.GU();

        if (this.jvm171_f_7d05c761df11e3()) {
            ItemStack itemStack = this.brewingItemStacks[3];
            int i = 0;

            while (i < 3) {
                if (this.brewingItemStacks[i] != null
                        && this.brewingItemStacks[i].getItem() == Items.potionitem) {
                    int j = this.brewingItemStacks[i].getItemDamage();
                    int k = this.getPotionResult(j, itemStack);
                    List list = Items.potionitem.getEffects(j);
                    List listValue = Items.potionitem.getEffects(k);
                    if ((j <= 0 || list != listValue)
                            && (list == null || !list.equals(listValue) && listValue != null)) {
                        if (!(j == k)) {

                            this.brewingItemStacks[i].setItemDamage(k);
                        }
                    } else {

                        if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
                            this.brewingItemStacks[i].setItemDamage(k);
                        }
                    }
                }

                i++;
            }

            if (itemStack.getItem().hasContainerItem()) {
                this.brewingItemStacks[3] = new ItemStack(itemStack.getItem().getContainerItem());

                return;
            }

            this.brewingItemStacks[3].stackSize--;
            if (this.brewingItemStacks[3].stackSize <= 0) {
                this.brewingItemStacks[3] = null;
            }
        }
    }

    public void update() {
        Entity[] entityArray;
        {
            entityArray = MobSpawnerBaseLogic.GU();
            if (this.brewTime > 0) {
                this.brewTime--;
                if (this.brewTime == 0) {
                    this.dn();
                    this.markDirty();

                } else {

                    if (!this.jvm171_f_7d05c761df11e3()) {
                        this.brewTime = 0;
                        this.markDirty();

                    } else {

                        if (!(this.ingredientID == this.brewingItemStacks[3].getItem())) {

                            this.brewTime = 0;
                            this.markDirty();
                        }
                    }
                }
            } else {

                if (this.jvm171_f_7d05c761df11e3()) {
                    this.brewTime = 400;
                    this.ingredientID = this.brewingItemStacks[3].getItem();
                }
            }
        }

        if (!this.worldObj.isRemote) {
            boolean[] aboolean = this.Sk();
            if (!Arrays.equals(aboolean, this.filledSlots)) {
                this.filledSlots = aboolean;
                BlockState blockState = this.worldObj.getBlockState(this.getPos());
                if (!(blockState.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }

                int i = 0;

                while (i < BlockBrewingStand.BOTTLE_PROPS.length) {
                    blockState =
                            blockState.withProperty(BlockBrewingStand.BOTTLE_PROPS[i], aboolean[i]);
                    i++;
                }

                this.worldObj.setBlockState(this.pos, blockState, 2);
            }
        }
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.brewing";
    }

    public int getFieldCount() {
        return 1;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == 3
                ? itemStack.getItem().isPotionIngredient(itemStack)
                : itemStack.getItem() == Items.potionitem
                        || itemStack.getItem() == Items.glass_bottle;
    }

    public boolean canExtractItem(int i, ItemStack itemStack, Facing facing) {
        return true;
    }

    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public int getSizeInventory() {
        return this.brewingItemStacks.length;
    }

    public int getField(int i) {
        switch (i) {
            case 0:
                return this.brewTime;
            default:
                return 0;
        }
    }

    private int getPotionResult(int i, ItemStack itemStack) {
        return itemStack == null
                ? i
                : (itemStack.getItem().isPotionIngredient(itemStack)
                        ? PotionHelper.applyIngredient(
                                i, itemStack.getItem().getPotionEffect(itemStack))
                        : i);
    }

    public void clearInventory() {
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < this.brewingItemStacks.length) {
            this.brewingItemStacks[i] = null;
            i++;
        }
    }

    public String getGuiID() {
        return "minecraft:brewing_stand";
    }

    static {
        net.minecraft.tileentity.TileEntityBrewingStand.inputSlots = new int[] {3};
        net.minecraft.tileentity.TileEntityBrewingStand.outputSlots = new int[] {0, 1, 2};
    }

    public int[] getSlotsForFace(Facing facing) {
        return facing == Facing.UP
                ? net.minecraft.tileentity.TileEntityBrewingStand.inputSlots
                : net.minecraft.tileentity.TileEntityBrewingStand.outputSlots;
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public ItemStack getStackInSlot(int i) {
        return i >= 0 && i < this.brewingItemStacks.length ? this.brewingItemStacks[i] : null;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    private boolean jvm171_f_7d05c761df11e3() {
        MobSpawnerBaseLogic.GU();

        if (this.brewingItemStacks[3] != null && this.brewingItemStacks[3].stackSize > 0) {
            ItemStack itemStack = this.brewingItemStacks[3];
            if (!itemStack.getItem().isPotionIngredient(itemStack)) {
                return false;
            }

            byte b0 = 0;
            int i = 0;

            while (i < 3) {
                if (this.brewingItemStacks[i] != null
                        && this.brewingItemStacks[i].getItem() == Items.potionitem) {
                    int j = this.brewingItemStacks[i].getItemDamage();
                    int k = this.getPotionResult(j, itemStack);
                    if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
                        b0 = 1;

                        break;
                    }

                    List list = Items.potionitem.getEffects(j);
                    List listValue = Items.potionitem.getEffects(k);
                    if ((j <= 0 || list != listValue)
                            && (list == null || !list.equals(listValue) && listValue != null)
                            && j != k) {
                        b0 = 1;

                        break;
                    }
                }

                i++;
            }

            return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
        } else {
            return false;
        }
    }
}
