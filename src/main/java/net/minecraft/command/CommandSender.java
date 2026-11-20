package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface CommandSender {
    String getName();

    ChatComponent getDisplayName();

    void setCommandStat(CommandResultStatsType commandResultStatsType, int i);

    boolean canCommandSenderUseCommand(int i, String text);

    boolean sendCommandFeedback();

    World getEntityWorld();

    Vec3 getPositionVector();

    void addChatMessage(ChatComponent chatComponent);

    Entity getCommandSenderEntity();

    BlockPos getPosition();
}
