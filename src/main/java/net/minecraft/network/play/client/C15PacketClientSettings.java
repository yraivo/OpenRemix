package net.minecraft.network.play.client;

import net.minecraft.entity.player.EntityPlayerEnumChatVisibility;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C15PacketClientSettings implements Packet {
    private int modelPartFlags;
    private int view;
    private EntityPlayerEnumChatVisibility chatVisibility;
    private String lang;
    private boolean enableColors;

    public EntityPlayerEnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }

    public int getView() {
        return this.modelPartFlags;
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processClientSettings(this);
    }

    public String getLang() {
        return this.lang;
    }

    public C15PacketClientSettings(
            String text,
            int viewValue,
            EntityPlayerEnumChatVisibility bwDValue,
            boolean enableColorsValue,
            int j) {
        this.lang = text;
        this.view = viewValue;
        this.chatVisibility = bwDValue;
        this.enableColors = enableColorsValue;
        this.modelPartFlags = j;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.lang);
        packetBuffer.writeByte(this.view);
        packetBuffer.writeByte(this.chatVisibility.getChatVisibility());
        packetBuffer.writeBoolean(this.enableColors);
        packetBuffer.writeByte(this.modelPartFlags);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.lang = packetBuffer.readStringFromBuffer(7);
        this.view = packetBuffer.readByte();
        this.chatVisibility =
                EntityPlayerEnumChatVisibility.getEnumChatVisibility(packetBuffer.readByte());
        this.enableColors = packetBuffer.readBoolean();
        this.modelPartFlags = packetBuffer.readUnsignedByte();
    }

    public boolean isColorsEnabled() {
        return this.enableColors;
    }

    public C15PacketClientSettings() {}

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }
}
