package net.minecraft.network.handshake.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

import java.io.IOException;

public class C00Handshake implements Packet {
    private ConnectionState requestedState;
    private int protocolVersion;
    private String ip;
    private static int[] aAz;
    private int port;

    public ConnectionState getRequestedState() {
        return this.requestedState;
    }

    public C00Handshake() {}

    static {
        if (fM() != null) {
            b(new int[4]);
        }
    }

    public String getIp() {
        return this.ip;
    }

    public static void b(int[] aint) {
        aAz = aint;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = fM();
        packetBuffer.writeVarIntToBuffer(this.protocolVersion);
        int[] aint = aint1;

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeString(this.ip);
            packetBuffer.writeShort(this.port);
            packetBuffer.writeVarIntToBuffer(this.requestedState.getId());
            if (aint != null) {
                Entity.setLegacyGlobalFlowMarker(new String[2]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public static int[] fM() {
        return aAz;
    }

    public void setIp(String text) {
        this.ip = text;
    }

    public void func_180770_a(INetHandlerHandshakeServer iNetHandlerHandshakeServer) {
        iNetHandlerHandshakeServer.processHandshake(this);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public C00Handshake(int protocolVersionValue, String text, int j, ConnectionState bvrValue) {
        this.protocolVersion = protocolVersionValue;
        this.ip = text;
        this.port = j;
        this.requestedState = bvrValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180770_a((INetHandlerHandshakeServer) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.protocolVersion = packetBuffer.readVarIntFromBuffer();
        fM();
        this.ip = packetBuffer.readStringFromBuffer(255);
        this.port = packetBuffer.readUnsignedShort();

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.requestedState = ConnectionState.getById(packetBuffer.readVarIntFromBuffer());
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(new int[2]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }
}
