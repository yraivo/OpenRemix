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

public final class NetworkManagerClientChannelInitializer extends ChannelInitializer<Channel> {

    NetworkManager networkManager;

    protected void initChannel(Channel channel) {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
        } catch (ChannelException channelexception) {
        }

        channel.pipeline()
                .addLast("timeout", new ReadTimeoutHandler(30))
                .addLast("splitter", new MessageDeserializer2())
                .addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND))
                .addLast("prepender", new MessageSerializer2())
                .addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND))
                .addLast("packet_handler", this.networkManager);
    }


    // Recovered explicit constructors from original mapped instruction streams.
    public NetworkManagerClientChannelInitializer(NetworkManager awSValue) {
        super();
        this.networkManager = awSValue;
    }
}
