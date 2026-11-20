package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C0BPacketEntityAction implements Packet {
    private int entityID;
    private int auxData;
    private C0BPacketEntityActionAction action;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.action =
                (C0BPacketEntityActionAction)
                        packetBuffer.readEnumValue(C0BPacketEntityActionAction.class);
        this.auxData = packetBuffer.readVarIntFromBuffer();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180765_a((ServerPlayPacketHandler) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarIntToBuffer(this.auxData);
    }

    public C0BPacketEntityAction() {}

    public C0BPacketEntityAction(
            Entity entity, C0BPacketEntityActionAction c0BPacketEntityActionAction) {
        this(entity, c0BPacketEntityActionAction, 0);
    }

    public int getAuxData() {
        return this.auxData;
    }

    public C0BPacketEntityActionAction getAction() {
        return this.action;
    }

    public C0BPacketEntityAction(
            Entity entity, C0BPacketEntityActionAction bxbValue, int auxDataValue) {
        this.entityID = entity.getEntityId();
        this.action = bxbValue;
        this.auxData = auxDataValue;
    }

    public void func_180765_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processEntityAction(this);
    }
}
