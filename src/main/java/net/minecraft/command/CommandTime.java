package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.List;

public class CommandTime extends CommandBase {

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length > 1) {
            if (astring[0].equals("set")) {
                int l;
                label44:
                {
                    if (astring[1].equals("day")) {
                        l = 1000;
                        if (!flag) {
                            break label44;
                        }
                    }

                    if (astring[1].equals("night")) {
                        l = 13000;
                        if (!flag) {
                            break label44;
                        }
                    }

                    l = parseInt(astring[1], 0);
                }

                this.method_bccf68325e43e0055c24(commandSender, l);
                notifyOperators(
                        commandSender, this, "commands.time.set", new Object[] {l});
                return;
            }

            if (astring[0].equals("add")) {
                int k = parseInt(astring[1], 0);
                this.method_df0b34efbad8e4856f06(commandSender, k);
                notifyOperators(
                        commandSender, this, "commands.time.added", new Object[] {k});
                return;
            }

            if (astring[0].equals("query")) {
                if (astring[1].equals("daytime")) {
                    long worldTime = commandSender.getEntityWorld().getWorldTime();

                    int j = (int) (worldTime % 2147483647L);
                    commandSender.setCommandStat(CommandResultStatsType.QUERY_RESULT, j);
                    notifyOperators(
                            commandSender, this, "commands.time.query", new Object[] {j});
                    return;
                }

                if (astring[1].equals("gametime")) {
                    long k1 = commandSender.getEntityWorld().getTotalWorldTime();

                    int i = (int) (k1 % 2147483647L);
                    commandSender.setCommandStat(CommandResultStatsType.QUERY_RESULT, i);
                    notifyOperators(
                            commandSender, this, "commands.time.query", new Object[] {i});
                    return;
                }
            }
        }

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }


    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandName() {
        return "time";
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.time.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    protected void method_bccf68325e43e0055c24(CommandSender commandSender, int i) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        int j = 0;
        boolean flag = flag1;

        while (j < MinecraftServer.getServer().worldServers.length) {
            MinecraftServer.getServer().worldServers[j].setWorldTime(i);
            j++;
            if (!flag) {
                break;
            }
        }
    }

    protected void method_df0b34efbad8e4856f06(CommandSender commandSender, int i) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        int j = 0;
        boolean flag = flag1;

        while (j < MinecraftServer.getServer().worldServers.length) {
            WorldServer worldServer = MinecraftServer.getServer().worldServers[j];
            worldServer.setWorldTime(worldServer.getWorldTime() + i);
            j++;
            if (!flag) {
                break;
            }
        }
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, new String[] {"set", "add", "query"})
                : (astring.length == 2 && astring[0].equals("set")
                        ? getListOfStringsMatchingLastWord(astring, new String[] {"day", "night"})
                        : (astring.length == 2 && astring[0].equals("query")
                                ? getListOfStringsMatchingLastWord(
                                        astring, new String[] {"daytime", "gametime"})
                                : null));
    }
}
