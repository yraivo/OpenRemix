package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.client.ClientServices;
import remix.client.RemixClient;

public final class PrefixClientCommand extends AbstractClientCommand {


    public String getUsage() {
        return "prefix <newPrefix>";
    }

    public PrefixClientCommand() {
        super("Prefix", new String[] {"prefix", "pref"});
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            String text = astring[1];
                            if (text.length() >= 2) {
                                ClientCommandManager.printMessage(
                                        "The prefix shouldn't be more than a single character, try again");
                                return false;
                            } else {
                                ((ClientServices) RemixClient.INSTANCE.getClientServices()).commandPrefix = text;
                                ClientCommandManager.printMessage("Prefix was set to " + text);
                                return true;
                            }
                        }));
    }
}
