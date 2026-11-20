package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class UseItemPacket implements Packet {
    private float facingZ;
    private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
    private float facingX;
    private float facingY;
    private ItemStack stack;
    private int placedBlockDirection;
    private BlockPos position;

    public float getPlacedBlockOffsetY() {
        return this.facingY;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readBlockPos();
        this.placedBlockDirection = packetBuffer.readUnsignedByte();
        this.stack = packetBuffer.readItemStackFromBuffer();
        this.facingX = packetBuffer.readUnsignedByte() / 16.0F;
        this.facingY = packetBuffer.readUnsignedByte() / 16.0F;
        this.facingZ = packetBuffer.readUnsignedByte() / 16.0F;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.placedBlockDirection);
        int j = PlayerMovementPacket.cM();
        packetBuffer.writeItemStackToBuffer(this.stack);
        packetBuffer.writeByte((int) (this.facingX * 16.0F));
        int i = j;

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeByte((int) (this.facingY * 16.0F));
            packetBuffer.writeByte((int) (this.facingZ * 16.0F));
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                PlayerMovementPacket.b(++i);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public UseItemPacket(ItemStack itemStack) {
        this(
                net.minecraft.network.play.client.UseItemPacket.field_179726_a,
                255,
                itemStack,
                0.0F,
                0.0F,
                0.0F);
    }

    public void func_180769_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processPlayerBlockPlacement(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180769_a((ServerPlayPacketHandler) packetHandler);
    }

    public UseItemPacket(
            BlockPos blockPos,
            int placedBlockDirectionValue,
            ItemStack itemStack,
            float facingXValue,
            float facingYValue,
            float facingZValue) {
        this.position = blockPos;
        this.placedBlockDirection = placedBlockDirectionValue;
        this.stack = itemStack != null ? itemStack.copy() : null;
        this.facingX = facingXValue;
        this.facingY = facingYValue;
        this.facingZ = facingZValue;
    }

    public int getPlacedBlockDirection() {
        return this.placedBlockDirection;
    }

    public float getPlacedBlockOffsetX() {
        return this.facingX;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public UseItemPacket() {}

    public float getPlacedBlockOffsetZ() {
        return this.facingZ;
    }
}
