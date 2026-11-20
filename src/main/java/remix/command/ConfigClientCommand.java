package remix.command;

import remix.command.AbstractClientCommand;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.CustomProfileConfigHelper;
import remix.config.ModulesJsonConfig;
import remix.module.world.AutoConfigModule;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class ConfigClientCommand extends AbstractClientCommand {

    private static String[] packedTextFragments;

    public ConfigClientCommand() {
        super("Config", new String[] {"config", "cf", "cfg"});
        this.addNamedAction(
                "list",
                new String[] {"list", "lt", "show"},
                astring -> {
                    ArrayList arrayList = new ArrayList();
                    boolean flag1 = TargetsClientCommand.usesLegacyTargetFlow();
                    File file1 = CustomProfileConfigHelper.profileDirectory;
                    boolean flag = flag1;
                    if (!file1.exists()) {
                        String absolutePath = file1.getAbsolutePath();
                        if (!absolutePath.contains(".minecraft")) {
                            ClientCommandManager.printMessage(
                                    "Wait, why your config folder is not inside .minecraft?");
                            ClientCommandManager.printMessage("It appears to be: " + absolutePath);
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {
                                                "Weird!",
                                                "Your game directory shouldn't be there..."
                                            },
                                            NotificationPriority.NORMAL,
                                            4000));
                            return true;
                        } else {
                            ClientCommandManager.printMessage(
                                    ".minecraft/Remix/Config doesn't exist...");
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {
                                                "OOF!", "Save a config for it to generate"
                                            },
                                            NotificationPriority.NORMAL,
                                            4000));
                            return true;
                        }
                    } else {
                        File[] afile = file1.listFiles();
                        if (afile != null && afile.length != 0) {
                            for (File file2 : afile) {
                                String text = new File(file2.getName()).getName();
                                int i = text.lastIndexOf(46);
                                String textValue =
                                        i == -1 ? packedTextFragments[1] : text.substring(i + 1);
                                if (textValue.equalsIgnoreCase("json")) {
                                    String textValue2 = text.substring(0, i);
                                    arrayList.add(textValue2);
                                }

                                if (flag) {
                                    break;
                                }
                            }

                            ClientCommandManager.printMessage(
                                    "You have the following configs: \n "
                                            + arrayList
                                                    .toString()
                                                    .replace(
                                                            packedTextFragments[2],
                                                            packedTextFragments[3])
                                                    .replace(
                                                            packedTextFragments[4],
                                                            packedTextFragments[5]));
                            return true;
                        } else {
                            ClientCommandManager.printMessage("You have no configs saved!");
                            return true;
                        }
                    }
                });
        this.addMinimumArgumentsAction(
                "save",
                3,
                new String[] {"save", "sv", "create"},
                astring -> {
                    String text = astring[2];

                    try {
                        CustomProfileConfigHelper.saveProfile(text);
                        ClientCommandManager.printMessage(
                                "Successfully saved a config called '"
                                        + ChatFormatting.GREEN
                                        + text
                                        + ChatFormatting.RESET
                                        + "'!");
                        NotificationManager.post(
                                new NotificationManager(
                                        new String[] {
                                            "Saved!", "Successfully created " + text + ".json!"
                                        },
                                        NotificationPriority.LOW,
                                        4000));
                        return true;
                    } catch (IOException ioexception) {
                        ClientCommandManager.printMessage(
                                "Failed at saving a config called '"
                                        + ChatFormatting.RED
                                        + text
                                        + ChatFormatting.RESET
                                        + "'!");
                        NotificationManager.post(
                                new NotificationManager(
                                        new String[] {
                                            "Failed!", "Couldn't save " + text + ".json!"
                                        },
                                        NotificationPriority.HIGH,
                                        4000));
                        return false;
                    }
                });
        this.addMinimumArgumentsAction(
                "load",
                3,
                new String[] {"load", "ld", "inject"},
                astring -> {
                    ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    AutoConfigModule autoConfig = ClientServices.moduleManager.autoConfig;
                    String text = astring[2];

                    try {
                        if (CustomProfileConfigHelper.loadProfile(text)) {
                            ClientCommandManager.printMessage(
                                    "Successfully loaded a config called '"
                                            + ChatFormatting.GREEN
                                            + text
                                            + ChatFormatting.RESET
                                            + "'!");
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {
                                                "Loaded!", text + ".json config has been loaded!"
                                            },
                                            NotificationPriority.LOW,
                                            4000));
                            clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                            ClientServices.configFileManager.saveConfig(
                                    ModulesJsonConfig.class);
                            autoConfig.loadedConfigName = text;
                            return true;
                        } else {
                            ClientCommandManager.printMessage(
                                    "There is no config called '"
                                            + ChatFormatting.RED
                                            + text
                                            + ChatFormatting.RESET
                                            + "'!");
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {
                                                "Missing!",
                                                "Are you sure there is a config called "
                                                        + text
                                                        + packedTextFragments[0]
                                            },
                                            NotificationPriority.NORMAL,
                                            4000));
                            return false;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        ClientCommandManager.printMessage(
                                "Failed at loading a config called '"
                                        + ChatFormatting.RED
                                        + text
                                        + ChatFormatting.RESET
                                        + "'!");
                        NotificationManager.post(
                                new NotificationManager(
                                        new String[] {
                                            "Error!",
                                            "Something went wrong while loading the config"
                                        },
                                        NotificationPriority.HIGH,
                                        4000));
                        return false;
                    }
                });
    }

    public String getUsage() {
        return "config <save/load/list> <config name>";
    }

    static {
        remix.command.ConfigClientCommand.packedTextFragments = new String[7];
        remix.command.ConfigClientCommand.packedTextFragments[0] = new String("?");
        remix.command.ConfigClientCommand.packedTextFragments[1] = new String("");
        remix.command.ConfigClientCommand.packedTextFragments[2] = new String("[");
        remix.command.ConfigClientCommand.packedTextFragments[3] = new String("");
        remix.command.ConfigClientCommand.packedTextFragments[4] = new String("]");
        remix.command.ConfigClientCommand.packedTextFragments[5] = new String("");
    }
}
