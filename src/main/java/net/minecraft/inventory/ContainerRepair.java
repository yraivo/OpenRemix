package net.minecraft.inventory;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ContainerRepair extends Container {
    private PlayerEntity thePlayer;
    private static String Cs;
    private World theWorld;
    private IInventory outputSlot = new InventoryCraftResult();
    private int materialCost;
    private BlockPos selfPosition;

    private static Logger logger;
    public int maximumCost;
    private String repairedItemName;
    private IInventory inputSlots;

    public ContainerRepair(
            InventoryPlayer inventoryPlayer, World world, PlayerEntity playerEntity) {
        this(inventoryPlayer, world, BlockPos.ORIGIN, playerEntity);
    }

    public void updateItemName(String text) {
        int j = Container.cR();
        this.repairedItemName = text;
        int i = j;
        if (this.getSlot(2).getHasStack()) {
            ItemStack itemStack = this.getSlot(2).getStack();
            if (StringUtils.isBlank(text)) {
                itemStack.clearCustomName();
                if (!(i == 0)) {

                    itemStack.setStackDisplayName(this.repairedItemName);
                }
            } else {

                itemStack.setStackDisplayName(this.repairedItemName);
            }
        }

        this.updateRepairOutput();
    }

    // region String decoding support

    // endregion

    public static IInventory access$000(ContainerRepair containerRepair) {
        return containerRepair.inputSlots;
    }

    public void updateRepairOutput() {
        int i5 = Container.cR();

        int i = i5;

        ItemStack itemStack = this.inputSlots.getStackInSlot(0);
        this.maximumCost = 1;
        int j = 0;
        byte b7 = 0;
        byte b8 = 0;
        if (itemStack == null) {
            this.outputSlot.setInventorySlotContents(0, (ItemStack) null);
            this.maximumCost = 0;
            if (i == 0) {
                return;
            }
        }

        ItemStack itemStackValue;
        ItemStack itemStackValue2;
        Map map;
        int l;
        itemStackValue = itemStack.copy();
        itemStackValue2 = this.inputSlots.getStackInSlot(1);
        map = EnchantmentHelper.getEnchantments(itemStackValue);
        int k = 0;
        l =
                b7
                        + itemStack.getRepairCost()
                        + (itemStackValue2 == null ? 0 : itemStackValue2.getRepairCost());
        this.materialCost = 0;
        label168:
        if (itemStackValue2 != null) {
            k =
                    itemStackValue2.getItem() == Items.enchanted_book
                                    && Items.enchanted_book
                                                    .getEnchantments(itemStackValue2)
                                                    .tagCount()
                                            > 0
                            ? 1
                            : 0;
            if (itemStackValue.isItemStackDamageable()
                    && itemStackValue.getItem().getIsRepairable(itemStack, itemStackValue2)) {
                int i1 = Math.min(itemStackValue.getMetadata(), itemStackValue.getMaxDamage() / 4);
                if (i1 <= 0) {
                    this.outputSlot.setInventorySlotContents(0, (ItemStack) null);
                    this.maximumCost = 0;
                    return;
                }

                int j1 = 0;

                while (i1 > 0 && j1 < itemStackValue2.stackSize) {
                    int k1 = itemStackValue.getMetadata() - i1;
                    itemStackValue.setItemDamage(k1);
                    j++;
                    i1 = Math.min(itemStackValue.getMetadata(), itemStackValue.getMaxDamage() / 4);
                    j1++;
                    if (i != 0) {
                        break;
                    }
                }

                this.materialCost = j1;
                if (i == 0) {
                    break label168;
                }
            }

            if (k == 0
                    && (itemStackValue.getItem() != itemStackValue2.getItem()
                            || !itemStackValue.isItemStackDamageable())) {
                this.outputSlot.setInventorySlotContents(0, (ItemStack) null);
                this.maximumCost = 0;
                return;
            }

            if (itemStackValue.isItemStackDamageable() && k == 0) {
                int i3 = itemStack.getMaxDamage() - itemStack.getMetadata();
                int k3 = itemStackValue2.getMaxDamage() - itemStackValue2.getMetadata();
                int l3 = k3 + itemStackValue.getMaxDamage() * 12 / 100;
                int i2 = i3 + l3;
                int l1 = itemStackValue.getMaxDamage() - i2;
                if (l1 < 0) {
                    l1 = 0;
                }

                if (l1 < itemStackValue.getItemDamage()) {
                    itemStackValue.setItemDamage(l1);
                    j += 2;
                }
            }

            Map map1 = EnchantmentHelper.getEnchantments(itemStackValue2);

            for (Object object : map1.keySet()) {
                int i4 = (Integer) object;
                Enchantment enchantment = Enchantment.getEnchantmentById(i4);
                if (enchantment != null) {
                    int k2;
                    {
                        int j4 = map.containsKey(i4) ? (Integer) map.get(i4) : 0;
                        int j2 = (Integer) map1.get(i4);
                        if (j4 == j2) {
                            k2 = ++j2;
                            if (!(i == 0)) {

                                k2 = Math.max(j2, j4);
                            }
                        } else {

                            k2 = Math.max(j2, j4);
                        }
                    }

                    int k4 = k2;
                    byte b9 =
                            remix.compat.compiler.JvmBooleanSlots.toByte(
                                    enchantment.canApply(itemStack));
                    if (this.thePlayer.capabilities.isCreativeMode
                            || itemStack.getItem() == Items.enchanted_book) {
                        b9 = 1;
                    }

                    for (java.util.Iterator<?> __recoveryIterator171_7333 =
                                    (map.keySet()).iterator();
                            (__recoveryIterator171_7333).hasNext(); ) {
                        int l2 =
                                ((java.lang.Integer) (__recoveryIterator171_7333).next())
                                        .intValue();
                        if (l2 != i4
                                && !enchantment.canApplyTogether(
                                        Enchantment.getEnchantmentById(l2))) {
                            b9 = 0;
                            j++;
                        }

                        if (i != 0) {
                            break;
                        }
                    }

                    if (b9 != 0) {
                        if (k4 > enchantment.getMaxLevel()) {
                            k4 = enchantment.getMaxLevel();
                        }

                        map.put(i4, k4);
                        int l4 = 0;
                        switch (enchantment.getWeight()) {
                            case 1:
                                l4 = 8;
                                if (i == 0) {
                                    break;
                                }
                            case 2:
                                l4 = 4;
                            case 3:
                            case 4:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            default:
                                if (i == 0) {
                                    break;
                                }
                            case 5:
                                l4 = 2;
                                if (i == 0) {
                                    break;
                                }
                            case 10:
                                l4 = 1;
                        }

                        if (k != 0) {
                            l4 = Math.max(1, l4 / 2);
                        }

                        j += l4 * k4;
                    }
                }

                if (i != 0) {
                    break;
                }
            }
        }

        label120:
        {
            if (StringUtils.isBlank(this.repairedItemName)) {
                if (!itemStack.hasDisplayName()) {
                    break label120;
                }

                b8 = 1;
                j += b8;
                itemStackValue.clearCustomName();
                if (i == 0) {
                    break label120;
                }
            }

            if (!this.repairedItemName.equals(itemStack.getDisplayName())) {
                b8 = 1;
                j += b8;
                itemStackValue.setStackDisplayName(this.repairedItemName);
            }
        }

        this.maximumCost = l + j;
        if (j <= 0) {
            itemStackValue = null;
        }

        if (b8 == j && b8 > 0 && this.maximumCost >= 40) {
            this.maximumCost = 39;
        }

        if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
            itemStackValue = null;
        }

        if (itemStackValue != null) {
            int j3 = itemStackValue.getRepairCost();
            if (itemStackValue2 != null && j3 < itemStackValue2.getRepairCost()) {
                j3 = itemStackValue2.getRepairCost();
            }

            j3 = j3 * 2 + 1;
            itemStackValue.setRepairCost(j3);
            EnchantmentHelper.setEnchantments(map, itemStackValue);
        }

        this.outputSlot.setInventorySlotContents(0, itemStackValue);
        this.detectAndSendChanges();
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        ItemStack itemStack = null;
        int k = Container.cR();
        Slot slot = (Slot) this.inventorySlots.get(i);
        int j = k;
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue;
            label50:
            {
                itemStackValue = slot.getStack();
                itemStack = itemStackValue.copy();
                if (i == 2) {
                    if (!this.mergeItemStack(itemStackValue, 3, 39, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemStackValue, itemStack);
                    if (j == 0) {
                        break label50;
                    }
                }

                if (i != 0 && i != 1) {
                    if (i >= 3 && i < 39 && !this.mergeItemStack(itemStackValue, 0, 2, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStackValue, 3, 39, false)) {
                    return null;
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

    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, this.maximumCost);
    }

    public ContainerRepair(
            InventoryPlayer inventoryPlayer,
            World bsAValue,
            BlockPos bsBValue,
            PlayerEntity playerEntity) {
        this.inputSlots = new ContainerRepairInputSlotsInventoryBasic(this, Cs, true, 2);
        int l = Container.cR();
        this.selfPosition = bsBValue;
        this.theWorld = bsAValue;
        int i = l;
        this.thePlayer = playerEntity;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(
                new ContainerRepairSlot2(this, this.outputSlot, 2, 134, 47, bsAValue, bsBValue));
        int j = 0;

        label35:
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
                }

                j++;
                if (i != 0) {
                    break label35;
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

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Container.method_30be6ce7816ed46ef891(++i);
        }
    }

    static {
        Cs = "Repair";
        net.minecraft.inventory.ContainerRepair.logger = LogManager.getLogger();
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.anvil
                ? false
                : playerEntity.getDistanceSq(
                                this.selfPosition.getX() + 0.5,
                                this.selfPosition.getY() + 0.5,
                                this.selfPosition.getZ() + 0.5)
                        <= 64.0;
    }

    public void updateProgressBar(int i, int j) {
        if (i == 0) {
            this.maximumCost = j;
        }
    }

    // region String decoding support

    // endregion

    public static int access$100(ContainerRepair containerRepair) {
        return containerRepair.materialCost;
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        int k = Container.aW();
        super.onContainerClosed(playerEntity);
        int i = k;
        if (!this.theWorld.isRemote) {
            int j = 0;

            while (j < this.inputSlots.getSizeInventory()) {
                ItemStack itemStack = this.inputSlots.getStackInSlotOnClosing(j);
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

    public void onCraftMatrixChanged(IInventory iInventory) {
        super.onCraftMatrixChanged(iInventory);
        if (iInventory == this.inputSlots) {
            this.updateRepairOutput();
        }
    }
}
