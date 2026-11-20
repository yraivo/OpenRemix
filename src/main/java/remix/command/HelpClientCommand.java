package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.ClientCommand;
import remix.command.CommandAction;
import remix.client.ClientServices;
import remix.client.RemixClient;

import com.mojang.realmsclient.gui.ChatFormatting;

public final class HelpClientCommand extends AbstractClientCommand {
    private static final String HELP_BORDER = "-------------------";

    public HelpClientCommand() {
        super("Help", new String[] {"help", "support", "?"});
        this.addAction(CommandAction.requiringArguments(1, arguments -> printHelp(arguments)));
    }

    public String getUsage() {
        return "help <command>";
    }

    private static boolean printHelp(String[] arguments) {
        boolean legacyFlow = TargetsClientCommand.usesLegacyTargetFlow();
        RemixClient.INSTANCE.getClientServices();
        ClientCommandRegistry registry = ClientServices.commands.getRegistry();
        if (arguments.length > 1) {
            boolean found = printCommandHelp(registry, arguments[1], legacyFlow);
            if (!found || legacyFlow) {
                ClientCommandManager.printMessage("Command not found, sorry");
            }
            if (!legacyFlow) {
                return true;
            }
        }

        printHelpLine(HELP_BORDER);
        printHelpLine("|          Help          |");
        printHelpLine(HELP_BORDER);
        for (Object entry : registry.getCommands().asList()) {
            ClientCommand command = (ClientCommand) entry;
            ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
            ClientCommandManager.printMessage(services.commandPrefix + command.getName());
            if (legacyFlow) {
                break;
            }
        }
        printHelpLine(HELP_BORDER);
        return true;
    }

    private static boolean printCommandHelp(ClientCommandRegistry registry, String alias, boolean legacyFlow) {
        ClientCommand command = registry.findByAlias(alias);
        if (command == null) {
            return false;
        }

        printHelpLine(command.getName());
        ClientCommandManager.printMessage(
                "" + ChatFormatting.GRAY + ChatFormatting.BOLD + "Usage: "
                        + ChatFormatting.WHITE + command.getUsage());
        String[] aliases = registry.getAliasesFor(alias);
        if (aliases != null) {
            StringBuilder line = new StringBuilder(
                    "" + ChatFormatting.GRAY + ChatFormatting.BOLD + "Aliases: " + ChatFormatting.WHITE);
            for (int i = 0; i < aliases.length; i++) {
                line.append(aliases[i]);
                if (i < aliases.length - 1) {
                    line.append(", ");
                }
                if (legacyFlow) {
                    break;
                }
            }
            ClientCommandManager.printMessage(line.toString());
        }
        return true;
    }

    private static void printHelpLine(String text) {
        ClientCommandManager.printMessage("" + ChatFormatting.GREEN + ChatFormatting.BOLD + text);
    }
}
