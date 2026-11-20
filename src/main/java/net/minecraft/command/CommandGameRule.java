package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRulesValueType;

import java.util.List;

public class CommandGameRule extends CommandBase {

    private static String[] packedTextFragments;

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        GameRules gameRules = this.getGameRules();
        boolean flag = flag1;
        String text = astring.length > 0 ? astring[0] : packedTextFragments[0];
        String textValue =
                astring.length > 1
                        ? buildString(astring, 1)
                        : packedTextFragments[1];
        switch (astring.length) {
            case 0:
                commandSender.addChatMessage(
                        new ChatComponentText(joinNiceString(gameRules.getRules())));
                if (flag) {
                    break;
                }
            case 1:
                if (!gameRules.hasRule(text)) {
                    throw new CommandException(
                            "commands.gamerule.norule", new Object[] {text});
                }

                String textValue2 = gameRules.getString(text);
                commandSender.addChatMessage(
                        new ChatComponentText(text).appendText(" = ").appendText(textValue2));
                commandSender.setCommandStat(
                        CommandResultStatsType.QUERY_RESULT,
                        gameRules.getInt(text));
                if (flag) {
                    break;
                }
            default:
                if (gameRules.areSameType(text, GameRulesValueType.BOOLEAN_VALUE)
                        && !"true".equals(textValue)
                        && !"false".equals(textValue)) {
                    throw new CommandException(
                            "commands.generic.boolean.invalid", new Object[] {textValue});
                }

                gameRules.setOrCreateGameRule(text, textValue);
                method_495a9630cc9826b65f4b(gameRules, text);
                notifyOperators(
                        commandSender, this, "commands.gamerule.success", new Object[0]);
        }
    }

    static {
        net.minecraft.command.CommandGameRule.packedTextFragments = new String[4];
        net.minecraft.command.CommandGameRule.packedTextFragments[0] = new String("");
        net.minecraft.command.CommandGameRule.packedTextFragments[1] = new String("");
    }

    public String getCommandName() {
        return "gamerule";
    }

    public static void method_495a9630cc9826b65f4b(GameRules gameRules, String text) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if ("reducedDebugInfo".equals(text)) {
            int i = gameRules.getGameRuleBooleanValue(text) ? 22 : 23;

            for (java.util.Iterator<?> __recoveryIterator171_28730 =
                            (MinecraftServer.getServer().getConfigurationManager().playerEntityList)
                                    .iterator();
                    (__recoveryIterator171_28730).hasNext(); ) {
                EntityPlayerMP entityPlayerMP =
                        (EntityPlayerMP) (__recoveryIterator171_28730).next();
                entityPlayerMP.playerNetServerHandler.sendPacket(
                        new S19PacketEntityStatus(entityPlayerMP, (byte) i));
                if (flag) {
                    break;
                }
            }
        }
    }

    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        if (astring.length == 1) {
            return getListOfStringsMatchingLastWord(astring, this.getGameRules().getRules());
        }

        if (astring.length == 2) {
            GameRules gameRules = this.getGameRules();
            if (gameRules.areSameType(
                    astring[0], GameRulesValueType.BOOLEAN_VALUE)) {
                return getListOfStringsMatchingLastWord(astring, new String[] {"true", "false"});
            }
        }

        return null;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.gamerule.usage";
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
