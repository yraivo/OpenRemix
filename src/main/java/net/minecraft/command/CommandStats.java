package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CommandStats extends CommandBase {

    public String getCommandName() {
        return "stats";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length < 1) {
            throw new WrongUsageException("commands.stats.usage", new Object[0]);
        }

        byte b0;
        {
            if (astring[0].equals("entity")) {
                b0 = 0;
                if (!(!flag)) {

                    if (!astring[0].equals("block")) {
                        throw new WrongUsageException("commands.stats.usage", new Object[0]);
                    }

                    b0 = 1;
                }
            } else {

                if (!astring[0].equals("block")) {
                    throw new WrongUsageException("commands.stats.usage", new Object[0]);
                }

                b0 = 1;
            }
        }

        byte b1;
        {
            if (b0 != 0) {
                if (astring.length < 5) {
                    throw new WrongUsageException(
                            "commands.stats.block.usage", new Object[0]);
                }

                b1 = 4;
                if (!(!flag)) {

                    if (astring.length < 3) {
                        throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
                    }

                    b1 = 2;
                }
            } else {

                if (astring.length < 3) {
                    throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
                }

                b1 = 2;
            }
        }

        int i = b1 + 1;
        String text = astring[b1];
        if ("set".equals(text)) {
            if (astring.length < i + 3) {
                if (i == 5) {
                    throw new WrongUsageException(
                            "commands.stats.block.set.usage", new Object[0]);
                }

                throw new WrongUsageException(
                        "commands.stats.entity.set.usage", new Object[0]);
            }
        } else {
            if (!"clear".equals(text)) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }

            if (astring.length < i + 1) {
                if (i == 5) {
                    throw new WrongUsageException(
                            "commands.stats.block.clear.usage", new Object[0]);
                }

                throw new WrongUsageException(
                        "commands.stats.entity.clear.usage", new Object[0]);
            }
        }

        CommandResultStatsType commandResultStatsType = CommandResultStatsType.V(astring[i++]);
        if (commandResultStatsType == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
        }

        World world;
        CommandResultStats commandResultStats;
        label101:
        {
            world = commandSender.getEntityWorld();
            if (b0 != 0) {
                BlockPos blockPos = parseBlockPos(commandSender, astring, 1, false);
                TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity == null) {
                    throw new CommandException(
                            "commands.stats.noCompatibleBlock",
                            new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
                }

                if (tileEntity instanceof TileEntityCommandBlock) {
                    commandResultStats =
                            ((TileEntityCommandBlock) tileEntity).getCommandResultStats();
                    if (!flag) {
                        break label101;
                    }
                }

                if (!(tileEntity instanceof TileEntitySign)) {
                    throw new CommandException(
                            "commands.stats.noCompatibleBlock",
                            new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
                }

                commandResultStats = ((TileEntitySign) tileEntity).getStats();
                if (!flag) {
                    break label101;
                }
            }

            Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[1]);
            commandResultStats = entity.getCommandStats();
        }

        label90:
        {
            if ("set".equals(text)) {
                String textValue = astring[i++];
                String textValue2 = astring[i];
                if (textValue.length() == 0 || textValue2.length() == 0) {
                    throw new CommandException("commands.stats.failed", new Object[0]);
                }

                CommandResultStats.setScoreBoardStat(
                        commandResultStats, commandResultStatsType, textValue, textValue2);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.stats.success",
                        new Object[] {commandResultStatsType.getTypeName(), textValue2, textValue});
                if (!flag) {
                    break label90;
                }
            }

            if ("clear".equals(text)) {
                CommandResultStats.setScoreBoardStat(
                        commandResultStats, commandResultStatsType, (String) null, (String) null);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.stats.cleared",
                        new Object[] {commandResultStatsType.getTypeName()});
            }
        }

        if (b0 != 0) {
            BlockPos blockPosValue = parseBlockPos(commandSender, astring, 1, false);
            TileEntity tileEntityValue = world.getTileEntity(blockPosValue);
            tileEntityValue.markDirty();
        }
    }


    protected String[] y() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.stats.usage";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, new String[] {"entity", "block"})
                : (astring.length == 2 && astring[0].equals("entity")
                        ? getListOfStringsMatchingLastWord(astring, this.y())
                        : ((astring.length != 3 || !astring[0].equals("entity"))
                                        && (astring.length != 5 || !astring[0].equals("block"))
                                ? ((astring.length != 4 || !astring[0].equals("entity"))
                                                && (astring.length != 6
                                                        || !astring[0].equals("block"))
                                        ? (astring.length == 6 && astring[0].equals("entity")
                                                        || astring.length == 8
                                                                && astring[0].equals("block")
                                                ? getListOfStringsMatchingLastWord(astring, this.eN())
                                                : null)
                                        : getListOfStringsMatchingLastWord(
                                                astring, CommandResultStatsType.getTypeNames()))
                                : getListOfStringsMatchingLastWord(
                                        astring, new String[] {"set", "clear"})));
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    protected List eN() {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        Collection scoreObjectives =
                MinecraftServer.getServer()
                        .worldServerForDimension(0)
                        .getScoreboard()
                        .getScoreObjectives();
        ArrayList arrayList = Lists.newArrayList();
        Iterator iterator = scoreObjectives.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            ScoreObjective scoreObjective = (ScoreObjective) iterator.next();
            if (!scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }

            if (!flag) {
                break;
            }
        }

        return arrayList;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return astring.length > 0 && astring[0].equals("entity") && i == 1;
    }
}
