package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.login.INetHandlerLoginClient;

import remix.compat.legacy.security.SecurityManagerRegistry;

import java.io.IOException;
import java.util.UUID;

public class S02PacketLoginSuccess implements Packet {
    private static String b;
    private static String[] packedTextFragments;
    private GameProfile profile;

    // region String decoding support

    // endregion

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        Entity[] entityArray = S03PacketEnableCompression.method_da2ac5c94686ee11e9e6();
        String text = packetBuffer.readStringFromBuffer(36);
        Entity[] entityArrayValue = entityArray;
        String textValue = packetBuffer.readStringFromBuffer(16);
        UUID uuid = UUID.fromString(text);

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.profile = new GameProfile(uuid, textValue);
            if (entityArrayValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    // region String decoding support

    // endregion

    public GameProfile getProfile() {
        return this.profile;
    }

    static {
        net.minecraft.network.login.server.S02PacketLoginSuccess.packedTextFragments =
                new String[2];
        net.minecraft.network.login.server.S02PacketLoginSuccess.packedTextFragments[0] =
                new String("");
        net.minecraft.network.login.server.S02PacketLoginSuccess.packedTextFragments[1] =
                new String("\u001f\t\\\rnl\u0005\u0016\ne\u0017\u00108ih\fD0\u001f4");
        b = "AFUJLeCHElP21/6CMw==";
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        if (SecurityManagerRegistry.INSTANCE
                .getPrimaryCheckRunner()
                .q()
                .equalsIgnoreCase(new String("SSSSDSSSDDDSD"))) {
            UUID uuid = this.profile.getId();

            PacketBuffer packetBufferValue;
            String text;
            label19:
            {
                try {
                    if (false)
                        throw (java.io.IOException)
                                null; // Original JVM checked handler; no emitted instructions.
                    packetBufferValue = packetBuffer;
                    if (uuid == null) {
                        text = packedTextFragments[0];
                        break label19;
                    }
                } catch (IOException ioexception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
                }

                text = uuid.toString();
            }

            packetBufferValue.writeString(text);
            packetBuffer.writeString(this.profile.getName());
        }
    }

    public S02PacketLoginSuccess() {}

    public void processPacket(PacketHandler packetHandler) {
        this.func_180771_a((INetHandlerLoginClient) packetHandler);
    }

    // region String decoding support

    // endregion

    public void func_180771_a(INetHandlerLoginClient iNetHandlerLoginClient) {
        iNetHandlerLoginClient.handleLoginSuccess(this);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public S02PacketLoginSuccess(GameProfile gameprofile) {
        this.profile = gameprofile;
    }
}
