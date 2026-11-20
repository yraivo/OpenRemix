package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C0FPacketConfirmTransaction implements Packet {
    private short uid;
    private int id;
    private boolean accepted;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        net.minecraft.network.PacketBuffer packetBufferValue;
        byte b0;
        label16:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                packetBuffer.writeByte(this.id);
                packetBuffer.writeShort(this.uid);
                packetBufferValue = packetBuffer;
                if (this.accepted) {
                    b0 = 1;
                    break label16;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.C0FPacketConfirmTransaction.a(
                                ioexception));
            }

            b0 = 0;
        }

        packetBufferValue.writeByte(b0);
    }

    public int getId() {
        return this.id;
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processConfirmTransaction(this);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public short getUid() {
        return this.uid;
    }

    public C0FPacketConfirmTransaction() {}

    public C0FPacketConfirmTransaction(int p171_0, short bxcValue, boolean acceptedValue) {
        this.id = p171_0;
        this.uid = bxcValue;
        this.accepted = acceptedValue;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        net.minecraft.network.play.client.C0FPacketConfirmTransaction c0FPacketConfirmTransaction;
        byte b0;
        label16:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                this.id = packetBuffer.readByte();
                this.uid = packetBuffer.readShort();
                c0FPacketConfirmTransaction = this;
                if (packetBuffer.readByte() != 0) {
                    b0 = 1;
                    break label16;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.C0FPacketConfirmTransaction.a(
                                ioexception));
            }

            b0 = 0;
        }

        c0FPacketConfirmTransaction.accepted = (b0 != 0);
    }
}
