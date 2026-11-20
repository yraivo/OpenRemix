package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptingEncoder extends MessageToByteEncoder {
    private NettyEncryptionTranslator encryptionCodec;

    protected void encode(
            ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf)
            throws ShortBufferException {
        this.encode(channelhandlercontext, (ByteBuf) object, bytebuf);
    }

    protected void encode(
            ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, ByteBuf bytebuf1)
            throws ShortBufferException {
        this.encryptionCodec.cipher(bytebuf, bytebuf1);
    }

    public NettyEncryptingEncoder(Cipher cipher) {
        this.encryptionCodec = new NettyEncryptionTranslator(cipher);
    }
}
