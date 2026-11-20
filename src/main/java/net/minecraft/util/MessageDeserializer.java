package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import net.minecraft.network.ConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.util.List;

public class MessageDeserializer extends ByteToMessageDecoder {
    private static Marker RECEIVED_PACKET_MARKER;

    private EnumPacketDirection direction;
    private static Logger logger;
    private static String[] packedTextFragments;

    public MessageDeserializer(EnumPacketDirection bxeValue) {
        this.direction = bxeValue;
    }

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List list)
            throws IOException, InstantiationException, IllegalAccessException {
        if (bytebuf.readableBytes() != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(bytebuf);
            int i = packetBuffer.readVarIntFromBuffer();
            Packet packet =
                    ((ConnectionState)
                                    channelhandlercontext
                                            .channel()
                                            .attr(NetworkManager.attrKeyConnectionState)
                                            .get())
                            .getPacket(this.direction, i);

            try {
                if (packet == null) {
                    throw new IOException("Bad packet id " + i);
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
            }

            try {
                packet.readPacketData(packetBuffer);
                if (packetBuffer.readableBytes() > 0) {
                    throw new IOException(
                            "Packet "
                                    + ((ConnectionState)
                                                    channelhandlercontext
                                                            .channel()
                                                            .attr(
                                                                    NetworkManager
                                                                            .attrKeyConnectionState)
                                                            .get())
                                            .getId()
                                    + packedTextFragments[0]
                                    + i
                                    + " ("
                                    + packet.getClass().getSimpleName()
                                    + ") was larger than I expected, found "
                                    + packetBuffer.readableBytes()
                                    + " bytes extra whilst reading packet "
                                    + i);
                }
            } catch (IOException ioexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception2));
            }

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                list.add(packet);
                if (net.minecraft.util.MessageDeserializer.logger.isDebugEnabled()) {
                    net.minecraft.util.MessageDeserializer.logger.debug(
                            net.minecraft.util.MessageDeserializer.RECEIVED_PACKET_MARKER,
                            " IN: [{}:{}] {}",
                            new Object[] {
                                channelhandlercontext
                                        .channel()
                                        .attr(NetworkManager.attrKeyConnectionState)
                                        .get(),
                                i,
                                packet.getClass().getName()
                            });
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
            }
        }
    }

    static {
        net.minecraft.util.MessageDeserializer.packedTextFragments = new String[3];
        net.minecraft.util.MessageDeserializer.packedTextFragments[0] = new String("/");
        net.minecraft.util.MessageDeserializer.logger = LogManager.getLogger();
        net.minecraft.util.MessageDeserializer.RECEIVED_PACKET_MARKER =
                MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
