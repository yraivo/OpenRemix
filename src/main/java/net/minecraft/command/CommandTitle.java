package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitleType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponentSerializer;

import com.google.gson.JsonParseException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CommandTitle extends CommandBase {
    private static Logger LOGGER;

    private static String[] packedTextFragments;

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.title.usage";
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandName() {
        return "title";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : (astring.length == 2
                        ? getListOfStringsMatchingLastWord(astring, S45PacketTitleType.getNames())
                        : null);
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length < 2) {
            throw new WrongUsageException("commands.title.usage", new Object[0]);
        }

        if (astring.length < 3) {
            if ("title".equals(astring[1]) || "subtitle".equals(astring[1])) {
                throw new WrongUsageException("commands.title.usage.title", new Object[0]);
            }

            if ("times".equals(astring[1])) {
                throw new WrongUsageException("commands.title.usage.times", new Object[0]);
            }
        }

        EntityPlayerMP entityPlayerMP = getPlayer(commandSender, astring[0]);
        S45PacketTitleType s45PacketTitleType = S45PacketTitleType.R(astring[1]);
        if (s45PacketTitleType != S45PacketTitleType.CLEAR
                && s45PacketTitleType != S45PacketTitleType.RESET) {
            if (s45PacketTitleType == S45PacketTitleType.TIMES) {
                if (astring.length != 5) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }

                int i = parseInt(astring[2]);
                int j = parseInt(astring[3]);
                int k = parseInt(astring[4]);
                S45PacketTitle s45PacketTitle = new S45PacketTitle(i, j, k);
                entityPlayerMP.playerNetServerHandler.sendPacket(s45PacketTitle);
                notifyOperators(
                        commandSender, this, "commands.title.success", new Object[0]);
                if (!flag) {
                    return;
                }
            }

            if (astring.length < 3) {
                throw new WrongUsageException("commands.title.usage", new Object[0]);
            }

            String text = buildString(astring, 2);

            ChatComponent chatComponent;
            try {
                chatComponent = IChatComponentSerializer.jsonToComponent(text);
            } catch (JsonParseException jsonparseexception) {
                Throwable throwable = ExceptionUtils.getRootCause(jsonparseexception);
                throw new SyntaxErrorException(
                        "commands.tellraw.jsonException",
                        new Object[] {
                            throwable == null ? packedTextFragments[0] : throwable.getMessage()
                        });
            }

            S45PacketTitle s45PacketTitleValue =
                    new S45PacketTitle(
                            s45PacketTitleType,
                            ChatComponentProcessor.processComponent(
                                    commandSender, chatComponent, entityPlayerMP));
            entityPlayerMP.playerNetServerHandler.sendPacket(s45PacketTitleValue);
            notifyOperators(
                    commandSender, this, "commands.title.success", new Object[0]);
            if (!flag) {
                return;
            }
        }

        if (astring.length != 2) {
            throw new WrongUsageException("commands.title.usage", new Object[0]);
        }

        S45PacketTitle s45PacketTitleValue2 =
                new S45PacketTitle(s45PacketTitleType, (ChatComponent) null);
        entityPlayerMP.playerNetServerHandler.sendPacket(s45PacketTitleValue2);
        notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
    }

    static {
        net.minecraft.command.CommandTitle.packedTextFragments = new String[3];
        net.minecraft.command.CommandTitle.packedTextFragments[0] = new String("");
        net.minecraft.command.CommandTitle.LOGGER = LogManager.getLogger();
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }
}
