package remix.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import remix.command.AbstractClientCommand;

import java.util.ArrayList;
import java.util.List;

public final class TargetsClientCommand extends AbstractClientCommand {
    private static String[] packedTextFragments;

    private static boolean standardTargetFlow;
    private static List<String> targetNames;

    public String getUsage() {
        return "targets <add/remove/list/clear> <name>";
    }

    public static boolean isTarget(Entity entity) {
        boolean standardFlow = isStandardTargetFlow();
        if (!(entity instanceof PlayerEntity) || remix.command.TargetsClientCommand.targetNames.isEmpty() || ec.player == entity) {
            return false;
        }
        for (String target : remix.command.TargetsClientCommand.targetNames) {
            if (target.equalsIgnoreCase(entity.getName())) {
                return true;
            }
            if (!standardFlow) {
                break;
            }
        }
        return false;
    }

    // region String decoding support

    // endregion

    public static boolean isStandardTargetFlow() {
        return remix.command.TargetsClientCommand.standardTargetFlow;
    }

    // region String decoding support

    // endregion

    public static boolean usesLegacyTargetFlow() {
        boolean flag = isStandardTargetFlow();
        return !flag;
    }

    public boolean execute(String[] arguments) {
        boolean legacyFlow = usesLegacyTargetFlow();
        if (arguments.length == 2) {
            String action = arguments[1];
            if (action.equalsIgnoreCase("clear")) {
                if (!remix.command.TargetsClientCommand.targetNames.isEmpty()) {
                    remix.command.TargetsClientCommand.targetNames.clear();
                    ClientCommandManager.printMessage("Successfully cleared the target list");
                    if (!legacyFlow) {
                        return true;
                    }
                }

                ClientCommandManager.printMessage("Target list was already empty");
                return true;
            }

            if (action.equalsIgnoreCase("list")) {
                if (!remix.command.TargetsClientCommand.targetNames.isEmpty()) {
                    StringBuilder displayedTargets = new StringBuilder();
                    int j = 0;
                    int k = remix.command.TargetsClientCommand.targetNames.size();

                    while (j < k) {
                        displayedTargets.append(remix.command.TargetsClientCommand.targetNames.get(j)).append(", ");
                        j++;
                        if (legacyFlow) {
                            break;
                        }
                    }

                    String names = displayedTargets.substring(0, displayedTargets.length() - 2);
                    boolean singleTarget = remix.command.TargetsClientCommand.targetNames.size() == 1;
                    if (!singleTarget) {
                        ClientCommandManager.printMessage(
                                String.format("There are currently %s targets (%s)", remix.command.TargetsClientCommand.targetNames.size(), names));
                    }
                    if (remix.command.TargetsClientCommand.targetNames.size() == 1 || legacyFlow) {
                        ClientCommandManager.printMessage(
                                String.format("Currently %s is only the only target (%s)", remix.command.TargetsClientCommand.targetNames.get(0), names));
                    }

                    if (!legacyFlow) {
                        return true;
                    }
                }

                ClientCommandManager.printMessage("There are currently no targets");
                return true;
            }

            if (!legacyFlow) {
                return false;
            }
        }

        if (arguments.length == 3) {
            String action = arguments[1];
            String playerName = arguments[2];
            if (action.equalsIgnoreCase("add")) {
                if (!remix.command.TargetsClientCommand.targetNames.contains(playerName)) {
                    remix.command.TargetsClientCommand.targetNames.add(playerName);
                    ClientCommandManager.printMessage(
                            "Successfully added \"" + playerName + "\" as target");
                    if (!legacyFlow) {
                        return true;
                    }
                }

                ClientCommandManager.printMessage(
                        packedTextFragments[0] + playerName + "\" is already in the target list!");
                return true;
            }

            if (action.equalsIgnoreCase("remove")) {
                int i = remix.command.TargetsClientCommand.targetNames.size() - 1;

                while (i >= 0) {
                    String candidate = remix.command.TargetsClientCommand.targetNames.get(i);
                    if (candidate.equalsIgnoreCase(playerName)) {
                        remix.command.TargetsClientCommand.targetNames.remove(i);
                        ClientCommandManager.printMessage(
                                "Successfully removed \"" + playerName + "\" from targets!");
                        return true;
                    }

                    i--;
                    if (legacyFlow) {
                        break;
                    }
                }

                ClientCommandManager.printMessage(
                        "No player found with the name \"" + playerName + "\"!");
                return true;
            }
        }

        return false;
    }

    // region String decoding support

    // endregion

    static {
        remix.command.TargetsClientCommand.packedTextFragments = new String[3];
        remix.command.TargetsClientCommand.packedTextFragments[0] = new String("\"");
        setStandardTargetFlow(true);
        remix.command.TargetsClientCommand.targetNames = new ArrayList<>();
    }

    public TargetsClientCommand() {
        super("Targets", new String[] {"targets"});
    }

    public static void setStandardTargetFlow(boolean flag) {
        remix.command.TargetsClientCommand.standardTargetFlow = flag;
    }
}
