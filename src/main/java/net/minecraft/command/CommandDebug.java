package net.minecraft.command;

import net.minecraft.profiler.ProfilerResult;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommandDebug extends CommandBase {
    private int ef;
    private static String[] packedTextFragments;

    private static Logger logger;
    private long vj;

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public String getCommandName() {
        return "debug";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length < 1) {
            throw new WrongUsageException("commands.debug.usage", new Object[0]);
        }

        if (astring[0].equals("start")) {
            if (astring.length != 1) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }

            notifyOperators(commandSender, this, "commands.debug.start", new Object[0]);
            MinecraftServer.getServer().enableProfiling();
            this.vj = MinecraftServer.getCurrentTimeMillis();
            this.ef = MinecraftServer.getServer().getTickCounter();
            if (!flag) {
                return;
            }
        }

        if (astring[0].equals("stop")) {
            if (astring.length != 1) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }

            if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
                throw new CommandException("commands.debug.notStarted", new Object[0]);
            }

            long currentTimeMillis = MinecraftServer.getCurrentTimeMillis();
            int j = MinecraftServer.getServer().getTickCounter();
            long k = currentTimeMillis - this.vj;
            int l = j - this.ef;
            this.method_5bc9561ede986193a8dc(k, l);
            MinecraftServer.getServer().theProfiler.profilingEnabled = false;
            notifyOperators(
                    commandSender,
                    this,
                    "commands.debug.stop",
                    new Object[] {(float) k / 1000.0F, l});
            if (!flag) {
                return;
            }
        }

        if (astring[0].equals("chunk")) {
            if (astring.length != 4) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }

            parseBlockPos(commandSender, astring, 1, true);
        }
    }

    private void method_940ce73bc4f20092c86e(int i, String text, StringBuilder stringbuilder) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        List list = MinecraftServer.getServer().theProfiler.getProfilingData(text);
        boolean flag = flag1;
        if (list != null && list.size() >= 3) {
            int j = 1;

            while (j < list.size()) {
                ProfilerResult profilerResult =
                        (ProfilerResult) list.get(j);
                stringbuilder.append(String.format("[%02d] ", i));
                int k = 0;

                while (k < i) {
                    stringbuilder.append(packedTextFragments[0]);
                    k++;
                    if (!flag) {
                        break;
                    }
                }

                stringbuilder
                        .append(profilerResult.sectionName)
                        .append(" - ")
                        .append(String.format("%.2f", profilerResult.parentPercent))
                        .append("%/")
                        .append(String.format("%.2f", profilerResult.totalPercent))
                        .append("%\n");
                if (!profilerResult.sectionName.equals("unspecified")) {
                    try {
                        this.method_940ce73bc4f20092c86e(
                                i + 1,
                                text + packedTextFragments[1] + profilerResult.sectionName,
                                stringbuilder);
                    } catch (Exception exception) {
                        stringbuilder.append("[[ EXCEPTION ").append(exception).append(" ]]");
                    }
                }

                j++;
                if (!flag) {
                    break;
                }
            }
        }
    }

    private String method_43a137d54c8693fe4479(long i, int j) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Profiler Results ----\n");
        stringbuilder.append("// ");
        stringbuilder.append(method_c60693f457aa70be7160());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time span: ").append(i).append(" ms\n");
        stringbuilder.append("Tick span: ").append(j).append(" ticks\n");
        stringbuilder
                .append("// This is approximately ")
                .append(String.format("%.2f", j / ((float) i / 1000.0F)))
                .append(" ticks per second. It should be ")
                .append(20)
                .append(" ticks per second\n\n");
        stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.method_940ce73bc4f20092c86e(0, "root", stringbuilder);
        stringbuilder.append("--- END PROFILE DUMP ---\n\n");
        return stringbuilder.toString();
    }

    static {
        net.minecraft.command.CommandDebug.packedTextFragments = new String[3];
        net.minecraft.command.CommandDebug.packedTextFragments[0] = new String(" ");
        net.minecraft.command.CommandDebug.packedTextFragments[1] = new String(".");
        net.minecraft.command.CommandDebug.logger = LogManager.getLogger();
    }

    private static Exception method_bbe44e843daabb729dea(Exception exception) {
        return exception;
    }

    private void method_5bc9561ede986193a8dc(long i, int j) {
        File file1 =
                new File(
                        MinecraftServer.getServer().getFile("debug"),
                        "profile-results-"
                                + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date())
                                + ".txt");
        file1.getParentFile().mkdirs();

        try {
            FileWriter filewriter = new FileWriter(file1);
            filewriter.write(this.method_43a137d54c8693fe4479(i, j));
            filewriter.close();
        } catch (Throwable throwable) {
            net.minecraft.command.CommandDebug.logger.error("Could not save profiler results to " + file1, throwable);
        }
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, new String[] {"start", "stop"})
                : null;
    }

    private static String method_c60693f457aa70be7160() {
        String[] astring =
                new String[] {
                    "Shiny numbers!",
                    "Am I not running fast enough? :(",
                    "I'm working as hard as I can!",
                    "Will I ever be good enough for you? :(",
                    "Speedy. Zoooooom!",
                    "Hello world",
                    "40% better than a crash report.",
                    "Now with extra numbers",
                    "Now with less numbers",
                    "Now with the same numbers",
                    "You should add flames to things, it makes them go faster!",
                    "Do you feel the need for... optimization?",
                    "*cracks redstone whip*",
                    "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
                };

        try {
            return astring[(int) (System.nanoTime() % astring.length)];
        } catch (Throwable throwable) {
            return "Witty comment unavailable :(";
        }
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.debug.usage";
    }
}
