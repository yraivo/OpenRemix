package net.minecraft.network;

import net.minecraft.util.LazyLoadBase;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.epoll.EpollEventLoopGroup;

public final class NetworkManagerClientEpollEventloopLazyLoadBase extends LazyLoadBase {

    private static String c;

    protected Object load() {
        return this.Ts();
    }

    protected EpollEventLoopGroup Ts() {
        return new EpollEventLoopGroup(
                0, new ThreadFactoryBuilder().setNameFormat(c).setDaemon(true).build());
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        c = "Netty Epoll Client IO #%d";
    }
}
