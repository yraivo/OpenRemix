package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponentSerializer;

import com.google.gson.JsonParseException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;

public class CommandMessageRaw extends CommandBase {

    private static String[] packedTextFragments;

    public String getCommandName() {
        return "tellraw";
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.tellraw.usage";
    }

    static {
        net.minecraft.command.server.CommandMessageRaw.packedTextFragments = new String[3];
        net.minecraft.command.server.CommandMessageRaw.packedTextFragments[0] = new String("");
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        if (astring.length < 2) {
            throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
        }

        EntityPlayerMP entityPlayerMP = getPlayer(commandSender, astring[0]);
        String text = buildString(astring, 1);

        try {
            ChatComponent chatComponent = IChatComponentSerializer.jsonToComponent(text);
            entityPlayerMP.addChatMessage(
                    ChatComponentProcessor.processComponent(
                            commandSender, chatComponent, entityPlayerMP));
        } catch (JsonParseException jsonparseexception) {
            Throwable throwable = ExceptionUtils.getRootCause(jsonparseexception);
            throw new SyntaxErrorException(
                    "commands.tellraw.jsonException",
                    new Object[] {
                        throwable == null ? packedTextFragments[0] : throwable.getMessage()
                    });
        }
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }
}
