package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class NettyCompressionDecoder extends ByteToMessageDecoder {
    private int treshold;

    private Inflater inflater;

    protected void decode(ChannelHandlerContext context, ByteBuf input, List output)
            throws DataFormatException {
        if (!input.isReadable()) {
            return;
        }
        PacketBuffer frame = new PacketBuffer(input);
        int uncompressedSize = frame.readVarIntFromBuffer();
        if (uncompressedSize == 0) {
            output.add(frame.readBytes(frame.readableBytes()));
            return;
        }
        if (uncompressedSize < this.treshold || uncompressedSize < 0) {
            throw new DecoderException("Badly compressed packet - size of " + uncompressedSize
                    + " is below server threshold of " + this.treshold);
        }
        if (uncompressedSize > 2097152) {
            throw new DecoderException("Badly compressed packet - size of " + uncompressedSize
                    + " is larger than protocol maximum of " + 2097152);
        }
        byte[] payload = new byte[frame.readableBytes()];
        frame.readBytes(payload);
        byte[] uncompressed = new byte[uncompressedSize];
        try {
            this.inflater.setInput(payload);
            int written = this.inflater.inflate(uncompressed);
            if (written != uncompressedSize || !this.inflater.finished()) {
                throw new DecoderException("Compressed packet did not match its declared size");
            }
            output.add(Unpooled.wrappedBuffer(uncompressed));
        } finally {
            this.inflater.reset();
        }
    }

    public void setCompressionThreshold(int tresholdValue) {
        this.treshold = tresholdValue;
    }


    public NettyCompressionDecoder(int tresholdValue) {
        this.treshold = tresholdValue;
        this.inflater = new Inflater();
    }
}
