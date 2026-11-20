package net.minecraft.tileentity;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;

public class TileEntityCommandBlock extends TileEntity {
    private final CommandBlockLogic commandBlockLogic = new TileCommandBlockLogic(this);

    @Override
    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        this.commandBlockLogic.writeDataToNBT(compoundTag);
    }

    @Override
    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.commandBlockLogic.readDataFromNBT(compoundTag);
    }

    @Override
    public Packet getDescriptionPacket() {
        net.minecraft.nbt.CompoundTag compoundTag = new net.minecraft.nbt.CompoundTag();
        this.writeToNBT(compoundTag);
        return new net.minecraft.network.play.server.S35PacketUpdateTileEntity(this.pos, 2, compoundTag);
    }

    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }

    public CommandResultStats getCommandResultStats() {
        return this.commandBlockLogic.getCommandResultStats();
    }
}
