package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class C14PacketTabComplete implements Packet {
    private BlockPos targetBlock;
    private String message;

    public void func_180756_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processTabComplete(this);
    }

    public C14PacketTabComplete(String text) {
        this(text, (net.minecraft.util.BlockPos) null);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public C14PacketTabComplete() {}

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(32767);
        boolean flag = packetBuffer.readBoolean();

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Checked exception carried by the original JVM handler.
            if (flag) {
                this.targetBlock = packetBuffer.readBlockPos();
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.network.play.client.C14PacketTabComplete.a(ioexception));
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180756_a((ServerPlayPacketHandler) packetHandler);
    }

    public String getMessage() {
        return this.message;
    }

    public BlockPos getTargetBlock() {
        return this.targetBlock;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        byte b0;
        label26:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                packetBuffer.writeString(
                        org.apache.commons.lang3.StringUtils.substring(this.message, 0, 32767));
                if (this.targetBlock != null) {
                    b0 = 1;
                    break label26;
                }
            } catch (java.io.IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.C14PacketTabComplete.a(ioexception1));
            }

            b0 = 0;
        }

        boolean flag = (b0 != 0);

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Checked exception carried by the original JVM handler.
            packetBuffer.writeBoolean(flag);
            if ((flag)) {
                packetBuffer.writeBlockPos(this.targetBlock);
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.network.play.client.C14PacketTabComplete.a(ioexception));
        }
    }

    public C14PacketTabComplete(String text, BlockPos blockPos) {
        this.message = text;
        this.targetBlock = blockPos;
    }
}
