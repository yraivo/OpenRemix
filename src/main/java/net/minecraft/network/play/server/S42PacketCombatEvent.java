package net.minecraft.network.play.server;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;

import java.io.IOException;

public class S42PacketCombatEvent implements Packet {
    public int dD;
    public S42PacketCombatEventEvent eventType;
    public int cz;
    public String deathMessage;
    public int c;

    public S42PacketCombatEvent() {}

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void func_179771_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleCombatEvent(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.eventType =
                (S42PacketCombatEventEvent)
                        packetBuffer.readEnumValue(S42PacketCombatEventEvent.class);
        int[] aint = aint1;
        if (this.eventType == S42PacketCombatEventEvent.END_COMBAT) {
            this.dD = packetBuffer.readVarIntFromBuffer();
            this.c = packetBuffer.readInt();
            if (aint == null) {
                return;
            }
        }

        if (this.eventType == S42PacketCombatEventEvent.ENTITY_DIED) {
            this.cz = packetBuffer.readVarIntFromBuffer();
            this.c = packetBuffer.readInt();
            this.deathMessage = packetBuffer.readStringFromBuffer(32767);
        }
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeEnumValue(this.eventType);
        int[] aint = aint1;
        if (this.eventType == S42PacketCombatEventEvent.END_COMBAT) {
            packetBuffer.writeVarIntToBuffer(this.dD);
            packetBuffer.writeInt(this.c);
            if (aint == null) {
                return;
            }
        }

        if (this.eventType == S42PacketCombatEventEvent.ENTITY_DIED) {
            packetBuffer.writeVarIntToBuffer(this.cz);
            packetBuffer.writeInt(this.c);
            packetBuffer.writeString(this.deathMessage);
        }
    }

    public S42PacketCombatEvent(
            CombatTracker combatTracker, S42PacketCombatEventEvent s42PacketCombatEventEvent) {
        super();
        int[] initialFlowMarker = S14PacketEntity.getLegacyPacketFlowMarker();

        int[] aint1 = initialFlowMarker;

        this.eventType = s42PacketCombatEventEvent;
        LivingEntity livingEntity = combatTracker.TZ();
        int[] aint = aint1;
        switch (s42PacketCombatEventEvent) {
            case END_COMBAT:
                this.dD = combatTracker.cR();
                this.c = livingEntity == null ? -1 : livingEntity.getEntityId();
                if (aint == null) {
                    break;
                }
            case ENTITY_DIED:
                this.cz = combatTracker.getFighter().getEntityId();
                this.c = livingEntity == null ? -1 : livingEntity.getEntityId();
                this.deathMessage = combatTracker.getDeathMessage().getUnformattedText();
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179771_a((INetHandlerPlayClient) packetHandler);
    }
}
