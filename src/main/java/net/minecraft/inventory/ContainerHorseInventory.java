package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container {
    private EntityHorse theHorse;
    private IInventory horseInventory;

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.horseInventory.isUseableByPlayer(playerEntity)
                && this.theHorse.isEntityAlive()
                && this.theHorse.getDistanceToEntity(playerEntity) < 8.0F;
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int i) {
        ItemStack itemStack = null;
        int k = Container.cR();
        Slot slot = (Slot) this.inventorySlots.get(i);
        int j = k;
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackValue = slot.getStack();
            itemStack = itemStackValue.copy();
            if (i < this.horseInventory.getSizeInventory()) {
                if (!this.mergeItemStack(
                        itemStackValue,
                        this.horseInventory.getSizeInventory(),
                        this.inventorySlots.size(),
                        true)) {
                    return null;
                }
            } else if (this.getSlot(1).isItemValid(itemStackValue)
                    && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(itemStackValue, 1, 2, false)) {
                    return null;
                }
            } else if (this.getSlot(0).isItemValid(itemStackValue)) {
                if (!this.mergeItemStack(itemStackValue, 0, 1, false)) {
                    return null;
                }
            } else if (this.horseInventory.getSizeInventory() <= 2
                    || !this.mergeItemStack(
                            itemStackValue, 2, this.horseInventory.getSizeInventory(), false)) {
                return null;
            }

            if (itemStackValue.stackSize == 0) {
                slot.putStack((ItemStack) null);
                if (j == 0) {
                    return itemStack;
                }
            }

            slot.onSlotChanged();
        }

        return itemStack;
    }

    public ContainerHorseInventory(
            IInventory iInventory,
            IInventory iInventoryValue,
            EntityHorse entityHorse,
            PlayerEntity playerEntity) {
        this(iInventory, iInventoryValue, entityHorse, playerEntity, Container.aW());
    }

    private ContainerHorseInventory(
            IInventory iInventory,
            IInventory bkDValue,
            EntityHorse bkJValue,
            PlayerEntity playerEntity,
            int initialFlowToken) {
        super();

        int k1 = initialFlowToken;

        this.horseInventory = bkDValue;
        this.theHorse = bkJValue;
        int i = k1;
        byte b0 = 3;
        bkDValue.openInventory(playerEntity);
        int j = (b0 - 4) * 18;
        this.addSlotToContainer(new ContainerHorseInventorySlot1(this, bkDValue, 0, 8, 18));
        this.addSlotToContainer(
                new ContainerHorseInventorySlot2(this, bkDValue, 1, 8, 36, bkJValue));
        if (bkJValue.isChested()) {
            int k = 0;

            while (k < b0) {
                int l = 0;

                while (l < 5) {
                    this.addSlotToContainer(
                            new Slot(bkDValue, 2 + l + k * 5, 80 + l * 18, 18 + k * 18));
                    l++;
                    if (i == 0) {
                        break;
                    }
                }

                k++;
                if (i == 0) {
                    break;
                }
            }
        }

        int i1 = 0;

        label38:
        while (i1 < 3) {
            int j1 = 0;

            while (true) {
                if (j1 < 9) {
                    this.addSlotToContainer(
                            new Slot(iInventory, j1 + i1 * 9 + 9, 8 + j1 * 18, 102 + i1 * 18 + j));
                    j1++;
                    if (i != 0) {
                        continue;
                    }
                }

                i1++;
                if (i == 0) {
                    break label38;
                }
                break;
            }
        }

        i1 = 0;

        while (i1 < 9) {
            this.addSlotToContainer(new Slot(iInventory, i1, 8 + i1 * 18, 160 + j));
            i1++;
            if (i == 0) {
                break;
            }
        }
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.horseInventory.closeInventory(playerEntity);
    }
}
