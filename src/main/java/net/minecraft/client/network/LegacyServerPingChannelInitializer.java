package net.minecraft.client.network;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;

public class LegacyServerPingChannelInitializer extends ChannelInitializer {
    ServerAddress arj;
    ServerData ark;
    OldServerPinger arl;

    public LegacyServerPingChannelInitializer(
            OldServerPinger arlValue, ServerAddress arjValue, ServerData arkValue) {
        this.arl = arlValue;
        this.arj = arjValue;
        this.ark = arkValue;
    }

    protected void initChannel(Channel channel) {
        try {
            channel.config().setOption(ChannelOption.IP_TOS, 24);
        } catch (ChannelException channelexception1) {
        }

        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
        } catch (ChannelException channelexception) {
        }

        channel.pipeline().addLast(new ChannelHandler[] {new OldServerPingerInitChannelSimpleChannelInboundHandler(this)});
    }
}
