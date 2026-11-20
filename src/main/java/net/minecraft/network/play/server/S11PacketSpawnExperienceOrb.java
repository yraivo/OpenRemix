package net.minecraft.network.play.server;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S11PacketSpawnExperienceOrb implements Packet {
    private int xpValue;
    private int posX;
    private int posZ;
    private int posY;
    private int entityID;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180719_a((INetHandlerPlayClient) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeShort(this.xpValue);
    }

    public int getEntityID() {
        return this.entityID;
    }

    public S11PacketSpawnExperienceOrb(EntityXPOrb entityXPOrb) {
        this.entityID = entityXPOrb.getEntityId();
        this.posX = MathHelper.floor(entityXPOrb.posX * 32.0);
        this.posY = MathHelper.floor(entityXPOrb.posY * 32.0);
        this.posZ = MathHelper.floor(entityXPOrb.posZ * 32.0);
        this.xpValue = entityXPOrb.getXpValue();
    }

    public int getX() {
        return this.posX;
    }

    public int getZ() {
        return this.posZ;
    }

    public void func_180719_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnExperienceOrb(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.xpValue = packetBuffer.readShort();
    }

    public int getY() {
        return this.posY;
    }

    public int getXPValue() {
        return this.xpValue;
    }

    public S11PacketSpawnExperienceOrb() {}
}
