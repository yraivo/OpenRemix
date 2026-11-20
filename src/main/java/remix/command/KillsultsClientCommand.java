package remix.command;

import remix.command.AbstractClientCommand;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.KillsultsTextConfig;
import remix.module.player.KillSultsModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class KillsultsClientCommand extends AbstractClientCommand {
    private static String[] packedTextFragments;

    public String getUsage() {
        return ".killsults <add/load/clear>";
    }

    public KillsultsClientCommand() {
        super("killsults", new String[] {"killsults", "kst"});
        this.addNamedAction("clear", new String[] {"clear", "empty"}, arguments -> clearMessages());
        this.addMinimumArgumentsAction("add", 3, new String[] {"add", "a", "create"},
                arguments -> addMessage(arguments));
        this.addMinimumArgumentsAction("load", 3, new String[] {"load", "l"},
                arguments -> loadMessages(arguments));
    }

    private static boolean clearMessages() {
        KillSultsModule.remainingMessages.clear();
        KillSultsModule.messageTemplates.clear();
        ClientCommandManager.printMessage("Killsults list cleaned");
        saveMessages();
        return true;
    }

    private static boolean addMessage(String[] arguments) {
        String text = collectArgumentText(arguments, !TargetsClientCommand.isStandardTargetFlow());
        appendMessage(text);
        ClientCommandManager.printMessage("Successfully added \"" + text + "\" to the killsult list");
        saveMessages();
        return true;
    }

    private static boolean loadMessages(String[] arguments) {
        boolean legacyImportFlow = TargetsClientCommand.usesLegacyTargetFlow();
        File file = new File(collectArgumentText(arguments, legacyImportFlow));
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appendMessage(line);
                if (legacyImportFlow) {
                    break;
                }
            }
            saveMessages();
            return true;
        } catch (IOException failure) {
            ClientCommandManager.printMessage("Error while parsing the killsult file!");
            return false;
        }
    }

    private static String collectArgumentText(String[] arguments, boolean firstWordOnly) {
        StringBuilder value = new StringBuilder();
        for (int index = 2; index < arguments.length; index++) {
            value.append(arguments[index]);
            if (index + 1 != arguments.length) {
                value.append(' ');
            }
            if (firstWordOnly) {
                break;
            }
        }
        return value.toString();
    }

    private static void appendMessage(String text) {
        KillSultsModule.remainingMessages.add(text);
        KillSultsModule.messageTemplates.add(text);
    }

    private static void saveMessages() {
        RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfig(KillsultsTextConfig.class);
    }

    static {
        remix.command.KillsultsClientCommand.packedTextFragments = new String[6];
        remix.command.KillsultsClientCommand.packedTextFragments[0] = new String("a");
        remix.command.KillsultsClientCommand.packedTextFragments[1] = new String("l");
        remix.command.KillsultsClientCommand.packedTextFragments[2] = new String(" ");
        remix.command.KillsultsClientCommand.packedTextFragments[3] = new String(" ");
    }
}
