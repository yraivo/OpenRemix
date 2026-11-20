package net.minecraft.util;

import net.minecraft.network.PacketBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageSerializer2 extends MessageToByteEncoder {

    protected void encode(
            ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, ByteBuf bytebuf1) {
        int i = bytebuf.readableBytes();
        int j = PacketBuffer.getVarIntSize(i);

        try {
            if (j > 3) {
                throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        PacketBuffer packetBuffer = new PacketBuffer(bytebuf1);
        packetBuffer.ensureWritable(j + i);
        packetBuffer.writeVarIntToBuffer(i);
        packetBuffer.writeBytes(bytebuf, bytebuf.readerIndex(), i);
    }


    protected void encode(
            ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf) {
        this.encode(channelhandlercontext, (ByteBuf) object, bytebuf);
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }
}
