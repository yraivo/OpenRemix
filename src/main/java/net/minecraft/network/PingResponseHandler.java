package net.minecraft.network;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import net.minecraft.server.MinecraftServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

public class PingResponseHandler extends ChannelInboundHandlerAdapter {

    private NetworkSystem networkSystem;

    private static Logger logger;

    private void writeAndFlush(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) {
        channelhandlercontext
                .pipeline()
                .firstContext()
                .writeAndFlush(bytebuf)
                .addListener(ChannelFutureListener.CLOSE);
    }

    public PingResponseHandler(NetworkSystem baQValue) {
        this.networkSystem = baQValue;
    }

    static {
        net.minecraft.network.PingResponseHandler.logger = LogManager.getLogger();
    }

    private ByteBuf getStringBuffer(String text) {
        ByteBuf bytebuf = Unpooled.buffer();
        bytebuf.writeByte(255);
        String textValue = PacketBuffer.getLegacyPacketBufferFlowMarker();
        char[] achar = text.toCharArray();
        String textValue2 = textValue;
        bytebuf.writeShort(achar.length);
        char[] achar1 = achar;
        int length = achar.length;
        int j = 0;

        while (j < length) {
            char c0 = achar1[j];
            bytebuf.writeChar(c0);
            j++;
            if (textValue2 == null) {
                break;
            }
        }

        return bytebuf;
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public void channelRead(ChannelHandlerContext channelhandlercontext, Object object) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        ByteBuf bytebuf = (ByteBuf) object;
        bytebuf.markReaderIndex();
        byte b0 = 1;
        String textValue = text;
        boolean flag = false /* VF: Semaphore variable */;

        label230:
        {
            label231:
            {
                label232:
                {
                    try {
                        flag = true;
                        if (bytebuf.readUnsignedByte() != 254) {
                            flag = false;
                            break label230;
                        }

                        InetSocketAddress inetsocketaddress =
                                (InetSocketAddress) channelhandlercontext.channel().remoteAddress();
                        MinecraftServer minecraftServer = this.networkSystem.getServer();
                        int i = bytebuf.readableBytes();
                        switch (i) {
                            case 0:
                                net.minecraft.network.PingResponseHandler.logger.debug(
                                        "Ping: (<1.3.x) from {}:{}",
                                        new Object[] {
                                            inetsocketaddress.getAddress(),
                                            inetsocketaddress.getPort()
                                        });
                                String textValue2 =
                                        String.format(
                                                "%s\u00a7%d\u00a7%d",
                                                minecraftServer.QL(),
                                                minecraftServer.getCurrentPlayerCount(),
                                                minecraftServer.getMaxPlayers());
                                this.writeAndFlush(
                                        channelhandlercontext, this.getStringBuffer(textValue2));
                                if (textValue != null) {
                                    break;
                                }
                            case 1:
                                if (bytebuf.readUnsignedByte() != 1) {
                                    flag = false;
                                    break label231;
                                }

                                net.minecraft.network.PingResponseHandler.logger.debug(
                                        "Ping: (1.4-1.5.x) from {}:{}",
                                        new Object[] {
                                            inetsocketaddress.getAddress(),
                                            inetsocketaddress.getPort()
                                        });
                                String textValue3 =
                                        String.format(
                                                "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                                                127,
                                                minecraftServer.QP(),
                                                minecraftServer.QL(),
                                                minecraftServer.getCurrentPlayerCount(),
                                                minecraftServer.getMaxPlayers());
                                this.writeAndFlush(
                                        channelhandlercontext, this.getStringBuffer(textValue3));
                                if (textValue != null) {
                                    break;
                                }
                            default:
                                int j = bytebuf.readUnsignedByte() == 1 ? 1 : 0;
                                j &= bytebuf.readUnsignedByte() == 250 ? 1 : 0;
                                j &=
                                        remix.compat.compiler.JvmBooleanSlots.toInt(
                                                "MC|PingHost"
                                                        .equals(
                                                                new String(
                                                                        remix.compat.library
                                                                                .LegacyLibraryAdapters
                                                                                .readBytes(
                                                                                        bytebuf,
                                                                                        bytebuf
                                                                                                        .readShort()
                                                                                                * 2),
                                                                        Charsets.UTF_16BE)));
                                int k = bytebuf.readUnsignedShort();
                                j &= bytebuf.readUnsignedByte() >= 73 ? 1 : 0;
                                j &=
                                        3
                                                                + remix.compat.library
                                                                        .LegacyLibraryAdapters
                                                                        .readBytes(
                                                                                bytebuf,
                                                                                bytebuf.readShort()
                                                                                        * 2)
                                                                        .length
                                                                + 4
                                                        == k
                                                ? 1
                                                : 0;
                                j &= bytebuf.readInt() <= 65535 ? 1 : 0;
                                j &= bytebuf.readableBytes() == 0 ? 1 : 0;
                                if (j == 0) {
                                    flag = false;
                                    break label232;
                                }

                                net.minecraft.network.PingResponseHandler.logger.debug(
                                        "Ping: (1.6) from {}:{}",
                                        new Object[] {
                                            inetsocketaddress.getAddress(),
                                            inetsocketaddress.getPort()
                                        });
                                String textValue4 =
                                        String.format(
                                                "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                                                127,
                                                minecraftServer.QP(),
                                                minecraftServer.QL(),
                                                minecraftServer.getCurrentPlayerCount(),
                                                minecraftServer.getMaxPlayers());
                                ByteBuf bytebuf1 = this.getStringBuffer(textValue4);

                                try {
                                    this.writeAndFlush(channelhandlercontext, bytebuf1);
                                } finally {
                                    bytebuf1.release();
                                }
                        }

                        bytebuf.release();
                        b0 = 0;
                        flag = false;
                    } catch (RuntimeException runtimeexception) {
                        flag = false;
                    } finally {
                        if (flag) {
                            if (b0 != 0) {
                                bytebuf.resetReaderIndex();
                                channelhandlercontext.channel().pipeline().remove("legacy_query");
                                channelhandlercontext.fireChannelRead(object);
                            }
                        }
                    }

                    if (b0 != 0) {
                        bytebuf.resetReaderIndex();
                        channelhandlercontext.channel().pipeline().remove("legacy_query");
                        channelhandlercontext.fireChannelRead(object);
                    }

                    return;
                }

                if (b0 != 0) {
                    bytebuf.resetReaderIndex();
                    channelhandlercontext.channel().pipeline().remove("legacy_query");
                    channelhandlercontext.fireChannelRead(object);
                }

                return;
            }

            if (b0 != 0) {
                bytebuf.resetReaderIndex();
                channelhandlercontext.channel().pipeline().remove("legacy_query");
                channelhandlercontext.fireChannelRead(object);
            }

            return;
        }

        if (b0 != 0) {
            bytebuf.resetReaderIndex();
            channelhandlercontext.channel().pipeline().remove("legacy_query");
            channelhandlercontext.fireChannelRead(object);
        }
    }
}
