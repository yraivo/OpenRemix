package net.minecraft.network;

import net.minecraft.util.LazyLoadBase;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.nio.NioEventLoopGroup;

public final class NetworkManagerClientNioEventloopLazyLoadBase extends LazyLoadBase {
    private static String c;

    static {
        c = "Netty Client IO #%d";
    }

    // region String decoding support

    // endregion

    protected Object load() {
        return this.genericLoad();
    }

    protected NioEventLoopGroup genericLoad() {
        return new NioEventLoopGroup(
                0, new ThreadFactoryBuilder().setNameFormat(c).setDaemon(true).build());
    }

    // region String decoding support

    // endregion
}
