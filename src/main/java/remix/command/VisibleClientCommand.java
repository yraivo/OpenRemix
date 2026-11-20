package remix.command;

import remix.command.AbstractClientCommand;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.ModulesJsonConfig;
import remix.module.ModuleBase;
import remix.module.ModuleManager;
import remix.module.ModuleMetadata;

import com.mojang.realmsclient.gui.ChatFormatting;

public final class VisibleClientCommand extends AbstractClientCommand {

    private final String visibleLabel = String.valueOf(ChatFormatting.BOLD) + ChatFormatting.GREEN + "Visible";

    private static String[] packedTextFragments;
    private final String invisibleLabel = String.valueOf(ChatFormatting.BOLD) + ChatFormatting.RED + "Invisible";

    public VisibleClientCommand() {
        super("visible", new String[] {"visible", packedTextFragments[0]});
    }

    public String getUsage() {
        return "visible <module/*>";
    }

    public boolean execute(String[] astring) {
        boolean flag = TargetsClientCommand.isStandardTargetFlow();
        if (astring.length < 2) {
            return false;
        }

        String text = astring[1];
        if (!text.equalsIgnoreCase(packedTextFragments[1])) {
            ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
            ModuleBase moduleBase =
                    ClientServices.moduleManager.getModuleByName(astring[1]);
            if (moduleBase != null) {
                moduleBase.setVisible(!moduleBase.isVisible());
                ClientCommandManager.printMessage(
                        moduleBase.getDisplayName()
                                + " was set to "
                                + (moduleBase.isEnabled() ? this.visibleLabel : this.invisibleLabel));
                clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                ClientServices.configFileManager.saveConfig(ModulesJsonConfig.class);
                return true;
            } else {
                ClientCommandManager.printMessage(
                        "Couldn't find any module called \"" + astring[1] + packedTextFragments[2]);
                return true;
            }
        } else {
            for (ModuleMetadata moduleMetadata : ModuleManager.modules) {
                if (!moduleMetadata.isVisible()) {
                    moduleMetadata.setVisible(true);
                }

                if (!flag) {
                    break;
                }
            }

            ClientCommandManager.printMessage("All modules are now visible");
            ClientServices clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
            ClientServices.configFileManager.saveConfig(ModulesJsonConfig.class);
            return true;
        }
    }

    static {
        remix.command.VisibleClientCommand.packedTextFragments = new String[5];
        remix.command.VisibleClientCommand.packedTextFragments[0] = new String("v");
        remix.command.VisibleClientCommand.packedTextFragments[1] = new String("*");
        remix.command.VisibleClientCommand.packedTextFragments[2] = new String("\"");
    }
}
