package net.minecraft.network;

import com.google.common.collect.Queues;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.LazyLoadBase;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.OioProxyChannelFactory;
import remix.util.network.ProxyType;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.util.Queue;

import javax.crypto.SecretKey;

public class NetworkManager extends SimpleChannelInboundHandler {
    public static LazyLoadBase CLIENT_NIO_EVENTLOOP;
    public static Marker logMarkerPackets;
    private boolean disconnected;
    private EnumPacketDirection direction;
    public static Marker logMarkerNetwork;
    private volatile Channel channel;
    private static String[] packedTextFragments;
    public static LazyLoadBase CLIENT_EPOLL_EVENTLOOP;
    private PacketHandler packetHandler;
    private final Queue<NetworkManagerInboundHandlerTuplePacketListener> outboundQueue =
            Queues.newConcurrentLinkedQueue();

    private SocketAddress remoteAddress;
    public static AttributeKey attrKeyConnectionState;
    private static Logger logger;

    private ChatComponent terminationReason;
    private boolean isEncrypted;
    public static LazyLoadBase CLIENT_LOCAL_EVENTLOOP;

    public void setPacketHandler(PacketHandler packetHandlerValue) {
        Validate.notNull(packetHandlerValue, "packetListener", new Object[0]);
        net.minecraft.network.NetworkManager.logger.debug(
                "Set listener of {} to {}", new Object[] {this, packetHandlerValue});
        this.packetHandler = packetHandlerValue;
    }

    public void checkDisconnected() {
        if (this.hasNoChannel() || this.isChannelOpen() || this.disconnected) {
            return;
        }
        this.disconnected = true;
        PacketHandler handler = this.getPacketHandler();
        if (handler != null) {
            ChatComponent reason = this.getExitMessage();
            handler.onDisconnect(reason == null ? new ChatComponentText("Disconnected") : reason);
        }
    }

