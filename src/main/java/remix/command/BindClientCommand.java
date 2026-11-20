package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.ModulesJsonConfig;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleManager;

import org.lwjgl.input.Keyboard;

import java.util.List;

public final class BindClientCommand extends AbstractClientCommand {

    private static String[] packedTextFragments;

    public String getUsage() {
        return "bind <clear/module> <key>";
    }

    static {
        remix.command.BindClientCommand.packedTextFragments = new String[7];
        remix.command.BindClientCommand.packedTextFragments[0] = new String("b");
        remix.command.BindClientCommand.packedTextFragments[1] = new String("c");
        remix.command.BindClientCommand.packedTextFragments[2] = new String(")");
        remix.command.BindClientCommand.packedTextFragments[3] = new String(")");
        remix.command.BindClientCommand.packedTextFragments[4] = new String("\"");
    }

    public BindClientCommand() {
        super("Bind", new String[] {packedTextFragments[0], "bind", "setBind"});
        this.addNamedAction(
                "clear",
                new String[] {"clear", "cl", packedTextFragments[1], "empty"},
                astring -> {
                    boolean flag1 = TargetsClientCommand.usesLegacyTargetFlow();
                    List modules = ModuleManager.modules;
                    int i = 0;
                    int j = modules.size();
                    boolean flag = flag1;

                    while (i < j) {
                        ((ModuleBase) modules.get(i)).setKeyBind(0);
                        i++;
                        if (flag) {
                            break;
                        }
                    }

                    ClientCommandManager.printMessage("All binds cleared");
                    ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    ClientServices.configFileManager.saveConfig(ModulesJsonConfig.class);
                    return true;
                });
        this.addAction(
                CommandAction.requiringArguments(
                        3,
                        astring -> {
                            boolean flag1 = TargetsClientCommand.usesLegacyTargetFlow();
                            ClientServices clientServicesValue =
                                    (ClientServices) RemixClient.INSTANCE.getClientServices();
                            ModuleBase moduleBase =
                                    ClientServices.moduleManager.getModuleByName(
                                            astring[1]);
                            boolean flag = flag1;
                            if (moduleBase == null) {
                                ClientCommandManager.printMessage(
                                        "Couldn't find any module called \""
                                                + astring[1]
                                                + packedTextFragments[4]);
                                return true;
                            }

                            if (moduleBase.getCategory() == ModuleCategory.HIDDEN) {
                                ClientCommandManager.printMessage("I wouldn't do that");
                                if (!flag) {
                                    return true;
                                }
                            }

                            String text = astring[2].toUpperCase();
                            int i = Keyboard.getKeyIndex(text);
                            moduleBase.setKeyBind(i);
                            ClientCommandManager.printMessage(
                                    moduleBase.getDisplayName()
                                            + "'s bind was set to "
                                            + text
                                            + " (Code: "
                                            + i
                                            + packedTextFragments[2]);
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {
                                                "Binded!",
                                                moduleBase.getDisplayName()
                                                        + "'s bind was set to "
                                                        + text
                                                        + " (Code: "
                                                        + i
                                                        + packedTextFragments[3]
                                            },
                                            NotificationPriority.LOW,
                                            4000));
                            ClientServices clientServices =
                                    (ClientServices) RemixClient.INSTANCE.getClientServices();
                            ClientServices.configFileManager.saveConfig(
                                    ModulesJsonConfig.class);
                            return true;
                        }));
    }
}
