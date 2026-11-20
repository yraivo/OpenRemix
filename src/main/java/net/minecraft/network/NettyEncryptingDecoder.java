package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptingDecoder extends MessageToMessageDecoder {
    private NettyEncryptionTranslator decryptionCodec;

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List list)
            throws ShortBufferException {
        list.add(this.decryptionCodec.decipher(channelhandlercontext, bytebuf));
    }

    public NettyEncryptingDecoder(Cipher cipher) {
        this.decryptionCodec = new NettyEncryptionTranslator(cipher);
    }

    protected void decode(ChannelHandlerContext channelhandlercontext, Object object, List list)
            throws ShortBufferException {
        this.decode(channelhandlercontext, (ByteBuf) object, list);
    }
}
