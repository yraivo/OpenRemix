package net.minecraft.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;

public class BlockJukeboxTileEntityJukebox extends TileEntity {

    private ItemStack record;

    public ItemStack getRecord() {
        return this.record;
    }

    public void setRecord(ItemStack bBeValue) {
        this.record = bBeValue;
        this.markDirty();
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        if (this.getRecord() != null) {
            compoundTag.setTag("RecordItem", this.getRecord().writeToNBT(new CompoundTag()));
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        super.readFromNBT(compoundTag);
        int i = j;
        if (compoundTag.hasKey("RecordItem", 10)) {
            this.setRecord(
                    ItemStack.loadItemStackFromNBT(compoundTag.getCompoundTag("RecordItem")));
            if (i == 0) {
                return;
            }
        }

        if (compoundTag.getInteger("Record") > 0) {
            this.setRecord(new ItemStack(Item.getItemById(compoundTag.getInteger("Record")), 1, 0));
        }
    }


}
