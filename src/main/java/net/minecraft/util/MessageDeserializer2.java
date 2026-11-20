package net.minecraft.util;

import net.minecraft.network.PacketBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class MessageDeserializer2 extends ByteToMessageDecoder {
    private static String a;

    static {
        a = "length wider than 21-bit";
    }

    // region String decoding support

    // endregion

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List list) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        bytebuf.markReaderIndex();
        byte[] abyte = new byte[3];
        String textValue = text;
        int i = 0;

        while (i < abyte.length) {
            try {
                if (!bytebuf.isReadable()) {
                    bytebuf.resetReaderIndex();
                    return;
                }
            } catch (CorruptedFrameException corruptedframeexception) {
                throw method_2abdaf5eb728eb1c59f6(corruptedframeexception);
            }

            abyte[i] = bytebuf.readByte();
            if (abyte[i] >= 0) {
                PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(abyte));

                try {
                    int j = packetBuffer.readVarIntFromBuffer();
                    if (bytebuf.readableBytes() < j) {
                        bytebuf.resetReaderIndex();
                        return;
                    }

                    list.add(bytebuf.readBytes(j));
                } finally {
                    packetBuffer.release();
                }

                return;
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        throw new CorruptedFrameException(a);
    }

    private static CorruptedFrameException method_2abdaf5eb728eb1c59f6(
            CorruptedFrameException corruptedframeexception) {
        return corruptedframeexception;
    }

    // region String decoding support

    // endregion
}
