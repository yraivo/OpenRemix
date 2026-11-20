package net.minecraft.tileentity;

import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

class TileCommandBlockLogic extends CommandBlockLogic {
    TileEntityCommandBlock field_145767_a;

    public void func_145757_a(ByteBuf bytebuf) {
        bytebuf.writeInt(this.field_145767_a.pos.getX());
        bytebuf.writeInt(this.field_145767_a.pos.getY());
        bytebuf.writeInt(this.field_145767_a.pos.getZ());
    }

    public void updateCommand() {
        this.field_145767_a.getWorld().markBlockForUpdate(this.field_145767_a.pos);
    }

    public World getEntityWorld() {
        return this.field_145767_a.getWorld();
    }

    TileCommandBlockLogic(TileEntityCommandBlock aYCValue) {
        this.field_145767_a = aYCValue;
    }

    public Vec3 getPositionVector() {
        return new Vec3(
                this.field_145767_a.pos.getX() + 0.5,
                this.field_145767_a.pos.getY() + 0.5,
                this.field_145767_a.pos.getZ() + 0.5);
    }

    public int func_145751_f() {
        return 0;
    }

    public void setCommand(String text) {
        super.setCommand(text);
        this.field_145767_a.markDirty();
    }

    public Entity getCommandSenderEntity() {
        return null;
    }

    public BlockPos getPosition() {
        return this.field_145767_a.pos;
    }
}
