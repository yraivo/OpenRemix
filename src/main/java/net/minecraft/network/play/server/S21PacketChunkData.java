package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;

public class S21PacketChunkData implements Packet {
    private boolean field_149279_g;
    private int chunkZ;
    private int chunkX;
    private S21PacketChunkDataExtracted extractedData;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.chunkX);
        packetBuffer.writeInt(this.chunkZ);
        packetBuffer.writeBoolean(this.field_149279_g);
        packetBuffer.writeShort((short) (this.extractedData.dataSize & 65535));
        packetBuffer.writeByteArray(this.extractedData.data);
    }

    protected static int method_6a78637df63d865ec53c(int p171_0, boolean p171_1, boolean p171_2) {
        int j = p171_0 * 2 * 16 * 16 * 16;
        int k = p171_0 * 16 * 16 * 16 / 2;
        int l = p171_1 ? p171_0 * 16 * 16 * 16 / 2 : 0;
        int i1 = p171_2 ? 256 : 0;
        return j + k + l + i1;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleChunkData(this);
    }

    public static S21PacketChunkDataExtracted func_179756_a(
            Chunk chunk, boolean flag, boolean flag1, int i) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        int[] aint = aint1;
        S21PacketChunkDataExtracted s21PacketChunkDataExtracted = new S21PacketChunkDataExtracted();
        ArrayList arrayList = Lists.newArrayList();
        int j = 0;

        while (j < blockStorageArray.length) {
            ExtendedBlockStorage extendedBlockStorage = blockStorageArray[j];
            if (extendedBlockStorage != null
                    && (!flag || !extendedBlockStorage.isEmpty())
                    && (i & 1 << j) != 0) {
                s21PacketChunkDataExtracted.dataSize |= 1 << j;
                arrayList.add(extendedBlockStorage);
            }

            j++;
            if (aint != null) {
                break;
            }
        }

        s21PacketChunkDataExtracted.data =
                new byte
                        [method_6a78637df63d865ec53c(
                                Integer.bitCount(s21PacketChunkDataExtracted.dataSize),
                                flag1,
                                flag)];
        j = 0;

        label56:
        for (java.util.Iterator<?> __recoveryIterator171_2545 = (arrayList).iterator();
                (__recoveryIterator171_2545).hasNext(); ) {
            ExtendedBlockStorage extendedBlockStorageValue =
                    (ExtendedBlockStorage) (__recoveryIterator171_2545).next();
            char[] achar = extendedBlockStorageValue.getData();
            char[] achar1 = achar;
            int k = achar.length;
            int l = 0;

            while (true) {
                if (l < k) {
                    char c0 = achar1[l];
                    s21PacketChunkDataExtracted.data[j++] = (byte) (c0 & 0xFF);
                    s21PacketChunkDataExtracted.data[j++] = (byte) (c0 >> '\b' & 0xFF);
                    l++;
                    if (aint == null) {
                        continue;
                    }
                }

                if (aint != null) {
                    break label56;
                }
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_3235 = (arrayList).iterator();
                (__recoveryIterator171_3235).hasNext(); ) {
            ExtendedBlockStorage extendedBlockStorageValue2 =
                    (ExtendedBlockStorage) (__recoveryIterator171_3235).next();
            j =
                    func_179757_a(
                            extendedBlockStorageValue2.getBlocklightArray().getData(),
                            s21PacketChunkDataExtracted.data,
                            j);
            if (aint != null) {
                break;
            }
        }

        if (flag1) {
            for (java.util.Iterator<?> __recoveryIterator171_3522 = (arrayList).iterator();
                    (__recoveryIterator171_3522).hasNext(); ) {
                ExtendedBlockStorage extendedBlockStorageValue3 =
                        (ExtendedBlockStorage) (__recoveryIterator171_3522).next();
                j =
                        func_179757_a(
                                extendedBlockStorageValue3.getSkylightArray().getData(),
                                s21PacketChunkDataExtracted.data,
                                j);
                if (aint != null) {
                    break;
                }
            }
        }

        if (flag) {
            func_179757_a(chunk.getBiomeArray(), s21PacketChunkDataExtracted.data, j);
        }

        return s21PacketChunkDataExtracted;
    }

    public S21PacketChunkData(Chunk chunk, boolean p171_1, int p171_2) {
        this.chunkX = chunk.xPosition;
        this.chunkZ = chunk.zPosition;
        this.field_149279_g = p171_1;
        this.extractedData =
                net.minecraft.network.play.server.S21PacketChunkData.func_179756_a(
                        chunk, p171_1, !chunk.getWorld().provider.getHasNoSky(), p171_2);
    }

    private static int func_179757_a(byte[] p171_0, byte[] p171_1, int p171_2) {
        java.lang.System.arraycopy(p171_0, 0, p171_1, p171_2, p171_0.length);
        return p171_2 + p171_0.length;
    }

    public S21PacketChunkData() {}

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public byte[] getChunkBytes() {
        return this.extractedData.data;
    }

    public boolean isFullChunk() {
        return this.field_149279_g;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.chunkX = packetBuffer.readInt();
        this.chunkZ = packetBuffer.readInt();
        this.field_149279_g = packetBuffer.readBoolean();
        this.extractedData = new net.minecraft.network.play.server.S21PacketChunkDataExtracted();
        this.extractedData.dataSize = packetBuffer.readShort();
        this.extractedData.data = packetBuffer.readByteArray();
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getSectionMask() {
        return this.extractedData.dataSize;
    }
}