    public boolean isLocalChannel() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }

    public void enableEncryption(SecretKey secretkey) {
        this.isEncrypted = true;
        this.channel
                .pipeline()
                .addBefore(
                        "splitter",
                        "decrypt",
                        new NettyEncryptingDecoder(
                                CryptManager.createNetCipherInstance(2, secretkey)));
        this.channel
                .pipeline()
                .addBefore(
                        "prepender",
                        "encrypt",
                        new NettyEncryptingEncoder(
                                CryptManager.createNetCipherInstance(1, secretkey)));
    }

    public void processReceivedPackets() {
        Channel activeChannel = this.channel;
        if (activeChannel == null) {
            // The connect future can complete before Netty fires channelActive.
            return;
        }

        this.flushOutboundQueue();
        if (this.packetHandler instanceof IUpdatePlayerListBox) {
            ((IUpdatePlayerListBox) this.packetHandler).update();
        }

        activeChannel.flush();
    }

    public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
        super.channelActive(channelhandlercontext);
        Channel activeChannel = channelhandlercontext.channel();
        this.remoteAddress = activeChannel.remoteAddress();
        this.channel = activeChannel;

        try {
            this.setConnectionState(ConnectionState.HANDSHAKING);
        } catch (Throwable throwable) {
            net.minecraft.network.NetworkManager.logger.fatal(throwable);
        }
    }

    public void sendPacket(Packet packet) {
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, null);
        } else {
            this.outboundQueue.add(
                    new NetworkManagerInboundHandlerTuplePacketListener(
                            packet, (GenericFutureListener[]) null));
        }
    }

    private void flushOutboundQueue() {
        if (!this.isChannelOpen()) {
            return;
        }
        NetworkManagerInboundHandlerTuplePacketListener queued;
        while ((queued = this.outboundQueue.poll()) != null) {
            this.dispatchPacket(
                    NetworkManagerInboundHandlerTuplePacketListener.getQueuedPacket(queued),
                    NetworkManagerInboundHandlerTuplePacketListener.getQueuedListeners(queued));
        }
    }

    public boolean isEncrypted() {
        return this.isEncrypted;
    }

    public boolean isChannelOpen() {
        return this.channel != null && this.channel.isOpen();
    }

    public SocketAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public static Channel access$000(NetworkManager networkManager) {
        return networkManager.channel;
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
        this.channelRead0(channelhandlercontext, (Packet) object);
    }

    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
        this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
    }

    public static NetworkManager provideLocalClient(SocketAddress socketaddress) {
        NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        ((Bootstrap)
                        ((Bootstrap)
                                        ((Bootstrap)
                                                        new Bootstrap()
                                                                .group(
                                                                        (EventLoopGroup)
                                                                                net.minecraft
                                                                                        .network
                                                                                        .NetworkManager
                                                                                        .CLIENT_LOCAL_EVENTLOOP
                                                                                        .getValue()))
                                                .handler(
                                                        new LocalPacketChannelInitializer(
                                                                networkManager)))
                                .channel(LocalChannel.class))
                .connect(socketaddress)
                .syncUninterruptibly();
        return networkManager;
    }

    public PacketHandler getPacketHandler() {
        return this.packetHandler;
    }

    public NetworkManager(EnumPacketDirection buZValue) {
        this.direction = buZValue;
    }

    public void sendPacket(
            Packet packet,
            GenericFutureListener listener,
            GenericFutureListener... additionalListeners) {
        GenericFutureListener[] listeners = ArrayUtils.add(additionalListeners, 0, listener);
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, listeners);
        } else {
            this.outboundQueue.add(
                    new NetworkManagerInboundHandlerTuplePacketListener(packet, listeners));
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.network.NetworkManager.packedTextFragments = new String[3];
        net.minecraft.network.NetworkManager.packedTextFragments[0] = new String(":");
        net.minecraft.network.NetworkManager.logger = LogManager.getLogger();
        net.minecraft.network.NetworkManager.logMarkerNetwork = MarkerManager.getMarker("NETWORK");
        net.minecraft.network.NetworkManager.logMarkerPackets =
                MarkerManager.getMarker(
                        "NETWORK_PACKETS", net.minecraft.network.NetworkManager.logMarkerNetwork);
        net.minecraft.network.NetworkManager.attrKeyConnectionState =
                AttributeKey.valueOf("protocol");
        net.minecraft.network.NetworkManager.CLIENT_NIO_EVENTLOOP =
                new NetworkManagerClientNioEventloopLazyLoadBase();
        net.minecraft.network.NetworkManager.CLIENT_EPOLL_EVENTLOOP =
                new NetworkManagerClientEpollEventloopLazyLoadBase();
        net.minecraft.network.NetworkManager.CLIENT_LOCAL_EVENTLOOP =
                new ClientLocalEventLoopGroupLazyLoad();
    }

    // endregion

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
        net.minecraft.network.NetworkManager.logger.debug(
                "Disconnecting " + this.getRemoteAddress(), throwable);
        this.closeChannel(
                new ChatComponentTranslation(
                        "disconnect.genericReason",
                        new Object[] {"Internal Exception: " + throwable}));
    }

    public void setCompressionThreshold(int threshold) {
        ChannelPipeline pipeline = this.channel.pipeline();
        if (threshold < 0) {
            if (pipeline.get("decompress") instanceof NettyCompressionDecoder) {
                pipeline.remove("decompress");
            }
            if (pipeline.get("compress") instanceof NettyCompressionEncoder) {
                pipeline.remove("compress");
            }
            return;
        }
        if (pipeline.get("decompress") instanceof NettyCompressionDecoder) {
            ((NettyCompressionDecoder) pipeline.get("decompress"))
                    .setCompressionThreshold(threshold);
        } else {
            pipeline.addBefore("decoder", "decompress", new NettyCompressionDecoder(threshold));
        }
        if (pipeline.get("compress") instanceof NettyCompressionEncoder) {
            ((NettyCompressionEncoder) pipeline.get("compress")).setCompressionThreshold(threshold);
        } else {
            pipeline.addBefore("encoder", "compress", new NettyCompressionEncoder(threshold));
        }
    }

    public void closeChannel(ChatComponent bvhValue) {
        if (this.channel.isOpen()) {
            this.channel.close().awaitUninterruptibly();
            this.terminationReason = bvhValue;
        }
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.channel
                .attr(net.minecraft.network.NetworkManager.attrKeyConnectionState)
                .set(connectionState);
        this.channel.config().setAutoRead(true);
        net.minecraft.network.NetworkManager.logger.debug("Enabled auto read");
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Packet packet) {
        if (this.channel.isOpen()) {
            try {
                PacketEvent packetEvent = new PacketEvent(PacketDirection.RECEIVED, packet);
                RemixClient.eventBus.post(packetEvent);
                if (!packetEvent.isCancelled()) {
                    packetEvent.getPacket().processPacket(this.packetHandler);
                }
            } catch (ThreadQuickExitException threadQuickExitException) {
            }
        }
    }

    private void dispatchPacket(Packet packet, GenericFutureListener[] listeners) {
        ConnectionState packetState = ConnectionState.getFromPacket(packet);
        ConnectionState channelState =
                (ConnectionState) this.channel.attr(attrKeyConnectionState).get();
        if (channelState != packetState) {
            logger.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }

        if (!this.channel.eventLoop().inEventLoop()) {
            this.channel
                    .eventLoop()
                    .execute(
                            new DispatchPacketRunnable(
                                    this, packetState, channelState, packet, listeners));
            return;
        }
        if (packetState != channelState) {
            this.setConnectionState(packetState);
        }
        ChannelFuture write = this.channel.writeAndFlush(packet);
        if (listeners != null) {
            write.addListeners(listeners);
        }
        write.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public static NetworkManager createNetworkManagerAndConnect(
            InetAddress address, int port, boolean preferNativeTransport) {
        NetworkManager manager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NordVpnProxyManager proxyManager = ClientServices.nordVpnProxyManager;
        if (proxyManager.getProxyConfigData() != null) {
            String[] endpoint =
                    proxyManager.getSelectedProxyEndpoint().split(packedTextFragments[0]);
            ProxyType type = proxyManager.getProxyConfigData().getProxyType();
            Proxy proxy =
                    new Proxy(
                            type == ProxyType.HTTP ? Type.HTTP : Type.SOCKS,
                            new InetSocketAddress(endpoint[0], Integer.parseInt(endpoint[1])));
            new Bootstrap()
                    .group(new OioEventLoopGroup())
                    .handler(new NetworkSystemLanChannelInitializer(manager))
                    .channelFactory(new OioProxyChannelFactory(proxy, type))
                    .connect(address, port)
                    .syncUninterruptibly();
            return manager;
        }

        boolean useEpoll = preferNativeTransport && Epoll.isAvailable();
        LazyLoadBase eventLoop = useEpoll ? CLIENT_EPOLL_EVENTLOOP : CLIENT_NIO_EVENTLOOP;
        Class<? extends Channel> channelType =
                useEpoll ? EpollSocketChannel.class : NioSocketChannel.class;
        new Bootstrap()
                .group((EventLoopGroup) eventLoop.getValue())
                .handler(new NetworkManagerClientChannelInitializer(manager))
                .channel(channelType)
                .connect(address, port)
                .syncUninterruptibly();
        return manager;
    }

    public boolean hasNoChannel() {
        return this.channel == null;
    }

    public ChatComponent getExitMessage() {
        return this.terminationReason;
    }

    public void disableAutoRead() {
        this.channel.config().setAutoRead(false);
    }

    // Helper used only by this class.
    public static class DispatchPacketRunnable implements java.lang.Runnable {
        net.minecraft.network.ConnectionState packetState;
        net.minecraft.network.NetworkManager networkManager;
        net.minecraft.network.ConnectionState previousChannelState;
        io.netty.util.concurrent.GenericFutureListener[] listeners;
        net.minecraft.network.Packet packet;

        public DispatchPacketRunnable(
                net.minecraft.network.NetworkManager bvOValue,
                net.minecraft.network.ConnectionState bvNValue,
                net.minecraft.network.ConnectionState bvPValue,
                net.minecraft.network.Packet packet,
                io.netty.util.concurrent.GenericFutureListener[] agenericfuturelistener) {
            this.networkManager = bvOValue;
            this.packetState = bvNValue;
            this.previousChannelState = bvPValue;
            this.packet = packet;
            this.listeners = agenericfuturelistener;
        }

        @java.lang.Override
        public void run() {
            if (this.packetState != this.previousChannelState) {
                this.networkManager.setConnectionState(this.packetState);
            }

            io.netty.channel.ChannelFuture channelfuture =
                    net.minecraft.network.NetworkManager.access$000(this.networkManager)
                            .writeAndFlush(this.packet);
            if (this.listeners != null) {
                channelfuture.addListeners(this.listeners);
            }

            channelfuture.addListener(
                    io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }
}
