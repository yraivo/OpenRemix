package net.minecraft.network;

import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class NetworkSystemAddLocalEndpointChannelInitializer extends ChannelInitializer {
    private static String b;
    NetworkSystem bsZ;

    static {
        b = "packet_handler";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public NetworkSystemAddLocalEndpointChannelInitializer(NetworkSystem bsZValue) {
        this.bsZ = bsZValue;
    }

    protected void initChannel(Channel channel) {
        NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
        networkManager.setPacketHandler(
                new NetHandlerHandshakeMemory(NetworkSystem.access$100(this.bsZ), networkManager));
        NetworkSystem.access$000(this.bsZ).add(networkManager);
        channel.pipeline().addLast(b, networkManager);
    }
}
