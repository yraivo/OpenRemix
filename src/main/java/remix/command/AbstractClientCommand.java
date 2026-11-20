package remix.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public abstract class AbstractClientCommand implements ClientCommand {
    private Set<CommandAction> actions = Collections.emptySet();
    private String name;
    private static int legacyCommandFlowMarker;
    private String[] aliases;

    public AbstractClientCommand(String text, String[] astring) {
        this.name = text;
        this.aliases = astring;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public static int getLegacyCommandFlowMarker() {
        return remix.command.AbstractClientCommand.legacyCommandFlowMarker;
    }

    static {
        if (getLegacyCommandFlowMarker() != 0) {
            setLegacyCommandFlowMarker(10);
        }
    }

    public void addMinimumArgumentsAction(
            String text, int i, String[] astring, Function<String[], Boolean> function) {
        this.addAction(new MinimumArgumentsCommandAction(text, i, astring, function));
    }

    public String getName() {
        return this.name;
    }

    public Set<CommandAction> getActions() {
        return this.actions;
    }

    public static int getStandardCommandFlowToken() {
        int i = getLegacyCommandFlowMarker();
        return i == 0 ? 32 : 0;
    }

    public static void setLegacyCommandFlowMarker(int i) {
        remix.command.AbstractClientCommand.legacyCommandFlowMarker = i;
    }

    public void addNamedAction(
            String text, String[] astring, Function<String[], Boolean> function) {
        this.addAction(new CallbackCommandAction(text, astring, function));
    }

    public boolean execute(String[] arguments) {
        boolean standardFlow = getStandardCommandFlowToken() != 0;
        if (arguments.length >= 2) {
            String requestedAlias = arguments[1];
            for (CommandAction action : this.actions) {
                for (String alias : action.getAliases()) {
                    if (alias.equalsIgnoreCase(requestedAlias)) {
                        return action.execute(arguments);
                    }
                    if (!standardFlow) {
                        break;
                    }
                }
                if (!standardFlow) {
                    break;
                }
            }
        }

        if (standardFlow) {
            for (CommandAction action : this.actions) {
                if (action instanceof MinimumArgumentsCommandAction
                        && ((MinimumArgumentsCommandAction) action).getMinimumArguments() <= arguments.length) {
                    return action.execute(arguments);
                }
            }
        }
        return false;
    }

    public void addAction(CommandAction commandAction) {
        if (this.actions.isEmpty()) {
            this.actions = new HashSet<>();
        }

        this.actions.add(commandAction);
    }
}
