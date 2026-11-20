package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.module.exploit.SpammerModule;

public final class SpamClientCommand extends AbstractClientCommand {
    private static String[] packedTextFragments;

    public SpamClientCommand() {
        super("Spam", new String[] {"spam"});
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            String messageTemplate = String.join(packedTextFragments[0], astring);
                            messageTemplate =
                                    messageTemplate.replaceFirst("spam ", packedTextFragments[1]);
                            SpammerModule.messageTemplate = messageTemplate;
                            ClientCommandManager.printMessage(
                                    "Successfully set the spam message to '"
                                            + messageTemplate
                                            + "'!");
                            return true;
                        }));
    }

    public String getUsage() {
        return "spam <message>";
    }

    static {
        remix.command.SpamClientCommand.packedTextFragments = new String[4];
        remix.command.SpamClientCommand.packedTextFragments[0] = new String(" ");
        remix.command.SpamClientCommand.packedTextFragments[1] = new String("");
    }
}
