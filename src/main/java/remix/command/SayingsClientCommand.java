package remix.command;

import remix.command.AbstractClientCommand;
import remix.event.InputType;
import remix.input.SayingKeyAction;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.BindsTextConfig;
import remix.input.KeyAction;
import remix.input.KeyBindingDispatcher;

import com.mojang.realmsclient.gui.ChatFormatting;

import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.IntSupplier;

public final class SayingsClientCommand extends AbstractClientCommand {
    public SayingsClientCommand() {
        super("Sayings", new String[] {"sayings", "saymsg"});
        this.addMinimumArgumentsAction(
                "add", 2, new String[] {"add", "plus", "bind"},
                arguments -> {
                    if (arguments.length <= 2) {
                        return false;
                    }
                    if (arguments.length == 3) {
                        ClientCommandManager.printMessage("You forgot to add the message to say!");
                        return false;
                    }

                    try {
                        int keyCode = Keyboard.getKeyIndex(arguments[2].toUpperCase());
                        String message = String.join(" ", Arrays.copyOfRange(arguments, 3, arguments.length));
                        KeyBindingDispatcher.bindings.put(
                                () -> keyCode, Collections.singletonList(new SayingKeyAction(message, InputType.KEY_PRESS)));
                        ClientCommandManager.printMessage("Message binded successfully!");
                        saveBindings();
                    } catch (Exception exception) {
                        ClientCommandManager.printMessage("Error, something went wrong...");
                    }
                    return true;
                });
        this.addMinimumArgumentsAction(
                "remove", 1, new String[] {"remove", "minus", "unbind"},
                arguments -> {
                    boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
                    if (arguments.length != 3) {
                        return false;
                    }

                    String keyName = arguments[2].toUpperCase();
                    int keyCode = Keyboard.getKeyIndex(keyName);
                    try {
                        Iterator<Entry<IntSupplier, List<KeyAction>>> entries =
                                KeyBindingDispatcher.bindings.entrySet().iterator();
                        while (entries.hasNext()) {
                            Entry<IntSupplier, List<KeyAction>> entry = entries.next();
                            if (entry.getKey().getAsInt() == keyCode) {
                                List<KeyAction> actions = entry.getValue();
                                boolean removedEntireBinding = actions.size() == 1 && actions.get(0) instanceof SayingKeyAction;
                                if (removedEntireBinding) {
                                    KeyAction action = actions.get(0);
                                    System.out.println("REMOVING: " + action.getBoundKeyTask().getDisplayName()
                                            + " " + action.getBoundKeyTask().getTarget());
                                    entries.remove();
                                }
                                if (!removedEntireBinding || !standardFlow) {
                                    try {
                                        actions.removeIf(action -> action instanceof SayingKeyAction);
                                    } catch (UnsupportedOperationException ignored) {
                                        // Singleton and other immutable lists cannot be edited in place.
                                    }
                                }
                            }
                            if (!standardFlow) {
                                break;
                            }
                        }

                        ClientCommandManager.printMessage("All messages (if any) binded to " + keyName + " were removed.");
                        saveBindings();
                    } catch (Exception exception) {
                        ClientCommandManager.printMessage("Error, something went wrong...");
                        exception.printStackTrace();
                    }
                    return true;
                });
        this.addNamedAction("list", new String[] {"list", "lt", "show"}, arguments -> printBindings());
    }

    public String getUsage() {
        return "saymsg add/remove/list <key> <message>";
    }

    private static void saveBindings() {
        RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfig(BindsTextConfig.class);
    }

    private static boolean printBindings() {
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        ClientCommandManager.printMessage("" + ChatFormatting.GREEN + ChatFormatting.BOLD + "Binded messages");
        printBorder();

        boolean printedAny = false;
        for (Entry<IntSupplier, List<KeyAction>> entry : KeyBindingDispatcher.bindings.entrySet()) {
            int keyCode = entry.getKey().getAsInt();
            if (keyCode != 0) {
                for (KeyAction action : entry.getValue()) {
                    if (action instanceof SayingKeyAction) {
                        String message = (String) action.getBoundKeyTask().getTarget();
                        ClientCommandManager.printMessage(Keyboard.getKeyName(keyCode) + " : \"" + message + "\"");
                        printedAny = true;
                    }
                    if (!standardFlow) {
                        break;
                    }
                }
            }
            if (!standardFlow) {
                break;
            }
        }

        if (!printedAny) {
            ClientCommandManager.printMessage("Seems like you don't have any at the moment");
        }
        printBorder();
        return true;
    }

    private static void printBorder() {
        ClientCommandManager.printMessage("" + ChatFormatting.GREEN + ChatFormatting.BOLD + "-------------------");
    }
}
