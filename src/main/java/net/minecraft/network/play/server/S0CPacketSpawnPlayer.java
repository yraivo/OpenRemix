package net.minecraft.network.play.server;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class S0CPacketSpawnPlayer implements Packet {
    private int y;
    private UUID playerId;
    private int currentItem;
    private byte yaw;
    private int entityId;
    private int x;
    private List field_148958_j;
    private DataWatcher watcher;
    private int z;
    private byte pitch;

    public UUID getPlayer() {
        return this.playerId;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeUuid(this.playerId);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeShort(this.currentItem);
        this.watcher.writeTo(packetBuffer);
    }

    public byte getPitch() {
        return this.pitch;
    }

    public List getWatchableObjects() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.watcher.getAllWatched();
        }

        return this.field_148958_j;
    }

    public int getX() {
        return this.x;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnPlayer(this);
    }

    public int getY() {
        return this.y;
    }

    public int getEntityID() {
        return this.entityId;
    }

    public int getCurrentItemID() {
        return this.currentItem;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.playerId = packetBuffer.readUuid();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.currentItem = packetBuffer.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }

    public S0CPacketSpawnPlayer(PlayerEntity playerEntity) {
        this.entityId = playerEntity.getEntityId();
        this.playerId = playerEntity.getGameProfile().getId();
        S14PacketEntity.getLegacyPacketFlowMarker();
        this.x = MathHelper.floor(playerEntity.posX * 32.0);
        this.y = MathHelper.floor(playerEntity.posY * 32.0);
        this.z = MathHelper.floor(playerEntity.posZ * 32.0);
        this.yaw = (byte) (playerEntity.rotationYaw * 256.0F / 360.0F);
        this.pitch = (byte) (playerEntity.rotationPitch * 256.0F / 360.0F);
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        this.currentItem = itemStack == null ? 0 : Item.getIdFromItem(itemStack.getItem());
        this.watcher = playerEntity.getDataWatcher();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            S14PacketEntity.setLegacyPacketFlowMarker(new int[2]);
        }
    }

    public byte getYaw() {
        return this.yaw;
    }

    public int getZ() {
        return this.z;
    }

    public S0CPacketSpawnPlayer() {}
}
