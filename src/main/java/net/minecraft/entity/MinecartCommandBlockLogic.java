package net.minecraft.entity;

import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

class MinecartCommandBlockLogic extends CommandBlockLogic {
    EntityMinecartCommandBlock field_145768_a;

    public void func_145757_a(ByteBuf bytebuf) {
        bytebuf.writeInt(this.field_145768_a.getEntityId());
    }

    public BlockPos getPosition() {
        return new BlockPos(
                this.field_145768_a.posX, this.field_145768_a.posY + 0.5, this.field_145768_a.posZ);
    }

    public void updateCommand() {
        this.field_145768_a.getDataWatcher().updateObject(23, this.getCustomName());
        this.field_145768_a
                .getDataWatcher()
                .updateObject(24, IChatComponentSerializer.componentToJson(this.getLastOutput()));
    }

    public World getEntityWorld() {
        return this.field_145768_a.world;
    }

    public int func_145751_f() {
        return 1;
    }

    public Vec3 getPositionVector() {
        return new Vec3(
                this.field_145768_a.posX, this.field_145768_a.posY, this.field_145768_a.posZ);
    }

    public Entity getCommandSenderEntity() {
        return this.field_145768_a;
    }

    MinecartCommandBlockLogic(EntityMinecartCommandBlock aYBValue) {
        this.field_145768_a = aYBValue;
    }
}
