package net.minecraft.world;

import java.util.concurrent.Callable;

class WorldPlayersCrashDetail implements Callable {

    World field_77440_a;
    private static String b;

    WorldPlayersCrashDetail(World world) {
        this.field_77440_a = world;
    }

    // region String decoding support

    // endregion

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    static {
        b = " total; ";
    }

    public String getCrashDetail() {
        return this.field_77440_a.playerEntities.size()
                + b
                + this.field_77440_a.playerEntities.toString();
    }

    // region String decoding support

    // endregion
}
