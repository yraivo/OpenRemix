package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

import java.io.IOException;
import java.util.Collection;

public class S34PacketMaps implements Packet {
    private byte[] mapDataBytes;
    private int mapMaxY;
    private int mapMinY;
    private int mapMaxX;
    private int mapMinX;
    private Vec4b[] mapVisiblePlayersVec4b;

    private byte mapScale;
    private int mapId;
    private static String te;

    public void func_180741_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleMaps(this);
    }

    // region String decoding support

    // endregion

    public void processPacket(PacketHandler packetHandler) {
        this.func_180741_a((INetHandlerPlayClient) packetHandler);
    }

    public S34PacketMaps() {}

    // region String decoding support

    // endregion

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.mapId = packetBuffer.readVarIntFromBuffer();
        this.mapScale = packetBuffer.readByte();
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.mapVisiblePlayersVec4b = new Vec4b[packetBuffer.readVarIntFromBuffer()];
        int i = 0;
        int[] aint = aint1;

        while (i < this.mapVisiblePlayersVec4b.length) {
            short short1 = packetBuffer.readByte();
            this.mapVisiblePlayersVec4b[i] =
                    new Vec4b(
                            (byte) (short1 >> 4 & 15),
                            packetBuffer.readByte(),
                            packetBuffer.readByte(),
                            (byte) (short1 & 15));
            i++;
            if (aint != null) {
                break;
            }
        }

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.mapMaxX = packetBuffer.readUnsignedByte();
            if (this.mapMaxX > 0) {
                this.mapMaxY = packetBuffer.readUnsignedByte();
                this.mapMinX = packetBuffer.readUnsignedByte();
                this.mapMinY = packetBuffer.readUnsignedByte();
                this.mapDataBytes = packetBuffer.readByteArray();
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void setMapdataTo(MapData mapData) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        mapData.scale = this.mapScale;
        mapData.playersVisibleOnMap.clear();
        int[] aint = aint1;
        int i = 0;

        while (i < this.mapVisiblePlayersVec4b.length) {
            Vec4b vec4b = this.mapVisiblePlayersVec4b[i];
            mapData.playersVisibleOnMap.put(te + i, vec4b);
            i++;
            if (aint != null) {
                break;
            }
        }

        i = 0;

        while (i < this.mapMaxX) {
            int j = 0;

            while (true) {
                if (j < this.mapMaxY) {
                    mapData.colors[this.mapMinX + i + (this.mapMinY + j) * 128] =
                            this.mapDataBytes[i + j * this.mapMaxX];
                    j++;
                    if (aint == null) {
                        continue;
                    }
                }

                i++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    public S34PacketMaps(
            int mapIdValue,
            byte b0,
            Collection collection,
            byte[] abyte,
            int j,
            int k,
            int l,
            int i1) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.mapId = mapIdValue;
        this.mapScale = b0;
        this.mapVisiblePlayersVec4b =
                ((net.minecraft.util.Vec4b[]) collection.toArray(new Vec4b[collection.size()]));
        this.mapMinX = j;
        this.mapMinY = k;
        this.mapMaxX = l;
        this.mapMaxY = i1;
        this.mapDataBytes = new byte[l * i1];
        int[] aint = aint1;
        int j1 = 0;

        while (j1 < l) {
            int k1 = 0;

            while (true) {
                if (k1 < i1) {
                    this.mapDataBytes[j1 + k1 * l] = abyte[j + j1 + (k + k1) * 128];
                    k1++;
                    if (aint == null) {
                        continue;
                    }
                }

                j1++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    static {
        te = "icon-";
    }

    public int getMapId() {
        return this.mapId;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeVarIntToBuffer(this.mapId);
        packetBuffer.writeByte(this.mapScale);
        packetBuffer.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length);
        Vec4b[] bxpValue = this.mapVisiblePlayersVec4b;
        int length = bxpValue.length;
        int[] aint = aint1;
        int j = 0;

        while (j < length) {
            Vec4b vec4b = bxpValue[j];
            packetBuffer.writeByte((vec4b.func_176110_a() & 15) << 4 | vec4b.func_176111_d() & 15);
            packetBuffer.writeByte(vec4b.func_176112_b());
            packetBuffer.writeByte(vec4b.func_176113_c());
            j++;
            if (aint != null) {
                break;
            }
        }

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeByte(this.mapMaxX);
            if (this.mapMaxX > 0) {
                packetBuffer.writeByte(this.mapMaxY);
                packetBuffer.writeByte(this.mapMinX);
                packetBuffer.writeByte(this.mapMinY);
                packetBuffer.writeByteArray(this.mapDataBytes);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }
}
