package net.minecraft.network;

import net.minecraft.util.LazyLoadBase;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.local.LocalEventLoopGroup;

public final class NetworkSystemServerLocalEventloopLazyLoadBase extends LazyLoadBase {

    private static String c;

    protected Object load() {
        return this.genericLoad();
    }

    protected LocalEventLoopGroup genericLoad() {
        return new LocalEventLoopGroup(
                0, new ThreadFactoryBuilder().setNameFormat(c).setDaemon(true).build());
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        c = "Netty Local Server IO #%d";
    }
}
