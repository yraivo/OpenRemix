package net.minecraft.network;

import net.minecraft.util.LazyLoadBase;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.local.LocalEventLoopGroup;

public final class ClientLocalEventLoopGroupLazyLoad extends LazyLoadBase {
    private static String c;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected LocalEventLoopGroup Tv() {
        return new LocalEventLoopGroup(
                0, new ThreadFactoryBuilder().setNameFormat(c).setDaemon(true).build());
    }

    static {
        c = "Netty Local Client IO #%d";
    }

    protected Object load() {
        return this.Tv();
    }
}
