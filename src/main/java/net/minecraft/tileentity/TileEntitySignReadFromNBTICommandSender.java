package net.minecraft.tileentity;

import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySignReadFromNBTICommandSender implements CommandSender {
    TileEntitySign bAY;
    private static String b;

    public Entity getCommandSenderEntity() {
        return null;
    }

    public Vec3 getPositionVector() {
        return new Vec3(
                this.bAY.pos.getX() + 0.5, this.bAY.pos.getY() + 0.5, this.bAY.pos.getZ() + 0.5);
    }

    public ChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }

    public String getName() {
        return b;
    }

    public TileEntitySignReadFromNBTICommandSender(TileEntitySign bAYValue) {
        this.bAY = bAYValue;
    }

    public World getEntityWorld() {
        return this.bAY.worldObj;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean sendCommandFeedback() {
        return false;
    }

    static {
        b = "Sign";
    }

    public void addChatMessage(ChatComponent chatComponent) {}

    public BlockPos getPosition() {
        return this.bAY.pos;
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        return true;
    }

    public void setCommandStat(CommandResultStatsType commandResultStatsType, int i) {}
}
