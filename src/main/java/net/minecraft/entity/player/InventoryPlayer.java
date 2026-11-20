package net.minecraft.entity.player;

import net.minecraft.block.Block;
import net.minecraft.command.server.CommandTestForBlock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ReportedException;
import net.minecraft.entity.player.InventoryPlayerItemNameCallable;

public class InventoryPlayer implements IInventory {
    public ItemStack[] mainInventory = new ItemStack[36];

    public boolean inventoryChanged;
    public int currentItem;
    public PlayerEntity player;

    private static int Ao;
    private ItemStack itemStack;
    public ItemStack[] armorInventory = new ItemStack[4];

    public void readFromNBT(NBTTagList nbtTagList) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        int l = getLegacyInventoryFlowToken();
        int j = 0;
        int i = l;

        while (j < nbtTagList.tagCount()) {
            CompoundTag compoundTag = nbtTagList.getCompoundTagAt(j);
            int k = compoundTag.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(compoundTag);
            if (itemStack != null) {
                if (k >= 0 && k < this.mainInventory.length) {
                    this.mainInventory[k] = itemStack;
                }

                if (k >= 100 && k < this.armorInventory.length + 100) {
                    this.armorInventory[k - 100] = itemStack;
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public boolean hasItemStack(ItemStack itemStack) {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.armorInventory.length) {
            if (this.armorInventory[j] != null && this.armorInventory[j].isItemEqual(itemStack)) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null && this.mainInventory[j].isItemEqual(itemStack)) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public static int getHotbarSize() {
        return 9;
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.player.isDead ? false : playerEntity.getDistanceSqToEntity(this.player) <= 64.0;
    }

    public void setItemStack(ItemStack bkWValue) {
        this.itemStack = bkWValue;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    private int getInventorySlotContainItem(Item item) {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null && this.mainInventory[j].getItem() == item) {
                return j;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return -1;
    }

    public int getField(int i) {
        return 0;
    }

    public ItemStack decrStackSize(int i, int j) {
        ItemStack[] bkVValue = this.mainInventory;
        if (i >= this.mainInventory.length) {
            bkVValue = this.armorInventory;
            i -= this.mainInventory.length;
        }

        if (bkVValue[i] != null) {
            if (bkVValue[i].stackSize <= j) {
                ItemStack itemStack = bkVValue[i];
                bkVValue[i] = null;
                return itemStack;
            }

            ItemStack itemStackValue = bkVValue[i].splitStack(j);
            if (bkVValue[i].stackSize == 0) {
                bkVValue[i] = null;
            }

            return itemStackValue;
        } else {
            return null;
        }
    }

    public ItemStack armorItemInSlot(int i) {
        return this.armorInventory[i];
    }

    public int getSizeInventory() {
        return this.mainInventory.length + 4;
    }

    public void damageArmor(float f) {
        int k = getLegacyInventoryFlowMarker();
        f /= 4.0F;
        int i = k;
        if (f < 1.0F) {
            f = 1.0F;
        }

        int j = 0;

        while (j < this.armorInventory.length) {
            if (this.armorInventory[j] != null
                    && this.armorInventory[j].getItem() instanceof ItemArmor) {
                this.armorInventory[j].damageItem((int) f, this.player);
                if (this.armorInventory[j].stackSize == 0) {
                    this.armorInventory[j] = null;
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public void dropAllItems() {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null) {
                this.player.dropItem(this.mainInventory[j], true, false);
                this.mainInventory[j] = null;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < this.armorInventory.length) {
            if (this.armorInventory[j] != null) {
                this.player.dropItem(this.armorInventory[j], true, false);
                this.armorInventory[j] = null;
            }

            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public static int getLegacyInventoryFlowMarker() {
        return Ao;
    }

    public void markDirty() {
        this.inventoryChanged = true;
    }

    public static int getLegacyInventoryFlowToken() {
        int i = getLegacyInventoryFlowMarker();
        return i == 0 ? 13 : 0;
    }

    public boolean hasCustomName() {
        return false;
    }

    public int clearMatchingItems(Item item, int i, int j, CompoundTag compoundTag) {
        int l = 0;
        int l1 = getLegacyInventoryFlowToken();
        int i1 = 0;
        int k = l1;

        while (i1 < this.mainInventory.length) {
            ItemStack itemStack = this.mainInventory[i1];
            if (itemStack != null
                    && (item == null || itemStack.getItem() == item)
                    && (i <= -1 || itemStack.getItemDamage() == i)
                    && (compoundTag == null
                            || CommandTestForBlock.method_b1e94d9ac0e51bb728b9(
                                    compoundTag, itemStack.getTag(), true))) {
                int j1 = j <= 0 ? itemStack.stackSize : Math.min(j - l, itemStack.stackSize);
                l += j1;
                if (j != 0) {
                    this.mainInventory[i1].stackSize -= j1;
                    if (this.mainInventory[i1].stackSize == 0) {
                        this.mainInventory[i1] = null;
                    }

                    if (j > 0 && l >= j) {
                        return l;
                    }
                }
            }

            i1++;
            if (k == 0) {
                break;
            }
        }

        i1 = 0;

        while (i1 < this.armorInventory.length) {
            ItemStack itemStackValue = this.armorInventory[i1];
            if (itemStackValue != null
                    && (item == null || itemStackValue.getItem() == item)
                    && (i <= -1 || itemStackValue.getItemDamage() == i)
                    && (compoundTag == null
                            || CommandTestForBlock.method_b1e94d9ac0e51bb728b9(
                                    compoundTag, itemStackValue.getTag(), false))) {
                int k1 =
                        j <= 0
                                ? itemStackValue.stackSize
                                : Math.min(j - l, itemStackValue.stackSize);
                l += k1;
                if (j != 0) {
                    this.armorInventory[i1].stackSize -= k1;
                    if (this.armorInventory[i1].stackSize == 0) {
                        this.armorInventory[i1] = null;
                    }

                    if (j > 0 && l >= j) {
                        return l;
                    }
                }
            }

            i1++;
            if (k == 0) {
                break;
            }
        }

        if (this.itemStack != null) {
            if (item != null && this.itemStack.getItem() != item) {
                return l;
            }

            if (i > -1 && this.itemStack.getItemDamage() != i) {
                return l;
            }

            if (compoundTag != null
                    && !CommandTestForBlock.method_b1e94d9ac0e51bb728b9(
                            compoundTag, this.itemStack.getTag(), false)) {
                return l;
            }

            i1 = j <= 0 ? this.itemStack.stackSize : Math.min(j - l, this.itemStack.stackSize);
            l += i1;
            if (j != 0) {
                this.itemStack.stackSize -= i1;
                if (this.itemStack.stackSize == 0) {
                    this.itemStack = null;
                }

                if (j > 0 && l >= j) {
                    return l;
                }
            }
        }

        return l;
    }

    public void changeCurrentItem(int i) {
        int j = getLegacyInventoryFlowToken();
        if (i > 0) {
            i = 1;
        }

        if (i < 0) {
            i = -1;
        }

        this.currentItem -= i;

        while (this.currentItem < 0) {
            this.currentItem += 9;
            if (j == 0) {
                break;
            }
        }

        while (this.currentItem >= 9) {
            this.currentItem -= 9;
            if (j == 0) {
                break;
            }
        }
    }

    public int getTotalArmorValue() {
        int i1 = getLegacyInventoryFlowToken();
        int j = 0;
        int i = i1;
        int k = 0;

        while (k < this.armorInventory.length) {
            if (this.armorInventory[k] != null
                    && this.armorInventory[k].getItem() instanceof ItemArmor) {
                int l = ((ItemArmor) this.armorInventory[k].getItem()).damageReduceAmount;
                j += l;
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return j;
    }

    // region String decoding support

    // endregion

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void clearInventory() {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            this.mainInventory[j] = null;
            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < this.armorInventory.length) {
            this.armorInventory[j] = null;
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public ItemStack getCurrentItem() {
        return this.currentItem < 9 && this.currentItem >= 0
                ? this.mainInventory[this.currentItem]
                : null;
    }

    public void copyInventory(InventoryPlayer inventoryPlayer) {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            this.mainInventory[j] = ItemStack.copyItemStack(inventoryPlayer.mainInventory[j]);
            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < this.armorInventory.length) {
            this.armorInventory[j] = ItemStack.copyItemStack(inventoryPlayer.armorInventory[j]);
            j++;
            if (i == 0) {
                break;
            }
        }

        this.currentItem = inventoryPlayer.currentItem;
    }

    public int getFieldCount() {
        return 0;
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        ItemStack[] bkVValue = this.mainInventory;
        if (i >= bkVValue.length) {
            i -= bkVValue.length;
            bkVValue = this.armorInventory;
        }

        bkVValue[i] = itemStack;
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public boolean canHeldItemHarvest(Block block) {
        if (block.getMaterial().isToolNotRequired()) {
            return true;
        }

        ItemStack itemStack = this.getStackInSlot(this.currentItem);
        return itemStack != null ? itemStack.canHarvestBlock(block) : false;
    }

    public ItemStack getStackInSlot(int i) {
        ItemStack[] bkVValue = this.mainInventory;
        if (i >= bkVValue.length) {
            i -= bkVValue.length;
            bkVValue = this.armorInventory;
        }

        return bkVValue[i];
    }

    public boolean hasItem(Item item) {
        int inventorySlotContainItem = this.getInventorySlotContainItem(item);
        return inventorySlotContainItem >= 0;
    }

    // region String decoding support

    // endregion

    private int storeItemStack(ItemStack itemStack) {
        int k = getLegacyInventoryFlowMarker();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null
                    && this.mainInventory[j].getItem() == itemStack.getItem()
                    && this.mainInventory[j].isStackable()
                    && this.mainInventory[j].stackSize < this.mainInventory[j].getMaxStackSize()
                    && this.mainInventory[j].stackSize < this.getInventoryStackLimit()
                    && (!this.mainInventory[j].getHasSubtypes()
                            || this.mainInventory[j].getItemDamage() == itemStack.getItemDamage())
                    && ItemStack.areItemStackTagsEqual(this.mainInventory[j], itemStack)) {
                return j;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return -1;
    }

    private int getInventorySlotContainItemAndDamage(Item item, int i) {
        int l = getLegacyInventoryFlowToken();
        int k = 0;
        int j = l;

        while (k < this.mainInventory.length) {
            if (this.mainInventory[k] != null
                    && this.mainInventory[k].getItem() == item
                    && this.mainInventory[k].getItemDamage() == i) {
                return k;
            }

            k++;
            if (j == 0) {
                break;
            }
        }

        return -1;
    }

    public InventoryPlayer(PlayerEntity bidValue) {
        this.player = bidValue;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public String getName() {
        return "container.inventory";
    }

    public NBTTagList writeToNBT(NBTTagList nbtTagList) {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.setByte("Slot", (byte) j);
                this.mainInventory[j].writeToNBT(compoundTag);
                nbtTagList.appendTag(compoundTag);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < this.armorInventory.length) {
            if (this.armorInventory[j] != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte("Slot", (byte) (j + 100));
                this.armorInventory[j].writeToNBT(compoundTagValue);
                nbtTagList.appendTag(compoundTagValue);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return nbtTagList;
    }

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public float getStrVsBlock(Block block) {
        float fx = 1.0F;
        if (this.mainInventory[this.currentItem] != null) {
            fx *= this.mainInventory[this.currentItem].getStrVsBlock(block);
        }

        return fx;
    }

    public void setCurrentItem(Item item, int i, boolean flag, boolean flag1) {
        int k1 = getLegacyInventoryFlowToken();
        ItemStack itemStack = this.getCurrentItem();
        int j = k1;
        int k =
                flag
                        ? this.getInventorySlotContainItemAndDamage(item, i)
                        : this.getInventorySlotContainItem(item);
        if (k >= 0 && k < 9) {
            this.currentItem = k;
            if (j != 0) {
                return;
            }
        }

        if (flag1 && item != null) {
            int l = this.cR();
            if (l >= 0 && l < 9) {
                this.currentItem = l;
            }

            if (itemStack == null
                    || !itemStack.isUnenchantedTool()
                    || this.getInventorySlotContainItemAndDamage(
                                    itemStack.getItem(), itemStack.getMetadata())
                            != this.currentItem) {
                int j1;
                {
                    int i1 = this.getInventorySlotContainItemAndDamage(item, i);
                    if (i1 >= 0) {
                        j1 = this.mainInventory[i1].stackSize;
                        this.mainInventory[i1] = this.mainInventory[this.currentItem];
                        if (!(j != 0)) {

                            j1 = 1;
                        }
                    } else {

                        j1 = 1;
                    }
                }

                this.mainInventory[this.currentItem] = new ItemStack(item, j1, i);
            }
        }
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public int cR() {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] == null) {
                return j;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return -1;
    }

    private int storePartialItemStack(ItemStack itemStack) {
        Item item = itemStack.getItem();
        int stackSize = itemStack.stackSize;
        int j = this.storeItemStack(itemStack);
        if (j < 0) {
            j = this.cR();
        }

        if (j < 0) {
            return stackSize;
        }

        if (this.mainInventory[j] == null) {
            this.mainInventory[j] = new ItemStack(item, 0, itemStack.getItemDamage());
            if (itemStack.hasTagCompound()) {
                this.mainInventory[j].setTag((CompoundTag) itemStack.getTag().copy());
            }
        }

        int k = stackSize;
        if (stackSize > this.mainInventory[j].getMaxStackSize() - this.mainInventory[j].stackSize) {
            k = this.mainInventory[j].getMaxStackSize() - this.mainInventory[j].stackSize;
        }

        if (k > this.getInventoryStackLimit() - this.mainInventory[j].stackSize) {
            k = this.getInventoryStackLimit() - this.mainInventory[j].stackSize;
        }

        if (k == 0) {
            return stackSize;
        }

        stackSize -= k;
        this.mainInventory[j].stackSize += k;
        this.mainInventory[j].animationsToGo = 5;
        return stackSize;
    }

    public void decrementAnimations() {
        int k = getLegacyInventoryFlowToken();
        int j = 0;
        int i = k;

        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null) {
                this.mainInventory[j].updateAnimation(
                        this.player.world, this.player, j, this.currentItem == j);
            }

            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public void setField(int i, int j) {}

    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack[] bkVValue = this.mainInventory;
        if (i >= this.mainInventory.length) {
            bkVValue = this.armorInventory;
            i -= this.mainInventory.length;
        }

        if (bkVValue[i] != null) {
            ItemStack itemStack = bkVValue[i];
            bkVValue[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public boolean addItemStackToInventory(ItemStack itemStack) {
        if (itemStack != null && itemStack.stackSize != 0 && itemStack.getItem() != null) {
            try {
                if (itemStack.isItemDamaged()) {
                    int j = this.cR();
                    if (j >= 0) {
                        this.mainInventory[j] = ItemStack.copyItemStack(itemStack);
                        this.mainInventory[j].animationsToGo = 5;
                        itemStack.stackSize = 0;
                        return true;
                    } else if (this.player.capabilities.isCreativeMode) {
                        itemStack.stackSize = 0;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    int i;
                    do {
                        i = itemStack.stackSize;
                        itemStack.stackSize = this.storePartialItemStack(itemStack);
                    } while (itemStack.stackSize > 0 && itemStack.stackSize < i);

                    if (itemStack.stackSize == i && this.player.capabilities.isCreativeMode) {
                        itemStack.stackSize = 0;
                        return true;
                    } else {
                        return itemStack.stackSize < i;
                    }
                }
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Item being added");
                crashReportCategory.addCrashSection(
                        "Item ID", Item.getIdFromItem(itemStack.getItem()));
                crashReportCategory.addCrashSection("Item data", itemStack.getItemDamage());
                crashReportCategory.addCrashSectionCallable(
                        "Item name", new InventoryPlayerItemNameCallable(this, itemStack));
                throw new ReportedException(crashReport);
            }
        } else {
            return false;
        }
    }

    public boolean consumeInventoryItem(Item item) {
        int inventorySlotContainItem = this.getInventorySlotContainItem(item);
        if (inventorySlotContainItem < 0) {
            return false;
        }

        if (--this.mainInventory[inventorySlotContainItem].stackSize <= 0) {
            this.mainInventory[inventorySlotContainItem] = null;
        }

        return true;
    }

    static {
        setLegacyInventoryFlowMarker(0);
    }

    public static void setLegacyInventoryFlowMarker(int i) {
        Ao = i;
    }
}
