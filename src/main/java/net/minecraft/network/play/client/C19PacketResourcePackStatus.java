package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C19PacketResourcePackStatus implements Packet {
    private String hash;
    private C19PacketResourcePackStatusAction status;

    public void func_179718_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.handleResourcePackStatus(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hash);
        packetBuffer.writeEnumValue(this.status);
    }

    public C19PacketResourcePackStatus() {}

    public C19PacketResourcePackStatus(String text, C19PacketResourcePackStatusAction bwHValue) {
        if (text.length() > 40) {
            text = text.substring(0, 40);
        }

        this.hash = text;
        this.status = bwHValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179718_a((ServerPlayPacketHandler) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.hash = packetBuffer.readStringFromBuffer(40);
        this.status =
                (C19PacketResourcePackStatusAction)
                        packetBuffer.readEnumValue(C19PacketResourcePackStatusAction.class);
    }
}
