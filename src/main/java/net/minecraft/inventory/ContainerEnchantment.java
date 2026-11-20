package net.minecraft.inventory;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ContainerEnchantment extends Container {
    private BlockPos position;
    public int[] field_178151_h;
    private static String oF;
    public int[] enchantLevels;
    private Random rand;
    public IInventory tableInventory;

    private World worldPointer;
    public int xpSeed;

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        int k = Container.cR();
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        int j = k;
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue = slot.getStack();
            itemStack = itemStackValue.copy();
            if (i == 0) {
                if (!this.mergeItemStack(itemStackValue, 2, 38, true)) {
                    return null;
                }
            } else if (i == 1) {
                if (!this.mergeItemStack(itemStackValue, 2, 38, true)) {
                    return null;
                }
            } else if (itemStackValue.getItem() == Items.dye
                    && EnumDyeColor.byDyeDamage(itemStackValue.getItemDamage())
                            == EnumDyeColor.BLUE) {
                if (!this.mergeItemStack(itemStackValue, 1, 2, true)) {
                    return null;
                }
            } else {
                label72:
                {
                    if (((Slot) this.inventorySlots.get(0)).getHasStack()
                            || !((Slot) this.inventorySlots.get(0)).isItemValid(itemStackValue)) {
                        return null;
                    }

                    if (itemStackValue.hasTagCompound() && itemStackValue.stackSize == 1) {
                        ((Slot) this.inventorySlots.get(0)).putStack(itemStackValue.copy());
                        itemStackValue.stackSize = 0;
                        if (j == 0) {
                            break label72;
                        }
                    }

                    if (itemStackValue.stackSize >= 1) {
                        ((Slot) this.inventorySlots.get(0))
                                .putStack(
                                        new ItemStack(
                                                itemStackValue.getItem(),
                                                1,
                                                itemStackValue.getItemDamage()));
                        itemStackValue.stackSize--;
                    }
                }
            }

            {
                if (itemStackValue.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                    if (!(j == 0)) {

                        slot.onSlotChanged();
                    }
                } else {

                    slot.onSlotChanged();
                }
            }

            if (itemStackValue.stackSize == itemStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerEntity, itemStackValue);
        }

        return itemStack;
    }

    public int getLapisAmount() {
        ItemStack itemStack = this.tableInventory.getStackInSlot(1);
        return itemStack == null ? 0 : itemStack.stackSize;
    }

    public ContainerEnchantment(InventoryPlayer inventoryPlayer, World world, BlockPos blockPos) {
        this(inventoryPlayer, world, blockPos, Container.cR());
    }

    private ContainerEnchantment(
            InventoryPlayer inventoryPlayer,
            World bkIValue,
            BlockPos blockPos,
            int initialFlowToken) {
        super();

        int l = initialFlowToken;

        this.tableInventory =
                new ContainerEnchantmentTableInventoryInventoryBasic(this, oF, true, 2);
        int i = l;
        this.rand = new Random();
        this.enchantLevels = new int[3];
        this.field_178151_h = new int[] {-1, -1, -1};
        this.worldPointer = bkIValue;
        this.position = blockPos;
        this.xpSeed = inventoryPlayer.player.getXPSeed();
        this.addSlotToContainer(
                new ContainerEnchantmentSlot2(this, this.tableInventory, 0, 15, 47));
        this.addSlotToContainer(
                new ContainerEnchantmentSlot3(this, this.tableInventory, 1, 35, 47));
        int j = 0;

        label33:
        while (j < 3) {
            int k = 0;

            while (true) {
                if (k < 9) {
                    this.addSlotToContainer(
                            new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
                    k++;
                    if (i == 0) {
                        continue;
                    }

                    Entity.setLegacyGlobalFlowMarker(new String[4]);
                }

                j++;
                if (i != 0) {
                    break label33;
                }
                break;
            }
        }

        j = 0;

        while (j < 9) {
            this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 142));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public void detectAndSendChanges() {
        int k = Container.cR();
        super.detectAndSendChanges();
        int i = k;
        int j = 0;

        while (j < this.crafters.size()) {
            ICrafting iCrafting = (ICrafting) this.crafters.get(j);
            iCrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            iCrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            iCrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
            iCrafting.sendProgressBarUpdate(this, 3, this.xpSeed & -16);
            iCrafting.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
            iCrafting.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
            iCrafting.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        int k = Container.aW();
        super.onContainerClosed(playerEntity);
        int i = k;
        if (!this.worldPointer.isRemote) {
            int j = 0;

            while (j < this.tableInventory.getSizeInventory()) {
                ItemStack itemStack = this.tableInventory.getStackInSlotOnClosing(j);
                if (itemStack != null) {
                    playerEntity.dropPlayerItemWithRandomChoice(itemStack, false);
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.worldPointer.getBlockState(this.position).getBlock() != Blocks.enchanting_table
                ? false
                : playerEntity.getDistanceSq(
                                this.position.getX() + 0.5,
                                this.position.getY() + 0.5,
                                this.position.getZ() + 0.5)
                        <= 64.0;
    }

    private List func_178148_a(ItemStack itemStack, int i, int j) {
        this.rand.setSeed(this.xpSeed + i);
        List list = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, j);
        if (itemStack.getItem() == Items.book && list != null && list.size() > 1) {
            list.remove(this.rand.nextInt(list.size()));
        }

        return list;
    }

    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        iCrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        iCrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        iCrafting.sendProgressBarUpdate(this, 3, this.xpSeed & -16);
        iCrafting.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
        iCrafting.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
        iCrafting.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
    }

    public void onCraftMatrixChanged(IInventory iInventory) {
        int i = Container.cR();
        if (iInventory == this.tableInventory) {
            ItemStack itemStack = iInventory.getStackInSlot(0);
            if (itemStack != null && itemStack.isUnenchantedTool()) {
                if (this.worldPointer.isRemote) {
                    return;
                }

                int j = 0;
                int k = -1;

                while (k <= 1) {
                    int l = -1;

                    while (l <= 1) {
                        if ((k != 0 || l != 0)
                                && this.worldPointer.isAirBlock(this.position.add(l, 0, k))
                                && this.worldPointer.isAirBlock(this.position.add(l, 1, k))) {
                            if (this.worldPointer
                                            .getBlockState(this.position.add(l * 2, 0, k * 2))
                                            .getBlock()
                                    == Blocks.bookshelf) {
                                j++;
                            }

                            if (this.worldPointer
                                            .getBlockState(this.position.add(l * 2, 1, k * 2))
                                            .getBlock()
                                    == Blocks.bookshelf) {
                                j++;
                            }

                            if (l != 0 && k != 0) {
                                if (this.worldPointer
                                                .getBlockState(this.position.add(l * 2, 0, k))
                                                .getBlock()
                                        == Blocks.bookshelf) {
                                    j++;
                                }

                                if (this.worldPointer
                                                .getBlockState(this.position.add(l * 2, 1, k))
                                                .getBlock()
                                        == Blocks.bookshelf) {
                                    j++;
                                }

                                if (this.worldPointer
                                                .getBlockState(this.position.add(l, 0, k * 2))
                                                .getBlock()
                                        == Blocks.bookshelf) {
                                    j++;
                                }

                                if (this.worldPointer
                                                .getBlockState(this.position.add(l, 1, k * 2))
                                                .getBlock()
                                        == Blocks.bookshelf) {
                                    j++;
                                }
                            }
                        }

                        l++;
                        if (i != 0) {
                            break;
                        }
                    }

                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                this.rand.setSeed(this.xpSeed);
                k = 0;

                while (k < 3) {
                    this.enchantLevels[k] =
                            EnchantmentHelper.calcItemStackEnchantability(
                                    this.rand, k, j, itemStack);
                    this.field_178151_h[k] = -1;
                    if (this.enchantLevels[k] < k + 1) {
                        this.enchantLevels[k] = 0;
                    }

                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                k = 0;

                while (k < 3) {
                    if (this.enchantLevels[k] > 0) {
                        List list = this.func_178148_a(itemStack, k, this.enchantLevels[k]);
                        if (list != null && !list.isEmpty()) {
                            EnchantmentData enchantmentData =
                                    (EnchantmentData) list.get(this.rand.nextInt(list.size()));
                            this.field_178151_h[k] =
                                    enchantmentData.enchantmentobj.effectId
                                            | enchantmentData.enchantmentLevel << 8;
                        }
                    }

                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                this.detectAndSendChanges();
                if (i == 0) {
                    return;
                }
            }

            int i1 = 0;

            while (i1 < 3) {
                this.enchantLevels[i1] = 0;
                this.field_178151_h[i1] = -1;
                i1++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    static {
        oF = "Enchant";
    }

    public boolean enchantItem(PlayerEntity playerEntity, int i) {
        int j1 = Container.cR();
        ItemStack itemStack = this.tableInventory.getStackInSlot(0);
        int j = j1;
        ItemStack itemStackValue = this.tableInventory.getStackInSlot(1);
        int k = i + 1;
        if ((itemStackValue == null || itemStackValue.stackSize < k)
                && !playerEntity.capabilities.isCreativeMode) {
            return false;
        }

        if (this.enchantLevels[i] > 0
                && itemStack != null
                && (playerEntity.experienceLevel >= k
                                && playerEntity.experienceLevel >= this.enchantLevels[i]
                        || playerEntity.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isRemote) {
                List list = this.func_178148_a(itemStack, i, this.enchantLevels[i]);
                int l = itemStack.getItem() == Items.book ? 1 : 0;
                if (list != null) {
                    playerEntity.removeExperienceLevel(k);
                    if (l != 0) {
                        itemStack.setItem(Items.enchanted_book);
                    }

                    int i1 = 0;

                    while (i1 < list.size()) {
                        {
                            EnchantmentData enchantmentData = (EnchantmentData) list.get(i1);
                            if (l != 0) {
                                Items.enchanted_book.addEnchantment(itemStack, enchantmentData);
                                if (!(j == 0)) {

                                    itemStack.addEnchantment(
                                            enchantmentData.enchantmentobj,
                                            enchantmentData.enchantmentLevel);
                                }
                            } else {

                                itemStack.addEnchantment(
                                        enchantmentData.enchantmentobj,
                                        enchantmentData.enchantmentLevel);
                            }
                        }

                        i1++;
                        if (j != 0) {
                            break;
                        }
                    }

                    if (!playerEntity.capabilities.isCreativeMode) {
                        itemStackValue.stackSize -= k;
                        if (itemStackValue.stackSize <= 0) {
                            this.tableInventory.setInventorySlotContents(1, (ItemStack) null);
                        }
                    }

                    this.tableInventory.markDirty();
                    this.xpSeed = playerEntity.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public void updateProgressBar(int i, int j) {
        int k = Container.aW();
        if (i >= 0 && i <= 2) {
            this.enchantLevels[i] = j;
            if (k != 0) {
                return;
            }
        }

        if (i == 3) {
            this.xpSeed = j;
            if (k != 0) {
                return;
            }
        }

        if (i >= 4 && i <= 6) {
            this.field_178151_h[i - 4] = j;
            if (k != 0) {
                return;
            }
        }

        super.updateProgressBar(i, j);
    }

    public ContainerEnchantment(InventoryPlayer inventoryPlayer, World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }

    // region String decoding support

    // endregion
}
