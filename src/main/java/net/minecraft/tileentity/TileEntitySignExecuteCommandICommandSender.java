package net.minecraft.tileentity;

import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySignExecuteCommandICommandSender implements CommandSender {
    TileEntitySign bBa;
    PlayerEntity bBb;

    public void addChatMessage(ChatComponent chatComponent) {}

    public World getEntityWorld() {
        return this.bBb.getEntityWorld();
    }

    public String getName() {
        return this.bBb.getName();
    }

    public Entity getCommandSenderEntity() {
        return this.bBb;
    }

    public BlockPos getPosition() {
        return this.bBa.pos;
    }

    public ChatComponent getDisplayName() {
        return this.bBb.getDisplayName();
    }

    public void setCommandStat(CommandResultStatsType commandResultStatsType, int i) {
        TileEntitySign.access$000(this.bBa).applyResult(this, commandResultStatsType, i);
    }

    public boolean sendCommandFeedback() {
        return false;
    }

    public Vec3 getPositionVector() {
        return new Vec3(
                this.bBa.pos.getX() + 0.5, this.bBa.pos.getY() + 0.5, this.bBa.pos.getZ() + 0.5);
    }

    public TileEntitySignExecuteCommandICommandSender(TileEntitySign bBaValue, PlayerEntity bBbValue) {
        this.bBa = bBaValue;
        this.bBb = bBbValue;
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        return true;
    }
}
