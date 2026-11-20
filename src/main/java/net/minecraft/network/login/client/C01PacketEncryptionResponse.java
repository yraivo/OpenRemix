package net.minecraft.network.login.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class C01PacketEncryptionResponse implements Packet {
    private byte[] verifyTokenEncrypted;
    private byte[] secretKeyEncrypted;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.secretKeyEncrypted = packetBuffer.readByteArray();
        this.verifyTokenEncrypted = packetBuffer.readByteArray();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByteArray(this.secretKeyEncrypted);
        packetBuffer.writeByteArray(this.verifyTokenEncrypted);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerLoginServer) packetHandler);
    }

    public C01PacketEncryptionResponse(SecretKey secretkey, PublicKey publickey, byte[] abyte) {
        super();
        Entity[] initialFlowMarker = C00PacketLoginStart.method_da2ac5c94686ee11e9e6();

        Entity[] entityArray = initialFlowMarker;

        this.secretKeyEncrypted = new byte[0];
        this.verifyTokenEncrypted = new byte[0];
        Entity[] entityArrayValue = entityArray;
        this.secretKeyEncrypted = CryptManager.encryptData(publickey, secretkey.getEncoded());
        this.verifyTokenEncrypted = CryptManager.encryptData(publickey, abyte);
        if (entityArrayValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }

    public C01PacketEncryptionResponse() {
        super();
        C00PacketLoginStart.method_da2ac5c94686ee11e9e6();

        this.secretKeyEncrypted = new byte[0];
        this.verifyTokenEncrypted = new byte[0];
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            C00PacketLoginStart.b(new Entity[5]);
        }
    }

    public SecretKey getSecretKey(PrivateKey privatekey) {
        return CryptManager.decryptSharedKey(privatekey, this.secretKeyEncrypted);
    }

    public byte[] getVerifyToken(PrivateKey privatekey) {
        return privatekey == null
                ? this.verifyTokenEncrypted
                : CryptManager.decryptData(privatekey, this.verifyTokenEncrypted);
    }

    public void processPacket(INetHandlerLoginServer iNetHandlerLoginServer) {
        iNetHandlerLoginServer.processEncryptionResponse(this);
    }
}
