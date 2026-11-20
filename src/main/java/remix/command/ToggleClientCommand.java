package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.ModulesJsonConfig;
import remix.module.ModuleBase;
import remix.module.ModuleManager;
import remix.module.render.ClickGuiModule;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.List;

public final class ToggleClientCommand extends AbstractClientCommand {

    private static String[] packedTextFragments;

    private String disabledLabel;
    private final String enabledLabel = "" + ChatFormatting.BOLD + ChatFormatting.GREEN + "On";

    static {
        remix.command.ToggleClientCommand.packedTextFragments = new String[5];
        remix.command.ToggleClientCommand.packedTextFragments[0] = new String("t");
        remix.command.ToggleClientCommand.packedTextFragments[1] = new String("*");
        remix.command.ToggleClientCommand.packedTextFragments[2] = new String("\"");
    }

    public ToggleClientCommand() {
        super("Toggle", new String[] {"toggle", packedTextFragments[0]});
        this.disabledLabel = "" + ChatFormatting.BOLD + ChatFormatting.RED + "Off";
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            boolean flag = TargetsClientCommand.isStandardTargetFlow();
                            if (astring[1].equalsIgnoreCase(packedTextFragments[1])) {
                                List modules = ModuleManager.modules;
                                int i = 0;
                                int j = modules.size();

                                while (i < j) {
                                    ModuleBase moduleBase = (ModuleBase) modules.get(i);
                                    if (moduleBase.isEnabled()) {
                                        moduleBase.toggle();
                                    }

                                    i++;
                                    if (!flag) {
                                        break;
                                    }
                                }

                                ClientCommandManager.printMessage("Disabled all modules!");
                                ClientServices clientServices =
                                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                                ClientServices.configFileManager.saveConfig(
                                        ModulesJsonConfig.class);
                                return true;
                            } else {
                                ClientServices clientServicesValue =
                                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                                ModuleBase moduleBaseValue =
                                        ClientServices.moduleManager.getModuleByName(
                                                astring[1]);
                                if (moduleBaseValue == null) {
                                    ClientCommandManager.printMessage(
                                            "Couldn't find any module called \""
                                                    + astring[1]
                                                    + packedTextFragments[2]);
                                    return false;
                                }

                                if (moduleBaseValue instanceof ClickGuiModule) {
                                    ClientCommandManager.printMessage(
                                            "Use a keybind as everyone else");
                                    if (flag) {
                                        return true;
                                    }
                                }

                                moduleBaseValue.toggle();
                                ClientCommandManager.printMessage(
                                        moduleBaseValue.getDisplayName()
                                                + " was set to "
                                                + (moduleBaseValue.isEnabled() ? this.enabledLabel : this.disabledLabel));
                                clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
                                ClientServices.configFileManager.saveConfig(
                                        ModulesJsonConfig.class);
                                return true;
                            }
                        }));
    }

    public String getUsage() {
        return "toggle <module/*>";
    }
}
