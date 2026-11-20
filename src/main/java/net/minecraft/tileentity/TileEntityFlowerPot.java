package net.minecraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityFlowerPot extends TileEntity {

    private Item flowerPotItem;
    private static String[] packedTextFragments;
    private int flowerPotData;

    public Item getFlowerPotItem() {
        return this.flowerPotItem;
    }

    static {
        net.minecraft.tileentity.TileEntityFlowerPot.packedTextFragments = new String[3];
        net.minecraft.tileentity.TileEntityFlowerPot.packedTextFragments[0] = new String("");
    }

    public Packet getDescriptionPacket() {
        CompoundTag compoundTag = new CompoundTag();
        this.writeToNBT(compoundTag);
        compoundTag.removeTag("Item");
        compoundTag.setInteger("Item", Item.getIdFromItem(this.flowerPotItem));
        return new S35PacketUpdateTileEntity(this.pos, 5, compoundTag);
    }

    public TileEntityFlowerPot() {}

    public TileEntityFlowerPot(Item brdValue, int flowerPotDataValue) {
        this.flowerPotItem = brdValue;
        this.flowerPotData = flowerPotDataValue;
    }

    public int getFlowerPotData() {
        return this.flowerPotData;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        {
            MobSpawnerBaseLogic.GU();

            super.readFromNBT(compoundTag);

            if (compoundTag.hasKey("Item", 8)) {
                this.flowerPotItem = Item.getByNameOrId(compoundTag.getString("Item"));

                
            } else {

                this.flowerPotItem = Item.getItemById(compoundTag.getInteger("Item"));
            }
        }

        this.flowerPotData = compoundTag.getInteger("Data");
    }

    public void setFlowerPotData(Item brdValue, int flowerPotDataValue) {
        this.flowerPotItem = brdValue;
        this.flowerPotData = flowerPotDataValue;
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        ResourceLocation nameForObject =
                (ResourceLocation) Item.itemRegistry.getNameForObject(this.flowerPotItem);
        compoundTag.setString(
                "Item", nameForObject == null ? packedTextFragments[0] : nameForObject.toString());
        compoundTag.setInteger("Data", this.flowerPotData);
    }
}
