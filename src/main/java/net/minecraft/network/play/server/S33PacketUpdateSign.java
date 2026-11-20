package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class S33PacketUpdateSign implements Packet {
    private BlockPos blockPos;
    private ChatComponent[] lines;
    private World world;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeBlockPos(this.blockPos);
        int[] aint = aint1;
        int i = 0;

        while (i < 4) {
            packetBuffer.writeChatComponent(this.lines[i]);
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleUpdateSign(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.lines = new ChatComponent[4];
        int i = 0;
        int[] aint = aint1;

        while (i < 4) {
            this.lines[i] = packetBuffer.readChatComponent();
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public BlockPos getPos() {
        return this.blockPos;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S33PacketUpdateSign() {}

    public S33PacketUpdateSign(World world, BlockPos blockPos, ChatComponent[] chatComponentArray) {
        this.world = world;
        this.blockPos = blockPos;
        this.lines =
                new net.minecraft.util.ChatComponent[] {
                    chatComponentArray[0],
                    chatComponentArray[1],
                    chatComponentArray[2],
                    chatComponentArray[3]
                };
    }

    public ChatComponent[] getLines() {
        return this.lines;
    }
}
