package net.minecraft.network.login.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

import java.io.IOException;
import java.security.PublicKey;

public class S01PacketEncryptionRequest implements Packet {
    private PublicKey publicKey;
    private byte[] verifyToken;
    private String hashedServerId;

    public String getServerId() {
        return this.hashedServerId;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void processPacket(INetHandlerLoginClient iNetHandlerLoginClient) {
        iNetHandlerLoginClient.handleEncryptionRequest(this);
    }

    public S01PacketEncryptionRequest(String text, PublicKey publickey, byte[] abyte) {
        this.hashedServerId = text;
        this.publicKey = publickey;
        this.verifyToken = abyte;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.hashedServerId = packetBuffer.readStringFromBuffer(20);
        this.publicKey = CryptManager.decodePublicKey(packetBuffer.readByteArray());
        this.verifyToken = packetBuffer.readByteArray();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public S01PacketEncryptionRequest() {}

    public byte[] getVerifyToken() {
        return this.verifyToken;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerLoginClient) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        S03PacketEnableCompression.method_da2ac5c94686ee11e9e6();
        packetBuffer.writeString(this.hashedServerId);

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeByteArray(this.publicKey.getEncoded());
            packetBuffer.writeByteArray(this.verifyToken);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                S03PacketEnableCompression.b(new Entity[3]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }
}
