package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class S45PacketTitle implements Packet {
    private int fadeOutTime;
    private int displayTime;
    private ChatComponent message;
    private int fadeInTime;
    private S45PacketTitleType type;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.type);
        if (this.type == S45PacketTitleType.TITLE || this.type == S45PacketTitleType.SUBTITLE) {
            packetBuffer.writeChatComponent(this.message);
        }

        if (this.type == S45PacketTitleType.TIMES) {
            packetBuffer.writeInt(this.fadeInTime);
            packetBuffer.writeInt(this.displayTime);
            packetBuffer.writeInt(this.fadeOutTime);
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public int getFadeInTime() {
        return this.fadeInTime;
    }

    public S45PacketTitle(S45PacketTitleType s45PacketTitleType, ChatComponent chatComponent) {
        this(s45PacketTitleType, chatComponent, -1, -1, -1);
    }

    public int getFadeOutTime() {
        return this.fadeOutTime;
    }

    public ChatComponent getMessage() {
        return this.message;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179802_a((INetHandlerPlayClient) packetHandler);
    }

    public void func_179802_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleTitle(this);
    }

    public S45PacketTitle(
            S45PacketTitleType bxDValue,
            ChatComponent aqMValue,
            int fadeInTimeValue,
            int j,
            int k) {
        this.type = bxDValue;
        this.message = aqMValue;
        this.fadeInTime = fadeInTimeValue;
        this.displayTime = j;
        this.fadeOutTime = k;
    }

    public S45PacketTitle(int i, int j, int k) {
        this(S45PacketTitleType.TIMES, (ChatComponent) null, i, j, k);
    }

    public S45PacketTitleType getType() {
        return this.type;
    }

    public int getDisplayTime() {
        return this.displayTime;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.type = (S45PacketTitleType) packetBuffer.readEnumValue(S45PacketTitleType.class);
        if (this.type == S45PacketTitleType.TITLE || this.type == S45PacketTitleType.SUBTITLE) {
            this.message = packetBuffer.readChatComponent();
        }

        if (this.type == S45PacketTitleType.TIMES) {
            this.fadeInTime = packetBuffer.readInt();
            this.displayTime = packetBuffer.readInt();
            this.fadeOutTime = packetBuffer.readInt();
        }
    }

    public S45PacketTitle() {}
}
