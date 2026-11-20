package net.minecraft.network.play.server;

import net.minecraft.entity.DataWatcher;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.List;

public class S1CPacketEntityMetadata implements Packet {
    private List watchableObjects;
    private int entityId;

    public S1CPacketEntityMetadata() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        net.minecraft.entity.DataWatcher.writeWatchedListToPacketBuffer(this.watchableObjects, packetBuffer);
    }

    public void func_180748_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityMetadata(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180748_a((INetHandlerPlayClient) packetHandler);
    }

    public S1CPacketEntityMetadata(int entityIdValue, DataWatcher dataWatcher, boolean flag) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.entityId = entityIdValue;
        int[] aint = aint1;
        if (flag) {
            this.watchableObjects = dataWatcher.getAllWatched();
            if (aint == null) {
                return;
            }
        }

        this.watchableObjects = dataWatcher.getChanged();
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.watchableObjects = net.minecraft.entity.DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public List getWatchableObjects() {
        return this.watchableObjects;
    }
}
