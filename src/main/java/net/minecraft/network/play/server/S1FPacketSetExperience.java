package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S1FPacketSetExperience implements Packet {
    private float field_149401_a;
    private int totalExperience;
    private int level;

    public int getTotalExperience() {
        return this.totalExperience;
    }

    public S1FPacketSetExperience() {}

    public int getLevel() {
        return this.level;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_149401_a = packetBuffer.readFloat();
        this.level = packetBuffer.readVarIntFromBuffer();
        this.totalExperience = packetBuffer.readVarIntFromBuffer();
    }

    public float getExperienceProgress() {
        return this.field_149401_a;
    }

    public void func_180749_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSetExperience(this);
    }

    public S1FPacketSetExperience(float p171_0, int totalExperienceValue, int levelValue) {
        this.field_149401_a = p171_0;
        this.totalExperience = totalExperienceValue;
        this.level = levelValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180749_a((INetHandlerPlayClient) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.field_149401_a);
        packetBuffer.writeVarIntToBuffer(this.level);
        packetBuffer.writeVarIntToBuffer(this.totalExperience);
    }
}
