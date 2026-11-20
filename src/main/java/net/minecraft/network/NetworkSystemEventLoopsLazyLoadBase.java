package net.minecraft.network;

import net.minecraft.util.LazyLoadBase;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.nio.NioEventLoopGroup;

public final class NetworkSystemEventLoopsLazyLoadBase extends LazyLoadBase {
    private static String c;

    protected Object load() {
        return this.genericLoad();
    }

    static {
        c = "Netty Server IO #%d";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected NioEventLoopGroup genericLoad() {
        return new NioEventLoopGroup(
                0, new ThreadFactoryBuilder().setNameFormat(c).setDaemon(true).build());
    }
}
