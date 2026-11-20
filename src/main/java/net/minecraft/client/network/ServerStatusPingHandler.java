package net.minecraft.client.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.mojang.authlib.GameProfile;

import org.apache.commons.lang3.ArrayUtils;

public class ServerStatusPingHandler implements INetHandlerStatusClient {

    private static String[] packedTextFragments;
    private long Af;
    private boolean il;
    NetworkManager awa;

    OldServerPinger awb;
    ServerData awc;

    public void onDisconnect(ChatComponent chatComponent) {
        if (!this.il) {
            OldServerPinger.access$000()
                    .error(
                            "Can't ping "
                                    + this.awc.serverIP
                                    + ": "
                                    + chatComponent.getUnformattedText());
            this.awc.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
            this.awc.populationInfo = packedTextFragments[6];
            OldServerPinger.access$100(this.awb, this.awc);
        }
    }

    public void handlePong(S01PacketPong s01PacketPong) {
        long i = this.Af;
        long j = MinecraftClient.getSystemTime();
        this.awc.pingToServer = j - i;
        this.awa.closeChannel(new ChatComponentText("Finished"));
    }

    public void handleServerInfo(S00PacketServerInfo s00PacketServerInfo) {
        String[] astring;
        ServerStatusResponse serverStatusResponse;
        {
            String[] astring1 = ClientPlayHandler.getLegacyPlayHandlerFlowMarker();
            serverStatusResponse = s00PacketServerInfo.getResponse();
            astring = astring1;
            if (serverStatusResponse.getServerDescription() != null) {
                this.awc.serverMOTD = serverStatusResponse.getServerDescription().getFormattedText();

                
            } else {

                this.awc.serverMOTD = packedTextFragments[0];
            }
        }

        {
            if (serverStatusResponse.getProtocolVersionInfo() != null) {
                this.awc.gameVersion = serverStatusResponse.getProtocolVersionInfo().getName();
                this.awc.version = serverStatusResponse.getProtocolVersionInfo().getProtocol();

                
            } else {

                this.awc.gameVersion = "Old";
                this.awc.version = 0;
            }
        }

        {
            if (serverStatusResponse.getPlayerCountData() != null) {
                this.awc.populationInfo =
                        EnumChatFormatting.GRAY
                                + packedTextFragments[1]
                                + serverStatusResponse.getPlayerCountData().getOnlinePlayerCount()
                                + packedTextFragments[2]
                                + EnumChatFormatting.DARK_GRAY
                                + packedTextFragments[3]
                                + EnumChatFormatting.GRAY
                                + serverStatusResponse.getPlayerCountData().getMaxPlayers();
                if (!(!ArrayUtils.isNotEmpty(
                        serverStatusResponse.getPlayerCountData().getPlayers()))) {

                    StringBuilder stringbuilder = new StringBuilder();

                    for (GameProfile gameprofile :
                            serverStatusResponse.getPlayerCountData().getPlayers()) {
                        if (stringbuilder.length() > 0) {
                            stringbuilder.append(packedTextFragments[4]);
                        }

                        stringbuilder.append(gameprofile.getName());
                    }

                    if (serverStatusResponse.getPlayerCountData().getPlayers().length
                            < serverStatusResponse.getPlayerCountData().getOnlinePlayerCount()) {
                        if (stringbuilder.length() > 0) {
                            stringbuilder.append(packedTextFragments[5]);
                        }

                        stringbuilder
                                .append("... and ")
                                .append(
                                        serverStatusResponse.getPlayerCountData().getOnlinePlayerCount()
                                                - serverStatusResponse
                                                        .getPlayerCountData()
                                                        .getPlayers()
                                                        .length)
                                .append(" more ...");
                    }

                    this.awc.playerList = stringbuilder.toString();


                }
            } else {

                this.awc.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
            }
        }

        {
            if (serverStatusResponse.getFavicon() != null) {
                {
                    String text = serverStatusResponse.getFavicon();
                    if (text.startsWith("data:image/png;base64,")) {
                        this.awc.setBase64EncodedIconData(
                                text.substring("data:image/png;base64,".length()));

                        
                    } else {

                        OldServerPinger.access$000().error("Invalid server icon (unknown format)");
                    }
                }

                
            } else {

                this.awc.setBase64EncodedIconData(null);
            }
        }

        this.Af = MinecraftClient.getSystemTime();
        this.awa.sendPacket(new C01PacketPing(this.Af));
        this.il = true;
    }

    static {
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments = new String[9];
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[0] = new String("");
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[1] = new String("");
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[2] = new String("");
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[3] = new String("/");
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[4] = new String("\n");
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[5] = new String("\n");
        net.minecraft.client.network.ServerStatusPingHandler.packedTextFragments[6] = new String("");
    }

    public ServerStatusPingHandler(
            OldServerPinger awbValue, ServerData awcValue, NetworkManager awaValue) {
        this.awb = awbValue;
        this.awc = awcValue;
        this.awa = awaValue;
        this.il = false;

        this.Af = 0L;
    }
}
