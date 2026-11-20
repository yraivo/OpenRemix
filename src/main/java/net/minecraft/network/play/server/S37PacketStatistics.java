package net.minecraft.network.play.server;

import com.google.common.collect.Maps;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class S37PacketStatistics implements Packet {
    private Map field_148976_a;

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S37PacketStatistics() {}

    public S37PacketStatistics(Map map) {
        this.field_148976_a = map;
    }

    public Map getStatistics() {
        return this.field_148976_a;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleStatistics(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeVarIntToBuffer(this.field_148976_a.size());
        Iterator iterator = this.field_148976_a.entrySet().iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            packetBuffer.writeString(((StatBase) entry.getKey()).statId);
            packetBuffer.writeVarIntToBuffer((Integer) entry.getValue());
            if (aint != null) {
                break;
            }
        }
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        int i = packetBuffer.readVarIntFromBuffer();
        int[] aint = aint1;
        this.field_148976_a = Maps.newHashMap();
        int j = 0;

        while (j < i) {
            StatBase oneShotStat =
                    StatList.getOneShotStat(packetBuffer.readStringFromBuffer(32767));
            int k = packetBuffer.readVarIntFromBuffer();

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (oneShotStat != null) {
                    this.field_148976_a.put(oneShotStat, k);
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
            }

            j++;
            if (aint != null) {
                break;
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
