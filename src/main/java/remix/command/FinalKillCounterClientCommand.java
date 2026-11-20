package remix.command;

import net.minecraft.util.EnumChatFormatting;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.server.hypixel.MegaWallsScoreTracker;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.hidden.MegaWallsKillTrackerModule;

import java.util.stream.Collectors;

public class FinalKillCounterClientCommand extends AbstractClientCommand {
    private MegaWallsKillTrackerModule killTrackerModule;

    public FinalKillCounterClientCommand() {
        super("FinalKillCounter", new String[] {"fks"});
        RemixClient.INSTANCE.getClientServices();
        this.killTrackerModule = ClientServices.moduleManager.megaWallsKillTracker;
        this.addNamedAction(
                "players", new String[] {"players"},
                arguments -> {
                    MegaWallsScoreTracker tracker = this.killTrackerModule.megaWallsScoreTracker;
                    if (tracker == null) {
                        ClientCommandManager.printMessage("Something went wrong!");
                        return true;
                    }

                    String red = tracker.getTeamKillsMap(0).entrySet().stream()
                            .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                            .collect(Collectors.joining(", "));
                    String green = tracker.getTeamKillsMap(1).entrySet().stream()
                            .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                            .collect(Collectors.joining(", "));
                    String yellow = tracker.getTeamKillsMap(2).entrySet().stream()
                            .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                            .collect(Collectors.joining(", "));
                    String blue = tracker.getTeamKillsMap(3).entrySet().stream()
                            .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                            .collect(Collectors.joining(", "));
                    printTeamSummary(red, green, yellow, blue);
                    return true;
                });
        this.addAction(CommandAction.requiringArguments(0, arguments -> this.printTeamTotals()));
    }

    public String getUsage() {
        return "fks <players>";
    }

    private boolean printTeamTotals() {
        MegaWallsScoreTracker tracker = this.killTrackerModule.megaWallsScoreTracker;
        if (tracker == null) {
            ClientCommandManager.printMessage("Something went wrong!");
            return true;
        }

        printTeamSummary(tracker.getTeamTotalKills(0), tracker.getTeamTotalKills(1),
                tracker.getTeamTotalKills(2), tracker.getTeamTotalKills(3));
        return true;
    }

    private static void printTeamSummary(Object red, Object green, Object yellow, Object blue) {
        ClientCommandManager.printMessage(
                "\n" + EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": " + red
                        + "\n" + EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": " + green
                        + "\n" + EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": " + yellow
                        + "\n" + EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": " + blue);
    }
}
