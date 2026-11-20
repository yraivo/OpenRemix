package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Container {
    private Set playerList;
    public List<Slot> inventorySlots;
    private short transactionID;
    private Set dragSlots;
    protected List crafters;
    public List inventoryItemStacks = Lists.newArrayList();
    public int windowId;
    private int dragEvent;
    private static int tB;
    private int dragMode;
    private static String Cv;

    public static void method_30be6ce7816ed46ef891(int i) {
        tB = i;
    }

    public static int cR() {
        int i = aW();
        return i == 0 ? 76 : 0;
    }

    public Container() {
        this.inventorySlots = Lists.newArrayList();
        this.dragMode = -1;
        this.dragSlots = Sets.newHashSet();
        this.crafters = Lists.newArrayList();
        this.playerList = Sets.newHashSet();
    }

    public void setCanCraft(PlayerEntity playerEntity, boolean p171_1) {
        int i = net.minecraft.inventory.Container.aW();
        if (p171_1) {
            ((java.util.Set) (this.playerList)).remove(((java.lang.Object) (playerEntity)));
            if (i != 0) {
                return;
            }
        }

        ((java.util.Set) (this.playerList)).add(((java.lang.Object) (playerEntity)));
    }

    public static int aW() {
        return tB;
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        InventoryPlayer bdI = playerEntity.inventory;
        if (bdI.getItemStack() != null) {
            playerEntity.dropPlayerItemWithRandomChoice(bdI.getItemStack(), false);
            bdI.setItemStack((ItemStack) null);
        }
    }

    public List getInventory() {
        int k = aW();
        ArrayList arrayList = Lists.newArrayList();
        int i = k;
        int j = 0;

        while (j < this.inventorySlots.size()) {
            arrayList.add(this.inventorySlots.get(j).getStack());
            j++;
            if (i == 0) {
                break;
            }
        }

        return arrayList;
    }

    public static boolean isValidDragMode(int i, PlayerEntity playerEntity) {
        return i == 0 ? true : (i == 1 ? true : i == 2 && playerEntity.capabilities.isCreativeMode);
    }

    static {
        if (cR() != 0) {
            method_30be6ce7816ed46ef891(14);
        }
        Cv = "Listener already listening";
    }

    public abstract boolean canInteractWith(PlayerEntity playerEntity);

    public static int method_e0b8a2f32478f200146b(int p171_0, int p171_1) {
        return p171_0 & 3 | (p171_1 & 3) << 2;
    }

    public static int calcRedstoneFromInventory(TileEntity tileEntity) {
        try {
            if (tileEntity instanceof net.minecraft.inventory.IInventory) {
                return net.minecraft.inventory.Container.calcRedstoneFromInventory(
                        (net.minecraft.inventory.IInventory) tileEntity);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.inventory.Container.method_9a70918140b0a7642dba(
                    illegalargumentexception);
        }

        return 0;
    }

    public void putStackInSlot(int p171_0, ItemStack itemStack) {
        this.getSlot(p171_0).putStack(itemStack);
    }

    public boolean enchantItem(PlayerEntity playerEntity, int i) {
        return false;
    }

    public void onCraftMatrixChanged(IInventory iInventory) {
        this.detectAndSendChanges();
    }

    public void putStacksInSlots(ItemStack[] itemStackArray) {
        int k = cR();
        int j = 0;
        int i = k;

        while (j < itemStackArray.length) {
            this.getSlot(j).putStack(itemStackArray[j]);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public Slot getSlot(int p171_0) {
        return (net.minecraft.inventory.Slot) this.inventorySlots.get(p171_0);
    }

    public void updateProgressBar(int i, int j) {}

    public void detectAndSendChanges() {
        int l = net.minecraft.inventory.Container.cR();
        int j = 0;
        int i = l;

        while (j < ((int) (((java.util.List) (this.inventorySlots)).size()))) {
            net.minecraft.item.ItemStack itemStack =
                    ((net.minecraft.inventory.Slot)
                                    ((java.lang.Object)
                                            (((java.util.List) (this.inventorySlots))
                                                    .get(((int) (j))))))
                            .getStack();
            net.minecraft.item.ItemStack itemStackValue =
                    (net.minecraft.item.ItemStack)
                            ((java.lang.Object)
                                    (((java.util.List) (this.inventoryItemStacks))
                                            .get(((int) (j)))));
            if (!net.minecraft.item.ItemStack.areItemStacksEqual(itemStackValue, itemStack)) {
                itemStackValue = itemStack == null ? null : itemStack.copy();
                ((java.util.List) (this.inventoryItemStacks))
                        .set(((int) (j)), ((java.lang.Object) (itemStackValue)));
                int k = 0;

                while (k < ((int) (((java.util.List) (this.crafters)).size()))) {
                    ((net.minecraft.inventory.ICrafting)
                                    ((java.lang.Object)
                                            (((java.util.List) (this.crafters)).get(((int) (k))))))
                            .sendSlotContents(this, j, itemStackValue);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public void onCraftGuiOpened(ICrafting iCrafting) {
        try {
            if (this.crafters.contains(iCrafting)) {
                throw new java.lang.IllegalArgumentException(net.minecraft.inventory.Container.Cv);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.inventory.Container.method_9a70918140b0a7642dba(
                    illegalargumentexception);
        }

        this.crafters.add(iCrafting);
        iCrafting.updateCraftingInventory(this, this.getInventory());
        this.detectAndSendChanges();
    }

    public boolean getCanCraft(PlayerEntity playerEntity) {
        try {
            if (!this.playerList.contains(playerEntity)) {
                return true;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.inventory.Container.method_9a70918140b0a7642dba(
                    illegalargumentexception);
        }

        return false;
    }

    protected void retrySlotClick(
            int p171_0, int p171_1, boolean p171_2, PlayerEntity playerEntity) {
        this.slotClick(p171_0, p171_1, 1, playerEntity);
    }

    public boolean canDragIntoSlot(Slot slot) {
        return true;
    }

    public static int extractDragMode(int p171_0) {
        return p171_0 >> 2 & 3;
    }

    public static boolean canAddItemToSlot(Slot slot, ItemStack itemStack, boolean p171_2) {
        int i = slot != null && slot.getHasStack() ? 0 : 1;
        if (slot != null
                && slot.getHasStack()
                && itemStack != null
                && itemStack.isItemEqual(slot.getStack())
                && net.minecraft.item.ItemStack.areItemStackTagsEqual(slot.getStack(), itemStack)) {
            int j = p171_2 ? 0 : itemStack.stackSize;
            i |= slot.getStack().stackSize + j <= itemStack.getMaxStackSize() ? 1 : 0;
        }

        return (i != 0);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int p171_1) {
        net.minecraft.inventory.Slot slot =
                (net.minecraft.inventory.Slot) this.inventorySlots.get(p171_1);

        try {
            if (slot != null) {
                return slot.getStack();
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.inventory.Container.method_9a70918140b0a7642dba(
                    illegalargumentexception);
        }

        return null;
    }

    public static int calcRedstoneFromInventory(IInventory iInventory) {
        int i = cR();
        if (iInventory == null) {
            return 0;
        }

        int j = 0;
        float fx = 0.0F;
        int k = 0;

        while (k < iInventory.getSizeInventory()) {
            ItemStack itemStack = iInventory.getStackInSlot(k);
            if (itemStack != null) {
                fx +=
                        (float) itemStack.stackSize
                                / Math.min(
                                        iInventory.getInventoryStackLimit(),
                                        itemStack.getMaxStackSize());
                j++;
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        fx /= iInventory.getSizeInventory();
        return MathHelper.floor(fx * 14.0F) + (j > 0 ? 1 : 0);
    }

    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return true;
    }

    public void removeCraftingFromCrafters(ICrafting iCrafting) {
        this.crafters.remove(iCrafting);
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    protected boolean mergeItemStack(ItemStack itemStack, int i, int j, boolean flag) {
        int j1 = aW();
        byte b0 = 0;
        int k = j1;
        int l = i;
        if (flag) {
            l = j - 1;
        }

        if (itemStack.isStackable()) {
            while (itemStack.stackSize > 0 && (!flag && l < j || flag && l >= i)) {
                Slot slot = this.inventorySlots.get(l);
                ItemStack itemStackValue = slot.getStack();
                label101:
                if (itemStackValue != null
                        && itemStackValue.getItem() == itemStack.getItem()
                        && (!itemStack.getHasSubtypes()
                                || itemStack.getItemDamage() == itemStackValue.getItemDamage())
                        && ItemStack.areItemStackTagsEqual(itemStack, itemStackValue)) {
                    int stackSize = itemStackValue.stackSize + itemStack.stackSize;
                    if (stackSize <= itemStack.getMaxStackSize()) {
                        itemStack.stackSize = 0;
                        itemStackValue.stackSize = stackSize;
                        slot.onSlotChanged();
                        b0 = 1;
                        if (k != 0) {
                            break label101;
                        }
                    }

                    if (itemStackValue.stackSize < itemStack.getMaxStackSize()) {
                        itemStack.stackSize =
                                itemStack.stackSize
                                        - (itemStack.getMaxStackSize() - itemStackValue.stackSize);
                        itemStackValue.stackSize = itemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        b0 = 1;
                    }
                }

                if (flag) {
                    l--;
                    if (k != 0) {
                        continue;
                    }
                }

                l++;
                if (k == 0) {
                    break;
                }
            }
        }

        if (itemStack.stackSize > 0) {
            {
                if (flag) {
                    l = j - 1;
                    if (!(k != 0)) {

                        l = i;
                    }
                } else {

                    l = i;
                }
            }

            while (!flag && l < j || flag && l >= i) {
                Slot slotValue = this.inventorySlots.get(l);
                ItemStack itemStackValue2 = slotValue.getStack();
                if (itemStackValue2 == null) {
                    slotValue.putStack(itemStack.copy());
                    slotValue.onSlotChanged();
                    itemStack.stackSize = 0;
                    b0 = 1;
                    if (k != 0) {
                        break;
                    }
                }

                if (flag) {
                    l--;
                    if (k != 0) {
                        continue;
                    }
                }

                l++;
                if (k == 0) {
                    break;
                }
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    // region String decoding support

    // endregion

    public static int getDragEvent(int i) {
        return i & 3;
    }

    public Slot getSlotFromInventory(IInventory iInventory, int i) {
        int l = cR();
        int k = 0;
        int j = l;

        while (k < this.inventorySlots.size()) {
            Slot slot = this.inventorySlots.get(k);
            if (slot.isHere(iInventory, i)) {
                return slot;
            }

            k++;
            if (j != 0) {
                break;
            }
        }

        return null;
    }

    public short getNextTransactionID(InventoryPlayer inventoryPlayer) {
        this.transactionID++;
        return this.transactionID;
    }

    public static void computeStackSize(Set set, int i, ItemStack itemStack, int j) {
        int k = cR();
        switch (i) {
            case 0:
                itemStack.stackSize = MathHelper.floor((float) itemStack.stackSize / set.size());
                if (k == 0) {
                    break;
                }
            case 1:
                itemStack.stackSize = 1;
                if (k == 0) {
                    break;
                }
            case 2:
                itemStack.stackSize = itemStack.getItem().getItemStackLimit();
        }

        itemStack.stackSize += j;
    }

    public ItemStack slotClick(int i, int j, int k, PlayerEntity playerEntity) {
        ItemStack itemStack = null;
        int k4 = cR();
        InventoryPlayer bdI = playerEntity.inventory;
        int l = k4;
        if (k == 5) {
            label588:
            {
                int j1 = this.dragEvent;
                this.dragEvent = getDragEvent(j);
                if ((j1 != 1 || this.dragEvent != 2) && j1 != this.dragEvent) {
                    this.resetDrag();
                    if (l == 0) {
                        break label588;
                    }
                }

                if (bdI.getItemStack() == null) {
                    this.resetDrag();
                    if (l == 0) {
                        break label588;
                    }
                }

                if (this.dragEvent == 0) {
                    this.dragMode = extractDragMode(j);
                    if (isValidDragMode(this.dragMode, playerEntity)) {
                        this.dragEvent = 1;
                        this.dragSlots.clear();
                        if (l == 0) {
                            break label588;
                        }
                    }

                    this.resetDrag();
                    if (l == 0) {
                        break label588;
                    }
                }

                if (this.dragEvent == 1) {
                    Slot slot = this.inventorySlots.get(i);
                    if (slot != null
                            && canAddItemToSlot(slot, bdI.getItemStack(), true)
                            && slot.isItemValid(bdI.getItemStack())
                            && bdI.getItemStack().stackSize > this.dragSlots.size()
                            && this.canDragIntoSlot(slot)) {
                        this.dragSlots.add(slot);
                    }

                    if (l == 0) {
                        break label588;
                    }
                }

                if (this.dragEvent == 2) {
                    if (!this.dragSlots.isEmpty()) {
                        ItemStack itemStackValue = bdI.getItemStack().copy();
                        int stackSize = bdI.getItemStack().stackSize;

                        for (java.util.Iterator<?> __recoveryIterator171_21361 =
                                        (this.dragSlots).iterator();
                                (__recoveryIterator171_21361).hasNext(); ) {
                            Slot slotValue = (Slot) (__recoveryIterator171_21361).next();
                            if (slotValue != null
                                    && canAddItemToSlot(slotValue, bdI.getItemStack(), true)
                                    && slotValue.isItemValid(bdI.getItemStack())
                                    && bdI.getItemStack().stackSize >= this.dragSlots.size()
                                    && this.canDragIntoSlot(slotValue)) {
                                ItemStack itemStackValue2 = itemStackValue.copy();
                                int k1 =
                                        slotValue.getHasStack()
                                                ? slotValue.getStack().stackSize
                                                : 0;
                                computeStackSize(
                                        this.dragSlots, this.dragMode, itemStackValue2, k1);
                                if (itemStackValue2.stackSize > itemStackValue2.getMaxStackSize()) {
                                    itemStackValue2.stackSize = itemStackValue2.getMaxStackSize();
                                }

                                if (itemStackValue2.stackSize
                                        > slotValue.getItemStackLimit(itemStackValue2)) {
                                    itemStackValue2.stackSize =
                                            slotValue.getItemStackLimit(itemStackValue2);
                                }

                                stackSize -= itemStackValue2.stackSize - k1;
                                slotValue.putStack(itemStackValue2);
                            }

                            if (l != 0) {
                                break;
                            }
                        }

                        itemStackValue.stackSize = stackSize;
                        if (itemStackValue.stackSize <= 0) {
                            itemStackValue = null;
                        }

                        bdI.setItemStack(itemStackValue);
                    }

                    this.resetDrag();
                    if (l == 0) {
                        break label588;
                    }
                }

                this.resetDrag();
            }

            if (l == 0) {
                return itemStack;
            }
        }

        if (this.dragEvent != 0) {
            this.resetDrag();
            if (l == 0) {
                return itemStack;
            }
        }

        if ((k == 0 || k == 1) && (j == 0 || j == 1)) {
            if (i == -999) {
                if (bdI.getItemStack() == null) {
                    return itemStack;
                }

                if (j == 0) {
                    playerEntity.dropPlayerItemWithRandomChoice(bdI.getItemStack(), true);
                    bdI.setItemStack((ItemStack) null);
                }

                if (j != 1) {
                    return itemStack;
                }

                playerEntity.dropPlayerItemWithRandomChoice(bdI.getItemStack().splitStack(1), true);
                if (bdI.getItemStack().stackSize != 0) {
                    return itemStack;
                }

                bdI.setItemStack((ItemStack) null);
                if (l == 0) {
                    return itemStack;
                }
            }

            if (k == 1) {
                if (i < 0) {
                    return null;
                }

                Slot slotValue2 = this.inventorySlots.get(i);
                if (slotValue2 != null && slotValue2.canTakeStack(playerEntity)) {
                    ItemStack itemStackValue3 = this.transferStackInSlot(playerEntity, i);
                    if (itemStackValue3 != null) {
                        Item item = itemStackValue3.getItem();
                        itemStack = itemStackValue3.copy();
                        if (slotValue2.getStack() != null
                                && slotValue2.getStack().getItem() == item) {
                            this.retrySlotClick(i, j, true, playerEntity);
                        }
                    }
                }
            } else {
                if (i < 0) {
                    return null;
                }

                Slot slotValue3 = this.inventorySlots.get(i);
                if (slotValue3 != null) {
                    ItemStack itemStackValue4 = slotValue3.getStack();
                    ItemStack itemStackValue5 = bdI.getItemStack();
                    if (itemStackValue4 != null) {
                        itemStack = itemStackValue4.copy();
                    }

                    label556:
                    {
                        if (itemStackValue4 == null) {
                            if (itemStackValue5 == null
                                    || !slotValue3.isItemValid(itemStackValue5)) {
                                break label556;
                            }

                            int l2 = j == 0 ? itemStackValue5.stackSize : 1;
                            if (l2 > slotValue3.getItemStackLimit(itemStackValue5)) {
                                l2 = slotValue3.getItemStackLimit(itemStackValue5);
                            }

                            if (itemStackValue5.stackSize >= l2) {
                                slotValue3.putStack(itemStackValue5.splitStack(l2));
                            }

                            if (itemStackValue5.stackSize != 0) {
                                break label556;
                            }

                            bdI.setItemStack((ItemStack) null);
                            if (l == 0) {
                                break label556;
                            }
                        }

                        label462:
                        if (slotValue3.canTakeStack(playerEntity)) {
                            if (itemStackValue5 == null) {
                                int i3 =
                                        j == 0
                                                ? itemStackValue4.stackSize
                                                : (itemStackValue4.stackSize + 1) / 2;
                                ItemStack itemStackValue6 = slotValue3.decrStackSize(i3);
                                bdI.setItemStack(itemStackValue6);
                                if (itemStackValue4.stackSize == 0) {
                                    slotValue3.putStack((ItemStack) null);
                                }

                                slotValue3.onPickupFromSlot(playerEntity, bdI.getItemStack());
                                if (l == 0) {
                                    break label462;
                                }
                            }

                            if (slotValue3.isItemValid(itemStackValue5)) {
                                if (itemStackValue4.getItem() == itemStackValue5.getItem()
                                        && itemStackValue4.getItemDamage()
                                                == itemStackValue5.getItemDamage()
                                        && ItemStack.areItemStackTagsEqual(
                                                itemStackValue4, itemStackValue5)) {
                                    int j3 = j == 0 ? itemStackValue5.stackSize : 1;
                                    if (j3
                                            > slotValue3.getItemStackLimit(itemStackValue5)
                                                    - itemStackValue4.stackSize) {
                                        j3 =
                                                slotValue3.getItemStackLimit(itemStackValue5)
                                                        - itemStackValue4.stackSize;
                                    }

                                    if (j3
                                            > itemStackValue5.getMaxStackSize()
                                                    - itemStackValue4.stackSize) {
                                        j3 =
                                                itemStackValue5.getMaxStackSize()
                                                        - itemStackValue4.stackSize;
                                    }

                                    itemStackValue5.splitStack(j3);
                                    if (itemStackValue5.stackSize == 0) {
                                        bdI.setItemStack((ItemStack) null);
                                    }

                                    itemStackValue4.stackSize += j3;
                                    if (l == 0) {
                                        break label462;
                                    }
                                }

                                if (itemStackValue5.stackSize
                                        > slotValue3.getItemStackLimit(itemStackValue5)) {
                                    break label462;
                                }

                                slotValue3.putStack(itemStackValue5);
                                bdI.setItemStack(itemStackValue4);
                                if (l == 0) {
                                    break label462;
                                }
                            }

                            if (itemStackValue4.getItem() == itemStackValue5.getItem()
                                    && itemStackValue5.getMaxStackSize() > 1
                                    && (!itemStackValue4.getHasSubtypes()
                                            || itemStackValue4.getItemDamage()
                                                    == itemStackValue5.getItemDamage())
                                    && ItemStack.areItemStackTagsEqual(
                                            itemStackValue4, itemStackValue5)) {
                                int k3 = itemStackValue4.stackSize;
                                if (k3 > 0
                                        && k3 + itemStackValue5.stackSize
                                                <= itemStackValue5.getMaxStackSize()) {
                                    itemStackValue5.stackSize += k3;
                                    itemStackValue4 = slotValue3.decrStackSize(k3);
                                    if (itemStackValue4.stackSize == 0) {
                                        slotValue3.putStack((ItemStack) null);
                                    }

                                    slotValue3.onPickupFromSlot(playerEntity, bdI.getItemStack());
                                }
                            }
                        }
                    }

                    slotValue3.onSlotChanged();
                }
            }
        } else {
            if (k == 2 && j >= 0 && j < 9) {
                Slot slotValue4 = this.inventorySlots.get(i);
                if (!slotValue4.canTakeStack(playerEntity)) {
                    return itemStack;
                }

                ItemStack itemStackValue7 = bdI.getStackInSlot(j);
                int l3 =
                        itemStackValue7 != null
                                        && (slotValue4.inventory != bdI
                                                || !slotValue4.isItemValid(itemStackValue7))
                                ? 0
                                : 1;
                int j2 = -1;
                if (l3 == 0) {
                    j2 = bdI.cR();
                    l3 |= j2 > -1 ? 1 : 0;
                }

                label419:
                {
                    if (slotValue4.getHasStack() && l3 != 0) {
                        ItemStack itemStackValue8 = slotValue4.getStack();
                        bdI.setInventorySlotContents(j, itemStackValue8.copy());
                        if ((slotValue4.inventory != bdI
                                        || !slotValue4.isItemValid(itemStackValue7))
                                && itemStackValue7 != null) {
                            if (j2 <= -1) {
                                break label419;
                            }

                            bdI.addItemStackToInventory(itemStackValue7);
                            slotValue4.decrStackSize(itemStackValue8.stackSize);
                            slotValue4.putStack((ItemStack) null);
                            slotValue4.onPickupFromSlot(playerEntity, itemStackValue8);
                            if (l == 0) {
                                break label419;
                            }
                        }

                        slotValue4.decrStackSize(itemStackValue8.stackSize);
                        slotValue4.putStack(itemStackValue7);
                        slotValue4.onPickupFromSlot(playerEntity, itemStackValue8);
                        if (l == 0) {
                            break label419;
                        }
                    }

                    if (!slotValue4.getHasStack()
                            && itemStackValue7 != null
                            && slotValue4.isItemValid(itemStackValue7)) {
                        bdI.setInventorySlotContents(j, (ItemStack) null);
                        slotValue4.putStack(itemStackValue7);
                    }
                }

                if (l == 0) {
                    return itemStack;
                }
            }

            if (k == 3
                    && playerEntity.capabilities.isCreativeMode
                    && bdI.getItemStack() == null
                    && i >= 0) {
                Slot slotValue5 = this.inventorySlots.get(i);
                if (slotValue5 == null || !slotValue5.getHasStack()) {
                    return itemStack;
                }

                ItemStack itemStackValue9 = slotValue5.getStack().copy();
                itemStackValue9.stackSize = itemStackValue9.getMaxStackSize();
                bdI.setItemStack(itemStackValue9);
                if (l == 0) {
                    return itemStack;
                }
            }

            if (k == 4 && bdI.getItemStack() == null && i >= 0) {
                Slot slotValue6 = this.inventorySlots.get(i);
                if (slotValue6 == null
                        || !slotValue6.getHasStack()
                        || !slotValue6.canTakeStack(playerEntity)) {
                    return itemStack;
                }

                ItemStack itemStackValue10 =
                        slotValue6.decrStackSize(j == 0 ? 1 : slotValue6.getStack().stackSize);
                slotValue6.onPickupFromSlot(playerEntity, itemStackValue10);
                playerEntity.dropPlayerItemWithRandomChoice(itemStackValue10, true);
                if (l == 0) {
                    return itemStack;
                }
            }

            if (k == 6 && i >= 0) {
                Slot slotValue7 = this.inventorySlots.get(i);
                ItemStack itemStackValue11 = bdI.getItemStack();
                if (itemStackValue11 != null
                        && (slotValue7 == null
                                || !slotValue7.getHasStack()
                                || !slotValue7.canTakeStack(playerEntity))) {
                    int i2 = j == 0 ? 0 : this.inventorySlots.size() - 1;
                    int k2 = j == 0 ? 1 : -1;
                    int i4 = 0;

                    while (i4 < 2) {
                        int j4 = i2;

                        while (j4 >= 0
                                && j4 < this.inventorySlots.size()
                                && itemStackValue11.stackSize
                                        < itemStackValue11.getMaxStackSize()) {
                            Slot slotValue8 = this.inventorySlots.get(j4);
                            if (slotValue8.getHasStack()
                                    && canAddItemToSlot(slotValue8, itemStackValue11, true)
                                    && slotValue8.canTakeStack(playerEntity)
                                    && this.canMergeSlot(itemStackValue11, slotValue8)
                                    && (i4 != 0
                                            || slotValue8.getStack().stackSize
                                                    != slotValue8.getStack().getMaxStackSize())) {
                                int l1 =
                                        Math.min(
                                                itemStackValue11.getMaxStackSize()
                                                        - itemStackValue11.stackSize,
                                                slotValue8.getStack().stackSize);
                                ItemStack itemStackValue12 = slotValue8.decrStackSize(l1);
                                itemStackValue11.stackSize += l1;
                                if (itemStackValue12.stackSize <= 0) {
                                    slotValue8.putStack((ItemStack) null);
                                }

                                slotValue8.onPickupFromSlot(playerEntity, itemStackValue12);
                            }

                            j4 += k2;
                            if (l != 0) {
                                break;
                            }
                        }

                        i4++;
                        if (l != 0) {
                            break;
                        }
                    }
                }

                this.detectAndSendChanges();
            }
        }

        return itemStack;
    }

    protected Slot addSlotToContainer(Slot slot) {
        slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(slot);
        this.inventoryItemStacks.add(null);
        return slot;
    }

    // region String decoding support

    // endregion

    protected void resetDrag() {
        this.dragEvent = 0;
        this.dragSlots.clear();
    }
}
