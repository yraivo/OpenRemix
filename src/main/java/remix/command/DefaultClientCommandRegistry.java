package remix.command;

import net.minecraft.entity.Entity;
import remix.command.ClientCommand;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

public class DefaultClientCommandRegistry implements ClientCommandRegistry {
    protected final FluentListWrapper commands = FluentCollections.ax();
    private static boolean scanAllCommandAliases;

    public ClientCommand findByAlias(String alias) {
        boolean scanAllCommands = isEnabled();
        for (Object entry : this.getCommands().asList()) {
            ClientCommand command = (ClientCommand) entry;
            for (String candidate : command.getAliases()) {
                if (candidate.equalsIgnoreCase(alias)) {
                    return command;
                }
                if (!scanAllCommands) {
                    break;
                }
            }
            if (!scanAllCommands) {
                break;
            }
        }
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setEnabled(!scanAllCommands);
        }
        return null;
    }

    public static void setEnabled(boolean flag) {
        remix.command.DefaultClientCommandRegistry.scanAllCommandAliases = flag;
    }

    public String[] getAliasesFor(String alias) {
        boolean firstAliasOnly = isDisabled();
        for (Object entry : this.getCommands().asList()) {
            ClientCommand command = (ClientCommand) entry;
            for (String candidate : command.getAliases()) {
                if (candidate.equalsIgnoreCase(alias)) {
                    return command.getAliases();
                }
                if (firstAliasOnly) {
                    Entity.setLegacyGlobalFlowMarker(new String[4]);
                    break;
                }
            }
            if (firstAliasOnly) {
                return null;
            }
        }
        return null;
    }

    public FluentListWrapper getCommands() {
        return this.commands;
    }

    static {
        if (isDisabled()) {
            setEnabled(true);
        }
    }

    public static boolean isEnabled() {
        return remix.command.DefaultClientCommandRegistry.scanAllCommandAliases;
    }

    public static boolean isDisabled() {
        boolean flag = isEnabled();
        return !flag;
    }
}
