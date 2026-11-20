package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import org.apache.commons.lang3.Validate;

import java.io.IOException;

public class S29PacketSoundEffect implements Packet {
    private int posZ;
    private float soundVolume;
    private int posX;
    private int posY = Integer.MAX_VALUE;
    private String soundName;
    private int soundPitch;
    private static String g;

    public double getX() {
        return this.posX / 8.0F;
    }

    public S29PacketSoundEffect() {}

    public double getY() {
        return this.posY / 8.0F;
    }

    public String getSoundName() {
        return this.soundName;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.soundName);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeFloat(this.soundVolume);
        packetBuffer.writeByte(this.soundPitch);
    }

    static {
        g = "name";
    }

    public float getPitch() {
        return this.soundPitch / 63.0F;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.soundName = packetBuffer.readStringFromBuffer(256);
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.soundVolume = packetBuffer.readFloat();
        this.soundPitch = packetBuffer.readUnsignedByte();
    }

    public double getZ() {
        return this.posZ / 8.0F;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSoundEffect(this);
    }

    public float getVolume() {
        return this.soundVolume;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S29PacketSoundEffect(
            String text, double d0, double d1, double d2, float soundVolumeValue, float f1) {
        Validate.notNull(text, g, new Object[0]);
        this.soundName = text;
        this.posX = (int) (d0 * 8.0);
        this.posY = (int) (d1 * 8.0);
        this.posZ = (int) (d2 * 8.0);
        this.soundVolume = soundVolumeValue;
        this.soundPitch = (int) (f1 * 63.0F);
        f1 = MathHelper.clamp(f1, 0.0F, 255.0F);
    }
}
