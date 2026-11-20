package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.HudModule;

import com.google.common.base.Joiner;

import java.util.Arrays;

public final class ClientNameClientCommand extends AbstractClientCommand {

    private static String[] packedTextFragments;

    public String getUsage() {
        return "clientname <name>";
    }

    public ClientNameClientCommand() {
        super("ClientName", new String[] {"clientname", "name"});
        this.addAction(
                CommandAction.requiringArguments(
                        1,
                        astring -> {
                            boolean flag1 = TargetsClientCommand.isStandardTargetFlow();
                            int length = astring.length;
                            boolean flag = flag1;
                            if (length > 1) {
                                ClientServices clientServices =
                                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                                HudModule hudModule = ClientServices.moduleManager.hud;
                                hudModule.watermarkRenderer.previousWatermarkText = hudModule.watermarkRenderer.watermarkText;
                                hudModule.watermarkRenderer.watermarkText =
                                        Joiner.on(packedTextFragments[1])
                                                .join(Arrays.copyOfRange(astring, 1, length))
                                                .replaceAll(
                                                        packedTextFragments[2],
                                                        packedTextFragments[3]);
                                NotificationManager.post(
                                        new NotificationManager(
                                                new String[] {
                                                    "Changed!", "Client name has been changed"
                                                },
                                                NotificationPriority.LOW,
                                                4000));
                                ClientServices clientServicesValue =
                                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                                ClientServices.configFileManager.saveConfigs();
                                if (flag) {
                                    return true;
                                }
                            }

                            ClientCommandManager.printMessage(
                                    "Client name too short, try with another one");
                            return true;
                        }));
        this.addNamedAction(
                "reset",
                new String[] {"reset", packedTextFragments[0]},
                astring -> {
                    ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    HudModule hudModule = ClientServices.moduleManager.hud;
                    String text = hudModule.watermarkRenderer.previousWatermarkText;
                    hudModule.watermarkRenderer.previousWatermarkText = hudModule.watermarkRenderer.watermarkText;
                    hudModule.watermarkRenderer.watermarkText = text;
                    NotificationManager.post(
                            new NotificationManager(
                                    new String[] {"Back in time!", "Client name has been reset"},
                                    NotificationPriority.LOW,
                                    4000));
                    clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    ClientServices.configFileManager.saveConfigs();
                    return true;
                });
    }

    static {
        remix.command.ClientNameClientCommand.packedTextFragments = new String[6];
        remix.command.ClientNameClientCommand.packedTextFragments[0] = new String("r");
        remix.command.ClientNameClientCommand.packedTextFragments[1] = new String(" ");
        remix.command.ClientNameClientCommand.packedTextFragments[2] = new String("&");
        remix.command.ClientNameClientCommand.packedTextFragments[3] = new String("\u00a7");
    }
}
