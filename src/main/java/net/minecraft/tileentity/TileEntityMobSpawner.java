package net.minecraft.tileentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox {
    private final MobSpawnerBaseLogic spawnerLogic = new TileSpawnerLogic(this);

    private static String vI;

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        this.spawnerLogic.writeToNBT(compoundTag);
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.spawnerLogic.readFromNBT(compoundTag);
    }

    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return this.spawnerLogic;
    }

    public boolean receiveClientEvent(int i, int j) {
        return this.spawnerLogic.setDelayToMin(i) ? true : super.receiveClientEvent(i, j);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public Packet getDescriptionPacket() {
        CompoundTag compoundTag = new CompoundTag();
        this.writeToNBT(compoundTag);
        compoundTag.removeTag(vI);
        return new S35PacketUpdateTileEntity(this.pos, 1, compoundTag);
    }

    static {
        vI = "SpawnPotentials";
    }

    public void update() {
        this.spawnerLogic.updateSpawner();
    }
}
