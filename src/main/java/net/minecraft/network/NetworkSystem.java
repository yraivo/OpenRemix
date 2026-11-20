package net.minecraft.network;

import com.google.common.collect.Lists;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.ReportedException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NetworkSystem {

    private MinecraftServer mcServer;
    private List networkManagers;
    public volatile boolean isAlive;
    public static LazyLoadBase SERVER_LOCAL_EVENTLOOP;

    private static Logger logger;

    private final List endpoints = Collections.synchronizedList(Lists.newArrayList());
    public static LazyLoadBase eventLoops;

    public static List access$000(NetworkSystem networkSystem) {
        return networkSystem.networkManagers;
    }

    public static MinecraftServer access$100(NetworkSystem networkSystem) {
        return networkSystem.mcServer;
    }

    public NetworkSystem(MinecraftServer bvSValue) {
        this.networkManagers = Collections.synchronizedList(Lists.newArrayList());
        this.mcServer = bvSValue;
        this.isAlive = true;
    }

    public void addLanEndpoint(InetAddress inetaddress, int i) throws IOException {
        List endpointsValue = this.endpoints;
        synchronized (this.endpoints) {
            this.endpoints.add(
                    ((ServerBootstrap)
                                    ((ServerBootstrap)
                                                    new ServerBootstrap()
                                                            .channel(NioServerSocketChannel.class))
                                            .childHandler(
                                                    new NetworkSystemAddLanEndpointChannelInitializer(
                                                            this))
                                            .group(
                                                    (EventLoopGroup)
                                                            net.minecraft.network.NetworkSystem
                                                                    .eventLoops
                                                                    .getValue())
                                            .localAddress(inetaddress, i))
                            .bind()
                            .syncUninterruptibly());
        }
    }

    public MinecraftServer getServer() {
        return this.mcServer;
    }

    public SocketAddress addLocalEndpoint() {
        List endpointsValue = this.endpoints;
        ChannelFuture channelfuture;
        synchronized (this.endpoints) {
            channelfuture =
                    ((ServerBootstrap)
                                    ((ServerBootstrap)
                                                    new ServerBootstrap()
                                                            .channel(LocalServerChannel.class))
                                            .childHandler(
                                                    new NetworkSystemAddLocalEndpointChannelInitializer(
                                                            this))
                                            .group(
                                                    (EventLoopGroup)
                                                            net.minecraft.network.NetworkSystem
                                                                    .eventLoops
                                                                    .getValue())
                                            .localAddress(LocalAddress.ANY))
                            .bind()
                            .syncUninterruptibly();
            this.endpoints.add(channelfuture);
        }

        return channelfuture.channel().localAddress();
    }

    public void terminateEndpoints() {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        this.isAlive = false;
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_3866 = (this.endpoints).iterator();
                (__recoveryIterator171_3866).hasNext(); ) {
            ChannelFuture channelfuture = (ChannelFuture) (__recoveryIterator171_3866).next();
            try {
                channelfuture.channel().close().sync();
            } catch (InterruptedException interruptedexception) {
                net.minecraft.network.NetworkSystem.logger.error(
                        "Interrupted whilst closing channel");
            }

            if (textValue == null) {
                break;
            }
        }
    }

    public void tickConnections() {
        boolean legacyProcessAllConnections =
                PacketBuffer.getLegacyPacketBufferFlowMarker() != null;
        synchronized (this.networkManagers) {
            // The archived monitor exits before the iterator is acquired.
        }

        Iterator iterator = this.networkManagers.iterator();
        while (iterator.hasNext()) {
            NetworkManager connection = (NetworkManager) iterator.next();
            if (!connection.hasNoChannel()) {
                boolean disconnected = !connection.isChannelOpen();
                if (disconnected) {
                    iterator.remove();
                    connection.checkDisconnected();
                }
                if (!disconnected || !legacyProcessAllConnections) {
                    try {
                        connection.processReceivedPackets();
                    } catch (Exception failure) {
                        if (connection.isLocalChannel()) {
                            CrashReport crash =
                                    CrashReport.makeCrashReport(
                                            failure, "Ticking memory connection");
                            CrashReportCategory category = crash.makeCategory("Ticking connection");
                            category.addCrashSectionCallable(
                                    "Connection",
                                    new NetworkSystemPacketCallable(this, connection));
                            throw new ReportedException(crash);
                        }

                        net.minecraft.network.NetworkSystem.logger.warn(
                                "Failed to handle packet for " + connection.getRemoteAddress(),
                                failure);
                        ChatComponentText message = new ChatComponentText("Internal server error");
                        connection.sendPacket(
                                new S40PacketDisconnect(message),
                                new DisconnectFutureListener(this, connection, message),
                                new GenericFutureListener[0]);
                        connection.disableAutoRead();
                    }
                }
            }

            if (!legacyProcessAllConnections) {
                break;
            }
        }
    }

    static {
        net.minecraft.network.NetworkSystem.logger = LogManager.getLogger();
        net.minecraft.network.NetworkSystem.eventLoops = new NetworkSystemEventLoopsLazyLoadBase();
        net.minecraft.network.NetworkSystem.SERVER_LOCAL_EVENTLOOP =
                new NetworkSystemServerLocalEventloopLazyLoadBase();
    }

    // Helper used only by this class.
    public static class DisconnectFutureListener
            implements io.netty.util.concurrent.GenericFutureListener {
        net.minecraft.network.NetworkSystem networkSystem;
        net.minecraft.network.NetworkManager networkManager;
        net.minecraft.util.ChatComponentText chatComponentText;

        public DisconnectFutureListener(
                net.minecraft.network.NetworkSystem bvWValue,
                net.minecraft.network.NetworkManager awSValue,
                net.minecraft.util.ChatComponentText bvXValue) {
            this.networkSystem = bvWValue;
            this.networkManager = awSValue;
            this.chatComponentText = bvXValue;
        }

        public void operationComplete(io.netty.util.concurrent.Future future) {
            this.networkManager.closeChannel(this.chatComponentText);
        }
    }
}
