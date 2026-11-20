package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {

    private TileEntityEnderChest associatedChest;

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.associatedChest != null
                        && !this.associatedChest.canBeUsed(playerEntity)
                ? false
                : super.isUseableByPlayer(playerEntity);
    }

    public NBTTagList saveInventoryToNBT() {
        int k = Container.cR();
        NBTTagList nbtTagList = new NBTTagList();
        int i = k;
        int j = 0;

        while (j < this.getSizeInventory()) {
            ItemStack itemStack = this.getStackInSlot(j);
            if (itemStack != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.setByte("Slot", (byte) j);
                itemStack.writeToNBT(compoundTag);
                nbtTagList.appendTag(compoundTag);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return nbtTagList;
    }

    public void setChestTileEntity(TileEntityEnderChest btQValue) {
        this.associatedChest = btQValue;
    }


    public void openInventory(PlayerEntity playerEntity) {
        if (this.associatedChest != null) {
            this.associatedChest.openChest();
        }

        super.openInventory(playerEntity);
    }

    public void loadInventoryFromNBT(NBTTagList nbtTagList) {
        int l = Container.aW();
        int j = 0;
        int i = l;

        while (j < this.getSizeInventory()) {
            this.setInventorySlotContents(j, (ItemStack) null);
            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < nbtTagList.tagCount()) {
            CompoundTag compoundTag = nbtTagList.getCompoundTagAt(j);
            int k = compoundTag.getByte("Slot") & 255;
            if (k >= 0 && k < this.getSizeInventory()) {
                this.setInventorySlotContents(k, ItemStack.loadItemStackFromNBT(compoundTag));
            }

            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public void closeInventory(PlayerEntity playerEntity) {
        if (this.associatedChest != null) {
            this.associatedChest.closeChest();
        }

        super.closeInventory(playerEntity);
        this.associatedChest = null;
    }

    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }
}
