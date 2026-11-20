package net.minecraft.network.login.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.login.INetHandlerLoginServer;

import com.mojang.authlib.GameProfile;

import java.io.IOException;

public class C00PacketLoginStart implements Packet {
    private GameProfile profile;
    private static Entity[] b;

    public C00PacketLoginStart(GameProfile auCValue) {
        this.profile = auCValue;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.profile =
                new com.mojang.authlib.GameProfile(
                        (java.util.UUID) null, packetBuffer.readStringFromBuffer(16));
    }

    static {
        if (method_da2ac5c94686ee11e9e6() != null) {
            b(new Entity[3]);
        }
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.profile.getName());
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    public void func_180773_a(INetHandlerLoginServer iNetHandlerLoginServer) {
        iNetHandlerLoginServer.processLoginStart(this);
    }

    public C00PacketLoginStart() {}

    public void processPacket(PacketHandler packetHandler) {
        this.func_180773_a((INetHandlerLoginServer) packetHandler);
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }
}
