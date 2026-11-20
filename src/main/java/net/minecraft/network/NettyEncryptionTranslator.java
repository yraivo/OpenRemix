package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptionTranslator {
    private byte[] field_150505_b = new byte[0];
    private Cipher cipher;
    private byte[] field_150506_c = new byte[0];

    protected ByteBuf decipher(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf)
            throws ShortBufferException {
        int i = bytebuf.readableBytes();
        byte[] abyte = this.func_150502_a(bytebuf);
        ByteBuf bytebuf1 = channelhandlercontext.alloc().heapBuffer(this.cipher.getOutputSize(i));
        bytebuf1.writerIndex(
                this.cipher.update(abyte, 0, i, bytebuf1.array(), bytebuf1.arrayOffset()));
        return bytebuf1;
    }

    private byte[] func_150502_a(ByteBuf bytebuf) {
        int i = bytebuf.readableBytes();
        if (this.field_150505_b.length < i) {
            this.field_150505_b = new byte[i];
        }

        bytebuf.readBytes(this.field_150505_b, 0, i);
        return this.field_150505_b;
    }

    protected void cipher(ByteBuf bytebuf, ByteBuf bytebuf1) throws ShortBufferException {
        int i = bytebuf.readableBytes();
        byte[] abyte = this.func_150502_a(bytebuf);
        int j = this.cipher.getOutputSize(i);

        try {
            if (false)
                throw (javax.crypto.ShortBufferException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (this.field_150506_c.length < j) {
                this.field_150506_c = new byte[j];
            }
        } catch (ShortBufferException shortbufferexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_2154e27351125fc7be44(shortbufferexception));
        }

        bytebuf1.writeBytes(
                this.field_150506_c, 0, this.cipher.update(abyte, 0, i, this.field_150506_c));
    }

    protected NettyEncryptionTranslator(Cipher cipher) {
        this.cipher = cipher;
    }

    private static ShortBufferException method_2154e27351125fc7be44(
            ShortBufferException shortbufferexception) {
        return shortbufferexception;
    }
}
