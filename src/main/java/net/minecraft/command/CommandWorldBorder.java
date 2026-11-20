package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.border.WorldBorder;

import java.util.List;

public class CommandWorldBorder extends CommandBase {

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, new String[] {"set", "center", "damage", "warning", "add", "get"})
                : (astring.length == 2 && astring[0].equals("damage")
                        ? getListOfStringsMatchingLastWord(astring, new String[] {"buffer", "amount"})
                        : (astring.length == 2 && astring[0].equals("warning")
                                ? getListOfStringsMatchingLastWord(
                                        astring, new String[] {"time", "distance"})
                                : null));
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length < 1) {
            throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
        }

        WorldBorder worldBorder = this.getWorldBorder();
        if (astring[0].equals("set")) {
            if (astring.length != 2 && astring.length != 3) {
                throw new WrongUsageException(
                        "commands.worldborder.set.usage", new Object[0]);
            }

            double targetSize = worldBorder.getTargetSize();
            double d1 = parseDouble(astring[1], 1.0, 6.0E7);
            long j1;
            if (astring.length > 2) {
                String text = astring[2];

                long j4 = 0L;

                j1 = parseLong(text, j4, 9223372036854775L);

                j1 *= 1000L;
            } else {

                j1 = 0L;
            }

            long i = j1;

            if (i > 0L) {
                worldBorder.setTransition(targetSize, d1, i);
                if (targetSize > d1) {
                    String textValue = "commands.worldborder.setSlowly.shrink.success";
                    Object[] aobject =
                            new Object[] {
                                String.format("%.1f", d1), String.format("%.1f", targetSize), null
                            };

                    aobject[2] = Long.toString(i / 1000L);
                    notifyOperators(commandSender, this, textValue, aobject);
                    if (flag) {
                        return;
                    }
                }

                String textValue2 = "commands.worldborder.setSlowly.grow.success";
                Object[] aobject1 =
                        new Object[] {
                            String.format("%.1f", d1), String.format("%.1f", targetSize), null
                        };

                aobject1[2] = Long.toString(i / 1000L);
                notifyOperators(commandSender, this, textValue2, aobject1);
                if (flag) {
                    return;
                }
            }

            worldBorder.setTransition(d1);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.worldborder.set.success",
                    new Object[] {String.format("%.1f", d1), String.format("%.1f", targetSize)});
            if (flag) {
                return;
            }
        }

        if (astring[0].equals("add")) {
            if (astring.length != 2 && astring.length != 3) {
                throw new WrongUsageException(
                        "commands.worldborder.add.usage", new Object[0]);
            }

            double diameter = worldBorder.getDiameter();
            double d8 =
                    diameter + parseDouble(astring[1], -diameter, 6.0E7 - diameter);
            long k1 = worldBorder.getTimeUntilTarget();
            long i2;
            if (astring.length > 2) {
                String textValue3 = astring[2];

                long k2 = 0L;

                i2 = parseLong(textValue3, k2, 9223372036854775L);

                i2 *= 1000L;
            } else {

                i2 = 0L;
            }

            long j = k1 + i2;

            if (j > 0L) {
                worldBorder.setTransition(diameter, d8, j);
                if (diameter > d8) {
                    String textValue4 = "commands.worldborder.setSlowly.shrink.success";
                    Object[] aobject2 =
                            new Object[] {
                                String.format("%.1f", d8), String.format("%.1f", diameter), null
                            };

                    aobject2[2] = Long.toString(j / 1000L);
                    notifyOperators(commandSender, this, textValue4, aobject2);
                    if (flag) {
                        return;
                    }
                }

                String textValue5 = "commands.worldborder.setSlowly.grow.success";
                Object[] aobject3 =
                        new Object[] {
                            String.format("%.1f", d8), String.format("%.1f", diameter), null
                        };

                aobject3[2] = Long.toString(j / 1000L);
                notifyOperators(commandSender, this, textValue5, aobject3);
                if (flag) {
                    return;
                }
            }

            worldBorder.setTransition(d8);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.worldborder.set.success",
                    new Object[] {String.format("%.1f", d8), String.format("%.1f", diameter)});
            if (flag) {
                return;
            }
        }

        if (astring[0].equals("center")) {
            if (astring.length != 3) {
                throw new WrongUsageException(
                        "commands.worldborder.center.usage", new Object[0]);
            }

            BlockPos position = commandSender.getPosition();
            double d2 = parseDouble(position.getX() + 0.5, astring[1], true);
            double d3 = parseDouble(position.getZ() + 0.5, astring[2], true);
            worldBorder.setCenter(d2, d3);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.worldborder.center.success",
                    new Object[] {d2, d3});
            if (flag) {
                return;
            }
        }

        if (astring[0].equals("damage")) {
            if (astring.length < 2) {
                throw new WrongUsageException(
                        "commands.worldborder.damage.usage", new Object[0]);
            }

            if (astring[1].equals("buffer")) {
                if (astring.length != 3) {
                    throw new WrongUsageException(
                            "commands.worldborder.damage.buffer.usage", new Object[0]);
                }

                double d5 = parseDouble(astring[2], 0.0);
                double damageBuffer = worldBorder.getDamageBuffer();
                worldBorder.setDamageBuffer(d5);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.worldborder.damage.buffer.success",
                        new Object[] {
                            String.format("%.1f", d5), String.format("%.1f", damageBuffer)
                        });
                if (flag) {
                    return;
                }
            }

            if (!astring[1].equals("amount")) {
                return;
            }

            if (astring.length != 3) {
                throw new WrongUsageException(
                        "commands.worldborder.damage.amount.usage", new Object[0]);
            }

            double d6 = parseDouble(astring[2], 0.0);
            double damageAmount = worldBorder.getDamageAmount();
            worldBorder.setDamageAmount(d6);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.worldborder.damage.amount.success",
                    new Object[] {String.format("%.2f", d6), String.format("%.2f", damageAmount)});
            if (flag) {
                return;
            }
        }

        if (astring[0].equals("warning")) {
            if (astring.length < 2) {
                throw new WrongUsageException(
                        "commands.worldborder.warning.usage", new Object[0]);
            }

            label107:
            {
                int k = parseInt(astring[2], 0);
                if (astring[1].equals("time")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException(
                                "commands.worldborder.warning.time.usage", new Object[0]);
                    }

                    int l = worldBorder.getWarningTime();
                    worldBorder.setWarningTime(k);
                    notifyOperators(
                            commandSender,
                            this,
                            "commands.worldborder.warning.time.success",
                            new Object[] {k, l});
                    if (flag) {
                        break label107;
                    }
                }

                if (astring[1].equals("distance")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException(
                                "commands.worldborder.warning.distance.usage", new Object[0]);
                    }

                    int warningDistance = worldBorder.getWarningDistance();
                    worldBorder.setWarningDistance(k);
                    notifyOperators(
                            commandSender,
                            this,
                            "commands.worldborder.warning.distance.success",
                            new Object[] {k, warningDistance});
                }
            }

            if (flag) {
                return;
            }
        }

        if (astring[0].equals("get")) {
            double diameterValue = worldBorder.getDiameter();
            commandSender.setCommandStat(
                    CommandResultStatsType.QUERY_RESULT, MathHelper.floor(diameterValue + 0.5));
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.worldborder.get.success",
                            new Object[] {String.format("%.0f", diameterValue)}));
        }
    }


    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.worldborder.usage";
    }

    protected WorldBorder getWorldBorder() {
        return MinecraftServer.getServer().worldServers[0].getWorldBorder();
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandName() {
        return "worldborder";
    }
}
