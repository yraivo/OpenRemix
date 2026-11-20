package net.minecraft.network.status.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.status.INetHandlerStatusServer;

import java.io.IOException;

public class C01PacketPing implements Packet {
    private static String[] b;
    private long clientTime;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180774_a((INetHandlerStatusServer) packetHandler);
    }

    public static String[] b() {
        return b;
    }

    public C01PacketPing() {}

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }

    public static void b(String[] astring) {
        b = astring;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }

    public long getClientTime() {
        return this.clientTime;
    }

    public void func_180774_a(INetHandlerStatusServer iNetHandlerStatusServer) {
        iNetHandlerStatusServer.processPing(this);
    }

    public C01PacketPing(long clientTimeValue) {
        b();

        this.clientTime = clientTimeValue;
    }

    static {
        if (b() == null) {
            b(new String[1]);
        }
    }
}
