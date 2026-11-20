package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class CommandSpreadPlayers extends CommandBase {

    private CommandSpreadPlayersPosition[] createInitialPositions(
            Random random, int i, double d0, double d1, double d2, double d3) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        CommandSpreadPlayersPosition[] commandSpreadPlayersPositionArray =
                new CommandSpreadPlayersPosition[i];
        boolean flag = flag1;
        int j = 0;

        while (j < commandSpreadPlayersPositionArray.length) {
            CommandSpreadPlayersPosition commandSpreadPlayersPosition =
                    new CommandSpreadPlayersPosition();
            commandSpreadPlayersPosition.method_874329c84755abd55bd7(random, d0, d1, d2, d3);
            commandSpreadPlayersPositionArray[j] = commandSpreadPlayersPosition;
            j++;
            if (!flag) {
                break;
            }
        }

        return commandSpreadPlayersPositionArray;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    private double spread(
            List list,
            World world,
            CommandSpreadPlayersPosition[] commandSpreadPlayersPositionArray,
            boolean flag) {
        boolean flag2 = CommandException.method_b417d7500ec59d69e373();
        double d0 = 0.0;
        int i = 0;
        HashMap hashmap = Maps.newHashMap();
        boolean flag1 = flag2;
        int j = 0;

        while (j < list.size()) {
            Entity entity;
            CommandSpreadPlayersPosition commandSpreadPlayersPosition;
            {
                entity = (Entity) list.get(j);
                if (flag) {
                    Team team =
                            entity instanceof PlayerEntity
                                    ? ((PlayerEntity) entity).getTeam()
                                    : null;
                    if (!hashmap.containsKey(team)) {
                        hashmap.put(team, commandSpreadPlayersPositionArray[i++]);
                    }

                    commandSpreadPlayersPosition = (CommandSpreadPlayersPosition) hashmap.get(team);
                    if (!(flag1)) {

                        commandSpreadPlayersPosition = commandSpreadPlayersPositionArray[i++];
                    }
                } else {

                    commandSpreadPlayersPosition = commandSpreadPlayersPositionArray[i++];
                }
            }

            entity.setPositionAndUpdate(
                    MathHelper.floor(commandSpreadPlayersPosition.uj) + 0.5F,
                    commandSpreadPlayersPosition.method_1912249ddb3c94e7de19(world),
                    MathHelper.floor(commandSpreadPlayersPosition.um) + 0.5);
            double MAX_VALUE = Double.MAX_VALUE;
            int k = 0;

            while (true) {
                if (k < commandSpreadPlayersPositionArray.length) {
                    if (commandSpreadPlayersPosition != commandSpreadPlayersPositionArray[k]) {
                        double d1 =
                                commandSpreadPlayersPosition.method_5c985ae91d9d2ff2ff2e(
                                        commandSpreadPlayersPositionArray[k]);
                        MAX_VALUE = Math.min(d1, MAX_VALUE);
                    }

                    k++;
                    if (flag1) {
                        continue;
                    }
                }

                d0 += MAX_VALUE;
                j++;
                if (!flag1) {
                    return d0 / list.size();
                }
                break;
            }
        }

        return d0 / list.size();
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.spreadplayers.usage";
    }

    private int spreadPositions(
            CommandSpreadPlayersPosition commandSpreadPlayersPosition,
            double d0,
            World world,
            Random random,
            double d1,
            double d2,
            double d3,
            double d4,
            CommandSpreadPlayersPosition[] commandSpreadPlayersPositionArray,
            boolean flag)
            throws CommandException {
        byte b0 = 1;
        boolean flag2 = CommandException.method_b417d7500ec59d69e373();
        double MAX_VALUE = Float.MAX_VALUE;
        int i = 0;
        boolean flag1 = flag2;

        while (i < 10000 && b0 != 0) {
            b0 = 0;
            MAX_VALUE = Float.MAX_VALUE;
            int k = 0;

            while (k < commandSpreadPlayersPositionArray.length) {
                CommandSpreadPlayersPosition commandSpreadPlayersPositionValue =
                        commandSpreadPlayersPositionArray[k];
                int j = 0;
                CommandSpreadPlayersPosition commandSpreadPlayersPositionValue2 =
                        new CommandSpreadPlayersPosition();
                int l = 0;

                while (l < commandSpreadPlayersPositionArray.length) {
                    if (k != l) {
                        CommandSpreadPlayersPosition commandSpreadPlayersPositionValue3 =
                                commandSpreadPlayersPositionArray[l];
                        double d6 =
                                commandSpreadPlayersPositionValue.method_5c985ae91d9d2ff2ff2e(
                                        commandSpreadPlayersPositionValue3);
                        MAX_VALUE = Math.min(d6, MAX_VALUE);
                        if (d6 < d0) {
                            j++;
                            commandSpreadPlayersPositionValue2.uj =
                                    commandSpreadPlayersPositionValue2.uj
                                            + (commandSpreadPlayersPositionValue3.uj
                                                    - commandSpreadPlayersPositionValue.uj);
                            commandSpreadPlayersPositionValue2.um =
                                    commandSpreadPlayersPositionValue2.um
                                            + (commandSpreadPlayersPositionValue3.um
                                                    - commandSpreadPlayersPositionValue.um);
                        }
                    }

                    l++;
                    if (!flag1) {
                        break;
                    }
                }

                if (j > 0) {
                    {
                        commandSpreadPlayersPositionValue2.uj /= j;
                        commandSpreadPlayersPositionValue2.um /= j;
                        double d7 = commandSpreadPlayersPositionValue2.aN();
                        if (d7 > 0.0) {
                            commandSpreadPlayersPositionValue2.method_8d9b536bcdc9991c48fb();
                            commandSpreadPlayersPositionValue.method_49177e68d1cf2f23bbc7(
                                    commandSpreadPlayersPositionValue2);
                            if (!(flag1)) {

                                commandSpreadPlayersPositionValue.method_874329c84755abd55bd7(
                                        random, d1, d2, d3, d4);
                            }
                        } else {

                            commandSpreadPlayersPositionValue.method_874329c84755abd55bd7(
                                    random, d1, d2, d3, d4);
                        }
                    }

                    b0 = 1;
                }

                if (commandSpreadPlayersPositionValue.method_33897f45f2222b224424(d1, d2, d3, d4)) {
                    b0 = 1;
                }

                k++;
                if (!flag1) {
                    break;
                }
            }

            if (b0 == 0) {
                CommandSpreadPlayersPosition[] commandSpreadPlayersPositionArrayValue =
                        commandSpreadPlayersPositionArray;
                int j1 = commandSpreadPlayersPositionArray.length;
                int i1 = 0;

                while (i1 < j1) {
                    CommandSpreadPlayersPosition commandSpreadPlayersPositionValue4 =
                            commandSpreadPlayersPositionArrayValue[i1];
                    if (!commandSpreadPlayersPositionValue4.method_4e028d13c660a558a4cd(world)) {
                        commandSpreadPlayersPositionValue4.method_874329c84755abd55bd7(
                                random, d1, d2, d3, d4);
                        b0 = 1;
                    }

                    i1++;
                    if (!flag1) {
                        break;
                    }
                }
            }

            i++;
            if (!flag1) {
                break;
            }
        }

        if (i >= 10000) {
            throw new CommandException(
                    "commands.spreadplayers.failure." + (flag ? "teams" : "players"),
                    new Object[] {
                        commandSpreadPlayersPositionArray.length,
                        commandSpreadPlayersPosition.uj,
                        commandSpreadPlayersPosition.um,
                        String.format("%.2f", MAX_VALUE)
                    });
        } else {
            return i;
        }
    }


    public String getCommandName() {
        return "spreadplayers";
    }

    private int getNumberOfTeams(List list) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        HashSet hashset = Sets.newHashSet();
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_34306 = (list).iterator();
                (__recoveryIterator171_34306).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_34306).next();
            {
                if (entity instanceof PlayerEntity) {
                    hashset.add(((PlayerEntity) entity).getTeam());
                    if (!(flag)) {

                        hashset.add(null);
                    }
                } else {

                    hashset.add(null);
                }
            }

            if (!flag) {
                break;
            }
        }

        return hashset.size();
    }

    private void func_110669_a(
            CommandSender commandSender,
            List list,
            CommandSpreadPlayersPosition commandSpreadPlayersPosition,
            double d0,
            double d1,
            World world,
            boolean flag)
            throws CommandException {
        Random random = new Random();
        double d2 = commandSpreadPlayersPosition.uj - d1;
        double d3 = commandSpreadPlayersPosition.um - d1;
        double d4 = commandSpreadPlayersPosition.uj + d1;
        double d5 = commandSpreadPlayersPosition.um + d1;
        CommandSpreadPlayersPosition[] initialPositions =
                this.createInitialPositions(
                        random,
                        flag ? this.getNumberOfTeams(list) : list.size(),
                        d2,
                        d3,
                        d4,
                        d5);
        int i =
                this.spreadPositions(
                        commandSpreadPlayersPosition,
                        d0,
                        world,
                        random,
                        d2,
                        d3,
                        d4,
                        d5,
                        initialPositions,
                        flag);
        double d6 =
                this.spread(list, world, initialPositions, flag);
        notifyOperators(
                commandSender,
                this,
                "commands.spreadplayers.success." + (flag ? "teams" : "players"),
                new Object[] {
                    initialPositions.length,
                    commandSpreadPlayersPosition.uj,
                    commandSpreadPlayersPosition.um
                });
        if (initialPositions.length > 1) {
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.spreadplayers.info." + (flag ? "teams" : "players"),
                            new Object[] {String.format("%.2f", d6), i}));
        }
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length < 6) {
            throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
        }

        byte b0 = 0;
        BlockPos position = commandSender.getPosition();
        double x = position.getX();
        int i = b0 + 1;
        double d1 = parseDouble(x, astring[b0], true);
        double d2 = parseDouble(position.getZ(), astring[i++], true);
        double d3 = parseDouble(astring[i++], 0.0);
        double d4 = parseDouble(astring[i++], d3 + 1.0);
        boolean flag1 = parseBoolean(astring[i++]);
        ArrayList arrayList = Lists.newArrayList();

        while (i < astring.length) {
            label46:
            {
                String text = astring[i++];
                if (PlayerSelector.hasArguments(text)) {
                    List list =
                            PlayerSelector.matchEntities(
                                    commandSender, text, Entity.class);
                    if (list.size() == 0) {
                        throw new EntityNotFoundException();
                    }

                    arrayList.addAll(list);
                    if (flag) {
                        break label46;
                    }
                }

                EntityPlayerMP playerByUsername =
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getPlayerByUsername(text);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException();
                }

                arrayList.add(playerByUsername);
            }

            if (!flag) {
                break;
            }
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ENTITIES, arrayList.size());
        if (arrayList.isEmpty()) {
            throw new EntityNotFoundException();
        }

        commandSender.addChatMessage(
                new ChatComponentTranslation(
                        "commands.spreadplayers.spreading." + (flag1 ? "teams" : "players"),
                        new Object[] {arrayList.size(), d4, d1, d2, d3}));
        this.func_110669_a(
                commandSender,
                arrayList,
                new CommandSpreadPlayersPosition(d1, d2),
                d3,
                d4,
                ((Entity) arrayList.get(0)).world,
                flag1);
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
