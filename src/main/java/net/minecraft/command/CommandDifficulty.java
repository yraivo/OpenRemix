package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

import java.util.List;

public class CommandDifficulty extends CommandBase {

    private static String[] packedTextFragments;

    public int getRequiredPermissionLevel() {
        return 2;
    }

    protected EnumDifficulty getDifficultyFromCommand(String text) throws CommandException {
        return text.equalsIgnoreCase("peaceful") || text.equalsIgnoreCase(packedTextFragments[0])
                ? EnumDifficulty.PEACEFUL
                : (text.equalsIgnoreCase("easy") || text.equalsIgnoreCase(packedTextFragments[1])
                        ? EnumDifficulty.EASY
                        : (!text.equalsIgnoreCase("normal")
                                        && !text.equalsIgnoreCase(packedTextFragments[2])
                                ? (!text.equalsIgnoreCase("hard")
                                                && !text.equalsIgnoreCase(packedTextFragments[3])
                                        ? EnumDifficulty.getDifficultyEnum(
                                                parseInt(text, 0, 3))
                                        : EnumDifficulty.HARD)
                                : EnumDifficulty.NORMAL));
    }

    static {
        net.minecraft.command.CommandDifficulty.packedTextFragments = new String[6];
        net.minecraft.command.CommandDifficulty.packedTextFragments[0] = new String("p");
        net.minecraft.command.CommandDifficulty.packedTextFragments[1] = new String("e");
        net.minecraft.command.CommandDifficulty.packedTextFragments[2] = new String("n");
        net.minecraft.command.CommandDifficulty.packedTextFragments[3] = new String("h");
    }

    public String getCommandName() {
        return "difficulty";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, new String[] {"peaceful", "easy", "normal", "hard"})
                : null;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.difficulty.usage";
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        if (astring.length <= 0) {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }

        EnumDifficulty enumDifficulty = this.getDifficultyFromCommand(astring[0]);
        MinecraftServer.getServer().setDifficultyForAllWorlds(enumDifficulty);
        notifyOperators(
                commandSender,
                this,
                "commands.difficulty.success",
                new Object[] {
                    new ChatComponentTranslation(
                            enumDifficulty.getDifficultyResourceKey(), new Object[0])
                });
    }
}
