package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandBase {

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.weather.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length >= 1 && astring.length <= 2) {
            int i = (300 + new Random().nextInt(600)) * 20;
            if (astring.length >= 2) {
                i = parseInt(astring[1], 1, 1000000) * 20;
            }

            label40:
            {
                WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
                WorldInfo worldInfo = worldServer.getWorldInfo();
                if ("clear".equalsIgnoreCase(astring[0])) {
                    worldInfo.setCleanWeatherTime(i);
                    worldInfo.setRainTime(0);
                    worldInfo.setThunderTime(0);
                    worldInfo.setRaining(false);
                    worldInfo.setThundering(false);
                    notifyOperators(
                            commandSender, this, "commands.weather.clear", new Object[0]);
                    if (!flag) {
                        break label40;
                    }
                }

                if ("rain".equalsIgnoreCase(astring[0])) {
                    worldInfo.setCleanWeatherTime(0);
                    worldInfo.setRainTime(i);
                    worldInfo.setThunderTime(i);
                    worldInfo.setRaining(true);
                    worldInfo.setThundering(false);
                    notifyOperators(
                            commandSender, this, "commands.weather.rain", new Object[0]);
                    if (!flag) {
                        break label40;
                    }
                }

                if (!"thunder".equalsIgnoreCase(astring[0])) {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }

                worldInfo.setCleanWeatherTime(0);
                worldInfo.setRainTime(i);
                worldInfo.setThunderTime(i);
                worldInfo.setRaining(true);
                worldInfo.setThundering(true);
                notifyOperators(
                        commandSender, this, "commands.weather.thunder", new Object[0]);
            }

            if (!flag) {
                return;
            }
        }

        throw new WrongUsageException("commands.weather.usage", new Object[0]);
    }


    public String getCommandName() {
        return "weather";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, new String[] {"clear", "rain", "thunder"})
                : null;
    }
}
