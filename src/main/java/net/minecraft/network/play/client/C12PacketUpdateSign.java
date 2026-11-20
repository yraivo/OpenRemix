package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class C12PacketUpdateSign implements Packet {
    private ChatComponent[] lines;
    private BlockPos pos;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int k = PlayerMovementPacket.cM();
        packetBuffer.writeBlockPos(this.pos);
        int j = 0;
        int i = k;

        while (j < 4) {
            packetBuffer.writeChatComponent(this.lines[j]);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public BlockPos getPosition() {
        return this.pos;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int k = PlayerMovementPacket.cK();
        this.pos = packetBuffer.readBlockPos();
        int i = k;
        this.lines = new ChatComponent[4];
        int j = 0;

        while (j < 4) {
            this.lines[j] = packetBuffer.readChatComponent();
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processUpdateSign(this);
    }

    public ChatComponent[] getLines() {
        return this.lines;
    }

    public C12PacketUpdateSign(BlockPos blockPos, ChatComponent[] chatComponentArray) {
        this.pos = blockPos;
        this.lines =
                new net.minecraft.util.ChatComponent[] {
                    chatComponentArray[0],
                    chatComponentArray[1],
                    chatComponentArray[2],
                    chatComponentArray[3]
                };
    }

    public C12PacketUpdateSign() {}
}
