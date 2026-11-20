package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import net.minecraft.network.ConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;

public class MessageSerializer extends MessageToByteEncoder {

    private EnumPacketDirection direction;
    private static Marker RECEIVED_PACKET_MARKER;
    private static Logger logger;

    protected void encode(
            ChannelHandlerContext channelhandlercontext, Packet packet, ByteBuf bytebuf)
            throws IOException {
        Integer integer =
                ((ConnectionState)
                                channelhandlercontext
                                        .channel()
                                        .attr(NetworkManager.attrKeyConnectionState)
                                        .get())
                        .getPacketId(this.direction, packet);

        try {
            if (net.minecraft.util.MessageSerializer.logger.isDebugEnabled()) {
                net.minecraft.util.MessageSerializer.logger.debug(
                        net.minecraft.util.MessageSerializer.RECEIVED_PACKET_MARKER,
                        "OUT: [{}:{}] {}",
                        new Object[] {
                            channelhandlercontext
                                    .channel()
                                    .attr(NetworkManager.attrKeyConnectionState)
                                    .get(),
                            integer,
                            packet.getClass().getName()
                        });
            }
        } catch (Throwable throwable1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable1));
        }

        try {
            if (integer == null) {
                throw new IOException("Can't serialize unregistered packet");
            }
        } catch (Throwable throwable2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable2));
        }

        PacketBuffer packetBuffer = new PacketBuffer(bytebuf);
        packetBuffer.writeVarIntToBuffer(integer);

        try {
            if (packet instanceof S0CPacketSpawnPlayer) {
                packet = packet;
            }

            packet.writePacketData(packetBuffer);
        } catch (Throwable throwable) {
            net.minecraft.util.MessageSerializer.logger.error(throwable);
        }
    }

    static {
        net.minecraft.util.MessageSerializer.logger = LogManager.getLogger();
        net.minecraft.util.MessageSerializer.RECEIVED_PACKET_MARKER =
                MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
    }

    protected void encode(
            ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf)
            throws IOException {
        this.encode(channelhandlercontext, (Packet) object, bytebuf);
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public MessageSerializer(EnumPacketDirection bxeValue) {
        this.direction = bxeValue;
    }
}
