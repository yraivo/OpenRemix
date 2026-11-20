package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

import java.io.IOException;

public class S1DPacketEntityEffect implements Packet {
    private int duration;
    private byte showParticles;
    private byte effectId;
    private int entityId;
    private byte amplifier;

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityEffect(this);
    }

    public byte getAmplifier() {
        return this.amplifier;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.effectId);
        packetBuffer.writeByte(this.amplifier);
        packetBuffer.writeVarIntToBuffer(this.duration);
        packetBuffer.writeByte(this.showParticles);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.effectId = packetBuffer.readByte();
        this.amplifier = packetBuffer.readByte();
        this.duration = packetBuffer.readVarIntFromBuffer();
        this.showParticles = packetBuffer.readByte();
    }

    public int getDuration() {
        return this.duration;
    }

    public S1DPacketEntityEffect(int entityIdValue, PotionEffect potionEffect) {
        {
            int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
            this.entityId = entityIdValue;
            this.effectId = (byte) (potionEffect.getPotionID() & 0xFF);
            this.amplifier = (byte) (potionEffect.getAmplifier() & 0xFF);
            int[] aint = aint1;
            if (potionEffect.getDuration() > 32767) {
                this.duration = 32767;
                if (!(aint == null)) {

                    this.duration = potionEffect.getDuration();
                }
            } else {

                this.duration = potionEffect.getDuration();
            }
        }

        this.showParticles = (byte) (potionEffect.getIsShowParticles() ? 1 : 0);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public boolean isDurationCapped() {
        return this.duration == 32767;
    }

    public boolean shouldShowParticles() {
        return this.showParticles != 0;
    }

    public S1DPacketEntityEffect() {}

    public int getEntityId() {
        return this.entityId;
    }

    public byte getEffectId() {
        return this.effectId;
    }
}
