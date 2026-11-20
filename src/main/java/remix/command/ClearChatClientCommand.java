package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;

public final class ClearChatClientCommand extends AbstractClientCommand {


    public String getUsage() {
        return "clearchat";
    }

    public ClearChatClientCommand() {
        super("ClearChat", new String[] {"cc", "clearchat", "clear"});
        this.addAction(
                CommandAction.requiringArguments(
                        1,
                        astring -> {
                            ec.ingameGUI.getChatGUI().clearChatMessages();
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {"Be gone chat!", "Now it's empty"},
                                            NotificationPriority.LOW,
                                            4000));
                            return true;
                        }));
    }
}
