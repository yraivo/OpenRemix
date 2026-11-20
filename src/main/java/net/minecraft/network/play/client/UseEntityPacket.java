package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.io.IOException;

public class UseEntityPacket implements Packet {
    private int entityId;
    private UseEntityAction action;
    private Vec3 hitVec;

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public Entity getEntityFromWorld(World world) {
        return world.getEntityByID(this.entityId);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.entityId = packetBuffer.readVarIntFromBuffer();
            this.action = (UseEntityAction) packetBuffer.readEnumValue(UseEntityAction.class);
            if (this.action == UseEntityAction.INTERACT_AT) {
                this.hitVec =
                        new Vec3(
                                packetBuffer.readFloat(),
                                packetBuffer.readFloat(),
                                packetBuffer.readFloat());
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processUseEntity(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeVarIntToBuffer(this.entityId);
            packetBuffer.writeEnumValue(this.action);
            if (this.action == UseEntityAction.INTERACT_AT) {
                packetBuffer.writeFloat((float) this.hitVec.x);
                packetBuffer.writeFloat((float) this.hitVec.y);
                packetBuffer.writeFloat((float) this.hitVec.z);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public UseEntityPacket(Entity entity, UseEntityAction bwZValue) {
        this.entityId = entity.getEntityId();
        this.action = bwZValue;
    }

    public UseEntityAction getAction() {
        return this.action;
    }

    public Vec3 getHitVec() {
        return this.hitVec;
    }

    public UseEntityPacket(Entity entity, Vec3 aAfValue) {
        this(entity, UseEntityAction.INTERACT_AT);
        this.hitVec = aAfValue;
    }

    public UseEntityPacket() {}
}
