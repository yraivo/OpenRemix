package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S06PacketUpdateHealth implements Packet {
    private float saturationLevel;
    private int foodLevel;
    private float health;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180750_a((INetHandlerPlayClient) packetHandler);
    }

    public S06PacketUpdateHealth(
            float healthValue, int foodLevelValue, float saturationLevelValue) {
        this.health = healthValue;
        this.foodLevel = foodLevelValue;
        this.saturationLevel = saturationLevelValue;
    }

    public float getSaturationLevel() {
        return this.saturationLevel;
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.health);
        packetBuffer.writeVarIntToBuffer(this.foodLevel);
        packetBuffer.writeFloat(this.saturationLevel);
    }

    public void func_180750_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleUpdateHealth(this);
    }

    public float getHealth() {
        return this.health;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.health = packetBuffer.readFloat();
        this.foodLevel = packetBuffer.readVarIntFromBuffer();
        this.saturationLevel = packetBuffer.readFloat();
    }

    public S06PacketUpdateHealth() {}
}
