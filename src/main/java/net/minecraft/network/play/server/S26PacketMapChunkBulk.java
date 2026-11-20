package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;

import java.io.IOException;
import java.util.List;

public class S26PacketMapChunkBulk implements Packet {
    private int[] zPositions;
    private boolean isOverworld;
    private int[] xPositions;
    private S21PacketChunkDataExtracted[] chunksData;

    public int getChunkSize(int p171_0) {
        return this.chunksData[p171_0].dataSize;
    }

    public int getChunkX(int i) {
        return this.xPositions[i];
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180738_a((INetHandlerPlayClient) packetHandler);
    }

    public S26PacketMapChunkBulk() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeBoolean(this.isOverworld);
        packetBuffer.writeVarIntToBuffer(this.chunksData.length);
        int[] aint = aint1;
        int i = 0;

        while (i < this.xPositions.length) {
            packetBuffer.writeInt(this.xPositions[i]);
            packetBuffer.writeInt(this.zPositions[i]);
            packetBuffer.writeShort((short) (this.chunksData[i].dataSize & 65535));
            i++;
            if (aint != null) {
                break;
            }
        }

        i = 0;

        while (i < this.xPositions.length) {
            packetBuffer.writeBytes(this.chunksData[i].data);
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void func_180738_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleMapChunkBulk(this);
    }

    public S26PacketMapChunkBulk(List list) {
        int i = list.size();
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.xPositions = new int[i];
        int[] aint = aint1;
        this.zPositions = new int[i];
        this.chunksData = new S21PacketChunkDataExtracted[i];
        this.isOverworld =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!((Chunk) list.get(0)).getWorld().provider.getHasNoSky() ? 1 : 0));
        int j = 0;

        while (j < i) {
            Chunk chunk = (Chunk) list.get(j);
            S21PacketChunkDataExtracted s21PacketChunkDataExtracted =
                    S21PacketChunkData.func_179756_a(chunk, true, this.isOverworld, 65535);
            this.xPositions[j] = chunk.xPosition;
            this.zPositions[j] = chunk.zPosition;
            this.chunksData[j] = s21PacketChunkDataExtracted;
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public int getChunkCount() {
        return this.xPositions.length;
    }

    public int getChunkZ(int i) {
        return this.zPositions[i];
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.isOverworld = packetBuffer.readBoolean();
        int i = packetBuffer.readVarIntFromBuffer();
        this.xPositions = new int[i];
        this.zPositions = new int[i];
        this.chunksData = new S21PacketChunkDataExtracted[i];
        int[] aint = aint1;
        int j = 0;

        while (j < i) {
            this.xPositions[j] = packetBuffer.readInt();
            this.zPositions[j] = packetBuffer.readInt();
            this.chunksData[j] = new S21PacketChunkDataExtracted();
            this.chunksData[j].dataSize = packetBuffer.readShort() & '\uffff';
            this.chunksData[j].data =
                    new byte
                            [S21PacketChunkData.method_6a78637df63d865ec53c(
                                    Integer.bitCount(this.chunksData[j].dataSize),
                                    this.isOverworld,
                                    true)];
            j++;
            if (aint != null) {
                break;
            }
        }

        j = 0;

        while (j < i) {
            packetBuffer.readBytes(this.chunksData[j].data);
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public byte[] getChunkBytes(int p171_0) {
        return this.chunksData[p171_0].data;
    }
}
