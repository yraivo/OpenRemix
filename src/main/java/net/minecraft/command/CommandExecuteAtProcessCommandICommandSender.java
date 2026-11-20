package net.minecraft.command;

import net.minecraft.command.CommandExecuteAt;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandExecuteAtProcessCommandICommandSender implements CommandSender {
    double rd;
    BlockPos ii;
    CommandSender aYX;
    double ul;
    Entity AO;

    private static String h;
    double ix;
    CommandExecuteAt aYZ;

    public Vec3 getPositionVector() {
        return new Vec3(this.ul, this.rd, this.ix);
    }

    static {
        h = "commandBlockOutput";
    }

    public BlockPos getPosition() {
        return this.ii;
    }

    public World getEntityWorld() {
        return this.AO.world;
    }

    public void setCommandStat(CommandResultStatsType commandResultStatsType, int i) {
        this.AO.setCommandStat(commandResultStatsType, i);
    }

    // region String decoding support

    // endregion

    public ChatComponent getDisplayName() {
        return this.AO.getDisplayName();
    }

    public Entity getCommandSenderEntity() {
        return this.AO;
    }

    public boolean sendCommandFeedback() {
        MinecraftServer server = MinecraftServer.getServer();
        return server == null || server.worldServers[0].getGameRules().getGameRuleBooleanValue(h);
    }

    // region String decoding support

    // endregion

    public boolean canCommandSenderUseCommand(int i, String text) {
        return this.aYX.canCommandSenderUseCommand(i, text);
    }

    public void addChatMessage(ChatComponent chatComponent) {
        this.aYX.addChatMessage(chatComponent);
    }

    public String getName() {
        return this.AO.getName();
    }

    public CommandExecuteAtProcessCommandICommandSender(
            CommandExecuteAt aYZValue,
            Entity entity,
            CommandSender aYXValue,
            BlockPos blockPos,
            double d0,
            double d1,
            double d2) {
        this.aYZ = aYZValue;
        this.AO = entity;
        this.aYX = aYXValue;
        this.ii = blockPos;
        this.ul = d0;
        this.rd = d1;
        this.ix = d2;
    }
}
