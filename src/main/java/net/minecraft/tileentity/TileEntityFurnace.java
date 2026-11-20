package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;

public class TileEntityFurnace extends TileEntityLockable
        implements ISidedInventory, IUpdatePlayerListBox {
    private static int[] slotsTop;
    private int currentItemBurnTime;

    private String furnaceCustomName;
    private static int[] slotsBottom;
    private static int[] slotsSides;
    private int totalCookTime;

    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    private int cookTime;
    private int furnaceBurnTime;

    public static boolean isBurning(IInventory iInventory) {
        return iInventory.getField(0) > 0;
    }

    public void setCustomInventoryName(String text) {
        this.furnaceCustomName = text;
    }

    public void setInventorySlotContents(int p171_0, ItemStack itemStack) {
        boolean flag =
                (itemStack != null
                        && itemStack.isItemEqual(this.furnaceItemStacks[p171_0])
                        && net.minecraft.item.ItemStack.areItemStackTagsEqual(
                                itemStack, this.furnaceItemStacks[p171_0]));

        this.furnaceItemStacks[p171_0] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        if (p171_0 == 0 && !(flag)) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public static boolean isItemFuel(ItemStack itemStack) {
        return net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack) > 0;
    }

    public ItemStack decrStackSize(int p171_0, int p171_1) {
        if (this.furnaceItemStacks[p171_0] != null) {
            if (this.furnaceItemStacks[p171_0].stackSize <= p171_1) {
                net.minecraft.item.ItemStack itemStack = this.furnaceItemStacks[p171_0];
                this.furnaceItemStacks[p171_0] = null;
                return itemStack;
            }

            net.minecraft.item.ItemStack itemStackValue = this.furnaceItemStacks[p171_0].splitStack(p171_1);
            if (this.furnaceItemStacks[p171_0].stackSize == 0) {
                this.furnaceItemStacks[p171_0] = null;
            }

            return itemStackValue;
        } else {
            return null;
        }
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public void update() {
        boolean flag = this.jvm171_f_255f321d26e05a();
        MobSpawnerBaseLogic.GU();

        byte b0 = 0;

        if (this.jvm171_f_255f321d26e05a()) {
            this.furnaceBurnTime--;
        }

        if (!this.worldObj.isRemote) {
            {
                if (!this.jvm171_f_255f321d26e05a()
                        && (this.furnaceItemStacks[1] == null
                                || this.furnaceItemStacks[0] == null)) {
                    if (!(this.jvm171_f_255f321d26e05a() || this.cookTime <= 0)) {

                        this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);


                    }
                } else {

                    if (!this.jvm171_f_255f321d26e05a() && this.fo()) {
                        this.currentItemBurnTime =
                                this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                        if (this.jvm171_f_255f321d26e05a()) {
                            b0 = 1;
                            if (this.furnaceItemStacks[1] != null) {
                                this.furnaceItemStacks[1].stackSize--;
                                if (this.furnaceItemStacks[1].stackSize == 0) {
                                    Item item = this.furnaceItemStacks[1].getItem().getContainerItem();
                                    this.furnaceItemStacks[1] =
                                            item != null ? new ItemStack(item) : null;
                                }
                            }
                        }
                    }

                    if (this.jvm171_f_255f321d26e05a() && this.fo()) {
                        this.cookTime++;
                        if (!(this.cookTime != this.totalCookTime)) {

                            this.cookTime = 0;
                            this.totalCookTime = this.getCookTime(this.furnaceItemStacks[0]);
                            this.fF();
                            b0 = 1;


                        }
                    } else {

                        this.cookTime = 0;
                    }
                }
            }

            if (flag != this.jvm171_f_255f321d26e05a()) {
                b0 = 1;
                BlockFurnace.setState(
                        this.jvm171_f_255f321d26e05a(), this.worldObj, this.pos);
            }
        }

        if (b0 != 0) {
            this.markDirty();
        }
    }

    public boolean hasCustomName() {
        return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
    }

    public int getField(int p171_0) {
        switch (p171_0) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public static int getItemBurnTime(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }

        Item item = itemStack.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
            Block block = Block.getBlockFromItem(item);
            if (block == Blocks.wooden_slab) {
                return 150;
            }

            if (block.getMaterial() == Material.wood) {
                return 300;
            }

            if (block == Blocks.coal_block) {
                return 16000;
            }
        }

        return item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")
                ? 200
                : (item instanceof SwordItem
                                && ((SwordItem) item).getToolMaterialName().equals("WOOD")
                        ? 200
                        : (item instanceof ItemHoe
                                        && ((ItemHoe) item).getMaterialName().equals("WOOD")
                                ? 200
                                : (item == Items.stick
                                        ? 100
                                        : (item == Items.coal
                                                ? 1600
                                                : (item == Items.lava_bucket
                                                        ? 20000
                                                        : (item
                                                                        == Item.getItemFromBlock(
                                                                                Blocks.sapling)
                                                                ? 100
                                                                : (item == Items.blaze_rod
                                                                        ? 2400
                                                                        : 0)))))));
    }

    public void setField(int i, int j) {
        MobSpawnerBaseLogic.GU();

        switch (i) {
            case 0:
                this.furnaceBurnTime = j;

                break;

            case 1:
                this.currentItemBurnTime = j;

                break;

            case 2:
                this.cookTime = j;

                break;

            case 3:
                this.totalCookTime = j;
        }
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setShort("BurnTime", (short) this.furnaceBurnTime);
        MobSpawnerBaseLogic.GU();

        compoundTag.setShort("CookTime", (short) this.cookTime);

        compoundTag.setShort("CookTimeTotal", (short) this.totalCookTime);
        NBTTagList nbtTagList = new NBTTagList();
        int i = 0;

        while (i < this.furnaceItemStacks.length) {
            if (this.furnaceItemStacks[i] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            i++;
        }

        compoundTag.setTag("Items", nbtTagList);
        if (this.hasCustomName()) {
            compoundTag.setString("CustomName", this.furnaceCustomName);
        }
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public void openInventory(PlayerEntity playerEntity) {}

    public void clearInventory() {
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < this.furnaceItemStacks.length) {
            this.furnaceItemStacks[i] = null;
            i++;
        }
    }

    public ItemStack getStackInSlot(int p171_0) {
        return this.furnaceItemStacks[p171_0];
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        MobSpawnerBaseLogic.GU();

        NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);

        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            byte b0 = compoundTagValue.getByte("Slot");
            if (b0 >= 0 && b0 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(compoundTagValue);
            }

            i++;
        }

        this.furnaceBurnTime = compoundTag.getShort("BurnTime");
        this.cookTime = compoundTag.getShort("CookTime");
        this.totalCookTime = compoundTag.getShort("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if (compoundTag.hasKey("CustomName", 8)) {
            this.furnaceCustomName = compoundTag.getString("CustomName");
        }
    }

    public String getGuiID() {
        return "minecraft:furnace";
    }

    public boolean jvm171_f_255f321d26e05a() {
        return this.furnaceBurnTime > 0;
    }

    public boolean canExtractItem(int i, ItemStack itemStack, Facing facing) {
        if (facing == Facing.DOWN && i == 1) {
            Item item = itemStack.getItem();
            if (item != Items.water_bucket && item != Items.bucket) {
                return false;
            }
        }

        return true;
    }

    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    }

    public int getFieldCount() {
        return 4;
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerFurnace(inventoryPlayer, this);
    }

    public void fF() {
        MobSpawnerBaseLogic.GU();

        if (this.fo()) {
            {
                ItemStack itemStack =
                        FurnaceRecipes.instance()
                                .getSmeltingResult(this.furnaceItemStacks[0]);
                if (this.furnaceItemStacks[2] == null) {
                    this.furnaceItemStacks[2] = itemStack.copy();

                    
                } else {

                    if (this.furnaceItemStacks[2].getItem() == itemStack.getItem()) {
                        this.furnaceItemStacks[2].stackSize++;
                    }
                }
            }

            if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge)
                    && this.furnaceItemStacks[0].getItemDamage() == 1
                    && this.furnaceItemStacks[1] != null
                    && this.furnaceItemStacks[1].getItem() == Items.bucket) {
                this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
            }

            this.furnaceItemStacks[0].stackSize--;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    static {
        net.minecraft.tileentity.TileEntityFurnace.slotsTop = new int[] {0};
        net.minecraft.tileentity.TileEntityFurnace.slotsBottom = new int[] {2, 1};
        net.minecraft.tileentity.TileEntityFurnace.slotsSides = new int[] {1};
    }

    public int getCookTime(ItemStack itemStack) {
        return 200;
    }

    public boolean canInsertItem(int p171_0, ItemStack itemStack, Facing facing) {
        return this.isItemValidForSlot(p171_0, itemStack);
    }

    public boolean isItemValidForSlot(int p171_0, ItemStack itemStack) {
        return p171_0 == 2
                ? false
                : (p171_0 != 1
                        ? true
                        : net.minecraft.tileentity.TileEntityFurnace.isItemFuel(itemStack)
                                || net.minecraft.inventory.SlotFurnaceFuel.isBucket(
                                        itemStack));
    }

    public ItemStack getStackInSlotOnClosing(int p171_0) {
        if (this.furnaceItemStacks[p171_0] != null) {
            net.minecraft.item.ItemStack itemStack = this.furnaceItemStacks[p171_0];
            this.furnaceItemStacks[p171_0] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    private boolean fo() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }

        net.minecraft.item.ItemStack itemStack =
                net.minecraft.item.crafting.FurnaceRecipes.instance()
                        .getSmeltingResult(this.furnaceItemStacks[0]);
        return itemStack == null
                ? false
                : (this.furnaceItemStacks[2] == null
                        ? true
                        : (!this.furnaceItemStacks[2].isItemEqual(itemStack)
                                ? false
                                : (this.furnaceItemStacks[2].stackSize
                                                        < this.getInventoryStackLimit()
                                                && this.furnaceItemStacks[2].stackSize
                                                        < this.furnaceItemStacks[2]
                                                                .getMaxStackSize()
                                        ? true
                                        : this.furnaceItemStacks[2].stackSize
                                                < itemStack.getMaxStackSize())));
    }

    public int[] getSlotsForFace(Facing facing) {
        return facing == net.minecraft.util.Facing.DOWN
                ? net.minecraft.tileentity.TileEntityFurnace.slotsBottom
                : (facing == net.minecraft.util.Facing.UP
                        ? net.minecraft.tileentity.TileEntityFurnace.slotsTop
                        : net.minecraft.tileentity.TileEntityFurnace.slotsSides);
    }
}
