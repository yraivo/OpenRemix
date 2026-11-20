package net.minecraft.network;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

public final class NetworkSystemLanChannelInitializer extends ChannelInitializer {

    NetworkManager networkManager;


    protected void initChannel(Channel channel) {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
            channel.config().setOption(ChannelOption.IP_TOS, 24);
        } catch (ChannelException channelexception) {
        }

        channel.pipeline()
                .addLast("timeout", new ReadTimeoutHandler(6000))
                .addLast("splitter", new MessageDeserializer2())
                .addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND))
                .addLast("prepender", new MessageSerializer2())
                .addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND))
                .addLast("packet_handler", this.networkManager);
    }

    public NetworkSystemLanChannelInitializer(NetworkManager awSValue) {
        this.networkManager = awSValue;
    }
}
