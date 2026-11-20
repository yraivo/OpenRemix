package net.minecraft.client.network;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

class OldServerPingerInitChannelSimpleChannelInboundHandler extends SimpleChannelInboundHandler {

    private static String[] packedTextFragments;
    LegacyServerPingChannelInitializer aul;

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
        this.channelRead0(channelhandlercontext, (ByteBuf) object);
    }

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
        channelhandlercontext.close();
    }

    public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
        super.channelActive(channelhandlercontext);
        ClientPlayHandler.getLegacyPlayHandlerFlowMarker();

        ByteBuf bytebuf = Unpooled.buffer();

        try {
            bytebuf.writeByte(254);
            bytebuf.writeByte(1);
            bytebuf.writeByte(250);
            char[] achar = "MC|PingHost".toCharArray();
            bytebuf.writeShort(achar.length);
            char[] achar1 = achar;
            int length = achar.length;
            int j = 0;

            while (j < length) {
                char c0 = achar1[j];
                bytebuf.writeChar(c0);
                j++;
            }

            bytebuf.writeShort(7 + 2 * this.aul.arj.getIP().length());
            bytebuf.writeByte(127);
            achar = this.aul.arj.getIP().toCharArray();
            bytebuf.writeShort(achar.length);
            achar1 = achar;
            length = achar.length;
            j = 0;

            while (j < length) {
                char c1 = achar1[j];
                bytebuf.writeChar(c1);
                j++;
            }

            bytebuf.writeInt(this.aul.arj.getPort());
            channelhandlercontext
                    .channel()
                    .writeAndFlush(bytebuf)
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } finally {
            bytebuf.release();
        }
    }

    OldServerPingerInitChannelSimpleChannelInboundHandler(
            LegacyServerPingChannelInitializer aulValue) {
        this.aul = aulValue;
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) {
        short short1 = bytebuf.readUnsignedByte();
        if (short1 == 255) {
            String text =
                    new String(
                            remix.compat.library.LegacyLibraryAdapters.readBytes(
                                    bytebuf, bytebuf.readShort() * 2),
                            Charsets.UTF_16BE);
            String[] astring =
                    (String[])
                            Iterables.toArray(
                                    OldServerPinger.access$200().split(text), String.class);
            if ("\u00a71".equals(astring[0])) {
                int i = MathHelper.parseIntWithDefault(astring[1], 0);
                String textValue = astring[2];
                String textValue2 = astring[3];
                int j = MathHelper.parseIntWithDefault(astring[4], -1);
                int k = MathHelper.parseIntWithDefault(astring[5], -1);
                this.aul.ark.version = -1;
                this.aul.ark.gameVersion = textValue;
                this.aul.ark.serverMOTD = textValue2;
                this.aul.ark.populationInfo =
                        EnumChatFormatting.GRAY
                                + packedTextFragments[0]
                                + j
                                + packedTextFragments[1]
                                + EnumChatFormatting.DARK_GRAY
                                + packedTextFragments[2]
                                + EnumChatFormatting.GRAY
                                + k;
            }
        }

        channelhandlercontext.close();
    }

    static {
        net.minecraft.client.network.OldServerPingerInitChannelSimpleChannelInboundHandler
                        .packedTextFragments =
                new String[4];
        net.minecraft.client.network.OldServerPingerInitChannelSimpleChannelInboundHandler
                        .packedTextFragments[0] =
                new String("");
        net.minecraft.client.network.OldServerPingerInitChannelSimpleChannelInboundHandler
                        .packedTextFragments[1] =
                new String("");
        net.minecraft.client.network.OldServerPingerInitChannelSimpleChannelInboundHandler
                        .packedTextFragments[2] =
                new String("/");
    }
}
