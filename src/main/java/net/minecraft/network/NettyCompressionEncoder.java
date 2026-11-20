package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Deflater;

public class NettyCompressionEncoder extends MessageToByteEncoder {
    private int treshold;
    private Deflater deflater;
    private final byte[] buffer = new byte[8192];

    protected void encode(
            ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf) {
        this.compress(channelhandlercontext, (ByteBuf) object, bytebuf);
    }

    protected void compress(ChannelHandlerContext context, ByteBuf input, ByteBuf output) {
        int size = input.readableBytes();
        PacketBuffer frame = new PacketBuffer(output);
        if (size < this.treshold) {
            frame.writeVarIntToBuffer(0);
            frame.writeBytes(input);
            return;
        }

        byte[] payload = new byte[size];
        input.readBytes(payload);
        frame.writeVarIntToBuffer(size);
        try {
            this.deflater.setInput(payload);
            this.deflater.finish();
            while (!this.deflater.finished()) {
                int written = this.deflater.deflate(this.buffer);
                frame.writeBytes(this.buffer, 0, written);
            }
        } finally {
            this.deflater.reset();
        }
    }

    public void setCompressionThreshold(int i) {
        this.treshold = i;
    }

    public NettyCompressionEncoder(int i) {
        this.treshold = i;
        this.deflater = new Deflater();
    }
}
