package net.minecraft.command;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class CommandResultStats {
    private static String[] STRING_RESULT_TYPES;

    private String[] field_179675_c;
    private static int NUM_RESULT_TYPES;

    private String[] field_179673_d;

    public void applyResult(
            CommandSender commandSender, CommandResultStatsType commandResultStatsType, int i) {
        String text = this.field_179675_c[commandResultStatsType.getTypeID()];
        if (text != null) {
            String textValue;
            try {
                textValue = CommandBase.getEntityName(commandSender, text);
            } catch (EntityNotFoundException entityNotFoundException) {
                return;
            }

            String textValue2 = this.field_179673_d[commandResultStatsType.getTypeID()];
            if (textValue2 != null) {
                Scoreboard scoreboard = commandSender.getEntityWorld().getScoreboard();
                ScoreObjective objective = scoreboard.getObjective(textValue2);

                try {
                    if (false)
                        throw (net.minecraft.command.EntityNotFoundException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (objective == null || !scoreboard.entityHasObjective(textValue, objective)) {
                        return;
                    }
                } catch (EntityNotFoundException entityNotFoundExceptionValue) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            method_1b02ea66e116e7c98a28(entityNotFoundExceptionValue));
                }

                Score valueFromObjective = scoreboard.getValueFromObjective(textValue, objective);
                valueFromObjective.setScorePoints(i);
            }
        }
    }

    public static void setScoreBoardStat(
            CommandResultStats commandResultStats,
            CommandResultStatsType commandResultStatsType,
            String text,
            String textValue) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (text != null && text.length() != 0 && textValue != null && textValue.length() != 0) {
            if (commandResultStats.field_179675_c
                            == net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES
                    || commandResultStats.field_179673_d
                            == net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES) {
                commandResultStats.field_179675_c =
                        new String[net.minecraft.command.CommandResultStats.NUM_RESULT_TYPES];
                commandResultStats.field_179673_d =
                        new String[net.minecraft.command.CommandResultStats.NUM_RESULT_TYPES];
            }

            commandResultStats.field_179675_c[commandResultStatsType.getTypeID()] = text;
            commandResultStats.field_179673_d[commandResultStatsType.getTypeID()] = textValue;
            if (!flag) {
                return;
            }
        }

        removeScoreBoardStat(commandResultStats, commandResultStatsType);
    }

    public void writeStatsToNBT(CompoundTag compoundTag) {
        CompoundTag compoundTagValue = new CompoundTag();
        CommandResultStatsType[] commandResultStatsTypeArray = CommandResultStatsType.values();
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        int length = commandResultStatsTypeArray.length;
        int j = 0;
        boolean flag = flag1;

        while (j < length) {
            CommandResultStatsType commandResultStatsType = commandResultStatsTypeArray[j];
            String text = this.field_179675_c[commandResultStatsType.getTypeID()];
            String textValue = this.field_179673_d[commandResultStatsType.getTypeID()];
            if (text != null && textValue != null) {
                compoundTagValue.setString(commandResultStatsType.getTypeName() + "Name", text);
                compoundTagValue.setString(
                        commandResultStatsType.getTypeName() + "Objective", textValue);
            }

            j++;
            if (!flag) {
                break;
            }
        }

        if (!compoundTagValue.hasNoTags()) {
            compoundTag.setTag("CommandStats", compoundTagValue);
        }
    }

    public void readStatsFromNBT(CompoundTag compoundTag) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (compoundTag.hasKey("CommandStats", 10)) {
            CompoundTag compoundTagValue = compoundTag.getCompoundTag("CommandStats");

            for (CommandResultStatsType commandResultStatsType : CommandResultStatsType.values()) {
                String text = commandResultStatsType.getTypeName() + "Name";
                String textValue = commandResultStatsType.getTypeName() + "Objective";
                if (compoundTagValue.hasKey(text, 8) && compoundTagValue.hasKey(textValue, 8)) {
                    String textValue2 = compoundTagValue.getString(text);
                    String textValue3 = compoundTagValue.getString(textValue);
                    setScoreBoardStat(this, commandResultStatsType, textValue2, textValue3);
                }

                if (flag) {
                    break;
                }
            }
        }
    }

    static {
        net.minecraft.command.CommandResultStats.NUM_RESULT_TYPES =
                CommandResultStatsType.values().length;
        net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES =
                new String[net.minecraft.command.CommandResultStats.NUM_RESULT_TYPES];
    }

    private static void removeScoreBoardStat(
            CommandResultStats commandResultStats, CommandResultStatsType commandResultStatsType) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (commandResultStats.field_179675_c
                        != net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES
                && commandResultStats.field_179673_d
                        != net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES) {
            commandResultStats.field_179675_c[commandResultStatsType.getTypeID()] = null;
            commandResultStats.field_179673_d[commandResultStatsType.getTypeID()] = null;
            byte b0 = 1;

            for (CommandResultStatsType commandResultStatsTypeValue :
                    CommandResultStatsType.values()) {
                if (commandResultStats.field_179675_c[commandResultStatsTypeValue.getTypeID()]
                                != null
                        && commandResultStats
                                        .field_179673_d[commandResultStatsTypeValue.getTypeID()]
                                != null) {
                    b0 = 0;
                    if (!flag) {
                        break;
                    }
                }

                if (flag) {
                    break;
                }
            }

            if (b0 != 0) {
                commandResultStats.field_179675_c =
                        net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES;
                commandResultStats.field_179673_d =
                        net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES;
            }
        }
    }

    public CommandResultStats() {
        this.field_179675_c = net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES;
        this.field_179673_d = net.minecraft.command.CommandResultStats.STRING_RESULT_TYPES;
    }

    public void copyFrom(CommandResultStats commandResultStats) {
        CommandResultStatsType[] commandResultStatsTypeArray = CommandResultStatsType.values();
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        int length = commandResultStatsTypeArray.length;
        boolean flag = flag1;
        int j = 0;

        while (j < length) {
            CommandResultStatsType commandResultStatsType = commandResultStatsTypeArray[j];
            setScoreBoardStat(
                    this,
                    commandResultStatsType,
                    commandResultStats.field_179675_c[commandResultStatsType.getTypeID()],
                    commandResultStats.field_179673_d[commandResultStatsType.getTypeID()]);
            j++;
            if (flag) {
                break;
            }
        }
    }

    private static EntityNotFoundException method_1b02ea66e116e7c98a28(
            EntityNotFoundException entityNotFoundException) {
        return entityNotFoundException;
    }
}
