package net.minecraft.client.network;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.network.LegacyServerPingChannelInitializer;
import net.minecraft.client.network.ServerStatusPingHandler;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class OldServerPinger {
    private final List pingDestinations = Collections.synchronizedList(Lists.newArrayList());

    private static Splitter PING_RESPONSE_SPLITTER;
    private static Logger logger;

    public static void access$100(OldServerPinger oldServerPinger, ServerData serverData) {
        oldServerPinger.tryCompatibilityPing(serverData);
    }

    public static Splitter access$200() {
        return net.minecraft.client.network.OldServerPinger.PING_RESPONSE_SPLITTER;
    }

    public void ping(ServerData serverData) throws UnknownHostException {
        ServerAddress serverAddress = ServerAddress.fromString(serverData.serverIP);
        NetworkManager networkManager =
                NetworkManager.createNetworkManagerAndConnect(
                        InetAddress.getByName(serverAddress.getIP()),
                        serverAddress.getPort(),
                        false);
        this.pingDestinations.add(networkManager);
        serverData.serverMOTD = "Pinging...";

        serverData.pingToServer = -1L;
        serverData.playerList = null;
        networkManager.setPacketHandler(
                new ServerStatusPingHandler(this, serverData, networkManager));

        try {
            networkManager.sendPacket(
                    new C00Handshake(
                            47,
                            serverAddress.getIP(),
                            serverAddress.getPort(),
                            ConnectionState.STATUS));
            networkManager.sendPacket(new C00PacketServerQuery());
        } catch (Throwable throwable) {
            net.minecraft.client.network.OldServerPinger.logger.error(throwable);
        }
    }

    public void clearPendingNetworks() {
        List pingDestinationsValue = this.pingDestinations;
        ClientPlayHandler.getLegacyPlayHandlerFlowMarker();

        List listValue = this.pingDestinations;
        synchronized (this.pingDestinations) {
        }

        try {
            Iterator iterator = this.pingDestinations.iterator();

            while (iterator.hasNext()) {
                NetworkManager networkManager = (NetworkManager) iterator.next();
                if (networkManager.isChannelOpen()) {
                    iterator.remove();
                    networkManager.closeChannel(new ChatComponentText("Cancelled"));
                }
            }

        } finally {
        }
    }

    private void tryCompatibilityPing(ServerData serverData) {
        ServerAddress serverAddress = ServerAddress.fromString(serverData.serverIP);
        ((Bootstrap)
                        ((Bootstrap)
                                        ((Bootstrap)
                                                        new Bootstrap()
                                                                .group(
                                                                        (EventLoopGroup)
                                                                                NetworkManager
                                                                                        .CLIENT_NIO_EVENTLOOP
                                                                                        .getValue()))
                                                .handler(
                                                        new LegacyServerPingChannelInitializer(
                                                                this, serverAddress, serverData)))
                                .channel(NioSocketChannel.class))
                .connect(serverAddress.getIP(), serverAddress.getPort());
    }

    static {
        net.minecraft.client.network.OldServerPinger.PING_RESPONSE_SPLITTER = Splitter.on('\u0000').limit(6);
        net.minecraft.client.network.OldServerPinger.logger = LogManager.getLogger();
    }

    public void pingPendingNetworks() {
        ClientPlayHandler.getLegacyPlayHandlerFlowMarker();

        List pingDestinationsValue = this.pingDestinations;
        synchronized (this.pingDestinations) {
            Iterator iterator = this.pingDestinations.iterator();

            while (iterator.hasNext()) {
                {
                    NetworkManager networkManager = (NetworkManager) iterator.next();
                    if (networkManager.isChannelOpen()) {
                        networkManager.processReceivedPackets();

                        
                    } else {

                        iterator.remove();
                        networkManager.checkDisconnected();
                    }
                }
            }
        }
    }

    public static Logger access$000() {
        return net.minecraft.client.network.OldServerPinger.logger;
    }
}
