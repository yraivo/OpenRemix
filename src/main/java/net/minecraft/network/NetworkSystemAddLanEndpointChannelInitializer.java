package net.minecraft.network;

import net.minecraft.entity.Entity;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PingResponseHandler;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NetworkSystemAddLanEndpointChannelInitializer extends ChannelInitializer {

    NetworkSystem bsZ;
    private static String[] packedTextFragments;

    protected void initChannel(Channel channel) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();

        try {
            channel.config().setOption(ChannelOption.IP_TOS, 24);
        } catch (ChannelException channelexception2) {
        }

        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, false);
        } catch (ChannelException channelexception1) {
        }

        channel.pipeline()
                .addLast("timeout", new ReadTimeoutHandler(30))
                .addLast("legacy_query", new PingResponseHandler(this.bsZ))
                .addLast("splitter", new MessageDeserializer2())
                .addLast("decoder", new MessageDeserializer(EnumPacketDirection.SERVERBOUND))
                .addLast("prepender", new MessageSerializer2())
                .addLast("encoder", new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
        NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);

        try {
            NetworkSystem.access$000(this.bsZ).add(networkManager);
            channel.pipeline().addLast("packet_handler", networkManager);
            networkManager.setPacketHandler(
                    new NetHandlerHandshakeTCP(NetworkSystem.access$100(this.bsZ), networkManager));
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                PacketBuffer.setLegacyPacketBufferFlowMarker(packedTextFragments[0]);
            }
        } catch (ChannelException channelexception) {
            throw method_88f695b4c75452585b26(channelexception);
        }
    }

    private static ChannelException method_88f695b4c75452585b26(ChannelException channelexception) {
        return channelexception;
    }

    static {
        net.minecraft.network.NetworkSystemAddLanEndpointChannelInitializer.packedTextFragments = new String[3];
        net.minecraft.network.NetworkSystemAddLanEndpointChannelInitializer.packedTextFragments[0] = new String("wrNPZb");
    }

    public NetworkSystemAddLanEndpointChannelInitializer(NetworkSystem bsZValue) {
        this.bsZ = bsZValue;
    }
}
