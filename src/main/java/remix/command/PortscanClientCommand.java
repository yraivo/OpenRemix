package remix.command;

import net.minecraft.client.multiplayer.ServerAddress;

import org.apache.commons.lang3.math.NumberUtils;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.util.network.PortScanner;

public final class PortscanClientCommand extends AbstractClientCommand {


    public boolean execute(String[] astring) {
        boolean flag = TargetsClientCommand.isStandardTargetFlow();
        if (astring.length != 5) {
            return false;
        }

        byte b0 = 1;
        int i = 2;

        while (i <= astring.length) {
            if (!NumberUtils.isNumber(astring[i - 1])) {
                b0 = 0;
            }

            i++;
            if (!flag) {
                break;
            }
        }

        if (!ec.getNetworkHandler().getNetworkManager().isLocalChannel()) {
            if (b0 == 0) {
                return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
            }

            ClientCommandManager.printMessage("Starting to scan ports, please be patient!");
            i = Integer.parseInt(astring[1]);
            int j = Integer.parseInt(astring[2]);
            int k = Integer.parseInt(astring[3]);
            int l = Integer.parseInt(astring[4]);
            final int capturedI171 = i;
            new Thread(
                            () -> {
                                ServerAddress serverAddress =
                                        ServerAddress.fromString(
                                                ec.getCurrentServerData().serverIP);
                                ClientServices clientServices =
                                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                                ClientServices.portScanner.scanPortRange(
                                        serverAddress.getIP(), capturedI171, j, k, l);
                                ClientCommandManager.printMessage(
                                        String.format(
                                                "Found %s open port(s) on %s. %s",
                                                PortScanner.openPorts.size(),
                                                serverAddress.getIP(),
                                                PortScanner.openPorts));
                            })
                    .start();
            if (flag) {
                return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
            }
        }

        ClientCommandManager.printMessage("Your in Singleplayer, this feature is for Multiplayer");
        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public PortscanClientCommand() {
        super("Portscan", new String[] {"portscan", "ps"});
    }

    public String getUsage() {
        return "portscan <start port> <end port> <timeout> <threads>";
    }
}
