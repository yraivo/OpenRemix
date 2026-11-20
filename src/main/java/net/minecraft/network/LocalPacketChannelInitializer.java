package net.minecraft.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

final class LocalPacketChannelInitializer extends ChannelInitializer {
    private static String b;

    NetworkManager field_179248_a;

    protected void initChannel(Channel channel) {
        channel.pipeline().addLast(b, this.field_179248_a);
    }

    // region String decoding support

    // endregion

    static {
        b = "packet_handler";
    }

    // region String decoding support

    // endregion

    LocalPacketChannelInitializer(NetworkManager awSValue) {
        this.field_179248_a = awSValue;
    }
}
