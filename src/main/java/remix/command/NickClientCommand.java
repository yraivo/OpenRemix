package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public final class NickClientCommand extends AbstractClientCommand {


    public NickClientCommand() {
        super("nick", new String[] {"nick"});
        this.addAction(
                CommandAction.requiringArguments(
                        1,
                        astring -> {
                            try {
                                String text = ec.player.getNameClear();
                                StringSelection stringselection = new StringSelection(text);
                                Clipboard clipboard =
                                        Toolkit.getDefaultToolkit().getSystemClipboard();
                                clipboard.setContents(stringselection, null);
                                ClientCommandManager.printMessage(
                                        "Your player name \""
                                                + text
                                                + "\" was copied into your clipboard");
                                return true;
                            } catch (Exception exception) {
                                ClientCommandManager.printMessage("Something went wrong, sorry");
                                return false;
                            }
                        }));
    }

    public String getUsage() {
        return "nick";
    }
}
